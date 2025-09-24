<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>MOODSHOP</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Sidebar CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css" />

<!-- Swiper CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css" />

<style>
body {
	font-family: 'Arial', sans-serif;
	padding-top: 120px;
	background: #f9f9f9;
	margin: 0;
}

.container-fluid {
	padding: 0;
	margin: 0 auto;
	max-width: 1400px;
}

.product_container {
	max-width: 1300px;
	margin: 0 auto;
	display: grid;
	grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
	gap: 25px;
	padding: 0 20px;
}

.product_item {
	position: relative;
	background: #fff;
	border-radius: 10px;
	overflow: hidden;
	cursor: pointer;
	transition: transform 0.3s, box-shadow 0.3s;
	display: flex;
	flex-direction: column;
}

.product_item:hover {
	transform: translateY(-5px);
	box-shadow: 0 12px 20px rgba(0, 0, 0, 0.15);
}

.product_item img {
	width: 100%;
	height: 200px;
	object-fit: cover;
}

.product_item .info {
	padding: 15px;
	text-align: center;
}

.product_item .info p {
	margin: 4px 0;
	font-size: 16px;
	font-weight: 500;
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
	z-index: 9999; /* ✅ 맨 앞으로 올리기 */
}

#product_detail_sidebar.active {
	transform: translateX(0);
}

/* 닫기 버튼 */
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

/* 🔥 감정별 섹션 타이틀 디자인 */
.emotion_section {
	margin: 60px 0;
}

.emotion_section h3 {
	text-align: center;
	font-size: 28px;
	margin-bottom: 30px;
	font-weight: bold;
	color: #333;
	position: relative;
}

.emotion_section h3::after {
	content: '';
	display: block;
	width: 50px;
	height: 3px;
	background: #1a237e;
	margin: 10px auto 0;
	border-radius: 3px;
}

