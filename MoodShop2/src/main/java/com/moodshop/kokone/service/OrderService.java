package com.moodshop.kokone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.moodshop.kokone.vo.Company1VO;
import com.moodshop.kokone.vo.OrderVO;
import com.moodshop.kokone.vo.Order_DetailVO;
import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.UserVO;

public interface OrderService{
ArrayList <OrderVO> getAllOrder();

ArrayList<Order_DetailVO> getOrder_DetailVO(String order_id);
List<Map<String, Object>> getOrdersByDate(Map<String, Object> param);
OrderVO getOrderById(String order_id);
void deleteOrder(OrderVO deletevo);

// 여기서부터 김동주가 만듬

List<Company1VO> getCompany(String managerId);

List<ProductVO> getProducts(String companyName);

List<Order_DetailVO> getOrderDetailsByProductId(String product_id);

List<OrderVO> getOrderByOrderId(String order_id);

List<UserVO> getUserInfoByUserId(String user_id);

// --2차 김동주가 추가

void updateDeliveryStatusBatch(List<Long> orderIds, String newStatus);



void insertOrder(Map<String, Object> param);

void insertOrderOne(Map<String, Object> param);

List<Order_DetailVO> getOrderDetailsByOrderId(String orderId);

List<OrderVO> getPastOrdersByUserId(String user_id);


}