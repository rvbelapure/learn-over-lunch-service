<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign Out</title>
</head>
<body>
<% session.setAttribute("user", null);
   session.invalidate();
%>
You have been logged out successfully. <br>
Please close this browser window for security.
</body>
</html>