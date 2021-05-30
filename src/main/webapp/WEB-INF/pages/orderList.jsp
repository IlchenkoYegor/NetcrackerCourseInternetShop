<%--
  Created by IntelliJ IDEA.
  User: orel7
  Date: 23.05.2021
  Time: 00:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order List</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title">Order List</div>

<!--<div>Total Order Count: ${1111}</div>-->

<table border="1" style="width:100%">
    <tr>
        <th>Order Num</th>
        <th>Order Date</th>
        <th>Account Id</th>
        <th>Order Id</th>

        <th>Amount</th>
        <th>View</th>
    </tr>
    <c:forEach items="${paginationResult}" var="orderInfo">
        <tr>
            <td>${orderInfo.orderNum}</td>
            <td>
                <fmt:formatDate value="${orderInfo.orderDate}" pattern="dd-MM-yyyy HH:mm"/>
            </td>
            <td> ${orderInfo.accountId}</td>
            <td>${orderInfo.id}</td>

            <td style="color:red;">
                <fmt:formatNumber value="${orderInfo.amount}" type="currency"/>
            </td>
            <td><a href="${pageContext.request.contextPath}/order?orderId=${orderInfo.id}">
                View</a></td>
        </tr>
    </c:forEach>
</table>
<%--<c:if test="${paginationResult.totalPages > 1}">
    <div class="page-navigator">
        <c:forEach items="${paginationResult.navigationPages}" var = "page">
            <c:if test="${page != -1 }">
                <a href="orderList?page=${page}" class="nav-item">${page}</a>
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
