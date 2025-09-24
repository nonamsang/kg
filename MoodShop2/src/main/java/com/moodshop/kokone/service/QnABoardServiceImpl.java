package com.moodshop.kokone.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.AnswerDAO;
import com.moodshop.kokone.dao.QnABoardDAO;
import com.moodshop.kokone.vo.AnswerVO;
import com.moodshop.kokone.vo.QnABoardVO;

@Service("QnABoardService")
public class QnABoardServiceImpl implements QnABoardService {
	
	@Resource(name="qnaboardDAO")
	private QnABoardDAO qnaboardDAO;
	
	@Resource(name="")
	private AnswerDAO answerDAO;
	
	private static final int PAGE_SIZE = 10;

	@Override
	public List<QnABoardVO> getQnaAllList() {
	    List<QnABoardVO> list = qnaboardDAO.getQnaAllList();
	    System.out.println("불러온 게시글 수: " + list.size());
	    return list;
	}
	
    @Override
    public List<QnABoardVO> getUserQnaList(String userId, int page) {
        List<QnABoardVO> fullList = qnaboardDAO.getQnaListByUserId(userId);
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, fullList.size());

        if (start >= fullList.size()) {
            return new ArrayList<>();
        }

        return fullList.subList(start, end);
    }

    @Override
    public int getTotalPagesForUser(String userId) {
        int total = qnaboardDAO.getQnaCountByUserId(userId);
        return (int) Math.ceil((double) total / PAGE_SIZE);
    }

    @Override
    public List<QnABoardVO> getAdminQnaList(String managerId, int page) {
        List<QnABoardVO> fullList = qnaboardDAO.getQnaListByManagerId(managerId);
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, fullList.size());

        if (start >= fullList.size()) {
            return new ArrayList<>();
        }

        return fullList.subList(start, end);
    }

    @Override
    public int getTotalPagesForManager(String managerId) {
        int total = qnaboardDAO.getQnaCountByManagerId(managerId);
        return (int) Math.ceil((double) total / PAGE_SIZE);
    }

    @Override
    public QnABoardVO getQnaById(String qId) {
        return qnaboardDAO.getQnaById(qId);
    }
    
    @Override
    public void modifyQna(QnABoardVO qna) {
        qnaboardDAO.updateQna(qna);
    }
    
    @Override
    public boolean hasAnswer(String qId) {
        int count = qnaboardDAO.hasAnswer(qId);
        System.out.println("hasAnswer count: " + count);
        return count > 0;
    }

    @Override
    public List<AnswerVO> getAnswersByQnaId(String qId) {
        List<AnswerVO> list = answerDAO.selectAnswersByQnaId(qId);
        return (list != null) ? list : new ArrayList<>();
    }

	@Override
	public void insertQna(QnABoardVO qna) {
		qnaboardDAO.insertQna(qna);
	}

	@Override
	public void updateAnswer(AnswerVO answer) {
		answerDAO.updateAnswer(answer);
	}
	
    @Override
    public AnswerVO getAnswerById(String aId) {
        return answerDAO.getAnswerById(aId);
    }

	@Override
	public void insertAnswer(AnswerVO answer) {
		answerDAO.insertAnswer(answer);
	}
}
