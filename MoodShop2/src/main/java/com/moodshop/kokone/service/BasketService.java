package com.moodshop.kokone.service;

import java.util.List;

import com.moodshop.kokone.vo.BasketDetailVO;
import com.moodshop.kokone.vo.BasketJoinVO;
import com.moodshop.kokone.vo.BasketOptionVO;

public interface BasketService {

	String getOrCreateBasketIdByUser(String user_id);

	void insertBasketDetail(BasketDetailVO detail);

	void updateBasketTotalPrice(String basket_id);

	List<BasketJoinVO> getBasketListByUserId(String user_id);

	void deleteItems(List<String> selectedIds);

	void updateQuantity(String basket_detail_id, int basket_detail_count);

	void processPayment(List<String> selectedIds);

	int getItemTotalPrice(String basket_detail_id);

	List<BasketDetailVO> getSelectedItems(List<String> selectedIds);

	void addCart(String userId, String productId);

	void removeCart(String userId, String productId);

	void addCartList(String user_id, List<BasketJoinVO> cartList);

	List<BasketOptionVO> getProductOptions2(String product_id);

	String findBasketIdByUserId(String user_id);

	void createBasket(String user_id);

}
