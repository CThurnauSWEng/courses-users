<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashboard</title>
</head>
<body>
    <h1>Welcome <c:out value="${currentUser.fullName}"></c:out></h1>
    
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    
    <div>
    		<h4>Name: <c:out value="${currentUser.fullName}"></c:out></h4>
    		<h4>Email: <c:out value="${currentUser.username}"></c:out></h4>
    		<h4>Sign up date: <c:out value="${currentUser.createdAt}"></c:out></h4>
    		<h4>Last sign in: <c:out value="${currentUser.lastSignIn}"></c:out></h4>
    </div>
</body>
</html>