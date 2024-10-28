<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách D? Án</title>
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
        .sort-indicator {
            margin-left: 5px;
            font-size: 0.8em;
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
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>List project</h2>
            <a href="${pageContext.request.contextPath}/projects?action=addProject" class="btn btn-success">Add new project</a>
        </div>

        <!-- Thanh Tìm Ki?m -->
        <form class="row g-3 mb-4" method="get" action="${pageContext.request.contextPath}/projects">
            <input type="hidden" name="action" value="viewProjects" />
            <div class="col-auto">
                <input type="text" class="form-control" name="search" placeholder="Search by project name" value="${param.search}">
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3">Search</button>
            </div>
        </form>

        <!-- B?ng danh sách d? án -->
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">
                            <a href="projects?sort=project.project_id&order=${currentSort == 'project.project_id' ? nextOrder : 'ASC'}&search=${param.search}" class="text-white text-decoration-none">
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
                            <a href="projects?sort=project.name&order=${currentSort == 'project.name' ? nextOrder : 'ASC'}&search=${param.search}" class="text-white text-decoration-none">
                                Project Name
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
                            <a href="projects?sort=project.code&order=${currentSort == 'project.code' ? nextOrder : 'ASC'}&search=${param.search}" class="text-white text-decoration-none">
                                Project Code
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
                            <a href="projects?sort=project.start_date&order=${currentSort == 'project.start_date' ? nextOrder : 'ASC'}&search=${param.search}" class="text-white text-decoration-none">
                                Start Day
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
                            <a href="projects?sort=project.end_date&order=${currentSort == 'project.end_date' ? nextOrder : 'ASC'}&search=${param.search}" class="text-white text-decoration-none">
                                End Day
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
                        <th scope="col">Status</th>
                        <th scope="col">Describe</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty projects}">
                            <c:forEach var="project" items="${projects}">
                                <tr>
                                    <td>${project.projectId}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/projects?action=projectDetails&projectId=${project.projectId}" class="text-decoration-none">
                                            ${project.name}
                                        </a>
                                    </td>
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
                                        <!-- Nút Status Nh?p ???c -->
                                        <button type="button" class="btn btn-link p-0 status-button" data-project-id="${project.projectId}">
                                            <c:choose>
                                                <c:when test="${project.status}">
                                                    <span class="badge bg-success">Active</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">Inactive</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </button>
                                    </td>
                                    <td>
                                        <c:if test="${not empty project.description}">
                                            ${project.description}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="7" class="text-center">Không tìm th?y d? án nào.</td>
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
                                <a class="page-link" href="projects?sort=${currentSort}&order=${currentOrder}&page=${i}&search=${param.search}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </nav>
    </div>

    <!-- Modal Xác Nh?n Thay ??i Tr?ng Thái -->
    <div class="modal fade" id="confirmStatusModal" tabindex="-1" aria-labelledby="confirmStatusModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Xác Nh?n Thay ??i Tr?ng Thái</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    B?n có mu?n thay ??i tr?ng thái c?a d? án này?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary" id="confirmYesButton">Yes</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bao g?m jQuery và Bootstrap JS t? CDN -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" 
            integrity="sha256-/xUj+3OJh0Ctt5zz7jBqBjJz9D9kFn1Hw5PZTdlF0X0=" 
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            var projectIdToUpdate = null;
            var confirmStatusModal = new bootstrap.Modal(document.getElementById('confirmStatusModal'), {
                keyboard: false
            });

            // Khi ng??i dùng nh?p vào nút Status
            $('.status-button').on('click', function() {
                projectIdToUpdate = $(this).data('project-id');
                console.log("Clicked project ID: " + projectIdToUpdate); // Thêm dòng này ?? ki?m tra
                confirmStatusModal.show();
            });

            // Khi ng??i dùng nh?p vào nút Yes trong modal
            $('#confirmYesButton').on('click', function() {
                console.log("Confirm Yes clicked"); // Thêm dòng này ?? ki?m tra
                if (projectIdToUpdate !== null) {
                    // G?i yêu c?u ??n server ?? thay ??i tr?ng thái
                    $.ajax({
                        url: '${pageContext.request.contextPath}/projects',
                        type: 'POST',
                        data: {
                            action: 'toggleStatus',
                            projectId: projectIdToUpdate
                        },
                        success: function(response) {
                            console.log("Status toggled successfully"); // Thêm dòng này ?? ki?m tra
                            // Reload l?i trang ?? c?p nh?t tr?ng thái
                            location.reload();
                        },
                        error: function(xhr, status, error) {
                            console.error('Error toggling status:', error); // Thêm dòng này ?? ki?m tra
                            alert('?ã x?y ra l?i khi thay ??i tr?ng thái d? án.');
                        }
                    });
                }
                confirmStatusModal.hide();
            });
        });
    </script>
</body>
</html>
