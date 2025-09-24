<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>리뷰 수정</title>

<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
%>

<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #f5f5f5;
	padding: 30px;
}

.container {
	max-width: 700px;
	margin: 0 auto;
	background-color: #fff;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

nav a {
	text-decoration: none;
	color: #007bff;
	font-weight: bold;
}

h2, h4 {
	text-align: center;
}

input[type="file"], textarea {
	width: 100%;
	padding: 10px;
	margin-top: 10px;
	margin-bottom: 20px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-sizing: border-box;
	font-size: 1rem;
}

input[type="submit"], input[type="reset"] {
	padding: 10px 20px;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	margin-right: 10px;
	font-size: 1rem;
	transition: background-color 0.3s, color 0.3s;
}

input[type="submit"] {
	background-color: #007bff;
	color: white;
}

input[type="reset"] {
	background-color: #6c757d;
	color: white;
}

input[type="submit"]:hover {
	background-color: #0056b3;
}

input[type="reset"]:hover {
	background-color: #5a6268;
}
</style>
</head>

<body>

	<div class="container">

		<nav>
			<a href="MyPage.do">← 뒤로가기</a>
		</nav>
		<hr>

		<h2>리뷰 수정 안내문</h2>
		<h4>
			리뷰 작성 시 욕설이나 비속어는 삼가주세요.<br> 수정하실 내용이 없으시면 뒤로가기를 눌러주세요.<br> 내용을 삭제하고 싶다면 초기화를 눌러주세요.<br> 이미지 첨부는 선택 사항이며, 최대 1장까지 업로드 가능합니다.
		</h4>

		<form id="reviewUpdateForm" action="MyReviewUpdate.do" method="post" enctype="multipart/form-data">
			<input type="hidden" name="review_id" value="${update.review_id}"> <input type="hidden" name="user_id" value="${update.user_id}">

			<p>
				<strong>리뷰번호:</strong> ${update.review_id} &nbsp; <strong>작성자:</strong> ${update.user_id}
			</p>

			<label for="review_image"><strong>이미지 업로드 (최대 5장)</strong></label><input type="file" name="upload_image" id="review_image" accept="image/*" multiple> <label for="review_content"><strong>리뷰 내용</strong></label>
			<textarea rows="10" name="review_content" id="review_content" required placeholder="리뷰를 입력하세요...">${update.review_content}</textarea>

			<input type="submit" value="리뷰 수정"> <input type="reset" value="초기화">
		</form>

	</div>

	<script>
		document.getElementById("reviewUpdateForm").addEventListener("submit", function(e) {
			var files = document.getElementById("review_image").files;
			if (files.length > 5) {
				e.preventDefault();
				alert("파일은 최대 5장까지 업로드 가능합니다.");
			}
		});

		document.getElementById("reviewUpdateForm").addEventListener("reset", function(e) {
			setTimeout(function() {
				document.getElementById("review_content").value = "";
			}, 0);
		});
	</script>

</body>
</html>
