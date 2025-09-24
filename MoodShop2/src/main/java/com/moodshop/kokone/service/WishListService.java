package com.moodshop.kokone.service;

import java.util.List;

import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.WishListVO;

public interface WishListService {
	int getWishlistCountByProduct(String productId);

	List<ProductVO> getWishlistByUser(String userId);

	boolean toggleWishlist(WishListVO vo);

	boolean checkIfWished(String userId, String productId);
}