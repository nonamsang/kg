<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="managerVO" value="${sessionScope.managerVO}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 주문 목록</title>
<style>
  body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(135deg, #f0f0f0 0%, #ffffff 100%);
    padding: 40px 10%;
    color: #333;
    margin: 0;
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

  form {
    background-color: white;
    padding: 20px 25px;
    border-radius: 10px;
    box-shadow: 0 3px 15px rgb(0 0 0 / 0.1);
    margin-bottom: 20px;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    font-size: 16px;
    background-color: white;
    border-radius: 10px;
    box-shadow: 0 3px 15px rgb(0 0 0 / 0.1);
    overflow: hidden;
  }

  th, td {
    padding: 12px 15px;
    text-align: center;
    border-bottom: 1px solid #ddd;
    vertical-align: middle;
  }

  thead th {
    background-color: #4CAF50;
    color: white;
    font-weight: 700;
    letter-spacing: 0.05em;
  }

  tbody tr:hover {
    background-color: #e8f5e9;
  }

  input[type="radio"] {
    margin-right: 5px;
    cursor: pointer;
  }

  #pagination {
    text-align: center;
    margin-top: 20px;
  }

  #pagination button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 8px 16px;
    margin: 0 5px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
  }

  #pagination button:hover {
    background-color: #388E3C;
  }

  #pagination button[style*="bold"] {
    background-color: #2E7D32;
  }

  @media screen and (max-width: 600px) {
    body {
      padding: 20px 5%;
      font-size: 14px;
    }
    table {
      font-size: 14px;
    }
    #pagination button {
      padding: 6px 10px;
      font-size: 13px;
    }
  }
</style>
</head>
<body>
<a href="AdminMainPage.do">← 관리자 메인 페이지로 돌아가기</a>
	<form>
		<label><input type="radio" name="addrType" value="road" checked onclick="changeAddressType('road')"> 도로명 주소</label>
    	<label><input type="radio" name="addrType" value="normal" onclick="changeAddressType('normal')"> 지번 주소</label>
	</form>

	<table>
		<tr>
			<th>주문 날짜|시간</th>
			<th>주문자</th>
			<th>주문 받은 회사</th>
			<th>주문제품명</th>
			<th>수량</th>
			<th>총가격</th>
			<th>주소</th>
		</tr>
		<c:forEach var="comL" items="${combinedList}" varStatus="status">
			<tr class="data-row" data-index="${status.index}">
				<td><fmt:formatDate value="${comL.orderDate}" pattern="yyyy-MM-dd(EEEE)||HH:mm:ss" /></td>
				<td>${comL.userName}</td>
				<td>${comL.companyName}</td>
				<td>${comL.productName}</td>
				<td>${comL.productCount}</td>
				<td><fmt:formatNumber value="${comL.totalPrice}" type="number" groupingUsed="true" />원</td>
				<td>
					<span class="addr-road" id="road-${status.index}">${comL.road_fulladdress}</span>
                	<span class="addr-normal" id="normal-${status.index}" style="display: none;">${comL.normal_fulladdress}</span>
				</td>
			</tr>
		</c:forEach>
	</table>

	<div id="pagination"></div>

	<script>
		function changeAddressType(type) {
			document.querySelectorAll('.addr-road').forEach(e => e.style.display = (type === 'road') ? 'inline' : 'none');
  			document.querySelectorAll('.addr-normal').forEach(e => e.style.display = (type === 'normal') ? 'inline' : 'none');
   		}
   	    const rowsPerPage = 10;
   	    const rows = document.querySelectorAll(".data-row");
   	    const totalPages = Math.ceil(rows.length / rowsPerPage);
   	    const pagination = document.getElementById("pagination");

   	    function showPage(page) {
   	        rows.forEach((row, index) => {
   	            row.style.display = (index >= (page - 1) * rowsPerPage && index < page * rowsPerPage) ? "" : "none";
   	        });

   	        pagination.innerHTML = "";
   	        for (let i = 1; i <= totalPages; i++) {
   	            const btn = document.createElement("button");
   	            btn.textContent = i;
   	            if (i === page) btn.style.fontWeight = "bold";
   	            btn.onclick = () => showPage(i);
   	            pagination.appendChild(btn);
   	        }
   	    }
   	    showPage(1);
	</script>

</body>
</html>
