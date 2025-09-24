<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
.recent-product-body {   /* 고유 클래스명 사용 */
	padding: 40px;
	font-family: 'Arial', sans-serif;
	background-color: #f8f9fa;
}

table {
	width: 80%;
	margin: auto;
	border-collapse: collapse;
	background-color: #fff;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	border-radius: 12px;
	overflow: hidden;
}

th, td {
	padding: 14px;
	border-bottom: 1px solid #eee;
	text-align: center;
}

th {
	background-color: #f2f2f2;
	font-size: 16px;
}

img {
	width: 100px;
	height: 100px;
	object-fit: cover;
	border-radius: 8px;
	cursor: pointer;
}

.product-link {
	text-decoration: none;
	color: black;
	font-weight: bold;
}

.product-link:hover {
	color: #007bff;
}

.delete-btn {
	background-color: red;
	color: white;
	border: none;
	padding: 6px 12px;
	border-radius: 4px;
	cursor: pointer;
}

.delete-btn:hover {
	background-color: darkred;
}

.page-title {
	text-align: center;
	margin-bottom: 30px;
	font-size: 28px;
	font-weight: bold;
}

.button-area {
	text-align: center;
	margin-top: 30px;
}

.button-area a {
	margin: 0 10px;
    text-decoration: none;
    color: white;
    background-color: #007bff;
    padding: 10px 20px;
    border-radius: 6px;
    cursor: pointer;
}

.button-area a:hover {
	background-color: #0056b3;
}
</style>
<div class="recent-product-body">
<div class="page-title">내가 본 상품 목록</div>

<table>
	<thead>
		<tr>
			<th>상품 이미지</th>
			<th>상품명</th>
			<th>조회 일시</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody id="recentViewTableBody">
		<c:forEach var="recent" items="${recentList}">
			<tr id="recentRow-${recent.recent_id}">
				<td><a href="MainProductName.do?product_id=${recent.product_id}"> 
					<img src="${pageContext.request.contextPath}/resources/img/${recent.product_image}" alt="상품 이미지" />
				</a></td>
				<td><a href="MainProductName.do?product_id=${recent.product_id}" class="product-link">${recent.product_name}</a></td>
				<td><fmt:formatDate value="${recent.view_date}" pattern="yyyy.MM.dd HH:mm" /></td>
				<td>
					<button type="button" class="delete-btn" onclick="deleteRecent('${recent.recent_id}')">삭제</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="button-area">
	<a href="#" onclick="deleteAllRecent()" class="btn-link">전체 삭제</a>
	<a href="MyPage.do">마이페이지로 돌아가기</a> 
	<a href="MainMoodShop.do">홈으로 가기</a>
</div>
</div>
<script>
	// 최근 본 상품 삭제 AJAX
	function deleteRecent(recentId) {
		if (!confirm("정말 삭제하시겠습니까?")) return;

		$.ajax({
			url: "${pageContext.request.contextPath}/deleteRecent.do",
			type: "POST",
			data: {recentId: recentId},
			success: function() {
				$("#recentRow-" + recentId).remove();
				alert("삭제되었습니다.");
			},
			error: function() {
				alert("삭제 요청에 실패했습니다.");
			}
		});
	}

	// 전체 삭제
	function deleteAllRecent() {
		if (!confirm("정말 모든 상품을 삭제하시겠습니까?")) return;

		$.ajax({
			url: "${pageContext.request.contextPath}/deleteAllRecent.do",
			type: "POST",
			success: function() {
				$("#recentViewTableBody").empty();
				alert("모든 상품이 삭제되었습니다.");
			},
			error: function() {
				alert("전체 삭제 요청에 실패했습니다.");
			}
		});
	}
</script>
