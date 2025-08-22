/*const water = document.getElementById("water");
const waterStock = document.getElementById("water_Stock");
const timewatch = document.getElementById("timewatch");
const giver = document.getElementById("giver");

const nextdate=new Date();
let givewater = 3;
let havewater = parseInt(localStorage.getItem('havewater')) ||0;

const today=new Date().toISOString().slice(0,10);
const yesterday=localStorage.getItem("yesterday");

if(yesterday!=today){
	havewater=0;
	localStorage.setItem('havewater',havewater);
	localStorage.setItem('yesterday',today)
}
giver.textContent = `${havewater}/${givewater}`;
function updatetime() {
	const now = new Date();
	const end = new Date();
	end.setHours(24, 0, 0, 0);
	let timer = end - now;
	if (parseInt(waterStock.textContent) >= 3) {
		water.disabled = true;
	} else {
		water.disabled = false;
	}
	if (timer > 0) {
		const hh = String(Math.floor(timer / (1000 * 60 * 60))).padStart(2, '0');
		const mm = String(Math.floor((timer % (1000 * 60 * 60)) / (1000 * 60))).padStart(2, '0');
		const ss = String(Math.floor((timer % (1000 * 60) / 1000))).padStart(2, '0');

		timewatch.textContent = `${hh}:${mm}:${ss}`;
	}
}
setInterval(updatetime, 1000);
updatetime();
water.onclick = () => {
  const water2 = parseInt(waterStock.textContent);
  console.log(water2)
  if (water2 >= 3) {
	water.disabled = true;
  }
  if (givewater > havewater) {
	fetch('/give', {
	  method: 'POST',
	  headers: {
		'Content-Type': 'application/json',
		'Accept': 'application/json'
	  },
	  body: JSON.stringify({ water_Stock: water2 + 1, havewater: havewater + 1 })
	})
	.then(response => response.text())  // text형식 응답 처리
	.then(data => {
	  havewater++;
	  localStorage.setItem('havewater',havewater);
	  giver.textContent = `${havewater}/${givewater}`;
	  waterStock.textContent = water2 +1;
	  if (havewater >= givewater) {
		water.disabled = true;
	  }
	})
  } else {
	water.disabled = true;
  }
};
*/

// time.js (교체 권장)

(function() {
	const btn = document.getElementById('water'); // 물 받기 버튼(사이드)
	const stockEl = document.getElementById('water_Stock') || document.getElementById('waterStock'); // 하단 보유 물 표시
	const giverEl = document.getElementById('giver');      // 0/3 카운트
	const timerEl = document.getElementById('timewatch');  // 타이머

	// 필수 요소 없으면 바로 종료(다른 페이지/상태 보호)
	if (!btn || !stockEl || !giverEl || !timerEl) return;

	const DAILY_LIMIT = 3;

	// 날짜/횟수 로컬 저장
	function todayKey() { return new Date().toISOString().slice(0, 10); }
	function loadState() {
		try { return JSON.parse(localStorage.getItem('waterReceive')) || {}; } catch { return {}; }
	}
	function saveState(s) { localStorage.setItem('waterReceive', JSON.stringify(s)); }

	let state = loadState();
	if (state.date !== todayKey()) { state = { date: todayKey(), count: 0 }; saveState(state); }

	function fmt(ms) {
		const h = String(Math.floor(ms / 3600000)).padStart(2, '0');
		const m = String(Math.floor((ms % 3600000) / 60000)).padStart(2, '0');
		const s = String(Math.floor((ms % 60000) / 1000)).padStart(2, '0');
		return `${h}:${m}:${s}`;
	}

	function updateUI() {
		giverEl.textContent = `${state.count}/${DAILY_LIMIT}`;
		// 자정까지 카운트다운
		const now = new Date(), end = new Date();
		end.setHours(24, 0, 0, 0);
		timerEl.textContent = fmt(end - now);
		btn.disabled = state.count >= DAILY_LIMIT;
	}

	btn.onclick = async () => {
		if (state.count >= DAILY_LIMIT) return;
		const current = parseInt(stockEl.textContent) || 0;
		try {
			await fetch('/give', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
				body: JSON.stringify({ water_Stock: current + 1, havewater: state.count + 1 })
			});
			state.count += 1;
			saveState(state);
			stockEl.textContent = String(current + 1);
			updateUI();
		} catch (e) {
			console.error('[time] receive failed', e);
		}
	};

	setInterval(updateUI, 1000);
	updateUI();
})();
