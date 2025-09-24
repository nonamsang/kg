<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>공지사항 목록</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            padding: 30px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        table th, table td {
            text-align: center;
            vertical-align: middle;
            padding: 12px !important;
            font-size: 14px;
        }
        .text-truncate {
            max-width: 150px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            cursor: pointer;
        }
        .text-truncate:hover {
            background-color: #f0f0f0;
        }
    </style>
    <script>
        function deleteNotice(noticeId) {
            if (confirm('정말 삭제하시겠습니까?')) {
                location.href = 'deleteNotice.do?noticeId=' + noticeId;
            }
        }

        function enableEdit(td, noticeId, columnName) {
            var currentValue = $(td).text().trim();
            var inputType = (columnName == 'notice_start_date' || columnName == 'notice_end_date') ? 'date' : 'text';
            var input = $('<input type="' + inputType + '" class="form-control form-control-sm text-center" style="width: 120px; margin: auto;">').val(currentValue);
            $(td).html(input);
            input.focus();

            input.on('blur', function () {
                var newValue = $(this).val().trim();
                if (newValue === '' || newValue === currentValue) {
                    $(td).text(currentValue);
                    return;
                }
                location.href = 'updateNotice.do?noticeId=' + noticeId + '&column=' + columnName + '&newValue=' + encodeURIComponent(newValue);
            });
        }

        function showDetailModal(td) {
            var content = $(td).text().trim();
            $("#modalContent").text(content);
            var myModal = new bootstrap.Modal(document.getElementById('detailModal'));
            myModal.show();
        }

        var clickTimer = null;

        function handleClick(td, noticeId, columnName) {
            if (clickTimer == null) {
                clickTimer = setTimeout(function () {
                    showDetailModal(td);
                    clickTimer = null;
                }, 250);
            } else {
                clearTimeout(clickTimer);
                clickTimer = null;
                enableEdit(td, noticeId, columnName);
            }
        }
    </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header2.jsp" />

<div class="container">
    <h2 class="mb-4 text-center">공지사항 목록</h2>
    <div class="table-responsive">
        <table class="table table-bordered align-middle">
            <thead class="table-primary">
                <tr>
                    <th>ID</th>
                    <th>공지명</th>
                    <th>제목</th>
                    <th>설명</th>
                    <th>시작일</th>
                    <th>종료일</th>
                    <th>카테고리</th>
                    <th>메인 이미지</th>
                    <th>서브 이미지</th>
                    <th>삭제</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="notice" items="${noticeList}">
                    <tr>
                        <td>${notice.notice_id}</td>

                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_name')">${notice.notice_name}</td>
                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_title')">${notice.notice_title}</td>
                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_description')">${notice.notice_description}</td>

                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_start_date')">${notice.notice_start_date}</td>
                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_end_date')">${notice.notice_end_date}</td>

                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_category')">${notice.notice_category}</td>

                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_image_source')">${notice.notice_image_source}</td>

                        <td class="text-truncate" onclick="handleClick(this, '${notice.notice_id}', 'notice_sub_image_source')">${notice.notice_sub_image_source}</td>

                        <td><button type="button" class="btn btn-danger btn-sm" onclick="deleteNotice('${notice.notice_id}')">삭제</button></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- 상세 모달 -->
<div class="modal fade" id="detailModal" tabindex="-1" aria-labelledby="detailModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-body" id="modalContent" style="white-space: pre-wrap; word-break: break-word;"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
