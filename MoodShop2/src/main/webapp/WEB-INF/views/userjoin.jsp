<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 회원 가입</title>
<style>
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: 'Nunito', sans-serif;
}

body {
	background: linear-gradient(to bottom right, #f9f7f7, #dbe2ef);
	min-height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 40px 20px;
}

form {
	background: white;
	border-radius: 20px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
	padding: 40px 50px;
	width: 520px;
}

h1 {
	text-align: center;
	margin-bottom: 30px;
	color: #112d4e;
	font-weight: 700;
	font-size: 28px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

td {
	padding: 10px 10px 8px 0;
	vertical-align: middle;
	font-weight: 600;
	color: #3f72af;
	width: 120px;
}

td input[type="text"], td input[type="password"], td input[type="tel"] {
	width: 100%;
	padding: 10px 12px;
	border-radius: 10px;
	border: 1px solid #ccc;
	font-size: 14px;
	font-family: 'Nunito', sans-serif;
	transition: border-color 0.3s;
}

td input[type="text"]:focus, td input[type="password"]:focus, td input[type="tel"]:focus
	{
	border-color: #3f72af;
	outline: none;
}

td input[type="button"] {
	padding: 8px 15px;
	background-color: #3f72af;
	border: none;
	border-radius: 10px;
	color: white;
	font-weight: 600;
	cursor: pointer;
	transition: background-color 0.3s;
}

td input[type="button"]:hover {
	background-color: #112d4e;
}

#NameChecker, #IDChecker, #PasswordChecker, #NickNameChecker {
	font-size: 13px;
	margin-top: 5px;
	font-weight: 600;
}

.full-row {
	padding-left: 0 !important;
	color: #112d4e;
	font-weight: 600;
}

#regisubmit {
	width: 100%;
	margin-top: 30px;
	padding: 15px 0;
	background-color: #3f72af;
	color: white;
	font-size: 18px;
	font-weight: 700;
	border: none;
	border-radius: 12px;
	cursor: pointer;
	transition: background-color 0.3s;
}

#regisubmit:disabled {
	background-color: #9bb1d4;
	cursor: not-allowed;
}

#regisubmit:hover:not(:disabled) {
	background-color: #112d4e;
}

input[type="button"].back-btn {
	margin-top: 15px;
	width: 100%;
	padding: 12px 0;
	background-color: #f25f5c;
	border: none;
	border-radius: 12px;
	color: white;
	font-weight: 700;
	cursor: pointer;
	transition: background-color 0.3s;
}

input[type="button"].back-btn:hover {
	background-color: #a63631;
}

.form-button {
	width: 100%;
	background-color: #3f72af;
	color: white;
	padding: 12px;
	border: none;
	border-radius: 10px;
	font-size: 16px;
	cursor: pointer;
	transition: 0.3s;
	margin-top: 10px;
}

.form-button:hover {
	background-color: #112d4e;
}
</style>

