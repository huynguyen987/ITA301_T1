<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Project</title>
    <!-- Bao g?m Bootstrap CSS t? CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bao g?m CSS tùy ch?nh n?u có -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/update-project.css">
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
        <h2 class="mb-4">Update Project</h2>

        <!-- Hi?n th? thông báo l?i n?u có -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/projects?action=updateProject" method="post">
            <!-- Project ID (Không ???c thay ??i) -->
            <div class="mb-3">
                <label for="projectId" class="form-label">Project ID</label>
                <input type="text" class="form-control" id="projectId" name="projectId" value="${project.projectId}" readonly>
            </div>

            <div class="mb-3">
                <label for="name" class="form-label">Project Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${project.name}" required>
            </div>

            <div class="mb-3">
                <label for="code" class="form-label">Project Code</label>
                <input type="text" class="form-control" id="code" name="code" value="${project.code}" required>
            </div>

            <div class="mb-3">
                <label for="startDate" class="form-label">Start Date</label>
                <input type="date" class="form-control" id="startDate" name="startDate" value="${project.startDate}" required>
            </div>

            <div class="mb-3">
                <label for="endDate" class="form-label">End Date</label>
                <input type="date" class="form-control" id="endDate" name="endDate" value="${project.endDate}">
            </div>

            <div class="mb-3">
                <label for="status" class="form-label">Status</label>
                <select class="form-select" id="status" name="status" required>
                    <option value="true" <c:if test="${project.status}">selected</c:if>>Active</option>
                    <option value="false" <c:if test="${!project.status}">selected</c:if>>Inactive</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Project Description</label>
                <textarea class="form-control" id="description" name="description" rows="3">${project.description}</textarea>
            </div>

            <button type="submit" class="btn btn-primary">Update Project</button>
            <a href="${pageContext.request.contextPath}/projects?action=viewProjects" class="btn btn-secondary">Cancel</a>
        </form>
    </div>

    <!-- Bao g?m Bootstrap JS t? CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
