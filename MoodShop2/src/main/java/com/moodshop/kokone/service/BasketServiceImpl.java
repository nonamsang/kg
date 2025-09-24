package com.moodshop.kokone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.BasketDAO;
import com.moodshop.kokone.vo.BasketDetailVO;
import com.moodshop.kokone.vo.BasketJoinVO;
import com.moodshop.kokone.vo.BasketOptionVO;

@Service("basketService")
public class BasketServiceImpl implements BasketService {

	@Autowired
	private BasketDAO basketDAO; // 소문자로 객체명 사용

	@Override
	public String getOrCreateBasketIdByUser(String user_id) {
		return basketDAO.getOrCreateBasketIdByUser(user_id);
	}

	@Override
	public void insertBasketDetail(BasketDetailVO detail) {
		basketDAO.insertBasketDetail(detail);
	}

	@Override
	public void updateBasketTotalPrice(String basket_id) {
		basketDAO.updateBasketTotalPrice(basket_id);
	}

	@Override
	public List<BasketJoinVO> getBasketListByUserId(String user_id) {
		// TODO Auto-generated method stub
		return basketDAO.getBasketListByUserId(user_id);
	}


	@Override
	public void deleteItems(List<String> selectedIds) {
		// TODO Auto-generated method stub
		basketDAO.deleteItems(selectedIds);
	}

	@Override
	public void updateQuantity(String id, int newQty) {
		// TODO Auto-generated method stub
		basketDAO.updateQuantity(id, newQty);
	}

	@Override
	public void processPayment(List<String> selectedIds) {
		// TODO Auto-generated method stub
		basketDAO.processPayment(selectedIds);
	}

	@Override
	public int getItemTotalPrice(String basket_detail_id) {
		// TODO Auto-generated method stub
		return basketDAO.getItemTotalPrice(basket_detail_id);
	}

	@Override
	public List<BasketDetailVO> getSelectedItems(List<String> selectedIds) {
		// TODO Auto-generated method stub
		return basketDAO.getSelectedItems(selectedIds);
	}

	@Override
	public void addCart(String userId, String productId) {
	    String basketId = basketDAO.findBasketIdByUserId(userId);
	    if (basketId == null) { // 장바구니가 없으면 생성
	        basketId = basketDAO.getBasketId(); // 시퀀스로 ID 생성
	        basketDAO.createBasket(basketId, userId);
	    }

	    Integer count = basketDAO.checkProductInBasket(basketId, productId);
	    if (count == 0) {
	        basketDAO.addBasketDetail(basketId, productId);
	    } else {
	        basketDAO.increaseBasketDetailCount(basketId, productId);
	    }
	}

	@Override
	public void removeCart(String userId, String productId) {
	    String basketId = basketDAO.findBasketIdByUserId(userId);
	    basketDAO.removeBasketDetail(basketId, productId);
	}

	@Override
	public void addCartList(String userId, List<BasketJoinVO> cartList) {
	    String basketId = basketDAO.findBasketIdByUserId(userId);
	    if (basketId == null) {
	        basketId = basketDAO.createBasket2(userId);
	    }

	    for (BasketJoinVO item : cartList) {
	        int optionPrice = basketDAO.getOptionPrice(item.getOption_id());
	        int totalItemPrice = item.getProduct_price() + optionPrice;

	        item.setBasket_id(basketId);
	        item.setBasket_detail_price(totalItemPrice);

	        basketDAO.addBasketDetail(item);
	        basketDAO.increaseBasketTotalPrice(basketId, totalItemPrice);
	    }
	}

	@Override
	public List<BasketOptionVO> getProductOptions2(String product_id) {
		// TODO Auto-generated method stub
		return basketDAO.getProductOptions2(product_id);
	}

	@Override
	public String findBasketIdByUserId(String userId) {
		// TODO Auto-generated method stub
		return basketDAO.findBasketIdByUserId(userId);
	}

	@Override
	public void createBasket(String userId) {
		// TODO Auto-generated method stub
		basketDAO.createBasket2(userId);
	}
}
