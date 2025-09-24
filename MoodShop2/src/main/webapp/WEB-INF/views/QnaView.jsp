<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q&A 상세 보기</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<style>
html, body {
  height: 100%;
  margin: 0;
}

  body {
    padding-top: 80px;
    background-color: #f8f9fa;
    font-family: 'Arial', sans-serif;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }

  .qna-container {
    max-width: 900px;
    margin: 0 auto;
    background-color: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 0 15px rgba(0,0,0,0.1);
    flex: 1;
  }

  .section-title {
    font-size: 1.5rem;
    font-weight: bold;
    margin-bottom: 20px;
    color: #333;
    border-left: 5px solid #ff7f50;
    padding-left: 10px;
  }

  .qna-card, .answer-card {
    background-color: #fff;
    border: 1px solid #ddd;
    border-left: 5px solid #ff7f50;
    border-radius: 8px;
    padding: 20px 25px;
    margin-bottom: 25px;
    transition: box-shadow 0.3s;
  }

  .qna-card:hover, .answer-card:hover {
    box-shadow: 0 4px 10px rgba(0,0,0,0.1);
  }

  .qna-card .label, .answer-card .label {
    font-weight: bold;
    color: #ff7f50;
    display: block;
    margin-bottom: 8px;
  }

  .qna-card .content, .answer-card .content {
    font-size: 1rem;
    color: #333;
    line-height: 1.6;
    white-space: pre-wrap;
  }

  .answer-card .reply {
    margin-left: 20px;
    margin-top: 15px;
    font-size: 1rem;
    color: #444;
    position: relative;
  }

  .answer-card .reply::before {
    content: "ㄴ";
    margin-right: 5px;
    color: #555;
  }

  .answer-card .meta {
    text-align: right;
    font-size: 0.85rem;
    color: #777;
    margin-top: 15px;
  }

  .btn-back {
    background-color: #000;
    color: #fff;
    padding: 10px 24px;
    font-weight: bold;
    border: none;
    border-radius: 8px;
    transition: background-color 0.3s ease;
  }

  .btn-back:hover {
    background-color: #333;
  }
  
  footer {
      margin-top: 40px;
  }
</style>
</head>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp" />


<div class="container qna-container">

  <!-- 질문 -->
  <div class="section-title">🙋 질문</div>
  <div class="qna-card">
    <span class="label">제목 :</span>
    <div class="content mb-3">${qna.qTitle}</div>

    <span class="label">내용 :</span>
    <div class="content">${qna.qContent}</div>
  </div>

  <!-- 답변 -->
  <div class="section-title">💬 관리자 답변</div>
  <c:choose>
    <c:when test="${not empty answers}">
      <c:forEach var="answer" items="${answers}">
        <div class="answer-card">
          <span class="label">제목 :</span>
          <div class="content mb-3">${answer.aTitle}</div>

          <span class="label">내용 :</span>
          <div class="reply">${answer.aContent}</div>

          <div class="meta">
            작성자: ${answer.managerId} | 작성일: 
            <fmt:formatDate value="${qna.qDate}" pattern="yyyy-MM-dd(EEEE) HH:mm:ss" />
          </div>
        </div>
      </c:forEach>
    </c:when>

    <c:otherwise>
      <div class="answer-card text-muted">
        아직 등록된 답변이 없습니다.
      </div>
    </c:otherwise>
  </c:choose>

  <!-- 버튼 -->
  <div class="text-end mt-4">
    <button class="btn-back" onclick="location.href='gotoQnA.do'">되돌아가기</button>
  </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
