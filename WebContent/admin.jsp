<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Console</title>
</head>
<body>
<a href="signout.jsp">Sign Out</a>
<h2>Add New Category</h2>
<form action="add_category.jsp" method="post">
<table>
<tr><td>Category Name : </td><td><input name="catname"></td></tr>
<tr><td><input type="submit" value="Submit"></td><td></td></tr></table>
</form>

<br><br>
<h2>Add New Restaurant</h2>
<form action="add_restaurant.jsp" method="post">
<table>
<tr><td>City : </td><td><select name="cityname" style="width: 155px; ">
<option value="Atlanta">Atlanta</option>
<option value="New York">New York</option>
<option value="Chicago">Chicago</option>
<option value="Los Angeles">Los Angeles</option>
<option value="San Fransisco">San Fransisco</option>
</select></td></tr>
<tr><td>Restaurant Name : </td><td><input name="restname"></td></tr><tr><td><input type="submit" value="Submit"></td><td></td></tr></table>
</form>
</body>
</html>