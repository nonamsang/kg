package com.moodshop.kokone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.WishListDAO;
import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.WishListVO;

@Service("wishlistService")
public class WishListServiceImpl implements WishListService {

	@Autowired
	private WishListDAO wishlistDAO;

    @Override
	public int getWishlistCountByProduct(String productId) {
		return wishlistDAO.getWishlistCountByProduct(productId);
    }

    @Override
	public List<ProductVO> getWishlistByUser(String userId) {
		return wishlistDAO.getWishlistByUser(userId);
    }

    @Override
	public boolean toggleWishlist(WishListVO vo) {
		int exists = wishlistDAO.checkIfWished(vo);
		if (exists > 0) {
			wishlistDAO.deleteWishlist(vo);
			return false;
		} else {
			wishlistDAO.insertWishlist(vo);
			return true;
		}
    }

    @Override
	public boolean checkIfWished(String userId, String productId) {
		WishListVO vo = new WishListVO();
		vo.setUserId(userId);
		vo.setProductId(productId);
		return wishlistDAO.checkIfWished(vo) > 0;
    }

}