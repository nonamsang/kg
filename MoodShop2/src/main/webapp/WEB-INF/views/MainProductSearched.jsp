<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <title>검색 결과</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
  
  <!-- Sidebar CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css" />

  <style>
    body {
      margin: 0;
      padding-top: 120px;
      font-family: 'Arial', sans-serif;
      background: linear-gradient(to bottom, #ffffff, #f9f9f9);
    }

    .category-desc {
      text-align: center;
      font-size: 30px;
      font-weight: bold;
      margin: 30px 0 30px;
      color: #333;
    }

    .search-status {
      display: flex;
      align-items: center;
      margin: 60px 0 40px;
    }

    .search-status hr {
      flex: 1;
      border: none;
      height: 2px;
      background: linear-gradient(to right, #222, #aaa);
      box-shadow: 0 0 3px rgba(0, 0, 0, 0.4);
    }

    .search-status span {
      padding: 0 20px;
      color: #111;
      font-size: 20px;
      font-weight: bold;
      letter-spacing: 1px;
      text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
    }

    .product-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      max-width: 1200px;
      margin: 0 auto;
      gap: 30px;
      padding: 0 30px 60px;
      justify-content: center;
    }

    .product-card {
      position: relative;
      background: white;
      width: 100%;
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
      display: flex;
      flex-direction: column;
    }

    .product-card:hover {
      transform: translateY(-8px);
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
    }

    .product-card img {
      width: 100%;
      height: 220px;
      object-fit: cover;
    }

    .product-card .info {
      padding: 15px;
      text-align: left;
    }

    .product_item .info p {
		margin: 4px 0;
		font-size: 16px;
		font-weight: 500;
	}

    .product-card .info a {
      text-decoration: none;
      color: #007bff;
      font-weight: bold;
    }

    .add-btn {
      position: absolute;
      top: 10px;
      right: 10px;
      background: #fff;
      border: 1px solid #ddd;
      border-radius: 50%;
      width: 30px;
      height: 30px;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 18px;
      cursor: pointer;
      transition: background 0.3s;
    }

    .add-btn:hover {
      background: #f0f0f0;
    }

    #product_detail_sidebar {
      position: fixed;
      right: 0;
      top: 180px;
      width: 400px;
      height: calc(100% - 120px);
      background: #fff;
      box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
      overflow-y: auto;
      transform: translateX(100%);
      transition: transform 0.3s;
      padding: 20px;
      z-index: 9999;
    }

    #product_detail_sidebar.active {
      transform: translateX(0);
    }

    #product_detail_sidebar .close-btn {
      position: absolute;
      top: 10px;
      right: 27px;
      font-size: 20px;
      font-weight: bold;
      color: #333;
      cursor: pointer;
      background: none;
      border: none;
      transition: transform 0.3s ease;
    }

    #product_detail_sidebar .close-btn:hover {
      transform: rotate(90deg);
    }
  </style>
</head>

<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />
<jsp:include page="/WEB-INF/views/common/sidebar.jsp" />

<!-- 🔍 검색 상태별 문구 -->
<div class="search-status">
  <hr>
  <span>
    <c:choose>
      <c:when test="${not empty keyword and not empty productList}">
        검색 결과: <span style="color:#007bff;">'${keyword}'</span>
      </c:when>
      <c:when test="${not empty keyword and empty productList}">
        검색 결과가 없습니다.
      </c:when>
      <c:otherwise>
        모든 상품 목록입니다.
      </c:otherwise>
    </c:choose>
  </span>
  <hr>
</div>

<div class="product-grid">
  <c:forEach var="product" items="${productList}">
    <div class="product-card">
      <div class="add-btn">+</div>
      <a href="MainProductName.do?product_id=${product.product_id}">
        <img src="${pageContext.request.contextPath}/resources/img/${product.product_image}" alt="상품 이미지">
      </a>
      <div class="info">
        <p>${product.product_name}</p>
		<p>${product.product_price}원</p>
      </div>
    </div>
  </c:forEach>
</div>

<!-- 장바구니 사이드바 -->
<div id="product_detail_sidebar">
  <button class="close-btn" id="closeSidebarBtn">✖</button>
  <h4>임시 장바구니</h4>
  <ul id="cart_list" class="list-group mb-3"></ul>
  <button id="final_add_to_cart" class="btn btn-success w-100">장바구니 담기</button>
