<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Courses</title>
</head>
<body>
	<h1>Welcome, ${currentUser.fullName }</h1>
	<h2></h2>
	<h2>Courses</h2>
	<p></p>
	<table>
	    <thead>
	        <tr>
	            <th>Course</th>
	            <th>Instructor</th>
	            <th>Signups/Limit</th>
	            <th>Action</th>
	        </tr>
	    </thead>
	    <tbody>
	    <c:forEach var="course" items="${courses}" >
	    <tr>
	        <td><a href="courses/showCourse/${course.id }">${course.title}</td>
	        <td>${course.instructor}</td>
	        <td>${fn:length(course.users)}/${course.courseLimit }</td>
	        <td><a href="courses/signup/${course.id }">Add</a></td>
	    </tr>
	    </c:forEach>		
	    </tbody>
	</table>
	<a href="/courses/new">Add a Course</a>

    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>


</body>
</html>