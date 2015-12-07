<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/style/MyMovies.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Movie</title>


<script>
function validateForm()
{
    if(document.frm.title.value==""){
      alert("Title should not be left blank");
      document.frm.title.focus();
      return false;
    }
    else if(document.frm.director.value==""){
      alert("Director should not be left blank");
      document.frm.director.focus();
      return false;
    }
    else if(document.frm.year.value==""){
      alert("Year should not be left blank");
      document.frm.year.focus();
      return false;
    }
    else if(!document.frm.year.value.match(new RegExp('^\\d{4}$'))){
    	alert("Year can only contain four digits. E.g. 1987");
    	document.frm.year.focus();
    	return false;
    }
    else if(document.frm.category.value==""){
      alert("Category should not be left blank");
      document.frm.category.focus();
      return false;
    }
}
</script>


</head>
<body id="body">
	<jsp:include page="Header.jsp">
		<jsp:param name="MyMovies" value="MyMovies"/>
	</jsp:include>
	
	
	<div id="border">
	
	<a href="<%=request.getContextPath()%>/MyMovies.servlet"><button id="link" type="button">Back</button></a>
	
	<form name="frm" action="<%=request.getContextPath()%>/MyMovies.servlet" method="POST" onsubmit="return validateForm()">
	
	<table id="table" align="center">
		<tr>
			<th>Title</th>
			<td><input type="text" name="title"></input></td>
		</tr>
		<tr>
			<th>Director</th>
			<td><input type="text" name="director"></input></td>
		</tr>
		<tr>
			<th>Year</th>
			<td><input type="text" name="year"></input></td>
		</tr>
		<tr>
			<th>Category</th>
			<td><input type="text" name="category"></input></td>
		</tr>
	</table>
		
		<input id="link" type="submit" value="Submit">
	</form>
	
	</div>
	

	<jsp:include page="Footer.jsp">
		<jsp:param name="MyMovies" value=""/>
	</jsp:include>


</body>
</html>