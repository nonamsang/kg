const waterbtn = document.getElementById("waterbtn");
const waterStock3 = document.getElementById("water_Stock")
const waterjson = document.getElementById("waterjson");
const level = document.getElementById("tree_Level");
const biyrobtn3 = document.getElementById("biyrobtn");
const congrulationpopup = document.getElementById("congrulationpopup");
const closeBtnc = document.querySelector("#congrulationpopup .close");
const levelvalue = parseInt(level.textContent);
const waterCountc=document.getElementById("water_Count").value;
if (levelvalue == 7) {
	waterbtn.disabled = true;
	biyrobtn3.disabled = true;
	congrulationpopup.style.display = "block";
}

if (closeBtnc) {
	closeBtnc.onclick = () => {
		congrulationpopup.style.display = "none";
	};
}
const bamboobar = document.getElementById("bambooBar");
const level2 = parseInt(level.textContent);
const needexp = [0, 5, 20, 50, 80, 130, 200];
const currentexp = waterCountc;
const levelnow = needexp[level2 - 1];  // 현재 레벨 누적 경험치
const levelnext = needexp[level2] || needexp[needexp.length - 1];  // 다음 레벨 누적 경험치
const needexp2 = currentexp - levelnow;  // 현재 레벨 내 경험치
const needexp3 = levelnext - levelnow;   // 현재 레벨 구간 경험치 총량
const maxlevel = needexp.length - 1;

bamboobar.innerHTML = '<div class="exp-text" id="expText"></div>'; // 초기화 및 텍스트 div 추가

for (let i = 0; i < maxlevel; i++) {
  const seg = document.createElement("div");
  seg.classList.add("segment");
  seg.style.flex = "1";

  if (i < level2 - 1) {
    // 이전 레벨 세그먼트는 모두 채움
    seg.classList.add("filled");
  } else if (i === level2 - 1) {
    // 현재 레벨 세그먼트는 경험치 비율만큼 부분 채움
    const fillPercent = needexp2 / needexp3;
    seg.style.background = `linear-gradient(to right, #2e7d32 ${fillPercent * 100}%, #ffffff ${fillPercent * 100}%)`;
  } else {
    // 아직 안 도달한 레벨 세그먼트는 빈 상태
    seg.classList.add("empty");
  }
  bamboobar.appendChild(seg);
}

document.getElementById("expText").textContent = `${currentexp} / ${needexp[6]}`;


waterbtn.onclick = () => {
	const waterstock = parseInt(waterStock3.textContent);
	waterbtn.disabled = true;
	fetch('/water')
		.then(response => response.text())
		.then(data => {
			if (waterstock == 0) {
				alert("보유물이 없습니다.")
			}
			else {
				fetch('/water2', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json'
					},
					body: JSON.stringify({ water_Stock: waterstock - 1 })
				}).then(response => response.text())
					.then(text => {
						const data = JSON.parse(text);
						//console.log(data.treeLevel);
						//console.log(data.exp);

						console.log("애니메이션 쇼")
						waterjson.style.display = "block";
						setTimeout(() => {
							waterjson.style.display = "none";
							window.location.reload();
							waterbtn.disabled = false;
						}, 2000);
						/*물주기 이벤트가 증가되면서*/
						waterStock3.textContent = waterstock - 1;
					})
			}
		})

}