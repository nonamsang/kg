package com.moodshop.kokone.service;

import java.util.List;
import java.util.Map;

import com.moodshop.kokone.vo.EventVO;

public interface EventService {

	List<EventVO> getAllEventDetail();

	// EventService.java
	List<EventVO> getMainEventDetails();

	List<EventVO> getEventDetailsByCategoryPrefix(String prefix);

	// eventClick.jsp
	List<EventVO> getOngoingEventDetails(); // 진행중 필터링

	List<EventVO> getEndedEventDetails(); // 종료 필터링

	EventVO getEventById(String eventId);

	List<EventVO> getEventDetailsByNameLike(String keyword);

	// 여기 부터 관리자용
	void insertEvent(EventVO vo);

	void updateEvent(EventVO vo);

	void deleteEvent(String eventId);

	List<EventVO> getEventList();

	void updateEventColumn(Map<String, Object> paramMap);
}

