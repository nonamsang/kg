<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>선택한 리뷰 댓글 전체보기</title>

<style>
body {
    font-family: 'Arial', sans-serif;
    padding: 40px;
    background-color: #a8c0d6; /* 아주 연한 하늘색 */
    min-height: 100vh;
}

h1 {
    text-align: center;
    color: #222;
    font-size: 2.5rem;
    margin-bottom: 15px;
    animation: fadeInDown 1s ease;
}

h4 {
    text-align: center;
    color: #555;
    font-size: 1rem;
    margin-bottom: 30px;
}

@keyframes fadeInDown {
    0% { opacity: 0; transform: translateY(-50px); }
    100% { opacity: 1; transform: translateY(0); }
}

a {
    display: inline-block;
    margin: 20px 0;
    text-decoration: none;
    color: #007bff;
    font-weight: bold;
    border: 1px solid #007bff;
    padding: 8px 16px;
    border-radius: 5px;
    transition: background-color 0.3s, color 0.3s, transform 0.2s;
}

a:hover {
    background-color: #007bff;
    color: #fff;
    transform: scale(1.05);
}

.review-card {
    background-color: #fff;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
    margin: 0 auto 40px auto;
    max-width: 700px;
    transition: transform 0.3s, box-shadow 0.3s;
}

.review-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.review-card h3 {
    margin-bottom: 20px;
    color: #444;
}

.review-card p {
    margin: 8px 0;
    font-size: 1rem;
}

.review-content {
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 10px;
    background-color: #fafafa;
    white-space: pre-wrap;
    margin-top: 20px;
    font-size: 1rem;
    line-height: 1.5;
}

.comment-card {
    border: 1px solid #ddd;
    padding: 20px;
    margin: 20px auto;
    max-width: 700px;
    border-radius: 10px;
    background-color: #fff;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    transition: transform 0.3s, box-shadow 0.3s;
}

.comment-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.12);
}

.comment-card p {
    margin: 8px 0;
    font-size: 0.95rem;
}

.button-group {
    margin-top: 15px;
}

.button-group button {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 8px 16px;
    border-radius: 5px;
    cursor: pointer;
    margin-right: 10px;
    font-size: 0.9rem;
    transition: background-color 0.3s, transform 0.2s;
}

.button-group button:hover {
    background-color: #0056b3;
    transform: scale(1.05);
}
</style>

</head>
<body>

<h1>리뷰 댓글 전체보기</h1>
<a href="MytoReviewList.do">뒤로가기</a>

<div class="review-card">
    <h3>리뷰 ID: ${param.review_id}</h3>
    <p><strong>작성자:</strong> ${review.user_id}</p>
    <p><strong>작성일:</strong> ${review.review_date}</p>

    <div class="review-content">${review.review_content}</div>
</div>

<!-- 댓글 목록 출력 -->
<c:forEach var="sublist2" items="${sublist}">
    <div class="comment-card">
        <p><strong>댓글 ID:</strong> ${sublist2.sub_id}</p>
        <p><strong>작성일:</strong> ${sublist2.sub_date}</p>
        <p><strong>내용:</strong> ${sublist2.sub_content}</p>
        <p><strong>작성자:</strong> ${sublist2.user_id}</p>

        <c:if test="${sublist2.user_id == loginUserId}">
            <div class="button-group">
                <form action="MyReviewSubUpdateForm.do" method="get" style="display:inline;">
                    <input type="hidden" name="sub_id" value="${sublist2.sub_id}" />
                    <button type="submit">수정</button>
                </form>

                <form action="MyReviewSubDelete.do" method="get" style="display:inline;">
                    <input type="hidden" name="sub_id" value="${sublist2.sub_id}" />
                    <button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                </form>
            </div>
        </c:if>
    </div>
</c:forEach>

</body>
</html>
