<%@page import="com.centrify.cloud.saas.SamlPrincipal"%><%@
   page import="com.centrify.cloud.saas.SamlResponse"%><%@ 
   page language="java" contentType="text/html; charset=UTF-8" 
%><html>
  <head>
    <title>Welcome to Centrify Saas sample jsp</title>
  </head>
  <body bgcolor="white">
  <img src="centrify_horiz_color_pos.jpg"/>
  <a href="logout.jsp">Logout</a> 
        <%  String authType = request.getAuthType();
	    String authStatus = authType == null ? 
				"." : " with "+authType+" auth.";
            String contentType = request.getContentType();
            int    contentLength = request.getContentLength();
            String pathInfo = request.getPathInfo();
            String pathTranslated = request.getPathTranslated();
            String queryString = request.getQueryString();
            String requestMethod = request.getMethod();
            String requestURI = request.getRequestURI();
            String requestProtocol = request.getProtocol();
            String remoteUser = request.getRemoteUser();
            String remoteHost = request.getRemoteHost();
            String serverName = request.getServerName();
            int    serverPort = request.getServerPort();
            String servletPath = request.getServletPath();
            java.security.Principal principal = request.getUserPrincipal();
            String userPrincipal = (principal==null)?
                                        "null":principal.getName();
            String roleParam = request.getParameter("role");
            boolean isUserInRole = (roleParam != null) ?
			       request.isUserInRole(roleParam) : false;
	    String not = isUserInRole ? "" : "not";
	    String heading = isUserInRole ? "Congratulations!" : "Sorry. ";
	    String loginStatus = userPrincipal == null ? 
				    "not logged in." : 
				    "logged in as "+userPrincipal+".";
	    String isInRoleMsg = roleParam == null ? "" : 
		heading+" You are "+not+" in role <b><i>"+roleParam+"</i></b>.";
				    
        %>
	<p>Greetings! Welcome to Centrify DirectControl sample application<%=authStatus%>
	<p>You are <%=loginStatus%>
	<p><%=isInRoleMsg%>

	<form action="welcome.jsp">
	    <p>To check if you are in a J2EE role, enter the role name below and click submit. <br>
            <p>Role name: <input type="text" name="role"> </p>
	    <p>  <input type="submit" value="Submit"/></p>
        </form>

	<hr>

        <h3>Your request information:</h3>
        <p>
        Authentication Type = <%= authType %> <br>
        Content Type = <%= contentType %> <br>
        Content Length = <%= contentLength %> <br>
        Path Info = <%= pathInfo %> <br>
        Path Translated = <%= pathTranslated %> <br>
        Query String = <%= queryString %> <br>
        Request Method = <%= requestMethod %> <br>
        Request URI = <%= requestURI %> <br>
        Request Protocol = <%= requestProtocol %> <br>
        Remote User = <%= remoteUser %> <br>
        Remote Host = <%= remoteHost %> <br>
        Server Name = <%= serverName %> <br>
        Server Port = <%= serverPort %> <br>
        Servlet Path = <%= servletPath %> <br>

        User Principal = <%= userPrincipal %> <br>
        Is User In Role <i><%=roleParam%></i> = <%=isUserInRole%><br>

    <% if(principal instanceof SamlPrincipal) {
	    SamlResponse samlResponse = ((SamlPrincipal)principal).getResponse(); 
	%>
	<h3>Saml Response:</h3>
		NameId = <%= samlResponse.getNameid() %><br>
		NameFormat = <%= samlResponse.getNameFormat() %><br>
		Attributes:<br>
        <% for(SamlResponse.Attribute attribute : samlResponse.getAttributes()) { %>
		&nbsp;Name = <b><%=attribute.getName() %></b><br>
		&nbsp;NameFormat = <%=attribute.getNameFormat() %><br>
		&nbsp;FriendlyName = <%=attribute.getFriendlyName() %><br>
		  <% for(SamlResponse.AttributeValue attributeValue : attribute.getValues()) { %>
		    &nbsp;&nbsp;Value = <%=attributeValue.getValue() %>  	&#40;<%=attributeValue.getFormat()%>&#41;<br>
		  <% } %>  
	    <% } %>
	<%
	   } 
	%>

	<h3>Request Headers:</h3>
	<%  java.util.Enumeration e = request.getHeaderNames();
	    while (e.hasMoreElements()) {
		String name = (String)e.nextElement();
		String value = request.getHeader(name); %>
		<%= name %>: <%= value %> <br> <%  } %>

	<h3>Request Attributes:</h3>
	<%  e = request.getAttributeNames();
	    while (e.hasMoreElements()) {
		String name = (String)e.nextElement();
		Object obj = request.getAttribute(name); 
		String value = obj == null ? "" : obj.toString(); %>
		<%= name %>: <%= value %> <br> <%  } %>

	<H3>Servlet Parameters:</H3>
	<%  e = request.getParameterNames();
	    while (e.hasMoreElements()) {
		String name = (String)e.nextElement();
		String value = request.getParameter(name); %>
	        <%= name %> = <%= value %> <%  } %>

	<H3>Servlet Parameters (multiple value):</H3>
	<%  e = request.getParameterNames();
	    while (e.hasMoreElements()) {
		String name = (String)e.nextElement();
		String[] values = request.getParameterValues(name); %>
		<%= name %>: <br>
		<% for (int i = 0; i < values.length; i++) { %>
		    &nbsp&nbsp&nbsp&nbsp<%= values[i] %> <br><% } %>
	    <%   } %>
	
</html>
</body>
