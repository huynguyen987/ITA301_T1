<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Dashboard</title>
    <!-- Bao g?m Bootstrap CSS t? CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bao g?m CSS t?y ch?nh n?u c? -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-dashboard.css">
</head>
<body>
    <!-- Thanh ?i?u h??ng -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">MPMS</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user?action=logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- N?i dung ch?nh -->
    <div class="container mt-5 text-center">
        <h1 class="mb-4">Welcome to Your Dashboard</h1>
        <p class="lead">Welcome, <strong>${loggedUser.fullName}</strong>!</p>
        <p>This is your dashboard. Below are the available options:</p>

        <!-- C?c n?t ?i?u h??ng -->
        <div class="row mt-4">
            <div class="col-md-4 mb-3">
                <a href="${pageContext.request.contextPath}/projects" class="btn btn-primary btn-lg w-100">View All Projects</a>
            </div>
            <div class="col-md-4 mb-3">
                <a href="${pageContext.request.contextPath}/user?action=viewProfile" class="btn btn-secondary btn-lg w-100">View Profile</a>
            </div>
            <div class="col-md-4 mb-3">
                <a href="${pageContext.request.contextPath}/user?action=editProfile" class="btn btn-info btn-lg w-100">Edit Profile</a>
            </div>
            <div class="col-md-4 mb-3">
                <a href="${pageContext.request.contextPath}/settings" class="btn btn-info btn-lg w-100">View Setting</a>
            </div>
            <div class="col-md-4 mb-3">
                <a href="requirement" class="btn btn-info btn-lg w-100">View Requirement</a>
            </div>
            <!-- Th?m c?c n?t kh?c n?u c?n -->
        </div>
    </div>

    <!-- Bao g?m Bootstrap JS t? CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
