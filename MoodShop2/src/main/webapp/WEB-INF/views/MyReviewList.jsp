<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
.review-product-body {
	font-family: 'Arial', sans-serif;
	padding: 50px;
	background-color: #f5f5f5;
}

.review-list {
	max-width: 1000px;
	margin: 0 auto;
}

.review-item {
	background: #fff;
	border-radius: 10px;
	padding: 20px;
	margin-bottom: 20px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.review-info {
	display: flex;
	align-items: center;
	gap: 20px;
}

.review-info img {
	width: 80px;
	height: 80px;
	object-fit: cover;
	border-radius: 8px;
}

.review-details {
	display: flex;
	flex-direction: column;
	gap: 5px;
}

.review-actions a, .review-actions form {
	margin-left: 10px;
	text-decoration: none;
	color: #007bff;
	border: 1px solid #007bff;
	padding: 5px 10px;
	border-radius: 5px;
	transition: 0.3s;
	display: inline-block;
}

.review-actions a:hover {
	background: #007bff;
	color: #fff;
}

.review-actions button {
	background: #fff;
	border: none;
	color: #007bff;
	cursor: pointer;
	padding: 0;
	font-size: 1rem;
}

.review-actions button:hover {
	text-decoration: underline;
}
</style>
<div class="review-product-body">
<h2 style="text-align: center; margin-bottom: 30px;">나의 리뷰</h2>
<div class="review-list">
	<c:forEach var="review" items="${reviews}" varStatus="status">
		<div class="review-item">
			<div class="review-info">
				<c:if test="${not empty review.review_image}">
					<img
						src="${pageContext.request.contextPath}/resources/reviewupload/${review.review_image}"
						alt="리뷰 이미지">
				</c:if>
				<div class="review-details">
					<strong>${status.index + 1}. ${review.user_id}</strong> <span>${review.review_date}</span>
					<div>
						<c:forEach var="i" begin="1" end="5">
							<c:choose>
								<c:when test="${i <= review.rating}">
									<span style="color: gold; font-size: 1.2rem;">&#9733;</span>
								</c:when>
								<c:otherwise>
									<span style="font-size: 1.2rem;">&#9733;</span>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="review-actions">
				<a href="MyReviewSubAll.do?review_id=${review.review_id}">댓글보기</a> <a
					href="MyReviewSubInsertForm.do?review_id=${review.review_id}">댓글달기</a>
				<c:if test="${review.user_id eq loginUserId}">
					<form action="MyReviewUpdateForm.do" method="get"
						style="display: inline;">
						<input type="hidden" name="review_id" value="${review.review_id}" />
						<button type="submit">리뷰 수정</button>
					</form>
					<form action="MyReviewDelete.do" method="get"
						style="display: inline;">
						<input type="hidden" name="review_id" value="${review.review_id}" />
						<input type="hidden" name="user_id" value="${review.user_id}" />
						<button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')">리뷰
							삭제</button>
					</form>
				</c:if>
			</div>
		</div>
	</c:forEach>
</div>
</div>