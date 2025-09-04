// 1. SDK 초기화 (카카오 REST API 앱 키 사용)
  window.Kakao.init("c2a49ae7428eee2fc07b7fb509ae845a"); 
  console.log("Kakao SDK 초기화 상태:", window.Kakao.isInitialized());

  // 2. 카카오 로그인 함수
  function kakaoLogin() {
      window.Kakao.Auth.login({
          scope: 'profile_nickname, profile_image, account_email',
          success: function(authObj) {
              console.log("✅ 로그인 성공:", authObj);


              // 3. 사용자 정보 요청
              window.Kakao.API.request({
                  url: '/v2/user/me',
                  success: res => {
                      const kakao_account = res.kakao_account;
                      fetch('/checkemail?email=' + kakao_account.email, {
                          method: 'POST' // GET 방식
                      })
                          .then(res => res.text())
                          .then(text => {
                              const intValue = parseInt(text, 10);
                              if (intValue == 1) {

                                  window.location.href = "/main";
                              }
                              else {
								const kakaoAccessToken = window.Kakao.Auth.getAccessToken();
								alert("가입되지 않은 이메일, 회원가입 페이지로 이동합니다.");

								fetch('/auth/kakao/session', {
								  method: 'POST',
								  headers: {
								    'Content-Type': 'application/json',
								    // CSRF를 쓰면 메타에서 읽어 같이 전송
								    // 'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content
								  },
								  body: JSON.stringify({ accessToken: kakaoAccessToken })
								})
								  .then(response => {
								    if (!response.ok) throw new Error('세션 설정 실패');
								    window.location.assign('/join');
								  })
								  .catch(error => console.error(error));
                              }
                          });

                  }
              })
                  .catch(err => {
                      console.error('서버 요청 오류:', err);
                  });
          }
      });
  }
