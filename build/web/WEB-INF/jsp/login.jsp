<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Project Management System</title>
</head>
<body>
    <h2>Login</h2>
    <form action="<c:url value='/user?action=authenticate' />" method="post">
        <label for="userName">Username:</label>
        <input type="text" name="userName" required>
        
        <label for="password">Password:</label>
        <input type="password" name="password" required>
        
        <button type="submit">Login</button>
        
        <!-- Display an error message if login fails -->
        <c:if test="${not empty errorMessage}">
            <div style="color: red;">${errorMessage}</div>
        </c:if>
    </form>
    <p>Don't have an account? <a href="<c:url value='/user?action=register' />">Register here</a></p>
</body>
</html>
