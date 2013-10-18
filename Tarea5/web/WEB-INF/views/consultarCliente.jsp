


<%@ include file="/WEB-INF/views/include.jsp" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head><title><fmt:message key="title"/></title>
    <style>
    .error { color: red; }
    </style>
    </head>
    <body>
        <h1>Consultar clientes</h1>
        
        <h3>Clientes</h3>
        <c:forEach items="${model.Clientes}" var="cliente">
            <b>Cedula: </b><c:out value="${cliente.cedula}"/>
            <b>Nombre: </b><c:out value="${cliente.nombre}"/>
            <b>Direccion: </b><c:out value="${cliente.direccion}"/>
            <b>Telefono(s): </b>
            <c:forEach items="${cliente.telefonos}" var="telf">
                <c:out value="${telf}"/>,
            </c:forEach><br><br>
        </c:forEach>
            <br><br><br>
            <h3> Modificar Cliente </h3>
            <br>
            
    <form:form modelAttribute="cliente" action ="consultarCliente.htm">
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
        
        <br><br>
        <h3>Cliente a Facturar</h3>
        <p>Esta opcion puede tardar varios minutos</p>
    <form:form modelAttribute="clienteFactura" action ="consultarFacturas.htm">
        <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
            <tr>
                <td>Cedula</td>
                <td><form:input path="cedula"/></td>
                <td><form:errors path="cedula" cssClass="error"/></td>
            </tr>
        </table>
        <input type="submit" align="center" value="Enviar">
    </form:form>
        
     <br><br>
     <a href="<c:url value="hello.htm"/>">Home</a>
     <br><br>
        
    </body>
</html>
