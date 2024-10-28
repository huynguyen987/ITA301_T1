<%@ page import="java.util.List" %>
<%@ page import="model.Setting" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Settings List</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
        <style>
            /* Inline CSS for a compact, professional look */
            body {
                font-family: Arial, sans-serif;
            }
            .container {
                width: 80%;
                margin: 20px auto;
            }
            .search-box {
                margin-bottom: 15px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .actions a {
                margin-right: 5px;
                text-decoration: none;
                color: #007bff;
            }
            .actions a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Settings List</h2>

            <!-- Search box to filter settings by name or value -->
            <form action="settings" method="get" class="search-box">
                <input type="hidden" name="action" value="search">
                <input type="text" name="keyword" placeholder="Search by name or value" required>
                <button type="submit">Search</button>
                <a href="settings?action=list">Clear Search</a>
            </form>

            <a href="settings?action=new">Add New Setting</a>

            <!-- Settings table -->
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Value</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="setting" items="${listSettings}">
                        <tr>
                            <td>${setting.name}</td>
                            <td>${setting.value}</td>
                            <td>${setting.status ? "Active" : "Inactive"}</td>
                            <td>${setting.description}</td>
                            <td class="actions">
                                <a href="settings?action=edit&id=${setting.settingId}">Edit</a>
                                <a href="settings?action=delete&id=${setting.settingId}" onclick="return confirm('Are you sure?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>