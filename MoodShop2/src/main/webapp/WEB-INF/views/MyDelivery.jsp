<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>배송조회</title>

<style>
/* ✅ 마이페이지에 맞춘 레이아웃 */
.page-wrapper {
    width: 100%;
    max-width: 800px;
    margin: 0 auto;
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-sizing: border-box;
    text-align: center;
}

h1 {
    margin-bottom: 20px;
}

.delivery-info {
    font-size: 18px;
    line-height: 1.8;
    margin-bottom: 30px;
}

.home-button {
    display: inline-block;
    padding: 10px 20px;
    background-color: #6c63ff;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    transition: background-color 0.3s;
}

.home-button:hover {
    background-color: #4a45b5;
}
</style>
</head>
<body>

<div class="page-wrapper">
    <h1>배송조회</h1>
    <hr>

    <div class="delivery-info">
        <p>주문번호: ${order.order_id}</p>
        <p>주문일자: ${order.order_date}</p>
        <p>배송상태: ${order.deliverStatus}</p>
    </div>

    <a href="MainMoodShop.do" class="home-button">홈으로 가기</a>
</div>

</body>
</html>
