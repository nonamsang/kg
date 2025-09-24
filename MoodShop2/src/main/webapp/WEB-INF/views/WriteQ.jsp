<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="userVO" value="${sessionScope.userVO}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문 작성</title>
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

  .readonly-input {
    border: none;
    background-color: transparent;
    font-weight: bold;
    pointer-events: none;
  }

  .alert-box {
    background-color: #fff3cd;
    color: #856404;
    padding: 20px;
    border-radius: 8px;
    border: 1px solid #ffeeba;
    margin-top: 30px;
    text-align: center;
  }
</style>
</head>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="container qna-container">

  <div class="section-title">📝 질문 작성</div>

  <c:choose>
    <c:when test="${not empty userVO}">
      <form action="QWriteCom.do" method="post">
        <div class="mb-3">
          <label class="form-label fw-bold text-secondary">작성자</label>
          <input type="text" class="form-control readonly-input" value="${userVO.name}" readonly onfocus="this.blur();" />
        </div>

        <div class="mb-3">
          <label for="qTitle" class="form-label fw-bold text-secondary">제목</label>
          <input type="text" id="qTitle" name="qTitle" class="form-control" required />
        </div>

        <div class="mb-4">
          <label for="qContent" class="form-label fw-bold text-secondary">내용</label>
          <textarea id="qContent" name="qContent" rows="8" class="form-control" required></textarea>
        </div>

        <div class="text-end">
          <button type="submit" class="btn-submit">작성 완료</button>
          <button type="button" class="btn-cancel" onclick="history.back()">취소</button>
        </div>
      </form>
    </c:when>

    <c:otherwise>
      <div class="alert-box">
        로그인한 사용자만 질문을 작성할 수 있습니다. 3초 후 로그인 페이지로 이동합니다.
      </div>
      <meta http-equiv="refresh" content="3;url=MyLogin.do" />
    </c:otherwise>
  </c:choose>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
