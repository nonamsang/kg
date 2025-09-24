package com.moodshop.kokone.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.WishListVO;

@Mapper
public interface WishListDAO {
	int getWishlistCountByProduct(String productId);

	List<ProductVO> getWishlistByUser(String userId);

	int checkIfWished(WishListVO vo);

	void insertWishlist(WishListVO vo);

	void deleteWishlist(WishListVO vo);

	// 김동주가 추가한 것
	int countWishlistByProductId(String productId);

}