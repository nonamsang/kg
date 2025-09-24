<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>이벤트 수정/삭제 페이지</title>
<!-- Bootstrap 5 CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
	function deleteEvent(eventId, button) {
		if (confirm('정말 삭제하시겠습니까?')) {
			$.ajax({
				url : 'deleteEvent.do',
				type : 'POST',
				data : {
					eventId : eventId
				},
				success : function() {
					$(button).closest('tr').remove();
					alert('삭제가 완료되었습니다.');
				},
				error : function() {
					alert('삭제 중 오류가 발생했습니다.');
				}
			});
		}
	}

	function showDetailModal(content) {
		$("#modalContent").text(content);
		var myModal = new bootstrap.Modal(document
				.getElementById('detailModal'));
		myModal.show();
	}

	function enableEdit(td, eventId, columnName) {
		var currentValue = $(td).text().trim();
		var input = $(
				'<input type="text" class="form-control form-control-sm text-center" style="width: 120px; margin: auto;">')
				.val(currentValue);
		$(td).html(input);
		input.focus();

		input.on('blur', function() {
			var newValue = $(this).val().trim();
			if (newValue === '' || newValue === currentValue) {
				$(td).text(currentValue);
				return;
			}

			$.ajax({
				url : 'updateEvent.do',
				type : 'POST',
				data : {
					eventId : eventId,
					column : columnName,
					newValue : newValue
				},
				success : function() {
					$(td).text(newValue);
					alert('수정이 완료되었습니다.');
				},
				error : function() {
					alert('수정 중 오류가 발생했습니다.');
					$(td).text(currentValue);
				}
			});
		});
	}
</script>

<style>
body {
	padding: 30px;
	background-color: #f5f5f5;
}

.container-fluid {
	padding: 30px;
	background-color: white;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.text-truncate {
	max-width: 120px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	cursor: pointer;
}

table th, table td {
	padding: 8px !important;
	font-size: 14px;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header2.jsp" />

	<div class="container-fluid">
		<h2 class="mb-4 text-center">이벤트 목록</h2>

		<div class="table-responsive">
			<table class="table table-bordered text-center align-middle">
				<thead class="table-primary">
					<tr>
						<th>이벤트 ID</th>
						<th>이벤트명</th>
						<th>이벤트 타입</th>
						<th>이벤트 제목</th>
						<th>이벤트 설명</th>
						<th>이벤트 시작일</th>
						<th>이벤트 종료일</th>
						<th>카테고리</th>
						<th>메인 이미지</th>
						<th>서브 이미지</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="event" items="${eventList}">
						<tr>
							<td>${event.event_id}</td>

							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_name')">${event.event_name}</td>
							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_type')">${event.event_type}</td>
							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_title')">${event.event_title}</td>

							<!-- description 클릭 시 모달, 더블클릭 시 수정 -->
							<td class="text-truncate"
								onclick="handleClick(this, '${event.event_id}', 'event_description')">
								${event.event_description}</td>


							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_start_date')">
								${event.event_start_date}</td>

							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_end_date')">
								${event.event_end_date}</td>


							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_category')">${event.event_category}</td>

							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_image_source')">${event.event_image_source}</td>

							<td class="text-truncate"
								ondblclick="enableEdit(this, '${event.event_id}', 'event_sub_image_source')">${event.event_sub_image_source}</td>

							<td><button type="button" class="btn btn-danger btn-sm"
									onclick="deleteEvent('${event.event_id}', this)">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 상세 모달 -->
	<div class="modal fade" id="detailModal" tabindex="-1"
		aria-labelledby="detailModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-lg">
			<div class="modal-content">
				<div class="modal-body" id="modalContent"
					style="white-space: pre-wrap; word-break: break-word;"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<script>
		function showDetailModal(td) {
			var content = $(td).text().trim();
			$("#modalContent").text(content);
			var myModal = new bootstrap.Modal(document
					.getElementById('detailModal'));
			myModal.show();
		}

		var clickTimer = null;

		function handleClick(td, eventId, columnName) {
			if (clickTimer == null) {
				clickTimer = setTimeout(function() {
					// 300ms 안에 두 번째 클릭이 없으면 → 단일 클릭으로 판단
					showDetailModal(td);
					clickTimer = null;
				}, 250); // 250~300ms 권장
			} else {
				clearTimeout(clickTimer);
				clickTimer = null;
				// 더블 클릭으로 판단
				enableEdit(td, eventId, columnName);
			}
		}
	</script>
	
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
