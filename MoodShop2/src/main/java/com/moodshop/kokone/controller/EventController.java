package com.moodshop.kokone.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodshop.kokone.service.EventService;
import com.moodshop.kokone.service.MainMoodShopService;
import com.moodshop.kokone.vo.EventVO;

@Controller
public class EventController {

	@Resource(name = "eventService")
	private EventService eventService;

	@Autowired
	private MainMoodShopService mainService;

	/**
	 * 📌 이벤트 메인 화면 (limitevent / flowers / goods)
	 */
	@RequestMapping("/eventMain.do")
	public String eventMain(
			@RequestParam(value = "section", required = false, defaultValue = "limitevent") String section,
			Model model) {


		// 메뉴 상세(section) 분기
		List<EventVO> sectionEventList = null;

		if ("limitevent".equals(section)) {
			sectionEventList = eventService.getEventDetailsByCategoryPrefix("여름 프로모션");
		} else if ("flowers".equals(section)) {
			sectionEventList = eventService.getEventDetailsByCategoryPrefix("라넌큘러스");
		} else if ("goods".equals(section)) {
			sectionEventList = eventService.getEventDetailsByCategoryPrefix("봉제인형");
		} else {
			sectionEventList = eventService.getAllEventDetail(); // fallback
		}

		model.addAttribute("eventcard_detail_list", sectionEventList); // ✅ 메뉴용 카드

		// 2. 이벤트 상세 리스트 (굿즈, 이미지, 영상)
		List<EventVO> eventlist = eventService.getMainEventDetails();
		model.addAttribute("event_detail_list", eventlist);

		// 3. section 파라미터 확인

		if (section == null || section.trim().isEmpty()) {
			section = "limitevent"; // 기본값
		}
		model.addAttribute("section", section);

		return "eventMain"; // /WEB-INF/views/eventMain.jsp
	}

	/**
	 * 📌 이벤트 전체 상세 보기 (eventClick.jsp)
	 */
	@RequestMapping("/eventClick.do")
	public String showEventClick(@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "keyword", required = false) String keyword, Model model) {

		// 처음 들어올 때 status가 없으면 강제로 ongoing으로 리다이렉트
		if (status == null || status.trim().isEmpty()) {
			return "redirect:/eventClick.do?status=ongoing";
		}

		List<EventVO> list;

		if (keyword != null && !keyword.trim().isEmpty()) {
			list = eventService.getEventDetailsByNameLike(keyword);
		} else if ("ongoing".equals(status)) {
			list = eventService.getOngoingEventDetails();
		} else if ("ended".equals(status)) {
			list = eventService.getEndedEventDetails();
		} else {
			list = new ArrayList<>();
		}

		model.addAttribute("event_detail_list", list);
		model.addAttribute("status", status); // JSP에서 메뉴 활성화 용
		return "eventClick";
	}

	/* eventClick 자세히보기 버튼의 페이지-> event_id로 구분하여 출력함 */
	@RequestMapping("/eventClick_serve.do")
	public String showEventClickServe(@RequestParam("event_id") String eventId, Model model) {
		EventVO event = eventService.getEventById(eventId);
		model.addAttribute("event", event);
		return "eventClick_serve";
	}

	/**
	 * 📌 이벤트 배너 클릭 시 연결 경로 처리
	 */
	@RequestMapping("eventbanner.do")
	public String eventbanner(@RequestParam("event_id") String eventId) {
		// 예: link_url = "eventMain.do?section=shop"
		return eventId;
	}
}
