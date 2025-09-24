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
  /* 전체 바디 스타일 */
  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #f0f0f0 0%, #ffffff 100%);
    padding: 40px 10%;
    color: #333;
    margin: 0;
  }

  a {
    display: inline-block;
    margin-bottom: 25px;
    font-weight: 600;
    color: #4CAF50;
    text-decoration: none;
    transition: color 0.3s ease;
  }
  a:hover {
    color: #388E3C;
  }

  form {
    background-color: white;
    padding: 20px 25px;
    border-radius: 10px;
    box-shadow: 0 3px 15px rgb(0 0 0 / 0.1);
  }

  table {
    width: 100%;
    border-collapse: collapse;
    font-size: 16px;
  }
  th, td {
    padding: 12px 15px;
    text-align: center;
    border-bottom: 1px solid #ddd;
    vertical-align: middle;
  }
  thead th {
    background-color: #4CAF50;
    color: white;
    font-weight: 700;
    letter-spacing: 0.05em;
  }
  tbody tr:hover {
    background-color: #e8f5e9;
  }

  input[type="radio"] {
    width: 18px;
    height: 18px;
    cursor: pointer;
  }

  img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0,0,0,0.1);
    transition: transform 0.2s ease;
    cursor: pointer;
  }
  img:hover {
    transform: scale(1.1);
    box-shadow: 0 5px 15px rgba(0,0,0,0.2);
  }

  button[type="submit"] {
    margin-top: 20px;
    background-color: #4CAF50;
    color: white;
    font-size: 18px;
    padding: 14px 45px;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    font-weight: 600;
    transition: background-color 0.3s ease;
    display: block;
    width: 100%;
    max-width: 300px;
    margin-left: auto;
    margin-right: auto;
  }
  button[type="submit"]:hover {
    background-color: #388E3C;
  }

  @media screen and (max-width: 600px) {
    body {
      padding: 20px 5%;
      font-size: 14px;
    }
    img {
      width: 70px;
      height: 70px;
    }
    button[type="submit"] {
      padding: 12px 25px;
      font-size: 16px;
    }
  }
</style>
</head>
<body>

<a href="return.do">관리자페이지로 가기</a>

<br>
<form action="MProductDetails.do" method="post">
  <input type="hidden" name="company_name" value="${company_name}">
  <c:set var="contextPath" value="${pageContext.request.contextPath}" />
  <table>
    <thead>
      <tr>
        <th>선택</th>
        <th>상품이름</th>
        <th>상품 가격</th>
        <th>감정</th>
        <th>카테고리</th>
        <th>이미지</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${product}">
      <tr>
        <td><input type="radio" name="product_id" value="${product.product_id}" required/></td>
        <td>${product.product_name}</td>
        <td><fmt:formatNumber value="${product.product_price}" type="number" groupingUsed="true" />원</td>
        <td>${product.product_mood}</td>
        <td>${product.product_category}</td>
             <td>
  <c:choose>
    <c:when test="${not empty product.product_image}">
      <img src="${contextPath}/resources/img/${product.product_image}"
           onclick="openModal('${contextPath}/resources/img/${product.product_image}')" />
    </c:when>
    <c:otherwise>
      <img src="${contextPath}/images/noimage.jpg"
           onclick="openModal('${contextPath}/images/noimage.jpg')" />
    </c:otherwise>
  </c:choose>
</td>
      </tr>
    </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="6"><button type="submit" name="action" value="more">더보기</button></td>
      </tr>
    </tfoot>
  </table>
</form>
<c:if test="${not empty ok}">
  <script>
    alert("<c:out value='${ok}'/>");
  </script>
</c:if>
</body>
</html>
