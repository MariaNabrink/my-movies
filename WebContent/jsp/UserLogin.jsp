<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/style/MyMovies.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyMovies</title>

<script>
	function validateForm() {
		if (document.frm.user.value == "") {
			alert("User name should not be left blank");
			document.frm.user.focus();
			return false;
		} else if (!validateUserName(document.frm.user.value)) {
			alert("Invalid user name or password");
			document.frm.user.focus();
			return false;
		} else if (document.frm.password.value == "") {
			alert("Password should not be left blank");
			document.frm.password.focus();
			return false;
		} else if (!validatePassword(document.frm.password.value)) {
			alert("Invalid user name or password");
			document.frm.password.focus();
			return false;
		}
	}

	function validateUserName(user) {
		var regEx = /^[a-zA-Z0-9]+$/;
		return regEx.test(user);
	}

	function validatePassword(password) {
		var regEx = /^[a-zA-Z0-9]+$/;
		return regEx.test(password);
	}
</script>

</head>
<body id="body1">
	<jsp:include page="Header.jsp">
		<jsp:param name="MyMovies" value="MyMovies" />
	</jsp:include>


	<div id="border">

		<h2>Welcome and Login</h2>

		<form name="frm" action="<%=request.getContextPath()%>/UserLogin"
			method="POST" onsubmit="return validateForm()">

			<table id="table" align="center">
				<tr>
					<th>User name</th>
					<td><input type="text" name="user"></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><input type="password" name="password"></td>
				</tr>
				<tr>
					<th>Google authentication code</th>
					<td><input type="text" name="auth"></td>
				</tr>
			</table>

			<input id="link" type="submit" value="Login">

		</form>

		<a href="<%=request.getContextPath()%>/RegisterUser"><button
				id="link" type="submit">Register</button></a>

	</div>

	<jsp:include page="Footer.jsp">
		<jsp:param name="MyMovies" value="" />
	</jsp:include>

</body>
</html>