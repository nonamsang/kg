document.addEventListener("DOMContentLoaded", () => {
	const modal = document.getElementById("popupModal"); // 팝업창
	const buyBtn = document.getElementById("buy"); // 비료 구매 버튼
	const closeBtn = document.querySelector(".close"); // 닫기 버튼
	const purchase = document.getElementById("purchase"); // 모달 구매 버튼

	// 여러 위치에 있는 비료 재고
	const biyroStock = document.getElementById("biyroStock");   // 버튼 옆 표시
	const biyroStockm = document.getElementById("biyroStockm"); // 모달 안 표시

	const point = document.getElementById("point"); // 보유 포인트
	const soo = document.getElementById("soo"); // 수량
	const minus = document.getElementById("minus");
	const plus = document.getElementById("plus");
	const price = document.getElementById("price");
	const levelif = document.getElementById("tree_Level");

	// 모달이 없으면 실행 안 함 (iftree=false)
	if (!modal || !purchase || !price || !point || !soo) {
		console.log("구매 모달 요소 없음 → purchase.js 실행 안 함");
		return;
	}

	const levelint = levelif ? parseInt(levelif.textContent) : 0;
	console.log("현재 레벨:", levelint);

	let sales = 500;
	let gaesoo = 1;

	// 구매 모달 열기
	if (buyBtn) {
		buyBtn.onclick = () => {
			modal.style.display = "block";
		};
	}

	// 닫기 버튼
	if (closeBtn) {
		closeBtn.onclick = () => {
			modal.style.display = "none";
			if (levelint !== 7) {
				window.location.reload();
			}
		};
	}

	// 구매 버튼 동작
	purchase.onclick = () => {
		const point2 = point ? parseInt(point.textContent) : 0;
		const biyro2 = biyroStock ? parseInt(biyroStock.textContent) : 0;
		const biyro2m = biyroStockm ? parseInt(biyroStockm.textContent) : 0;
		const price2 = price ? parseInt(price.textContent) : 0;

		console.log("구매 수량:", gaesoo, "가격:", price2);

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
					// 화면 갱신 (있을 때만)
					if (biyroStock) biyroStock.textContent = biyro2 + gaesoo;
					if (biyroStockm) biyroStockm.textContent = biyro2m + gaesoo;
					if (point) point.textContent = point2 - price2;

					alert("구매 성공하였습니다.");
					gaesoo = 1;
					if (price) price.textContent = sales;
					if (soo) soo.textContent = gaesoo;
				});
		} else {
			alert("포인트가 부족합니다.");
			gaesoo = 1;
			if (price) price.textContent = sales;
			if (soo) soo.textContent = gaesoo;
		}
	};

	// 수량 감소
	if (minus) {
		minus.onclick = () => {
			if (gaesoo > 1) {
				gaesoo--;
				soo.textContent = gaesoo;
				price.textContent = sales * gaesoo;
			} else {
				alert("최소 한 개는 선택하셔야 합니다.");
			}
		};
	}

	// 수량 증가
	if (plus) {
		plus.onclick = () => {
			gaesoo++;
			soo.textContent = gaesoo;
			price.textContent = sales * gaesoo;
		};
	}
});
