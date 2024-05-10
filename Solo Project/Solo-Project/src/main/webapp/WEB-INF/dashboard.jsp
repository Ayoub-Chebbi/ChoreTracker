<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Welcome, ${user.username}!</h1>
        </div>
        <div class="col text-right">
            <button class="btn btn-primary logout-btn" onclick="window.location.href='/logout'">Logout</button>
        </div>
    </div>
    <h2>All chores:</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Job Name</th>
            <th>Location</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="chore" items="${chores}">
    <tr>
        <td>${chore.title}</td>
        <td>${chore.location}</td>
        <td><a href="/classes/${chore.id}">View</a></td>
        <td>Add</td>
        <td>
            <c:if test="${chore.user.id == user.id}">
                <a href="/classes/${chore.id}/edit" class="btn btn-sm btn-primary">Edit</a>
                <a href="/classes/${chore.id}/delete" class="btn btn-danger">Cancel</a>
            </c:if>
        </td>
    </tr>
</c:forEach>

        </tbody>
    </table>
    
    <!-- Button to add new chore -->
    <a href="/classes/new" class="btn btn-primary">New chore</a>
</div>

</body>
</html>
