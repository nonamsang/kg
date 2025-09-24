<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>주문 취소 안내 페이지</title>
</head>
<body>
  <h2 align="center">주문취소</h2>
  <hr>

  <div>
    <p>주문을 취소 하시겠습니까? (주문 취소를 누를시 결제하신 수단으로 환불이 됩니다.)</p>
    <p>환불 요청까지는 최대 3일까지 걸릴 수 있는 점 양해 부탁 드립니다.</p>
    <hr>
    <p>주문 아이디: ${order.order_id}</p>
    <p>주문 날짜: ${order.order_date}</p>
    <c:forEach var="details" items="${detail}">
      <p>상품 : ${details.product_id}</p>
      <p>옵션 : ${details.option_id}</p>
      <p>수량 : ${details.detail_count}</p>
      <p>상품 가격 :
        <fmt:formatNumber value="${details.detail_price}" type="number" groupingUsed="true" />
      </p>
    </c:forEach>
    <p>주문 가격: <fmt:formatNumber value="${order.total_price}" type="number" groupingUsed="true" /></p>
  </div>

  <div>
    <button type="button" id="orderDelete">주문 취소</button>
    <button type="button" id="orderNoDelete">주문 유지</button>
  </div>

  <script>
    const orderId = "${order.order_id}";
    console.log("order_id = ", orderId);

    document.getElementById("orderDelete").addEventListener("click", function () {
      fetch("MyOrderDelete2.do", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "order_id=" + encodeURIComponent(orderId)
      })
        .then(response => {
          if (!response.ok) {
            throw new Error("서버 오류 발생");
          }
          return response.text();
        })
        .then(data => {
          alert("주문이 성공적으로 취소되었습니다.");
          window.location.href = "MyPage.do";
        })
        .catch(error => {
          console.error("오류 발생:", error);
          alert("주문 취소 중 문제가 발생했습니다.");
        });
    });

    document.getElementById("orderNoDelete").addEventListener("click", function () {
    	alert("마이페이지로 이동합니다.")
      location.href = "MyPage.do";
    });
  </script>
</body>
</html>
