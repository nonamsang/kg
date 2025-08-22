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
    	
    		//로그인 확인
    	/*
    		UserVO loginUser = (UserVO) session.getAttribute("UserVO");
    		if (loginUser ==null) {	//로그인X
    			return "redirect:/user/userLogin";	//로그인화면으로 
    		}
    		*/
    		int testUserId =1; //db 테스트용 유저 번호 
    		//로그인한 사용자의 user_seq 기준으로 사용자 정보 조회
    		// int userSeq = loginUser.getUser_seq();
    		
    		//회원정보 조회  DB에서 최신 사용자 정보 조회
    		UserVO userInfo = userService.getInfo(testUserId);
    			/*
			 * if (userInfo == null) { // 사용자 정보를 찾을 수 없는 경우 메인페이지로 return
			 * "redirect:/mainpage"; }
			 */
    	
    	    // (프로필 사진) 가공된 경로 직접 설정
    		String profilePath = (userInfo.getProfile_Photo() == null || userInfo.getProfile_Photo().isBlank()) 
                    ? "profile.png" 
                    : userInfo.getProfile_Photo();

    		model.addAttribute("profilePhotoPath", profilePath); // 별도로 전달
    		//Thymeleaf로 전달
    		model.addAttribute("userInfo",userInfo); 

    		
    		return "mypage";
    } 
}

