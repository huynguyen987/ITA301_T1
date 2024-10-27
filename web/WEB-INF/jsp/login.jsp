<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Project Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/user?action=authenticate" method="post">
            <label for="userName">Username:</label>
            <input type="text" name="userName" required>

            <label for="password">Password:</label>
            <input type="password" name="password" required>

            <button type="submit">Login</button>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div style="color: red;"><%= request.getAttribute("errorMessage") %></div>
            <% } %>
        </form>
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/user?action=register">Register here</a></p>
    </div>
</body>
</html>
