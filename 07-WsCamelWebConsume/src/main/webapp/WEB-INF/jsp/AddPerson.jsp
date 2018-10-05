<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="/webapp/addPerson.htm" method="get">
		<table>
			<tr>
				<td>Id :</td>
				<td><input type="text" name="id" /></td>
			</tr>
			<tr>
				<td>Name :</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Age :</td>
				<td><input type="text" name="age" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Add Person" /></td>
			</tr>
		</table>
	</form>
	
	<form action="/webapp/index.jsp" method="get">
		<table>
			<tr>
				<td colspan="2"><input type="submit" value="Home" /></td>
			</tr>
		</table>
	</form>

</body>
</html>