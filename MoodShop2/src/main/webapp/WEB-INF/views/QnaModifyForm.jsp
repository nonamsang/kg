<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 수정</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<style>
  body {
    padding-top: 80px;
    background-color: #f8f9fa;
    font-family: Arial, sans-serif;
  }

  .container-box {
    max-width: 900px;
    background: #fff;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
    margin: 0 auto 50px auto;
  }

  .section-title {
    font-size: 1.5rem;
    font-weight: bold;
    color: #333;
    border-left: 5px solid #ff7f50;
    padding-left: 10px;
    margin-bottom: 30px;
  }

  .form-label {
    font-weight: bold;
    color: #555;
  }

  .form-control {
    border-radius: 6px;
  }

  .btn-submit {
    background-color: #ff7f50;
    color: white;
    font-weight: bold;
    padding: 10px 24px;
    border: none;
    border-radius: 8px;
  }

  .btn-submit:hover {
    background-color: #e36430;
  }

  .btn-back {
    background-color: #6c757d;
    color: white;
    padding: 10px 24px;
    border: none;
    border-radius: 8px;
    margin-top: 20px;
  }

  .btn-back:hover {
    background-color: #5a6268;
  }

  textarea {
    resize: vertical;
  }
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="container container-box">
  <h2 class="section-title">QnA 수정</h2>

  <%
    Object user = session.getAttribute("userVO");
    Object manager = session.getAttribute("managerVO");
  %>

  <c:choose>
    <c:when test="${role eq 'USER'}">
      <form action="QnaModify.do" method="post">
        <input type="hidden" name="qId" value="${qna.qId}" />
        
        <div class="mb-3">
          <label class="form-label">제목</label>
          <input type="text" name="qTitle" value="${qna.qTitle}" class="form-control" />
        </div>

        <div class="mb-4">
          <label class="form-label">내용</label>
          <textarea name="qContent" rows="8" class="form-control">${qna.qContent}</textarea>
        </div>

        <button type="submit" class="btn btn-submit">질문 수정하기</button>
      </form>
    </c:when>

    <c:when test="${role eq 'ADMIN'}">
      <form action="AnswerModify.do" method="post">
        <input type="hidden" name="qId" value="${qna.qId}" />

        <div class="mb-3">
          <label class="form-label">질문 제목</label>
          <p class="form-control-plaintext">${qna.qTitle}</p>
        </div>

        <div class="mb-3">
          <label class="form-label">질문 내용</label>
          <p class="form-control-plaintext">${qna.qContent}</p>
        </div>

        <div class="mb-4">
          <label class="form-label">답변 수정</label>
          <textarea name="aContent" rows="8" class="form-control">${answer.aContent}</textarea>
        </div>

        <button type="submit" class="btn btn-submit">답변 수정하기</button>
      </form>
    </c:when>

    <c:otherwise>
      <p>권한이 없습니다. 로그인 후 이용해 주세요.</p>
      <meta http-equiv="refresh" content="5;url=MyLogin.do" />
    </c:otherwise>
  </c:choose>

  <form action="myQnaList.do" method="get">
    <input type="hidden" name="page" value="${page}" />
    <button type="submit" class="btn btn-back">목록으로 돌아가기</button>
  </form>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
