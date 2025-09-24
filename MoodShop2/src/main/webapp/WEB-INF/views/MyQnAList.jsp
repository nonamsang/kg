<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 QnA 목록</title>

<link rel="icon"
	href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png"
	type="image/png">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<style>
html, body {
	height: 100%;
	margin: 0;
}

body {
	padding-top: 120px;
	background-color: #f8f9fa;
	font-family: 'Arial', sans-serif;
	min-height: 100vh;
	display: flex;
	flex-direction: column;
}

.container {
	flex: 1;
}

.qna-table {
	background-color: white;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.qna-table tbody tr {
	cursor: pointer;
}

.qna-table tbody tr:hover {
	background-color: #f0f0f0;
}

.pagination a, .pagination strong {
	margin: 0 5px;
	text-decoration: none;
}

.btn-custom {
	background-color: #000;
	color: #fff;
	border: none;
	padding: 10px 20px;
	font-weight: bold;
	border-radius: 8px;
	transition: background-color 0.3s ease, transform 0.2s ease;
}

.btn-custom:hover {
	background-color: #333;
	transform: translateY(-2px);
}

.btn-outline-custom {
	background-color: transparent;
	color: #000;
	border: 2px solid #000;
	padding: 8px 18px;
	font-weight: bold;
	border-radius: 8px;
	transition: background-color 0.3s ease, color 0.3s ease;
}

.btn-outline-custom:hover {
	background-color: #000;
	color: #fff;
}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/views/include/header.jsp" />

	<div class="container">
		<h2 class="text-center mb-4">내 Q&A 목록</h2>

		<table class="table table-hover text-center qna-table">
			<thead class="table-dark">
				<tr>
					<th style="width: 20%;">작성일</th>
					<th style="width: 10%;">글번호</th>
					<th style="width: 40%;">제목</th>
					<c:choose>
						<c:when test="${userType eq 'user'}">
							<th style="width: 15%;">내 닉네임</th>
						</c:when>
						<c:when test="${userType eq 'manager'}">
							<th style="width: 15%;">질문자</th>
						</c:when>
					</c:choose>
					<th style="width: 15%;">답변여부</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="qna" items="${qnaAllList}">
				<c:choose>
				<c:when test="${editAllowedMap[qna.qId] != null && editAllowedMap[qna.qId]}">
					<tr onclick="location.href='MainLetter.do?id=${qna.qId}&from=modify'">
						<td><fmt:formatDate value="${qna.qDate}" pattern="yyyy-MM-dd(EEEE) HH:mm:ss" /></td>
						<td>${qna.qId}</td>
						<td>${qna.qTitle}(수정 가능)</td>
						<td>${qna.nickname}</td>
						<td>
							<c:choose>
								<c:when test="${answerMap[qna.qId]}">답변 완료</c:when>
								<c:otherwise>미답변</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td><fmt:formatDate value="${qna.qDate}" pattern="yyyy-MM-dd(EEEE) HH:mm:ss" /></td>
						<td>${qna.qId}</td>
						<td>${qna.qTitle}[수정불가]</td>
						<td>${qna.nickname}</td>
						<td>
							<c:choose>
								<c:when test="${answerMap[qna.qId]}">답변 완료</c:when>
								<c:otherwise>미답변</c:otherwise>
							</c:choose>
						</td>
					</tr>
					</c:otherwise>
				</c:choose>
				</c:forEach>

				<c:if test="${empty qnaAllList}">
					<tr>
						<td colspan="5" style="text-align: center;">표시할 QnA가 없습니다.</td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<div class="d-flex justify-content-between align-items-center mt-4">
			<button class="btn btn-outline-primary"
				onclick="location.href='qnaAllList.do'">전체목록으로 돌아가기</button>

			<div class="pagination">
				<c:if test="${currentPage > 1}">
					<a href="myQnaList.do?page=${currentPage - 1}">◀ 이전</a>
				</c:if>

				<c:forEach var="i" begin="1" end="${totalPage}">
					<c:choose>
						<c:when test="${i == currentPage}">
							<strong>[${i}]</strong>
						</c:when>
						<c:otherwise>
							<a href="myQnaList.do?page=${i}">[${i}]</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${currentPage < totalPage}">
					<a href="myQnaList.do?page=${currentPage + 1}">다음 ▶</a>
				</c:if>
			</div>

			<c:if test="${userType eq 'user'}">
				<button class="btn btn-outline-primary"
					onclick="location.href='MainWrite.do'">작성하기</button>
			</c:if>
		</div>
	</div>
	<br>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
