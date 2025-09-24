<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul id="recentListSidebar">
  <c:forEach var="recent" items="${recentList}" varStatus="status" begin="0" end="2">
    <li>
      <a href="MainProductName.do?product_id=${recent.product_id}">
        <img src="${pageContext.request.contextPath}/resources/img/${recent.product_image}" 
             style="width: 70px; height: 70px; object-fit: cover; border-radius: 8px; margin-bottom: 10px;" />
      </a>
    </li>
  </c:forEach>

  <c:if test="${empty recentList}">
    <li style="text-align:center; color:#999;">최근 본 상품이 없습니다.</li>
  </c:if>
</ul>
