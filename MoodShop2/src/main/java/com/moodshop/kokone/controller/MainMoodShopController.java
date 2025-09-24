package com.moodshop.kokone.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moodshop.kokone.service.BasketService;
import com.moodshop.kokone.service.EventService;
import com.moodshop.kokone.service.MainMoodShopService;
import com.moodshop.kokone.service.OrderService;
import com.moodshop.kokone.service.RecentViewService;
import com.moodshop.kokone.service.ReviewService;
import com.moodshop.kokone.service.UserService;
import com.moodshop.kokone.service.WishListService;
import com.moodshop.kokone.vo.BasketDetailVO;
import com.moodshop.kokone.vo.BasketJoinVO;
import com.moodshop.kokone.vo.BasketOptionVO;
import com.moodshop.kokone.vo.EventVO;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.ProductOptionVO;
import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.RecentViewVO;
import com.moodshop.kokone.vo.UserVO;

@Controller
public class MainMoodShopController {

	@Autowired
	private ServletContext context;

	@Autowired
	private BasketService basketService;

	@Autowired
	private MainMoodShopService mainService;

	@Autowired
	private EventService eventService;

	@Autowired
	private RecentViewService recentViewService;

	@Resource(name = "wishlistService")
	private WishListService wishlistService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "ReviewService")
	private ReviewService reviewService;
	
	//김동주가 추가한 것
	@Resource(name = "OrderService")
	private OrderService orderService;

	@RequestMapping("MainMoodShop.do")
	public String MainMoodShop(HttpSession session, Model model) {
		List<EventVO> bannerList = eventService.getMainEventDetails();
		model.addAttribute("bannerList", bannerList);

		List<ProductVO> productList = mainService.MainGetProducts();
		model.addAttribute("productList", productList);

		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user != null) {
			String userId = user.getUser_id();

			// int basketCount = mainService.getBasketCount(userId);
			// int wishCount = mainService.getWishCount(userId);

			List<RecentViewVO> recentList = recentViewService.getRecentViews(userId);
			model.addAttribute("recentList", recentList);
			// model.addAttribute("basketCount", basketCount);
			// model.addAttribute("wishCount", wishCount);
		}

		return "MainMoodShop";
	}

	@RequestMapping("eventMainDetail.do")
	public String eventMainDetail(Model model) {
		List<EventVO> event_detail_list = eventService.getAllEventDetail();
		model.addAttribute("event_detail_list", event_detail_list);
		return "redirect:/eventClick.do?status=ongoing";
	}

	// 최근 본 상품 저장
	@RequestMapping("main/addRecentView.do")
	public void addRecentView(HttpSession session, @RequestParam("productId") String productId,
			HttpServletResponse response) throws IOException {
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user != null) {
			String userId = user.getUser_id();
			recentViewService.addRecentView(userId, productId);
		}
		response.getWriter().write("success");
	}

	// 사이드바 AJAX HTML 반환
	@RequestMapping("main/recentViewSidebar.do")
	public String recentViewSidebar(HttpSession session, Model model) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user != null) {
			String userId = user.getUser_id();
			List<RecentViewVO> recentList = recentViewService.getRecentViews(userId);
			model.addAttribute("recentList", recentList);
		}
		return "common/sidebarAjax"; // sidebarAjax.jsp
	}

	@RequestMapping(value = "/HeaderProduct.do", method = RequestMethod.GET)
	public String HeaderGetProduct(HttpServletRequest request, Model model) {
		String product_category = request.getParameter("product_category");
		String product_mood = request.getParameter("product_mood");

		if (product_category != null) {
			if (product_mood != null) {
				ProductVO vo = mainService.CategoryMood(product_category, product_mood);
				model.addAttribute("product", vo);

			} else {
				ProductVO vo = mainService.Category(product_category);
				model.addAttribute("product", vo);
			}
		}

		return "Product_Detail";

	}

	@RequestMapping(value = "/HeaderProductCategoryGo.do", method = RequestMethod.GET)
	public String HeaderProductCategoryGo(HttpSession session, 
								@RequestParam("product_category") String product_category, Model model) {

		List<ProductVO> list = mainService.HeaderProductCategory(product_category);
		model.addAttribute("productList", list);

		model.addAttribute("selectedCategory", product_category); // 배경색 등에 활용 가능
		
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user != null) {
			String userId = user.getUser_id();
			// 사이드바용 코드
			// int basketCount = mainService.getBasketCount(userId);
			// int wishCount = mainService.getWishCount(userId);

			List<RecentViewVO> recentList = recentViewService.getRecentViews(userId);
			model.addAttribute("recentList", recentList);
			// model.addAttribute("basketCount", basketCount);
			// model.addAttribute("wishCount", wishCount);
		}
			
		return "MainProductCategory";
	}

	@RequestMapping(value = "/HeaderProductMood.do", method = RequestMethod.GET)
	public String HeaderProductMoodGo(HttpSession session, 
								@RequestParam("product_category") String product_category,
			@RequestParam("product_mood") String product_mood, Model model) {

		List<ProductVO> list = mainService.HeaderProductMood(product_category, product_mood);

		model.addAttribute("productList", list);
		model.addAttribute("emotion", product_mood);
		model.addAttribute("category", product_category);
		
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user != null) {
			String userId = user.getUser_id();

			// int basketCount = mainService.getBasketCount(userId);
			// int wishCount = mainService.getWishCount(userId);

			List<RecentViewVO> recentList = recentViewService.getRecentViews(userId);
			model.addAttribute("recentList", recentList);
			// model.addAttribute("basketCount", basketCount);
			// model.addAttribute("wishCount", wishCount);
		}
			
		return "MainProductMood"; // => MainProductMood.jsp
	}

	@RequestMapping("HeaderMainBrand.do")
	public String HeaderMainBrand() {
		return "Brand";
	}

	/*
	 * @RequestMapping("HeaderMainNotice.do") public String HeaderMainNotice() {
	 * return "Notice"; }
	 */

	@RequestMapping("MainProductName.do")
	public String MainProductName(HttpSession session, @RequestParam("product_id") String product_id, Model model) {
		List<ProductVO> reviewlist = mainService.RatingSelect(product_id);
		List<ProductVO> productlist = mainService.getProductById(product_id);

		// ✔️ 찜 개수 조회
		int wishCount = wishlistService.getWishlistCountByProduct(product_id);

		// ✔️ 찜 여부도 조회 (선택)
		UserVO user = (UserVO) session.getAttribute("userVO");
		boolean isWished = false;
		if (user != null) {
			String user_id = user.getUser_id();
			isWished = wishlistService.checkIfWished(user_id, product_id);
		}
		/*
		 * Stirng order_id = list.getOrder_id; reviewService
		 */

		model.addAttribute("productList", productlist);
		model.addAttribute("reviewList", reviewlist);
		ArrayList<ProductOptionVO> list2 = new ArrayList<>(mainService.getProductOptionById(product_id));
		model.addAttribute("optionList", list2);
		model.addAttribute("wishCount", wishCount); // ✔️ 찜 수 JSP로 전달
		model.addAttribute("isWished", isWished); // ✔️ 찜 여부 JSP로 전달

		return "Product_Detail";
	}

	@RequestMapping("MainOffline.do")
	public String HeaderMainOffline() { // 오프라인 매장 지도 열기
		return "OfflineMap";
	}

	@RequestMapping("Search.do")
	public String search(HttpSession session, @RequestParam("search") String search, Model model) {
	    List<ProductVO> list = mainService.getProductBySearched(search);
	    model.addAttribute("productList", list);
	    model.addAttribute("keyword", search); // (검색어 넘기기)
	    
	    UserVO user = (UserVO) session.getAttribute("userVO");
		if (user != null) {
			String userId = user.getUser_id();
	    
			// 사이드바용 코드
			// int basketCount = mainService.getBasketCount(userId);
			// int wishCount = mainService.getWishCount(userId);

			List<RecentViewVO> recentList = recentViewService.getRecentViews(userId);
			model.addAttribute("recentList", recentList);
			// model.addAttribute("basketCount", basketCount);
			// model.addAttribute("wishCount", wishCount);
		}
	 	
	    return "MainProductSearched";
	}


	// 여기서부터 장바구니 컨트롤러

	@RequestMapping("addCartList.do")
	public String addCartList(@RequestBody List<BasketJoinVO> cartList, HttpSession session) {
	    UserVO user = (UserVO) session.getAttribute("userVO");
	    if (user == null) {
	        throw new RuntimeException("로그인이 필요합니다.");
	    }
	    String user_id = user.getUser_id();

	    basketService.addCartList(user_id, cartList);

	    return "forward:/MainMoodShop.do";
	}
	
	@RequestMapping("getProductOptions.do")
	@ResponseBody
	public List<BasketOptionVO> getProductOptions(@RequestParam("product_id") String product_id) {
		System.out.println(product_id);
		System.out.println("옵션컨틀롤러작동중");
	    return basketService.getProductOptions2(product_id);
	}
	
	
	@RequestMapping("addCart.do")
	public String addCart(@RequestParam("productId") String productId, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("userVO");

		if (user == null) {
			// RuntimeException은 브라우저에서 error 콜백으로 전달됨
			throw new RuntimeException("로그인이 필요합니다.");
		}

		String userId = user.getUser_id();
		basketService.addCart(userId, productId);

		return "forward:/MainMoodShop.do";
	}

	@RequestMapping("removeCart.do")
	public String removeCart(@RequestParam("productId") String productId, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			throw new RuntimeException("로그인이 필요합니다.");
		}

		String userId = user.getUser_id();

		basketService.removeCart(userId, productId);

		return "forward:/MainMoodShop.do";
	}

	@RequestMapping("addToCart.do")
	public String addToCart(HttpSession session, @RequestParam("product_id") String product_id,
			@RequestParam(value = "color", required = false) String option_color,
			@RequestParam(value = "option_size", required = false) String option_size,
			@RequestParam("quantityInput") int basket_detail_count, Model model) {

		// 옵션 값이 공백일 경우 null 처리
		if (option_color != null && option_color.trim().isEmpty()) {
			option_color = null;
		}

		// 요청값 확인 로그
		System.out.println("상품 ID: " + product_id);
		System.out.println("수량: " + basket_detail_count);
		System.out.println("옵션 색상: " + option_color);
		System.out.println("옵션 사이즈: " + option_size);

		// 로그인 유저 확인
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do"; // 비로그인 상태면 로그인 페이지로
		}
		String user_id = user.getUser_id();
		System.out.println("유저 ID: " + user_id);

		// 장바구니 ID 조회
		String basketId = basketService.findBasketIdByUserId(user_id);

		// 1. 제품 정보 가져오기
		List<ProductVO> productList = mainService.getProductById(product_id);
		if (productList == null || productList.isEmpty()) {
			model.addAttribute("error", "해당 상품 정보를 찾을 수 없습니다.");
			return "redirect:/MainMoodShop.do";
		}
		ProductVO product = productList.get(0);
		int unitPrice = product.getProduct_price();

		// 2. 옵션 ID 가져오기
		ProductOptionVO option = null;
		if (option_color != null && option_size != null) {
			option = mainService.getProductionOptionByIdAllOption(product_id, option_color, option_size);
		} else if (option_color != null) {
			option = mainService.getProductOptionByIdOption(product_id, option_color);
		} else if (option_size != null) {
			option = mainService.getProductOptionByIdOptionSize(product_id, option_size);
		} else {
			option = mainService.getProductOptionByIdBasket(product_id, option_color);
		}

		if (option == null) {
			model.addAttribute("error", "선택한 옵션 정보를 찾을 수 없습니다.");
			return "redirect:/MainProductName.do?product_id=" + product_id;
		}

		String option_id = option.getOption_id();

		// 3. 장바구니가 없으면 생성
		if (basketId == null) {
			basketService.createBasket(user_id);
			basketId = basketService.findBasketIdByUserId(user_id);
		}

		// 4. 장바구니 상세 정보 생성
		BasketDetailVO detail = new BasketDetailVO();
		detail.setBasket_id(basketId);
		detail.setBasket_detail_id(UUID.randomUUID().toString()); // UUID 사용 중
		detail.setProduct_id(product_id);
		detail.setOption_id(option_id);
		detail.setBasket_detail_count(basket_detail_count);
		detail.setBasket_detail_price(unitPrice * basket_detail_count);

		// 5. 장바구니 상세 insert
		basketService.insertBasketDetail(detail);

		// 6. BASKET 테이블의 TOTAL_PRICE 갱신
		basketService.updateBasketTotalPrice(basketId); // ✅ 변수명 수정 (오류 원인)

		// 7. 사용자에게 피드백
		model.addAttribute("message", "장바구니에 추가되었습니다.");
		return "redirect:/MainProductName.do?product_id=" + product_id;
	}

	@RequestMapping("MyCartList.do")
	public String cartList(HttpSession session, Model model) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do"; // 비로그인 상태면 로그인 페이지로
		}
		String user_id = user.getUser_id();
		System.out.println("회원이름" + user_id);
		List<BasketJoinVO> cartList = basketService.getBasketListByUserId(user_id);
		System.out.println("장바구니 유무" + cartList);
		model.addAttribute("cartList", cartList);
		return "MyPageBasket"; // views/mypage/cartList.jsp
	}

	@RequestMapping(value = "basketAction.do")
	public String handleBasketAction(@RequestParam("action") String action,
			@RequestParam(value = "selectedIds", required = false) List<String> selectedIds,
			HttpServletRequest request) {

		System.out.println(selectedIds);
		if (selectedIds == null || selectedIds.isEmpty()) {
			return "redirect:/MyPage.do";
		}

		switch (action) {
		case "delete":
			basketService.deleteItems(selectedIds); // List<String>
			break;
		case "update":
			for (String id : selectedIds) {
				String param = request.getParameter("quantity_" + id);
				System.out.println(param);
				int quantity = Integer.parseInt(param);
				basketService.updateQuantity(id, quantity);
			}
			break;

		case "order":
			basketService.processPayment(selectedIds);
			break;
		}

		return "redirect:/MyPage.do";
	}

	// ✅ 수량 수정 컨트롤러
	@ResponseBody
	@RequestMapping("updateQuantity.do")
	public Map<String, Object> updateQuantity(@RequestBody List<BasketDetailVO> updateList) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> updatedItems = new ArrayList<>();

		try {
			for (BasketDetailVO item : updateList) {
				System.out.println("수정 요청 ID: " + item.getBasket_detail_id());
				System.out.println("수정 요청 수량: " + item.getBasket_detail_count());

				// 수량 수정
				basketService.updateQuantity(item.getBasket_detail_id(), item.getBasket_detail_count());

				// 총 금액 재계산
				int updatedPrice = basketService.getItemTotalPrice(item.getBasket_detail_id());
				String formattedPrice = NumberFormat.getCurrencyInstance(Locale.KOREA).format(updatedPrice);

				Map<String, Object> updatedItem = new HashMap<>();
				updatedItem.put("id", item.getBasket_detail_id()); // 프론트에서 업데이트할 ID
				updatedItem.put("totalPriceFormatted", formattedPrice);

				updatedItems.add(updatedItem);
			}

			result.put("success", true);
			result.put("updatedItems", updatedItems);
		} catch (Exception e) {
			e.printStackTrace(); // 서버 오류 확인용
			result.put("success", false);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "deleteItems.do", method = RequestMethod.POST)
	public Map<String, Object> deleteItems(@RequestParam("selectedIds") List<String> selectedIds) {
		Map<String, Object> result = new HashMap<>();

		if (selectedIds == null || selectedIds.isEmpty()) {
			result.put("success", false);
			return result;
		}

		basketService.deleteItems(selectedIds);

		result.put("success", true);
		return result;
	}

	// 관리자 페이지
	@RequestMapping("AdminDetailPage.do")
	public String showAdminDetailPage(HttpSession session) {
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");
		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}
		return "admin_product_detail"; // 실제 뷰 이름: /WEB-INF/views/admin_detail.jsp
	}

	@RequestMapping(value = "/AdminProductUpdatePage.do", method = RequestMethod.GET)
	public String showAdminProductUpdatePage(@RequestParam("product_id") String product_id, Model model) {

		// DB에서 상품 정보 가져오기
		List<ProductVO> productList = mainService.getProductById(product_id);
		model.addAttribute("productlist", productList);

		return "admin_productupdate"; // admin_product_update.jsp로 이동
	}

	// 회원 목록 페이지(김동주 추가 2차 수정)
	@RequestMapping("UserListPage.do")
	public String showUserList(HttpSession session, Model model) {
		
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");

		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}
		
		ArrayList<UserVO> userList = userService.getAllUser();

		List<Map<String, Object>> parsedUserList = new ArrayList<>();

		for (UserVO user : userList) {
			Map<String, Object> userMap = new HashMap<>();
			userMap.put("user", user);

			String address = user.getAddress(); // 예: "03392%$서울 은평구 서오릉로 94%$서울 은평구 대조동 231%$[없음]%$ (대조동, 삼성타운아파트)"
			String[] parts = address != null ? address.split("%\\$") : new String[0];

			String postcode = parts.length > 0 ? parts[0] : "";
			String roadAddr = parts.length > 1 ? parts[1] : "";
			String addr = parts.length > 2 ? parts[2] : "";
			String subAddr = parts.length > 3 ? parts[3] : "";
			String extraAddr = parts.length > 4 ? parts[4] : "";

			// 도로명 전체 주소
			String roadFullAddress = "[" + postcode + "] " + roadAddr + " " + subAddr + " " + extraAddr;
			// 지번 전체 주소
			String normalFullAddress = "[" + postcode + "] " + addr + " " + subAddr + " " + extraAddr;

			userMap.put("roadFullAddress", roadFullAddress.trim());
			userMap.put("normalFullAddress", normalFullAddress.trim());

			parsedUserList.add(userMap);
		}
		model.addAttribute("userList", parsedUserList);
		return "list";
	}

}