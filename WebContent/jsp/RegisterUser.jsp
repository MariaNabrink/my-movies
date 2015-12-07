<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/MyMovies.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google Authenticator Settings</title>

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

			<a href="<%=request.getContextPath()%>/UserLogin.servlet"><button
				id="link" type="button">Back</button></a>

		<h2>Register</h2>

		<form name="frm" action="<%=request.getContextPath()%>/RegisterUser"
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
			</table>

			<input id="link" type="submit" value="Register">

		</form>
		<jsp:include page="Footer.jsp">
			<jsp:param name="MyMovies" value="" />
		</jsp:include>
	</div>
</body>
</html>