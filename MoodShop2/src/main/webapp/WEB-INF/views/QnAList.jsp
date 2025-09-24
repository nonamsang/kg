<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 목록</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
html, body {
	background-color: #f8f9fa;
	font-family: 'Arial', sans-serif;
	height: 100%;
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

.wrapper {
	min-height: 100%;
	display: flex;
	flex-direction: column;
}

.container {
	flex: 1;
}

.pagination-wrapper {
	margin-top: 30px;
	display: flex;
	justify-content: center; /* ✅ 중앙 정렬 */
}

.pagination a, .pagination strong {
	margin: 0 5px;
	text-decoration: none;
	font-weight: bold;
	color: #000;
}

.pagination a:hover {
	text-decoration: underline;
}

.btn-area {
	margin-top: 30px;
	display: flex;
	justify-content: space-between;
}
</style>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/views/include/header.jsp" />

		<div class="container">
			<h2 class="text-center mb-4">Q&A 목록</h2>

			<table class="table table-hover text-center qna-table">
				<thead class="table-dark">
					<tr>
						<th style="width: 20%;">작성일</th>
						<th style="width: 10%;">글번호</th>
						<th style="width: 40%;">제목</th>
						<th style="width: 15%;">작성자</th>
						<th style="width: 15%;">답변여부</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="qna" items="${qnaAllList}">
						<tr onclick="location.href='MainLetter.do?id=${qna.qId}&from=list'">
							<td><fmt:formatDate value="${qna.qDate}" pattern="yyyy-MM-dd(EEEE)  HH:mm:ss" /></td>
							<td>${qna.qId}</td>
							<td>${qna.qTitle}</td>
							<td>${qna.nickname}</td>
							<td>
								<c:choose>
									<c:when test="${answerMap[qna.qId]}">답변 완료</c:when>
									<c:otherwise>미답변</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!-- 버튼 영역 -->
			<div class="btn-area">
				<button class="btn btn-outline-primary" onclick="location.href='MainQandA.do'">내 Q&A로 이동하기</button>
			</div>

			<!-- 페이지네이션 중앙 배치 -->
			<div class="pagination-wrapper">
				<div class="pagination">
					<c:if test="${currentPage > 1}">
						<a href="qnaAllList.do?page=${currentPage - 1}">◀ 이전</a>
					</c:if>

					<c:forEach begin="1" end="${totalPage}" var="i">
						<c:choose>
							<c:when test="${i == currentPage}">
								<strong>[${i}]</strong>
							</c:when>
							<c:otherwise>
								<a href="qnaAllList.do?page=${i}">[${i}]</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:if test="${currentPage < totalPage}">
						<a href="qnaAllList.do?page=${currentPage + 1}">다음 ▶</a>
					</c:if>
				</div>
			</div>
		</div>
		<br>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
