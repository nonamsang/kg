<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>결제 완료</title>
    <style>
        body {
            font-family: 'Courier New', Courier, monospace;
            background-color: #f5f5f5;
            margin: 0;
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            gap: 50px; /* 좌우 간격 */
            padding: 50px 0;
        }
        .receipt, .history-receipt {
            width: 350px;
            background: white;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            line-height: 1.6;
        }
        .receipt h2, .history-receipt h3 {
            text-align: center;
            margin-bottom: 15px;
            font-size: 20px;
        }
        .receipt h3, .history-receipt h4 {
            font-size: 16px;
            margin-bottom: 10px;
            border-bottom: 1px dashed #333;
            padding-bottom: 5px;
        }
        .divider {
            border-top: 1px dashed #333;
            margin: 15px 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }
        table th, table td {
            padding: 5px 0;
            text-align: left;
        }
        table th:nth-child(2), table td:nth-child(2) {
            text-align: center;
        }
        table th:nth-child(3), table td:nth-child(3) {
            text-align: right;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
            font-size: 13px;
        }
        .home-btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: black;
            color: white;
            text-decoration: none;
            border-radius: 3px;
            margin-top: 10px;
            font-size: 14px;
        }
        .home-btn:hover {
            background-color: #333;
        }
        .cut-line {
            border-top: 2px dotted #333;
            margin: 20px 0 10px 0;
        }
        /* 오른쪽 주문 내역 스크롤 */
        .history-list {
            max-height: 600px;
            overflow-y: auto;
        }
        .history-receipt {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/include/header.jsp" />

    <div class="container">

        <!-- 현재 주문 -->
        <div class="receipt">
            <h2>결제 완료</h2>

            <h3>주문 정보</h3>
            <p>주문번호: ${order.order_id}</p>
            <p>결제 금액: ${order.total_price}원</p>
            <p>주문 날짜: ${order.order_date}</p>

            <div class="divider"></div>

            <h3>주문 상품</h3>
            <table>
                <thead>
                    <tr>
                        <th>상품명</th>
                        <th>수량</th>
                        <th>금액</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="detail" items="${orderDetails}">
                        <tr>
                            <td>${detail.product_id}</td>
                            <td>${detail.detail_count}개</td>
                            <td>${detail.detail_price}원</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="cut-line"></div>

            <div class="footer">
                <p>MOODSHOP을 이용해주셔서 감사합니다.</p>
                <a href="MainMoodShop.do" class="home-btn">홈으로 가기</a>
            </div>
        </div>

        <!-- 지난 주문 내역 -->
        <div class="history-list">
            <c:forEach var="pastOrder" items="${pastOrders}">
                <div class="history-receipt">
                    <h3>주문 번호: ${pastOrder.order_id}</h3>

                    <h4>상품 목록</h4>
                    <table>
                        <thead>
                            <tr>
                                <th>상품명</th>
                                <th>수량</th>
                                <th>금액</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="detail" items="${pastOrder.orderDetails}">
                                <tr>
                                    <td>${detail.product_id}</td>
                                    <td>${detail.detail_count}개</td>
                                    <td>${detail.detail_price}원</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="cut-line"></div>
                    <p style="text-align: center;">주문일: ${pastOrder.order_date}</p>
                </div>
            </c:forEach>
        </div>

    </div>

    <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
