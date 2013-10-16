
<%@ include file="/WEB-INF/views/include.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Factura</h3>
        <c:forEach items="${model.facturasActuales}" var="factura">
            <b>Codigo Producto: </b><c:out value="${factura.producto.codigoProd}"/>
            <b>Fecha: </b><c:out value="${factura.fecha}"/>
            <b>Monto Total: </b><c:out value="${factura.montoTotal}"/>
            <b>Comentario(s): </b>
            <c:forEach items="${factura.comentarios}" var="comment">
                <c:out value="${comment}"/>,
            </c:forEach><br><br>
        </c:forEach>
            
     <br><br>
     <a href="<c:url value="hello.htm"/>">Home</a>
     <br><br>
     
    </body>
</html>
