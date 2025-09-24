<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
</head>
<body>
	<h1>카카오페이 테스트 결제</h1>
	<button id="kyulje">결제하기</button>
	
	<script>
	// 객체.메소드(kaja();)
		// dom에서 "접근" // dom에서 "동적메소드"        //람다 기법
	document.getElementById('kyulje').addEventListener('click',()=>{
		const IMP = window.IMP;
		IMP.init('imp66570674');        //내 가맹점 코드
		
		const requestData = {
			pg: 'kakaopay',
			pay_method:'card',
			merchant_uid:`order_${new Date().getTime()}`,
			name:'청바지',
			amount:100,
			buyer_email:'ondal@ondal.com',
			buyer_addr:'서울특별시 종로구',
			buyer_postcode:'12345',
			m_redirect_url: 'https://moya.com/naya'
		};
	IMP.request_pay(requestData,(response)=>{
		if(response.success){
		//결제성공
			alert(`테스트 결제 완료 \n결제 금액: ${response.paid_amount}원`);
			console.log('결제 성공',response);
			} else{
		//결제실패
			alert(`테스트 결제 실패\nerror msg: ${response.error_msg}`);
			console.error('결제 실패',response);
			}
		});
	});

	</script>
</body>
</html>
