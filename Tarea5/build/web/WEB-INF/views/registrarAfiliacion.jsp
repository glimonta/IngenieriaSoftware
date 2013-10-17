<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><fmt:message key="title"/></title>
    <style>
    .error { color: red; }
    </style>
</head>
<body>
    <h1><fmt:message key="heading"/></h1>
    <form:form modelAttribute="afiliacion" action ="registrarAfiliacion.htm">
        <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
            <tr>
                <td>Codigo de producto</td>
                <td><form:input path="producto.codigoProd"/></td>
                <td><form:errors path="producto.codigoProd" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Codigo de plan</td>
                <td> <form:input path="plan.nombre"/> </td>
                <td><form:errors path="plan.nombre" cssClass="error"/></td>
                <td>Tipo de plan</td>
                <td> <form:input path="plan.tipoPlan"/> </td>
                <td><form:errors path="plan.tipoPlan" cssClass="error"/></td>
            </tr>
        </table>
        <br>
        <input type="submit" align="center" value="Enviar">
    </form:form>
<a href="<c:url value="hello.htm"/>">Home</a>
</body>
</html>
