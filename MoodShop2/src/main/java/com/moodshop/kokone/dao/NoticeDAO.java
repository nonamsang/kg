package com.moodshop.kokone.dao;

import java.util.List;
import java.util.Map;

import com.moodshop.kokone.vo.NoticeVO;

public interface NoticeDAO {
    List<NoticeVO> selectAllNotices();

	// 관리자 전용
	void insertNotice(NoticeVO vo);

	void deleteNotice(String noticeId);

	List<NoticeVO> getNoticeList();

	void updateNoticeColumn(Map<String, Object> paramMap);
}