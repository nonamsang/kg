package com.moodshop.kokone.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.moodshop.kokone.vo.Review_SubVO;
@Mapper
public interface Review_SubDAO {
	
	ArrayList<Review_SubVO> getAllReviewSub(String review_id);
	
	void getReviewSubInsert(Review_SubVO insertvo); 
	
	void getReviewSubUpdate(Review_SubVO updatevo);
	
	void getReviewSubDelete(Review_SubVO deletevo);
	
	Review_SubVO getReviewSubOne(String sub_id);
	
	void deleteReview2(String review_id);
	
}
