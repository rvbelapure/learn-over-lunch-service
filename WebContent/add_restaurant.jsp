<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add category</title>
</head>
<body>
<jsp:useBean id="cathandler"
		class="edu.gatech.mas.learnoverlunch.server.AdminServices"
		scope="session" />
<% cathandler.addRestaurant((String) request.getParameter("restname"),
		(String) request.getParameter("cityname")); %>
<jsp:forward page="admin.jsp"></jsp:forward>
</body>
</html>