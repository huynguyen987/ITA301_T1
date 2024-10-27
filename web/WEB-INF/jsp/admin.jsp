<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <c:forEach var="user" items="${listUser}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.fullName}</td>
                <td>${user.userName}</td>
                <td>${user.email}</td>
                <td>${user.role.name}</td>
                <td>${user.department.name}</td>
                <td>${user.status ? "Active" : "Inactive"}</td>
                <td>
                    <a href="<c:url value='/user?action=edit&id=${user.userId}' />">Edit</a>
                    |
                    <a href="<c:url value='/user?action=delete&id=${user.userId}' />" onclick="return confirm('Are you sure?')">Delete</a>
                    |
                    <a href="<c:url value='/user?action=${user.status ? 'deactivate' : 'activate'}&id=${user.userId}' />">
                        ${user.status ? "Deactivate" : "Activate"}
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <p><a href="<c:url value='/user?action=new' />">Add New User</a></p>
    <p><a href="<c:url value='/user?action=list' />">Back to User List</a></p>
</body>
</html>
