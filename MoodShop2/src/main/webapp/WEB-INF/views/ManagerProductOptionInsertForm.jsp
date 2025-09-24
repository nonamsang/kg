<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상품 옵션 등록</title>
<style>
  body {
    margin: 0;
    padding: 40px;
    background: #A8D0EB;
    font-family: 'Segoe UI', sans-serif;
    color: #fff;
  }

  h2 {
    text-align: center;
    margin-bottom: 40px;
    color: #fff;
    font-size: 28px;
    letter-spacing: 1px;
  }

  form {
    max-width: 700px;
    margin: 0 auto;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 20px;
    padding: 30px 40px;
    box-shadow: 0 0 30px rgba(0,0,0,0.5);
    backdrop-filter: blur(5px);
  }

  .card {
    background: rgba(255,255,255,0.1);
    border: 2px solid #2ec4b6;
    border-radius: 12px;
    padding: 25px;
    margin-bottom: 25px;
    box-shadow: 0 0 15px rgba(46,196,182,0.4);
    transition: transform 0.3s ease;
  }

  .card:hover {
    transform: scale(1.02);
  }

  .field-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
  }

  label {
    font-weight: 600;
    margin-bottom: 8px;
    color: #4a6fa5;
  }

  select, input[type="number"], input[type="file"] {
    padding: 10px;
    font-size: 16px;
    border-radius: 8px;
    border: none;
    outline: none;
    background: #fff;
    color: #333;
    transition: box-shadow 0.2s ease;
  }

  select:focus, input:focus {
    box-shadow: 0 0 10px #2ec4b6;
  }

  .button-group {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin-top: 30px;
  }

  input[type="submit"], input[type="reset"] {
    padding: 12px 30px;
    font-size: 16px;
    font-weight: bold;
    color: white;
    background: #2ec4b6;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: background 0.3s ease;
  }

  input[type="submit"]:hover {
    background: #25a99f;
  }

  input[type="reset"]:hover {
    background: #ff4d4d;
  }

  /* Alert */
  .alert {
    text-align: center;
    font-size: 18px;
    color: #ffcccb;
    margin-top: 20px;
  }
</style>
</head>
<body>
<h2>⚽ 판매할 상품 옵션 등록 ⚽</h2>

<c:set var="colors" value="빨강,주황,노랑,초록,파랑,보라,검정" />
<c:set var="sizes" value="FREE" />

<form action="MProductOptionInsertAll.do" method="post" enctype="multipart/form-data" onsubmit="return no()">
  <input type="hidden" name="product_id" value="${product_id}" />

  <div class="card">
    <div class="field-group">
      <label for="option_color">상품 색깔</label>
      <select name="option_color" id="option_color">
        <option value="">-- 선택하세요 --</option>
        <c:forEach var="color" items="${fn:split(colors, ',')}">
          <option value="${color}">${color}</option>
        </c:forEach>
      </select>
    </div>

    <div class="field-group">
      <label for="option_size">상품 사이즈</label>
      <select name="option_size" id="option_size">
        <option value="">-- 선택하세요 --</option>
        <c:forEach var="size" items="${fn:split(sizes, ',')}">
          <option value="${size}">${size}</option>
        </c:forEach>
      </select>
    </div>

    <div class="field-group">
      <label for="option_stock">상품 재고</label>
      <input type="number" name="option_stock" id="option_stock" required min="0" max="9999" />
    </div>

    <div class="field-group">
      <label for="option_picture">상품 이미지</label>
      <input type="file" name="option_picture" id="option_picture" />
    </div>
  </div>

  <div class="button-group">
    <input type="submit" name="action" value="상품 옵션 등록" />
    <input type="reset" value="초기화" />
  </div>
</form>

<c:if test="${not empty ok}">
  <div class="alert">
    <script>alert("${ok}");</script>
  </div>
</c:if>

<script>
function no() {
  const size = document.querySelector("select[name='option_size']").value;
  const color = document.querySelector("select[name='option_color']").value;

  if (size === "" && color === "") {
    alert("색깔과 사이즈를 선택해 주세요");
    return false;
  } else if (size === "") {
    alert("사이즈를 선택해주세요");
    return false;
  } else if (color === "") {
    alert("색깔을 선택해주세요");
    return false;
  }
  return true;
}
</script>
</body>
</html>
