/*const congrulationpopupbtn = document.getElementById("congrulationpopup");
const historybtn = document.getElementById("historyBtn");
const nobtn = document.getElementById("nobtn");
const morebtn = document.getElementById("morebtn");
const popupident = document.getElementById("popupident");
const identbtn=document.getElementById("identbtn");
historybtn.onclick = () => {
	location.href = "/grown";
	console.log();
}
nobtn.onclick = () => {
	congrulationpopupbtn.style.display = "none";
}

morebtn.onclick = () => {
	popupident.style.display="block";
	location.href = "/more";
}

identbtn.onclick = () => {
	let identTree = document.getElementById("identTree").value.trim();

		if (identTree.length < 1 || identTree.length > 5) {
			alert("식별자 이름은 1 ~ 5 자 사이로 입력해주세요.");
			return;
		}
	location.href = "/more";
}*/

document.addEventListener('DOMContentLoaded', () => {
	const $ = (id) => document.getElementById(id);

	const congrulationpopup = $('congrulationpopup');
	const historyBtn = $('historyBtn');
	const nobtn = $('nobtn');
	const morebtn = $('morebtn');
	const popupident = $('popupident');
	const identbtn = $('identbtn');   // 있을 수도, 없을 수도 있음

	if (historyBtn) {
		historyBtn.addEventListener('click', () => {
			location.href = '/grown';
		});
	}

	if (nobtn && congrulationpopup) {
		nobtn.addEventListener('click', () => {
			congrulationpopup.style.display = 'none';
		});
	}

	if (morebtn) {
		morebtn.addEventListener('click', () => {
			// popup에서 식별자 입력을 쓰려면 이 라인 사용:
			// if (popupident) popupident.style.display = 'block';
			// 그냥 바로 이동이면 아래 한 줄만:
			location.href = '/more';
		});
	}

	if (identbtn) {
		identbtn.addEventListener('click', () => {
			// 필요하면 레벨 읽어서 로직에 활용
			const levelEl = $('tree_Level') || $('treeLevel');
			const levelInt = levelEl ? parseInt(levelEl.textContent, 10) : 0;
			// TODO: levelInt 사용 로직이 있으면 여기에 추가

			location.href = '/more';
		});
	}
});
