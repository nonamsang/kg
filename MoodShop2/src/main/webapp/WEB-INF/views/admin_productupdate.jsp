<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상품 수정</title>
</head>
<body>
<form action="/MProductUpdate.do" method="post">
<c:set var="categories" value="상의,하의,모자,양말" />
<c:set var="product_mood_list" value="기쁨,슬픔,분노,사랑,불안,평온" />
    <input type="hidden" name="company_id" value="${productlist[0].company_id}"/>
    <table border="1">
        <tr>
            <th>상품아이디</th>
            <th>상품이름</th>
            <th>상품 가격</th>
            <th>기분</th>
            <th>카테고리</th>
            <th>이미지</th>
        </tr>
        <c:forEach var="product" items="${productlist}" varStatus="status">
            <input type="hidden" name="product_id" value="${product.product_id}" />
            <tr>
                <td>${product.product_id}</td>
                <td><input type="text" name="product_name" value="${product.product_name}" /></td>
                <td><input type="text" name="product_price" value="${product.product_price}" /></td>
                <td>
                    <select name="product_mood">
                        <c:forEach var="mood" items="${fn:split(product_mood_list, ',')}">
                            <option value="${mood}" <c:if test="${mood == product.product_mood}">selected</c:if>>
                                ${mood}
                            </option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <select name="product_category">
                        <c:forEach var="category" items="${fn:split(categories, ',')}">
                            <option value="${category}" <c:if test="${category == product.product_category}">selected</c:if>>
                                ${category}
                            </option>
                        </c:forEach>
                    </select>
                </td>
                <td><input type="text" name="product_image" value="${product.product_image}" /></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="수정하기" />
</form>

</body>
</html>