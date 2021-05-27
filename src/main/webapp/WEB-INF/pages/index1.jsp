<%--
  Created by IntelliJ IDEA.
  User: orel7
  Date: 23.05.2021
  Time: 00:16
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">

    <title>Shop Online</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>


<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<div class="page-title">User Details</div>

<div class="demo-container">
    <c:if test="${pageContext.request.userPrincipal.name == null}">
<h1>Welcome to custom shop, you can register or login if you want to order some</h1>
    <ul>
        <li><a href="${pageContext.request.contextPath}/productList">Buy online</a></li>
        <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
        <li><a href="${pageContext.request.contextPath}/register">Sign in</a> </li>
    </ul>
    </c:if>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <ul>
        <li><center>Welcome,</center></li>
        <li><center>${currentCustomerInfo.customerName}</center></li>

            </ul>
        <table>
        <tr>
            <tr><td>username:</td><td>${currentCustomerInfo.userName}</td> </tr>
        <tr><td>email:</td> <td>${currentCustomerInfo.customerEmail}</td></tr>
            <tr><td>phone:</td> <td>${currentCustomerInfo.customerPhone}</td></tr>
        <tr><td>address</td> <td>${currentCustomerInfo.customerAdress}</td></tr>
        </table>
    </c:if>

</div>


<jsp:include page="_footer.jsp" />

</body>
</html>