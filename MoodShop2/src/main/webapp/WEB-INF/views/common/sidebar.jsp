<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="sidebar">
  <div class="side-box">
    <!-- 장바구니 -->
    <div class="menu-item">
      <span>🛒 장바구니</span>
      <span class="count">${basketCount}</span>
    </div>

    <!-- 찜 목록 -->
    <div class="menu-item">
      <span>❤️ 찜 목록</span>
      <span class="count">${wishCount}</span>
    </div>

    <!-- 최근 본 상품 -->
    <div class="menu-title">최근 본 상품</div>
    <ul id="recentListSidebar">
      <c:forEach var="recent" items="${recentList}" varStatus="status" begin="0" end="3">
        <li>
          <a href="MainProductName.do?product_id=${recent.product_id}">
            <img src="${pageContext.request.contextPath}/resources/img/${recent.product_image}" />
          </a>
        </li>
      </c:forEach>
      <c:if test="${empty recentList}">
        <li style="text-align:center; color:#999;">최근 본 상품이 없습니다.</li>
      </c:if>
    </ul>

    <div class="top-btn-wrapper">
      <button id="topBtn" onclick="scrollToTop()" title="맨 위로">TOP</button>
    </div>
  </div>
</div>


<script>
	// TOP 버튼
	function scrollToTop() {
		window.scrollTo({
			top : 0,
			behavior : 'smooth'
		});
	}
</script>
