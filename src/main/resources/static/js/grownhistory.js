const congrulationpopupbtn=document.getElementById("congrulationpopup");
const historybtn=document.getElementById("historyBtn");
const nobtn=document.getElementById("nobtn");
const morebtn=document.getElementById("morebtn");
const popupident=document.getElementById("popupident");
/*const identbtn=document.getElementById("identbtn");*/
historybtn.onclick=()=>{
	location.href="/grown";
	console.log();
}
nobtn.onclick=()=>{
	congrulationpopupbtn.style.display="none";
}

morebtn.onclick=()=>{
	/*popupident.style.display="block";*/
	location.href="/more";
}

identbtn.onclick=()=>{
	/*let identTree = document.getElementById("identTree").value.trim();

		if (identTree.length < 1 || identTree.length > 5) {
			alert("식별자 이름은 1 ~ 5 자 사이로 입력해주세요.");
			return;
		}*/
		location.href="/more";
}