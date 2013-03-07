<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Sign In</title>

</head>
<body>
	<%
		String loginerrormsg = null;
		loginerrormsg = (String) session.getAttribute("loginerrormsg");
		if (loginerrormsg != null) {
	%>
	<%=loginerrormsg%>
	<%
		}
	%>

	<form action="login_action.jsp" method="post">
		<table>
			<tr>
				<td>Username :</td>
				<td><input name="username" value="admin"></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Sign In"></td>
				<td><input type="reset"></td>
			</tr>
		</table>
	</form>
</body>
</html>