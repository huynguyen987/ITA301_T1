<%-- 
    Document   : confirmDelete
    Created on : 28 thg 10, 2024, 14:55:12
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Confirm Delete Requirement</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .confirm-container {
            text-align: center;
            margin-top: 50px;
            color: #dc3545;
        }
        .button {
            margin: 10px;
        }
    </style>
</head>
<body>
    <div class="confirm-container">
        <h2>Confirm Deletion</h2>
        <p>Are you sure you want to delete the following requirement?</p>
        <p><strong>Title:</strong> ${requirement.title}</p>
        <p><strong>Description:</strong> ${requirement.description}</p>
        <p><strong>Owner:</strong> ${requirement.owner.fullName}</p>

        <form action="deleteRequirement" method="post">
            <input type="hidden" name="reqId" value="${requirement.reqId}" />
            <button type="submit" class="button button-danger">Yes, Delete</button>
            <a href="listRequirement" class="button button-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>

