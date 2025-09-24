<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>댓글 수정창</title>
</head>
<body>
<h1>댓글 수정</h1>
<a href="MytoReviewList.do">리뷰보기창으로 가기</a>
<hr>

<form action="MyReviewSubUpdate.do" method="post">
    <input type="hidden" name="sub_id" value="${subone.sub_id}" />
    <input type="hidden" name="user_id" value="${subone.user_id}" />
    <input type="hidden" name="review_id" value="${subone.review_id}" />

    <table border="1">
        <tr>
            <td>댓글 ID</td>
            <td>${subone.sub_id}</td>
        </tr>
        <tr>
            <td>작성일</td>
            <td>${subone.sub_date}</td>
        </tr>
        <tr>
            <td>작성자</td>
            <td>${subone.user_id}</td>
        </tr>
        <tr>
            <td>리뷰 ID</td>
            <td>${subone.review_id}</td>
        </tr>
        <tr>
            <td colspan="2">
                <textarea name="sub_content" cols="50" rows="8">${subone.sub_content}</textarea>
            </td>
        </tr>
    </table>
    <br>
    <button type="submit">수정하기</button>
</form>
</body>
</html>