package com.moodshop.kokone.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moodshop.kokone.service.LoginService;
import com.moodshop.kokone.util.PasswordUtil;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.UserVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	@Resource(name = "loginService")
	private LoginService loginService;
	
	// -- 이 부분은 Mainpage.jsp로 접근하는 메서드입니다.

	@RequestMapping("MyLogin.do")
	public String showLoginPage(HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		
		if (user != null || manager != null) {
			return "redirect:Mainpage.do";
			// Url 입력으로 접근한 상황에서 로그인이 되어 있으면 무조건 메인페이지로 리디렉션
		}
		return "login"; // 아닐경우 로그인 페이지로
	}
	
	@RequestMapping("MyPage.do")
	public String showMyPage(HttpSession session, Model model) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		
		if (user != null) {
			model.addAttribute("userType", "사용자");
			model.addAttribute("name", user.getName());
			model.addAttribute("nickname", user.getNickname());
			return "mypage";
		} else if (manager != null) {
			model.addAttribute("userType", "관리자");
			model.addAttribute("name", manager.getManager_id());
			model.addAttribute("nickname", manager.getNickname());
			return "mypage";
		}
		return "redirect:MyLogin.do"; // 아닐경우 로그인 로직으로
	}
	
	@RequestMapping("Logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/MainMoodShop.do";
	}

	@RequestMapping("MyLoginMember.do")
	public String showJoinPage(HttpSession session) {
		if(session.getAttribute("user_id") != null || session.getAttribute("manager_id") != null) {
			return "redirect:Mainpage.do";
			// 로그인 되어있는 상태에서 이 페이지에 접근할 경우 무조건 메인페이지로 리디렉션
		}
		return "join";
	}

	@RequestMapping("joinsubmit.do")
	public String selectMemberType(HttpServletRequest request) {
		String MemberType = request.getParameter("MemberType");
		System.out.println("선택된 타입: " + MemberType);

		if ("Admin".equals(MemberType)) {
			return "redirect:/MyLoginAdminForm.do";
		} else if ("User".equals(MemberType)) {
			return "redirect:/MyLoginUserForm.do";
		} else {
			return "errorPage";
		}

	}

	@RequestMapping("MyLoginAdminForm.do")
	public String showAdminJoinPage() {
		return "adminjoin";
	}

	@RequestMapping("MyLoginUserForm.do")
	public String showUserJoinPage() {
		return "userjoin";
	}

	//
	
	@ResponseBody
	@RequestMapping(value = "MyLoginKaja.do", method = RequestMethod.POST,
					produces = "text/plain; charset=UTF-8")
	public String selectMemberTypeAjax(HttpServletRequest request, HttpSession session) {
		String MemberType = request.getParameter("MemberType");
		String userId = request.getParameter("user_id");
		String password = request.getParameter("password");
		
		password = PasswordUtil.toSHA256(password);
		
		System.out.println("선택된 타입: " + MemberType);

		if ("Admin".equals(MemberType)) {
			// 관리자 로그인 처리
			String manager_id = userId;
			ManagerVO managervo = loginService.getManagerVO(manager_id);
			if (managervo != null && managervo.getPassword().equals(password)) {
				session.setAttribute("managerVO", managervo);// 관리자 세션 설정
				return "admin";
			} else {
				return "아이디 또는 비밀번호가 일치하지 않습니다.";
			}
		} else if ("User".equals(MemberType)) {
			// 사용자 로그인 처리
			UserVO uservo = loginService.getUserVO(userId);
			if (uservo != null && uservo.getPassword().equals(password)) {
				session.setAttribute("userVO", uservo); // 사용자 세션 설정
				return "success"; // 로그인 성공
			} else {
				return "아이디 또는 비밀번호가 일치하지 않습니다.";
			}
		} else {
			return "회원유형 선택이 필요합니다.";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "MyLoginIdDoubleCheck.do", method = RequestMethod.POST)
	public String handleDoubleCheck(
			@RequestParam(value = "user_id", required= false) String user_id,
			@RequestParam(value = "manager_id", required= false) String manager_id
			) {
			if (user_id != null && manager_id == null) {
				boolean isDuplicate = loginService.checkUserId(user_id);
				return isDuplicate ? "DUPLICATE" : "AVAILABLE"; 
			} else if (user_id == null && manager_id != null) {
				boolean isDuplicate = loginService.checkManagerId(manager_id);
				return isDuplicate ? "DUPLICATE" : "AVAILABLE"; 
			} else {
				return "잘못된 요청입니다!";
			}
		}
	

	@ResponseBody
	@RequestMapping(value = "MyLoginAsDoubleCheck.do", method = RequestMethod.POST)
	public String handleDoubleCheck2(
			@RequestParam(value = "UserNickName", required= false) String UserNickName,
			@RequestParam(value = "ManagerNickName", required= false) String ManagerNickName
			) {
			if (UserNickName != null && ManagerNickName == null) {
				boolean isDuplicate = loginService.checkUserNick(UserNickName);
				return isDuplicate ? "DUPLICATE" : "AVAILABLE"; 
			} else if (UserNickName == null && ManagerNickName != null) {
				boolean isDuplicate = loginService.checkManagerNick(ManagerNickName);
				return isDuplicate ? "DUPLICATE" : "AVAILABLE"; 
			} else {
				return "잘못된 요청입니다!";
			}
		}
	
		@RequestMapping(value = "MyLoginAdminBeen.do", method = RequestMethod.POST)
	public String insertAdmin(ManagerVO managervo, Model model) {
		loginService.insertAdmin(managervo);
		
		model.addAttribute("manager_id",managervo.getManager_id());
		model.addAttribute("nickname",managervo.getNickname());
		return "AdminRegiCom";
	}
	
	@RequestMapping(value = "MyLoginBeen.do", method = RequestMethod.POST)
	public String insertUser(UserVO uservo, HttpServletRequest request, Model model) {

		String postcode = request.getParameter("postcode");// 우편번호
		String road_addr = request.getParameter("road_addr");// 도로명 주소
		String addr = request.getParameter("addr");// 지번 주소
		String sub_addr = request.getParameter("sub_addr");// 상세주소
		String extra_addr = request.getParameter("extra_addr");// 참고항목

		String address = postcode + "%$" + road_addr + "%$" + addr + "%$" + sub_addr + "%$" + extra_addr;
		// 나중에 출력시에는 split("%\\$")로 다시 배열하여 나눌 수 있습니다.
		// 출력 순서는 위를 따릅니다. index는 0부터 시작한다는 것을 기억하세요.

		uservo.setAddress(address);

		loginService.insertUser(uservo);
		
		model.addAttribute("user_id",uservo.getUser_id());
		model.addAttribute("nickname",uservo.getNickname());
		return "UserRegiCom";
	}
	
	@RequestMapping(value = "MyAdminStore.do", method = RequestMethod.POST)
	public String showAdminStoresPage() {
		return "AdminStores";
	}// 미구현....
	
	@RequestMapping("MyLoginId.do")
	public String showIdFind(HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		
		if (user != null || manager != null) {
			return "redirect:Mainpage.do";
		}
		return "IDFind";
	}
	
	@ResponseBody
	@RequestMapping(value = "MyLoginIdSeach.do", produces = "text/plain; charset=UTF-8", method = RequestMethod.POST)
	public String findLoginId(HttpServletRequest request) {
	    String memberType = request.getParameter("MemberType");
	    String name = request.getParameter("name");
	    String tel = request.getParameter("tel");

	    if ("User".equals(memberType)) {
	        UserVO user = loginService.findUserId(name, tel);
	        return (user != null) ? user.getUser_id() : "NOT_FOUND";
	    } else if ("Admin".equals(memberType)) {
	        ManagerVO manager = loginService.findManagerId(name, tel);
	        return (manager != null) ? manager.getManager_id() : "NOT_FOUND";
	    }
	    return "NOT_FOUND";
	}

	
	@RequestMapping("MyLoginPw.do")
	public String showPwFind(HttpSession session) {
		UserVO user = (UserVO) session.getAttribute("userVO");
		ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");
		
		if (user != null || manager != null) {
			return "redirect:Mainpage.do";
		}
		return "PWFind";
	}
	
	@ResponseBody
	@RequestMapping(value = "MyLoginPwSeach.do", produces = "text/plain; charset=UTF-8", method = RequestMethod.POST)
	public String findLoginPw(HttpServletRequest request) {
	    String memberType = request.getParameter("MemberType");
	    String name = request.getParameter("name");
	    String tel = request.getParameter("tel");

	    if ("User".equals(memberType)) {
	    	String user_id = request.getParameter("id");
	        UserVO user = loginService.findUserPw(name, tel, user_id);
	        return (user != null) ? user.getPassword() : "NOT_FOUND";
	    } else if ("Admin".equals(memberType)) {
	    	String manager_id = request.getParameter("id");
	        ManagerVO manager = loginService.findManagerPw(name, tel, manager_id);
	        return (manager != null) ? manager.getPassword() : "NOT_FOUND";
	    }
	    return "NOT_FOUND";
	}
	
	@RequestMapping(value = "ChangePasswordForm.do", method = RequestMethod.POST)
	public String changePasswordForm(@RequestParam String memberType, @RequestParam String id, Model model) {
	    model.addAttribute("memberType", memberType);
	    model.addAttribute("id", id);
		return "changePasswordForm"; // JSP 경로
	}
	
	@RequestMapping(value = "ChangePassword.do", method = RequestMethod.POST)
	public String changePassword( @RequestParam String memberType, @RequestParam String newPassword, HttpServletRequest request) {
	    if ("User".equals(memberType)) {
	    	String user_id = request.getParameter("id");
	        loginService.updateUserPassword(user_id, newPassword);
	    } else if ("Admin".equals(memberType)) {
	    	String manager_id = request.getParameter("id");
	        loginService.updateManagerPassword(manager_id, newPassword);
	    }

		return "redirect:/MyLogin.do"; // 변경 후 로그인 페이지로 리디렉션
	}

	@RequestMapping("AdminMainPage.do")
	public String showAdminMainPage(HttpSession session) {
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");
		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}

		return "admin_page"; // 관리자 메인 페이지 JSP
	}

}