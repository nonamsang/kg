<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>주문 내역</title>

<style>
/* ✅ 마이페이지 섹션 폭 고정 */
.page-wrapper {
    width: 100%;
    max-width: 900px;
    margin: 0 auto;
    background-color: white;
    padding: 30px;
    border-radius: 12px;
    box-sizing: border-box;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
    margin-bottom: 20px;
    text-align: center;
    font-size: 32px; /* ✅ 글자 크기 키움 */
    font-weight: bold;
}

.search-box {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    margin-bottom: 20px;
}

.search-box input[type="date"] {
    padding: 6px 12px;
    border: 1px solid #ddd;
    border-radius: 6px;
}

.search-box button {
    padding: 6px 14px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.search-box button:hover {
    background-color: #0056b3;
}

.order-table {
    width: 100%;
    border-collapse: collapse;
    border-radius: 8px;
    overflow: hidden;
}

.order-table th, .order-table td {
    border: 1px solid #ddd;
    padding: 12px;
    text-align: center;
}

.order-table th {
    background-color: #f1f1f1;
    font-weight: bold;
}

.order-buttons a {
    display: inline-block;
    margin: 2px 4px;
    padding: 6px 12px;
    font-size: 14px;
    border-radius: 6px;
    text-decoration: none;
    transition: all 0.2s ease;
}

.btn-review {
    background-color: #28a745;
    color: white;
    border: none;
}

.btn-review:hover {
    background-color: #218838;
}
</style>

<script>
function searches() {
    const between = document.getElementById("between").value;
    const and = document.getElementById("and").value;

    if (!between || !and) {
        alert("검색하시려는 시작일과 종료일을 입력해주세요");
        return;
    }

    const startDate = new Date(between);
    const endDate = new Date(and);

    if (startDate > endDate) {
        alert("시작일이 종료일보다 큽니다. 다시 입력해주세요.");
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/MyOrderSearches.do?between=" + between + "&and=" + and, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("orderlist").innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}
</script>

</head>
<body>

<div class="page-wrapper">

    <!-- 🔹 타이틀만 표시 -->
    <h2>주문 내역</h2>

    <!-- 🔹 검색 영역 -->
    <div class="search-box">
        <label for="between">시작일</label>
        <input type="date" id="between">
        <label for="and">종료일</label>
        <input type="date" id="and">
        <button type="button" onclick="searches()">검색</button>
    </div>

    <!-- 🔹 주문 내역 테이블 -->
    <div id="orderlist">
        <table class="order-table">
            <thead>
                <tr>
                    <th>주문 번호</th>
                    <th>주문 날짜</th>
                    <th>총 금액</th>
                    <th>기능</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orderlist}">
                    <tr>
                        <td>${order.order_id}</td>
                        <td>${order.order_date}</td>
                        <td><fmt:formatNumber value="${order.total_price}" type="currency" currencySymbol="₩" /></td>
                        <td class="order-buttons">
                            <a href="MyReviewWrite.do?order_id=${order.order_id}" class="btn-review">리뷰등록</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</div>

</body>
</html>
