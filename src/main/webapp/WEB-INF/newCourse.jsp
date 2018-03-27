<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>New Course</title>
</head>
<body>
	<h1>Create a new course</h1>
	<h2></h2>
	<form:form method="POST" action="/courses/createCourse" modelAttribute="course">
	    <form:label path="title">Name:
	    <form:errors path="title"/>
	    <form:input path="title"/></form:label>
	
	    <form:label path="instructor">Instructor:
	    <form:errors path="instructor"/>
	    <form:input path="instructor"/></form:label>
	
	    <form:label path="courseLimit">Limit:
	    <form:errors path="courseLimit"/>
	    <form:input path="courseLimit"/></form:label>
	
	    <input type="submit" value="Create"/>
	</form:form>


</body>
</html>