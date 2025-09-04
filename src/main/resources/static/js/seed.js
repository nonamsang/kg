/*const seedmodal = document.getElementById("popupseed");
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
*/



document.addEventListener('DOMContentLoaded', () => {
	const seedmodal = document.getElementById("popupseed");
	const seedBtn = document.getElementById("seed");          // 씨앗 심기 버튼 (없을 수 있음)
	const closebtn2 = document.querySelector("#popupseed .close");
	const treenamebtn = document.getElementById("treenaming");     // 이름 확정 버튼 (없을 수 있음)

	if (seedBtn && seedmodal) {
		seedBtn.onclick = () => {
			seedmodal.style.display = "block";
		};
	}

	if (closebtn2 && seedmodal) {
		closebtn2.onclick = () => {
			seedmodal.style.display = "none";
		};
	}

	if (treenamebtn) {
		treenamebtn.onclick = () => {
			let treeName = (document.getElementById("treeName")?.value || "").trim();
			if (treeName.length < 1 || treeName.length > 5) {
				alert("나무 이름은 1 ~ 5 자 사이로 입력해주세요.");
				return;
			}
			if (!treeName.includes("나무")) treeName += "나무";

			fetch('/plant', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
				body: JSON.stringify({ treeName })
			})
				.then(r => r.text())
				.then(() => {
					alert("등록 성공했습니다.");
					if (seedmodal) seedmodal.style.display = "none";
					location.reload();
				})
				.catch(err => {
					console.error(err);
					alert("등록 중 오류가 발생했습니다.");
				});
		};
	}
});
