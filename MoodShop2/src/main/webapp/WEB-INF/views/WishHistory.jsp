<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>찜 목록</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.wishlist-wrapper {
    width: 100%;
    max-width: 900px;
    margin: 0 auto;
    padding: 20px;
    box-sizing: border-box;
}

h2 {
    text-align: center;
    margin-bottom: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

th, td {
    border: 1px solid #ddd;
    padding: 12px;
    text-align: center;
}

th {
    background-color: #f8f8f8;
}

img {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 6px;
    cursor: pointer;
}

.product-link {
    text-decoration: none;
    color: #007bff;
    font-weight: bold;
}

.product-link:hover {
    text-decoration: underline;
}

.delete-btn {
    padding: 6px 12px;
    background-color: #dc3545;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.delete-btn:hover {
    background-color: #c82333;
}

.empty-message {
    text-align: center;
    margin-top: 30px;
    color: #555;
    font-size: 16px;
}
</style>
</head>
<body>
<div class="wishlist-wrapper">
<c:set var="User" value="${User}" />
<h2>❤️ ${User.nickname}님의 찜한 상품 목록</h2>
<hr>

<table>
  <thead>
    <tr>
      <th>이미지</th>
      <th>상품명</th>
      <th>가격</th>
      <th>삭제</th>
    </tr>
  </thead>
  <tbody id="wishlistTableBody">
    <c:forEach var="item" items="${wishlist}">
      <tr id="wishRow-${item.product_id}">
        <td>
          <a href="MainProductName.do?product_id=${item.product_id}">
            <img src="${pageContext.request.contextPath}/resources/img/${item.product_image}" alt="상품 이미지" />
          </a>
        </td>
        <td>
          <a href="MainProductName.do?product_id=${item.product_id}" class="product-link">
            ${item.product_name}
          </a>
        </td>
        <td>${item.product_price} 원</td>
        <td>
          <button type="button" class="delete-btn" onclick="deleteWish('${item.product_id}')">삭제</button>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<c:if test="${empty wishlist}">
  <div class="empty-message">찜한 상품이 없습니다.</div>
</c:if>

</div>

<script>
function deleteWish(productId) {
  if (!confirm("정말 삭제하시겠습니까?")) return;

  $.ajax({
    url: "${pageContext.request.contextPath}/delete.do",
    type: "POST",
    data: { productId: productId },
    success: function(response) {
      if (response.success) {
        $("#wishRow-" + productId).remove();
        alert("삭제되었습니다.");

        if ($("#wishlistTableBody").children().length === 0) {
          $("#wishlistTableBody").closest("table").after("<div class='empty-message'>찜한 상품이 없습니다.</div>");
          $("#wishlistTableBody").closest("table").remove();
        }
      } else {
        alert(response.message || "삭제 실패");
      }
    },
    error: function() {
      alert("삭제 요청에 실패했습니다.");
    }
  });
}
</script>

</body>
</html>
