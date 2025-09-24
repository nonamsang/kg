<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>리뷰 작성</title>
  <style>
    body {
      font-family: 'Arial', sans-serif;
      padding: 50px;
      background-color: #f9f9f9;
    }

    h2 {
      text-align: center;
      margin-bottom: 30px;
    }

    form {
      max-width: 600px;
      margin: 0 auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    textarea {
      width: 100%;
      padding: 10px;
      font-size: 16px;
      border: 1px solid #ccc;
      border-radius: 5px;
      resize: none;
    }

    input[type="file"] {
      margin-top: 10px;
    }

    .star-rating {
      direction: rtl;
      display: flex;
      justify-content: center;
      font-size: 2rem;
      margin-bottom: 20px;
    }

    .star-rating input {
      display: none;
    }

    .star-rating label {
      color: #ddd;
      cursor: pointer;
      transition: color 0.2s;
    }

    .star-rating input:checked ~ label,
    .star-rating label:hover,
    .star-rating label:hover ~ label {
      color: #ffcc00;
    }

    input[type="submit"] {
      width: 100%;
      padding: 12px;
      font-size: 16px;
      background-color: #ff6f61;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    input[type="submit"]:hover {
      background-color: #ff4c3b;
    }
  </style>

  <script>
    document.addEventListener("DOMContentLoaded", function () {
      document.getElementById("reviewInsertForm").addEventListener("submit", function (e) {
        var files = document.getElementById("review_image").files;
        if (files.length > 5) {
          e.preventDefault();
          alert("이미지는 최대 5장까지 업로드 가능합니다.");
        }
      });
    });
  </script>
</head>

<body>

  <h2>리뷰 작성</h2>

  <form id="reviewInsertForm" action="MyReviewInsert.do" method="POST" enctype="multipart/form-data">
    <!-- 리뷰 대상 주문번호 -->
    <input type="hidden" name="order_id" value="${order_id}" />

    <p><strong>주문번호:</strong> ${order_id}</p>

    <!-- 별점 선택 -->
    <p><strong>별점 선택:</strong></p>
    <div class="star-rating">
      <input type="radio" id="star5" name="rating" value="5" required><label for="star5">&#9733;</label>
      <input type="radio" id="star4" name="rating" value="4"><label for="star4">&#9733;</label>
      <input type="radio" id="star3" name="rating" value="3"><label for="star3">&#9733;</label>
      <input type="radio" id="star2" name="rating" value="2"><label for="star2">&#9733;</label>
      <input type="radio" id="star1" name="rating" value="1"><label for="star1">&#9733;</label>
    </div>

    <!-- 리뷰 내용 -->
    <p><strong>리뷰 내용:</strong></p>
    <textarea name="review_content" rows="6" cols="60" placeholder="리뷰를 입력하세요." required></textarea>
    <br><br>

    <!-- 이미지 업로드 -->
    <p><strong>이미지 업로드 (최대 5장):</strong></p>
    <input type="file" id="review_image" name="review_image" multiple accept="image/*" />
    <br><br>

    <!-- 등록 버튼 -->
    <input type="submit" value="리뷰 등록" />
  </form>

</body>
</html>
