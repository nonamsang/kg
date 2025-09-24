<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답변 작성</title>
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

  .qna-card {
    background-color: #fff;
    border: 1px solid #ddd;
    border-left: 5px solid #ff7f50;
    border-radius: 8px;
    padding: 20px 25px;
    margin-bottom: 25px;
  }

  .qna-card .label {
    font-weight: bold;
    color: #ff7f50;
    display: block;
    margin-bottom: 8px;
  }

  .qna-card .content {
    font-size: 1rem;
    color: #333;
    line-height: 1.6;
    white-space: pre-wrap;
  }

  .form-control, textarea {
    border-radius: 6px;
  }

  .btn-submit {
    background-color: #ff7f50;
    color: #fff;
    font-weight: bold;
    padding: 10px 24px;
    border: none;
    border-radius: 8px;
    transition: background-color 0.3s ease;
  }

  .btn-submit:hover {
    background-color: #e36430;
  }

  .btn-cancel {
    background-color: #6c757d;
    color: #fff;
    font-weight: bold;
    padding: 10px 24px;
    border: none;
    border-radius: 8px;
    margin-left: 10px;
  }

  .btn-cancel:hover {
    background-color: #5a6268;
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

  <!-- 답변 작성 폼 -->
  <div class="section-title">✏️ 답변 작성</div>
  <form action="AnswerWrite.do" method="post">
    <input type="hidden" name="qId" value="${qna.qId}" />

    <div class="mb-3">
      <label for="aTitle" class="form-label fw-bold text-secondary">답변 제목</label>
      <input type="text" id="aTitle" name="aTitle" class="form-control" value="${qna.qTitle}" required>
    </div>

    <div class="mb-4">
      <label for="aContent" class="form-label fw-bold text-secondary">답변 내용</label>
      <textarea id="aContent" name="aContent" rows="8" class="form-control" placeholder="답변을 입력하세요" required></textarea>
    </div>

    <div class="text-end">
      <button type="submit" class="btn-submit">답변 등록</button>
      <button type="button" class="btn-cancel" onclick="history.back()">취소</button>
    </div>
  </form>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
