<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MOODSHOP | 회원수정</title>
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f9f9f9;
    padding: 30px;
}

.container {
    max-width: 550px;
    margin: 0 auto;
    background-color: #ffffff;
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

h2 {
    text-align: center;
    margin-bottom: 30px;
}

.form-group {
    margin-bottom: 15px;
}

label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
}

input[type="text"] {
    width: 90%;
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

button {
    width: 50%;
    padding: 10px;
    background-color: #323232;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    display: block;  /* 중앙 정렬을 위한 필수 속성 */
    margin: 20px auto 0;  /* 위 20px, 좌우 auto로 중앙 */
}

button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
    color: #666;
    border: 1px solid #aaa;
}

</style>
</head>
<body>
<div class="container">
<h2>회원 정보 수정</h2>
<form action="UserUpdate.do" method="post">
	<input type="hidden" name="user_id" value="${user.user_id}" />
	
	<div class="form-group">
            <label>아이디</label>
            <input type="text" name="id" value="${user.user_id}" readonly />
        </div>
        
        <div class="form-group">
            <label>비밀번호</label>
            <input type="text" name="password" id="password"/>
        </div>
        
        <div class="form-group">
        	<label>비밀번호 확인</label>
        	<input type="text" name="passwordcheck" id="passwordcheck"/>
        </div>
        
        <div class="form-group" id="PasswordChecker" style="color: red; margin-top: 5px;">비밀번호를 입력하세요</div>

        <div class="form-group">
            <label>이름</label>
            <input type="text" name="name" id="name" value="${user.name}" />
        </div>
        
        <div class="form-group" id="NameChecker" style="color: gray; margin-top: 5px;">이름을 입력하세요</div>
        
        <div class="form-group">
            <label>주소</label>
            <input type="text" id="postcode" name="postcode" placeholder="우편번호"><input type="button" onclick="DaumPostCode()" value="주소검색"><br>
            <input type="text" id="road_addr" name="road_addr" placeholder="도로명주소" required><br>
            <input type="text" id="addr" name="addr" placeholder="지번주소"><br>
            <input type="text" id="sub_addr" name="sub_addr" placeholder="상세주소"><br>
            <input type="text" id="extra_addr" name="extra_addr" placeholder="참고항목">
        </div>

        <div class="form-group"> 
            <label>닉네임</label>
            <input type="text" name="nickname" id="nickname" value="${user.nickname}" /><input type="button" id="nicknamecheck" value="닉네임 중복확인">
        </div>
        
        <div class="form-group" id="NickNameChecker" style="color: red; margin-top: 5px;">닉네임을 입력하세요</div>

        <div class="form-group">
            <label>전화번호</label>
            <input type="text" name="tel" value="${user.tel}" />
        </div>

        <button type="submit" id="updatesubmit" disabled="disabled">수정하기</button>
</form>
</div>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>// 주소 검색
		function DaumPostCode(){
			new daum.Postcode({
			oncomplete: function(data){
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postcode').value = data.zonecode;
                document.getElementById("road_addr").value = roadAddr;
                document.getElementById("addr").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("extra_addr").value = extraRoadAddr;
                } else {
                    document.getElementById("extra_addr").value = '';
                }

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
		}).open();
	}
	</script>
	
<script>
$(document).ready(function() {
    let isNickNameChecked = false;
    let isPasswordMatched = false;
    let isNameChecked = false;

    function updateSubmitBtnState() {
        if (isNickNameChecked && isPasswordMatched && isNameChecked) {
            $("#updatesubmit").prop("disabled", false);
        } else {
            $("#updatesubmit").prop("disabled", true);
        }
    }

    // ✅ 페이지 로딩 시 초기 상태 적용
    updateSubmitBtnState();

    // 닉네임 중복 확인
    $("#nicknamecheck").click(function() {
        const userNickName = $("#nickname").val();
        $.ajax({
            url: "MyLoginAsDoubleCheck.do",
            type: "POST",
            data: { UserNickName: userNickName },
            success: function(response) {
                if (response === "DUPLICATE") {
                    $("#NickNameChecker").text("이미 사용중인 닉네임 입니다.").css("color", "red");
                    isNickNameChecked = false;
                } else if (response === "AVAILABLE") {
                    $("#NickNameChecker").text("사용가능한 닉네임 입니다.").css("color", "blue");
                    isNickNameChecked = true;
                } else {
                    $("#NickNameChecker").text("잘못된 요청입니다.").css("color", "orange");
                    isNickNameChecked = false;
                }
                updateSubmitBtnState();
            },
            error: function() {
                $("#NickNameChecker").text("에러가 발생하였습니다.").css("color", "red");
                isNickNameChecked = false;
                updateSubmitBtnState();
            }
        });
    });

    // 비밀번호 일치 확인
    $("#password, #passwordcheck").on("keyup", function() {
        const pw = $("#password").val();
        const pwc = $("#passwordcheck").val();
        if (pw && pw === pwc) {
            $("#PasswordChecker").text("비밀번호가 일치합니다.").css("color", "blue");
            isPasswordMatched = true;
        } else {
            $("#PasswordChecker").text("비밀번호가 불일치합니다.").css("color", "red");
            isPasswordMatched = false;
        }
        updateSubmitBtnState();
    });

    // 이름 길이 확인
    $("#name").on("keyup", function() {
        const input = $(this).val();
        let msg = "", color = "gray";
        if (input.length === 0) {
            msg = "이름을 입력하세요.";
            color = "gray";
            isNameChecked = false;
        } else if (input.length < 2) {
            msg = "이름이 너무 짧습니다. : 최소 2자 이상";
            color = "red";
            isNameChecked = false;
        } else if (input.length > 5) {
            msg = "이름이 너무 깁니다. : 최대 5자 이하";
            color = "red";
            isNameChecked = false;
        } else {
            msg = "사용가능한 길이입니다.";
            color = "blue";
            isNameChecked = true;
        }
        $("#NameChecker").text(msg).css("color", color);
        updateSubmitBtnState();
    });
});
</script>

</body>
</html>