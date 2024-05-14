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
    <table class="table table-striped" id="all-chores-table">
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
                <td>
                    <a href="/classes/${chore.id}" class="btn btn-sm btn-primary">View</a>
                    <button class="btn btn-sm btn-success add-chore" data-chore-id="${chore.id}">Add</button>
                    <c:if test="${chore.user.id == user.id}">
                        <a href="/classes/${chore.id}/edit" class="btn btn-sm btn-warning">Edit</a>
                        <button class="btn btn-sm btn-danger delete-chore" data-chore-id="${chore.id}">Delete</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <!-- Button to add new chore -->
    <a href="/classes/new" class="btn btn-primary">New chore</a>
    

    <!-- Table to show user's chores -->
    <h2>Your chores:</h2>
    <table class="table table-striped" id="user-chores-table">
        <thead>
            <tr>
                <th>Job Name</th>
                <th>Location</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
           
        </tbody>
    </table>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
   
    var userChores = JSON.parse(localStorage.getItem("userChores")) || [];

    
    function renderUserChores() {
        var userChoresTable = document.getElementById("user-chores-table").querySelector("tbody");
        userChoresTable.innerHTML = "";
        userChores.forEach(function(chore) {
            var newRow = '<tr>' +
                '<td>' + chore.title + '</td>' +
                '<td>' + chore.location + '</td>' +
                '<td>' +
                '<a href="/classes/' + chore.id + '" class="btn btn-sm btn-primary">View</a>' +
                '<button class="btn btn-sm btn-danger delete-chore" data-chore-id="' + chore.id + '">Done</button>' +
                '</td>' +
                '</tr>';
            userChoresTable.insertAdjacentHTML("beforeend", newRow);
        });
    }

    
    renderUserChores();

    
    var addChoreButtons = document.querySelectorAll(".add-chore");
    addChoreButtons.forEach(function(button) {
        button.addEventListener("click", function() {
            var choreId = this.getAttribute("data-chore-id");
            var row = this.closest("tr");
            var choreTitle = row.querySelector("td:nth-child(1)").innerText;
            var choreLocation = row.querySelector("td:nth-child(2)").innerText;

           
            userChores.push({ id: choreId, title: choreTitle, location: choreLocation });
            localStorage.setItem("userChores", JSON.stringify(userChores));

            
            renderUserChores();

            
            row.remove();
        });
    });

   
    var deleteChoreButtons = document.querySelectorAll(".delete-chore");
    deleteChoreButtons.forEach(function(button) {
        button.addEventListener("click", function() {
            var choreId = this.getAttribute("data-chore-id");

            
            var allChoresTable = document.getElementById("all-chores-table");
            var userChoresTable = document.getElementById("user-chores-table");
            var choreRow = allChoresTable.querySelector("button[data-chore-id='" + choreId + "']").closest("tr");
            choreRow.remove();
            userChores = userChores.filter(function(chore) {
                return chore.id !== choreId;
            });
            localStorage.setItem("userChores", JSON.stringify(userChores));
            renderUserChores();
        });
    });
});
</script>

</body>
</html>
