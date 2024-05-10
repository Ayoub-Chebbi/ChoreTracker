<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login and Registration</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>

<div class="container mt-5">
    <h1 class="text-center">Course Platform</h1>
    <p class="text-center">Join our growing community</p>

    <div class="row">
        <div class="col-md-6">
            <form:form action="/register" method="post" modelAttribute="newUser">
                <h2>Register</h2>
                <div class="form-group">
                    <form:errors path="username" class="text-danger"/>
                    <form:input class="form-control" path="username" placeholder="Name"/>
                </div>
                <div class="form-group">
                    <form:errors path="email" class="text-danger"/>
                    <form:input class="form-control" path="email" placeholder="Email"/>
                </div>
                <div class="form-group">
                    <form:errors path="password" class="text-danger"/>
                    <form:input class="form-control" path="password" placeholder="Password" type="password"/>
                </div>
                <div class="form-group">
                    <form:errors path="confirm" class="text-danger"/>
                    <form:input class="form-control" path="confirm" placeholder="Confirm Password" type="password"/>
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
            </form:form>
        </div>
        <div class="col-md-6">
            <form:form action="/login" method="post" modelAttribute="newLogin">
                <h2>Log In</h2>
                <div class="form-group">
                    <form:errors path="email" class="text-danger"/>
                    <form:input class="form-control" path="email" placeholder="Email"/>
                </div>
                <div class="form-group">
                    <form:errors path="password" class="text-danger"/>
                    <form:input class="form-control" path="password" placeholder="Password" type="password"/>
                </div>
                <button type="submit" class="btn btn-primary">Log In</button>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>
