<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Registrar afiliacion a servicio adicional</title>
    <style>
    .error { color: red; }
    </style>
</head>
<body>
    <h1>Registrar afiliacion a servicio adicional</h1>
    <form:form modelAttribute="servicioAdicional" action ="registrarServicioAdicional.htm">
        <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
            <tr>
                <td>Codigo de producto</td>
                <td><form:input path="codigo"/></td>
                <td><form:errors path="codigo" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Nombre del servicio</td>
                <td> <form:input path="nombreProd"/> </td>
                <td><form:errors path="nombreProd" cssClass="error"/></td>
            </tr>
        </table>
        <br>
        <input type="submit" align="center" value="Enviar">
    </form:form>
<a href="<c:url value="hello.htm"/>">Home</a>
</body>
</html>
