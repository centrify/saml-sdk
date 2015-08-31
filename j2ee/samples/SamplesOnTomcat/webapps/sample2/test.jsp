<%@ page import="com.centrify.cloud.saas.example.CentrifySamlInitalizer"
%><%@ page import="com.centrify.cloud.saas.CentrifySamlFactory"
%><%@ page import="com.centrify.cloud.saas.ISamlResponseValidator"
%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SAML Response test page</title>
</head>
<body>
<form action="" method="POST">
<textarea name="samlresponse" cols=85 rows= 20 ></textarea>
<br><input type="submit" value="Submit" />
</form>
<%
String samlResponseStr = request.getParameter("samlresponse");
CentrifySamlFactory factory = CentrifySamlInitalizer.getFactory();
ISamlResponseValidator validator = factory.getResponseValidator();

 
%>
</body>
</html>