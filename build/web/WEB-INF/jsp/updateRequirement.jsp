<%-- 
    Document   : updateRequirement
    Created on : 28 thg 10, 2024, 14:04:02
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Requirement</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Update Requirement</h2>
    <form action="updateRequirement" method="post">
    <input type="hidden" name="reqId" value="${requirement.reqId}" />

    <label for="title">Title:</label>
    <input type="text" name="title" id="title" value="${requirement.title}" required><br><br>

    <label for="description">Description:</label>
    <textarea name="description" id="description">${requirement.description}</textarea><br><br>

    <!-- Dropdown for Owner Selection (sử dụng ID) -->
    <label for="ownerId">Owner:</label>
    <select name="ownerId" id="ownerId">
        <c:forEach var="user" items="${users}">
            <option value="${user.userId}" ${user.userId == requirement.owner.userId ? 'selected' : ''}>${user.fullName}</option>
        </c:forEach>
    </select><br><br>

    <!-- Dropdown for Complexity Selection (sử dụng ID) -->
    <label for="complexityId">Complexity:</label>
    <select name="complexityId" id="complexityId">
        <c:forEach var="complexity" items="${complexities}">
            <option value="${complexity.settingId}" ${complexity.settingId == requirement.complexity.settingId ? 'selected' : ''}>${complexity.name}</option>
        </c:forEach>
    </select><br><br>

    <!-- Dropdown for Status Selection (sử dụng ID) -->
    <label for="statusId">Status:</label>
    <select name="statusId" id="statusId">
        <c:forEach var="status" items="${statuses}">
            <option value="${status.settingId}" ${status.settingId == requirement.status.settingId ? 'selected' : ''}>${status.name}</option>
        </c:forEach>
    </select><br><br>

    <button type="submit" class="button button-primary">Update Requirement</button>
</form>


</html>
