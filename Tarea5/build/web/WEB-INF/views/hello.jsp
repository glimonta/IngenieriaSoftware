

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
<a href="<c:url value="consultarCliente.htm"/>">Consultar Cliente</a>
</body>
</html>
