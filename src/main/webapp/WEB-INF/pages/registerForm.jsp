<%--
  Created by IntelliJ IDEA.
  User: orel7
  Date: 23.05.2021
  Time: 00:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Enter Customer Information</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>
<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<div class="page-title">Enter Customer Information</div>

<form:form method="POST" modelAttribute="customerForm"
           action="${pageContext.request.contextPath}/register">

    <table>

        <tr>
            <td>Username *</td>
            <td><form:input path="userName" /></td>
            <td><form:errors path="userName" class="error-message" /></td>
        </tr>
        <tr>
            <td>Name *</td>
            <td><form:input path="customerName" /></td>
            <td><form:errors path="customerName" class="error-message" /></td>
        </tr>
        <tr>
            <td>Password *</td>
            <td><form:password path="password"/></td>
            <td><form:errors path="password" class="error-message"/></td>
        </tr>

        <tr>
            <td>Email *</td>
            <td><form:input path="customerEmail" /></td>
            <td><form:errors path="customerEmail" class="error-message" /></td>
        </tr>

        <tr>
            <td>Phone *</td>
            <td><form:input path="customerPhone" /></td>
            <td><form:errors path="customerPhone" class="error-message" /></td>
        </tr>

        <tr>
            <td>Address *</td>
            <td><form:input path="customerAdress" /></td>
            <td><form:errors path="customerAdress" class="error-message" /></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Submit" /> <input type="reset"
                                                              value="Reset" /></td>
        </tr>
    </table>

</form:form>


<jsp:include page="_footer.jsp" />


</body>
</html>