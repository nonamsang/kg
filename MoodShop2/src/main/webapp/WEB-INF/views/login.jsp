
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>MOODSHOP | 로그인</title>
<link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;700&display=swap" rel="stylesheet">
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

.login-container {
	background: white;
	border-radius: 20px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
	padding: 40px 50px;
	width: 400px;
}

.login-container h2 {
	text-align: center;
	margin-bottom: 30px;
	font-size: 28px;
	font-weight: 700;
	color: #112d4e;
}

.radio-group {
	text-align: center;
	margin-bottom: 20px;
	font-weight: bold;
	color: #3f72af;
}

.login-container input[type="text"], .login-container input[type="password"]
	{
	width: 100%;
	padding: 12px;
	margin-top: 10px;
	margin-bottom: 20px;
	border: 1px solid #ccc;
	border-radius: 10px;
	font-size: 14px;
}

.login-container input[type="submit"] {
	width: 100%;
	background-color: #3f72af;
	color: white;
	padding: 12px;
	border: none;
	border-radius: 10px;
	font-size: 16px;
	cursor: pointer;
	transition: 0.3s;
}

.login-container input[type="submit"]:hover {
	background-color: #112d4e;
}

.login-container .extra-links {
	margin-top: 20px;
	text-align: center;
}

.login-container .extra-links a {
	color: #3f72af;
	text-decoration: none;
	margin: 0 10px;
	font-size: 13px;
}

.login-container .extra-links a:hover {
	text-decoration: underline;
}

#loginMassage {
	text-align: center;
	color: red;
	font-size: 14px;
	margin-top: -10px;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/header.jsp" />


	<div class="login-container">
		<h2>MOODSHOP LOGIN</h2>
		<form id="loginForm">
			<div class="radio-group">
				<label><input type="radio" name="MemberType" value="User" required> 사용자</label> &nbsp;&nbsp; <label><input type="radio" name="MemberType" value="Admin"> 관리자</label>
			</div>

			<input type="text" name="user_id" required placeholder="아이디를 입력해주세요"> <input type="password" name="password" required placeholder="비밀번호를 입력해주세요">
			<div id="loginMassage"></div>
			<input type="submit" value="로그인">
		</form>

		<div class="extra-links">
			<a href="MyLoginId.do">아이디 찾기</a> <a href="MyLoginPw.do">비밀번호 찾기</a> <a href="MyLoginMember.do">회원가입</a>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
		function submitLoginAjax() {
			const formData = $('#loginForm').serialize();

			$.ajax({
				type : "POST",
				url : "MyLoginKaja.do",
				data : formData,
				success : function(response) {
					if (response === "success") {
						window.location.href = "MainMoodShop.do";
					} else if (response === "admin") {
						window.location.href = "AdminMainPage.do"; // 관리자 페이지로 이동
					} else {
						$('#loginMassage').html(response);
					}
				},
				error : function() {
					$('#loginMassage').html("서버 오류가 발생했습니다.");
				}
			});
		}

		$(document).ready(function() {
			$('#loginForm').on('submit', function(e) {
				e.preventDefault();
				submitLoginAjax();
			});
		});
	</script>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
