package com.moodshop.kokone.service;

import java.util.List;

import com.moodshop.kokone.vo.AnswerVO;
import com.moodshop.kokone.vo.QnABoardVO;

public interface QnABoardService {

	List<QnABoardVO> getQnaAllList();
	
    // 사용자용
    List<QnABoardVO> getUserQnaList(String userId, int page);
    int getTotalPagesForUser(String userId);

    // 관리자용
    List<QnABoardVO> getAdminQnaList(String managerId, int page);
    int getTotalPagesForManager(String managerId);
    
    public QnABoardVO getQnaById(String qId);

	void modifyQna(QnABoardVO qna);
	
	boolean hasAnswer(String qId);  // 답변 여부 확인 메서드 추가
    
    // 질문에 대한 답변 조회
    List<AnswerVO> getAnswersByQnaId(String qId);

	void insertQna(QnABoardVO Qna);

	void updateAnswer(AnswerVO ans);

    AnswerVO getAnswerById(String aId);

	void insertAnswer(AnswerVO answer);;

    
}