const water = document.getElementById("water");
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
