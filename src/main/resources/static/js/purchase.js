const modal = document.getElementById("popupModal"); // 팝업창
const buyBtn = document.getElementById("buy"); // 비료 구매 버튼
const closeBtn = document.querySelector(".close"); // 닫기 버튼
const purchase = document.getElementById("purchase"); //팝업창에서 비료 구매 버튼
const biyroStock = document.getElementById("biyro_Stock"); //비료 재고 조회(1/2)
const biyroStockm = document.getElementById("biyro_Stockm"); //비료 재고 조회(2/2)
const point = document.getElementById("point"); //사용자가 보유한 포인트 버튼
const soo = document.getElementById("soo"); //현재 개수
const minus = document.getElementById("minus"); //개수 감소 버튼
const plus = document.getElementById("plus"); //개수 증가 버튼
const price = document.getElementById("price"); // 가격(구매가격)
const levelif = document.getElementById("tree_Level")
const levelint=parseInt(levelif.textContent)
console.log(levelint)

let sales = 500;
let gaesoo = 1;

buyBtn.onclick = () => {
	modal.style.display = "block";
};

if (closeBtn) {
	closeBtn.onclick = () => {
		modal.style.display = "none";
		if (levelint != 7) {
			window.location.reload();
		}
	};
}

/*window.onclick = (e) => {
	if (e.target === modal) modal.style.display = "none";
};*/

purchase.onclick = () => {
	const point2 = parseInt(point.textContent);
	const biyro2 = parseInt(biyroStock.textContent);
	const price2 = parseInt(price.textContent);
	console.log = gaesoo;
	console.log = price2;
	if (point2 >= price2) {
		fetch('/purchase', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			},
			body: JSON.stringify({ quantity: gaesoo, totalprice: price2 })
		})
			.then(response => response.text())
			.then(() => {
				biyroStock.textContent = biyro2 + gaesoo;
				biyroStockm.textContent = biyro2 + gaesoo;
				point.textContent = point2 - price2;
				alert("구매 성공하였습니다.");
				gaesoo = 1;
				price.textContent = sales;
				soo.textContent = gaesoo;
				/*	modal.style.display = "none";*/
			})

	} else {
		alert("포인트가 부족합니다.");
		gaesoo = 1;
		price.textContent = sales;
		soo.textContent = gaesoo;
	}
};

minus.onclick = () => {
	if (gaesoo > 1) {
		gaesoo--;
		soo.textContent = gaesoo;
		price.textContent = sales * gaesoo;
	} else {
		alert("최소 한 개는 선택하셔야 합니다.");
	}
};

plus.onclick = () => {
	gaesoo++;
	soo.textContent = gaesoo;
	price.textContent = sales * gaesoo;
};
