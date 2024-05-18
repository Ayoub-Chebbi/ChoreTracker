<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta title="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit chore</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h1>Edit chore</h1>
    <form action="/classes/${chore.id}/edit" method="post">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" title="title" class="form-control" value="${chore.title}" required>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <input type="text" id="description" title="description" class="form-control" value="${chore.description}" required>
        </div>
        <div class="form-group">
            <label for="location">Location:</label>
            <input type="text" id="location" title="location" class="form-control" value="${chore.location}" required>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
        <!-- Delete Button -->
        <a href="/classes/${chore.id}/delete" class="btn btn-danger">Delete</a>
        <!-- Cancel Button -->
        <button type="button" class="btn btn-secondary" onclick="clearFields()">Cancel</button>
    </form>
</div>

<script>
    function clearFields() {
        // Set value of all input fields to empty
        document.getElementById("title").value = "";
        document.getElementById("description").value = "";
        document.getElementById("location").value = "";
    }
</script>

</body>
</html>
