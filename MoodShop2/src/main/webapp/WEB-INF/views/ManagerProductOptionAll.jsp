<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상품 더보기</title>
<style>
  body {
    font-family: 'Segoe UI', sans-serif;
    background: linear-gradient(to right, #f4f4f4, #e9e9e9);
    margin: 0;
    padding: 30px;
  }

  .product-header {
    display: flex;
    align-items: center;
    gap: 30px;
    padding: 20px;
    background: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    margin-bottom: 40px;
  }

  .product-header img {
    width: 180px;
    height: 180px;
    object-fit: cover;
    border-radius: 12px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    cursor: pointer;
  }

  .product-details {
    flex: 1;
  }

  .product-details h2 {
    margin: 0 0 10px;
    font-size: 24px;
  }

  .product-details .price {
    font-size: 20px;
    color: #1a8917;
    margin: 10px 0;
  }

  .product-details .meta {
    font-size: 16px;
    color: #555;
  }

  .grid-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
    gap: 20px;
  }

  .product-tile {
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    overflow: hidden;
    position: relative;
    transition: transform 0.3s ease;
  }

  .product-tile:hover {
    transform: translateY(-6px);
    box-shadow: 0 8px 20px rgba(0,0,0,0.15);
  }

  .product-image {
    width: 100%;
    height: 160px;
    object-fit: cover;
    cursor: pointer;
  }

  .product-info {
    padding: 15px;
  }

  .product-meta {
    font-size: 14px;
    margin: 5px 0;
    color: #555;
  }

  .radio-btn {
    margin-top: 10px;
    text-align: right;
  }

  /* 모달 */
  #imgModal {
    display: none;
    position: fixed;
    z-index: 10000;
    left: 0; top: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0,0,0,0.8);
    justify-content: center;
    align-items: center;
  }

  #imgModal.open {
    display: flex;
  }

  #imgModalContent {
    max-width: 60vw;
    max-height: 80vh;
    border-radius: 12px;
    box-shadow: 0 0 20px white;
  }

  #imgModalClose {
    position: fixed;
    top: 20px;
    right: 30px;
    font-size: 36px;
    font-weight: bold;
    color: white;
    cursor: pointer;
    user-select: none;
  }

  #imgModalClose:hover {
    color: #ccc;
  }

  .button-group {
    text-align: center;
    margin-top: 40px;
  }

  .button-group button {
    padding: 10px 25px;
    margin: 0 10px;
    font-size: 16px;
    background-color: #4CAF50;
    color: #fff;
    border: none;
    border-radius: 8px;
    cursor: pointer;
  }

  .button-group button:hover {
    background-color: #3a8d44;
  }
</style>
</head>
<body>

<form action="MProductOptionIUD.do" method="post">
  <input type="hidden" name="product_id" value="${product.product_id}" />

  <!-- 상품 상단 정보 -->
  <div class="product-header">
    <c:choose>
      <c:when test="${not empty product.product_image}">
        <img src="${pageContext.request.contextPath}/resources/img/${product.product_image}" alt="상품 이미지" onclick="showModal(this)" />
      </c:when>
      <c:otherwise>
        <img src="${pageContext.request.contextPath}/images/noimage.jpg" alt="기본 이미지" onclick="showModal(this)" />
      </c:otherwise>
    </c:choose>
    <div class="product-details">
      <h2>${product.product_name}</h2>
      <div class="price">
        <fmt:formatNumber value="${product.product_price}" type="number" groupingUsed="true" />원
      </div>
      <div class="meta">기분: ${product.product_mood}</div>
      <div class="meta">카테고리: ${product.product_category}</div>
    </div>
  </div>

  <!-- 옵션 그리드 -->
  <div class="grid-container">
    <c:forEach var="option" items="${optionlist}">
      <c:if test="${option.product_id == product.product_id}">
        <div class="product-tile">
          <c:choose>
            <c:when test="${not empty option.option_picture}">
              <img class="product-image" src="${pageContext.request.contextPath}/resources/img/${option.option_picture}" alt="옵션 이미지" onclick="showModal(this)" />
            </c:when>
            <c:otherwise>
              <img class="product-image" src="${pageContext.request.contextPath}/images/noimage.jpg" alt="옵션 기본 이미지" onclick="showModal(this)" />
            </c:otherwise>
          </c:choose>
          <div class="product-info">
            <div class="product-meta">색상: ${option.option_color}</div>
            <div class="product-meta">사이즈: ${option.option_size}</div>
            <div class="product-meta">재고: ${option.option_stock}개</div>
            <div class="radio-btn">
              <input type="radio" name="option_id" value="${option.option_id}" />
            </div>
          </div>
        </div>
      </c:if>
    </c:forEach>
  </div>

  <div class="button-group">
    <button type="submit" name="action" value="insert">추가</button>
    <button type="submit" name="action" value="update" onclick="return confirmD('update')">수정</button>
    <button type="submit" name="action" value="delete" onclick="return confirmD('delete')">삭제</button>
  </div>
</form>

<!-- 이미지 모달 -->
<div id="imgModal" onclick="closeModal(event)">
  <span id="imgModalClose" onclick="closeModal(event)">&times;</span>
  <img id="imgModalContent" src="" alt="이미지 확대" />
</div>

<script>
function showModal(img) {
  document.getElementById("imgModal").classList.add("open");
  document.getElementById("imgModalContent").src = img.src;
}

function closeModal(event) {
  if (event.target.id === "imgModal" || event.target.id === "imgModalClose") {
    document.getElementById("imgModal").classList.remove("open");
  }
}

function confirmD(action){
  const options = document.getElementsByName("option_id");
  let selected = false;
  for(let o of options){
    if(o.checked) selected = true;
  }

  if(!selected && (action === 'update' || action === 'delete')){
    alert(action === 'update' ? "수정할 옵션을 선택해주세요." : "삭제할 옵션을 선택해주세요.");
    return false;
  }

  return action !== 'delete' || confirm("정말 삭제하시겠습니까?");
}
</script>

<c:if test="${not empty ok}">
  <script>
    alert("${ok}");
  </script>
</c:if>

</body>
</html>
