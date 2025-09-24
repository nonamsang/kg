package com.moodshop.kokone.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodshop.kokone.service.UserService;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.UserVO;

@Controller
public class AdminController {
	
	
	  @Autowired
	  private UserService userService;
	 
	  @RequestMapping("UserUpdatePage.do")
		public String showUserUpdatePage(HttpSession session, @RequestParam("user_id") String user_id, Model model) {

			ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");
			if (managervo == null) {
				return "redirect:/MyLogin.do";
			}

		  UserVO user = userService.getUserVO(user_id);
		  model.addAttribute("user",user);
		  
		  return "user_update";
	  }
	  
		// 김동주 수정
	  @RequestMapping(value = "UserUpdate.do", method = RequestMethod.POST)
		public String updateUser(UserVO user, Model model, HttpServletRequest request) {
		  // 전달받은 회원 정보를 DB에 업데이트
			String postcode = request.getParameter("postcode");// 우편번호
			String road_addr = request.getParameter("road_addr");// 도로명 주소
			String addr = request.getParameter("addr");// 지번 주소
			String sub_addr = request.getParameter("sub_addr");// 상세주소
			String extra_addr = request.getParameter("extra_addr");// 참고항목

			String address = postcode + "%$" + road_addr + "%$" + addr + "%$" + sub_addr + "%$" + extra_addr;
			// 나중에 출력시에는 split("%\\$")로 다시 배열하여 나눌 수 있습니다.
			// 출력 순서는 위를 따릅니다. index는 0부터 시작한다는 것을 기억하세요.

			user.setAddress(address);
		    userService.updateUser(user);
		    
		    return "redirect:/UserListPage.do"; 
	  }
	  
	  @RequestMapping("DeleteUser.do")
	  public String deleteUser(@RequestParam("user_id") String user_id) {
	      // user_id에 해당하는 회원을 DB에서 삭제
	      userService.deleteUser(user_id);

	      return "redirect:/UserListPage.do";
	  }

}
