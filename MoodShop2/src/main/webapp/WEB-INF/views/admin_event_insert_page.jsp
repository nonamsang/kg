<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>이벤트 등록 페이지</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function autoSetEventId() {
            var startDate = document.getElementById('startDate').value;
            if (startDate) {
                var dateParts = startDate.split('-');
                var eventId = dateParts[0].substring(2) + dateParts[1] + dateParts[2];
                document.getElementById('eventId').value = eventId;
            }
        }

        function previewMainImage(input) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var img = document.getElementById('mainImagePreview');
                img.src = e.target.result;
                img.style.display = 'inline-block';
            }
            reader.readAsDataURL(input.files[0]);
        }

        function previewSubImage(input) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var img = document.getElementById('subImagePreview');
                img.src = e.target.result;
                img.style.display = 'inline-block';
            }
            reader.readAsDataURL(input.files[0]);
        }

        function showImageModal(imageSrc) {
            var modalImage = document.getElementById('modalImage');
            modalImage.src = imageSrc;
            var myModal = new bootstrap.Modal(document.getElementById('imageModal'));
            myModal.show();
        }

        $(document).ready(function () {
            $("#eventForm").submit(function (e) {
                e.preventDefault(); // 기본 form 전송 방지

                var formData = new FormData(this);

                $.ajax({
                    url: 'insertEvent.do',
                    type: 'POST',
                    data: formData,
                    enctype: 'multipart/form-data',
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        $("#successMessage").text("정상적으로 DB에 등록되었습니다.").show();
                        $("#eventForm")[0].reset();
                        $("#mainImagePreview").hide();
                        $("#subImagePreview").hide();
                        $("#eventId").val("");
                    },
                    error: function () {
                        alert("등록 중 오류가 발생했습니다.");
                    }
                });
            });
        });
    </script>

    <style>
        .preview {
            display: inline-block;
            margin-left: 15px;
            vertical-align: middle;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 3px;
            background-color: #f8f9fa;
            cursor: pointer;
        }
        .success-message {
            color: #0d6efd;
            margin-left: 15px;
            font-weight: bold;
            display: none;
        }
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
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header2.jsp" />

<div class="container">
    <h2 class="mb-4 text-center">이벤트 등록</h2>

    <form id="eventForm" enctype="multipart/form-data">

        <div class="mb-3">
            <label class="form-label">이벤트 아이디</label>
            <input type="text" id="eventId" name="event_id" class="form-control" readonly style="background-color: #e9ecef;">
        </div>

        <div class="mb-3">
            <label class="form-label">이벤트명</label>
            <input type="text" name="event_name" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">이벤트 타입</label>
            <input type="text" name="event_type" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">이벤트 제목</label>
            <input type="text" name="event_title" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">이벤트 설명</label>
            <textarea name="event_description" class="form-control" rows="4" required></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label">시작일</label>
            <input type="date" id="startDate" name="event_start_date" class="form-control" required onchange="autoSetEventId()">
        </div>

        <div class="mb-3">
            <label class="form-label">종료일</label>
            <input type="date" name="event_end_date" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">카테고리</label>
            <input type="text" name="event_category" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">메인 이미지</label>
            <div class="d-flex align-items-center">
                <input type="file" name="mainImage" class="form-control" accept="image/*" required onchange="previewMainImage(this)">
                <img id="mainImagePreview" class="preview ms-3" style="width: 150px; display: none;" onclick="showImageModal(this.src)">
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label">서브 이미지</label>
            <div class="d-flex align-items-center">
                <input type="file" name="subImage" class="form-control" accept="image/*" onchange="previewSubImage(this)">
                <img id="subImagePreview" class="preview ms-3" style="width: 150px; display: none;" onclick="showImageModal(this.src)">
            </div>
        </div>

        <div class="d-flex align-items-center">
            <button type="submit" class="btn btn-primary me-3">이벤트 등록</button>
            <span id="successMessage" class="success-message">정상적으로 DB에 등록되었습니다.</span>
        </div>

    </form>
</div>

<!-- 이미지 모달 -->
<div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-body text-center">
                <img id="modalImage" src="" class="img-fluid rounded">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
