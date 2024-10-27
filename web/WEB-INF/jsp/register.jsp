<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Project Management System</title>
</head>
<body>
    <h2>Register</h2>
    <form action="<c:url value='/user?action=create' />" method="post">
        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" required>
        
        <label for="userName">Username:</label>
        <input type="text" name="userName" required>
        
        <label for="email">Email:</label>
        <input type="email" name="email" required>
        
        <label for="password">Password:</label>
        <input type="password" name="password" required>
        
        <label for="roleId">Role:</label>
        <select name="roleId" required>
            <c:forEach var="role" items="${roles}">
                <option value="${role.settingId}">${role.name}</option>
            </c:forEach>
        </select>
        
        <label for="deptId">Department:</label>
        <select name="deptId" required>
            <c:forEach var="department" items="${departments}">
                <option value="${department.settingId}">${department.name}</option>
            </c:forEach>
        </select>
        
        <label for="startDate">Start Date:</label>
        <input type="date" name="startDate" required>
        
        <label for="status">Active:</label>
        <input type="checkbox" name="status" checked>
        
        <label for="note">Note:</label>
        <textarea name="note"></textarea>
        
        <button type="submit">Register</button>
    </form>
    <p>Already have an account? <a href="<c:url value='/user?action=login' />">Login here</a></p>
</body>
</html>
