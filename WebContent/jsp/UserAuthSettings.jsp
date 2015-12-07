<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/MyMovies.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google Authenticator Settings</title>
</head>
<body id="body1">
	<jsp:include page="Header.jsp">
		<jsp:param name="MyMovies" value="MyMovies" />
	</jsp:include>
<body>
	<div id="border">
		<a href="<%=request.getContextPath()%>/UserLogout"><button id="link2" type="button">Log Out</button></a>
				
				<h2></h2>
				 
				 <a href="<%=request.getContextPath()%>/MyMovies.servlet"><button id="link2" type="button">Back</button></a>

		<h2>If you want to use Google Authenticator you have to install
			it on your smart phone and generate QR code with the button and
			adding it to the authenticator</h2>

		<a href= "<%=request.getContextPath()%>/GoogleAuthQRGenerator" target="_blank"><button id="link" type="button">Generate QR code</button></a>

		<h2></h2>
		<h1></h1>
		<h2></h2>
		<h1></h1>

		<h2>Register your usage of Google Authenticator</h2>

		<form name="frm"
			action="<%=request.getContextPath()%>/UserAuthSettings" method="POST" onsubmit="return validateForm()">
			<input id="link" type="submit" value="Register">
		</form>
		
	</div>
	
	<jsp:include page="Footer.jsp">
		<jsp:param name="MyMovies" value="" />
	</jsp:include>
	
</body>
</html>