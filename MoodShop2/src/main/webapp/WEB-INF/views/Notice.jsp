<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MOODSHOP 공지사항</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">

<style>

html, body {
  height: 100%;
}
  body {
    margin: 0;
    font-family: 'Arial', sans-serif;
    background: linear-gradient(180deg, #ffffff, #f8f8f8);
    background-size: 100% 200%;
    animation: softFade 20s ease-in-out infinite alternate;
    color: #111111;
    padding-top: 120px;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  @keyframes softFade {
    0% { background-position: 0% 0%; }
    100% { background-position: 0% 100%; }
  }

  .notice-wrapper {
    max-width: 960px;
    margin: auto;
    padding: 40px 20px 80px;
    background-color: #ffffff;
    border-radius: 12px;
    box-shadow: 0 0 10px rgba(0,0,0,0.05);
    flex: 1;
  }

  .notice-title {
    font-size: 34px;
    font-weight: bold;
    margin-bottom: 30px;
    border-bottom: 2px solid #ccc;
    padding-bottom: 10px;
  }

  .accordion {
    margin-top: 20px;
  }

  .accordion-item {
    border-bottom: 1px solid #ddd;
    padding: 15px 0;
  }

  .accordion-header {
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
    transition: color 0.3s;
  }

  .accordion-header:hover {
    color: #007bff; /* #828282 회색 */ /* #ff69b4 핑크색 */
  }

  .accordion-body {
    visibility: hidden;
    opacity: 0;
    height: 0;
    overflow: hidden;
    transition: all 0.4s ease;
    font-size: 16px;
    line-height: 1.6;
    margin-top: 10px;
  }

  .accordion-body.show {
    visibility: visible;
    opacity: 1;
    height: auto;
  }

  .accordion-body img {
    max-width: 100%;
    margin-top: 10px;
    border-radius: 8px;
  }
</style>

<script>
  function toggleBody(id) {
    const element = document.getElementById(id);
    element.classList.toggle('show');
  }
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="notice-wrapper">
  <div class="notice-title">공지사항</div>

  <div class="accordion">
    <c:forEach var="notice" items="${noticeList}" varStatus="status">
      <div class="accordion-item">
        <div class="accordion-header" onclick="toggleBody('body${status.index}')">
          ${notice.notice_title} (${notice.notice_start_date} ~ ${notice.notice_end_date})
        </div>
        <div class="accordion-body" id="body${status.index}">
          ${notice.notice_description}
          <c:if test="${not empty notice.notice_image_source}">
            <img src="${pageContext.request.contextPath}/resources/img/notice_img/${notice.notice_image_source}" alt="공지 이미지" />
          </c:if>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
