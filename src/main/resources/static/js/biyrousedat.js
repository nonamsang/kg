/*const biyrobtn = document.getElementById("biyrobtn");
const biyroStocka = document.getElementById("biyro_Stock");
const biyroTimer = document.getElementById("biyrotimer");
const biyroani=document.getElementById("soil-container");
const waterbtn00=document.getElementById("waterbtn");

function time(a){
	return a < 10 ? '0' + a : a;
}

function timereload(){
	const biyroUsedAtaValue = document.getElementById("biyro_Used_At").value;
	if(!biyroUsedAtaValue) {
		biyroTimer.style.display="none";
		return;
	}
	const biyrostart = new Date(biyroUsedAtaValue);
	const biyroend = new Date(biyrostart);
	biyroend.setDate(biyroend.getDate() + 3);
	
	const now = new Date();
	const between = biyroend - now;

	if(between <= 0){
		biyroTimer.style.display="none";
		return;
	}

	const totalSeconds = Math.floor(between / 1000);
	const hours = Math.floor(totalSeconds / 3600);
	const minutes = Math.floor((totalSeconds % 3600) / 60);
	const seconds = totalSeconds % 60;

	biyroTimer.textContent = `${time(hours)}:${time(minutes)}:${time(seconds)}`;
}

setInterval(timereload, 1000);
timereload();

biyrobtn.onclick = () => {
	let stocka = parseInt(biyroStocka.textContent);

	if(stocka === 0){
		alert("비료 재고가 없습니다.");
		return;
	}

	fetch('/biyro', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json'
		},
		body: JSON.stringify({biyro_Stock: stocka})
	})
	.then(response => response.text())
	.then(data => {
		stocka = stocka - 1;
		biyroStocka.textContent = stocka;
		biyroani.style.display="block";
		waterbtn00.disabled=true;
		biyrobtn.disabled=true;
		setTimeout(() => {
			biyroani.style.display = "none";
			waterbtn00.disabled=false;
			biyrobtn.disabled=false;
		}, 2000);
		// 현재시간 포맷팅 (yyyy-MM-dd HH:mm:ss)
		const now = new Date();
		const formattedNow = `${now.getFullYear()}-${time(now.getMonth()+1)}-${time(now.getDate())} ${time(now.getHours())}:${time(now.getMinutes())}:${time(now.getSeconds())}`;

		// 숨겨진 input에 업데이트
		document.getElementById("biyro_Used_At").value = formattedNow;
		biyroTimer.style.display="block";
		// 타이머 리셋
		timereload();

	});
}
*/

document.addEventListener("DOMContentLoaded", () => {
	const biyrobtn = document.getElementById("biyrobtn");
	const biyroStocka = document.getElementById("biyroStock"); 
	const biyroTimer = document.getElementById("biyrotimer");
	const biyroani = document.getElementById("soil-container");
	const waterbtn00 = document.getElementById("waterbtn");
	const biyroUsedAtInput = document.getElementById("biyro_Used_At");

	// 요소가 없으면 실행 안 함
	if (!biyrobtn || !biyroStocka || !biyroTimer || !biyroUsedAtInput) {
		console.log("비료 관련 요소 없음 → biyrousedat.js 실행 안 함");
		return;
	}

	function time(a) {
		return a < 10 ? '0' + a : a;
	}

	function timereload() {
		const biyroUsedAtaValue = biyroUsedAtInput.value;
		if (!biyroUsedAtaValue) {
			biyroTimer.style.display = "none";
			return;
		}
		const biyrostart = new Date(biyroUsedAtaValue);
		const biyroend = new Date(biyrostart);
		biyroend.setDate(biyroend.getDate() + 3);

		const now = new Date();
		const between = biyroend - now;

		if (between <= 0) {
			biyroTimer.style.display = "none";
			return;
		}

		const totalSeconds = Math.floor(between / 1000);
		const hours = Math.floor(totalSeconds / 3600);
		const minutes = Math.floor((totalSeconds % 3600) / 60);
		const seconds = totalSeconds % 60;

		biyroTimer.textContent =
			`${time(hours)}:${time(minutes)}:${time(seconds)}`;
	}

	setInterval(timereload, 1000);
	timereload();

	biyrobtn.onclick = () => {
		let stocka = parseInt(biyroStocka.textContent);

		if (stocka === 0) {
			alert("비료 재고가 없습니다.");
			return;
		}

		fetch('/biyro', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			},
			body: JSON.stringify({ biyro_Stock: stocka })
		})
			.then(response => response.text())
			.then(() => {
				stocka = stocka - 1;
				biyroStocka.textContent = stocka;

				// 애니메이션
				biyroani.style.display = "block";
				waterbtn00.disabled = true;
				biyrobtn.disabled = true;

				setTimeout(() => {
					biyroani.style.display = "none";
					waterbtn00.disabled = false;
					biyrobtn.disabled = false;
				}, 2000);

				// 현재시간 포맷팅 (yyyy-MM-dd HH:mm:ss)
				const now = new Date();
				const formattedNow =
					`${now.getFullYear()}-${time(now.getMonth() + 1)}-${time(now.getDate())} `
					+ `${time(now.getHours())}:${time(now.getMinutes())}:${time(now.getSeconds())}`;

				// 숨겨진 input에 업데이트
				biyroUsedAtInput.value = formattedNow;
				biyroTimer.style.display = "block";

				// 타이머 리셋
				timereload();
			});
	};
});
