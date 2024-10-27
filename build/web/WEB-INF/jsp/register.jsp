<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Project Management System</title>
</head>
<body>
    <h2>Register</h2>
    <form action="${pageContext.request.contextPath}/user?action=create" method="post">
        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" required>
        
        <label for="userName">Username:</label>
        <input type="text" name="userName" required>
        
        <label for="email">Email:</label>
        <input type="email" name="email" required>
        
        <label for="password">Password:</label>
        <input type="password" name="password" required>
        
        <button type="submit">Register</button>
    </form>
    <p>Already have an account? <a href="${pageContext.request.contextPath}/user?action=login">Login here</a></p>
</body>
</html>
