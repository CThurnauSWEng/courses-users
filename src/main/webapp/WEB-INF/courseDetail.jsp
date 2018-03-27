<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Course Detail</title>
</head>
<body>
	<h1>${this_course.title }</h1>
	<h2></h2>
	<h3>Instructor: ${this_course.instructor }</h3>
	<h3>Signups: ${count }</h3>
	<h3></h3>

	<table>
	    <thead>
	        <tr>
	            <th>Name</th>
	            <th>Sign Up Date</th>
	            <th>Action</th>
	        </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="user" items="${this_course.users}" >
	    <tr>
	        <td>${user.fullName}</td>
	        <td>${user.lastSignIn }</td>
	        <td>Placeholder for action</td>
	    </tr>
	    </c:forEach>		
	    </tbody>
	</table>
	
	<form method="POST" action="/courses/edit">
		<input type="hidden" name="courseId" value="${this_course.id }">
  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>		
		<input type="submit" value="Edit"/>
	</form>
	<form method="POST" action="/courses/delete">
		<input type="hidden" name="courseId" value="${this_course.id }">
  		<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>		
		<input type="submit" value="Delete"/>
	</form>
</body>
</html>