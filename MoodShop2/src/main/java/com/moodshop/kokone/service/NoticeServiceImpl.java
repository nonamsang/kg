package com.moodshop.kokone.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.NoticeDAO;
import com.moodshop.kokone.vo.NoticeVO;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    @Override
    public List<NoticeVO> getAllNotices() {
        return noticeDAO.selectAllNotices();
    }

	// 관리자 전용
	@Override
	public void insertNotice(NoticeVO vo) {
		noticeDAO.insertNotice(vo);
	}

	@Override
	public void deleteNotice(String noticeId) {
		noticeDAO.deleteNotice(noticeId);
	}

	@Override
	public List<NoticeVO> getNoticeList() {
		return noticeDAO.getNoticeList();
	}

	@Override
	public void updateNoticeColumn(Map<String, Object> paramMap) {
		noticeDAO.updateNoticeColumn(paramMap);
	}
}