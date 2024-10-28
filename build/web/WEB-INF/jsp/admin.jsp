<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page import="model.Setting"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - User Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
    <%@ include file="/WEB-INF/jsp/header.jsp" %>
    <div class="admin-container">
        <h2>Admin Dashboard - User Management</h2>

        <!-- Conditionally render the form if the action is "new" or "edit" -->
        <%
            String action = request.getParameter("action");
            User user = (User) request.getAttribute("user");
            boolean isFormVisible = "new".equals(action) || "edit".equals(action);
        %>

        <% if (isFormVisible) { %>
            <!-- Add/Edit User Form -->
            <h3><%= (user == null) ? "Add New User" : "Edit User" %></h3>
            <form action="${pageContext.request.contextPath}/user" method="post">
                <input type="hidden" name="action" value="<%= (user == null) ? "insert" : "update" %>">
                <% if (user != null) { %>
                    <input type="hidden" name="id" value="<%= user.getUserId() %>">
                <% } %>

                <label for="fullName">Full Name:</label>
                <input type="text" name="fullName" value="<%= user != null ? user.getFullName() : "" %>" required>

                <label for="userName">Username:</label>
                <input type="text" name="userName" value="<%= user != null ? user.getUserName() : "" %>" required>

                <label for="email">Email:</label>
                <input type="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" required>

                <label for="password">Password:</label>
                <input type="password" name="password" <%= (user == null) ? "required" : "" %>>

                <label for="roleId">Role:</label>
                <select name="roleId">
                    <option value="">Select Role</option>
                    <% 
                        List<Setting> roles = (List<Setting>) request.getAttribute("roles");
                        for (Setting role : roles) { 
                    %>
                        <option value="<%= role.getSettingId() %>" <%= (user != null && user.getRole() != null && user.getRole().getSettingId() == role.getSettingId()) ? "selected" : "" %>>
                            <%= role.getName() %>
                        </option>
                    <% } %>
                </select>

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

                <label for="startDate">Start Date:</label>
                <input type="date" name="startDate" value="<%= user != null ? user.getStartDate() : "" %>">

                <label for="status">Status:</label>
                <input type="checkbox" name="status" value="true" <%= user != null && user.isStatus() ? "checked" : "" %>>

                <label for="note">Note:</label>
                <textarea name="note"><%= user != null ? user.getNote() : "" %></textarea>

                <button type="submit"><%= (user == null) ? "Add User" : "Update User" %></button>
                <a href="${pageContext.request.contextPath}/user?action=admin">Cancel</a>
            </form>
        <% } else { %>
            <!-- Display User List -->
            <table>
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
                        for (User u : listUser) { 
                    %>
                        <tr>
                            <td><%= u.getUserId() %></td>
                            <td><%= u.getFullName() %></td>
                            <td><%= u.getUserName() %></td>
                            <td><%= u.getEmail() %></td>
                            <td><%= (u.getRole() != null) ? u.getRole().getName() : "N/A" %></td>
                            <td><%= (u.getDepartment() != null) ? u.getDepartment().getName() : "N/A" %></td>
                            <td><%= u.isStatus() ? "Active" : "Inactive" %></td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/user?action=edit&id=<%= u.getUserId() %>">Edit</a>
                                <a href="${pageContext.request.contextPath}/user?action=delete&id=<%= u.getUserId() %>" onclick="return confirm('Are you sure?')">Delete</a>
                                <a href="${pageContext.request.contextPath}/user?action=<%= u.isStatus() ? "deactivate" : "activate" %>&id=<%= u.getUserId() %>">
                                    <%= u.isStatus() ? "Deactivate" : "Activate" %>
                                </a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <p><a href="${pageContext.request.contextPath}/user?action=new">Add New User</a></p>
        <% } %>
    </div>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
</body>
</html>
