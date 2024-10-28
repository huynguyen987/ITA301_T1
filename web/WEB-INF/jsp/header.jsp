<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
    <header class="navbar">
        <div class="navbar-logo">
            <h1>Admin Dashboard</h1>
        </div>
        <nav class="navbar-links">
            <ul>
                <li><a href="${pageContext.request.contextPath}/user?action=admin">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/user?action=list">Manage Users</a></li>
                <li><a href="${pageContext.request.contextPath}/user?action=new">Add New User</a></li>
                <li><a href="${pageContext.request.contextPath}/user?action=logout">Logout</a></li>
            </ul>
        </nav>
        <div class="welcome-message">
            <p>Welcome, Administrator!</p>
        </div>
    </header>
</body>
</html>
