<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="icon"
   href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png">
<link rel="stylesheet"
   href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>

<script>
    window.addEventListener('load', function() {
        console.log('페이지 강제 호출 방지 확인: openOrderModal이 강제 실행되는지 모니터링 시작');
    });
</script>

   
<style>
body {
   margin: 0;
   padding-top: 150px;
   font-family: Arial, sans-serif;
   background: #fff;
}

.container {
   max-width: 1200px;
   margin: 0 auto;
   padding: 0 20px 100px 20px; /* 아래쪽 여백 100px 추가 */
}

.product-detail {
   display: flex;
   flex-wrap: wrap;
   gap: 50px;
}

.left {
   flex: 1 1 55%;
}

.left .main-img {
   width: 100%;
   border-radius: 10px;
}

.left .thumbs {
   display: flex;
   gap: 10px;
   margin-top: 15px;
   overflow-x: auto;
}

.left .thumbs img {
   width: 80px;
   height: 80px;
   object-fit: cover;
   cursor: pointer;
   border-radius: 5px;
   transition: 0.3s;
}

.left .thumbs img:hover {
   transform: scale(1.05);
}

.right {
   flex: 1 1 40%;
   display: flex;
   flex-direction: column;
   gap: 25px;
}

.right h2 {
   font-size: 48px;
   margin: 0;
}

.right .price {
   font-size: 32px;
   color: #ff4d4f;
}

.right .desc {
   font-size: 18px;
   color: #555;
   line-height: 1.5;
}

.right select, .right input[type=number] {
   width: 200px;
   padding: 10px;
   font-size: 16px;
   border: 1px solid #ddd;
   border-radius: 5px;
}

.btn-group {
   display: flex;
   gap: 15px;
   flex-wrap: wrap;
}

.btn-group button {
   flex: 1 1 auto;
   padding: 15px;
   font-size: 18px;
   border: none;
   border-radius: 5px;
   cursor: pointer;
   transition: 0.3s;
}

.btn-cart {
   background: #000;
   color: #fff;
}

.btn-cart:hover {
   background: #333;
}

.btn-like {
   background: #fff;
   color: #000;
   border: 1px solid #000;
   min-width: 130px;
   text-align: center;
   display: inline-flex;
   align-items: center;
   justify-content: center;
   gap: 6px;
   padding: 12px 16px;
   position: relative;
}

.btn-like:hover {
   background: #f0f0f0;
}

.btn-buy {
   background: #ff4d4f;
   color: #fff;
}

.btn-buy:hover {
   background: #e60023;
}

.review {
   font-size: 18px;
   color: gold;
}

.product-id {
   font-size: 14px;
   color: #aaa;
}

.separator {
   width: 100%;
   height: 1px;
   background: #ddd;
   margin: 50px 0;
}

.tab-menu {
   display: flex;
   justify-content: center;
   gap: 30px;
   margin-bottom: 30px;
   cursor: pointer;
   position: sticky;
   top: 150px;
   background: #fff;
   z-index: 1000;
   padding: 10px 0;
}

.tab-menu div {
   padding: 10px 20px;
   border-bottom: 2px solid transparent;
   font-weight: bold;
}

.tab-menu .active {
   border-bottom: 2px solid #000;
}

.detail-imgs img {
   width: 100%;
   max-width: 800px;
   margin: 0 auto 30px;
   display: block;
   border-radius: 10px;
}

.heart-animate {
   display: inline-block;
   transition: transform 0.3s ease, color 0.3s ease;
}

.heart-animate.active {
   color: red !important;
   transform: scale(1.4);
}

.heart-animate.active.animate-pop {
   animation: pop 0.4s forwards;
}

@
keyframes pop { 0% {
   transform: scale(1.4);
}

50
%
{
transform
:
scale(
1.7
);
}
100
%
{
transform
:
scale(
1.4
);
}
}
.wish-text {
   font-size: 16px;
}

.wish-badge {
   position: absolute;
   top: -8px;
   right: -8px;
   background: red;
   color: #fff;
   border-radius: 50%;
   width: 22px;
   height: 22px;
   display: flex;
   align-items: center;
   justify-content: center;
   font-size: 14px;
   font-weight: bold;
}
</style>
</head>

