package com.fintecho.littleforest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	@Autowired
	UserService userService;

	/*
	 * @GetMapping("/") public String home() { return "mainpage"; //
	 * templates/index.html 로 연결됨 (확장자 제외) }
	 */

	/* @RequestMapping("/mypage") */
	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model) {

		UserVO loginUser = (UserVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login"; // 로그인 안 했으면 로그인 페이지로
		}

		int user_Id = loginUser.getId();
		UserVO userInfo = userService.getInfo(user_Id);

		// (프로필 사진) 가공된 경로 직접 설정
		String profilePath = (userInfo.getProfile_Photo() == null || userInfo.getProfile_Photo().isBlank())
				? "profile.png"
				: userInfo.getProfile_Photo();

///////////////////// 09.03 추가 세션에 저장 (사이드바가 여기서 꺼내 씀) //////////////////////
	    session.setAttribute("userInfo", userInfo);
	    session.setAttribute("profilePhotoPath", profilePath);
///////////////////// 09.03 추가 세션에 저장 (사이드바가 여기서 꺼내 씀) //////////////////////
	    
		model.addAttribute("profilePhotoPath", profilePath); // 별도로 전달
		// Thymeleaf로 전달
		model.addAttribute("userInfo", userInfo);

		return "mypage";
	}
}
