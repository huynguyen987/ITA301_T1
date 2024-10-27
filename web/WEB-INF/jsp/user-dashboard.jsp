<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-dashboard.css">
</head>
<body>
    <h1>Welcome to Your Dashboard</h1>
    <p>Welcome, ${loggedUser.fullName}!</p>
    <p>This is the user dashboard. Features and options available to you will depend on your role.</p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/user?action=viewProfile">View Profile</a></li>
        <li><a href="${pageContext.request.contextPath}/user?action=viewProjects">View Projects</a></li>
        <!-- Add more user-specific links here -->
    </ul>
</body>
</html>
