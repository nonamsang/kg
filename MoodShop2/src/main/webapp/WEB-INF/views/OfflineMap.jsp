<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오프라인 매장 찾기</title>
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon/favicon-32x32.png" type="image/png">

<style>
body {
    font-family: 'Noto Sans KR', sans-serif;
    background-color: #f9f9f9;
    margin: 0;
    padding: 0;
}

.container {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    gap: 50px;
    margin-top: 40px;
    padding: 0 40px 80px 40px; /* footer 여백 */
    flex-wrap: nowrap; /* 한 줄 고정 */
}

.map-section {
    text-align: center;
}

#map {
    width: 700px;
    height: 500px;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.right-panel {
    display: flex;
    flex-direction: column;
    gap: 20px;
    width: 300px;
}

.info-panel {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    font-size: 14px;
    line-height: 1.6;
    color: #555;
    min-height: 200px;
    transition: opacity 0.3s;
}

.info-panel h3 {
    margin-bottom: 15px;
    font-size: 18px;
    color: #333;
}

.info-panel p {
    margin-bottom: 10px;
}

.store-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.store-card {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 12px;
    padding: 12px 15px;
    cursor: pointer;
    transition: transform 0.3s, box-shadow 0.3s;
    text-align: center;
}

.store-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    border-color: #ff7f50;
}

.store-card h4 {
    margin-bottom: 5px;
    font-size: 16px;
    color: #333;
}

.store-card p {
    font-size: 14px;
    color: #777;
}

.custom-infowindow {
    padding: 4px 8px;
    font-size: 12px;
    color: #333;
    white-space: nowrap;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: white;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.title-section {
    text-align: center;
    margin-top: 40px;
}

.title-section h2 {
    font-size: 32px;
    margin-bottom: 10px;
}

.title-section p {
    font-size: 16px;
    color: #666;
}
</style>
</head>

<body>

<jsp:include page="/WEB-INF/views/include/header.jsp" />

<div class="title-section">
    <h2>오프라인 매장 찾기</h2>
    <p>가까운 무드샵 매장을 확인하고 방문해보세요!</p>
</div>

<div class="container">

    <!-- 지도 영역 -->
    <div class="map-section">
        <div id="map"></div>
    </div>

    <!-- 오른쪽 매장 정보 + 버튼 -->
    <div class="right-panel">

        <!-- 매장 정보 패널 (개별 태그로 구분) -->
        <div class="info-panel" id="store-info">
            <h3 id="store-title">매장 정보</h3>
            <p id="store-description">매장을 선택하시면<br>오는 길과 영업시간이 표시됩니다.</p>
            <p id="store-hours"></p>
            <p id="store-directions"></p>
        </div>

        <!-- 버튼 목록 -->
        <div class="store-list">
            <div class="store-card" onclick="moveToStore(0)">
                <h4>무드샵 본점</h4>
                <p>감성구 무드로 101</p>
            </div>
            <div class="store-card" onclick="moveToStore(1)">
                <h4>MOODSHOP POPUP</h4>
                <p>무드타운 팝업 거리</p>
            </div>
            <div class="store-card" onclick="moveToStore(2)">
                <h4>MOODSHOP 1호점</h4>
                <p>무드빌 5번가</p>
            </div>
            <div class="store-card" onclick="moveToStore(3)">
                <h4>MOODSHOP 2호점</h4>
                <p>온더무드 스트리트</p>
            </div>
        </div>

    </div>
</div>

<script>
let map;
let markers = [];
let positions = [
    { title: '무드샵 본점', lat: 37.556680, lng: 126.923862, description: '감성구 무드로 101, 1층 무드샵 본점', hours: '운영시간: 10:00 ~ 20:00', directions: '무드역 1번 출구 도보 3분' },
    { title: 'MOODSHOP POPUP', lat: 37.557944, lng: 126.926396, description: '무드타운 팝업 거리, 시즌 한정 팝업 운영', hours: '운영시간: 11:00 ~ 19:00', directions: '무드광장 앞 팝업존' },
    { title: 'MOODSHOP 1호점', lat: 37.543097, lng: 127.056281, description: '무드빌 5번가 플래그십 스토어', hours: '운영시간: 10:30 ~ 21:00', directions: '무드빌역 3번 출구 5번가 입구' },
    { title: 'MOODSHOP 2호점', lat: 37.540145, lng: 127.056238, description: '온더무드 스트리트 문화 복합 매장', hours: '운영시간: 11:00 ~ 20:00', directions: '온더무드 스트리트 중앙 광장' }
];

function loadKakaoMap() {
    return new Promise((resolve, reject) => {
        if (window.kakao && window.kakao.maps) {
            resolve();
            return;
        }

        const script = document.createElement('script');
        script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?appkey=c2a49ae7428eee2fc07b7fb509ae845a&autoload=false';
        script.onload = () => { kakao.maps.load(resolve); };
        script.onerror = reject;
        document.head.appendChild(script);
    });
}

loadKakaoMap().then(() => {
    let mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(positions[0].lat, positions[0].lng),
            level: 3
        };

    map = new kakao.maps.Map(mapContainer, mapOption);

    for (let i = 0; i < positions.length; i++) {
        let markerPosition = new kakao.maps.LatLng(positions[i].lat, positions[i].lng);

        let marker = new kakao.maps.Marker({
            map: map,
            position: markerPosition
        });

        markers.push(marker);

        let infowindow = new kakao.maps.InfoWindow({
            content: '<div class="custom-infowindow">' + positions[i].title + '</div>'
        });

        kakao.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, marker);
            updateStoreInfo(i);
        });
    }
}).catch(() => {
    alert('카카오맵 로딩 실패');
});

function moveToStore(index) {
    let latlng = new kakao.maps.LatLng(positions[index].lat, positions[index].lng);
    map.setCenter(latlng);
    map.setLevel(3, { animate: true });
    updateStoreInfo(index);
}

function updateStoreInfo(index) {
    document.getElementById('store-title').textContent = positions[index].title;
    document.getElementById('store-description').textContent = positions[index].description;
    document.getElementById('store-hours').textContent = positions[index].hours;
    document.getElementById('store-directions').textContent = positions[index].directions;
}
</script>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
