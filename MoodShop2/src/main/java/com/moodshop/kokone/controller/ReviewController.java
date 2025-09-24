package com.moodshop.kokone.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.moodshop.kokone.service.ReviewService;
import com.moodshop.kokone.vo.ReviewVO;
import com.moodshop.kokone.vo.Review_SubVO;
import com.moodshop.kokone.vo.UserVO;

@Controller
public class ReviewController {

	@Resource(name = "ReviewService")
	private ReviewService reviewService;
	
	//상품페이지 리뷰
	@RequestMapping("ProductReviewAjax.do")
	public String loadProductReview(@RequestParam("product_id") String product_id, Model model) {
	    List<ReviewVO> reviews = reviewService.getProductReviews(product_id);
	    System.out.println("조회된 리뷰 수: " + reviews.size()); // 확인용 로그
	    model.addAttribute("reviews", reviews);
	    return "ProductReviewAjax";
	}

	// 리뷰 조회 기본값 오름차순
	@RequestMapping(value = "MyReviewList.do")
	public String Myreview_Main(@RequestParam(value = "opt2", required = false, defaultValue = "asc") String opt2,
			Model model, HttpSession session) {

		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do"; // 비로그인 상태면 로그인 페이지로
		}
		String user_id = user.getUser_id();
		System.out.println(user_id);
		model.addAttribute("loginUserId", user_id);
		ArrayList<ReviewVO> reviewlist;
		switch (opt2) {
		case "asc":
			reviewlist = reviewService.getAllReviewAsc(user_id);
			model.addAttribute("reviews", reviewlist);
			break;
		case "desc":
			reviewlist = reviewService.getAllReviewDesc(user_id);
			model.addAttribute("reviews", reviewlist);
			break;

		case "mine":
			reviewlist = reviewService.getAllMyReview(user_id);
			model.addAttribute("reviews", reviewlist);
			break;
		default:
			return "error";
		}
		return "MyReviewList";
	}

	@RequestMapping(value = "MyReviewInsert.do", method = RequestMethod.POST)
	public String MyReviewInsert(@RequestParam("order_id") String order_id,
			@RequestParam("review_content") String review_content, @RequestParam("rating") int rating, // ⭐ 별점 추가
			@RequestParam(value = "review_image", required = false) MultipartFile[] file, HttpServletRequest request,
			HttpSession session
	) throws IllegalStateException, IOException {

		// 세션에서 로그인 사용자 정보 확인
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do"; // 로그인 안되어 있으면 로그인 페이지로
		}
		String user_id = user.getUser_id();

		// ✅ 파일 저장 경로 얻기
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/reviewupload");
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// ✅ 실제 저장될 파일명 변수
		String savedFileName = null;

		// ✅ 파일이 존재할 경우, 첫 번째 파일만 저장
		if (file != null && file.length > 0 && !file[0].isEmpty()) {
			MultipartFile firstFile = file[0];
			String originalName = firstFile.getOriginalFilename();
			String uuid = UUID.randomUUID().toString().substring(0, 8);
			savedFileName = uuid + "_" + originalName;

			File destFile = new File(uploadDir, savedFileName);

			System.out.println("UPLOAD PATH: " + uploadPath);
			System.out.println("ORIGINAL NAME: " + originalName);
			System.out.println("SAVED NAME: " + savedFileName);
			System.out.println("FULL PATH: " + destFile.getAbsolutePath());

			firstFile.transferTo(destFile);
		}

		// ✅ ReviewVO 생성 및 값 주입
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setOrder_id(order_id);
		reviewVO.setUser_id(user_id);
		reviewVO.setReview_content(review_content);
		reviewVO.setRating(rating); // ⭐ 별점 저장
		reviewVO.setReview_image(savedFileName); // 첫 번째 파일만 저장

		// ✅ DB 저장
		reviewService.insertReview(reviewVO);

		return "redirect:MyPage.do"; // 리뷰 작성 후 이동할 페이지
	}

	// 리뷰 수정폼으로 이동
	@RequestMapping(value = "MyReviewUpdateForm.do")
	public String ru(@RequestParam("review_id") String review_id, Model model, HttpSession session) {
		// 세션에서 로그인 사용자 정보 확인
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do"; // 로그인 안되어 있으면 로그인 페이지로
		}
		String user_id = user.getUser_id();

		if (user_id.equals(user_id)) {
			ReviewVO updatevo = reviewService.getReviewByID(review_id);

			model.addAttribute("update", updatevo);
		}
		return "MyReviewUpdateForm";
	}

	@RequestMapping(value = "MyReviewUpdate.do", method = RequestMethod.POST)
	public String rv(@ModelAttribute ReviewVO updatevo, HttpSession session) {

		System.out.println("리뷰 ID: " + updatevo.getReview_id());
		System.out.println("리뷰 내용: " + updatevo.getReview_content());
		System.out.println("유저 ID: " + updatevo.getUser_id());

		MultipartFile[] files = updatevo.getUpload_image();

		String uploadPath = session.getServletContext().getRealPath("/resources/upload/review/");
		File folder = new File(uploadPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		if (files != null && files.length > 0 && !files[0].isEmpty()) {
			String fileName = files[0].getOriginalFilename();
			File saveFile = new File(uploadPath, fileName);

	        try {
				files[0].transferTo(saveFile);
				updatevo.setReview_image(fileName); // 🔥 바로 DB에 저장할 필드에 세팅
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("파일 업로드 실패");
	        }
	    } else {
			updatevo.setReview_image(null); // 🔥 null이면 파일 update 제외 처리
	    }

	    reviewService.updateReview(updatevo);

	    return "redirect:/MyReviewList.do";
	}




	// 리뷰삭제
	@RequestMapping(value = "MyReviewDelete.do")
	public String rd(ReviewVO deletevo, Model model) {
		System.out.println("삭제 요청 review_id: " + deletevo.getReview_id());
		System.out.println("삭제 요청 user_id: " + deletevo.getUser_id());
		reviewService.deleteReview(deletevo);
		System.out.println(deletevo + "삭제완료");
		return "redirect:/MyPage.do";

	}

	// 해당하는 리뷰의 댓글이 나오는 것 (날짜기준으로 무조건 내림차순으로 지정) (아직 나오게 하는 기능은 안나옴)
	@RequestMapping(value = "MyReviewSubAll.do")
	public String MyReviewSubAll(@RequestParam("review_id") String review_id, Model model) {
		ReviewVO review = reviewService.getReviewByID(review_id);
		ArrayList<Review_SubVO> sublist = reviewService.getAllReviewSub(review_id);
		if (sublist != null && !sublist.isEmpty()) {
			model.addAttribute("review", review);
			model.addAttribute("sublist", sublist);
			System.out.print(sublist.size());
			System.out.println(sublist);
		} else
			model.addAttribute("review", review);
		System.out.printf("실패", sublist);
		return "MyReviewSubList";

	}

	// 해당하는 리뷰 댓글 입력 창으로 가는것
	@RequestMapping(value = "MyReviewSubInsertForm.do")
	public String MyReviewSubInsertForm() {
		return "MyReviewSubInsertForm";
	}

	// 리뷰 댓글보기 창에서 리뷰목록 창으로 이동
	@RequestMapping(value = "MytoReviewList.do")
	public String MytoReviewList() {
		return "redirect:/MyReviewList.do";
	}

	// 해당하는 리뷰의 댓글을 작성해서 등록하는것
	@RequestMapping(value = "MyReviewSubInsert.do")
	public String MyReviewSubInsert(Review_SubVO insertvo, Model model, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do"; // 로그인 안되어 있으면 로그인 페이지로
		}
		String user_id = user.getUser_id();
		insertvo.setUser_id(user_id); // 임시로 로그인아이디를 넣음
		reviewService.getReviewSubInsert(insertvo);
		return "redirect:/MyReviewList.do";
	}

	// 사용자가 작성한 댓글 수정 폼 요청
	@RequestMapping(value = "MyReviewSubUpdateForm.do", method = RequestMethod.GET)
	public String showUpdateForm(@RequestParam("sub_id") String sub_id, Model model, HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do"; // 로그인 안되어 있으면 로그인 페이지로
		}
		String user_id = user.getUser_id();
		Review_SubVO subone = reviewService.getReviewSubOne(sub_id);

		// 본인 댓글인지 확인
		if (!user_id.equals(subone.getUser_id())) {
			System.out.println("회원이 다릅니다.");
			return "redirect:/MyReviewSubAll.do";
		}

		// 본인 댓글이면 수정 폼으로 이동
		model.addAttribute("subone", subone);
		return "MyReviewSubUpdateForm"; // 수정 폼 JSP 경로
	}

	// 댓글 수정
	@RequestMapping(value = "MyReviewSubUpdate.do")
	public String ReviewSubUpdate(Review_SubVO updatevo, Model model) {
		reviewService.getReviewSubUpdate(updatevo);
		return "redirect:/MyReviewList.do";
	}

	// 댓글 삭제
	@RequestMapping(value = "MyReviewSubDelete.do")
	public String ReviewSubDelete(Review_SubVO deletevo, Model model, HttpSession session) {
		// String user_id=(String) session.getAttribute("user_id"); 로그인 하면 주석 해제할거임
		String user_id = (String) session.getAttribute("user_id");
		reviewService.getReviewSubDelete(deletevo);
		return "redirect:/MyReviewList.do";

	}

	@RequestMapping("product_review.do")
	public String arrrwr(@RequestParam("product_id") String product_id,Model model) {
		List<ReviewVO> reviewlist = reviewService.productToReview(product_id);
		model.addAttribute("reviewlist", reviewlist);
		model.addAttribute("product", product_id);
		return "ProductReview";
		
	}
	


}
