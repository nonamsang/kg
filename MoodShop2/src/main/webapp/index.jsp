<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>[MOODSHOP] 여름 이벤트 미리보기</title>
  <link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <!-- Google Fonts: Pacifico : 외부 저작권 무료 텍스트 폰트 적용 -->
  <link href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap" rel="stylesheet">
 
  <!-- tsParticles core : 외부 저작권 무료 js 적용 -->
  <script src="https://cdn.jsdelivr.net/npm/tsparticles@2/tsparticles.bundle.min.js"></script>

  <!-- 레이어 애니메이션 + css 스타일 -->
  <style>
    body { margin: 0; overflow: hidden; background: #000000; }

    /* 텍스트 레이어 (몽환적 + 그라데이션 + 블러 애니메이션용) */
    #present-text {
      position: fixed; top: 35%; left: 50%; transform: translate(-50%, -50%);
      font-size: 48px; opacity: 0; filter: blur(8px);
      z-index: 2; font-family: 'Pacifico', cursive; text-align: center;

      /* 텍스트 은하수 그라데이션 */
      background: linear-gradient(270deg, #a18cd1, #fbc2eb, #8fd3f4, #84fab0, #a6c1ee);
      background-size: 800% 800%;
      -webkit-background-clip: text; /* 그라데이션을 텍스트에 입힘 */
      -webkit-text-fill-color: transparent; /* 텍스트를 투명 처리 */
      animation: galaxyMove 5s ease infinite; /* 그라데이션 애니메이션 */
    }

    /* 텍스트 내 그라데이션 움직임 (은하수 느낌) */
    @keyframes galaxyMove {
      0% { background-position: 0% 50%; }
      50% { background-position: 100% 50%; }
      100% { background-position: 0% 50%; }
    }

    /* 반짝임 애니메이션 (별'점' 효과) */
    @keyframes twinkle {
      0%, 100% { opacity: 0; }
      50% { opacity: 1; }
    }

    /* 핑크색 레이어 (페이지 이동 전 부드러운 전환용) */
    #white-layer {
      position: fixed; top: 0; left: 0; width: 100%; height: 100%;
      background: #FFE4E1; opacity: 0; z-index: 3;
    }
  </style>
</head>
<body>
 <!-- 애니메이션 효과들은 레이어 개념으로 구분되어 있으며 레이어 순서는 div 태그가 작성된 순서 + z-index 기준 입니다.  -->
 <!-- div 순서 보다 z-index값이 우선 적용 되지만 z-index가 미지정인 div 태그는 순서를 기반으로 순차적으로 실행됩니다. -->

  <!-- 별자리 레이어 -->
  <div id="stars" style="position:fixed; top:0; left:0; width:100%; height:100%; z-index:0;"></div>

  <!-- 반짝임 레이어 -->
  <div id="twinkle-layer"></div> 						<!-- z-index 미지정 : auto -->

  <!-- 텍스트 레이어 -->
  <div id="present-text">KOKONE</div> <!-- z-index : 2 -->

  <!-- 핑크색 레이어 -->
  <div id="white-layer"></div> 							<!-- z-index : 3 -->


  <!-- 레이어 애니메이션 + css 스타일의 기능 -->
  <script>
    window.onload = function() {

      // ⭐ 별자리 효과 (tsParticles 커스텀 : floating dots + links(연결 선))
      tsParticles.load("stars", {
        background: { color: "#000000" },
        fpsLimit: 60,
        particles: {
          number: { value: 120 }, // 별 개수
          color: { value: "#ffffff" }, // 별 색상
          shape: { type: "circle" },
          opacity: { // 별 무작위 반짝임
            value: { min: 0.3, max: 0.9 },
            animation: { enable: true, speed: 0.5, minimumValue: 0.3, sync: false }
          },
          size: { value: { min: 1, max: 2 }, random: true }, // 별 크기
          move: { enable: false }, // 별은 고정 (움직이지 않음)
          links: { enable: true, distance: 150, color: "#ffffff", opacity: 0.4, width: 1 } // 별 연결선 (별자리)
        },
        detectRetina: true,
        interactivity: { events: { onHover: { enable: false }, onClick: { enable: false } } }
      });

      // 반짝임 레이어 생성
      const twinkleLayer = document.getElementById('twinkle-layer');

      for (let i = 0; i < 150; i++) {
        const twinkle = document.createElement('div');
        twinkle.className = 'twinkle';
        twinkle.style.top = Math.random() * window.innerHeight + 'px';
        twinkle.style.left = Math.random() * window.innerWidth + 'px';

        // ⭐ 반짝임 크기를 4~6px 랜덤으로 지정 (시각 적으로 더 잘 보이게 하기 위함)
        const size = 4 + Math.random() * 2;
        twinkle.style.width = size + 'px';
        twinkle.style.height = size + 'px';

        twinkle.style.background = 'white';
        twinkle.style.position = 'absolute';
        twinkle.style.borderRadius = '50%';
        twinkle.style.opacity = 0;
        twinkle.style.animation = 'twinkle 1s infinite';
        twinkle.style.animationDuration = (0.5 + Math.random() * 1) + 's';
        twinkle.style.animationDelay = Math.random() * 1 + 's';

        twinkleLayer.appendChild(twinkle);
      }

      // 텍스트 애니메이션 (블러 → 선명해지는 속도)
      setTimeout(() => {
        $('#present-text').animate({ opacity: 1 }, {
          duration: 1000, // 텍스트 나타나는 속도 (1000ms = 1초)
          step: function(now) {
            $(this).css('filter', 'blur(' + (8 - now * 8) + 'px)');
          }
        });
      }, 500); // 0.5초 후 애니메이션 시작

      // 핑크색 레이어 fade-in (페이지 전환 자연스럽게)
      setTimeout(() => { $('#white-layer').fadeTo(2500, 1); }, 2000); // 2초 후에 2.5초 동안 fade-in

      // 페이지 이동
      setTimeout(() => { window.location.href = "eventMain.do?section=limitevent"; }, 4500); // 4.5초 후 페이지 이동
    };
  </script>
</body>
</html>
