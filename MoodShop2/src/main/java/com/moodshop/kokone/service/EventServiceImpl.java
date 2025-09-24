package com.moodshop.kokone.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.EventDAO;
import com.moodshop.kokone.vo.EventVO;

@Service("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
    private EventDAO eventDAO;

	@Override
	public List<EventVO> getAllEventDetail() {
		// TODO Auto-generated method stub
		return eventDAO.getAllEventDetail();
	}

	// eventMain.jsp
	@Override
	public List<EventVO> getMainEventDetails() {
		return eventDAO.getMainEventDetails();
	}

	@Override
	public List<EventVO> getEventDetailsByCategoryPrefix(String prefix) {
		return eventDAO.selectEventDetailsByCategoryPrefix(prefix);
	}

	// eventClick.jsp
	@Override
	public List<EventVO> getOngoingEventDetails() {
		return eventDAO.selectOngoingEventDetails(); // 🔁 SQL 필터링된 결과만 가져옴
	}

	@Override
	public List<EventVO> getEndedEventDetails() {
		return eventDAO.selectEndedEventDetails(); // 🔁 SQL 필터링된 결과만 가져옴
	}

	@Override
	public EventVO getEventById(String eventId) {
		return eventDAO.selectEventById(eventId);
	}

	@Override
	public List<EventVO> getEventDetailsByNameLike(String keyword) {
		return eventDAO.selectEventDetailsByNameLike(keyword);
	}

	// 여기 부터 관리자용
	@Override
	public void insertEvent(EventVO vo) {
		eventDAO.insertEvent(vo);
	}

	@Override
	public void updateEvent(EventVO vo) {
		eventDAO.updateEvent(vo);
	}

	@Override
	public void deleteEvent(String eventId) {
		eventDAO.deleteEvent(eventId);
	}

	@Override
	public List<EventVO> getEventList() {
		return eventDAO.getEventList();
	}

	@Override
	public void updateEventColumn(Map<String, Object> paramMap) {
		eventDAO.updateEventColumn(paramMap);
	}

	// 유틸 메서드
	private String getColumnValue(EventVO vo, String column) {
		switch (column) {
		case "event_name":
			return vo.getEvent_name();
		case "event_type":
			return vo.getEvent_type();
		case "event_title":
			return vo.getEvent_title();
		case "event_description":
			return vo.getEvent_description();
		case "event_category":
			return vo.getEvent_category();
		case "event_image_source":
			return vo.getEvent_image_source();
		case "event_sub_image_source":
			return vo.getEvent_sub_image_source();
		default:
			return "";
		}
	}

}
