<%-- 
    Document   : listRequirement
    Created on : 28 thg 10, 2024, 14:13:12
    Author     : mituz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List of Requirements</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h2>List of Requirements</h2>

    <!-- Search Form -->
    <form action="searchRequirement" method="get">
        <label for="searchTitle">Search by Title:</label>
        <input type="text" name="title" id="searchTitle" placeholder="Enter title to search" />
        <button type="submit" class="button button-primary">Search</button>
    </form>
    
    <!-- Filter Form -->
    <form action="filterRequirement" method="get">
    <label for="complexityId">Filter by Complexity:</label>
    <select name="complexityId" id="complexityId">
        <option value="-1" ${selectedComplexityId == -1 ? 'selected' : ''}>All</option>
        <c:forEach var="complexity" items="${complexities}">
            <option value="${complexity.settingId}" ${complexity.settingId == selectedComplexityId ? 'selected' : ''}>${complexity.name}</option>
        </c:forEach>
    </select>

    <label for="statusId">Filter by Status:</label>
    <select name="statusId" id="statusId">
        <option value="-1" ${selectedStatusId == -1 ? 'selected' : ''}>All</option>
        <c:forEach var="status" items="${statuses}">
            <option value="${status.settingId}" ${status.settingId == selectedStatusId ? 'selected' : ''}>${status.name}</option>
        </c:forEach>
    </select>

    <button type="submit" class="button button-warning">Filter</button>
</form>


    <br>

    <!-- Table of Requirements -->
    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Owner</th>
            <th>Complexity</th>
            <th>Status</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="requirement" items="${requirements}">
            <tr>
                <td>${requirement.reqId}</td>
                <td>${requirement.title}</td>
                <td>${requirement.owner.fullName}</td>
                <td>${requirement.complexity.name}</td>
                <td>${requirement.status.name}</td>
                <td>${requirement.description}</td>
                <td>
                    <a href="detailRequirement?reqId=${requirement.reqId}" class="button button-secondary">View</a>
                    <a href="updateRequirement?reqId=${requirement.reqId}" class="button button-primary">Edit</a>
                    <form action="deleteRequirement" method="post" style="display:inline;">
                        <input type="hidden" name="reqId" value="${requirement.reqId}" />
                        <button type="submit" class="button button-danger" onclick="return confirm('Are you sure you want to delete this requirement?')">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <a href="insertRequirement" class="button button-primary">Add New Requirement</a>
</body>
</html>
