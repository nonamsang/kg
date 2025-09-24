package com.moodshop.kokone.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.moodshop.kokone.service.MainMoodShopService;
import com.moodshop.kokone.service.NoticeService;
import com.moodshop.kokone.service.OrderService;
import com.moodshop.kokone.vo.Company1VO;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.NoticeVO;

@Controller
public class NoticeController {

    @Resource(name = "noticeService")
    private NoticeService noticeService;
    
    @Autowired
	private MainMoodShopService mainService;
    
	//김동주가 추가한 것
	@Resource(name = "OrderService")
	private OrderService orderService;

    // 공지사항 리스트 (Notice.jsp)
    @RequestMapping("/Notice.do")
    public String showNoticeList(Model model) {
        List<NoticeVO> noticeList = noticeService.getAllNotices();
        model.addAttribute("noticeList", noticeList);
        return "Notice"; // /WEB-INF/views/Notice.jsp
    }

	// Admin 전용
	@RequestMapping("admin_notice_list_page.do")
	public String adminNoticeListPage(HttpSession session, Model model) {
		
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");

		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}
		
		// 매니저가 관리 중인 회사 목록 조회
		List<Company1VO> companyVOs = orderService.getCompany(managervo.getManager_id());
		
		if (companyVOs == null || companyVOs.isEmpty()) {
			return "redirect:/AdminMainPage.do";
		}
		
		List<NoticeVO> noticeList = noticeService.getNoticeList();
		model.addAttribute("noticeList", noticeList);
		return "admin_notice_list_page";
	}

	// 공지사항 등록 페이지
	@RequestMapping("admin_notice_insert_page.do")
	public String adminNoticeInsertPage(HttpSession session) {
		
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");

		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}
		
		// 매니저가 관리 중인 회사 목록 조회
		List<Company1VO> companyVOs = orderService.getCompany(managervo.getManager_id());
		
		if (companyVOs == null || companyVOs.isEmpty()) {
			return "redirect:/AdminMainPage.do";
		}
		
		return "admin_notice_insert_page";
	}

	@RequestMapping("insertNotice.do")
	public String insertNotice(NoticeVO vo, MultipartFile mainImage, MultipartFile subImage, HttpServletRequest request,
			Model model) throws IOException {
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/img/notice_img/");

		File mainImg = new File(uploadPath, mainImage.getOriginalFilename());
		mainImage.transferTo(mainImg);
		vo.setNotice_image_source(mainImage.getOriginalFilename());

		if (!subImage.isEmpty()) {
			File subImg = new File(uploadPath, subImage.getOriginalFilename());
			subImage.transferTo(subImg);
			vo.setNotice_sub_image_source(subImage.getOriginalFilename());
		} else {
			vo.setNotice_sub_image_source(null);
		}

		noticeService.insertNotice(vo);
		model.addAttribute("message", "정상적으로 DB에 등록되었습니다.");

		return "admin_notice_insert_page";
	}

	@RequestMapping("deleteNotice.do")
	public String deleteNotice(String noticeId) {
		noticeService.deleteNotice(noticeId);
		return "redirect:admin_notice_list_page.do";
	}

	@RequestMapping("updateNotice.do")
	public String updateNotice(String noticeId, String column, String newValue) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("noticeId", noticeId);
		paramMap.put("column", column);
		paramMap.put("newValue", newValue);

		noticeService.updateNoticeColumn(paramMap);
		return "redirect:admin_notice_list_page.do";
	}
}