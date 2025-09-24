package com.moodshop.kokone.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moodshop.kokone.service.BasketService;
import com.moodshop.kokone.service.OrderService;
import com.moodshop.kokone.vo.Company1VO;
import com.moodshop.kokone.vo.ManagerVO;
import com.moodshop.kokone.vo.OrderVO;
import com.moodshop.kokone.vo.Order_DetailVO;
import com.moodshop.kokone.vo.ProductVO;
import com.moodshop.kokone.vo.UserVO;

@Controller
public class OrderController {

@Resource(name = "OrderService")
	private OrderService orderService;

	@Autowired
	private BasketService basketService;

//주문내역 조회 페이지
@RequestMapping(value = "MyOrderHistory.do")
	public String order(Model model) {
		ArrayList<OrderVO> orderlist = orderService.getAllOrder(); // 모든 주문 목록 + 상세 포함
		// String user_id = (String) session.getAttribute("user_id");
		model.addAttribute("orderlist", orderlist);
		return "OrderHistory"; // OrderHistory.jsp 로 이동
	}
	// 주문내역 기간을 지정해서 조회 페이지
	@RequestMapping(value = "MyOrderSearches.do", produces = "text/html; charset=UTF-8")
		public String orderduring(@RequestParam("between") String between, @RequestParam("and") String and, Model model,
				HttpSession session) {
			String user_id = (String) session.getAttribute("user_id");
			Map<String, Object> param = new HashMap<>();
			param.put("startDate", between);
			param.put("endDate", and);
			param.put("user_id", user_id);

			List<Map<String, Object>> orderlist = orderService.getOrdersByDate(param);

			model.addAttribute("orderlist", orderlist);

			// ✅ 이 JSP는 AJAX 요청이 받아올 "조각 뷰"
			return "ajax/OrderListAjax"; // 예: /WEB-INF/views/ajax/OrderListAjax.jsp
		}
		// 주문내역에서 뒤로가기 (MyOrderHistory 페이지로)
		@RequestMapping(value = "MytoOrderHistory.do")
		public String goMyOrderHistorypage(Model model) {
			ArrayList<OrderVO> orderlist = orderService.getAllOrder(); // 모든 주문 목록 + 상세 포함
			model.addAttribute("orderlist", orderlist);
			return "OrderHistory";
		}

		// 배송 조회 (주문 ID로 배송 상태 계산)
		@RequestMapping(value = "MyDelivery.do")
		public String Mydelivery(@RequestParam("order_id") String order_id, Model model) {

			ArrayList<OrderVO> orderlist2 = orderService.getAllOrder(); // 전체 주문 가져오기
			LocalDate today = LocalDate.now(); // 현재 날짜
			System.out.println(order_id);
			for (OrderVO order : orderlist2) {
				if (order.getOrder_id().equals(order_id)) {

					// 주문일자 가져오기
					LocalDate orderDate = order.getOrder_date().toLocalDate();
					long days = ChronoUnit.DAYS.between(orderDate, today); // 경과 일수 계산

					// 주문 객체에 배송 상태 설정
					model.addAttribute("order", order); // 뷰에 전달
					break;
				}
			}

			return "MyDelivery"; // MyDelivery.jsp
		}

		// 주문 상세 조회 (여러 개일 수 있음)
		@RequestMapping(value = "MyOrderDetail.do")
		public String Myorder_Detail(@RequestParam("order_id") String order_id, Model model) {

			// 주문 ID로 상세 내역 목록 조회
			ArrayList<Order_DetailVO> detailList = orderService.getOrder_DetailVO(order_id);

			if (detailList != null && !detailList.isEmpty()) {
				model.addAttribute("detailList", detailList);
				System.out.println("상세 내역: " + detailList);
			} else {
				System.out.println("상세 내역 없음: " + detailList);
			}

			return "MyOrderDetail"; // MyOrderDetail.jsp
		}
		
