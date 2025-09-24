<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>찜 수량 통계</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style>
  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #f0f0f0 0%, #ffffff 100%);
    padding: 40px 10%;
    color: #333;
    margin: 0;
    text-align: center;
  }

  a {
    display: inline-block;
    margin-bottom: 25px;
    font-weight: 600;
    color: #4CAF50;
    text-decoration: none;
    transition: color 0.3s ease;
  }

  a:hover {
    color: #388E3C;
  }

  h2 {
    margin-bottom: 30px;
  }

  canvas {
    background-color: white;
    padding: 20px;
    border-radius: 16px;
    box-shadow: 0 3px 15px rgb(0 0 0 / 0.1);
	display: block;
	margin-left: auto;
	margin-right: auto;
  }

  @media screen and (max-width: 600px) {
    canvas {
      width: 100% !important;
      height: auto !important;
    }
    body {
      padding: 20px 5%;
      font-size: 14px;
    }
  }
  
</style>
</head>
<body>

<a href="AdminMainPage.do">← 관리자 메인 페이지로 돌아가기</a>

<h2>전체 상품 찜 수 Top 5 + 기타</h2>
<canvas id="wishPieChart" width="450" height="450"
	style="width: 450px; height: 450px;"></canvas>

<script>
    const labels = [
        <c:forEach var="item" items="${totalTop5}">
            "<c:out value='${item.label}'/>",
        </c:forEach>
    ];

    const data = [
        <c:forEach var="item" items="${totalTop5}">
            ${item.count},
        </c:forEach>
    ];

    const backgroundColors = [
        'rgba(255, 99, 132, 0.7)',
        'rgba(54, 162, 235, 0.7)',
        'rgba(255, 206, 86, 0.7)',
        'rgba(75, 192, 192, 0.7)',
        'rgba(153, 102, 255, 0.7)',
        'rgba(201, 203, 207, 0.7)'
    ];

    const config = {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: '찜 수',
                data: data,
                backgroundColor: backgroundColors,
                borderColor: 'rgba(255, 255, 255, 1)',
                borderWidth: 2
            }]
        },
        options: {
            responsive: false,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                title: {
                    display: true,
                    text: '전체 회사 상품 찜 수 통계'
                }
            }
        }
    };

    new Chart(
        document.getElementById('wishPieChart'),
        config
    );
</script>

</body>
</html>
