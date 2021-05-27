<%--
  Created by IntelliJ IDEA.
  User: orel7
  Date: 23.05.2021
  Time: 00:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<head>
    <meta charset="UTF-8">
    <title>Product</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
    <script>
        function ToMainPage() {
            document.location.href = "${pageContext.request.contextPath}/productList";
        }

        function deleteFunct(id) {
            $.ajax({
                url: '${pageContext.request.contextPath}/deleteProduct/'+id,
                type: 'DELETE',
                success: function () {
                    alert('product has been deleted');
                    ToMainPage()// alerts the response from jsp
                    //document.location.href = "/"
                },
                error: function (){
                    alert('can`t delete this product');
                }
            });
        }
    </script>


</head>
<body>

<jsp:include page="_header.jsp" />
<jsp:include page="_menu.jsp" />

<div class="page-title">Product</div>

<c:if test="${not empty errorMessage }">
    <div class="error-message">
            ${errorMessage}
    </div>
</c:if>

<form:form modelAttribute="productForm" method="POST" enctype="multipart/form-data">
    <table style="text-align:left;">
        <tr>
            <td>Id *</td>
            <td style="color:red;">
                <c:if test="${not empty productForm.productId}">
                    <form:hidden path="productId"/>
                    ${productForm.productId}
                </c:if>
                <c:if test="${empty productForm.productId}">
                    <form:input path="productId" />
                    <form:hidden path="newProduct" />
                </c:if>
            </td>
            <td><form:errors path="code" class="error-message" /></td>
        </tr>
        <tr>
            <td>Code *</td>
            <td style="color:red;">
                <c:if test="${not empty productForm.code}">
                    <form:input path="code"/>
                    ${productForm.code}
                </c:if>
                <c:if test="${empty productForm.code}">
                    <form:input path="code" />
                    <form:hidden path="newProduct" />
                </c:if>
            </td>
            <td><form:errors path="code" class="error-message" /></td>
        </tr>
        <tr>
            <td>Name *</td>
            <td><form:input path="name" /></td>
            <td><form:errors path="name" class="error-message" /></td>
        </tr>
        <tr>
            <td>City *</td>
            <td><form:input path="locationCity" /></td>
            <td><form:errors path="locationCity" class="error-message" /></td>
        </tr>
        <tr>
            <td>Country *</td>
            <td><form:input path="locationCountry" /></td>
            <td><form:errors path="locationCountry" class="error-message" /></td>
        </tr>
        <tr>
            <td>Address *</td>
            <td><form:input path="locationAddress" /></td>
            <td><form:errors path="locationAddress" class="error-message" /></td>
        </tr>
        <tr>
            <td>Recommended to this product (id) -1 if empty*</td>
            <td><form:input path="recommended" /></td>
            <td><form:errors path="name" class="error-message" /></td>
        </tr>
        <tr>
            <td>Price *</td>
            <td><form:input path="price" /></td>
            <td><form:errors path="price" class="error-message" /></td>
        </tr>
        <tr>
            <td>Image</td>
            <td>
                <img src="${pageContext.request.contextPath}/productImage?id=${productForm.productId}" width="100"/></td>
            <td> </td>
        </tr>
        <tr>
            <td>Upload Image</td>
            <td><form:input type="file" path="fileData"/></td>
            <td> </td>
        </tr>


        <tr>
            <td>&nbsp;</td>
            <td><input type="submit" value="Submit" /> <input type="reset"
                                                              value="Reset" /></td>
        </tr>

    </table>
</form:form>

<div>
    <button onclick="deleteFunct(${productForm.productId})">Delete</button>
</div>

<jsp:include page="_footer.jsp" />

</body>
</html>