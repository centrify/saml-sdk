<%@ page import="com.centrify.cloud.saas.SamlPrincipal"
%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>
</head>
<body>
Welcome to <%
SamlPrincipal principal = SamlPrincipal.getPrincipal(request);
if(principal != null){
    out.append(principal.getName());
} else{
    out.append("Guest");
}
%>. <br>
<%
if(principal != null) {
    %><a href="protected/logout.jsp">Logout</a><br><%
}
%>
<a href="protected">Access protected resource</a><br>
<br>This page generated at <%= new java.util.Date() %>
</body>
</html>