<body>
   <jsp:include page="/WEB-INF/views/include/header.jsp" />

   <div class="container product-detail">
      <c:set var="product" value="${productList[0]}" />

      <div class="left">
         <img id="mainImage" class="main-img"
            src="${pageContext.request.contextPath}/resources/img/${product.product_image}"
            alt="메인 이미지" />
         <div class="thumbs">
            <img
               src="${pageContext.request.contextPath}/resources/img/${product.product_image}"
               onclick="swap(this)" />
            <c:forEach var="opt" items="${optionList}">
               <c:if test="${not empty opt.option_picture}">
                  <img
                     src="${pageContext.request.contextPath}/resources/img/${opt.option_picture}"
                     onclick="swap(this)" onerror="this.style.display='none';" />
               </c:if>
            </c:forEach>
         </div>
      </div>

      <div class="right">
         <h2>${product.product_name}</h2>
         <div class="price">${product.product_price}원</div>
         <div class="desc">${product.product_description}</div>

         <c:if test="${not empty optionList}">
            <select id="colorSelect" name="color" onchange="updateStock()">
               <option value="">-- 색상 선택 --</option>
               <c:forEach var="opt" items="${optionList}">
                  <option value="${opt.option_id}"
                     data-stock="${opt.option_stock}">${opt.option_color}</option>
               </c:forEach>
            </select>
            <div id="stockInfo">
               재고: <span id="stockCount"></span>
            </div>
         </c:if>

         <div>
            수량: <input id="qty" type="number" min="1" value="1" />
         </div>

         <div class="btn-group">
            <form action="addToCart.do" method="get" onsubmit="return prepForm()">
                 <input type="hidden" name="product_id" value="${product.product_id}"/>
                 <input type="hidden" name="color" id="colorVal"/>
                 <input type="hidden" name="quantityInput" id="qtyVal"/>
                 <button type="submit" class="btn-cart">장바구니</button>
            </form>
            <button id="wishBtn" class="btn-like"
               data-product-id="${product.product_id}" onclick="toggleWish()">
               <span id="wishIcon" class="heart-animate">♡</span> <span
                  class="wish-text">찜하기</span> <span id="wishCount"
                  ${wishCount} class="wish-badge">0</span>
            </button>
            <button class="btn-buy" onclick="openOrderModal()">결제하기</button>
         </div>
      </div>
   </div>
   
   <!-- 주문 확인 모달 -->
<div id="orderModal" style="display:none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.6); z-index: 9999; display: flex; align-items: center; justify-content: center;">
    <div style="background: white; padding: 30px 20px; border-radius: 10px; width: 400px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); text-align: center;">
        <h2 style="margin-bottom: 20px;">주문 확인</h2>
        <div id="orderSummary" style="font-size: 16px; line-height: 1.6; margin-bottom: 20px; text-align: left;"></div>
        <div>
            <button onclick="closeOrderModal()" style="padding: 10px 20px; background: #555; color: white; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px;">취소</button>
            <button onclick="payNow()" style="padding: 10px 20px; background: #ff4d4f; color: white; border: none; border-radius: 5px; cursor: pointer;">확인</button>
        </div>
    </div>
</div>


   <div class="separator"></div>

   <div class="container">
      <div class="tab-menu">
         <div class="tab-button" data-target="#description">상품 상세 설명</div>
         <div class="tab-button" data-target="#images">상세 사진</div>
         <div class="tab-button" data-target="#reviews">상품 리뷰</div>
      </div>

      <div id="description">
         <h3>상품 상세 설명</h3>
         <br>
         <br> ${product.product_description}<br>
         <br> <br>
         <br>
      </div>

      <div id="images">
         <hr>
         <br>
         <br>
         <h3>상세 사진</h3>
         <br>
         <br>
         <div class="detail-imgs">
            <img
               src="${pageContext.request.contextPath}/resources/img/${product.product_image}" />
            <c:forEach var="opt" items="${optionList}">
               <c:if test="${not empty opt.option_picture}">
                  <img
                     src="${pageContext.request.contextPath}/resources/img/${opt.option_picture}"
                     onerror="this.style.display='none';" />
               </c:if>
            </c:forEach>
         </div>
      </div>

      <div id="reviews">
         <hr>
         <br>
         <br>
         <h3>상품 리뷰</h3>
         <div id="reviewSection"></div>
      </div>
   </div>

   <script>
$(document).ready(function() {
    $('#reviewSection').load('${pageContext.request.contextPath}/ProductReviewAjax.do?product_id=${product.product_id}');
});

$('.tab-button').click(function() {
    const target = $(this).data('target');
    $('html, body').animate({ scrollTop: $(target).offset().top - 210 }, 400);
});

