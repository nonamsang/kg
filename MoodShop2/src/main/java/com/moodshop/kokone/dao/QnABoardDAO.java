package com.moodshop.kokone.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.moodshop.kokone.vo.QnABoardVO;


@Repository("qnaboardDAO")
public interface QnABoardDAO {

	List<QnABoardVO> getQnaAllList();
	
	 // 전체 리스트 (사용자 ID 기준)
    List<QnABoardVO> getQnaListByUserId(@Param("userId")String userId);

    // 전체 개수 (페이징 계산용)
    int getQnaCountByUserId(@Param("userId")String userId);

    // 관리자도 마찬가지로 필요하다면 아래 추가
    List<QnABoardVO> getQnaListByManagerId(@Param("managerId")String managerId);
    int getQnaCountByManagerId(@Param("managerId")String managerId);

	QnABoardVO getQnaById(String qId);
	
	void updateQna(QnABoardVO qna);

	int hasAnswer(String qId);

	void insertQna(QnABoardVO qna);
}
