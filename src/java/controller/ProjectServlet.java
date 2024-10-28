package controller;

import dao.ProjectDAO;
import model.Project;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProjectDAO projectDAO;

    public void init() {
        projectDAO = new ProjectDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                action = "viewProjects";
            }

            switch (action) {
                case "addProject":
                    showAddProjectForm(request, response);
                    break;
                case "insertProject":
                    // This action should be handled by doPost
                    response.sendRedirect("projects?action=viewProjects");
                    break;
                case "projectDetails":
                    showProjectDetails(request, response);
                    break;
                case "showUpdateForm":
                    showUpdateProjectForm(request, response);
                    break;
                case "updateProject":
                    // This action should be handled by doPost
                    response.sendRedirect("projects?action=viewProjects");
                    break;
                case "viewProjects":
                default:
                    listProjects(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("insertProject".equals(action)) {
                insertProject(request, response);
            } else if ("updateProject".equals(action)) {
                updateProject(request, response);
            } else if ("toggleStatus".equals(action)) {
                toggleProjectStatus(request, response);
            } else {
                doGet(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProjects(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String search = request.getParameter("search"); // Lấy tham số tìm kiếm

        // Đặt mặc định nếu không có tham số
        if (sort == null || sort.trim().isEmpty()) {
            sort = "project.project_id";
        }
        if (order == null || order.trim().isEmpty()) {
            order = "ASC";
        }

        List<Project> listProjects = projectDAO.selectAllProjects(sort, order, search); // Truyền tham số tìm kiếm
        request.setAttribute("projects", listProjects);

        // Handle pagination variables
        // Để đơn giản, giả định totalPages = 1 và currentPage = 1
        int totalPages = 1;
        int currentPage = 1;

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("currentSort", sort);
        // Toggle order for next sorting
        String nextOrder = "ASC".equalsIgnoreCase(order) ? "DESC" : "ASC";
        request.setAttribute("nextOrder", nextOrder);
        request.setAttribute("search", search); // Truyền lại giá trị tìm kiếm cho JSP

        System.out.println("ListProjects: Fetched " + listProjects.size() + " projects.");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/view_projects.jsp");
        dispatcher.forward(request, response);
    }

    private void showAddProjectForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // Không cần lấy danh sách phòng ban
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/add_project.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String statusStr = request.getParameter("status");
        String description = request.getParameter("description");

        // Log các giá trị nhận được từ form
        System.out.println("Received Parameters:");
        System.out.println("Name: " + name);
        System.out.println("Code: " + code);
        System.out.println("Start Date: " + startDateStr);
        System.out.println("End Date: " + endDateStr);
        System.out.println("Status: " + statusStr);
        System.out.println("Description: " + description);

        // Validate input data (basic validation)
        String errorMessage = null;
        if (name == null || name.trim().isEmpty()) {
            errorMessage = "Project name is required.";
        } else if (code == null || code.trim().isEmpty()) {
            errorMessage = "Project code is required.";
        } else if (startDateStr == null || startDateStr.trim().isEmpty()) {
            errorMessage = "Start date is required.";
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            showAddProjectForm(request, response);
            return;
        }

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = null;
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            endDate = Date.valueOf(endDateStr);
        }
        boolean status = Boolean.parseBoolean(statusStr);

        Project newProject = new Project(name, code, startDate, endDate, status, description);
        projectDAO.insertProject(newProject);
        System.out.println("Inserted new project: " + newProject.getName());

        response.sendRedirect("projects?action=viewProjects");
    }

    private void showProjectDetails(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String projectIdStr = request.getParameter("projectId");
        if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
            // Redirect to list with an error message or handle appropriately
            response.sendRedirect("projects?action=viewProjects");
            return;
        }

        int projectId;
        try {
            projectId = Integer.parseInt(projectIdStr);
        } catch (NumberFormatException e) {
            // Invalid projectId, handle appropriately
            response.sendRedirect("projects?action=viewProjects");
            return;
        }

        Project project = projectDAO.selectProjectById(projectId);
        if (project == null) {
            // Project not found, handle appropriately
            response.sendRedirect("projects?action=viewProjects");
            return;
        }

        request.setAttribute("project", project);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/project_details.jsp");
        dispatcher.forward(request, response);
    }

    private void showUpdateProjectForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String projectIdStr = request.getParameter("projectId");
        if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
            // Redirect to list with an error message or handle appropriately
            response.sendRedirect("projects?action=viewProjects");
            return;
        }

        int projectId;
        try {
            projectId = Integer.parseInt(projectIdStr);
        } catch (NumberFormatException e) {
            // Invalid projectId, handle appropriately
            response.sendRedirect("projects?action=viewProjects");
            return;
        }

        Project project = projectDAO.selectProjectById(projectId);
        if (project == null) {
            // Project not found, handle appropriately
            response.sendRedirect("projects?action=viewProjects");
            return;
        }

        request.setAttribute("project", project);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/update_project.jsp");
        dispatcher.forward(request, response);
    }

    private void updateProject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String projectIdStr = request.getParameter("projectId");
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String statusStr = request.getParameter("status");
        String description = request.getParameter("description");

        // Log các giá trị nhận được từ form
        System.out.println("Received Parameters for Update:");
        System.out.println("Project ID: " + projectIdStr);
        System.out.println("Name: " + name);
        System.out.println("Code: " + code);
        System.out.println("Start Date: " + startDateStr);
        System.out.println("End Date: " + endDateStr);
        System.out.println("Status: " + statusStr);
        System.out.println("Description: " + description);

        // Validate input data (basic validation)
        String errorMessage = null;
        if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
            errorMessage = "Project ID is required.";
        } else if (name == null || name.trim().isEmpty()) {
            errorMessage = "Project name is required.";
        } else if (code == null || code.trim().isEmpty()) {
            errorMessage = "Project code is required.";
        } else if (startDateStr == null || startDateStr.trim().isEmpty()) {
            errorMessage = "Start date is required.";
        }

        int projectId = 0;
        if (errorMessage == null) {
            try {
                projectId = Integer.parseInt(projectIdStr);
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Project ID.";
            }
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            // Lấy lại thông tin dự án để hiển thị trong form
            if (projectId != 0) {
                Project project = projectDAO.selectProjectById(projectId);
                request.setAttribute("project", project);
            }
            showUpdateProjectForm(request, response);
            return;
        }

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = null;
        if (endDateStr != null && !endDateStr.trim().isEmpty()) {
            endDate = Date.valueOf(endDateStr);
        }
        boolean status = Boolean.parseBoolean(statusStr);

        Project updatedProject = new Project(projectId, name, code, startDate, endDate, status, description);
        projectDAO.updateProject(updatedProject);
        System.out.println("Updated project: " + updatedProject.getName());

        response.sendRedirect("projects?action=viewProjects");
    }

    private void toggleProjectStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String projectIdStr = request.getParameter("projectId");
        if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int projectId;
        try {
            projectId = Integer.parseInt(projectIdStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        boolean updated = projectDAO.toggleProjectStatus(projectId);
        if (updated) {
            System.out.println("Toggled status for project ID " + projectId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("Project ID " + projectId + " not found.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
