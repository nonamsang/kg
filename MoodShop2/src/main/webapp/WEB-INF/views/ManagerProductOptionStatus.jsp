<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상품 더보기</title>
</head>
<body>
<form action="MProductUpdate.do" method=post enctype="multipart/form-data">
  <c:set var="contextPath" value="${pageContext.request.contextPath}" />
<input type=hidden name=company_name value="${company_name}">
<table border=1>
<tr>
	  <th>상품아이디</th>
      <th>상품이름</th>
      <th>상품 가격</th>
      <th>감정</th>
      <th>카테고리</th>
      <th>이미지</th>
    </tr>
<c:forEach var="product" items="${productlist}">
  <table border="1" style="margin-bottom:20px; width:800px">
    <tr>
      <th>상품아이디</th>
      <th>상품이름</th>
      <th>상품 가격</th>
      <th>감정</th>
      <th>카테고리</th>
      <th>이미지</th>
    </tr>
    <tr>
      <td>
        ${product.product_id}
        <input type="hidden" name="product_id" value="${product.product_id}" />
      </td>
      <td>${product.product_name}<input type="hidden" name="product_name" value="${product.product_name}" /></td>
      <td>${product.product_price}<input type="hidden" name="product_price" value="${product.product_price}" /></td>
      <td>${product.product_mood}<input type="hidden" name="product_mood" value="${product.product_mood}" /></td>
      <td>${product.product_category}<input type="hidden" name="product_category" value="${product.product_category}" /></td>
      <td>
        <img src="${contextPath}/${product.product_image}" width="100px" height="100px" />
        <input type="hidden" name="product_image" value="${product.product_image}" />
      </td>
    </tr>
  </table>

  <c:set var="hasOption" value="false" />
  <c:forEach var="option" items="${optionlist}">
    <c:if test="${option.product_id == product.product_id}">
      <c:set var="hasOption" value="true" />
    </c:if>
  </c:forEach>

  <c:if test="${hasOption}">
    <table border="1" style="margin-bottom:30px; width:800px">
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
              <c:if test="${not empty option.option_picture}">
                <img src="${contextPath}/${option.option_picture}" width="100" height="100" />
              </c:if>
            </td>
          </tr>
        </c:if>
      </c:forEach>
    </table>
  </c:if>
</c:forEach>
    </table>
    <input type=submit value="상품목록으로가기"></form>
</body>
</html>