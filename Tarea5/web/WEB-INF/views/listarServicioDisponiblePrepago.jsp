<%-- 
    Document   : listarServicioDisponiblePrepago
    Created on : 18-oct-2013, 0:38:27
    Author     : johndelgado
--%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar cupos disponibles para producto prepago</title>
        <h3>Cupos disponibles</h3>
        <c:forEach items="${serviciosDisponibles}" var="servicios">
            <b>Nombre: </b><c:out value="${servicios.nombreServicio}"/> 
            <b>    Cantidad: </b><c:out value="${servicios.cantidadDisponible}"/>
            <br>
        </c:forEach>
    </head>
    <body>





    </body>
</html>
