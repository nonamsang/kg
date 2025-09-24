<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>공지사항 등록 페이지</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function autoSetNoticeId() {
            var startDate = document.getElementById('noticeStartDate').value;
            if (startDate) {
                var dateParts = startDate.split('-');
                var noticeId = 'N' + dateParts[0].substring(2) + dateParts[1] + dateParts[2];
                document.getElementById('noticeId').value = noticeId;
            }
        }
    </script>

    <style>
        body {
            padding: 30px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 700px;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .form-label {
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header2.jsp" />

<div class="container">
    <h2 class="mb-4 text-center">공지사항 등록</h2>

    <form id="noticeForm" action="insertNotice.do" method="post" enctype="multipart/form-data">

        <div class="mb-3">
            <label class="form-label">공지 ID</label>
            <input type="text" id="noticeId" name="notice_id" class="form-control" readonly style="background-color: #e9ecef;">
        </div>

        <div class="mb-3">
            <label class="form-label">공지명</label>
            <input type="text" name="notice_name" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">공지 제목</label>
            <input type="text" name="notice_title" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">공지 설명</label>
            <textarea name="notice_description" class="form-control" rows="5" required></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">시작일</label>
            <input type="date" id="noticeStartDate" name="notice_start_date" class="form-control" required onchange="autoSetNoticeId()">
        </div>

        <div class="mb-3">
            <label class="form-label">종료일</label>
            <input type="date" name="notice_end_date" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">카테고리</label>
            <input type="text" name="notice_category" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">메인 이미지</label>
            <input type="file" name="mainImage" class="form-control" accept="image/*" required>
        </div>

        <div class="mb-3">
            <label class="form-label">서브 이미지</label>
            <input type="file" name="subImage" class="form-control" accept="image/*">
        </div>

        <div class="d-flex align-items-center">
            <button type="submit" class="btn btn-primary me-3">공지 등록</button>
            <span style="color:blue;">${message}</span>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
