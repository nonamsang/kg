const seedmodal = document.getElementById("popupseed");
const seed = document.getElementById("seed"); // 씨앗 심기 버튼
const wantseed = document.getElementById("wantseed"); // 씨앗을 심어주세요 화면
const closebtn2 = document.querySelector("#popupseed .close");
const treenamebtn = document.getElementById("treenaming");

seed.onclick = () => {
	seedmodal.style.display = "block";
	console.log(seed);
};

if (closebtn2) {
	closebtn2.onclick = () => {
		seedmodal.style.display = "none";
	};
}

treenamebtn.onclick = () => {
	let tree_Name = document.getElementById("tree_Name").value.trim();

	if (tree_Name.length < 1 || tree_Name.length > 5) {
		alert("나무 이름은 1 ~ 5 자 사이로 입력해주세요.");
		return;
	}
	if(!tree_Name.includes("나무")){
		tree_Name=tree_Name+"나무";
	}
		fetch('/plant', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			},
			body: JSON.stringify({ tree_Name: tree_Name })
		})
		.then(response => response.text())  // text형식 응답 처리
		.then(data => {
			alert("등록 성공했습니다.",data);
			seedmodal.style.display = "none";
			location.reload();
			// 필요시 화면 갱신 등 추가 작업
		})
		.catch(error => {
			console.error(error);
			alert("등록 중 오류가 발생했습니다.");
		});
	
};
