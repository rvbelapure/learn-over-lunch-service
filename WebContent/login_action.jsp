<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logging in</title>
</head>
<body>
	<jsp:useBean id="loginhandler"
		class="edu.gatech.mas.learnoverlunch.server.ServerLoginHandler"
		scope="session" />
	<%
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if((username != null) && (!username.equals("admin")))
	{
			session.setAttribute("loginerrormsg", "You are not authorized to log in." +
				"<br>This service is only for administrative purposes.");

	%>
	<jsp:forward page="login.jsp"></jsp:forward>
	<%
		}
		else
		{
			boolean auth = loginhandler.authenticateAdmin(password);
			if(auth)
			{
				session.setAttribute("user", "admin");
				%>
				<jsp:forward page="admin.jsp"></jsp:forward>
				<%
			}
			else
			{
				session.setAttribute("loginerrormsg", "Incorrect password.");

			%>
			<jsp:forward page="login.jsp"></jsp:forward>
			<%
			}
		}
	%>

</body>
</html>