/* 🔥 Swiper 스타일 + 흘러가는 그라데이션 적용 */
.swiper {
	background: linear-gradient(270deg, #fceabb, #f8b500);
	background-size: 400% 400%;
	animation: gradientMove 8s ease infinite;
}

@
keyframes gradientMove { 0% {
	background-position: 0% 50%;
}

50








%
{
background-position








:








100






%
50






%;
}
100








%
{
background-position








:








0






%
50






%;
}
}
.swiper-slide {
	background: none;
	display: flex;
	justify-content: center;
	align-items: center;
}

.swiper-slide img {
	width: auto;
	max-width: 100%;
	height: 400px;
	object-fit: contain;
}

.swiper-button-next, .swiper-button-prev {
	color: rgba(0, 0, 0, 0.7);
}

.swiper-pagination-bullet {
	background: #1a237e;
	opacity: 0.5;
}

.swiper-pagination-bullet-active {
	background: #ff7f50;
	opacity: 1;
}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<jsp:include page="/WEB-INF/views/common/sidebar.jsp" />

	<div class="container-fluid">
		<!-- Swiper -->
		<div class="swiper mySwiper">
			<div class="swiper-wrapper">
				<c:forEach var="img" items="${bannerList}" varStatus="status">
					<div class="swiper-slide">
						<a href="eventClick_serve.do?event_id=${img.event_id}"> <img src="${pageContext.request.contextPath}/resources/img/event_img/${img.event_image_source}" alt="banner ${status.index}">
						</a>
					</div>
				</c:forEach>
			</div>

			<!-- Swiper Buttons -->
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>

			<!-- Swiper Pagination -->
			<div class="swiper-pagination"></div>
		</div>
	</div>

<c:set var="product_mood_list" value="기쁨,슬픔,분노,사랑,불안,평온" />
<c:forTokens var="mood" items="${product_mood_list}" delims=",">
    <div class="emotion_section ${mood}">
        <h3>${mood}</h3>
        <div class="product_container">
            <c:forEach var="product" items="${productList}">
                <c:if test="${product.product_mood eq mood}">
                    <div class="product_item">

                        <!-- ✅ data 속성으로 변경 -->
                        <div class="add-btn" 
                             data-product-id="${product.product_id}"
                             data-product-name="${product.product_name}"
                             data-product-price="${product.product_price}"
                             data-product-image="${product.product_image}">+</div>

                        <a href="MainProductName.do?product_id=${product.product_id}">
                            <img src="${pageContext.request.contextPath}/resources/img/${product.product_image}" alt="${product.product_name}">
                        </a>

                        <div class="info">
                            <p>${product.product_name}</p>
                            <p>${product.product_price}원</p>
                        </div>

                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</c:forTokens>




	<div id="product_detail_sidebar">
		<button class="close-btn" id="closeSidebarBtn">✖</button>
		<!-- 닫기 버튼 -->
		<h4>임시 장바구니</h4>
		<ul id="cart_list" class="list-group mb-3"></ul>
		<button id="final_add_to_cart" class="btn btn-success w-100">장바구니 담기</button>
	</div>

	<script>
//🛒 장바구니 배열을 객체로 선언
var cart = {};
var contextPath = '${pageContext.request.contextPath}';

$(document).on('click', '.add-btn', function () {
    // data 속성에서 바로 가져오기
    var product_id = $(this).data('product-id');
    var product_name = $(this).data('product-name');
    var product_price = $(this).data('product-price');
    var product_image = $(this).data('product-image');

    console.log('🍚 추출된 상품 정보:');
    console.log('상품 ID:', product_id);
    console.log('상품명:', product_name);
    console.log('상품 가격:', product_price);
    console.log('상품 이미지:', product_image);

    if (!cart[product_id]) {
        $.ajax({
            url: contextPath + "/getProductOptions.do",
            type: "GET",
            data: { product_id: product_id },
            success: function (option_list) {
                console.log('서버 응답:', option_list);

                cart[product_id] = {
                    product_id: product_id,
                    product_name: product_name,
                    product_price: product_price,
                    product_image: product_image,
                    option_id: null,
                    option_list: option_list
                };

                renderCart(cart);
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



function renderCart(cartList) {
    console.log('🍚 현재 장바구니 상황:');

    var cartArray = Object.values(cartList); // 🔥 객체를 배열로 변환

    cartArray.forEach(function (item, index) {
        console.log(`-------------------------------`);
        console.log(`상품 ${index + 1}`);
        console.log(`상품 ID: ${item.product_id}`);
        console.log(`상품명: ${item.product_name}`);
        console.log(`가격: ${item.product_price}`);
        console.log(`이미지: ${item.product_image}`);
        console.log(`선택한 옵션 ID: ${item.option_id}`);
        console.log(`옵션 목록:`);
        item.option_list.forEach(function (option, optIndex) {
            console.log(`  옵션 ${optIndex + 1}: ${option.option_id} / ${option.option_color}`);
        });
    });
    console.log('===============================');

    // 장바구니 렌더링
    var cartListHtml = '';

    cartArray.forEach(function (item, index) {
        var optionHtml = '<option value="">옵션 선택</option>';
        item.option_list.forEach(function (option) {
            optionHtml += `<option value="${option.option_id}">${option.option_color}</option>`;
        });

        cartListHtml += `
            <li class="list-group-item">
                <div style="display: flex; align-items: center;">
                    <img src="` + contextPath + `/resources/img/${item.product_image}" 
                         style="width: 60px; height: 60px; object-fit: cover; margin-right: 10px;" 
                         onerror="this.onerror=null; this.src='` + contextPath + `/resources/img/default.jpg';" />
                    <div>
                        <strong>${item.product_name}</strong> (${item.product_price}원)
                        <br/>
                        <label>옵션 선택:
                            <select onchange="selectOption(this, '${item.product_id}')">
                                ${optionHtml}
                            </select>
                        </label>
                        <br/>
                        <button onclick="removeFromCart('${item.product_id}')" class="btn btn-danger btn-sm mt-2">삭제</button>
                    </div>
                </div>
            </li>
        `;
    });

    document.getElementById('cart_list').innerHTML = cartListHtml;
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

function selectOption(selectElement, productId) {
    var selectedOption = selectElement.value;
    console.log('선택한 옵션 ID:', selectedOption, '상품 ID:', productId);

    if (cart[productId]) {
        cart[productId].option_id = selectedOption;
    }
}

function removeFromCart(product_id) {
    delete cart[product_id];
    renderCart(cart);
}

$('#final_add_to_cart').click(function () {
    if (Object.keys(cart).length === 0) {
        alert('장바구니가 비어 있습니다.');
        return;
    }

    for (var key in cart) {
        if (!cart[key].option_id) {
            alert('모든 상품의 옵션을 선택해 주세요.');
            return;
        }
    }

    $.ajax({
        url: contextPath + "/addCartList.do",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(Object.values(cart)),
        success: function () {
            alert('장바구니에 담겼습니다.');
            cart = {};
            renderCart(cart);
            $('#product_detail_sidebar').removeClass('active');
        },
        error: function (xhr) {
            alert('로그인 후 이용해 주세요.\n서버 메시지: ' + xhr.responseText);
            location.href = contextPath + "/MyPage.do";
        }
    });
});



</script>

	<!-- Swiper JS -->
	<script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script>
	<script>
var swiper = new Swiper(".mySwiper", {
	slidesPerView: 1,
	centeredSlides: true,
	spaceBetween: 30,
	loop: true,
	loopAdditionalSlides: 2,
	autoplay: {
		delay: 3000,
		disableOnInteraction: false,
	},
	pagination: {
		el: ".swiper-pagination",
		clickable: true,
	},
	navigation: {
		nextEl: ".swiper-button-next",
		prevEl: ".swiper-button-prev",
	},
});
</script>

	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
