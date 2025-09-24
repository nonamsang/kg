<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.moodshop.kokone.vo.EventVO" %>
<%
  EventVO event = (EventVO) request.getAttribute("event");
  if (event == null) {
%>
  <p style="text-align:center; padding-top: 200px;">잘못된 접근입니다. 이벤트 정보가 존재하지 않습니다.</p>
<% return; } %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 상세 페이지</title>
  <link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
  
  <style>
    body {
      margin: 0;
      font-family: 'Arial', sans-serif;
      background: linear-gradient(180deg, #ffffff, #f8f8f8);
      background-size: 100% 200%;
      animation: softFade 20s ease-in-out infinite alternate;
      color: #111111;
      padding-top: 120px;
    }

    @keyframes softFade {
      0% { background-position: 0% 0%; }
      100% { background-position: 0% 100%; }
    }

    .event-wrapper {
      max-width: 960px;
      margin: auto;
      padding: 40px 20px;
      background-color: #ffffff;
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0,0,0,0.05);
      animation: fadeIn 1s ease-out;
    }

    .event-title {
      font-size: 34px;
      font-weight: bold;
      text-align: center;
      margin-bottom: 30px;
      border-bottom: 2px solid #ccc;
      padding-bottom: 10px;
    }

    .event-text {
      font-size: 17px;
      line-height: 1.7;
      text-align: center;
      margin-bottom: 30px;
    }
    
    .event-text-last {
      font-size: 17px;
      line-height: 1.7;
      text-align: center;
      margin-bottom: 30px;
    }

    .event-banner {
      display: block;
      width: 100%;
      height: auto;
      margin: auto;
      border-radius: 8px;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
    
    #topBtn {
  		position: fixed;
  		bottom: 30px;
  		right: 30px;
  		z-index: 1000;

  		font-size: 14px;
  		font-weight: bold;
  		color: #333333;
  		background-color: #f0f0f0;
  		border: 1px solid #ccc;
  		padding: 8px 16px;
  		border-radius: 4px;

  		cursor: pointer;
  		display: none;
  		transition: all 0.3s ease;
	}

	#topBtn:hover {
  		background-color: #333333;
  		color: #ffffff;
  		border-color: #333333;
	}
	
	#sell-btn {
  		display: inline-block;
  		margin-top: 30px;
  		padding: 10px 20px;
  		font-size: 14px;
  		font-weight: bold;
  		text-align: center;
  		text-decoration: none;

  		color: #333333;
  		background-color: #f0f0f0;
  		border: 1px solid #ccc;
  		border-radius: 4px;

  		transition: all 0.3s ease;
	}

	#sell-btn:hover {
  		background-color: #333333;
  		color: #ffffff;
  		border-color: #333333;
	}

  </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="event-wrapper">
  <div class="event-title"><%= event.getEvent_title() %></div>

  <div class="event-text">
    <%= event.getEvent_description() %>
  </div>

  <img src="<%= request.getContextPath() %>/resources/img/event_img/<%= event.getEvent_image_source() %>"
       alt="이벤트 대표 이미지"
       class="event-banner" />
  <img src="<%= request.getContextPath() %>/resources/img/event_img/<%= event.getEvent_sub_image_source() %>"
       alt="이벤트 서브 이미지"
       class="event-banner" />
  <br>
  <div class="event-text-last">
  	[이벤트 기간]<br>
  	<%= event.getEvent_start_date() %> ~ <%= event.getEvent_end_date() %>
  <br><br><br><br><br>
  <a href="${pageContext.request.contextPath}/eventClick.do?status=ongoing" id="sell-btn">이벤트 목록</a>
  <a href="#" id="sell-btn">구매 하러가기</a>
  </div>
</div>

<script>
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

	<!-- 페이지 맨 상단으로 올리는 TOP 버튼 선언문 -->
	<button id="topBtn" onclick="scrollToTop()" title="맨 위로">TOP</button>

	<!-- footer -->
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
