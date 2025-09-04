const updatepopupy = document.getElementById("updatepopupy");
const pibclosey = document.querySelector("#updatepopupy .close");
const upbtn2 = document.getElementById("upbtn2");
const cupdbtnrey = document.getElementById("cupdbtnrey");

// 수정 버튼 클릭 이벤트
upbtn2.addEventListener("click", async () => {
    // 수정할 요소 선택
    const pibcardy = document.querySelector(".community-post"); // 단일 요소만
    const pibcidy = pibcardy.querySelector(".communityId3").value;
    const pibuidy = pibcardy.querySelector(".userId3").value;
    const pibtypey = document.getElementById("type3").textContent;
    const pibtitley = document.getElementById("title3").textContent;
    const pibcontenty = document.getElementById("content3").textContent;
    const pibimagey = document.getElementById("photo3")?.value || "";

    // 팝업 열기
    updatepopupy.style.display = "flex";

    // DOM 요소에 값 넣기
    document.getElementById("uuserIdy").textContent = pibuidy;
    document.getElementById("utypey").value = pibtypey;
    document.getElementById("utitley").value = pibtitley;
    document.getElementById("ucontenty").value = pibcontenty;
    document.getElementById("uphotoHidden").value = pibimagey;
    const imgPreview = document.getElementById("uphotopathy");
    imgPreview.src = pibimagey;
    imgPreview.style.display = pibimagey ? "block" : "none";
});

// 초기화 버튼
cupdbtnrey.onclick = () => {
    document.getElementById("utypey").value = "";
    document.getElementById("utitley").value = "";
    document.getElementById("ucontenty").value = "";
    document.getElementById("uphotoHidden").value = "";
    document.getElementById("uphotopathy").style.display = "none";
    document.getElementById("uphotoy").value = "";
};

// 팝업 닫기
pibclosey.onclick = () => {
    updatepopupy.style.display = "none";
};

// 글 실제 수정 요청
document.getElementById("upbtn2").addEventListener("click", async () => {
    const typeVal = document.getElementById("utypey").value;
    const titleVal = document.getElementById("utitley").value;
    const contentVal = document.getElementById("ucontenty").value;
    const fileInput = document.getElementById("uphotoy");
    let photoUrl = document.getElementById("uphotoHidden").value;

    if (!typeVal) { alert("타입 선택해주세요"); return; }
    if (!titleVal || titleVal.length < 1 || titleVal.length > 20) { alert("제목 1~20자 입력"); return; }
    if (!contentVal || contentVal.length <= 1) { alert("글 2자 이상 입력"); return; }

    // 파일 업로드
    if (fileInput.files.length > 0) {
        const file = fileInput.files[0];
        const CLOUD_NAME = "dkaeihkco";
        const UPLOAD_PRESET = "community";
        const formDataImg = new FormData();
        formDataImg.append("upload_preset", UPLOAD_PRESET);
        formDataImg.append("file", file);

        const res = await fetch(`https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`, { method: "POST", body: formDataImg });
        if (!res.ok) {
            alert("이미지 업로드 실패");
            return;
        }
        const data = await res.json();
        photoUrl = data.secure_url;
    }

    const formData = new FormData();
    formData.append("id", document.querySelector(".communityId3").value);
    formData.append("userId", document.querySelector(".userId3").value);
    formData.append("type", typeVal);
    formData.append("title", titleVal);
    formData.append("content", contentVal);
    formData.append("photoPath", photoUrl);

    fetch('/community/update', { method: 'POST', body: formData })
        .then(res => res.text())
        .then(data => {
            if (data === "success") {
                alert("수정 성공");
                updatepopupy.style.display = "none";
                location.reload();
            } else {
                alert("수정 실패");
            }
        })
        .catch(err => {
            console.error(err);
            alert("수정 중 오류 발생");
        });
});

/*document.querySelectorAll(".debtn1").forEach(btn => {
    btn.addEventListener("click", function(e) {
        if (!confirm("삭제하시겠습니까?")) return;

        const pibcardd = e.target.closest(".post-card");
        const pibcidd = pibcardd.querySelector(".communityId").value;
        const pibuidd = pibcardd.querySelector(".userId").value;

        const formDatad = new FormData();
        formDatad.append("id", pibcidd);
        formDatad.append("userId", pibuidd);

        fetch('/community/delete', {
            method: 'POST',
            body: formDatad
        })
        .then(response => response.text())
        .then(data => {
            if (data === 'success') {
                console.log(data)
                alert("삭제성공");
                location.reload();
            }
        })
        .catch(error => {
            console.error("삭제 오류:", error);
            alert("삭제 실패");
        });
    });
});
*/