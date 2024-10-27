<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - Project Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
    <div class="register-container">
        <h2>Register</h2>
        <form action="${pageContext.request.contextPath}/user?action=create" method="post">
            <label for="fullName">Full Name:</label>
            <input type="text" name="fullName" required>

            <label for="userName">Username:</label>
            <input type="text" name="userName" required>

            <label for="email">Email:</label>
            <input type="email" name="email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <div id="passwordError" style="color: red;"></div>

            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="${pageContext.request.contextPath}/user?action=login">Login here</a></p>

        <!-- Include the JavaScript file for password validation -->
        <script src="${pageContext.request.contextPath}/js/passwordValidation.js"></script>
    </div>
</body>
</html>
