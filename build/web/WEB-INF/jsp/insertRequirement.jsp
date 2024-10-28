<%-- 
    Document   : insertRequirement
    Created on : 28 thg 10, 2024, 14:16:28
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Insert Requirement</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>Add New Requirement</h2>
    <form action="insertRequirement" method="post">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" required><br><br>

        <label for="ownerId">Owner:</label>
        <select name="ownerId" id="ownerId" required>
            <c:forEach var="user" items="${users}">
                <option value="${user.userId}">${user.fullName}</option>
            </c:forEach>
        </select><br><br>

        <label for="complexityId">Complexity:</label>
        <select name="complexityId" id="complexityId" required>
            <c:forEach var="complexity" items="${complexities}">
                <option value="${complexity.settingId}">${complexity.name}</option>
            </c:forEach>
        </select><br><br>

        <label for="statusId">Status:</label>
        <select name="statusId" id="statusId" required>
            <c:forEach var="status" items="${statuses}">
                <option value="${status.settingId}">${status.name}</option>
            </c:forEach>
        </select><br><br>

        <label for="description">Description:</label>
        <textarea name="description" id="description"></textarea><br><br>

        <button type="submit" class="button button-primary">Add Requirement</button>
    </form>
    <br>
    <a href="listRequirement" class="button button-secondary">Back to List</a>
</body>
</html>


