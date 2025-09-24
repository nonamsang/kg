package com.moodshop.kokone.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moodshop.kokone.service.MainMoodShopService;
import com.moodshop.kokone.service.OrderService;
import com.moodshop.kokone.service.ProductService;
import com.moodshop.kokone.vo.Company1VO;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.ProductOptionVO;
import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.UserVO;

@Controller
public class ProductController {

	@Autowired
	ServletContext servletContext;

	@Resource(name = "ProductService")
	private ProductService productService;

	@Autowired
	private MainMoodShopService mainService;
	
	//김동주가 추가한 것
	@Resource(name = "OrderService")
	private OrderService orderService;
		
	// 최근본 상품에 로그가 남으면서 제품상세페이지로 가는것
	@RequestMapping("MainProductOption.do")
		public String Viewlist(@RequestParam("product_id") String product_id, Model model, HttpServletRequest request,
				HttpServletResponse response, HttpSession session) {
			String user_id = (String) session.getAttribute("user_id");

			// 상품 정보 조회 (없으면 상세 페이지로 갈 수 없음)
			ProductVO product = productService.SelectedProduct(product_id);
			if (product == null) {
				return "productNotFound";
			}

			// 모델에 상품 담기 → JSP에서 ${product.속성명} 으로 출력 가능
			model.addAttribute("product", product);

			// 쿠키 처리
			String cookie = "recent" + user_id;
			String value = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals(cookie)) {
						value = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
						break;
					}
				}
			}

			List<String> ids = new LinkedList<>();
			if (!value.isEmpty()) {
				ids = new LinkedList<>(Arrays.asList(value.split(",")));
				ids.remove(product_id); // 중복 제거
			}
			ids.add(0, product_id); // 최신 상품을 맨 앞에 추가

			// 쿠키 저장
			String newValue = String.join(",", ids);
			String encodedValue = URLEncoder.encode(newValue, StandardCharsets.UTF_8);
			Cookie newCookie = new Cookie(cookie, encodedValue);
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 유지
			response.addCookie(newCookie);

			// 제품 상세 페이지로 이동
			return "MainProductOption";

		}

		// 최근본 상품 목록으로 가는것
		@RequestMapping("MyViewItemList.do")
		public String showViewedItems(HttpServletRequest request, HttpSession session, Model model) {
			UserVO user = (UserVO) session.getAttribute("userVO");
			if (user == null) {
				return "redirect:MyLogin.do"; // 비로그인 상태면 로그인 페이지로
			}
			String user_id = user.getUser_id();
			String cookieName = "recent" + user_id;
			String value = "";

			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals(cookieName)) {
						value = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
						System.out.println(value);
						break;
					}
				}
			}

			List<ProductVO> viewedProducts = new ArrayList<>();
			if (!value.isEmpty()) {
				String[] ids = value.split(",");
				for (String id : ids) {
					ProductVO product = productService.SelectedProduct(id);
					if (product != null) {
						viewedProducts.add(product);
					}
				}
			}

			model.addAttribute("last", viewedProducts);
			return "MyProductLastView"; // 이건 최근본상품.jsp 이름
		}

		@RequestMapping(value = "MyProductLastDelete.do", method = RequestMethod.POST)
		public String deleteViewedProducts(@RequestParam("product_id") List<String> deleteList,
		                                   HttpServletRequest request,
		                                   HttpServletResponse response,
		                                   HttpSession session) {
			String user_id = (String) session.getAttribute("user_id");
		    String cookieName = "recent" + user_id;
		    String value = "";

		    Cookie[] cookies = request.getCookies();
		    if (cookies != null) {
		        for (Cookie c : cookies) {
		            if (c.getName().equals(cookieName)) {
		                value = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
		                break;
		            }
		        }
		    }

		    if (!value.isEmpty()) {
		        List<String> ids = new ArrayList<>(Arrays.asList(value.split(",")));
		        ids.removeAll(deleteList); // 선택된 상품 ID 제거

		        // 새 쿠키 생성
		        String newValue = URLEncoder.encode(String.join(",", ids), StandardCharsets.UTF_8);
		        Cookie newCookie = new Cookie(cookieName, newValue);
		        newCookie.setPath("/");
		        newCookie.setMaxAge(60 * 60 * 24 * 30); // 30일
		        response.addCookie(newCookie);
		    }

			return "redirect:/MyViewItemList.do";
		}
	// 상품 전체 조회
	@RequestMapping("MainProduct.do")
	public String product(Model model) {
		ArrayList<ProductVO> product = productService.getAllLastProduct();
		model.addAttribute("product", product);
		return "MainProducts";

	}

	// 김동주 작성
	@RequestMapping("wishRanking.do")
	public String wishCountRanking(HttpSession session, Model model) {
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		if (manager == null) {
			return "redirect:MyLogin.do";
		}
		String managerId = manager.getManager_id();

		// 회사 전체 상품의 찜 순위 (Top5 + 기타)
		List<Map<String, Object>> chartData = productService.getWishChartData(managerId);
		model.addAttribute("totalTop5", chartData);

		return "WishRanking";
	}

	////////////////////////////////////////////////
	// 관리자 상품에 대해서 백업
	// 해당하는 회사의 상품조회 (관리자)
	@RequestMapping("MProductList.do")
	public String product2(Model model, HttpSession session) {
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		if (manager == null) {
			return "redirect:MyLogin.do";
		}
		String managerId = manager.getManager_id();
		
		String company_name = mainService.get_companyid_by_managerid(managerId);

		ArrayList<ProductVO> product = productService.MSelectProduct(company_name);
		if (product.isEmpty() || product == null) {
			System.out.println("상품이 없습니다.");
		} else {
			model.addAttribute("product", product);
		}
		return "ManagerProductList";

	}

	// 감정기준, 카테고리기준
	@RequestMapping("MProductAlign.do")
	public String mc(@RequestParam("malign") String malign, Model model, HttpSession session) {
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		if (manager == null) {
			return "redirect:MyLogin.do";
		}
		String managerId = manager.getManager_id();
		
		// 매니저가 관리 중인 회사 목록 조회
		List<Company1VO> companyVOs = orderService.getCompany(managerId);
		
		if (companyVOs == null || companyVOs.isEmpty()) {
			return "redirect:/AdminMainPage.do";
		}
		
		String company_name = mainService.get_companyid_by_managerid(managerId);
		ArrayList<ProductVO> product;
		if (malign.equals("mood")) {
			product = productService.MAlinProductM(company_name);
		} else if (malign.equals("category")) {
			product = productService.MAlinProductC(company_name);
		} else { // 상품아이디 기준
			product = productService.MSelectProduct(company_name);
		}
		model.addAttribute("product", product);
		return "ManagerProductList";

	}

	// 해당하는 회사의 상품조회(상품상세) (관리자)
	@RequestMapping("MProductDetail.do")
	public String product3(Model model, HttpSession session) {
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		if (manager == null) {
			return "redirect:MyLogin.do";
		}
		String managerId = manager.getManager_id();
		
		String company_name = mainService.get_companyid_by_managerid(managerId);
		ArrayList<ProductVO> product = productService.MSelectProduct(company_name);
		if (product.isEmpty() || product == null) {
			System.out.println("상품이 없습니다.");
		} else {
			model.addAttribute("product", product);
		}
		return "ManagerProductOption";

	}

	// 조회하는 해당상품의 제품 상세 클릭(관리자)
	@RequestMapping(value = "/MProductDetails.do", method = RequestMethod.POST)
	public String detailmore(@RequestParam("action") String action, Model model, HttpSession session,
			@RequestParam("product_id") String product_id) {
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		if (manager == null) {
			return "redirect:MyLogin.do";
		}
		String managerId = manager.getManager_id();
		String company_name = mainService.get_companyid_by_managerid(managerId);
		if (action.equals("more")) {
			ProductVO product = productService.MselectProDetail(company_name, product_id);
			ArrayList<ProductOptionVO> optionList = productService.MSelectProductDAll(product_id);
			model.addAttribute("product", product);
			model.addAttribute("optionlist", optionList);
		}
		return "ManagerProductOptionAll";
	}
	// 해당하는 회사의
	/*
	 * @RequestMapping("/MProductDetail.do") public String product3(Model model,
	 * HttpSession session) { String company_name="C000002"; //String
	 * company_name=(String) session.getAttribute("company_name"); 지금은 로그인 상태가 아니다 보니 주석
	 * List<ProductVO> productList = productService.MSelectProducts(company_name,
	 * product_id); ArrayList<Product_OptionVO>
	 * optionList=productService.MSelectProductD(product_id);
	 * model.addAttribute("productlist", productList);
	 * model.addAttribute("optionlist", optionList); return
	 * "ManagerProductOptionList"; return "ManagerProductList";
	 * 
	 * }
	 */

	@RequestMapping(value = "MProductUD.do", method = RequestMethod.POST)
	public String productUpdateOrDelete(@RequestParam("action") String action,
			@RequestParam(value = "product_id", required = false) List<String> product_id, Model model,
			RedirectAttributes redirectAttributes, HttpSession session) {
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		if (manager == null) {
			return "redirect:MyLogin.do";
		}
		String managerId = manager.getManager_id();
		String company_name = mainService.get_companyid_by_managerid(managerId);
		if (product_id == null || product_id.isEmpty()) {
			ArrayList<ProductVO> productList = productService.MSelectProduct(company_name);
			model.addAttribute("msg", "상품을 선택해주세요");
			model.addAttribute("product", productList);
			return "ManagerProductList";
		} else if (action.equals("more")) {
			List<ProductVO> productList = productService.MSelectProducts(company_name, product_id);
			ArrayList<ProductOptionVO> optionList = productService.MSelectProductD(product_id);
			model.addAttribute("productlist", productList);
			model.addAttribute("optionlist", optionList);
			return "ManagerProductOptionList";
		}

		else if (action.equals("update")) {
			List<ProductVO> productList = productService.MSelectProducts(company_name, product_id);
			model.addAttribute("productlist", productList);
			return "ManagerProductUpdateForm";

		} else if (action.equals("delete")) {
			// 직접 company_name와 product_id 리스트를 서비스에 넘김
			int deletedCount = productService.MDeleteProduct(company_name, product_id);
			redirectAttributes.addFlashAttribute("ok", deletedCount + "건 삭제완료");
			return "redirect:/MProductList.do";
		}

		return "redirect:/MProductList.do";
	}

	// 상품 수정(선택한 것 여러개)(관리자)
	@RequestMapping(value = "MProductUpdate.do", method = RequestMethod.POST)
	public String mupdate(@RequestParam("product_id") List<String> product_id,
			@RequestParam("product_name") List<String> product_name,
			@RequestParam("product_category") List<String> product_category,
			@RequestParam("product_price") List<Integer> product_price,
			@RequestParam("product_mood") List<String> product_mood,
			@RequestParam("product_image") List<MultipartFile> product_image,
			@RequestParam(value = "original_image", required = false) List<String> original_image,
			@RequestParam("company_name") String company_name, HttpSession session, HttpServletRequest request)
			throws IllegalStateException, IOException {

		List<ProductVO> productList = new ArrayList<>();
		for (int i = 0; i < product_id.size(); i++) {
			ProductVO product = new ProductVO();
			product.setProduct_id(product_id.get(i));
			product.setProduct_name(product_name.get(i));
			product.setProduct_category(product_category.get(i));
			product.setProduct_price(product_price.get(i));
			product.setProduct_mood(product_mood.get(i));
			product.setCompany_name(company_name);

			String uploadPath = "";
			String mood = product_mood.get(i);
			String category = product_category.get(i);
			String name = product_name.get(i);
			if (category.equals("Goods")) {
				uploadPath = request.getSession().getServletContext().getRealPath("goods/캐릭터 상품 이미지_키링/" + mood + "/");
			} else if (category.equals("Flower")) {
				uploadPath = request.getSession().getServletContext().getRealPath("product/" + mood + "/");
			}

			MultipartFile imgFile = product_image.get(i);

			if (imgFile != null && !imgFile.isEmpty()) {
				String originalFilename = imgFile.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
				String fileName = "상품이미지_" + name + extension;
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				// 파일 저장
				File saveFile = new File(uploadPath, fileName);
				imgFile.transferTo(saveFile);

				// DB에 저장할 경로 (웹 경로 기준)
				if (category.equals("Goods")) {
					product.setProduct_image("goods/캐릭터 상품 이미지_키링/" + mood + "/" + fileName);
				} else if (category.equals("Flower")) {
					product.setProduct_image("product/" + mood + "/" + fileName);
				}

			} else if (original_image != null && i < original_image.size() && original_image.get(i) != null
					&& !original_image.get(i).isEmpty()) {
				// 이미지 선택 안 했으면 기존 이미지 유지 혹은 빈값 처리
				product.setProduct_image(original_image.get(i));
				// 또는 기존 DB 이미지 경로를 불러와서 세팅하는 로직 필요할 수 있음
			} else {
				product.setProduct_image(null);
			}
			productList.add(product);
		}

		productService.MUpdateProduct(productList);

		return "redirect:/MProductList.do";
	}

	@RequestMapping("return.do")
	public String mm() {
		return "admin_page";
	}

	// 상품 등록 폼으로 가기(관리자)
	@RequestMapping("MProductInsert.do")
	public String minsertf(HttpSession session) {
		
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");

		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}
		
		return "ManagerProductInsertForm";
	}

	// 상품 등록
	@RequestMapping(value = "MProductInsertAll.do", method = RequestMethod.POST)
	public String minsert(@RequestParam(value = "product_name", required = false) String product_name2,
			@RequestParam(value = "product_category", required = false) String product_category2,
			@RequestParam(value = "product_price", required = false) Integer product_price2,
			@RequestParam(value = "product_mood", required = false) String product_mood2,
			@RequestParam(value = "product_image", required = false) MultipartFile product_images, HttpSession session,
			RedirectAttributes redirectAttributes, Model model) throws IOException {

		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		if (manager == null) {
			return "redirect:MyLogin.do";
		}
		String managerId = manager.getManager_id();
		String company_name = mainService.get_companyid_by_managerid(managerId);
		ProductVO insertvo = new ProductVO();
		insertvo.setCompany_name(company_name);
		insertvo.setProduct_name(product_name2);
		insertvo.setProduct_category(product_category2);
		insertvo.setProduct_price(product_price2);
		insertvo.setProduct_mood(product_mood2);
		String uploadDir = null;
		if (product_category2.equals("Goods")) {
			uploadDir = session.getServletContext().getRealPath("goods/캐릭터 상품 이미지_키링/" + product_mood2 + "/");
		} else if (product_category2.equals("Flower")) {
			uploadDir = session.getServletContext().getRealPath("product/" + product_mood2 + "/");
		}
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			System.out.println("디렉토리 생성됨?: " + created);
		} else {
			System.out.println("디렉토리 이미 존재함");
		}

		String imagePaths = null;

		if (product_images != null && !product_images.isEmpty()) {
			String originalFileName = product_images.getOriginalFilename();
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String fileName = "상품이미지_" + product_name2 + extension;
			File saveFile = new File(uploadDir, fileName);
			product_images.transferTo(saveFile);
			if (product_category2.equals("Goods")) {
				imagePaths = "goods/캐릭터 상품 이미지_키링/" + product_mood2 + "/" + fileName;
			} else if (product_category2.equals("Flower")) {
				imagePaths = "product/" + product_mood2 + "/" + fileName;
			}
			insertvo.setProduct_image(imagePaths);
		}

		else {
			insertvo.setProduct_image(null);
		}

		// 이미지 경로저장
		System.out.println("이미지 경로 DB에 저장할 값: " + insertvo.getProduct_image());

		productService.MInsertProduct(insertvo);

		redirectAttributes.addFlashAttribute("ok", "등록이 완료되었습니다.");
		return "redirect:/MProductList.do";
	}

	// 상품 등록후 이미지만 업로드 (안써요)
	@RequestMapping(value = "MProductInsertImage", method = RequestMethod.POST)
	public String up1(@RequestParam("product_id") String product_id, @RequestParam("company_name") String company_name,
			@RequestParam("product_image") MultipartFile product_image2, Model model) {
		ProductVO imagevo = new ProductVO();
		imagevo.setCompany_name(company_name);
		imagevo.setProduct_id(product_id);
		MultipartFile file = product_image2;
		if (file != null && !file.isEmpty()) {
			// 실제 저장은 생략, 파일 이름만 세팅
			String fileName = file.getOriginalFilename();
			imagevo.setProduct_image(fileName);
		} else {
			imagevo.setProduct_image(null);
		}
		productService.MUpdateImage(imagevo);
		return "redirect:/MProductList.do";

	}

	// 상품옵션 추가 수정 삭제
	@RequestMapping(value = "MProductOptionIUD.do", method = RequestMethod.POST)
	public String iud(@RequestParam(value = "option_id", required = false) String option_id,
			@RequestParam("product_id") String product_id, @RequestParam("action") String action, Model model,
			RedirectAttributes redirectAttributes) {
		if (action.equals("insert")) {
			model.addAttribute("product_id", product_id);
			return "ManagerProductOptionInsertForm";
		} else if (action.equals("update")) {
			ProductOptionVO opt = productService.MSelectProductDOne(option_id, product_id);
			System.out.println(opt);
			model.addAttribute("opt", opt);
			return "ManagerProductOptionUpdateForm";
		} else if (action.equals("delete")) {
			ProductOptionVO opt = productService.MSelectProductDOne(option_id, product_id);
			int dd = productService.MDeleteProductD(opt);
			redirectAttributes.addFlashAttribute("ok", dd + "건 삭제가 되었습니다.");
			return "redirect:/MProductDetail.do";

			/* productService.MdeleteProductD(deletevo); */
		}
		return "redirect:/MProductDetail.do";

	}

	// 상품 옵션 추가
	@RequestMapping(value = "MProductOptionInsertAll.do", method = RequestMethod.POST)
	public String in(@RequestParam(value = "product_id") String product_id,
			@RequestParam(value = "option_color") String option_color,
			@RequestParam(value = "option_size") String option_size,
			@RequestParam(value = "option_stock") int option_stock,
			@RequestParam(value = "option_picture", required = false) MultipartFile option_picture, Model model,
			HttpSession session, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		ProductOptionVO insertvo = new ProductOptionVO();
		ProductVO product = productService.SelectedProduct(product_id);
		String mood = product.getProduct_mood();
		String category = product.getProduct_category();
		String name = product.getProduct_name();
		insertvo.setProduct_id(product_id);
		insertvo.setOption_color(option_color);
		insertvo.setOption_size(option_size);
		insertvo.setOption_stock(option_stock);
		System.out.println(option_picture);
		String uploadDir = null;
		if (category.equals("Goods")) {
			uploadDir = session.getServletContext().getRealPath("goods/goods_option/" + mood + "/");
		} else if (category.equals("Flower")) {
			uploadDir = session.getServletContext().getRealPath("product/product_option/" + mood + "/");
		}
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			System.out.println("디렉토리 생성됨?: " + created);
		} else {
			System.out.println("디렉토리 이미 존재함");
		}
		System.out.println("실제 저장 경로: " + uploadDir);

		String imagePaths = null;

		if (option_picture != null && !option_picture.isEmpty()) {
			String originalFileName = option_picture.getOriginalFilename();
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String fileName = "상품이미지_" + name + "_" + option_color + extension;
			File saveFile = new File(uploadDir, fileName);
			option_picture.transferTo(saveFile);
			if (category.equals("Goods")) {
				imagePaths = "goods/goods_option/" + mood + "/" + fileName;
			} else if (category.equals("Flower")) {
				imagePaths = "product/product_option/" + mood + "/" + fileName;
			}
			insertvo.setOption_picture(imagePaths);
		} else {
			insertvo.setOption_picture(null);
		}
		System.out.println(insertvo.getOption_picture());

		// 이미지 경로저장
		productService.MInsertProductD(insertvo);
		redirectAttributes.addFlashAttribute("ok", "등록이 완료되었습니다.");
		return "redirect:/MProductDetail.do";

	}

	// 상품 옵션 수정
	@RequestMapping(value = "MProductOptionUpdate.do", method = RequestMethod.POST)
	public String mageneric(@RequestParam(value = "product_id") String product_id,
			@RequestParam(value = "option_id") String option_id,
			@RequestParam(value = "option_color") String option_color,
			@RequestParam(value = "option_size") String option_size,
			@RequestParam(value = "option_stock") int option_stock,
			@RequestParam(value = "option_picture", required = false) MultipartFile option_picture,
			@RequestParam(value = "original_image", required = false) String original_image, HttpSession session)
			throws IllegalStateException, IOException {
		ProductOptionVO updatevo = new ProductOptionVO();
		ProductVO product = productService.SelectedProduct(product_id);
		String mood = product.getProduct_mood();
		String category = product.getProduct_category();
		String name = product.getProduct_name();
		updatevo.setOption_color(option_color);
		updatevo.setOption_id(option_id);
		updatevo.setOption_size(option_size);
		updatevo.setOption_stock(option_stock);
		updatevo.setProduct_id(product_id);
		String uploadDir = null;
		if (category.equals("Goods")) {
			uploadDir = session.getServletContext().getRealPath("goods/goods_option/" + mood + "/");
		} else if (category.equals("Flower")) {
			uploadDir = session.getServletContext().getRealPath("product/product_option/" + mood + "/");
		}
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			System.out.println("디렉토리 생성됨?: " + created);
		} else {
			System.out.println("디렉토리 이미 존재함");
		}

		String imagePaths = null;

		if (option_picture != null && !option_picture.isEmpty()) {
			String originalFileName = option_picture.getOriginalFilename();
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String fileName = "상품이미지_" + name + "_" + option_color + extension;
			File saveFile = new File(uploadDir, fileName);
			option_picture.transferTo(saveFile);

			if (category.equals("Goods")) {
				imagePaths = "goods/goods_option/" + mood + "/" + fileName;
			} else if (category.equals("Flower")) {
				imagePaths = "product/product_option/" + mood + "/" + fileName;
			}

			updatevo.setOption_picture(imagePaths);
		} else if (original_image != null && !original_image.isEmpty()) {
			// 이미지 선택 안 했으면 기존 이미지 유지
			updatevo.setOption_picture(original_image);

		}

		else {
			// 이미지 선택 안 했으면 기존 이미지 유지 혹은 빈값 처리
			updatevo.setOption_picture(null);
			// 또는 기존 DB 이미지 경로를 불러와서 세팅하는 로직 필요할 수 있음
		}
		productService.MUpdateProductD(updatevo);
		return "redirect:/MProductDetail.do";

	}

}