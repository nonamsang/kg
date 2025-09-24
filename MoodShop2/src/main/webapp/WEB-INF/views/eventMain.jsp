<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
  boolean hideToday = false;
  Cookie[] cookies = request.getCookies();
  if (cookies != null) {
    for (Cookie c : cookies) {
      if ("hideToday".equals(c.getName()) && "true".equals(c.getValue().trim())) {
        hideToday = true;
        break;
      }
    }
  }
  if (hideToday) {
    response.sendRedirect(request.getContextPath() + "/MainMoodShop.do");
    return;
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>[MOODSHOP] 여름 이벤트 미리보기</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
  


  
  
<style>
    body {
    	font-family: 'Arial', sans-serif;
      	padding-top: 100px;
      	background: linear-gradient(270deg, #FFFAF0, #FFE4B5, #FFDAB9, #FFF0F5);
      	background-size: 500% 500%;
      	animation: gradientShift 18s ease-in-out infinite;
      	background-attachment: fixed;
     	overflow-x: hidden;
      	color: #111;
      	position: relative;
    }

    @keyframes gradientShift {
      0% { background-position: 0% 50%; }
      50% { background-position: 100% 50%; }
      100% { background-position: 0% 50%; }
    }

    .particle {
      position: fixed;
      bottom: 0;
      border-radius: 50%;
      opacity: 0.5;
      filter: blur(1px);
      animation: rise 6s linear infinite;
      z-index: 0;
    }

    @keyframes rise {
      0% { transform: translateY(0); opacity: 0.6; }
      100% { transform: translateY(-100vh); opacity: 0; }
    }

    .d-flex { display: flex; }

    .menu-section {
      flex-shrink: 0;
      width: 220px;
      padding: 30px 15px;
      border-right: 1px solid #333;
      
      /* 메뉴 버튼 스크롤 따라오는 거 */
      position: sticky;
  	  top: 100px; /* 헤더 높이에 맞게 조정 */
      align-self: flex-start; /* 높이 고정 */
      height: fit-content;
    }

    .menu-section .section-title {
      font-size: 20px;
      font-weight: bold;
      color: #d84315;
      text-align: center;
      margin-bottom: 20px;
      letter-spacing: 1px;
    }

    .menu-section a {
  		display: block;
  		padding: 12px 16px;
  		margin-bottom: 10px;
  		text-decoration: none;
  		color: #333333; /* 진하고 안정적인 텍스트 색상 */
  		font-size: 15px;
  		font-weight: 600; /* 약간 더 굵게 */
  		border-radius: 8px;
  		background-color: #fef3e7; /* 배경과 자연스럽게 어우러지는 연살구색 */
  		box-shadow: inset 0 0 0 1px #f5cbaa; /* 부드러운 테두리 */
  		transition: all 0.3s ease;
	}

	.menu-section a:hover,
	.menu-section a.active {
  		background-color: #ffcc80; /* 부드러운 주황 hover 유지 */
  		color: #d84315;
  		transform: translateX(4px);
  		box-shadow: inset 0 0 0 1px #ffa726; /* 강조 테두리 */
	}

    .event-content {
      flex: 1;
      padding: 30px;
      background-color: #1e1e1e;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,0.5);
      min-height: 400px;
      animation: fadeIn 0.5s ease;
      z-index: 1;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .today-btn {
      position: absolute;
      top: 80px;
      right: 30px;
      z-index: 999;
    }

    .today-btn button {
      background: none;
      color: #cccccc;
      border: none;
      font-weight: bold;
      font-size: 14px;
      transition: color 0.3s ease, filter 0.2s ease;
    }

    .today-btn button:hover {
      color: #ffffff;
      filter: brightness(180%);
      cursor: pointer;
    }
    
    #topBtn {
  		position: fixed;
  		bottom: 30px;
  		right: 30px;
  		z-index: 1000;

  		font-size: 14px;
  		font-weight: bold;
  		color: #333;
  		background-color: rgba(240, 240, 240, 0.95);
  		border: 1px solid #aaa;
  		padding: 8px 16px;
  		border-radius: 4px;

  		cursor: pointer;
  		display: none;
  		transition: all 0.3s ease;
	}
	
	#topBtn:hover {
  		.today-btn button:hover {
  		background-color: #333;
  		color: #ff69b4;
  		border-color: #333;
	}
}
</style>
</head>
<body>
<%
	//1. section 값 가져오기
	String section = (String) request.getAttribute("section");
	if (section == null) section = "limitevent";

  String sectionClass = "";
  if ("limitevent".equals(section)) {
    sectionClass = "theme-limitevent";
  } else if ("flowers".equals(section)) {
    sectionClass = "theme-flowers";
  } else if ("goods".equals(section)) {
    sectionClass = "theme-goods";
  }
%>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<script>
  // 파티클 효과 강화
  const colors = ["#ff1744", "#2979ff", "#ffd600", "#d500f9"]; // 진하고 선명한 색상
  for (let i = 0; i < 100; i++) {  // 개수 증가
    const dot = document.createElement('div');
    dot.className = 'particle';
    dot.style.left = Math.random() * 100 + 'vw';
    dot.style.animationDelay = (Math.random() * 6) + 's';
    dot.style.width = dot.style.height = (Math.random() * 6 + 4) + 'px'; // 크기 살짝 증가
    dot.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
    document.body.appendChild(dot);
  }
</script>


<div class="today-btn">
  <button onclick="hideToday()">🚫 오늘 그만 보기</button>
</div>

<div class="container">
  <div class="d-flex">
    <div class="menu-section">
      <div class="section-title">KOKONE TEAM</div>
      <a href="eventMain.do?section=limitevent" class="${section == 'limitevent' ? 'active' : ''}">&nbsp;&nbsp;여름 이벤트 미리보기</a>
      <a href="eventMain.do?section=flowers" class="${section == 'flowers' ? 'active' : ''}">&nbsp;&nbsp;NEW <b>라넌큘러스</b></a>
      <a href="eventMain.do?section=goods" class="${section == 'goods' ? 'active' : ''}">&nbsp;&nbsp;NEW 분노 키링</a>
      <br><br><br>
      <a href="${pageContext.request.contextPath}/eventClick.do?status=ongoing" class="event-detail-link">&nbsp;&nbsp;이벤트 바로가기</a>
      <a href="${pageContext.request.contextPath}/Notice.do" class="event-detail-link">&nbsp;&nbsp;공지사항 바로가기</a>
    </div>
    <div class="event-content <%= sectionClass %>">
  		<jsp:include page="/WEB-INF/views/eventMain_serve.jsp" />
	</div>
  </div>
</div>

<!-- 페이지 맨 상단으로 올리는 TOP 버튼 선언문 -->
<button id="topBtn" onclick="scrollToTop()" title="맨 위로">TOP</button>

<script>
  const contextPath = '<%= request.getContextPath() %>';
  function hideToday() {
    const exp = new Date();
    exp.setHours(23, 59, 59);
    document.cookie = "hideToday=true; path=/; expires=" + exp.toUTCString();
    location.href='MainMoodShop.do';
  }
</script>

<script>
  // TOP 버튼 동작
  window.onscroll = function() {
    const btn = document.getElementById("topBtn");
    if (document.body.scrollTop > 200 || document.documentElement.scrollTop > 200) {
      btn.style.display = "block";
    } else {
      btn.style.display = "none";
    }
  };

  function scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
</script>
</body>
</html>
