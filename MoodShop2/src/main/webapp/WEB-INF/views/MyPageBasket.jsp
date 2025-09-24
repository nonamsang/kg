<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>장바구니 - MOODSHOP</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
</head>
<body>

<div style="max-width: 1000px; margin: 0 auto; padding: 30px 10px;">

    <div class="text-center mb-4">
        <h2>🛒 장바구니 목록</h2>
    </div>

    <c:choose>
        <c:when test="${empty cartList}">
            <p class="text-center">장바구니에 담긴 상품이 없습니다.</p>
        </c:when>
        <c:otherwise>
            <form id="orderForm" method="POST" action="${pageContext.request.contextPath}/orderPage.do">
                <input type="hidden" name="selectedIds" id="selectedIdsInput">

                <div class="table-responsive">
                    <table class="table table-bordered text-center align-middle" style="table-layout: fixed;">
                        <thead class="table-dark">
                            <tr>
                                <th style="width: 10%;">선택</th>
                                <th style="width: 25%;">상품명</th>
                                <th style="width: 20%;">옵션</th>
                                <th style="width: 15%;">수량</th>
                                <th style="width: 15%;">단가</th>
                                <th style="width: 15%;">총 금액</th>
                            </tr>
                        </thead>
                        <tbody id="cartTableBody">
                            <c:forEach var="item" items="${cartList}">
                                <tr id="cartRow-${item.basket_detail_id}">
                                    <td><input type="checkbox" name="selectedIds" value="${item.basket_detail_id}" class="form-check-input item-checkbox"></td>
                                    <td>${item.product_name}</td>
                                    <td>${item.option_color} / ${item.option_size}</td>
                                    <td>
                                        <input type="number" class="form-control quantity-input" name="basket_count_${item.basket_detail_id}"
                                               data-id="${item.basket_detail_id}" value="${item.basket_detail_count}" min="1" style="width: 80px; margin: 0 auto;">
                                    </td>
                                    <td class="product-price" data-price="${item.product_price}">${item.product_price} 원</td>
                                    <td id="totalPrice-${item.basket_detail_id}">${item.product_price * item.basket_detail_count} 원</td>

                                    <input type="hidden" name="product_id_${item.basket_detail_id}" value="${item.product_id}" />
                                    <input type="hidden" name="option_id_${item.basket_detail_id}" value="${item.option_id}" />
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- 🔸 총 금액 표시 -->
                <div class="text-end mt-3 mb-4">
                    <h4>총 결제 금액: <span id="totalAmountDisplay">0</span> 원</h4>
                </div>

                <!-- 🔸 버튼 그룹 -->
                <div class="d-flex justify-content-center gap-3 mt-4">
                    <button type="button" id="updateBtn" class="btn btn-outline-primary btn-lg" disabled>수량 수정</button>
                    <button type="button" id="deleteBtn" class="btn btn-outline-danger btn-lg" disabled>삭제</button>
                    <button type="button" id="orderBtn" class="btn btn-success btn-lg" disabled>결제하기</button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>

</div>

