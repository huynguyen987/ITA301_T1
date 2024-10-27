<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page import="model.Setting"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= request.getAttribute("user") == null ? "Add New User" : "Edit User" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> <!-- Link to your CSS if needed -->
</head>
<body>
    <h2><%= request.getAttribute("user") == null ? "Add New User" : "Edit User" %></h2>

    <form action="${pageContext.request.contextPath}/user" method="post">
        <input type="hidden" name="action" value="<%= request.getAttribute("user") == null ? "insert" : "update" %>">
        <% if (request.getAttribute("user") != null) { %>
            <input type="hidden" name="id" value="<%= ((User) request.getAttribute("user")).getUserId() %>">
        <% } %>

        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getFullName() : "" %>" required>
        <br>

        <label for="userName">Username:</label>
        <input type="text" name="userName" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getUserName() : "" %>" required>
        <br>

        <label for="email">Email:</label>
        <input type="email" name="email" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getEmail() : "" %>" required>
        <br>

        <label for="password">Password:</label>
        <input type="password" name="password" <%= request.getAttribute("user") == null ? "required" : "" %>>
        <br>

        <label for="roleId">Role:</label>
        <select name="roleId">
            <option value="">Select Role</option>
            <% 
                List<Setting> roles = (List<Setting>) request.getAttribute("roles");
                User user = (User) request.getAttribute("user");
                for (Setting role : roles) { 
            %>
                <option value="<%= role.getSettingId() %>" <%= (user != null && user.getRole() != null && user.getRole().getSettingId() == role.getSettingId()) ? "selected" : "" %>>
                    <%= role.getName() %>
                </option>
            <% } %>
        </select>
        <br>

        <label for="deptId">Department:</label>
        <select name="deptId">
            <option value="">Select Department</option>
            <% 
                List<Setting> departments = (List<Setting>) request.getAttribute("departments");
                for (Setting department : departments) { 
            %>
                <option value="<%= department.getSettingId() %>" <%= (user != null && user.getDepartment() != null && user.getDepartment().getSettingId() == department.getSettingId()) ? "selected" : "" %>>
                    <%= department.getName() %>
                </option>
            <% } %>
        </select>
        <br>

        <label for="startDate">Start Date:</label>
        <input type="date" name="startDate" value="<%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getStartDate() : "" %>">
        <br>

        <label for="status">Status:</label>
        <input type="checkbox" name="status" value="true" <%= request.getAttribute("user") != null && ((User) request.getAttribute("user")).isStatus() ? "checked" : "" %>>
        <br>

        <label for="note">Note:</label>
        <textarea name="note"><%= request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getNote() : "" %></textarea>
        <br>

        <button type="submit"><%= request.getAttribute("user") == null ? "Add User" : "Update User" %></button>
        <a href="${pageContext.request.contextPath}/user?action=admin">Cancel</a>
    </form>
</body>
</html>
