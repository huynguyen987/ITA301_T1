<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page import="model.Setting"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Management - User List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> <!-- Link to your CSS if needed -->
</head>
<body>
    <h2>User Management - User List</h2>
    
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>User ID</th>
                <th>Full Name</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Department</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<User> listUser = (List<User>) request.getAttribute("listUser");
                for (User user : listUser) { 
            %>
                <tr>
                    <td><%= user.getUserId() %></td>
                    <td><%= user.getFullName() %></td>
                    <td><%= user.getUserName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= (user.getRole() != null) ? user.getRole().getName() : "N/A" %></td>
                    <td><%= (user.getDepartment() != null) ? user.getDepartment().getName() : "N/A" %></td>
                    <td><%= user.isStatus() ? "Active" : "Inactive" %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/user?action=edit&id=<%= user.getUserId() %>">Edit</a> |
                        <a href="${pageContext.request.contextPath}/user?action=delete&id=<%= user.getUserId() %>" onclick="return confirm('Are you sure?')">Delete</a> |
                        <a href="${pageContext.request.contextPath}/user?action=<%= user.isStatus() ? "deactivate" : "activate" %>&id=<%= user.getUserId() %>">
                            <%= user.isStatus() ? "Deactivate" : "Activate" %>
                        </a>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <p><a href="${pageContext.request.contextPath}/user?action=new">Add New User</a></p>
    <p><a href="${pageContext.request.contextPath}/user?action=admin">Back to Admin Dashboard</a></p>
</body>
</html>
