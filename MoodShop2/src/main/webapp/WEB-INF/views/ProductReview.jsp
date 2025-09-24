<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상품 리뷰 게시판</title> <!-- ✅ 원래 제목 유지 -->

<style>
body {
    font-family: 'Arial', sans-serif;
    padding: 40px;
    background-color: #a8c0d6; /* 기존 배경 유지 */
    overflow: hidden; /* 눈이 화면 바깥으로 안 보이게 */
    position: relative;
}

h2 {
    text-align: center;
    margin-bottom: 40px;
    font-size: 2.2rem;
    color: #333;
}

a {
    margin: 0 10px;
    text-decoration: none;
    color: #007bff;
    font-weight: bold;
    transition: color 0.3s;
}

a:hover {
    color: #0056b3;
}

.review-arena {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 50px;
    flex-wrap: wrap;
}

.review-card {
    background-color: #fff;
    width: 300px; 
    height: 500px; 
    padding: 15px;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s, box-shadow 0.3s;
    cursor: pointer;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.review-card:hover {
    transform: scale(1.05);
    box-shadow: 0 0 15px #007bff;
}

.review-header {
    text-align: center;
    font-weight: bold;
    font-size: 1rem;
    color: #555;
}

.review-image {
    width: 100%;
    height: 250px; 
    object-fit: contain; 
    border-radius: 8px;
    margin: 10px 0;
    background-color: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
}

.review-image img {
    max-width: 100%;
    max-height: 100%;
}

.review-content-box {
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #fafafa;
    white-space: pre-wrap;
    font-size: 0.9rem;
    color: #333;
    flex-grow: 1;
    overflow-y: auto;
}

.star-display {
    margin: 10px 0;
    text-align: center;
}

.star-display span {
    font-size: 1.4rem;
    margin-right: 4px;
    color: #ffc107;
}

.review-actions {
    margin-top: 10px;
    display: flex;
    justify-content: center;
    gap: 8px;
    flex-wrap: wrap;
}

.review-actions button,
.review-actions a {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 6px 12px;
    border-radius: 5px;
    cursor: pointer;
    text-decoration: none;
    transition: background-color 0.3s;
    font-size: 0.9rem;
}

.review-actions button:hover,
.review-actions a:hover {
    background-color: #0056b3;
}

/* ✅ 눈 애니메이션 CSS */
.snowflake {
    position: fixed;
    top: -10px;
    color: white;
    font-size: 1.2rem;
    animation-name: fall;
    animation-timing-function: linear;
       animation-duration: 10s; /* ✅ 기본값 필요 */
    pointer-events: none; /* 눈 클릭 방지 */
}

@keyframes fall {
    0% { transform: translateY(-10px); }
    100% { transform: translateY(110vh); }
}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<h2>리뷰 게시판</h2> <!-- ✅ 제목 절대 유지 -->

<div class="review-arena">
    <c:forEach var="review" items="${reviewlist}" varStatus="status" begin="0" end="1">
        <div class="review-card">

            <div class="review-header">
                <div><strong>${review.user_id}</strong></div>
                <div>${review.review_date}</div>
            </div>

            <div class="star-display">
                <c:forEach var="i" begin="1" end="5">
                    <c:choose>
                        <c:when test="${i <= review.rating}">
                            <span>&#9733;</span>
                        </c:when>
                        <c:otherwise>
                            <span style="color: #ddd;">&#9733;</span>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>

            <div class="review-image">
                <c:if test="${not empty review.review_image}">
                    <img src="${pageContext.request.contextPath}/resources/reviewupload/${review.review_image}" />
                </c:if>
            </div>

            <div class="review-content-box">${review.review_content}</div>

            <div class="review-actions">
                <a href="MyReviewSubAll.do?review_id=${review.review_id}">댓글보기</a>
                <a href="MyReviewSubInsertForm.do?review_id=${review.review_id}">댓글달기</a>
            </div>

            <c:if test="${review.user_id eq loginUserId}">
                <div class="review-actions">
                    <form action="MyReviewUpdateForm.do" method="get" style="display: inline;">
                        <input type="hidden" name="review_id" value="${review.review_id}" />
                        <button type="submit">리뷰 수정</button>
                    </form>

                    <form action="MyReviewDelete.do" method="get" style="display: inline;">
                        <input type="hidden" name="review_id" value="${review.review_id}" />
                        <input type="hidden" name="user_id" value="${review.user_id}" />
                        <button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')">리뷰 삭제</button>
                    </form>
                </div>
            </c:if>

        </div>
    </c:forEach>
</div>

<!-- ✅ 눈 내리는 효과 스크립트 -->
<script>
    function createSnowflake() {
        const snowflake = document.createElement('div');
        snowflake.classList.add('snowflake');
        snowflake.style.left = Math.random() * 100 + 'vw';
        snowflake.style.opacity = Math.random();
        snowflake.style.fontSize = (Math.random() * 10 + 10) + 'px';
        snowflake.style.animationDuration = (Math.random() * 3 + 5) + 's';
        snowflake.innerText = '❄';

        document.body.appendChild(snowflake);

        setTimeout(() => {
            snowflake.remove();
        }, 8000);
    }

    setInterval(createSnowflake, 300);
</script>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
