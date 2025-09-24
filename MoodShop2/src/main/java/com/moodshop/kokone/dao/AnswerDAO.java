package com.moodshop.kokone.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.moodshop.kokone.vo.AnswerVO;

@Mapper
@Repository("answerDAO")
public interface AnswerDAO {
	List<AnswerVO> getAnswersByQnaId(String qId);

	List<AnswerVO> selectAnswersByQnaId(String qId);

	void updateAnswer(AnswerVO answer);

	AnswerVO getAnswerById(String aId);
	
	void insertAnswer(AnswerVO answer);
}
