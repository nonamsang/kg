package com.moodshop.kokone.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.moodshop.kokone.vo.BasketDetailVO;
import com.moodshop.kokone.vo.BasketJoinVO;
import com.moodshop.kokone.vo.BasketOptionVO;

@Mapper
public interface BasketDAO {

	String getOrCreateBasketIdByUser(String user_id);

	int insertBasketDetail(BasketDetailVO detail);

	int updateBasketTotalPrice(String basket_id);

	List<BasketJoinVO> getBasketListByUserId(String user_id);

	void deleteItems(List<String> selectedIds);

	void updateQuantity(@Param("basket_detail_id") String basket_detail_id,
			@Param("basket_detail_count") int basket_detail_count);

	void processPayment(List<String> selectedIds);

	int getItemTotalPrice(String basket_detail_id);

	List<BasketDetailVO> getSelectedItems(List<String> selectedIds);



    String findBasketIdByUserId(String userId);

    // 시퀀스 가져오기
    String getBasketId();

    // 장바구니 생성
    void createBasket(@Param("basketId") String basketId, @Param("userId") String userId);

    // 장바구니에 같은 상품 있는지 확인
    int checkProductInBasket(@Param("basketId") String basketId, @Param("productId") String productId);

    // 장바구니 상세 추가
    void addBasketDetail(@Param("basketId") String basketId, @Param("productId") String productId);

    // 장바구니 수량 증가
    void increaseBasketDetailCount(@Param("basketId") String basketId, @Param("productId") String productId);

    // 장바구니 상세 삭제
    void removeBasketDetail(@Param("basketId") String basketId, @Param("productId") String productId);

	String createBasket2(String userId);

	
	int getOptionPrice(String option_id);

	void addBasketDetail(BasketJoinVO item);

	void increaseBasketTotalPrice(String basketId, int totalItemPrice);

	List<BasketOptionVO> getProductOptions2(String product_id);

}
