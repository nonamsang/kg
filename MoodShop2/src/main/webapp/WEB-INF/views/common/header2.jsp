<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Arial', sans-serif;
	padding-top: 120px; /* header 고정 보정 */
}
.header {
	width: 100%;
	height: 100px; /* 원하는 높이 설정 */
	background-color: #000000;
	border-bottom: #444;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 1000;
	color: #ffffff;

	display: flex; /* 추가 */
	justify-content: center; /* 가로 중앙 */
	align-items: center; /* 세로 중앙 */
}
.logo {
	font-size: 24px;
	font-weight: bold;
	display: inline-block;
	color: #ffffff;
	margin-top: 45px;
	cursor: pointer;
	margin-bottom: 30px;
}
  .menu_right {
    position: absolute;
    right: 20px;
  }
  .menu_right a {
    margin-left: 20px;
    text-decoration: none;
    color: #ffffff;
    font-weight: bold;
	margin-top: 0; /* flex로 하면 필요 없음 */
  }
</style>

<div class="header">
  <div class="logo" onclick="location.href='AdminMainPage.do'">MOODSHOP</div>
  <div class="menu_right">
    <a href="Logout.do">로그아웃</a>
  </div>
</div>
