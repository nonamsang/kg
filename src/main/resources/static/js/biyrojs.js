const container = document.getElementById('soil-container');
const soils = container.querySelectorAll('.soil');

soils.forEach((soil, /*i*/) => {

  // 고정 크기 (필요한 크기로 변경)
  let size = 100; // PNG 크기에 맞게 조절
  soil.style.width = size + 'px';
  soil.style.height = size + 'px';
    soil.style.left = "50%";
    soil.style.top = "50%";
  anime({
    targets: soil,
    translateY: 400,  // 아래로 500px 이동
    duration: 2200,   // 애니메이션 시간 2.5초
    easing: 'linear', // 일정한 속도
    /*delay: i * 300,   // 각 요소별로 지연 시간 추가 (옵션)*/
    loop: true,      // 반복 
    complete: function() {
      // 애니메이션 끝난 후 위치 초기화
      soil.style.transform = 'translate(0, 0)';
  
    }
  });
});
