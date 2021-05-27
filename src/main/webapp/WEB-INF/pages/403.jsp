<%--
  Created by IntelliJ IDEA.
  User: orel7
  Date: 23.05.2021
  Time: 00:16
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Access Denied</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">

</head>
<body>


<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<div class="page-title">Access Denied!</div>

<h3 style="color:red;">Sorry, you can not access this page!</h3>


<jsp:include page="_footer.jsp" />

</body>
</html>