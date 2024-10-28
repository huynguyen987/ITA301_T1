<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project List</title>
    <!-- Bao g?m Bootstrap CSS t? CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bao g?m CSS tùy ch?nh n?u có -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/view-projects.css">
    <style>
        /* ??nh d?ng cho các badge */
        .badge-active {
            background-color: #28a745;
        }
        .badge-inactive {
            background-color: #6c757d;
        }
    </style>
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
        <h2 class="mb-4">Project List</h2>

        <!-- B?ng danh sách d? án -->
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">
                            <a href="projects?sort=project.project_id&order=${currentSort == 'project.project_id' ? nextOrder : 'ASC'}" class="text-white text-decoration-none">
                                ID
                                <span class="sort-indicator">
                                    <c:if test="${currentSort == 'project.project_id'}">
                                        <c:choose>
                                            <c:when test="${currentOrder == 'ASC'}">?</c:when>
                                            <c:otherwise>?</c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </span>
                            </a>
                        </th>
                        <th scope="col">
                            <a href="projects?sort=project.name&order=${currentSort == 'project.name' ? nextOrder : 'ASC'}" class="text-white text-decoration-none">
                                Name
                                <span class="sort-indicator">
                                    <c:if test="${currentSort == 'project.name'}">
                                        <c:choose>
                                            <c:when test="${currentOrder == 'ASC'}">?</c:when>
                                            <c:otherwise>?</c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </span>
                            </a>
                        </th>
                        <th scope="col">
                            <a href="projects?sort=project.code&order=${currentSort == 'project.code' ? nextOrder : 'ASC'}" class="text-white text-decoration-none">
                                Code
                                <span class="sort-indicator">
                                    <c:if test="${currentSort == 'project.code'}">
                                        <c:choose>
                                            <c:when test="${currentOrder == 'ASC'}">?</c:when>
                                            <c:otherwise>?</c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </span>
                            </a>
                        </th>
                        <th scope="col">
                            <a href="projects?sort=project.start_date&order=${currentSort == 'project.start_date' ? nextOrder : 'ASC'}" class="text-white text-decoration-none">
                                Start Date
                                <span class="sort-indicator">
                                    <c:if test="${currentSort == 'project.start_date'}">
                                        <c:choose>
                                            <c:when test="${currentOrder == 'ASC'}">?</c:when>
                                            <c:otherwise>?</c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </span>
                            </a>
                        </th>
                        <th scope="col">
                            <a href="projects?sort=project.end_date&order=${currentSort == 'project.end_date' ? nextOrder : 'ASC'}" class="text-white text-decoration-none">
                                End Date
                                <span class="sort-indicator">
                                    <c:if test="${currentSort == 'project.end_date'}">
                                        <c:choose>
                                            <c:when test="${currentOrder == 'ASC'}">?</c:when>
                                            <c:otherwise>?</c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </span>
                            </a>
                        </th>
                        <th scope="col">
                            <a href="projects?sort=project.status&order=${currentSort == 'project.status' ? nextOrder : 'ASC'}" class="text-white text-decoration-none">
                                Status
                                <span class="sort-indicator">
                                    <c:if test="${currentSort == 'project.status'}">
                                        <c:choose>
                                            <c:when test="${currentOrder == 'ASC'}">?</c:when>
                                            <c:otherwise>?</c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </span>
                            </a>
                        </th>
                        <th scope="col">Description</th>
                        <th scope="col">Department</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty projects}">
                            <c:forEach var="project" items="${projects}">
                                <tr>
                                    <td>${project.projectId}</td>
                                    <td>${project.name}</td>
                                    <td>${project.code}</td>
                                    <td>
                                        <c:if test="${not empty project.startDate}">
                                            ${project.startDate}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty project.endDate}">
                                            ${project.endDate}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${project.status}">
                                                <span class="badge bg-success">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${not empty project.description}">
                                            ${project.description}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty project.department}">
                                            ${project.department.name}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="7" class="text-center">No projects found.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <!-- Phân trang -->
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <li class="page-item active">
                                <a class="page-link" href="#">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="projects?sort=${currentSort}&order=${currentOrder}&page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </nav>
    </div>

    <!-- Bao g?m Bootstrap JS t? CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
