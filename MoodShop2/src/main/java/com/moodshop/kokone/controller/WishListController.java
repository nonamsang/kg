package com.moodshop.kokone.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moodshop.kokone.service.LoginService;
import com.moodshop.kokone.service.ProductService;
import com.moodshop.kokone.service.WishListService;
import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.UserVO;
import com.moodshop.kokone.vo.WishListVO;

@Controller
public class WishListController {

	@Resource(name = "wishlistService")
	private WishListService wishlistService;

	@Autowired
	private ProductService productService;

	@Resource(name = "loginService")
	private LoginService loginService;

	@ResponseBody
	@RequestMapping("count.do")
	public Map<String, Object> getWishCount(@RequestParam("productId") String productId) {
		int count = wishlistService.getWishlistCountByProduct(productId);
		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("count", count);
		return result;
	}

	@ResponseBody
	@RequestMapping("toggle.do")
	public Map<String, Object> toggleWish(HttpSession session,
			@RequestParam("productId") String productId) {
		UserVO user = (UserVO) session.getAttribute("userVO");

		String userId = user.getUser_id();

		WishListVO vo = new WishListVO();
		vo.setUserId(userId);
		vo.setProductId(productId);

		boolean liked = wishlistService.toggleWishlist(vo);

        Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("liked", liked);
		return result;
	}


	@RequestMapping("MyWishList.do")
	public String myWishList(HttpSession session, Model model) {
		UserVO loginUser = (UserVO) session.getAttribute("userVO");

		String userId = loginUser.getUser_id();
		System.out.println(userId);
		List<ProductVO> wishlist = wishlistService.getWishlistByUser(userId);
		UserVO vo = loginService.getUserVO(userId);
		model.addAttribute("wishlist", wishlist);
		model.addAttribute("User", vo);

		return "WishHistory";
	}


	@RequestMapping("detail.do")
	public String productDetail(@RequestParam("product_id") String productId, HttpSession session, Model model) {
		ProductVO product = productService.SelectedProduct(productId);
		model.addAttribute("productList", List.of(product));

		// 찜 여부 판단
		String userId = ((UserVO) session.getAttribute("loginUser")).getUser_id();
		boolean isWished = wishlistService.checkIfWished(userId, productId);
		model.addAttribute("isWished", isWished);

		return "product_detail";
	}

	@ResponseBody
	@RequestMapping("delete.do")
	public Map<String, Object> deleteWishlist(@RequestParam String productId, HttpSession session) {

		Map<String, Object> result = new HashMap<>();

		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null || user.getUser_id() == null) {
			result.put("success", false);
			result.put("message", "로그인이 필요합니다.");
			return result;
		}

		String user_id = user.getUser_id();

		WishListVO vo = new WishListVO();
		vo.setUserId(user_id);
		vo.setProductId(productId);

		boolean deleted = wishlistService.toggleWishlist(vo); // ✔️ 토글 사용 그대로 OK

		result.put("success", true);
		result.put("deleted", deleted);
		return result;
	}


}