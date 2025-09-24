<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
<style>
  .header {
    width: 100%;
    background-color: transparent; /* 배경 투명 유지 */
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    padding: 5px 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 60px;
    margin-top: 20px;
  }

  .logo {
    font-size: 24px;
    font-weight: bold;
    cursor: pointer;
    background: linear-gradient(45deg, #ff7f50, #ffa07a);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    transition: transform 0.3s;
  }

  .logo:hover {
    transform: scale(1.05);
  }

  .menu_right {
    position: absolute;
    right: 20px;
  }

  .menu_right a {
    margin-left: 20px;
    text-decoration: none;
    font-weight: bold;
    background: linear-gradient(45deg, #ff7f50, #ffa07a);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    transition: color 0.3s, transform 0.3s;
  }

  .menu_right a:hover {
    transform: scale(1.05);
    background: linear-gradient(45deg, #ff4500, #ff6347);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }
</style>

<div class="header">
  <div class="logo" onclick="location.href='MainMoodShop.do'">MOODSHOP</div>
  <div class="menu_right">
    <a href="MyPage.do">로그인</a>
    <a href="MyPage.do">마이페이지</a>
  </div>
</div>
