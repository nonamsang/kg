<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>주문 내역</title>
</head>
<body>
<table border="1">
	<c:forEach var="order" items="${orderlist}">
		<tr>
			<td colspan="4" align="center"><a href="MainProduct1.do">이미지</a></td>
		</tr>
		<tr>
			<td align="center"><a href="MainProduct1.do">${order["ORDER_ID"]}</a></td>
	<td align="center"><a href="MainProduct1.do">
	<fmt:formatDate value="${order['ORDER_DATE']}" pattern="yyyy-mm-dd" /></a></td>
	<td align="center">
 <a href="MainProduct1.do">
    <fmt:formatNumber value="${order['TOTAL_PRICE']}" type="number" groupingUsed="true" />
  </a>
</td>
		</tr>
		<tr>
			<td><a href="MyDelivery.do?order_id=${order['ORDER_ID']}">배송조회</a></td>
			<td><a href="MyOrderDetail.do?order_id=${order['ORDER_ID']}">주문상세</a></td>
			<td><a href="MyReviewWrite.do?order_id=${order['ORDER_ID']}">리뷰등록</a></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>
