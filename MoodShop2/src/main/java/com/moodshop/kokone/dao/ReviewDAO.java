package com.moodshop.kokone.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.moodshop.kokone.vo.ReviewVO;
import com.moodshop.kokone.vo.Review_SubVO;
@Mapper
public interface ReviewDAO {

ArrayList<ReviewVO> getAllMyReview(String user_id);

ArrayList<Review_SubVO> getAllReviewSub(String review_id); //�׹ڶ߸鼭 �ڵ����� �־����Ͱ����� �ϴ� ����

ReviewVO getReviewByID(String review_id);

void insertReview(ReviewVO ReviewVO);

void updateReview(ReviewVO updatevo);

void deleteReview(ReviewVO deletevo);

void insertReviewNoImage(ReviewVO ReviewVO);

ArrayList<ReviewVO> getAllReviewAsc(String user_id);

ArrayList<ReviewVO> getAllReviewDesc(String user_id);

ArrayList<ReviewVO> productToReview(String product_id);

//상품 페이지 리뷰
List<ReviewVO> getProductReviews(String product_id);
}
