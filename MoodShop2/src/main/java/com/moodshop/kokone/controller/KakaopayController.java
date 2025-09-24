package com.moodshop.kokone.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moodshop.kokone.service.OrderService;
import com.moodshop.kokone.service.UserService;
import com.moodshop.kokone.vo.OrderVO;
import com.moodshop.kokone.vo.Order_DetailVO;
import com.moodshop.kokone.vo.UserVO;

@Controller
public class KakaopayController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@ResponseBody
	@RequestMapping("getUserInfo.do")
	public Map<String, Object> getUserInfo(HttpSession session) {
		UserVO user1 = (UserVO) session.getAttribute("userVO");

		String user_id = user1.getUser_id();

		// 유저 정보 조회
		UserVO user = userService.getUserVO(user_id);

		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("name", user.getName());
		userInfo.put("phone", user.getTel()); // 필요 시

		return userInfo;
	}

	@ResponseBody
	@RequestMapping(value = "orderInsert.do", method = RequestMethod.POST)
	public String insertOrder(@RequestBody Map<String, Object> requestData, HttpSession session) {

		String merchantUid = (String) requestData.get("merchant_uid");
		int paidAmount = (int) requestData.get("paid_amount");

		List<Map<String, Object>> orderItems = (List<Map<String, Object>>) requestData.get("orderItems");

		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do";
		}
		String user_id = user.getUser_id();

		// param 준비
		Map<String, Object> param = new HashMap<>();
		param.put("orderId", merchantUid);
		param.put("userId", user_id);
		param.put("paidAmount", paidAmount);
		param.put("orderItems", orderItems);

		// 다건 주문 insert 호출
		orderService.insertOrder(param);

		return "success";
	}



	@ResponseBody
	@RequestMapping(value = "orderInsertOne.do", method = RequestMethod.POST)
	public String insertOrderOne(@RequestParam("merchant_uid") String merchantUid,
			@RequestParam("paid_amount") int paidAmount, @RequestParam("product_id") String productId,
			@RequestParam("product_count") int productCount,
			@RequestParam(value = "option_id", required = false) String productColor, HttpSession session) {

		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do";
		}
		String user_id = user.getUser_id();

		Map<String, Object> param = new HashMap<>();
		param.put("orderId", merchantUid);
		param.put("userId", user_id);
		param.put("paidAmount", paidAmount);
		param.put("productId", productId);
		param.put("productCount", productCount);
		param.put("productColor", productColor);

		orderService.insertOrderOne(param);

		return "success";
	}

	@RequestMapping("orderComplete.do")
	public String orderComplete(@RequestParam("orderId") String orderId, Model model, HttpSession session) {

		UserVO user = (UserVO) session.getAttribute("userVO");
		if (user == null) {
			return "redirect:MyLogin.do";
		}
		String user_id = user.getUser_id();

		// 현재 주문
		OrderVO order = orderService.getOrderById(orderId);
		List<Order_DetailVO> orderDetails = orderService.getOrderDetailsByOrderId(orderId);

		model.addAttribute("order", order);
		model.addAttribute("orderDetails", orderDetails);

		// 과거 주문 목록 (현재 주문 제외)
		List<OrderVO> allOrders = orderService.getPastOrdersByUserId(user_id);

		// 현재 시간 기준 1분 전
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneMinuteAgo = now.minusMinutes(1);
		Date compareDate = Date.from(oneMinuteAgo.atZone(ZoneId.systemDefault()).toInstant());

		// 과거 주문만 필터링
		List<OrderVO> pastOrders = new ArrayList<>();
		for (OrderVO pastOrder : allOrders) {
			if (pastOrder.getOrder_date().before(compareDate) && !pastOrder.getOrder_id().equals(orderId)) {
				// 과거 주문 상세 가져오기
				List<Order_DetailVO> pastOrderDetails = orderService.getOrderDetailsByOrderId(pastOrder.getOrder_id());
				pastOrder.setOrderDetails(pastOrderDetails);
				pastOrders.add(pastOrder);
			}
		}

		model.addAttribute("pastOrders", pastOrders);


		return "orderComplete"; // JSP
	}

}
