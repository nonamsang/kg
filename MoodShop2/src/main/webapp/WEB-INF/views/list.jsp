<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MOODSHOP | 회원목록</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
<style>
.admin_title{
	font-weight: bold;
	margin: 30px 0 10px;
	text-align: center;
}
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
            text-align: center;
        }
        td {
            text-align: center;
        }
        .adminline {
    width: 85%;       /* 선 길이 */
    height: 3px;       /* 선 두께 */
    background-color: gray;  /* 선 색상 */
    margin-top: 0;  /* 글자랑 선 사이 여백 */
    margin: 50px auto;      /* 가운데 정렬 + 위아래 여백 */
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header2.jsp" />

<h2 class="admin_title">회원 목록</h2>
<div class="adminline"></div><!-- 김동주가 일부 수정함, 도로명 주소와 지번 주소가 보이도록 함 -->
<table>
	<thead>
		<tr>
			<th>ID</th>
            <th>이름</th>
            <th>주소</th>
            <th>닉네임</th>
            <th>전화번호</th>
        </tr>
    </thead>
<tbody>
    <c:forEach var="userMap" items="${userList}">
        <c:set var="user" value="${userMap.user}" />
        <tr>
            <td>${user.user_id}</td>
            <td>${user.name}</td>

            <!-- 주소: 도로명 주소 또는 지번 주소 출력 -->
            <td>
                <strong>도로명:</strong> ${userMap.roadFullAddress}<br />
                <strong>지번:</strong> ${userMap.normalFullAddress}
            </td>

            <td>${user.nickname}</td>
            <td>${user.tel}</td>

            <td>
                <a href="UserUpdatePage.do?user_id=${user.user_id}">수정</a> |
                <a href="DeleteUser.do?user_id=${user.user_id}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
            </td>
        </tr>
    </c:forEach>
</tbody>
</table>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>