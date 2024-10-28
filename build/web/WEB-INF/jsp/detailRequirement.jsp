<%-- 
    Document   : detailRequirement
    Created on : 28 thg 10, 2024, 14:17:04
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Requirement Details</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Requirement Details</h2>
    <p><strong>Title:</strong> ${requirement.title}</p>
    <p><strong>Owner:</strong> ${requirement.owner.fullName}</p>
    <p><strong>Complexity:</strong> ${requirement.complexity.name}</p>
    <p><strong>Status:</strong> ${requirement.status.name}</p>
    <p><strong>Description:</strong> ${requirement.description}</p>
    <p><strong>Created At:</strong> ${requirement.createdAt}</p>
    <p><strong>Created By:</strong> ${requirement.createdById.fullName}</p>
    <p><strong>Updated At:</strong> ${requirement.updatedAt}</p>
    <p><strong>Updated By:</strong> ${requirement.updatedById.fullName}</p>
    <br>
    <a href="listRequirement" class="button button-secondary">Back to List</a>
</body>
</html>


