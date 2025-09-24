<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>배송 관리</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f0f0f0 0%, #ffffff 100%);
            padding: 40px 10%;
            color: #333;
            margin: 0;
        }

        a {
            display: inline-block;
            margin-bottom: 25px;
            font-weight: 600;
            color: #4CAF50;
            text-decoration: none;
            transition: color 0.3s ease;
        }
        a:hover {
            color: #388E3C;
        }

        form {
            background-color: white;
            padding: 20px 25px;
            border-radius: 10px;
            box-shadow: 0 3px 15px rgb(0 0 0 / 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 16px;
        }
        th, td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
            vertical-align: middle;
        }
        thead th {
            background-color: #4CAF50;
            color: white;
            font-weight: 700;
            letter-spacing: 0.05em;
        }
        tbody tr:hover {
            background-color: #e8f5e9;
        }

        input[type="checkbox"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
        }

        select {
            padding: 10px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 15px;
            margin: 10px 0;
        }

        button {
            margin-top: 20px;
            background-color: #4CAF50;
            color: white;
            font-size: 18px;
            padding: 14px 45px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 600;
            transition: background-color 0.3s ease;
            display: block;
            width: 100%;
            max-width: 300px;
            margin-left: auto;
            margin-right: auto;
        }
        button:hover {
            background-color: #388E3C;
        }

        @media screen and (max-width: 600px) {
            body {
                padding: 20px 5%;
                font-size: 14px;
            }
            button {
                padding: 12px 25px;
                font-size: 16px;
            }
        }
    </style>
    <script>
        function toggleAll(source) {
            const checkboxes = document.querySelectorAll(".rowCheck");
            checkboxes.forEach(checkbox => {
                checkbox.checked = source.checked;
            });
        }

        function confirmAndSubmit() {
            const selectedOrders = Array.from(document.querySelectorAll(".rowCheck:checked"));
            const newStatus = document.getElementById("statusSelect").value;

            if (selectedOrders.length === 0) {
                alert("주문을 하나 이상 선택하세요.");
                return false;
            }

            if (!newStatus) {
                alert("변경할 배송 상태를 선택하세요.");
                return false;
            }

            if (newStatus === "CANCELED") {
                const invalid = selectedOrders.some(row => {
                    const rowIndex = row.closest("tr").dataset.index;
                    const currentStatus = document.querySelector(`.data-row[data-index="${rowIndex}"] td:last-child`).textContent.trim();
                    return currentStatus !== "결제 완료";
                });

                if (invalid) {
                    alert("주문 취소는 '결제 완료' 상태에서만 가능합니다.");
                    return false;
                }
            }

            document.getElementById("deliveryForm").submit();
        }

        function onFilterStatusChange() {
            const selectedStatus = document.getElementById("filterStatus").value;
            const rows = document.querySelectorAll(".data-row");

            rows.forEach(row => {
                const statusCell = row.querySelector("td:nth-child(8)").textContent.trim();
                row.style.display = (!selectedStatus || statusCell === selectedStatus) ? "" : "none";
            });
        }
    </script>
</head>
<body>

<a href="AdminMainPage.do">← 관리자 메인 페이지로 돌아가기</a>

<form id="deliveryForm" action="updateDeliveryStatus.do" method="post">
    <label>배송 상태 필터:</label>
    <select id="filterStatus" onchange="onFilterStatusChange()">
        <option value="">-- 필터할 상태 선택 --</option>
        <option value="ITEM_READY">상품 준비중</option>
        <option value="READY_FOR_SHIPMENT">배송 준비 완료</option>
        <option value="SHIPPED">배송 중</option>
        <option value="DELIVERED">배송 완료</option>
        <option value="CONEIRMED">구매 확정</option>
        <option value="CANCELED">주문 취소</option>
        <option value="REFUND_REQUESTED">환불 요청 받음</option>
        <option value="REFUND_PENDING">환불 처리 중</option>
        <option value="REFUNDED">환불 완료</option>
    </select>

    <table>
        <thead>
            <tr>
                <th><input type="checkbox" id="selectAll" onclick="toggleAll(this)"></th>
                <th>주문 번호</th>
                <th>주문 날짜|시간</th>
                <th>주문 상품명</th>
                <th>주문 수량</th>
                <th>주문자</th>
                <th>총 주문금액</th>
                <th>배송 상태</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="comL" items="${combinedList}" varStatus="status">
                <tr class="data-row" data-index="${status.index}">
                    <td><input type="checkbox" class="rowCheck" name="orderIds" value="${comL.orderId}"></td>
                    <td>${comL.orderId}</td>
                    <td><fmt:formatDate value="${comL.orderDate}" pattern="yyyy-MM-dd(EEEE)||HH:mm:ss" /></td>
                    <td>${comL.productName}</td>
                    <td>${comL.productCount}</td>
                    <td>${comL.userName}</td>
                    <td><fmt:formatNumber value="${comL.totalPrice}" type="number" groupingUsed="true" />원</td>
                    <td>${comL.status}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <label for="statusSelect">배송 상태 변경:</label>
    <select name="newStatus" id="statusSelect">
        <option value="">--선택--</option>
        <option value="ITEM_READY">상품 준비중</option>
        <option value="READY_FOR_SHIPMENT">배송 준비 완료</option>
        <option value="SHIPPED">배송 중</option>
        <option value="DELIVERED">배송 완료</option>
        <option value="CONEIRMED">구매 확정</option>
        <option value="CANCELED">주문 취소</option>
        <option value="REFUND_REQUESTED">환불 요청 받음</option>
        <option value="REFUND_PENDING">환불 처리 중</option>
        <option value="REFUNDED">환불 완료</option>
    </select>

    <button type="button" onclick="confirmAndSubmit()">변경 적용</button>
</form>

</body>
</html>
