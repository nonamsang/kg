<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
<style>
body {
  background-color: #eef1f6;
  font-family: 'Arial', sans-serif;
  margin: 0;
  padding: 0;
}

.pw-find-box {
  max-width: 420px;
  background: white;
  margin: 80px auto;
  padding: 30px 40px;
  border-radius: 15px;
  box-shadow: 0 5px 25px rgba(0,0,0,0.1);
}

.pw-find-box h2 {
  text-align: center;
  color: #1a237e;
  margin-bottom: 20px;
}

.pw-find-box h2 i {
  margin-right: 8px;
}

.radio-group {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-bottom: 20px;
  font-weight: bold;
  color: #666;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 6px;
  font-weight: bold;
  color: #444;
}

.form-group input[type="text"] {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
}

#errorMsg {
  color: red;
  margin-top: 10px;
  font-size: 13px;
  text-align: center;
  min-height: 20px;
}

.btn-box {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.btn-box input[type="button"] {
  width: 100%;
  padding: 12px 0;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  font-size: 14px;
  cursor: pointer;
  background-color: #3f51b5;
  color: white;
  transition: background-color 0.3s;
}

.btn-box input[type="button"]:hover {
  background-color: #2c387e;
}
</style>
</head>
<body>

<div class="pw-find-box">
  <h2><i class="fa-solid fa-lock"></i>비밀번호 찾기</h2>

  <div class="radio-group">
    <label><input type="radio" name="MemberType" value="User" required> 사용자</label>
    <label><input type="radio" name="MemberType" value="Admin"> 관리자</label>
  </div>

  <div class="form-group">
    <label for="name">이름</label>
    <input type="text" id="name" placeholder="이름을 입력하세요">
  </div>

  <div class="form-group">
    <label for="tel">전화번호</label>
    <input type="text" id="tel" placeholder="전화번호를 입력하세요">
  </div>

  <div class="form-group">
    <label for="id">아이디</label>
    <input type="text" id="id" placeholder="아이디를 입력하세요">
  </div>

  <div id="errorMsg">이름과 전화번호, 아이디를 입력하세요.</div>

  <div class="btn-box">
    <input type="button" id="MyLoginPwSeach" value="비밀번호 찾기">
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
  $("#MyLoginPwSeach").click(function() {
    const memberType = $("input[name='MemberType']:checked").val();
    const name = $("#name").val().trim();
    const tel = $("#tel").val().trim();
    const id = $("#id").val().trim();

    if (!memberType || !name || !tel || !id) {
      $("#errorMsg").text("모든 항목을 입력하세요.").css("color", "red");
      return;
    }

    $.ajax({
      type: "POST",
      url: "MyLoginPwSeach.do",
      data: { MemberType: memberType, name: name, tel: tel, id: id },
      success: function(response) {
        if (response && response !== "NOT_FOUND") {
          $("#errorMsg").text("정보가 일치합니다. 5초 후 비밀번호 변경 페이지로 이동합니다.").css("color", "blue");
          setTimeout(function() {
            const form = document.createElement("form");
            form.method = "POST";
            form.action = "ChangePasswordForm.do";

            const typeInput = document.createElement("input");
            typeInput.type = "hidden";
            typeInput.name = "memberType";
            typeInput.value = memberType;
            form.appendChild(typeInput);

            const idInput = document.createElement("input");
            idInput.type = "hidden";
            idInput.name = "id";
            idInput.value = id;
            form.appendChild(idInput);

            document.body.appendChild(form);
            form.submit();
          }, 5000);
        } else {
          $("#errorMsg").text("일치하는 정보가 없습니다.").css("color", "red");
        }
      },
      error: function() {
        $("#errorMsg").text("서버 오류 발생").css("color", "red");
      }
    });
  });
});
</script>
</body>
</html>