		//주문 취소 창으로 가는것
		@RequestMapping(value = "MyOrderDelete.do")
		public String orderCanceled(@RequestParam("order_id") String Order_id, Model model) {
			OrderVO order=orderService.getOrderById(Order_id);
			ArrayList<Order_DetailVO> detail=orderService.getOrder_DetailVO(Order_id);
			model.addAttribute("order", order);
			model.addAttribute("detail", detail);
			return "MyOrderCancel";
			
		}
		 //주문취소(일단 카카오페이 연동이 안되서 순수 주문 취소만)
			@RequestMapping(value = "MyOrderDelete2.do")
			public String orderCancel(OrderVO
		 deletevo, Model model,HttpSession session) {
			String user_id = (String) session.getAttribute("user_id");
			deletevo.setUser_id(user_id);
		 orderService.deleteOrder(deletevo);
		 
		 return "redirect:/MytoOrderHistory.do";
		 
		 }
		// 해당하는 리뷰 등록 창으로 가는것
		@RequestMapping(value = "MyReviewWrite.do")
		public String MyReviewWrite(@RequestParam("order_id") String order_id, Model model) {
			model.addAttribute("order_id", order_id);
			return "MyReviewInsertForm";
		}
		// 마이페이지로 이동
		@RequestMapping(value = "Mypage.do")
		public String goMypage() {
			return "Mypage";
		}

		// ----------------------- 이 아래, 김동주 작성
		@RequestMapping(value = "allOrderList.do", method = { RequestMethod.POST, RequestMethod.GET })
		public String AllOrderList(HttpSession session, Model model) {

			// 여기에 해당되는 DAO, Mapper.xml에는 전부 넣었다고 보면 됩니다. VO는 일체 건드리지 않음
			// 단, service나 serviceimpl의 경우에는 똑같이 그냥 orderservice와 그 구현체로 향하게 했습니다.

			ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");

			if (manager == null) { // 매니저 로그인 섹션 확인
				return "redirect:/MyLogin.do";
			}

			// 매니저가 관리중인 회사 목록
			List<Company1VO> companyVOs = orderService.getCompany(manager.getManager_id());
			

			List<Map<String, Object>> combinedList = new ArrayList<>(); // 하나로 묶어버리기

			for (Company1VO companyVO : companyVOs) {// 회사가 팔고 있는 제품의 목록
				String companyName = companyVO.getCompany_Name();

				List<ProductVO> products = orderService.getProducts(companyName);

				for (ProductVO pro : products) { // 제품의 목록에 대응되는 상세한 주문 목록
					List<Order_DetailVO> orderDetails = orderService.getOrderDetailsByProductId(pro.getProduct_id());

					for (Order_DetailVO ord : orderDetails) {// 주문 목록에 대응되는 주문자 아이디 확인
						List<OrderVO> orders = orderService.getOrderByOrderId(ord.getOrder_id());

						for (OrderVO ors : orders) { // 주문자 신상 확인
							List<UserVO> userinfos = orderService.getUserInfoByUserId(ors.getUser_id());

							for (UserVO userinfo : userinfos) { // 주문자 신상까지 정리해놓고 이를 단번에 목록으로 정리하는 과정

								Map<String, Object> item = new HashMap<>();
								item.put("companyName", companyName); // 회사명
								item.put("productName", pro.getProduct_name()); // 제품명
								item.put("productCount", ord.getDetail_count()); // 구입한 제품수량
								item.put("orderDate", ors.getOrder_date()); // 주문일
								item.put("totalPrice", ors.getTotal_price()); // 총금액
								item.put("userName", userinfo.getName()); // 주문자

								// ,로 구분된 주소를 분리 및 재조립
								String address = userinfo.getAddress();
								String[] address_parts = address.split("%\\$");

								String postcode = address_parts[0];// 우편번호
								String road_addr = address_parts[1];// 도로명 주소
								String addr = address_parts[2];// 지번 주소
								String sub_addr = address_parts[3];// 상세주소
								String extra_addr = address_parts[4];// 참고항목

								// 아래는 혹시나 있을 null에 대한 처리과정
								if (sub_addr == null) { // 상세 주소가 null인 경우, ""으로 처리
									sub_addr = "";
								}
								if (extra_addr == null) {// 참고 항목이 null인 경우, ""으로 처리
									extra_addr = "";
								}

								// 각각 도로명 주소와 지번 주소로 결합함
								String road_fulladdress = "[" + postcode + "] " + road_addr + " " + sub_addr + " "
										+ extra_addr;
								String normal_fulladdress = "[" + postcode + "] " + addr + " " + sub_addr + " "
										+ extra_addr;

								item.put("road_fulladdress", road_fulladdress);// 도로명 주소 full
								item.put("normal_fulladdress", normal_fulladdress);// 일반 지번주소 full

								combinedList.add(item);// 정리된 모든 목록을 컴바인 하여 정리할 것
							}
						}
					}
				}
			}

			return "AllOrderList";// AllOrderList.jsp로 향하게 됨
		}

