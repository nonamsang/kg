<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Page - MOODSHOP</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
  <style>
    html, body {
      height: 100%;
      margin: 0;
    }

    body {
      font-family: 'Arial', sans-serif;
      display: flex;
      flex-direction: column;
      min-height: 100vh;
      background-color: #f0f4f8;
    }

    .main-content {
      flex: 1;
      display: flex;
      justify-content: center;
      padding: 80px 0;
    }

    .container {
      display: flex;
      width: 90%;
      max-width: 1400px;
      margin: 0 auto;
      gap: 20px;
    }

    .sidebar {
      width: 280px;
      min-width: 280px;
      background-color: #ffffff;
      border-radius: 12px;
      padding: 20px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    }

    .content {
      flex-grow: 1;
      background-color: #ffffff;
      border-radius: 12px;
      padding: 30px;
      min-height: 500px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
    }

    /* ✅ 유저 카드 디자인 */
    .user-info {
      margin-bottom: 20px;
      padding: 20px;
      border-radius: 12px;
      background: linear-gradient(135deg, #dbe9ff, #f0f4ff);
      font-size: 14px;
      line-height: 1.6;
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    }

    .user-info div {
      display: flex;
      align-items: center;
      margin-bottom: 10px;
      font-weight: bold;
      color: #333;
    }

    .user-info div i {
      margin-right: 10px;
      color: #1a237e;
    }

    .menu-link {
      display: block;
      padding: 12px;
      margin: 10px 0;
      background-color: #e8f0fe;
      border-radius: 8px;
      text-decoration: none;
      color: #1a237e;
      font-weight: 600;
      transition: all 0.2s ease;
    }

    .menu-link:hover {
      background-color: #d2e3fc;
      color: #0d47a1;
    }

    .topbar {
  		text-align: right;
  		padding: 10px 40px;
  		position: static; /* ✅ 고정 해제 */
  		margin-top: -60px; /* ✅ header와 자연스럽게 겹치도록 조정 */
	}


    .topbar a {
      margin-left: 15px;
      text-decoration: none;
      color: #333;
      font-size: 14px;
    }

    .alert {
      color: red;
      font-weight: bold;
    }

    footer {
      margin-top: 40px;
    }
    
    .qna-quick {
  		margin-top: 40px;
  		padding: 10px 20px;
  		background: linear-gradient(135deg, #ff7f50, #ffb347);
  		border-radius: 30px;
  		color: white;
  		font-weight: bold;
  		text-align: center;
  		cursor: pointer; /* 포인터 적용 */
  		box-shadow: 0 4px 8px rgba(0,0,0,0.1);
  		transition: all 0.3s ease;
	}

	.qna-quick:hover {
  		background: linear-gradient(135deg, #ff5722, #ff9800);
  		transform: translateY(-2px);
	}

  </style>
</head>
<body>

  <!-- ✅ header.jsp include -->
  <jsp:include page="/WEB-INF/views/include/header.jsp" />

  <div class="topbar">
    <a href="MainMoodShop.do"><i class="fa-solid fa-house"></i> 메인페이지로</a>
    <a href="Logout.do"><i class="fa-solid fa-right-from-bracket"></i> 로그아웃</a>
  </div>

  <div class="main-content">

    <div class="container">
      <!-- 좌측 메뉴 -->
      <div class="sidebar">
        <c:choose>
          <c:when test="${not empty userType}">
            <div class="user-info">
              <div><i class="fa-solid fa-user"></i> 이름: ${name}</div>
              <div><i class="fa-solid fa-id-badge"></i> 닉네임: ${nickname}</div>
            </div>
          </c:when>
          <c:otherwise>
            <p class="alert">로그인이 필요합니다.</p>
          </c:otherwise>
        </c:choose>

        <a href="MyOrderHistory.do" class="menu-link"><i class="fa-solid fa-box"></i> 주문 내역</a>
        <a href="MyWishList.do" class="menu-link"><i class="fa-solid fa-heart"></i> 찜 목록</a>
        <a href="MyCartList.do" class="menu-link"><i class="fa-solid fa-cart-shopping"></i> 장바구니</a>
        <a href="recentViewList.do" class="menu-link"><i class="fa-solid fa-eye"></i> 최근 본 상품</a>
        <a href="MyReviewList.do" class="menu-link"><i class="fa-solid fa-pen"></i> 나의 리뷰</a>
        <br><br>
        <!-- ✅ 사이드바 맨 아래 Q&A 관리 추가 -->
  		<div class="qna-quick" onclick="location.href='gotoQnA.do'">❓ Q&A 바로가기</div>
      </div>

      <!-- AJAX 콘텐츠 영역 -->
      <div class="content" id="contents">
        <p>원하는 메뉴를 선택하세요.</p>
      </div>
    </div>

  </div>

  <!-- ✅ footer.jsp include -->
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />

  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script>
    $(document).ready(function(){
      $(".menu-link").click(function(e){
        e.preventDefault();
        const url = $(this).attr("href");
        $("#contents").html("<p>불러오는 중...</p>");

        $.ajax({
          url: url,
          type: "POST",
          success: function(data){
            $("#contents").html(data);
          },
          error: function(xhr, status, error){
            $("#contents").html("<p style='color:red;'>페이지 로드 실패: " + error + "</p>");
          }
        });
      });
    });
  </script>
</body>
</html>
