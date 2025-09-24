<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 리뷰 AJAX</title>
<style>
body { font-family: 'Arial', sans-serif; background-color: #fff; }
.review-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; }
.review-card { perspective: 1000px; cursor: pointer; }
.review-inner { position: relative; width: 100%; height: 300px; transition: transform 0.6s; transform-style: preserve-3d; }
.review-card.flipped .review-inner { transform: rotateY(180deg); }
.review-front, .review-back { position: absolute; width: 100%; height: 100%; backface-visibility: hidden; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 15px; }
.review-front { background: #fff; }
.review-front img { width: 80%; height: 150px; object-fit: cover; border-radius: 8px; margin-bottom: 10px; }
.review-back { background: #fafafa; transform: rotateY(180deg); overflow-y: auto; }
.review-actions a { display: inline-block; margin: 5px; padding: 5px 10px; border: 1px solid #007bff; border-radius: 5px; color: #007bff; text-decoration: none; transition: 0.3s; }
.review-actions a:hover { background: #007bff; color: #fff; }
</style>
</head>
<body>

<div class="review-grid">
  <c:choose>
    <c:when test="${not empty reviews}">
      <c:forEach var="review" items="${reviews}">
        <div class="review-card" onclick="flipCard(this)">
          <div class="review-inner">
            <!-- 카드 앞면 -->
            <div class="review-front">
              <c:if test="${not empty review.review_image}">
                <img src="${pageContext.request.contextPath}/resources/reviewupload/${review.review_image}" />
              </c:if>
              <h4>${review.review_content}</h4>
              <div>
                <c:forEach var="i" begin="1" end="5">
                  <c:choose>
                    <c:when test="${i <= review.rating}">
                      <i class="fa-solid fa-star" style="color: gold;"></i>
                    </c:when>
                    <c:otherwise>
                      <i class="fa-regular fa-star"></i>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </div>
            </div>

            <!-- 카드 뒷면 -->
            <div class="review-back">
              <div style="margin-bottom: 10px; white-space: pre-wrap;">${review.review_content}</div>
              <div class="review-actions">
                <a href="MyReviewSubInsertForm.do?review_id=${review.review_id}">댓글달기</a>
                <a href="MyReviewUpdateForm.do?review_id=${review.review_id}">수정</a>
                <a href="MyReviewDelete.do?review_id=${review.review_id}" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
              </div>
            </div>

          </div>
        </div>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <div style="width: 100%; text-align: center; font-size: 18px; color: #999; padding: 50px 0;">
        작성된 리뷰가 없습니다.
      </div>
    </c:otherwise>
  </c:choose>
</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
function flipCard(card) { $(card).toggleClass('flipped'); }
</script>

</body>
</html>