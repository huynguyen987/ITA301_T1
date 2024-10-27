<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - Project Management System</title>
</head>
<body>
    <h1>Welcome to the Project Management System</h1>
    <p>Please log in or register to continue.</p>

    <!-- Login and Register links using c:url to create absolute paths -->
    <a href="<c:url value='/user?action=login'/>">Login</a>
    <a href="<c:url value='/user?action=register'/>">Register</a>
</body>
</html>
