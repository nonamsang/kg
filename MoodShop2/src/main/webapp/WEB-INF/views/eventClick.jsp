<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>[MOODSHOP] 이벤트</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sakura.css" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
  <link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
  <style>
    body {
      font-family: 'Arial', sans-serif;
      padding-top: 120px;
      background-color: #ffffff;
      color: #000000;
    }

    .container-flex {
      display: flex;
      width: 80%;
      margin: auto;
    }

    /* ✅ 왼쪽 메뉴 */
    .menu-section {
      flex-shrink: 0;
      width: 220px;
      padding: 30px 15px;
      border-right: 1px solid #333;
      
      /* 메뉴 버튼 스크롤 따라오는 거 */
      position: sticky;
  	  top: 170px; /* 헤더 높이에 맞게 조정 */
      align-self: flex-start; /* 높이 고정 */
      height: fit-content;
    }

    .menu-section a {
      display: block;
      padding: 12px 0;
      margin-bottom: 10px;
      text-decoration: none;
      color: #333;
      font-size: 16px;
      border-radius: 4px;
      transition: all 0.3s ease;
      
      text-align: center;       /* 수평 중앙 정렬 */
	  /* line-height: 40px; */       /* 수직 중앙 정렬 (버튼 높이에 맞게 조정) */
  	  height: 45px;             /* 버튼 높이 명시 */
    }

    .menu-section a:hover,
    .menu-section a.active {
      background-color: #333;
      color: #FFFFFF;
      transform: translateX(4px);
    }

    /* ✅ 우측 메인 */
    .main-section {
      flex-grow: 1;
      padding: 30px;
    }

    .search-bar {
      display: flex;
      justify-content: flex-end;
      margin-bottom: 20px;
    }

    .search-bar input[type="text"] {
      width: 250px;
      height: 38px;
      padding: 0 15px;
      border: 1px solid #ccc;
      border-radius: 20px;
      outline: none;
      font-size: 14px;
      color: #333;
    }

    .banner {
      width: 100%;
      height: 400px;
      background-color: #f2f2f2;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 24px;
      font-weight: bold;
      margin-bottom: 30px;
    }

    .event-card .card {
      height: 100%;
    }

    .event-card img,
    .event-card video {
      max-height: 200px;
      object-fit: cover;
    }

    .card-body {
      text-align: center;
    }

    .category {
      font-weight: bold;
      color: #666;
    }

    .event-title {
      font-size: 18px;
      margin-top: 5px;
    }

    .event-date {
      font-size: 14px;
      color: #999;
    }
    
    /* 기존 TOP 버튼 스타일 유지 (일부 수정) */
  #topBtn {
    font-size: 14px;
    font-weight: bold;
    color: #000000;                  /* 검은색 텍스트 */
  	background-color: #ffffff;       /* 흰색 배경 */
  	border: 1px solid #000000;       /* 검은색 테두리 */
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
    display: block; 				 /* 항상 보이기 */
  }

  #topBtn:hover {
    background-color: #ff69b4;       /* 핑크색 배경 */
  	color: #ffffff;                  /* 흰색 텍스트 */
  	border-color: #ff69b4;           /* 핑크색 테두리 */
  }

  /* aside 사이드바 */
  #sidebar {
    position: fixed;
    top: 200px;
    right: 30px;
    width: 160px;
    z-index: 999;
    /* transform: translateY(200px); */
    /* transition: top 0.3s ease;  /* 부드러운 전환 */ */
    transform: translateY(-50%);
  	transition: transform 0.3s ease;
  }

  #sidebar .side-box {
  background-color: #ffffff;       /* 흰색 배경 */
  border: 1px solid #000000;       /* 검은색 테두리 */
  padding: 15px;
  border-radius: 10px;
  color: #000000;                  /* 검은색 텍스트 */
  font-size: 14px;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.2); /* 감각적인 그림자 추가 */
}


  #sidebar .side-box ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  #sidebar .side-box ul li {
    margin-bottom: 10px;
  }

  #sidebar .side-box ul li a {
    color: #000000; /* 검은색 링크 */
    text-decoration: none;
  }

  #sidebar .side-box ul li a:hover {
    text-decoration: underline;
    color: #ff69b4;   /* 핑크색 (핫핑크) */
  }

  #sidebar .side-box .top-btn-wrapper {
    text-align: center;
    margin-top: 20px;
  }
</style>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<%
  String status = (String) request.getAttribute("status");
  if (status == null) status = "ongoing"; // 안전 보정
%>

<div class="container-flex">
  
  <!-- 왼쪽 메뉴 -->
  <div class="menu-section">
  	<a href="${pageContext.request.contextPath}/eventClick.do?status=ongoing"
     	class="<%= "ongoing".equals(status) ? "active" : "" %>">
    	진행 중인 이벤트
  	</a>
  	<a href="${pageContext.request.contextPath}/eventClick.do?status=ended"
     	class="<%= "ended".equals(status) ? "active" : "" %>">
    	종료된 이벤트
  	</a>
  </div>


  <!-- 메인 영역 -->
  <div class="main-section">
    
    <!-- 검색창 -->
	<div class="search-bar">
  		<form method="get" action="${pageContext.request.contextPath}/eventClick.do">
    	<input type="text" name="keyword" value="${param.keyword}" placeholder="이벤트 검색" />
    	<input type="hidden" name="status" value="${status}" />
  		</form>
	</div>

    <!-- 배너 -->
    <div class="banner">
  		<img src="${pageContext.request.contextPath}/resources/event/이벤트배너_여름프로모션배너.png"
       		alt="이벤트 배너"
       		style="width: 100%; height: 100%; object-fit: cover;" />
	</div>

    <!-- 디버깅용 로그 -->
    <%-- <p>event_detail_list 크기: ${fn:length(event_detail_list)}</p>
    <c:forEach var="detail" items="${event_detail_list}">
      <p>디버깅: ${detail.event_detail_id} / ${detail.event_detail_name} 
			/ ${detail.event_start_date} / ${detail.event_end_date} 
			/ ${detail.event_category} / ${detail.event_image_source}</p>
    </c:forEach> --%>
    
    <!-- --이벤트 목록-- 상태별로 문구 변경 -->
