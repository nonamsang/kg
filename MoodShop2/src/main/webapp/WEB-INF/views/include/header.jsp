<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<head>
<meta charset="UTF-8">
<title>Welcome to MOODSHOP</title>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
<style>
/* 전체 초기화 */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Arial', sans-serif;
	padding-top: 120px;
}

/* Header Wrapper (2단 구성) */
.header {
	width: 100%;
	background-color: #000000;
	border-bottom: #444;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 1000;
	color: #ffffff;
	box-shadow: 0 2px 10px rgba(0,0,0,0.3);
}

/* ✅ 헤더 상단: 좌/우 정렬 */
.header-top {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 20px 40px;
}

/* 로고 + 여름 이벤트 묶음 */
.logo-wrapper {
	display: flex;
	align-items: center;
	gap: 15px;
}

.logo {
	font-size: 28px;
	font-weight: bold;
	color: #ffffff;
	cursor: pointer;
	transition: transform 0.3s;
}

.logo:hover {
	transform: scale(1.05);
}

/* 여름 이벤트 버튼 */
.summer-event {
	padding: 8px 15px;
	text-decoration: none;
	color: white;
	font-weight: bold;
	border-radius: 20px;
	background: linear-gradient(270deg, #ff7f50, #ffcccb, #ff7f50);
	background-size: 400% 400%;
	animation: gradientMove 6s ease infinite, floatMove 2s ease-in-out infinite;
	box-shadow: 0 0 10px rgba(255, 127, 80, 0.7);
	transition: transform 0.3s, box-shadow 0.3s;
}

.summer-event:hover {
	transform: scale(1.05);
	box-shadow: 0 0 15px rgba(255, 127, 80, 0.9);
}

@keyframes gradientMove {
	0% { background-position: 0% 50%; }
	50% { background-position: 100% 50%; }
	100% { background-position: 0% 50%; }
}

@keyframes floatMove {
	0% { transform: translateY(0); }
	50% { transform: translateY(-3px); }
	100% { transform: translateY(0); }
}

/* 우측 메뉴 */
.menu_right a {
	margin-left: 20px;
	text-decoration: none;
	color: #ffffff;
	font-weight: bold;
	transition: color 0.3s;
}

.menu_right a:hover {
	color: #ff7f50;
}

/* ✅ 헤더 하단: 중앙 메뉴 */
.menu {
	display: flex;
	justify-content: center;
	gap: 50px;
	margin: 10px 0 15px 0;
}

.menu_item {
	position: relative;
}

.content1 a {
	text-decoration: none;
	padding: 10px 15px;
	display: block;
	color: #ffffff;
	font-weight: bold;
	transition: color 0.3s;
}

.content1 a:hover {
	color: #ff7f50;
}

.submenu {
	display: none;
	position: absolute;
	top: 40px;
	left: 0;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 8px;
	min-width: 120px;
	box-shadow: 0 4px 10px rgba(0,0,0,0.2);
	z-index: 999;
	animation: fadeIn 0.3s ease forwards;
}

.submenu a {
	display: block;
	padding: 10px;
	color: #333;
	text-decoration: none;
	transition: background 0.3s;
}

.submenu a:hover {
	background-color: #f0f0f0;
}

.menu_item:hover .submenu {
	display: block;
}

@keyframes fadeIn {
	from { opacity: 0; transform: translateY(-10px); }
	to { opacity: 1; transform: translateY(0); }
}

/* 검색창 */
.search {
	margin: 50px auto 40px auto;
	width: 500px;
	height: 40px;
	background: white;
	border-radius: 40px;
	padding: 0 15px;
	border: 1px solid #ddd;
	display: flex;
	align-items: center;
	box-sizing: border-box;
	box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.search_txt {
	border: none;
	outline: none;
	flex-grow: 1;
	font-size: 16px;
	color: #333;
	background: transparent;
	padding-left: 15px;
}

.search_btn {
	color: #888;
	width: 40px;
	height: 40px;
	border-radius: 50%;
	background: none;
	border: none;
	font-size: 16px;
	display: flex;
	justify-content: center;
	align-items: center;
	cursor: pointer;
	transition: color 0.3s;
}

.search_btn:hover {
	color: #ff7f50;
}

.no-sub-links {
	display: flex;
	gap: 25px;
}
</style>
</head>

<body>
	<div class="header">

		<!-- ✅ 헤더 상단 -->
		<div class="header-top">

			<!-- 로고 + 여름 이벤트 묶음 -->
			<div class="logo-wrapper">
				<div class="logo" onclick="location.href='MainMoodShop.do'">MOOD SHOP</div>
				<a href="eventMainDetail.do" class="summer-event">여름 이벤트</a>
			</div>

			<!-- 우측 메뉴 -->
			<div class="menu_right">
				<a href="MainOffline.do">오프라인 매장</a>
				<a href="MyPage.do">마이페이지</a>
			</div>

		</div>

		<!-- ✅ 헤더 하단 (중앙 메뉴) -->
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

		<c:set var="categories" value="Flower,Goods" />
		<c:set var="product_mood_list" value="기쁨,슬픔,분노,사랑,불안,평온" />

		<div class="menu">
			<!-- 이벤트 항목 (서브메뉴 없음) -->
			<div class="menu_item no-sub">
				<div class="content1 no-sub-links">
					<a href="HeaderMainBrand.do">Brand</a>
					<a href="Notice.do">Notice</a>
					<a href="eventMainDetail.do">Event</a>
				</div>
			</div>

			<c:forEach var="product_category" items="${fn:split(categories, ',')}">
				<div class="menu_item">
					<div class="content1">
						<a href="HeaderProductCategoryGo.do?product_category=${product_category}">${product_category}</a>
					</div>
					<div class="submenu">
						<c:forEach var="mood" items="${fn:split(product_mood_list, ',')}">
							<a href="HeaderProductMood.do?product_category=${product_category}&product_mood=${mood}">${mood}</a>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</div>

	</div>

	<!-- ✅ 검색창 -->
	<form class="search" action="Search.do" method="get">
		<input class="search_txt" type="text" name="search" placeholder="검색어를 입력하세요.">
		<button class="search_btn" type="submit">
			<i class="fa-solid fa-magnifying-glass"></i>
		</button>
	</form>

</body>
