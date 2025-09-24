package com.moodshop.kokone.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.moodshop.kokone.vo.OrderVO;
import com.moodshop.kokone.vo.Order_DetailVO;

public interface OrderDAO {
	ArrayList<OrderVO> getAllOrder();
	OrderVO getOrderById(String order_id);
	
	List<Map<String, Object>> selectOrdersByDateRange(Map<String, Object> param);
	
	void deleteOrder(OrderVO deletevo);
	
	// 여기 김동주가 만들었음
	List<OrderVO> getOrderByOrderId(String order_id);

	// 2차 김동주가 추가

	void insertOrder(Map<String, Object> param);

	void insertOrderDetail(Order_DetailVO orderDetail);

	List<Order_DetailVO> getOrderDetailsByOrderId(String orderId);

	OrderVO getPastOrderByUserId(String user_id);

	List<OrderVO> getPastOrdersByUserId(String user_id);



}
