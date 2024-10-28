<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project Details</title>
    <!-- Bao g?m Bootstrap CSS t? CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bao g?m CSS tùy ch?nh n?u có -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/project-details.css">
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/user?action=userDashboard">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user?action=logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- N?i dung chính -->
    <div class="container mt-5">
        <h2>Project Details</h2>
        <hr>

        <div class="row">
            <div class="col-md-6">
                <h5>Project ID:</h5>
                <p>${project.projectId}</p>
            </div>
            <div class="col-md-6">
                <h5>Project Name:</h5>
                <p>${project.name}</p>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <h5>Project Code:</h5>
                <p>${project.code}</p>
            </div>
            <div class="col-md-6">
                <h5>Start Date:</h5>
                <p>${project.startDate}</p>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <h5>End Date:</h5>
                <p>
                    <c:if test="${not empty project.endDate}">
                        ${project.endDate}
                    </c:if>
                    <c:if test="${empty project.endDate}">
                        N/A
                    </c:if>
                </p>
            </div>
            <div class="col-md-6">
                <h5>Status:</h5>
                <p>
                    <c:choose>
                        <c:when test="${project.status}">
                            <span class="badge bg-success">Active</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-secondary">Inactive</span>
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <h5>Description:</h5>
                <p>
                    <c:if test="${not empty project.description}">
                        ${project.description}
                    </c:if>
                    <c:if test="${empty project.description}">
                        No description provided.
                    </c:if>
                </p>
            </div>
        </div>

        <!-- Nút Update -->
        <a href="${pageContext.request.contextPath}/projects?action=showUpdateForm&projectId=${project.projectId}" class="btn btn-primary mt-3">Update Project</a>
        <a href="${pageContext.request.contextPath}/projects?action=viewProjects" class="btn btn-secondary mt-3">Back to Project List</a>
    </div>

    <!-- Bao g?m Bootstrap JS t? CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
