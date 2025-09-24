<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상품 수정</title>
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #f4f6f9;
	padding: 30px;
}

h2 {
	text-align: center;
	color: #333;
	margin-bottom: 40px;
}

.product-form {
	display: flex; /* flex로 변경 */
	justify-content: center; /* 가운데 정렬 */
	flex-wrap: wrap;
	grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
	/* 최대 3열 */
	gap: 30px;
}

.product-card {
	background: white;
	border-radius: 12px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	padding: 25px;
	display: flex;
	flex-direction: column;
	  width: 350px; 
	gap: 14px;
	height: 100%;
}

.product-card img {
	border: 1px solid #ddd;
	border-radius: 6px;
	width: 100%;
	height: 200px;
	object-fit: cover;
}

.product-card label {
	font-weight: bold;
	font-size: 15px;
	margin-top: 6px;
}

.product-card input[type="text"], .product-card select, .product-card input[type="file"]
	{
	width: 100%;
	padding: 10px;
	font-size: 14px;
	border: 1px solid #ccc;
	border-radius: 6px;
	box-sizing: border-box;
}

.submit-btn {
	display: block;
	margin: 40px auto;
	padding: 14px 40px;
	background-color: #4CAF50;
	border: none;
	color: white;
	border-radius: 10px;
	font-size: 18px;
	cursor: pointer;
}

.submit-btn:hover {
	background-color: #3f9b41;
}
</style>
</head>
<body>

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="categories" value="Goods,Flower" />
	<c:set var="product_mood_list" value="기쁨,슬픔,분노,사랑,불안,평온" />

	<h2>상품 정보 수정</h2>

	<form action="MProductUpdate.do" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="company_name"
			value="${productlist[0].company_name}" />
		<div class="product-form">

			<c:forEach var="product" items="${productlist}" varStatus="status">
				<div class="product-card">
					<input type="hidden" name="product_id"
						value="${product.product_id}" /> <input type="hidden"
						name="original_image" value="${product.product_image}" /> <label>상품
						ID</label>
					<div>${product.product_id}</div>

					<label>상품 이름</label> <input type="text" name="product_name"
						value="${product.product_name}" /> <label>상품 가격</label> <input
						type="text" name="product_price" value="${product.product_price}" />

					<label>카테고리</label> <select name="product_category">
						<c:forEach var="category" items="${fn:split(categories, ',')}">
							<option value="${category}"
								<c:if test="${category == product.product_category}">selected</c:if>>${category}</option>
						</c:forEach>
					</select> <label>감정</label> <select name="product_mood">
						<c:forEach var="mood" items="${fn:split(product_mood_list, ',')}">
							<option value="${mood}"
								<c:if test="${mood == product.product_mood}">selected</c:if>>${mood}</option>
						</c:forEach>
					</select> <label>기존 이미지</label>
					<c:if test="${not empty product.product_image}">
						<img
							src="${contextPath}/resources/img/${product.product_image}?v=${status.index}" />
					</c:if>

					<label>새 이미지 선택</label> <input type="file" name="product_image" />
				</div>
			</c:forEach>
		</div>

		<input type="submit" value="수정하기" class="submit-btn" />
	</form>

</body>
</html>
