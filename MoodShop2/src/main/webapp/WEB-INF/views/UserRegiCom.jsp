<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 가입 완료</title>
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
        min-height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 40px 20px;
    }
    .container {
        background: white;
        border-radius: 20px;
        box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        padding: 40px 50px;
        width: 520px;
        text-align: center;
    }
    h3 {
        color: #112d4e;
        font-size: 24px;
        font-weight: 700;
        margin-bottom: 30px;
    }
    p {
        font-size: 16px;
        color: #3f72af;
        margin-bottom: 15px;
        font-weight: 600;
    }
    input[type="button"] {
        width: 100%;
        margin-top: 15px;
        padding: 12px 0;
        background-color: #3f72af;
        border: none;
        border-radius: 12px;
        color: white;
        font-weight: 700;
        cursor: pointer;
        transition: background-color 0.3s;
        font-size: 16px;
    }
    input[type="button"]:hover {
        background-color: #112d4e;
    }
</style>

</head>
<body>
<div class="container">
    <h3>MOODSHOP 쇼핑몰의 회원이 되신 것을 환영합니다.</h3>
    <input type="button" onclick="location.href='MainMoodShop.do'" value="메인으로 가기">
</div>
</body>
</html>
