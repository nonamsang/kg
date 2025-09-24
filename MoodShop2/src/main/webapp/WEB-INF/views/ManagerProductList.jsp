<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>상품 목록</title>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background-color: #f7f9fc;
      padding: 40px;
    }

    a {
      text-decoration: none;
      color: #3498db;
      font-weight: bold;
    }

    h2 {
      text-align: center;
      color: #2c3e50;
      margin-bottom: 30px;
    }

    form {
      max-width: 1000px;
      margin: auto;
      background: #fff;
      padding: 30px;
      border-radius: 15px;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    }

    select {
      padding: 8px;
      border-radius: 8px;
      border: 1px solid #ccc;
      font-size: 14px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }

    th, td {
      border: 1px solid #ddd;
      padding: 12px;
      text-align: center;
    }

    th {
      background-color: #ecf0f1;
      font-weight: bold;
    }

    img {
      width: 150px;
      height: 150px;
      border-radius: 10px;
      object-fit: cover;
      cursor: pointer;
      transition: transform 0.2s ease;
    }

    img:hover {
      transform: scale(1.03);
    }

    button {
      padding: 10px 20px;
      margin-top: 20px;
      margin-right: 10px;
      border: none;
      border-radius: 8px;
      font-weight: bold;
      font-size: 14px;
      cursor: pointer;
    }

    button[type="submit"][value="more"] {
      background-color: #8e44ad;
      color: white;
    }

    button[type="submit"][value="update"] {
      background-color: #f39c12;
      color: white;
    }

    button[type="submit"][value="delete"] {
      background-color: #e74c3c;
      color: white;
    }

    button[type="reset"] {
      background-color: #bdc3c7;
      color: #2c3e50;
    }

    p[align="right"] {
      margin-bottom: 10px;
    }

    /* 모달 스타일 */
    #imgModal {
      display: none;
      position: fixed;
      top: 0; left: 0;
      width: 100%; height: 100%;
      background: rgba(0, 0, 0, 0.8);
      justify-content: center;
      align-items: center;
      z-index: 1000;
    }

    #imgModal img {
      max-width: 70vw;
      max-height: 70vh;
      width: auto;
      height: auto;
      border-radius: 10px;
    }

    #imgModal span {
      position: absolute;
      top: 30px;
      right: 50px;
      color: white;
      font-size: 30px;
      cursor: pointer;
    }
  </style>
</head>
<body>

<a href="return.do">← 관리자페이지로 돌아가기</a>

<h2>📋 상품 목록</h2>

<form action="MProductAlign.do" method="get">
  <p align="right">
    <select name="malign" onchange="this.form.submit()">
      <option value="">-- 정렬 기준 선택 (기본: 상품 ID) --</option>
      <option value="mood" ${param.malign == 'mood' ? 'selected' : ''}>기분 기준</option>
      <option value="category" ${param.malign == 'category' ? 'selected' : ''}>카테고리 기준</option>
    </select>
  </p>
</form>

<form action="MProductUD.do" method="post">
  <input type="hidden" name="company_name" value="${company_name}">
  <c:set var="contextPath" value="${pageContext.request.contextPath}" />

  <table>
    <tr>
      <th></th>
      <th>상품 이름</th>
      <th>상품 가격</th>
      <th>감정</th>
      <th>카테고리</th>
      <th>이미지</th>
    </tr>
    <c:forEach var="product" items="${product}">
      <tr>
        <td><input type="checkbox" name="product_id" value="${product.product_id}"></td>
        <td>${product.product_name}</td>
        <td><fmt:formatNumber value="${product.product_price}" type="number" groupingUsed="true" /> 원</td>
        <td>${product.product_mood}</td>
        <td>${product.product_category}</td>
       <td>
  <c:choose>
    <c:when test="${not empty product.product_image}">
      <img src="${contextPath}/resources/img/${product.product_image}"
           onclick="openModal('${contextPath}/${product.product_image}')" />
    </c:when>
    <c:otherwise>
      <img src="${contextPath}/images/noimage.jpg"
           onclick="openModal('${contextPath}/images/noimage.jpg')" />
    </c:otherwise>
  </c:choose>
</td>
      </tr>
    </c:forEach>
  </table>

  <button type="submit" name="action" value="more">더보기</button>
  <button type="submit" name="action" value="update">수정</button>
  <button type="submit" name="action" value="delete" onclick="return confirmD()">삭제</button>
  <button type="reset">취소</button>
</form>

<!-- 알림 메시지 -->
<c:if test="${not empty msg}">
  <script>alert("${msg}");</script>
</c:if>
<c:if test="${not empty ok}">
  <script>alert("${ok}");</script>
</c:if>

<!-- 이미지 모달창 -->
<div id="imgModal" onclick="closeModal()">
  <span onclick="closeModal()">&times;</span>
  <img id="modalImg" onclick="event.stopPropagation()" />
</div>

<!-- 자바스크립트 -->
<script>
function confirmD() {
  return confirm("정말로 삭제하시겠습니까?");
}

function openModal(src) {
  document.getElementById('modalImg').src = src;
  document.getElementById('imgModal').style.display = 'flex';
}

function closeModal() {
  document.getElementById('imgModal').style.display = 'none';
}
</script>

</body>
</html>
