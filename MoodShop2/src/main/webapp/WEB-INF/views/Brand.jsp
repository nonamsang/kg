<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MOODSHOP 브랜드 소개</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">


<style>
    html, body {
      margin: 0;
      font-family: 'Arial', sans-serif;
      background: linear-gradient(180deg, #ffffff, #f8f8f8);
      background-size: 100% 200%;
      animation: softFade 20s ease-in-out infinite alternate;
      color: #111111;
      height: 100%;
    }

    .event-wrapper {
      max-width: 960px;
      margin: auto;
      padding: 40px 20px;
      background-color: #ffffff;
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0,0,0,0.05);
      
      min-height: 100%;
      display: flex;
      flex-direction: column;
    }

    .event-title {
      font-size: 34px;
      font-weight: bold;
      /* text-align: center; */
      margin-bottom: 30px;
      border-bottom: 2px solid #ccc;
      padding-bottom: 10px;
    }

    .event-text {
      font-size: 17px;
      line-height: 1.7;
      /* text-align: center; */
      margin-bottom: 30px;
      flex: 1;
    }
    
    .event-banner {
      display: block;
      width: 100%;
      height: auto;
      margin: auto;
      border-radius: 8px;
    }
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/header.jsp" />

	<div class="event-wrapper">
  		<div class="event-title">Moodshop 소개</div>

  			<div class="event-text">
  				<h2>Moodshop</h2><br>
				너의 오늘, 무슨 기분이야?
				<br><br>
				Moodshop은 감정을 담는 공간입니다.<br>
				기쁨, 슬픔, 사랑, 설렘 — 그 모든 순간을 위한 굿즈와 꽃.
				<br><br>
				우리는 감정이 단순히 스쳐가는 게 아니라, 머무를 수 있다고 믿어요.<br>
				그래서 Moodshop은 '기분'을 주제로 한 오브제, 문구, 향기, 그리고 그날의 감정에 어울리는 꽃을 큐레이션합니다.
				<br><br>
				네가 어떤 하루를 보내든, Moodshop이 너의 기분을 더 특별하게 만들어줄게.
				<br><br>
				너의 Mood, 여기서 찾아봐.<br>
  			</div>
	<br><br>
	</div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>