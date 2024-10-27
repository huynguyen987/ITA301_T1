<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - Project Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="index-container">
        <h1>Welcome to the Project Management System</h1>
        <p>Please log in or register to continue.</p>

        <a href="${pageContext.request.contextPath}/user?action=login" class="button">Login</a>
        <a href="${pageContext.request.contextPath}/user?action=register" class="button">Register</a>
    </div>
</body>
</html>
