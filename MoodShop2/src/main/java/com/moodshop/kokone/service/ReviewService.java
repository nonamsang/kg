package com.moodshop.kokone.service;

import java.util.ArrayList;
import java.util.List;

import com.moodshop.kokone.vo.ReviewVO;
import com.moodshop.kokone.vo.Review_SubVO;

public interface ReviewService {


ArrayList<ReviewVO> getAllMyReview(String user_id);

ArrayList<Review_SubVO> getAllReviewSub(String review_id);

void getReviewSubInsert(Review_SubVO insertvo);

void getReviewSubUpdate(Review_SubVO updatevo);

void getReviewSubDelete(Review_SubVO deletevo);

ReviewVO getReviewByID(String review_id);

void insertReview(ReviewVO ReviewVO);

void updateReview(ReviewVO updatevo);

void deleteReview(ReviewVO deletevo);

Review_SubVO getReviewSubOne(String sub_id);

void insertReviewNoImage(ReviewVO ReviewVO);

ArrayList<ReviewVO> getAllReviewAsc(String user_id);

ArrayList<ReviewVO> getAllReviewDesc(String user_id);

ArrayList<ReviewVO> productToReview(String product_id);

//상품 페이지 리뷰
List<ReviewVO> getProductReviews(String product_id);
}
