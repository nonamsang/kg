package com.moodshop.kokone.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodshop.kokone.service.RecentViewService;
import com.moodshop.kokone.vo.RecentViewVO;
import com.moodshop.kokone.vo.UserVO;


@Controller
public class RecentViewController {
	
	@Resource(name = "recentViewService")
	private RecentViewService recentViewService;
	
	/*
	 * @Autowired private RecentViewService recentViewService;
	 */
	// 상품 상세 페이지에 들어가면 자동으로 유저아이디와 제품아이디를 db에 저장
	@RequestMapping("addRecentView.do")
	public String addRecentView(HttpSession session, @RequestParam("productId") String productId) {
	    UserVO user = (UserVO) session.getAttribute("userVO");

	    if (user != null) {
	        String userId = user.getUser_id(); 
	        recentViewService.addRecentView(userId, productId);
	    }

	    return "redirect:/MainProductName.do?product_id=" + productId;
	}
	
	//페이지 접속 / 유저아이디를 갖고옴
	@RequestMapping("recentViewList.do")
	public String recentViewList(HttpSession session, Model model) {
	    UserVO user = (UserVO) session.getAttribute("userVO");

	    if (user != null) {
	        String userId = user.getUser_id(); 
	        List<RecentViewVO> recentList = recentViewService.getRecentViews(userId);
	        model.addAttribute("recentList", recentList);
	    }

	    return "MyProductLastView";
	}

	// 개별 삭제
    @RequestMapping("deleteRecent.do")
    public String deleteRecent(@RequestParam("recentId") String recentId, HttpServletResponse response) throws IOException {
        recentViewService.deleteRecentViewById(recentId);
        response.getWriter().write("success"); // 성공 응답
        return "redirect:/recentViewList.do"; // 삭제 후 목록 새로고침
    }

    // 전체 삭제
    @RequestMapping("deleteAllRecent.do")
    public String deleteAllRecent(HttpSession session, HttpServletResponse response) throws IOException {
        UserVO user = (UserVO) session.getAttribute("userVO");
        if (user != null) {
            String userId = user.getUser_id();
            recentViewService.deleteRecentViewByUser(userId);
        }
        response.getWriter().write("success"); // 성공 응답
        return "redirect:/recentViewList.do"; // 삭제 후 목록 새로고침
    }
}
