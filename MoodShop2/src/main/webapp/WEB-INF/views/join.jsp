<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
<style>
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: 'Nunito', sans-serif;
}

body {
	background: linear-gradient(to bottom right, #f9f7f7, #dbe2ef);
	height: 100vh;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

.register-container {
	background: white;
	border-radius: 20px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
	padding: 70px 50px;
	width: 500px;
	margin-bottom: 100px; /* 빈 공간을 위해 밑에 마진 추가 */
	margin-top: 50px;
}

.register-container h2 {
	text-align: center;
	margin-bottom: 30px;
	font-size: 28px;
	font-weight: 700;
	color: #112d4e;
}

.radio-group, .checkbox-group {
	text-align: center;
	margin-bottom: 20px;
	font-weight: bold;
	color: #3f72af;
}

.register-container input[type="submit"], .register-container input[type="button"]
	{
	width: 100%;
	background-color: #3f72af;
	color: white;
	padding: 12px;
	border: none;
	border-radius: 10px;
	font-size: 16px;
	cursor: pointer;
	transition: 0.3s;
	margin-top: 10px;
}

.register-container input[type="submit"]:hover, .register-container input[type="button"]:hover
	{
	background-color: #112d4e;
}

.register-container .extra-links {
	margin-top: 20px;
	text-align: center;
}

.register-container .extra-links a {
	color: #3f72af;
	text-decoration: none;
	margin: 0 10px;
	font-size: 13px;
}

.register-container .extra-links a:hover {
	text-decoration: underline;
}
</style>

</head>
<body>

	<jsp:include page="/WEB-INF/views/include/header.jsp" />

	<div class="register-container">
		<h2>MOODSHOP 회원가입</h2>
		<form action="joinsubmit.do" method=post>
			<!-- joinsubmit.do는
이후에 MyLoginAdminForm.do와 MyLoginUserForm.do로 가기위해 마련한 .do 입니다. -->
			<div class="radio-group">
				<label><input type="radio" name="MemberType" value="Admin" required>관리자로 가입</label> &nbsp;&nbsp; <label><input type="radio" name="MemberType" value="User">사용자로 가입</label>
			</div>
			<div class="checkbox-group">
				<label><input type="checkbox" name="Agree1" required> 개인정보 수집 및 이용에 동의합니다.</label>
			</div>
			<input type="submit" value="다음">
		</form>
		<input type="button" onclick="location.href='MyLogin.do'" value="로그인으로">
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>

</html>