<script>
$(document).ready(function() {

    function toggleButtons() {
        let anyChecked = $('.item-checkbox:checked').length > 0;
        $('#updateBtn').prop('disabled', !anyChecked);
        $('#deleteBtn').prop('disabled', !anyChecked);
        $('#orderBtn').prop('disabled', !anyChecked);

        calculateTotalAmount();
    }

    // ✅ 체크박스 상태 변경 시 버튼 상태 갱신
    $('#cartTableBody').on('change', '.item-checkbox', function() {
        toggleButtons();
    });

    // ✅ 수량 변경 시 총 금액 업데이트
    $('#cartTableBody').on('input', '.quantity-input', function() {
        let row = $(this).closest('tr');
        let quantity = parseInt($(this).val());
        let price = parseInt(row.find('.product-price').data('price'));

        if (isNaN(quantity) || quantity < 1) quantity = 1;

        let totalPrice = price * quantity;
        row.find('td').eq(5).text(totalPrice + ' 원');

        calculateTotalAmount();
    });

    // ✅ 총 금액 계산
    function calculateTotalAmount() {
        let totalAmount = 0;

        $('.item-checkbox:checked').each(function () {
            let row = $(this).closest('tr');
            let quantity = parseInt(row.find('.quantity-input').val());
            let price = parseInt(row.find('.product-price').data('price'));

            totalAmount += quantity * price;
        });

        $('#totalAmountDisplay').text(totalAmount);
    }

    // ✅ 수량 수정 서버 반영
    $('#updateBtn').click(function() {
        let selectedItems = [];

        $('.item-checkbox:checked').each(function() {
            let id = $(this).val();
            let quantity = parseInt($(this).closest('tr').find('.quantity-input').val());

            if (isNaN(quantity) || quantity < 1) quantity = 1;

            selectedItems.push({ basket_detail_id: id, basket_detail_count: quantity });
        });

        if (selectedItems.length === 0) {
            alert('수정할 상품을 선택하세요.');
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/updateQuantity.do",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(selectedItems),
            success: function(response) {
                if (response.success) {
                    alert('서버에 수량이 정상 수정되었습니다.');
                } else {
                    alert('서버 수량 수정 실패');
                }
            },
            error: function() {
                alert('서버 오류');
            }
        });
    });

    // ✅ 삭제 처리
    $('#deleteBtn').click(function() {
        if (!confirm('정말 삭제하시겠습니까?')) return;

        let selectedIds = [];
        $('.item-checkbox:checked').each(function() {
            selectedIds.push($(this).val());
        });

        $.ajax({
            url: "${pageContext.request.contextPath}/deleteItems.do",
            type: "POST",
            traditional: true,
            data: { selectedIds: selectedIds },
            success: function(response) {
                if (response.success) {
                    alert('삭제 성공');
                    location.reload();
                } else {
                    alert('삭제 실패');
                }
            },
            error: function() {
                alert('서버 오류');
            }
        });
    });

    // ✅ 결제 처리
    $('#orderBtn').click(function() {
        let selectedIds = [];
        let totalAmount = 0;

        $('.item-checkbox:checked').each(function () {
            let id = $(this).val();
            selectedIds.push(id);

            let row = $(this).closest('tr');
            let price = parseInt(row.find('.product-price').data('price'));
            let quantity = parseInt(row.find('.quantity-input').val());

            totalAmount += price * quantity;
        });

        if (selectedIds.length === 0) {
            alert('선택된 상품이 없습니다.');
            return;
        }

        if (!confirm('카카오페이 결제를 진행하시겠습니까?')) return;

        $.ajax({
            url: "${pageContext.request.contextPath}/getUserInfo.do",
            type: "GET",
            success: function (user) {
                const IMP = window.IMP;
                IMP.init('imp66570674');

                IMP.request_pay({
                    pg: 'kakaopay',
                    pay_method: 'card',
                    merchant_uid: 'order_' + new Date().getTime(),
                    name: 'MOODSHOP 결제',
                    amount: totalAmount,
                    buyer_name: user.name,
                    buyer_tel: user.phone,
                    m_redirect_url: 'http://localhost:8080/orderComplete.do'
                }, function (rsp) {
                    if (rsp.success) {
                        alert('결제가 완료되었습니다.');

                        let orderItems = [];
                        $('.item-checkbox:checked').each(function () {
                            let id = $(this).val();
                            let row = $(this).closest('tr');
                            let productId = row.find('input[name="product_id_' + id + '"]').val();
                            let optionId = row.find('input[name="option_id_' + id + '"]').val();
                            let basketCount = parseInt(row.find('.quantity-input').val());

                            orderItems.push({
                                basket_detail_id: id,
                                product_id: productId,
                                option_id: optionId,
                                basket_count: basketCount
                            });
                        });

                        $.ajax({
                            url: "${pageContext.request.contextPath}/orderInsert.do",
                            type: "POST",
                            contentType: "application/json",
                            data: JSON.stringify({
                                merchant_uid: rsp.merchant_uid,
                                paid_amount: rsp.paid_amount,
                                orderItems: orderItems
                            }),
                            success: function () {
                                location.href = "${pageContext.request.contextPath}/orderComplete.do?orderId=" + rsp.merchant_uid;
                            },
                            error: function () {
                                alert('서버 오류 (주문 저장 실패)');
                            }
                        });
                    } else {
                        alert(`결제 실패: ${rsp.error_msg}`);
                    }
                });
            },
            error: function () {
                alert('유저 정보 조회 실패');
            }
        });
    });
});
</script>

</body>

</html>
