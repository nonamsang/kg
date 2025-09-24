package com.moodshop.kokone.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodshop.kokone.dao.ReviewDAO;
import com.moodshop.kokone.dao.Review_SubDAO;
import com.moodshop.kokone.vo.ReviewVO;
import com.moodshop.kokone.vo.Review_SubVO;

@Service("ReviewService")
public class ReviewServiceImpl implements ReviewService {
	// ���� DAO
	@Resource(name = "reviewDAO")
	private ReviewDAO reviewDAO;
	// ���� ��� DAO
	@Resource(name = "review_SubDAO")
	private Review_SubDAO review_SubDAO;
	// ���� ������������ ��ȸ

	//�� ���丸 ����(�����������θ�..)
	@Override
	public ArrayList<ReviewVO> getAllMyReview(String user_id) {
		return reviewDAO.getAllMyReview(user_id);
	}
	//���� ����
	@Override
	public void insertReview(ReviewVO ReviewVO) {
		reviewDAO.insertReview(ReviewVO);
	}
	//���� ����
	@Override
	public void updateReview(ReviewVO updatevo) {
		reviewDAO.updateReview(updatevo);
	}
	//���� ������ ��� ��۱��� ����
	@Override
	@Transactional
	public void deleteReview(ReviewVO deletevo) {
		review_SubDAO.deleteReview2(deletevo.getReview_id());
		reviewDAO.deleteReview(deletevo);
	}
	//���� �ϳ� ����
	@Override
	public ReviewVO getReviewByID(String review_id) {
		return reviewDAO.getReviewByID(review_id);
	}
	//���� �ϳ� �����ؼ� ���� ��� �ۼ�
	@Override
	public ArrayList<Review_SubVO> getAllReviewSub(String review_id) {
		return review_SubDAO.getAllReviewSub(review_id);
	}
	//���� ��� ����
	@Override
	public void getReviewSubInsert(Review_SubVO insertvo) {
		review_SubDAO.getReviewSubInsert(insertvo);
	}
	//���� ��� ����
	@Override
	public void getReviewSubUpdate(Review_SubVO updatevo) {
		review_SubDAO.getReviewSubUpdate(updatevo);
	}
	//���� ��� ����
	@Override
	public void getReviewSubDelete(Review_SubVO deletevo) {
		review_SubDAO.getReviewSubDelete(deletevo);
	}
	//���� ��� �ϳ� ����
	@Override
	public Review_SubVO getReviewSubOne(String sub_id) {
		return review_SubDAO.getReviewSubOne(sub_id);
	}

	@Override
	public void insertReviewNoImage(ReviewVO ReviewVO) {
		// TODO Auto-generated method stub
		reviewDAO.insertReviewNoImage(ReviewVO);
		
	}

	@Override
	public ArrayList<ReviewVO> getAllReviewAsc(String user_id) {
		// TODO Auto-generated method stub
		return reviewDAO.getAllReviewAsc(user_id);
	}

	@Override
	public ArrayList<ReviewVO> getAllReviewDesc(String user_id) {
		// TODO Auto-generated method stub
		return reviewDAO.getAllReviewDesc(user_id);
	}

	@Override
	public ArrayList<ReviewVO> productToReview(String product_id) {
		// TODO Auto-generated method stub
		return reviewDAO.productToReview(product_id);
	}
	
	//상품 페이지 리뷰
	@Override
    public List<ReviewVO> getProductReviews(String product_id) {
        return reviewDAO.getProductReviews(product_id);
    }
}
