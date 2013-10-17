

<%@ include file="/WEB-INF/views/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>MANEJADOR INNOVA</title>
    <style>
    .error { color: red; }
    </style>
</head>
<body>
    <h1>Bienvenido al manejador INNOVA</h1>
    <h3>Seleccione una opcion de su preferencia:</h3>
    <br>

   
<a href="<c:url value="registrarCliente.htm"/>">Registrar cliente</a>
<br>
<a href="<c:url value="consultarCliente.htm"/>">Consultar cliente</a>
<br>
<a href="<c:url value="consultarFacturasActuales.htm"/>">Consultar facturas actuales</a>
<br>
<a href="<c:url value="consultarProductos.htm"/>">Consultar productos</a>
<br>
<a href="<c:url value="registrarAfiliacion.htm"/>">Registrar afiliacion</a>
<br>
<a href="<c:url value="registrarServicioAdicional.htm"/>">Registrar afiliacion a servicio adicional</a>

</body>
</html>
