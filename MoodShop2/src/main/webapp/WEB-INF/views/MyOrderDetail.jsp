<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>주문 상세 내역</title>

<style>
/* ✅ 마이페이지에 맞춘 스타일 */
.page-wrapper {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-sizing: border-box;
}

h3 {
    margin-bottom: 20px;
}

.nav-links {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.order-detail-table {
    width: 100%;
    border-collapse: collapse;
}

.order-detail-table th, .order-detail-table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
}

.order-detail-table th {
    background-color: #f8f8f8;
}

</style>
</head>
<body>

<div class="page-wrapper">

    <!-- 🔹 상단 네비게이션 -->
    <div class="nav-links">
        <a href="MytoOrderHistory.do">뒤로가기</a>
        <h3>주문 상세 내역</h3>
        <a href="MainMoodShop.do">홈으로가기</a>
    </div>

    <!-- 🔹 주문 상세 테이블 -->
    <table class="order-detail-table">
        <thead>
            <tr>
                <th>주문번호</th>
                <th>상품 ID</th>
                <th>옵션</th>
                <th>수량</th>
                <th>가격</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="detail" items="${detailList}">
                <tr>
                    <td>${detail.detail_id}</td>
                    <td>${detail.product_id}</td>
                    <td>${detail.option_id}</td>
                    <td>${detail.detail_count}</td>
                    <td><fmt:formatNumber value="${detail.detail_price}" type="currency" currencySymbol="₩" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>

</body>
</html>
