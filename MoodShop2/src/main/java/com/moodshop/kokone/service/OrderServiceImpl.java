package com.moodshop.kokone.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moodshop.kokone.dao.Company1DAO;
import com.moodshop.kokone.dao.OrderDAO;
import com.moodshop.kokone.dao.Order_DetailDAO;
import com.moodshop.kokone.dao.ProductDAO;
import com.moodshop.kokone.dao.UserDAO;
import com.moodshop.kokone.vo.Company1VO;
import com.moodshop.kokone.vo.OrderVO;
import com.moodshop.kokone.vo.Order_DetailVO;
import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.UserVO;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BasketService basketService;
	// �ֹ� DAO
	@Resource(name = "orderDAO")
	private OrderDAO orderDAO;
	// �ֹ� �� DAO
	@Resource(name = "order_DetailDAO")
	private Order_DetailDAO order_DetailDAO;

	// 김동주가 추가한 DAO
	@Resource(name = "company1DAO")
	private Company1DAO company1DAO;

	@Resource(name = "productDAO")
	private ProductDAO productDAO;

	@Resource(name = "userDAO")
	private UserDAO userDAO;

	// ��� �ֹ� ��ȸ + �ֹ��󼼱���
	@Override
	public ArrayList<OrderVO> getAllOrder() {
		ArrayList<OrderVO> orders = orderDAO.getAllOrder();
		for (OrderVO order : orders) {
			List<Order_DetailVO> details = order_DetailDAO.getOrder_DetailVO(order.getOrder_id());
			order.setOrderDetails(details);
		}
		return orders;
	}

	// �ֹ� ��¥���� ��ȸ
	@Override
	public List<Map<String, Object>> getOrdersByDate(Map<String, Object> param) {
		return orderDAO.selectOrdersByDateRange(param);
	}

	// �ֹ� �� ��ȸ
	@Override
	public ArrayList<Order_DetailVO> getOrder_DetailVO(String order_id) {
		return order_DetailDAO.getOrder_DetailVO(order_id);
	}

	// �ֹ��ϳ� ��ȸ
	@Override
	public OrderVO getOrderById(String order_id) {
		return orderDAO.getOrderById(order_id);
	}

	// �ֹ����
	@Override
	@Transactional
	public void deleteOrder(OrderVO deletevo) {
		order_DetailDAO.deleteOrderDetail(deletevo.getOrder_id());
		orderDAO.deleteOrder(deletevo);
	}

	// ------------김동주 작업 + Company1DAO.java추가
	// 회사 읽기
	@Override
	public List<Company1VO> getCompany(String managerId) {
		return company1DAO.getCompanyByManagerId(managerId);
	}

	// 제품읽기
	@Override
	public List<ProductVO> getProducts(String companyName) {
		return productDAO.getProductsByCompanyName(companyName);
	}

	@Override
	public List<Order_DetailVO> getOrderDetailsByProductId(String product_id) {
		return order_DetailDAO.getOrderDetailsByProductId(product_id);
	}

	@Override
	public List<OrderVO> getOrderByOrderId(String order_id) {
		return orderDAO.getOrderByOrderId(order_id);
	}

	@Override
	public List<UserVO> getUserInfoByUserId(String user_id) {
		return userDAO.getUserInfoByUserId(user_id);
	}
	// -- 2차 김동주가 추가

	@Override
	public void updateDeliveryStatusBatch(List<Long> orderIds, String newStatus) {
		order_DetailDAO.updateDeliveryStatusBatch(orderIds, newStatus);
	}

	@Override
	public void insertOrder(Map<String, Object> param) {
	    String orderId = (String) param.get("orderId");
	    String userId = (String) param.get("userId");
	    int paidAmount = (int) param.get("paidAmount");

	    // ✔️ 프론트에서 바로 넘겨준 orderItems JSON 목록
	    List<Map<String, Object>> orderItems = (List<Map<String, Object>>) param.get("orderItems");

	    // ✅ 주문 테이블 insert (한번)
	    orderDAO.insertOrder(param);

	    for (Map<String, Object> item : orderItems) {
	        String basketDetailId = (String) item.get("basket_detail_id");
	        String productId = (String) item.get("product_id");
	        String optionId = (String) item.get("option_id");
	        int basketCount = (int) item.get("basket_count");

			// ✔️ optionId = PK -> 색상 조회 필요
			String optionColor = productDAO.getOptionColorById(optionId);

	        // ✅ 재고 조회
	        Map<String, Object> stockParam = new HashMap<>();
	        stockParam.put("productId", productId);
			stockParam.put("productColor", optionId); // ✔️ optionColor로 수정

	        Integer currentStock = productDAO.getStock(stockParam);

	        if (currentStock == null) {
	            throw new RuntimeException("재고 정보를 찾을 수 없습니다.");
	        }

	        if (basketCount > currentStock) {
				throw new RuntimeException("재고 부족: 주문 수량이 현재 재고보다 많습니다.");
			}

			// ✅ 재고 차감
			productDAO.decreaseStock(productId, optionId, basketCount);

			// ✅ 주문 상세 저장
			Order_DetailVO orderDetail = new Order_DetailVO();
			orderDetail.setOrder_id(orderId);
			orderDetail.setProduct_id(productId);
			orderDetail.setOption_id(optionId);
			orderDetail.setDetail_count(basketCount);
			orderDetail.setDetail_price(paidAmount); // ✔️ 단가 관리 필요 시 수정 가능

			orderDAO.insertOrderDetail(orderDetail);
		}

		// ✅ 장바구니에서 결제한 상품 삭제
		List<String> basketDetailIds = new ArrayList<>();
		for (Map<String, Object> item : orderItems) {
			basketDetailIds.add((String) item.get("basket_detail_id"));
		}

		basketService.deleteItems(basketDetailIds);
	}



	// 단일 결제
	@Override
	public void insertOrderOne(Map<String, Object> param) {
		// Map에서 값 꺼내기
		String orderId = (String) param.get("orderId");
		String userId = (String) param.get("userId");
		int paidAmount = (int) param.get("paidAmount");
		String productId = (String) param.get("productId");
		String productColor = (String) param.get("productColor");
		int productCount = (int) param.get("productCount");

		// ✅ 현재 재고 조회용 파라미터 준비
		Map<String, Object> stockParam = new HashMap<>();
		stockParam.put("productId", productId);
		stockParam.put("productColor", productColor);

		// ✅ 현재 재고 조회
		Integer currentStock = productDAO.getStock(stockParam);

		if (currentStock == null) {
			throw new RuntimeException("재고 정보를 찾을 수 없습니다.");
		}

		if (productCount > currentStock) {
			throw new RuntimeException("재고 부족: 주문 수량이 현재 재고보다 많습니다.");
		}

		// ✅ 주문 테이블 insert
		orderDAO.insertOrder(param);

		// ✅ 주문 상세 insert
		Order_DetailVO orderDetail = new Order_DetailVO();
		orderDetail.setOrder_id(orderId);
		orderDetail.setProduct_id(productId);
		orderDetail.setOption_id(productColor);
		orderDetail.setDetail_count(productCount);
		orderDetail.setDetail_price(paidAmount);

		orderDAO.insertOrderDetail(orderDetail);

		// ✅ 재고 차감
		productDAO.decreaseStock(productId, productColor, productCount);
	}



	@Override
	public List<Order_DetailVO> getOrderDetailsByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return orderDAO.getOrderDetailsByOrderId(orderId);
	}

	@Override
	public List<OrderVO> getPastOrdersByUserId(String user_id) {
		// TODO Auto-generated method stub
		return orderDAO.getPastOrdersByUserId(user_id);
	}


}