<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>상품 등록</title>
  <style>
    body {
      font-family: 'Segoe UI', sans-serif;
      background: #f7f9fc;
      margin: 0;
      padding: 40px;
    }

    h2 {
      text-align: center;
      color: #2c3e50;
    }

    form {
      max-width: 600px;
      margin: auto;
      background: #fff;
      padding: 30px;
      border-radius: 15px;
      box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    td {
      padding: 12px;
      vertical-align: top;
    }

    input[type="text"],
    select,
    input[type="file"] {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 8px;
      font-size: 14px;
    }

    input[type="submit"],
    input[type="reset"] {
      padding: 10px 20px;
      border: none;
      border-radius: 8px;
      font-weight: bold;
      font-size: 15px;
      margin-top: 20px;
      cursor: pointer;
    }

    input[type="submit"] {
      background-color: #3498db;
      color: white;
      margin-right: 10px;
    }

    input[type="reset"] {
      background-color: #e0e0e0;
      color: #2c3e50;
    }

    input[type="submit"]:hover {
      background-color: #2980b9;
    }

    input[type="reset"]:hover {
      background-color: #ccc;
    }

    tr:first-child td {
      text-align: center;
      font-size: 18px;
      font-weight: bold;
      background-color: #ecf0f1;
      border-radius: 10px 10px 0 0;
    }
  </style>
</head>
<body>

<h2>📦 판매할 상품 등록</h2>

<c:set var="categories" value="Goods,Flower" />
<c:set var="product_mood_list" value="기쁨,슬픔,분노,사랑,불안,평온" />

<form action="MProductInsertAll.do" method="post" enctype="multipart/form-data" onsubmit="return no()">
  <table id="productTable">
    <tbody class="group">
      <tr><td colspan="2">상품 등록 폼</td></tr>
      <tr>
        <td>상품명</td>
        <td><input type="text" name="product_name" required placeholder="상품명을 입력하세요" /></td>
      </tr>
      <tr>
        <td>상품가격</td>
        <td><input type="text" name="product_price" required placeholder="가격을 입력하세요" /></td>
      </tr>
      <tr>
        <td>감정</td>
        <td>
          <select name="product_mood" required>
            <option value="">-- 선택하세요 --</option>
            <c:forEach var="mood" items="${fn:split(product_mood_list, ',')}">
              <option value="${mood}">${mood}</option>
            </c:forEach>
          </select>
        </td>
      </tr>
      <tr>
        <td>카테고리</td>
        <td>
          <select name="product_category" required>
            <option value="">-- 선택하세요 --</option>
            <c:forEach var="category" items="${fn:split(categories, ',')}">
              <option value="${category}">${category}</option>
            </c:forEach>
          </select>
        </td>
      </tr>
      <tr>
        <td>상품 이미지</td>
        <td><input type="file" name="product_image"></td>
      </tr>
    </tbody>
  </table>

  <input type="submit" name="action" value="상품 등록" />
  <input type="reset" value="초기화" />
</form>

<c:if test="${not empty ok}">
  <script>
    alert("<c:out value='${ok}'/>");
  </script>
</c:if>

<script>
function no() {
  const mood = document.querySelector("select[name='product_mood']").value;
  const category = document.querySelector("select[name='product_category']").value;

  if (mood === "" && category === "") {
    alert("감정과 카테고리를 선택해주세요");
    return false;
  } else if (mood === "") {
    alert("감정을 선택해주세요");
    return false;
  } else if (category === "") {
    alert("카테고리를 선택해주세요");
    return false;
  } else {
    return true;
  }
}
</script>

</body>
</html>
