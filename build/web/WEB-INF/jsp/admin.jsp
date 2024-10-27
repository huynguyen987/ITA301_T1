<%@page import="java.util.List"%>
<%@page import="model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - User Management</title>
</head>
<body>
    <h2>Admin Dashboard - User Management</h2>
    
    <table border="1">
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
        <% for (User user : (List<User>) request.getAttribute("listUser")) { %>
            <tr>
                <td><%= user.getUserId() %></td>
                <td><%= user.getFullName() %></td>
                <td><%= user.getUserName() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getRole() != null ? user.getRole().getName() : "N/A" %></td>
                <td><%= user.getDepartment() != null ? user.getDepartment().getName() : "N/A" %></td>
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
    </table>

    <p><a href="${pageContext.request.contextPath}/user?action=new">Add New User</a></p>
    <p><a href="${pageContext.request.contextPath}/user?action=list">Back to User List</a></p>
</body>
</html>
