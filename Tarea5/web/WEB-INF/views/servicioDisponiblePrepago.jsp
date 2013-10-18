<%-- 
    Document   : servicioDisponiblePrepago
    Created on : 18-oct-2013, 0:38:51
    Author     : johndelgado
--%>


<%@ include file="/WEB-INF/views/include.jsp" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listar cupos disponibles</title>
    </head>
        <h1>Listar cupos disponibles para un producto prepago</h1>
    <form:form modelAttribute="producto" action ="servicioDisponiblePrepago.htm">
        <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
            <tr>
                <td>Codigo del producto prepago</td>
                <td><form:input path="codigoProd"/></td>
                <td><form:errors path="codigoProd" cssClass="error"/></td>
            </tr>
        </table>
        <input type="submit" align="center" value="Enviar">
    </form:form>
        
     <br><br>
     <a href="<c:url value="hello.htm"/>">Home</a>
     <br><br>
        
</html>
