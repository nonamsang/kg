<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MOOD SHOP | 관리자 페이지</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />
<style>
html, body {
    height: 100%;
    margin: 0;
    display: flex;
    flex-direction: column;
}

body {
    min-height: 100vh;
}

.admin_title {
    font-weight: bold;
    margin: 30px 0 10px;
    text-align: center;
}

.adminline {
    width: 85%;
    height: 3px;
    background-color: gray;
    margin-top: 0;
    margin: 30px auto; /* 여백 축소 */
}

.product_wrapper {
    display: flex;
    flex-wrap: wrap; /* 줄바꿈 허용 */
    gap: 20px 0px; /* 컨테이너 간격 축소 */
    justify-content: center; /* 전체 그룹 가운데 정렬 */
    margin-top: 30px; /* 위로 당김 */
    margin-bottom: 60px; /* 여백 축소 */
    flex: 1;
}

.product_container {
    width: 22%; /* 한 행에 3개 배치 */
    display: flex;
    justify-content: center; /* 내부 컨텐츠 가운데 정렬 */
}

.product_manage_box {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 20px;
    width: 350px;  
    background-color: #fff;
    height: 250px; 
}

.product_manage_header {
    background-color: #f4f6f8;
    padding: 10px 15px;
    font-weight: bold;
    font-size: 18px;
    border-bottom: 1px solid #ddd;
}

.product_manage_body {
    margin-top: 15px;
    display: flex;
    flex-direction: column;
    gap: 5px;
}

.product_manage_body a {
    text-decoration: none;
    color: #333;
    font-size: 17px;
    cursor: pointer;
    padding-left: 15px;
}

.product_manage_body a:hover {
    text-decoration: underline;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header2.jsp" />

<h2 class="admin_title">관리자 페이지</h2>
<div class="adminline"></div>

<div class="product_wrapper">

<div class="product_container">
    <div class="product_manage_box">
        <div class="product_manage_header">
            <i class="fa-solid fa-shirt"></i> 상품관리
        </div>
        <div class="product_manage_body">
            <a href="MProductList.do">상품편집</a><br>
            <a href="MProductDetail.do">상품옵션 편집</a><br>
            <a href="MProductInsert.do">상품등록</a><br>
        </div>
    </div>
</div>

<div class="product_container">
    <div class="product_manage_box">
        <div class="product_manage_header">
            <i class="fa-solid fa-user"></i> 고객관리
        </div>
        <div class="product_manage_body">
            <a href="UserListPage.do">회원관리</a><br>
            <a href="wishRanking.do">찜 순위 확인</a>
        </div>
    </div>
</div>

<div class="product_container">
    <div class="product_manage_box">
        <div class="product_manage_header">
            <i class="fa-solid fa-gift"></i> 이벤트 관리
        </div>
        <div class="product_manage_body">
            <a href="admin_event_insert_page.do">이벤트 등록</a><br>
            <a href="admin_event_list_page.do">이벤트 수정/삭제</a>
        </div>
    </div>
</div>

<div class="product_container">
    <div class="product_manage_box">
        <div class="product_manage_header">
            <i class="fa-solid fa-bullhorn"></i> 공지사항 관리
        </div>
        <div class="product_manage_body">
            <a href="admin_notice_insert_page.do">공지사항 등록</a><br>
            <a href="admin_notice_list_page.do">공지사항 삭제</a>
        </div>
    </div>
</div>

</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
