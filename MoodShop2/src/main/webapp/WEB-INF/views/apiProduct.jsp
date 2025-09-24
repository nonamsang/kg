<!-- /WEB-INF/views/apiProduct.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 페이지</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .product-card {
            border: none;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            transition: transform 0.4s ease, box-shadow 0.4s ease;
            cursor: pointer;
            background-color: #fff;
        }

        .product-card:hover {
            transform: scale(1.05);
            box-shadow: 0 12px 24px rgba(0,0,0,0.2);
        }

        .product-card img {
            width: 100%;
            height: 220px;
            object-fit: cover;
        }

        .product-card-body {
            padding: 15px;
        }

        .product-card-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .product-card-text {
            font-size: 14px;
            color: #555;
        }

        .product-card-price {
            font-size: 16px;
            color: #e60023;
            font-weight: bold;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">상품 목록</h2>

    <div class="text-center mb-4">
        <input type="text" id="searchInput" class="form-control d-inline-block w-25" placeholder="상품명을 입력하세요" />
    </div>

    <div class="text-center mb-4">
        <button class="btn btn-outline-primary m-1 category-btn" data-category="all">전체</button>
        <button class="btn btn-outline-primary m-1 category-btn" data-category="기쁨">기쁨</button>
        <button class="btn btn-outline-primary m-1 category-btn" data-category="분노">분노</button>
    </div>

    <div id="productList" class="d-flex flex-wrap justify-content-start"></div>
</div>

<script>
let allData = [];

$(document).ready(function() {
    $.ajax({
        url: 'getProductList.do', // 내부 DB 연동 URL
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            allData = data;
            renderProducts(allData);
        },
        error: function() {
            alert('상품 데이터를 불러오는 데 실패했습니다.');
        }
    });
});

function renderProducts(productList) {
    let html = '';
    $.each(productList, function(index, product) {

        // 기본 데이터 세팅 (DB 기준)
        let newTitle = product.product_name;
        let newDescription = product.product_description;
        let newPrice = product.product_price + '원';
        let category = product.product_mood;

        // ✅ DB 경로를 정적 경로로 맞춰줌
        let newImage = '/resources/img/' + product.product_image;

        // 카드 생성
        html += `
            <div class="product-card m-3" style="width: 18rem;" data-category="${category}" onclick="location.href='productDetail.do?productId=${product.product_id}'">
              <img src="${newImage}" alt="상품 이미지">
              <div class="product-card-body">
                <h5 class="product-card-title">${newTitle}</h5>
                <p class="product-card-text">${newDescription}</p>
                <p class="product-card-price">${newPrice}</p>
              </div>
            </div>
        `;
    });
    $('#productList').html(html);
}

// 카테고리 필터
$(document).on('click', '.category-btn', function() {
    let selected = $(this).data('category');

    if (selected === 'all') {
        renderProducts(allData);
    } else {
        let filtered = allData.filter(product => {
            return product.product_mood === selected;
        });
        renderProducts(filtered);
    }
});

// 검색 기능
$(document).on('input', '#searchInput', function() {
    let keyword = $(this).val().toLowerCase();

    let filtered = allData.filter(product => {
        return product.product_name.toLowerCase().includes(keyword);
    });

    renderProducts(filtered);
});
</script>

</body>
</html>
