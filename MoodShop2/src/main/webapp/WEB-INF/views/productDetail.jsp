<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #fff;
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
        }

        .product-detail-container {
            max-width: 1200px;
            margin: 80px auto;
            padding: 0 20px;
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            gap: 80px;
        }

        .product-image {
            flex: 1 1 500px;
        }

        .product-image img {
            width: 100%;
            border-radius: 0;
            object-fit: cover;
        }

        .product-info {
            flex: 1 1 500px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            gap: 30px;
        }

        .product-title {
            font-size: 48px;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .product-price {
            font-size: 36px;
            color: #ff4d4f;
            font-weight: 600;
        }

        .product-description {
            font-size: 20px;
            color: #666;
            line-height: 1.8;
        }

        .product-option label,
        .product-quantity label {
            font-size: 18px;
            margin-bottom: 5px;
        }

        .product-option select,
        .product-quantity input {
            width: 250px;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .product-buttons {
            display: flex;
            gap: 20px;
            margin-top: 20px;
        }

        .product-buttons button {
            padding: 14px 0;
            font-size: 18px;
            flex: 1;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }

        .btn-cart {
            background-color: #000;
            color: #fff;
        }

        .btn-cart:hover {
            background-color: #333;
        }

        .btn-wishlist {
            background-color: #f5f5f5;
            color: #000;
            border: 1px solid #ddd;
        }

        .btn-wishlist:hover {
            background-color: #eaeaea;
        }

        .btn-buy {
            background-color: #ff4d4f;
            color: #fff;
        }

        .btn-buy:hover {
            background-color: #e60023;
        }

        .review-score {
            font-size: 20px;
            color: gold;
            font-weight: bold;
        }

        .product-code {
            font-size: 14px;
            color: #aaa;
        }

        .back-button {
            display: block;
            width: fit-content;
            margin: 100px auto 0 auto;
            padding: 14px 50px;
            background-color: #000;
            color: #fff;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s;
        }

        .back-button:hover {
            background-color: #333;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="product-detail-container">

    <div class="product-image">
        <img src="/resources/img/${product.product_image}" alt="상품 이미지">
    </div>

    <div class="product-info">
        <div class="product-title">${product.product_name}</div>
        <div class="product-price">${product.product_price}원</div>

        <div class="product-description">${product.product_description}</div>

        <div class="product-option">
            <label for="colorSelect">색상 선택</label><br>
            <select id="colorSelect">
                <option>-- 색상 선택 --</option>
                <c:forEach var="option" items="${productOptionList}">
                    <option>${option.option_color}</option>
                </c:forEach>
            </select>
        </div>

        <div class="product-quantity">
            <label for="quantity">주문 수량</label><br>
            <input type="number" id="quantity" value="1" min="1">
        </div>

        <div class="product-buttons">
            <button class="btn-cart">장바구니 담기</button>
            <button class="btn-wishlist">♡ 찜하기</button>
            <button class="btn-buy">결제하기</button>
        </div>

        <div class="review-score">★ 리뷰 보기 5.0</div>
        <div class="product-code">상품 코드: ${product.product_id}</div>
    </div>

</div>

<a href="apiProduct.do" class="back-button">목록으로 돌아가기</a>

</body>
</html>
