<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Details</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h1>Course Details</h1>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">${chore.title}</h5>
            <p class="card-text">Description: ${chore.description}</p>
            <p class="card-text">Location: ${chore.location}</p>
        </div>
    </div>
</div>

</body>
</html>
