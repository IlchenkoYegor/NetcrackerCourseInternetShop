<%--
  Created by IntelliJ IDEA.
  User: orel7
  Date: 23.05.2021
  Time: 00:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />
<jsp:include page="searchingSystem.jsp"/>

<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title">Product List</div>



<c:forEach items="${paginationProducts}" var="prodInfo">
    <div class="product-preview-container">
        <ul>
            <li><img class="product-image"
                     src="${pageContext.request.contextPath}/productImage?id=${prodInfo.productId}" /></li>
            <li>Code: ${prodInfo.code}</li>
            <li>Name: ${prodInfo.name}</li>
            <li>Price: <fmt:formatNumber value="${prodInfo.price}" type="currency"/></li>
            <li><a
                    href="${pageContext.request.contextPath}/buyProduct?id=${prodInfo.productId}">
                Buy Now</a></li>
            <!-- For Manager edit Product -->
            <security:authorize  access="hasRole('ROLE_MANAGER')">
                <li><a style="color:red;"
                       href="${pageContext.request.contextPath}/product?id=${prodInfo.productId}">
                    Edit Product</a></li>
            </security:authorize>
        </ul>
    </div>

</c:forEach>
<br/>


<%--<c:if test="${paginationProducts.size() > 1}">
    <div class="page-navigator">
        <c:forEach items="${paginationProducts}" var = "prodInfo">
            <c:if test="${page != -1 }">
                <a href="productList?page=${page}" class="nav-item">${page}</a>
            </c:if>
            <c:if test="${page == -1 }">
                <span class="nav-item"> ... </span>
            </c:if>
        </c:forEach>

    </div>
</c:if>--%>

<jsp:include page="_footer.jsp" />

</body>
</html>
