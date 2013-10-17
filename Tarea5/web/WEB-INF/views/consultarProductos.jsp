

<%@ include file="/WEB-INF/views/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Productos</title>
    </head>
    <body>
        <h3>Productos</h3>
        <c:forEach items="${model.Productos}" var="prod">
            <b>Codigo Producto: </b><c:out value="${prod.codigoProd}"/>
            <b>Modelo: </b><c:out value="${prod.modelo}"/>
            <b>Cliente (cedula): </b><c:out value="${prod.cliente.cedula}"/>
            <br><br>
        </c:forEach>            

     
    <form:form modelAttribute="producto" action ="consultarFacturaProducto.htm">
        <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
            <tr>
                <td>ID Producto</td>
                <td><form:input path="codigoProd"/></td>
                <td><form:errors path="codigoProd" cssClass="error"/></td>
            </tr>
                
           </tr>
        </table>
        <br>
        <input type="submit" align="center" value="Enviar">
    </form:form>
     
     <br><br>
     <a href="<c:url value="hello.htm"/>">Home</a>
     <br><br>
        
        
    </body>
</html>
