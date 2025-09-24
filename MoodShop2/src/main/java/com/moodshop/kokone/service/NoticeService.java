package com.moodshop.kokone.service;

import java.util.List;
import java.util.Map;

import com.moodshop.kokone.vo.NoticeVO;

public interface NoticeService {
    List<NoticeVO> getAllNotices();

	// 관리자 전용
	void insertNotice(NoticeVO vo);

	void deleteNotice(String noticeId);

	List<NoticeVO> getNoticeList();

	void updateNoticeColumn(Map<String, Object> paramMap);
}