function swap(el) { 
   $("#mainImage").attr("src", el.src); 
}
function updateStock() { 
   let sel = $("#colorSelect option:selected"); 
   $("#stockCount").text(sel.data("stock")); 
}
function prepForm() { 
   $("#colorVal").val($("#colorSelect").val()); 
   $("#qtyVal").val($("#qty").val()); return true; 
}

function toggleWish() {
    let pid = $("#wishBtn").data("product-id");
    $.ajax({
        url: "${pageContext.request.contextPath}/toggle.do",
        method: "POST",
        data: { productId: pid },
        success: function (res) {
            let icon = $("#wishIcon");
            if (res.liked) {
                icon.text("♥").addClass("active animate-pop");
            } else {
                icon.text("♡").removeClass("active animate-pop");
            }
            updateWishCount(pid);
            setTimeout(function () { icon.removeClass("animate-pop"); }, 400);
        },
        error: function () { alert("찜하기 실패"); }
    });
}

function updateWishCount(pid) {
    $.ajax({
        url: "${pageContext.request.contextPath}/count.do",
        method: "GET",
        data: { productId: pid },
        success: function (res) {
            if (res && res.count !== undefined && res.count !== null) {
                $("#wishCount").text(res.count);
            } else {
                $("#wishCount").text("0");
            }
        },
        error: function () { alert("찜 수 갱신 실패"); }
    });
}




$(function () {
    const pid = "${product.product_id}";
    if (pid) {
        $.get("${pageContext.request.contextPath}/addRecentView.do", { productId: pid });
    }
});
</script>

<!-- 최근 본 상품 저장 기능 스크립트 -->
<script>
    $(document).ready(function () {
        var productId = "${product.product_id}";
        
        if (productId === undefined || productId === "") {
            console.log("productId가 정의되지 않음");
            return; // productId 없으면 Ajax 호출 중단
        }

        $.ajax({
            url: "addRecentView.do",
            type: "GET",
            data: { productId: productId },
            success: function () {
                console.log("최근 본 상품이 저장되었습니다.");
            },
            error: function () {
                console.log("최근 본 상품 저장 실패.");
            }
        });

    });
</script>

<script>
//모달 열기
function openOrderModal() {
    const productName = "${product.product_name}";
    const price = parseInt("${product.product_price}");
    const qty = parseInt($("#qty").val());
    const optionText = $("#colorSelect option:selected").text();

    if ($("#colorSelect").val() == "" && ${fn:length(optionList)} > 0) {
        alert("색상 선택해주세요");
        return;
    }

    let summary = "<strong>상품명:</strong> " + productName + "<br>";
    summary += "<strong>옵션:</strong> " + (optionText === "-- 색상 선택 --" ? "없음" : optionText) + "<br>";
    summary += "<strong>수량:</strong> " + qty + "<br>";
    summary += "<strong>총 금액:</strong> " + (price * qty).toLocaleString() + "원";

    $("#orderSummary").html(summary);
    $("#orderModal").fadeIn();
}

// 모달 닫기
function closeOrderModal() {
    $("#orderModal").fadeOut();
}

// 카카오페이 결제 진행
function payNow(){
    const productName = "${product.product_name}";
    const productId = "${product.product_id}";
    const price = parseInt("${product.product_price}");
    const qty = parseInt($("#qty").val());
    const option_id = $("#colorSelect").val();

    if (option_id == "" && ${fn:length(optionList)} > 0) {
        alert("색상 선택해주세요");
        return;
    }

    $.ajax({
        url: "${pageContext.request.contextPath}/getUserInfo.do",
        method: "GET",
        success: function(user){
            const IMP = window.IMP;
            IMP.init("imp66570674");
            IMP.request_pay({
                pg: "kakaopay", pay_method: "card",
                merchant_uid: "order_" + new Date().getTime(),
                name: productName, amount: price * qty,
                buyer_name: user.name, buyer_tel: user.phone,
                m_redirect_url: "/orderComplete.do"
            }, function(rsp){
                if (rsp.success) {
                    $.post("${pageContext.request.contextPath}/orderInsertOne.do", {
                        merchant_uid: rsp.merchant_uid,
                        paid_amount: rsp.paid_amount,
                        product_id: productId,
                        product_count: qty,
                        option_id: option_id
                    }, function(){
                        location.href = "${pageContext.request.contextPath}/orderComplete.do?orderId=" + rsp.merchant_uid;
                    }).fail(function(){ alert("주문 저장 실패"); });
                } else {
                    alert("결제 실패: " + rsp.error_msg);
                }
            });
        },
        error: function(){ alert("유저 정보 조회 실패"); }
    });
}
</script>

   <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
