<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <title>상품 옵션 수정</title>
  <style>
 

    body {
      margin: 0;
      padding: 40px;
      font-family: 'Noto Sans KR', sans-serif;
      background: #f7f7f7;
      color: #333;
    }

    h1 {
      text-align: center;
      margin-bottom: 40px;
      font-weight: 700;
      color: #222;
      font-size: 28px;
    }

    form {
      max-width: 600px;
      margin: 0 auto;
      background: #fff;
      padding: 30px 40px;
      border-radius: 15px;
      box-shadow: 0 8px 25px rgba(0,0,0,0.1);
    }

    .form-row {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 25px;
      gap: 20px;
    }

    .form-label {
      flex: 1 0 110px;
      font-weight: 600;
      color: #555;
      font-size: 16px;
    }

    .form-control {
      flex: 2 1 300px;
    }

    select, input[type="number"], input[type="file"] {
      width: 100%;
      padding: 10px 12px;
      font-size: 15px;
      border: 1.8px solid #ddd;
      border-radius: 8px;
      transition: border-color 0.3s ease;
    }
    select:focus, input[type="number"]:focus, input[type="file"]:focus {
      border-color: #ff6f61;
      outline: none;
      box-shadow: 0 0 6px rgba(255,111,97,0.5);
    }

    .image-preview {
      flex: 1 0 120px;
      text-align: center;
    }

    .image-preview img {
      border-radius: 12px;
      width: 100px;
      height: 100px;
      object-fit: cover;
      border: 1.5px solid #ccc;
      box-shadow: 0 0 8px rgba(0,0,0,0.05);
    }

    input[type="submit"] {
      display: block;
      width: 100%;
      padding: 14px 0;
      background-color: #ff6f61;
      border: none;
      border-radius: 12px;
      color: white;
      font-weight: 700;
      font-size: 18px;
      cursor: pointer;
      transition: background-color 0.3s ease;
      margin-top: 10px;
      letter-spacing: 1px;
    }

    input[type="submit"]:hover {
      background-color: #e85b4f;
    }

    /* 숨긴 input 스타일 */
    input[type=hidden] {
      display: none;
    }
  </style>
</head>
<body>

<h1>상품 옵션 수정</h1>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<form action="MProductOptionUpdate.do" method="post" enctype="multipart/form-data">
  <c:set var="colors" value="빨강,주황,노랑,초록,파랑,보라,검정" />
  <c:set var="sizes" value="FREE" />

  <div class="form-row">
    <div class="form-label">옵션 아이디</div>
    <div class="form-control">
      ${opt.option_id}
      <input type="hidden" name="option_id" value="${opt.option_id}" />
    </div>
  </div>

  <div class="form-row">
    <div class="form-label">상품 아이디</div>
    <div class="form-control">
      ${opt.product_id}
      <input type="hidden" name="product_id" value="${opt.product_id}" />
    </div>
  </div>

  <div class="form-row">
    <label for="option_color" class="form-label">상품 색깔</label>
    <div class="form-control">
      <select name="option_color" id="option_color" required>
        <c:forEach var="color" items="${fn:split(colors, ',')}">
          <option value="${color}" <c:if test="${color == opt.option_color}">selected</c:if>>
            ${color}
          </option>
        </c:forEach>
      </select>
    </div>
  </div>

  <div class="form-row">
    <label for="option_size" class="form-label">상품 사이즈</label>
    <div class="form-control">
      <select name="option_size" id="option_size" required>
        <c:forEach var="size" items="${fn:split(sizes, ',')}">
          <option value="${size}" <c:if test="${size == opt.option_size}">selected</c:if>>
            ${size}
          </option>
        </c:forEach>
      </select>
    </div>
  </div>

  <div class="form-row">
    <label for="option_stock" class="form-label">상품 재고</label>
    <div class="form-control">
      <input type="number" name="option_stock" id="option_stock" value="${opt.option_stock}" min="0" required />
    </div>
  </div>

  <div class="form-row">
    <label for="option_picture" class="form-label">옵션 이미지</label>
    <div class="form-control">
      <input type="file" name="option_picture" id="option_picture" accept="image/*" />
      <input type="hidden" name="original_image" value="${opt.option_picture}" />
    </div>
    <div class="image-preview">
      <c:if test="${not empty opt.option_picture}">
        <img src="${contextPath}/resources/img/${opt.option_picture}" alt="옵션 이미지" />
      </c:if>
    </div>
  </div>

  <input type="submit" value="수정하기" />
</form>

</body>
</html>