</head>
<body>
	<form action="MyLoginBeen.do" method="post">
		<h1>사용자 회원 가입</h1>
		<table border=0>
			<tr>
				<td>이름</td>
				<td><input type="text" id="name" name="name" placeholder="이름 입력" required></td>
				<td></td>
			</tr>
			<tr>
				<td colspan=3><div id="NameChecker" style="color: gray; margin-top: 5px;">이름을 입력하세요</div></td>
			</tr>
			<tr>
				<td>아이디</td>
				<td><input type="text" id="user_id" name="user_id" placeholder="아이디 입력" required></td>
				<td><input type="button" id="idcheck" value="아이디 중복확인"></td>
			</tr>
			<tr>
				<td colspan=3>
					<!-- MyLoginIdDoubleCheck.do -->
					<div id="IDChecker" style="color: red; margin-top: 5px;">아이디를 입력하세요</div>
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" id="password" name="password" placeholder="비밀번호 입력" required></td>
				<td></td>
			</tr>
			<tr>
				<td>비밀번호 확인</td>
				<td><input type="password" id="passwordcheck" placeholder="비밀번호 재입력" required></td>
				<td></td>
			</tr>
			<tr>
				<td colspan=3>
					<div id="PasswordChecker" style="color: red; margin-top: 5px;">비밀번호를 입력하세요</div>
				</td>
			</tr>
			<tr>
				<td>닉네임</td>
				<td><input type="text" id="nickname" name="nickname" placeholder="닉네임 입력" required>
				<td><input type="button" id="nicknamecheck" value="닉네임 중복확인"></td>
			</tr>
			<tr>
				<td colspan=3>
					<!-- MyLoginAsDoubleCheck.do -->
					<div id="NickNameChecker" style="color: red; margin-top: 5px;">닉네임을 입력하세요</div>
				</td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type="tel" name="tel" placeholder="전화번호 입력" required></td>
				<td></td>
			</tr>
			<tr>
				<td>주소</td>
				<td colspan=2><input type="text" id="postcode" name="postcode" placeholder="우편번호"> <input type="button" onclick="DaumPostCode()" value="주소검색"><br> <input type="text" id="road_addr" name="road_addr" placeholder="도로명주소" required><br> <input type="text" id="addr" name="addr" placeholder="지번주소" required><br> <input type="text" id="sub_addr" name="sub_addr" placeholder="상세주소" required><br> <input type="text" id="extra_addr" name="extra_addr" placeholder="참고항목"></td>
			</tr>
		</table>
		<input type="submit" id="regisubmit" value="가입하기" disabled> <input type="button" class="form-button" onclick="location.href='MyLoginMember.do'" value="이전">
	</form>
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
	<script> // 아이디 체크, 닉네임 체크, 비밀번호 일치 확인, 이름 길이 확인
	let isIdChecked=false;//아이디 체크 확인 여부
	let isNickNameChecked=false;// 닉네임 체크 확인 여부
	let isPasswordMatched=false;// 비밀번호 일치 확인 여부
	let isNameChecked=false; // 이름 길이 확인 여부
	
	function updateSubmitBtnState() {//가입하기 버튼 활성화 여부
		if (isIdChecked && isNickNameChecked && isPasswordMatched && isNameChecked) {
			$("#regisubmit").prop("disabled", false);
		} else {
			$("#regisubmit").prop("disabled", true);
		}
	}
	//아이디 체크
	$(document).ready(function() {
		$("#idcheck").click(function(){
			var userId = $("#user_id").val();
			$.ajax({
				url:"MyLoginIdDoubleCheck.do",
				type:"POST",
				data:{user_id: userId},
				success: function(response) {
					if(response === "DUPLICATE") {
						$("#IDChecker").text("이미 사용중인 아이디 입니다.").css("color","red");
						isIdChecked=false;
					} else if (response === "AVAILABLE") {
						$("#IDChecker").text("사용가능한 아이디 입니다.").css("color","blue");
						isIdChecked=true;
					} else {
						$("#IDChecker").text("잘못된 요청입니다.").css("color","orange");
						isIdChecked=false;
					}
					updateSubmitBtnState();
				},
				error: function(){
					$("#IDChecker").text("에러가 발생하였습니다.").css("color","red");
					isIdChecked=false;
					updateSubmitBtnState();
				}
			});
		});
	// 닉네임 체크
		$("#nicknamecheck").click(function(){
			var userNickName = $("#nickname").val();
			$.ajax({
				url:"MyLoginAsDoubleCheck.do",
				type:"POST",
				data:{UserNickName: userNickName},
				success: function(response) {
					if(response === "DUPLICATE") {
						$("#NickNameChecker").text("이미 사용중인 닉네임 입니다.").css("color","red");
						isNickNameChecked = false;
					} else if (response === "AVAILABLE") {
						$("#NickNameChecker").text("사용가능한 닉네임 입니다.").css("color","blue");
						isNickNameChecked = true;
					} else {
						$("#NickNameChecker").text("잘못된 요청입니다.").css("color","orange");
						isNickNameChecked = false;
					}
					updateSubmitBtnState();
				},
				error: function(){
					$("#NickNameChecker").text("에러가 발생하였습니다.").css("color","red");
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
				$("#PasswordChecker").text("비밀번호가 일치합니다.").css("color","blue");
				isPasswordMatched = true;
			} else {
				$("#PasswordChecker").text("비밀번호가 불일치합니다.").css("color","red");
				isPasswordMatched = false;
			}
			updateSubmitBtnState();
		});
	// 이름 길이 확인
		$("#name").on("keyup", function() {
			var input = $(this).val();
			var meg = "";
			var color = "gray";
			
			if (input.length === 0) {
				meg = "이름을 입력하세요.";
				color = "gray";
				isNameChecked=false;
			} else if (input.length < 2) {
				meg = "이름이 너무 짧습니다. : 최소 2자 이상";
				color = "red";
				isNameChecked=false;
			} else if (input.length > 5) {
				meg = "이름이 너무 깁니다. : 최대 5자 이하";
				color = "red";
				isNameChecked=false;
			} else {
				meg = "사용가능한 길이입니다.";
				color = "blue";
				isNameChecked=true;
			}
			$("#NameChecker").text(meg).css("color",color);
			updateSubmitBtnState();
		});
	});
	</script>
</body>
</html>