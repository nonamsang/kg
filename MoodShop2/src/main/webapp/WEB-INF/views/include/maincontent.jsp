<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<head>
<meta charset="UTF-8">
<title>감정별 상품 리스트</title>

<style>
.product_container {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	padding: 20px;
	justify-content: flex-start;
}

.product_item {
	position: relative;
	width: 200px;
	margin: 10px 20px;
	text-align: left;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	border-radius: 4px;
	background-color: #fff;
	padding: 10px;
}

.product_item img {
	width: 100%;
	height: auto;
	border-radius: 4px;
}

.emotion_title {
	font-size: 20px;
	font-weight: bold;
	margin: 0 0 0 50px;
	text-align: left;
	background-color: transparent;
	color: inherit;
	padding: 0;
	border: none;
	user-select: text;
}

.emotion_line {
	width: 100%;
	height: 2px;
	background-color: #ccc;
	margin: 10px 0 0 0;
}
</style>
</head>

<body>

<c:set var="product_mood_list" value="기쁨,슬픔,분노,사랑,창피,아무렇지 않음" />
<c:forTokens var="mood" items="${product_mood_list}" delims=",">

	<!-- 감정명 출력 -->
	<div class="emotion_title">${mood}</div>
	<div class="emotion_line"></div>

	<!-- 상품 리스트 출력 -->
	<div class="product_container">
		<c:forEach var="product" items="${productList}">
			<c:if test="${product.mood eq mood}">
				<div class="product_item">
					<!-- DB에서 받아온 이미지 파일명으로 출력 -->
					<img src="${pageContext.request.contextPath}/resources/img/${product.image}" alt="상품 이미지">
					<p>
						<a href="MainProductCompany.do?productId=${product.id}">회사명: ${product.company}</a>
					</p>
					<p>
						<a href="MainProductName.do?productId=${product.id}">상품명: ${product.name}</a>
					</p>


					<p>가격: ${product.price}원</p>
				</div>
			</c:if>
		</c:forEach>
	</div>
	</c:forTokens>
</body>
