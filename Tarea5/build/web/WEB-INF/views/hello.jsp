

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
  
   
<a href="<c:url value="registrarCliente.htm"/>">Registrar Cliente</a>
<br>
<a href="<c:url value="consultarCliente.htm"/>">Consultar Cliente</a>
<br>
<a href="<c:url value="consultarFacturasActuales.htm"/>">Consultar Facturas Actuales</a>
<br>
<a href="<c:url value="consultarProductos.htm"/>">Consultar Productos</a>

</body>
</html>
