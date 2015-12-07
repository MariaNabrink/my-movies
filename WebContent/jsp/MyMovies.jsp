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
</head>
<body id="body2">
	<jsp:include page="Header.jsp">
		<jsp:param name="MyMovies" value="MyMovies" />
	</jsp:include>

	<div id="border">
		<a href="<%=request.getContextPath()%>/UserLogout"><button
				id="link2" type="button">Log Out</button></a>

		<h1>This is My Movies</h1>

		<a href="<%=request.getContextPath()%>/jsp/AddMovie.jsp"><button
				id="link" type="button">Add Movie</button></a>


		<table id="table1" align="center">
			<tr>
				<th>Title</th>
				<th>Director</th>
				<th>Year</th>
				<th>Category</th>
			</tr>

			<c:forEach var="movie" items="${movies}">
				<tr>
					<td>${movie.title}</td>
					<td>${movie.director}</td>
					<td>${movie.year}</td>
					<td>${movie.category}</td>
				</tr>
			</c:forEach>

		</table>
		
		<a href="<%=request.getContextPath()%>/jsp/UserAuthSettings.jsp"><button
				id="link" type="button">Google Authenticator Settings</button></a>

	</div>

	<jsp:include page="Footer.jsp">
		<jsp:param name="MyMovies" value=""/>
	</jsp:include>

</body>
</html>