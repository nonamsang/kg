package com.moodshop.kokone.dao;

import java.util.ArrayList;
import java.util.List;

import com.moodshop.kokone.vo.Order_DetailVO;

public interface Order_DetailDAO {

ArrayList<Order_DetailVO> getOrder_DetailVO(String order_id);

	void deleteOrderDetail(String order_id);

	// ------------ 아래 김동주가 추가한 것

	List<Order_DetailVO> getOrderDetailsByProductId(String product_id);

	void updateDeliveryStatusBatch(List<Long> orderIds, String newStatus);
}
