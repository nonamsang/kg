package com.fintecho.littleforest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintecho.littleforest.mapper.UserMapper;
import com.fintecho.littleforest.service.UserService;
import com.fintecho.littleforest.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	@Autowired
	UserService userService;
	@Autowired
	UserMapper userMapper;

	private final PasswordEncoder passwordEncoder;

	// 0홈 화면
	@GetMapping("/")
	public String home() {
		return "mainpage"; // templates/mainpage.html 렌더링
	}

	// 로그인 화면
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 회원가입 화면
	@GetMapping("/join")
	public String join() {
		return "join";
	}

	// 마이페이지

	/*
	 * @GetMapping("/mypage") public String mypage(HttpSession session) { if
	 * (session.getAttribute("loginuser") == null) { return "redirect:/login"; }
	 * return "mypage"; }
	 */

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// 탄소 배출량 페이지
	@GetMapping("/emission1")
	public String emission() {
		return "emission1";
	}

	// 로그인 !

	@PostMapping("/login")
	public String login(@RequestParam String oauth_id, @RequestParam String password, Model model,
			HttpSession session) {

		UserVO user = userService.findByOauthID(oauth_id);

		if (user != null && user.getPassword().equals(password)) {
			session.setAttribute("loginUser", user);
			return "redirect:/";
		} else {
			model.addAttribute("errorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "login";
		}
	}

	// 회원가입 처리
	@PostMapping("/join")
	public String joinSubmit(@RequestParam String name, @RequestParam String oauth_id, @RequestParam String birth, // String으로
																													// 받기
			@RequestParam String phone, @RequestParam String password, @RequestParam String address,
			@RequestParam String detail_address, @RequestParam String email) {
		UserVO user = new UserVO();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthDate = sdf.parse(birth);
			user.setBirth(birthDate);
		} catch (ParseException e) {
			e.printStackTrace();
			// 예외 처리 (예: 기본값 지정, 오류 메시지 전달 등)
		}

		user.setName(name);
		user.setOauth_Id(oauth_id);
		user.setNickname(oauth_id);
		user.setPhone(phone);
		// user.setPassword(password);
		user.setPassword(passwordEncoder.encode(password));
		user.setAddress(address + " " + detail_address);
		user.setEmail(email);

		userService.joinUser(user);
		return "redirect:/login";
	}

	@PostMapping("/checkemail")
	@ResponseBody
	public int checkemail(@RequestParam String email, Model model) {

		if (userMapper.checkEmailDupl(email) > 0) {
			return 1;
		} else {
			return 0;
		}
	}

}
