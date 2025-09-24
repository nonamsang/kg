package com.moodshop.kokone.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.moodshop.kokone.service.EventService;
import com.moodshop.kokone.service.OrderService;
import com.moodshop.kokone.vo.EventVO;
import com.moodshop.kokone.vo.ManagerVO;

//이벤트 관리자용 컨트롤러
@Controller
public class AdminEventController {

    @Resource(name = "eventService")
    private EventService eventService;
    
	//김동주가 추가한 것
	@Resource(name = "OrderService")
	private OrderService orderService;

    // 이벤트 등록 페이지
    @RequestMapping("admin_event_insert_page.do")
    public String adminEventInsertPage(HttpSession session) {
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");

		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}
        return "admin_event_insert_page";
    }

    // 이벤트 등록
    @RequestMapping("insertEvent.do")
    public String insertEvent(EventVO vo, MultipartFile mainImage, MultipartFile subImage, HttpServletRequest request, Model model) throws IOException {
        String uploadPath = request.getSession().getServletContext().getRealPath("/resources/img/event_img/");

// D:\springworkspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\
// wtpwebapps\MoodShop2\resources\img\event_img : 등록한 메인, 서브 이미지는 집컴 기준으로 이 경로로 임시 저장됨

        File mainImg = new File(uploadPath, mainImage.getOriginalFilename());
        mainImage.transferTo(mainImg);
        vo.setEvent_image_source(mainImage.getOriginalFilename());

        if (!subImage.isEmpty()) {
            File subImg = new File(uploadPath, subImage.getOriginalFilename());
            subImage.transferTo(subImg);
            vo.setEvent_sub_image_source(subImage.getOriginalFilename());
        }else {
            vo.setEvent_sub_image_source(null);
        }

        eventService.insertEvent(vo);
        model.addAttribute("message", "정상적으로 DB에 등록되었습니다.");
        return "admin_event_insert_page";
    }


    // 이벤트 목록 페이지 (수정/삭제)
    @RequestMapping("admin_event_list_page.do")
    public String adminEventListPage(HttpSession session, Model model) {
    	
    	
		ManagerVO managervo = (ManagerVO) session.getAttribute("managerVO");

		if (managervo == null) {
			return "redirect:/MyLogin.do";
		}
    	
    	
    	
        List<EventVO> eventList = eventService.getEventList();
        model.addAttribute("eventList", eventList);
        return "admin_event_list_page";
    }

    // 이벤트 수정
    @RequestMapping("updateEvent.do")
    public String updateEvent(String eventId, String column, String newValue) {
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("eventId", eventId);
        paramMap.put("column", column);
        paramMap.put("newValue", newValue);

        eventService.updateEventColumn(paramMap);

        return "redirect:admin_event_list_page.do";
    }


    // 이벤트 삭제
    @RequestMapping("deleteEvent.do")
    public String deleteEvent(String eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:admin_event_list_page.do";
    }
}

