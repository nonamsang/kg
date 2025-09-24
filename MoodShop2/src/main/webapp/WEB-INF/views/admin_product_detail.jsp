<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MOOD SHOP | 상품목록</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
.admin_title{
	font-weight: bold;
	margin: 30px 0 10px;
	text-align: center;
}
.adminline {
    width: 85%;       /* 선 길이 */
    height: 3px;       /* 선 두께 */
    background-color: gray;  /* 선 색상 */
    margin-top: 0px;  /* 글자랑 선 사이 여백 */
    margin: 50px auto;      /* 가운데 정렬 + 위아래 여백 */
}
table {
    width: 90%;
    margin: 0 auto;
    border-collapse: collapse;
}

table th, table td {
    border: 1px solid #ddd;
    padding: 12px;
    text-align: center;
}

table th {
    background-color: #f2f2f2;
    font-weight: bold;
}

input[type="text"], select, input[type="file"] {
    width: 95%;
    padding: 6px 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

input[type="submit"], input[type="reset"], input[type="button"] {
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    margin: 10px;
    cursor: pointer;
    background-color: #323232;
    color: white;
}


input[type="reset"] {
    background-color: #323232;
    color: white;
}

input[type="reset"] {
    background-color: #323232;
    color: white;
}

.button-area {
    text-align: center;
    margin-top: 20px;
    margin-bottom: 30px;
}
.back_btn {
  width: 90%;
  margin: 0 auto;       /* 위아래 마진 0, 좌우 중앙 정렬 */
  padding: 5px 0;       /* 약간만 위아래 패딩 */
  text-align: left;     /* 버튼 왼쪽 정렬 (필요하면 center로 바꾸세요) */
}

.back_btn input[type="button"] {
   padding: 10px 20px;
    border: none;
    border-radius: 4px;
    margin: 10px;
    cursor: pointer;
    background-color: #323232;
    color: white;
}

.back_btn input[type="button"]:hover {
  background-color: #323232;
}
</style>
</head>
<body>
<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/common/header2.jsp" />
	<h2 class="admin_title">상품목록</h2>
	<div class="adminline"></div>
	




<!-- 상품목록 테이블 -->
<div class="back_btn">  <!-- 누르면 뒤로가는 버튼 -->
  <input type="button" value="뒤로가기" onclick="history.back()" >
</div>
<div class="table_container">
<form action="/MProductUD.do" method="post">
  <input type="hidden" name="company_id" value="${company_id}">
  <table border="1">
    <tr>
      <th></th>
      <th>상품이름</th>
      <th>상품 가격</th>
      <th>기분</th>
      <th>카테고리</th>
      <th>이미지</th>
    </tr>
    <c:forEach var="product" items="${product}">
      <tr>
        <td><input type="checkbox" name="product_id" value="${product.product_id}"></td>
        <td>${product.product_name}</td>
        <td><fmt:formatNumber value="${product.product_price}" type="number" groupingUsed="true" />원</td>
        <td>${product.product_mood}</td>
        <td>${product.product_category}</td>
        <td>${product.product_image}</td>
      </tr>
    </c:forEach>
  </table>

  <br/>
  <div class="button-area">
  <button type=submit name=action value="more">더보기</button>
  <button type="submit" name=action value="update">수정</button>
  <button type="submit" name=action value="delete">삭제</button>
  <button type="reset">취소</button>
  </div>
</form>
<c:if test="${not empty msg}">
  <script>
    alert("${msg}");
  </script>
</c:if>
<c:if test="${not empty ok}">
  <script>
    alert("${ok}");
  </script>
</c:if>
 </div>   
    
    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>