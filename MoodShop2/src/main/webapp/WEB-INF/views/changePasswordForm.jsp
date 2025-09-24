<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CPWF</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
body {
  background-color: #eef1f6;
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
}

.pw-find-box {
  max-width: 420px;
  background: white;
  margin: 80px auto;
  padding: 30px 40px;
  border-radius: 15px;
  box-shadow: 0 5px 25px rgba(0,0,0,0.1);
}

.pw-find-box h2 {
  text-align: center;
  color: #1a237e;
  margin-bottom: 20px;
}

.pw-find-box h2 i {
  margin-right: 8px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  font-weight: bold;
  color: #444;
}

.form-group input[type="text"],
.form-group input[type="password"] {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
}

.btn-box {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.btn-box input[type="submit"] {
  width: 100%;
  padding: 12px 0;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  font-size: 14px;
  cursor: pointer;
  background-color: #3f51b5;
  color: white;
  transition: background-color 0.3s;
}

.btn-box input[type="submit"]:hover {
  background-color: #2c387e;
}
</style>
</head>
<body>

<%
String memberType = request.getAttribute("memberType").toString();
String id = request.getAttribute("id").toString();
%>

<div class="pw-find-box">
  <h2><i class="fa-solid fa-lock"></i>비밀번호 변경</h2>

  <form action="ChangePassword.do" method="post">
    <input type="hidden" name="memberType" value="<%=memberType%>">

    <div class="form-group">
      <label for="id">아이디</label>
      <input type="text" name="id" value="<%=id%>" readonly>
    </div>

    <div class="form-group">
      <label for="newPassword">새 비밀번호</label>
      <input type="password" name="newPassword" required>
    </div>

    <div class="form-group">
      <label for="confirmPassword">비밀번호 확인</label>
      <input type="password" name="confirmPassword" required>
    </div>

    <div class="btn-box">
      <input type="submit" value="비밀번호 변경">
    </div>
  </form>
</div>

</body>
</html>
