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
    <form:form modelAttribute="cliente" action ="registrarCliente.htm">
        <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
            <tr>
                <td>Cedula</td>
                <td><form:input path="cedula"/></td>
                <td><form:errors path="cedula" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Nombre</td>
                <td> <form:input path="nombre"/> </td>
                <td><form:errors path="nombre" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Direccion</td>
                <td><form:input path="direccion"/></td>
                <td><form:errors path="direccion" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Telefono</td>
                <td><form:input path="telefonos"/></td>
                <td><form:errors path="telefonos" cssClass="error"/></td>
            </tr>
                
           </tr>
        </table>
        <br>
        <input type="submit" align="center" value="Enviar">
    </form:form>
<a href="<c:url value="hello.htm"/>">Home</a>
</body>
</html>
