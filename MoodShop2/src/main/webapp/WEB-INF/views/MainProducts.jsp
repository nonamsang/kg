<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>상품</title>
  <style>
    .wish-btn {
      cursor: pointer;
      color: red;
      font-size: 20px;
      border: none;
      background: none;
    }
  </style>
</head>
<body>
<a href="MyPage.do">뒤로가기</a> | 상품
<a href="MainMoodShop.do">홈으로가기</a> | 검색하기
<hr>

<c:forEach var="last" items="${product}">
  <table border="1">
    <tr><td><a href="MypageLastList.do?product_id=${last.product_id}">${last.product_id}</a></td></tr>
    <tr><td>${last.product_name}</td></tr>
    <tr><td>${last.product_price}</td></tr>
    <tr><td>${last.product_mood}</td></tr>
    <tr><td>${last.product_category}</td></tr>
    <tr><td>${last.product_image}</td></tr>
    <tr><td>${last.company_id}</td></tr>
   <tr>
  <td>
    <span id="wishCount_${last.product_id}">${last.wish_id}</span>명 찜 |
    <button class="wish-btn"
            data-product-id="${last.product_id}"
            data-is-wished="${last.wish_id == true}"
            id="wishBtn_${last.product_id}">
      <c:choose>
        <c:when test="${last.wish_id == true}">
          ♥
        </c:when>
        <c:otherwise>
          ♡
        </c:otherwise>
      </c:choose>
    </button>
  </td>
</tr>
  </table>
  <a href="MainProductOption.do?product_id=${last.product_id}">제품상세보기</a>
  <hr>
</c:forEach>

<script>
document.addEventListener("DOMContentLoaded", function () {
  const buttons = document.querySelectorAll(".wish-btn");

  buttons.forEach(function (button) {
    button.addEventListener("click", function () {
      const productId = this.dataset.productId;

      const xhr = new XMLHttpRequest();
      xhr.open("POST", "/ToggleWishAjax.do", true);
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

      xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            const res = JSON.parse(xhr.responseText);

            if(res.success) {
              // 하트 토글
              this.textContent = res.wished ? "♥" : "♡";
              this.dataset.isWished = res.wished;

              // 찜 수 업데이트
              document.getElementById("wishCount_" + productId).textContent = res.wishCount;
            } else {
              alert(res.message || "찜 처리 중 오류가 발생했습니다.");
            }
          } else {
            alert("서버 오류가 발생했습니다. 상태코드: " + xhr.status);
          }
        }
      };

      xhr.send("product_id=" + encodeURIComponent(productId));
    });
  });
});
</script>
</body>
</html>