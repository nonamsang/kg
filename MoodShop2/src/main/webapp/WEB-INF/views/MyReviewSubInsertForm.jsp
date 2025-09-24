<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>댓글 입력창</title> <!-- 댓글 등록하는 창 (MyReviewSubInsertForm) -->

<style>
body {
    font-family: 'Arial', sans-serif;
    padding: 0;
    margin: 0;
    background-color: #ffffff; /* 전체 배경 흰색 */
}

.main-container {
    padding: 40px;
    background-color: #fff; /* 본문도 흰색 유지 */
    min-height: calc(100vh - 160px); /* header, footer 제외한 높이 */
}

h1 {
    text-align: center;
    color: #222;
    font-size: 2.5rem;
    margin-bottom: 30px;
    position: relative;
    animation: fadeInDown 1s ease; /* 제목 부드럽게 등장 */
}

@keyframes fadeInDown {
    0% { opacity: 0; transform: translateY(-50px); }
    100% { opacity: 1; transform: translateY(0); }
}

a.back-link {
    display: inline-block;
    margin-bottom: 30px;
    text-decoration: none;
    color: #ff7f50; /* 주황색 */
    font-weight: bold;
    border: 1px solid #ff7f50;
    padding: 8px 16px;
    border-radius: 5px;
    transition: background-color 0.3s, color 0.3s, transform 0.2s;
}

a.back-link:hover {
    background-color: #ff7f50;
    color: #fff;
    transform: scale(1.05);
}

form {
    background-color: #fff;
    padding: 40px;
    border-radius: 16px;
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
    max-width: 600px;
    margin: 0 auto;
    animation: fadeInUp 1s ease; /* 폼 부드럽게 등장 */
}

@keyframes fadeInUp {
    0% { opacity: 0; transform: translateY(50px); }
    100% { opacity: 1; transform: translateY(0); }
}

textarea {
    width: 100%;
    height: 200px;
    padding: 15px;
    border: 1px solid #ccc;
    border-radius: 10px;
    resize: none;
    font-size: 1rem;
    outline: none;
    transition: border-color 0.3s, box-shadow 0.3s;
}

textarea:focus {
    border-color: #ff7f50;
    box-shadow: 0 0 5px rgba(255, 127, 80, 0.5);
}

input[type=submit] {
    background-color: #ff7f50; /* 주황색 버튼 */
    color: #fff;
    border: none;
    padding: 12px 24px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 1rem;
    margin-top: 20px;
    transition: background-color 0.3s, transform 0.2s;
}

input[type=submit]:hover {
    background-color: #e7663c; /* 진한 주황색 */
    transform: scale(1.05);
}

hr {
    border: none;
    border-top: 2px dashed #ff7f50;
    margin-bottom: 30px;
}
</style>

</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="main-container">
    <h1>대댓글 등록</h1>
    <a href="MytoReviewList.do" class="back-link">뒤로가기</a>
    <hr>

    <form action="MyReviewSubInsert.do" method="get"> 
        <input type="hidden" name="review_id" value="${param.review_id}">
        <textarea cols="30" rows="10" name="sub_content" placeholder="댓글을 입력해주세요"></textarea>
        <br>
        <input type="submit" value="등록하기">
    </form>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