</div>

<script>
var cart = [];
var contextPath = "${pageContext.request.contextPath}";

$(document).on('click', '.add-btn', function () {
    var product_id = $(this).siblings('a').attr('href').split('=')[1];
    var product_name = $(this).siblings('.info').find('p').eq(0).text();
    var product_price = $(this).siblings('.info').find('p').eq(1).text().replace('가격: ', '').replace('원', '').replace(',', '').trim();
    var product_image_full = $(this).siblings('a').find('img').attr('src');
    var product_image = product_image_full.replace(contextPath + '/resources/img/', '');

    var existingProduct = cart.find(item => item.product_id === product_id);

    if (!existingProduct) {
        $.ajax({
            url: contextPath + "/getProductOptions.do",
            type: "GET",
            data: { product_id: product_id },
            success: function (option_list) {
                cart.push({ product_id, product_name, product_price, product_image, option_id: null, option_list });
                renderCart();
                $('#product_detail_sidebar').addClass('active');
            },
            error: function () {
                alert('옵션 조회에 실패했습니다.');
            }
        });
    } else {
        alert('이미 장바구니에 담긴 상품입니다.');
    }
});

function renderCart() {
    var cartListHtml = '';
    cart.forEach(function (item, index) {
        var optionHtml = '<option value="">옵션 선택</option>';
        item.option_list.forEach(function (option) {
            optionHtml += `<option value="${option.option_id}">${option.option_color}</option>`;
        });

        cartListHtml += `
            <li class="list-group-item">
                <div style="display: flex; align-items: center;">
                    <img src="` + contextPath + `/resources/img/${item.product_image}" style="width: 60px; height: 60px; object-fit: cover; margin-right: 10px;" />
                    <div>
                        <strong>${item.product_name}</strong> (${item.product_price}원)
                        <br/>
                        <label>옵션 선택:
                            <select onchange="selectOption(this, '${item.product_id}')">
                                ${optionHtml}
                            </select>
                        </label>
                        <div id="selected_option_${item.product_id}" style="margin-top: 5px; font-size: 14px; color: #555;"></div>
                        <button class="btn btn-sm btn-danger mt-2" onclick="removeFromCart('${item.product_id}', ${index})">X</button>
                    </div>
                </div>
            </li>
        `;
    });
    $('#cart_list').html(cartListHtml);
}

$('#closeSidebarBtn').click(function () {
    $('#product_detail_sidebar').removeClass('active');
});

$(document).mouseup(function (e) {
    var sidebar = $("#product_detail_sidebar");
    if (!sidebar.is(e.target) && sidebar.has(e.target).length === 0) {
        sidebar.removeClass('active');
    }
});

function selectOption(selectBox, product_id) {
    var option_id = selectBox.value;
    var target = cart.find(item => item.product_id === product_id);
    if (target) {
        target.option_id = option_id;
    }
}

function removeFromCart(product_id, index) {
    cart.splice(index, 1);
    renderCart();
}

$('#final_add_to_cart').click(function () {
    if (cart.length === 0) {
        alert('장바구니가 비어 있습니다.');
        return;
    }

    for (var i = 0; i < cart.length; i++) {
        if (!cart[i].option_id) {
            alert('모든 상품의 옵션을 선택해 주세요.');
            return;
        }
    }

    $.ajax({
        url: contextPath + "/addCartList.do",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(cart),
        success: function () {
            alert('장바구니에 담겼습니다.');
            cart = [];
            renderCart();
            $('#product_detail_sidebar').removeClass('active');
        },
        error: function (xhr) {
            alert('로그인 후 이용해 주세요.\n서버 메시지: ' + xhr.responseText);
            location.href = contextPath + "/MyPage.do";
        }
    });
});

function addRecentView(product_id) {
    $.ajax({
        url: contextPath + "/addRecentView.do",
        type: "POST",
        data: { product_id: product_id },
        success: function () { loadRecentList(); }
    });
}

function loadRecentList() {
    $.ajax({
        url: contextPath + "/recentViewSidebar.do",
        type: "GET",
        cache: false,
        success: function (data) { $("#recentListSidebar").html(data); }
    });
}

$(document).ready(function () {
    var product_id = "${product.product_id}";
    if (product_id) addRecentView(product_id);
});

window.addEventListener('pageshow', function (event) { loadRecentList(); });
</script>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />

</body>
</html>