<div style="display: flex; align-items: center; margin: 60px 0 40px;">
  <hr style="flex: 1; border: none; height: 2px; background: linear-gradient(to right, #222, #aaa); box-shadow: 0 0 3px rgba(0,0,0,0.4);">
  <span style="padding: 0 20px; color: #111; font-size: 20px; font-weight: bold; letter-spacing: 1px; text-shadow: 0 1px 1px rgba(0,0,0,0.2);">
    <c:choose>
      <c:when test="${not empty param.keyword}">
        검색 결과: <span style="color:#007bff;">'${param.keyword}'</span>
      </c:when>
      <c:when test="${status eq 'ongoing'}">진행 중인 이벤트</c:when>
      <c:when test="${status eq 'ended'}">종료된 이벤트</c:when>
      <c:otherwise>이벤트 목록</c:otherwise>
    </c:choose>
  </span>
  
  <hr style="flex: 1; border: none; height: 2px; background: linear-gradient(to left, #222, #aaa); box-shadow: 0 0 3px rgba(0,0,0,0.4);">
</div>

    <!-- 이벤트 카드 3열 -->
    <div class="row mt-4 event-card">
      <c:forEach var="detail" items="${event_detail_list}">
        <div class="col-md-4 mb-4">
          <div class="card">
            <c:choose>
              <c:when test="${fn:endsWith(detail.event_image_source, '.mp4')}">
                <video class="card-img-top" autoplay loop muted>
                  <source src="${pageContext.request.contextPath}/resources/video/${detail.event_image_source}" type="video/mp4">
                </video>
              </c:when>
              <c:otherwise>
                <img src="${pageContext.request.contextPath}/resources/img/event_img/${detail.event_image_source}" 
                     class="card-img-top" alt="${detail.event_name}" />
              </c:otherwise>
            </c:choose>
            <div class="card-body">
              <div class="category">${detail.event_category}</div>
              <div class="event-title">${detail.event_name}</div>
              <div class="event-date">
              		이벤트 기간: ${detail.event_start_date} ~ ${detail.event_end_date}
              </div>
              <a href="${pageContext.request.contextPath}/eventClick_serve.do?event_id=${detail.event_id}" 
   					class="btn btn-outline-dark mt-2">자세히 보기</a>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>
	
	<!-- 부드럽게 따라다니는 사이드바 (TOP 버튼 포함) -->
<div id="sidebar">
  <div class="side-box">
    <ul>
      <li><a href="eventMain.do?section=limitevent">이벤트 팝업창</a></li>
      <li><a href="HeaderProductCategoryGo.do?product_category=Goods">굿즈</a></li>
      <li><a href="qnaAllList.do">고객센터</a></li>
    </ul>
    <div class="top-btn-wrapper">
      <button id="topBtn" onclick="scrollToTop()" title="맨 위로">TOP</button>
    </div>
  </div>
</div>

<script>
const topBtn = document.getElementById("topBtn");
const sidebar = document.getElementById("sidebar");

let currentTop = window.scrollY + (window.innerHeight / 2 - 100);
let velocity = 0;
let rafId = null;

// TOP 버튼 표시
window.addEventListener("scroll", () => {
    topBtn.style.display = "block";

  if (!rafId) animateSidebar(); // 스크롤 시 애니메이션 실행
});

// 감속 애니메이션 수행
function animateSidebar() {
  const targetTop = window.scrollY + 200;
  const delta = targetTop - currentTop;
  velocity = delta * 0.15;
  currentTop += velocity;

  // 정수로 반올림하여 부드럽게 적용
  sidebar.style.top = Math.round(currentTop) + "px";

  // velocity가 매우 작으면 정지
  if (Math.abs(delta) < 0.5) {
    sidebar.style.top = Math.round(targetTop) + "px";
    rafId = null;
    return;
  }

  rafId = requestAnimationFrame(animateSidebar);
}

// TOP 버튼 이동
function scrollToTop() {
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

// 처음 실행
animateSidebar();
</script>

<script src="${pageContext.request.contextPath}/resources/js/sakura.js" type="text/javascript"></script>
<script>
  var sakura = new Sakura('body', {
    colors: [
      {
        gradientColorStart: 'rgba(255, 183, 197, 0.9)',
        gradientColorEnd: 'rgba(255, 197, 208, 0.9)',
        gradientColorDegree: 120,
      },
      {
        gradientColorStart: 'rgba(255,189,189)',
        gradientColorEnd: 'rgba(227,170,181)',
        gradientColorDegree: 120,
      },
      {
        gradientColorStart: 'rgba(212,152,163)',
        gradientColorEnd: 'rgba(242,185,196)',
        gradientColorDegree: 120,
      },
    ],
    delay: 100,
  });
</script>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
