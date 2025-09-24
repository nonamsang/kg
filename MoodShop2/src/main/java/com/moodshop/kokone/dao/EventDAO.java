package com.moodshop.kokone.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.moodshop.kokone.vo.EventVO;

@Mapper
public interface EventDAO {

	List<EventVO> getAllEventDetail();

	// eventMain.jsp (분기)
	List<EventVO> getMainEventDetails();

	List<EventVO> selectEventDetailsByCategoryPrefix(String prefix);

	// eventClick.jsp 분기
	List<EventVO> selectOngoingEventDetails(); // SITE% + 진행중

	List<EventVO> selectEndedEventDetails(); // SITE% + 종료됨

	EventVO selectEventById(String eventId);

	/* eventClick 검색기능 */
	List<EventVO> selectEventDetailsByNameLike(String keyword);

	// 여기 부터 관리자용
	void insertEvent(EventVO vo);

	void updateEvent(EventVO vo);

	void deleteEvent(String eventId);

	List<EventVO> getEventList();

	void updateEventColumn(Map<String, Object> paramMap);
}
