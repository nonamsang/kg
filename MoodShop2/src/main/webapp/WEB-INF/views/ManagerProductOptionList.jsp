<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>상품 더보기</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f9fafb;
      padding: 30px 15%;
      color: #333;
      font-size: 18px;
      margin: 0 auto;
      max-width: 70%;
      box-sizing: border-box;
    }
    table {
      border-collapse: collapse;
      width: 100%;
      margin-bottom: 40px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
      background: #fff;
      border-radius: 10px;
      overflow: hidden;
      font-size: 17px;
    }
    th, td {
      padding: 12px 15px;
      border-bottom: 1px solid #ddd;
      text-align: center;
      vertical-align: middle;
    }
    thead th, tr:first-child th {
      background-color: #4CAF50;
      color: white;
      font-weight: 600;
      font-size: 20px;
      letter-spacing: 0.05em;
    }
    tr:hover {
      background-color: #f1f7f3;
    }
    img {
      width: 80px;
      height: 80px;
      object-fit: cover;
      border-radius: 8px;
      box-shadow: 0 2px 6px rgba(0,0,0,0.15);
      transition: transform 0.2s ease;
      cursor: pointer;
    }
    img:hover {
      transform: scale(1.05);
      box-shadow: 0 4px 12px rgba(0,0,0,0.25);
    }
    input[type="submit"] {
      display: block;
      margin: 0 auto;
      background-color: #4CAF50;
      color: white;
      font-size: 18px;
      padding: 12px 40px;
      border: none;
      border-radius: 10px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }
    input[type="submit"]:hover {
      background-color: #3a7a37;
    }
    #imgModal {
      display: none;
      position: fixed;
      z-index: 9999;
      padding-top: 60px;
      left: 0; top: 0;
      width: 100%; height: 100%;
      overflow: auto;
      background-color: rgba(0,0,0,0.8);
    }
    #imgModalContent {
      margin: auto;
      display: block;
      max-width: 80vw;
      max-height: 80vh;
      border-radius: 12px;
      box-shadow: 0 4px 20px rgba(255,255,255,0.7);
      transition: 0.3s ease;
    }
    #imgModalClose {
      position: absolute;
      top: 20px;
      right: 35px;
      color: white;
      font-size: 40px;
      font-weight: bold;
      cursor: pointer;
      transition: color 0.3s ease;
      user-select: none;
    }
    #imgModalClose:hover {
      color: #bbb;
    }
  </style>
</head>
<body>

<form action="return.do" method="post" enctype="multipart/form-data">
  <input type="hidden" name="company_name" value="${company_name}" />
  <table border="1">
    <tr>
      <th>상품아이디</th>
      <th>상품이름</th>
      <th>상품 가격</th>
      <th>기분</th>
      <th>카테고리</th>
      <th>이미지</th>
    </tr>
    <c:forEach var="product" items="${productlist}">
      <tr>
        <td>${product.product_id}<input type="hidden" name="product_id" value="${product.product_id}" /></td>
        <td>${product.product_name}<input type="hidden" name="product_name" value="${product.product_name}" /></td>
        <td>${product.product_price}<input type="hidden" name="product_price" value="${product.product_price}" /></td>
        <td>${product.product_mood}<input type="hidden" name="product_mood" value="${product.product_mood}" /></td>
        <td>${product.product_category}<input type="hidden" name="product_category" value="${product.product_category}" /></td>
        <td>
          <c:choose>
            <c:when test="${not empty product.product_image}">
              <c:forEach var="imgPath" items="${fn:split(product.product_image, ',')}">
                <img src="${pageContext.request.contextPath}/resources/img/${imgPath}" alt="상품 이미지" onclick="showModal(this)" />
              </c:forEach>
            </c:when>
            <c:otherwise>
              <img src="${pageContext.request.contextPath}/images/noimage.jpg" alt="이미지 없음" />
            </c:otherwise>
          </c:choose>
          <input type="hidden" name="product_image" value="${product.product_image}" />
          <input type="hidden" name="original_image" value="${product.product_image}" />
        </td>
      </tr>

      <c:set var="hasOption" value="false" />
      <c:forEach var="opt" items="${optionlist}">
        <c:if test="${opt.product_id == product.product_id}">
          <c:set var="hasOption" value="true" />
        </c:if>
      </c:forEach>

      <c:if test="${hasOption}">
        <tr>
          <td colspan="6">
            <table border="1" style="margin-left:20px;">
              <tr>
                <th>상품아이디</th>
                <th>색깔</th>
                <th>사이즈</th>
                <th>재고 수량</th>
                <th>옵션 이미지</th>
              </tr>
              <c:forEach var="option" items="${optionlist}">
                <c:if test="${option.product_id == product.product_id}">
                  <tr>
                    <td>${option.product_id}</td>
                    <td>${option.option_color}</td>
                    <td>${option.option_size}</td>
                    <td>${option.option_stock}</td>
                    <td>
                      <c:choose>
                        <c:when test="${not empty option.option_picture}">
                          <img src="${pageContext.request.contextPath}/resources/img/${option.option_picture}" alt="옵션 이미지" onclick="showModal(this)" />
                        </c:when>
                        <c:otherwise>
                          <img src="${pageContext.request.contextPath}/images/noimage.jpg" alt="이미지 없음" />
                        </c:otherwise>
                      </c:choose>
                    </td>
                  </tr>
                </c:if>
              </c:forEach>
            </table>
          </td>
        </tr>
      </c:if>
    </c:forEach>
  </table>

  <br/>
  <input type="submit" value="관리자페이지로가기" />
</form>

<!-- 이미지 모달 -->
<div id="imgModal" onclick="closeModal(event)">
  <span id="imgModalClose" onclick="closeModal(event)">&times;</span>
  <img id="imgModalContent" src="" alt="확대한 이미지" />
</div>

<script>
  function showModal(imgElement) {
    const modal = document.getElementById("imgModal");
    const modalImg = document.getElementById("imgModalContent");
    modal.style.display = "block";
    modalImg.src = imgElement.src;
  }

  function closeModal(event) {
    if (event.target.id === "imgModal" || event.target.id === "imgModalClose") {
      document.getElementById("imgModal").style.display = "none";
    }
  }
</script>

</body>
</html>
