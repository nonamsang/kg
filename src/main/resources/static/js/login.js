// 1. SDK 초기화 (카카오 REST API 앱 키 사용)
  window.Kakao.init("ac057e840adb4dee14f59435952c2704"); 
  console.log("Kakao SDK 초기화 상태:", window.Kakao.isInitialized());

  // 2. 카카오 로그인 함수
  function kakaoLogin() {
    window.Kakao.Auth.login({
      scope: 'account_email, name, birthday, birthyear, phone_number',
      success: function(authObj) {
        console.log("✅ 로그인 성공:", authObj);

        // 3. 사용자 정보 요청
        window.Kakao.API.request({
          url: '/v2/user/me',
          success: res => {
			const kakao_account = res.kakao_account;
			fetch('/checkemail?email=' + kakao_account.email, {
			      method: 'POST'  // GET 방식
			    })
				.then(res => res.text())
				.then(text => {
				  const intValue = parseInt(text, 10);
				  if(intValue == 1){
					
					console.log('서버에서 받은 숫자:', intValue);
					window.location.href = "/main"
				  }
				  else {
					console.log('서버에서 받은 숫자:', intValue);
					alert("가입되지 않은 이메일, 회원가입으로 이동합니다.")
					window.location.href = "/join"
				  }
				  
				})
			    .catch(err => {
			      console.error('서버 요청 오류:', err);
			    });
			}
        });
      }
    });
  }