		// -----김동주 2차 추가, 배송관리 관련
		@RequestMapping(value = "deliveryList.do", method = { RequestMethod.POST, RequestMethod.GET })
		public String deliveryList(HttpSession session, Model model) {

			ManagerVO manager = (ManagerVO) session.getAttribute("managerVO");

			if (manager == null) {// 매니저 로그인 섹션 확인
				return "redirect:/MyLogin.do";
			}

			// 매니저가 관리 중인 회사 목록 조회
			List<Company1VO> companyVOs = orderService.getCompany(manager.getManager_id());
			
			
			// 모든 정보를 통합한 리스트
			List<Map<String, Object>> combinedList = new ArrayList<>();

			for (Company1VO companyVO : companyVOs) {// 회사가 팔고 있는 제품의 목록
				String companyId = companyVO.getCompany_Id();
				String companyName = companyVO.getCompany_Name();
				// 해당 회사의 상품 목록 조회
				List<ProductVO> products = orderService.getProducts(companyName);

				for (ProductVO pro : products) {
					// 상품에 해당하는 주문 상세 조회
					List<Order_DetailVO> orderDetails = orderService.getOrderDetailsByProductId(pro.getProduct_id());

					for (Order_DetailVO ord : orderDetails) {
						// 주문 상세에 연결된 주문 정보 조회
						List<OrderVO> orders = orderService.getOrderByOrderId(ord.getOrder_id());

						for (OrderVO ors : orders) {
							// 주문한 유저 정보 조회
							List<UserVO> userinfos = orderService.getUserInfoByUserId(ors.getUser_id());

							for (UserVO userinfo : userinfos) {
								// 주문 ID를 포함시켜야 JSP에서 상태 변경 시 참조 가능함
								Map<String, Object> item = new HashMap<>();
								item.put("orderId", ord.getOrder_id()); // 추가된 항목: 주문 번호 전송을 위함
								item.put("productName", pro.getProduct_name());
								item.put("productCount", ord.getDetail_count());
								item.put("orderDate", ors.getOrder_date());
								item.put("totalPrice", ors.getTotal_price());
								item.put("userName", userinfo.getName());
								item.put("status", ord.getDeliverStatus());

								combinedList.add(item);
							}
						}
					}
				}
			}

			// View에서 combinedList를 참조할 수 있도록 모델에 추가
			model.addAttribute("combinedList", combinedList);

			return "DeliveryManagement"; // 배송 관리 JSP로 이동
		}

		// 상태 변경 컨트롤러
		@RequestMapping(value = "updateDeliveryStatus.do", method = RequestMethod.POST)
		public String updateDeliveryStatus(HttpSession session, Model model,
				@RequestParam("orderIds") List<Long> orderIds, @RequestParam("newStatus") String newStatus) {
			if (!orderIds.isEmpty()) {
				orderService.updateDeliveryStatusBatch(orderIds, newStatus); // 한 번에 배치 업데이트
			}

			return "redirect:/deliveryList.do";
		}



}