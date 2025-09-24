<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.moodshop.kokone.vo.EventVO" %>

<%
  String section = (String) request.getAttribute("section");
  if (section == null) section = "limitevent";
%>

<%-- 제목 출력 --%>
<div class="title-wrapper">
<%
  if ("limitevent".equals(section)) {
%>
  <h4 class="title-section">7월 여름 이벤트</h4>
<%
  } else if ("flowers".equals(section)) {
%>
  <h4 class="title-section">새로운 꽃 <b>라넌큘러스</b> 입고!</h4>
<%
  } else {
%>
  <h4 class="title-section"><b>Summer Session</b> 기획!<br> 분노 봉제인형 키링 출시</h4>
<%
  }
%>
</div>

<!-- css 정의문 -->
<style>
	/* 🌊 여름 테마: 파스텔 하늘색 그라데이션 */
	@keyframes bgFlowSummer {
  		0% { background-position: 0% 50%; background-color: #b3e5fc; }
  		50% { background-position: 100% 50%; background-color: #e1f5fe; }
  		100% { background-position: 0% 50%; background-color: #b3e5fc; }
	}

	.theme-limitevent {
  		background: linear-gradient(270deg, #b3e5fc, #e1f5fe, #b3e5fc);
  		background-size: 600% 600%;
  		animation: floatLoop 4s ease-in-out infinite, bgFlowSummer 8s ease-in-out infinite;
  		border-radius: 8px;
  		box-shadow: 0 0 15px rgba(100, 200, 255, 0.3);
	}

	/* 🌼 꽃 테마: 파스텔 노란색 그라데이션 */
	@keyframes bgFlowFlowers {
  		0%   { background-color: #fff9c4; }
  		50%  { background-color: #fffde7; }
  		100% { background-color: #fff9c4; }
	}

	.theme-flowers {
  		animation: floatLoop 4s ease-in-out infinite, bgFlowFlowers 6s ease-in-out infinite;
  		background-color: #fff9c4;
  		border-radius: 8px;
  		box-shadow: 0 0 15px rgba(255, 230, 100, 0.3);
	}

	/* 💗 굿즈 테마: 파스텔 핑크색 그라데이션 */
	@keyframes bgFlowGoods {
  		0%   { background-position: 0% 50%; background-color: #f8bbd0; }
  		50%  { background-position: 100% 50%; background-color: #fce4ec; }
  		100% { background-position: 0% 50%; background-color: #f8bbd0; }
	}

	.theme-goods {
  		background: linear-gradient(270deg, #f8bbd0, #fce4ec, #f8bbd0);
  		background-size: 500% 500%;
  		animation: floatLoop 4s ease-in-out infinite, bgFlowGoods 6s ease-in-out infinite;
  		border-radius: 8px;
  		box-shadow: 0 0 20px rgba(255, 160, 180, 0.4);
	}

	/* 카드 애니메이션 공통 */
	@keyframes floatUp {
  		from { opacity: 0; transform: translateY(40px); }
  		to { opacity: 1; transform: translateY(0); }
	}

	.card-animate {
  		animation-duration: 0.8s;
  		animation-fill-mode: both;
	}

	.animate-float,
	.animate-zoom,
	.animate-shake {
  		animation-name: floatUp;
	}
	/* 제목 디자인 부분 */
	h4.title-section {
  		font-size: 28px;
  		font-weight: bold;
  		color: #333333;
  		text-align: center;
  		padding: 12px 20px;
  		margin-bottom: 30px;
  		background-color: #fef3e7; /* 카드 배경과 어울리는 연살구색 */
  		display: inline-block;
  		border-radius: 12px;
  		box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
  		transition: all 0.3s ease;
	}
	.title-wrapper {
  		text-align: center; /* h4를 div 안에서 가운데로 정렬 */
  		margin-bottom: 30px;
	}
</style>

<style>
  /* 카드 안 텍스트 통합 스타일 */
  .card-body {
    background-color: #ffffff;  /* 카드 본문 흰색 유지 */
    text-align: center;
    padding: 20px;
  }

  .category {
  font-size: 14px;
  font-weight: bold;
  color: #ffffff; /* 흰색 글씨로 가독성 극대화 */
  background-color: #ff69b4; /* 진한 핫핑크 배경 */
  display: inline-block;
  padding: 4px 12px; /* 조금 더 시각적으로 넉넉하게 */
  border-radius: 20px;
  margin-bottom: 10px;
  box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.3); /* 그림자 추가로 입체감 부여 */
}

  .event-title {
    font-size: 22px;
    font-weight: bold;
    color: #333333; /* 진한 검정으로 선명하게 */
    margin-bottom: 8px;
  }

  .event-date {
    font-size: 14px;
    color: #777777; /* 부드러운 회색 */
    margin-bottom: 15px;
  }

  .btn-outline-light {
    font-weight: 600;
    padding: 8px 16px;
    font-size: 14px;
    color: #333333;
    border: 1px solid #333333;
    background-color: #ffffff;
    border-radius: 4px;
    transition: all 0.3s ease;
  }

  .btn-outline-light:hover {
    background-color: #ff69b4;
    color: #ffffff;
    border-color: #ff69b4;
  }
</style>

<%
  String animationClass = "";
  if ("limitevent".equals(section)) {
    animationClass = "card-animate animate-float";
  } else if ("flowers".equals(section)) {
    animationClass = "card-animate animate-zoom";
  } else if ("goods".equals(section)) {
    animationClass = "card-animate animate-shake";
  }
%>

<%
List<EventVO> eventList = (List<EventVO>) request.getAttribute("eventcard_detail_list");
  if (eventList != null && !eventList.isEmpty()) {
    for (EventVO detail : eventList) {
      String fileName = detail.getEvent_image_source();
      boolean isVideo = fileName != null && fileName.toLowerCase().endsWith(".mp4");
%>

  <!-- 카드 출력 div에 class 추가 -->
  <div class="d-flex justify-content-center mb-4">
  	<div class="card bg-white text-dark <%= animationClass %>" style="width: 50rem;">
      <% if (isVideo) { %>
        <video class="card-img-top" autoplay loop muted>
          <source src="<%= request.getContextPath() %>/resources/video/<%= fileName %>" type="video/mp4">
        </video>
      <% } else { %>
        <img src="<%= request.getContextPath() %>/resources/img/event_img/<%= fileName %>" class="card-img-top" alt="<%= detail.getEvent_name() %>" />
      <% } %>
      <div class="card-body">
        <div class="category"><%= detail.getEvent_category()%></div> 
        <br>
        <div class="event-title"><%= detail.getEvent_name() %></div>
        <div class="event-date">
        	이벤트 기간: <%= detail.getEvent_start_date()%> ~ <%= detail.getEvent_end_date()%>
        </div>
        <a href="<%= request.getContextPath() %>/eventClick_serve.do?event_id=<%= detail.getEvent_id() %>" 
   						class="btn btn-outline-light mt-2">자세히 보기</a>
      </div>
    </div>
  </div>
<%
    }
  } else {
%>
  <p>이벤트 정보가 없습니다.</p>
<%
  }
%>
