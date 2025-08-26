function payment() {
		// 선택된 라디오 버튼 가져오기
		const selectedWallet = document.querySelector('input[name="selectedWallet"]:checked');

		// 선택 안 한 경우 경고
		if (!selectedWallet) {
			alert('결제할 계좌를 선택해주세요.');
			return;
		}

		// 선택된 계좌 정보
		const accountNumber = selectedWallet.value;

		// 예시: 콘솔에 출력
		console.log('선택된 계좌번호:', accountNumber);

		// 이후 원하는 동작 수행 (예: 서버 요청, 페이지 이동 등)

		// 예시: fetch로 서버에 POST 요청
		/*
		fetch('/api/payment', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ accountNumber: accountNumber })
		})
		.then(response => response.json())
		.then(result => {
			alert('결제가 완료되었습니다!');
			console.log(result);
		})
		.catch(error => {
			console.error('결제 중 오류 발생:', error);
			alert('결제에 실패했습니다.');
		});
		*/

		// 또는 페이지 이동 예시:
		// window.location.href = `/payment/confirm?account=${accountNumber}`;
	}