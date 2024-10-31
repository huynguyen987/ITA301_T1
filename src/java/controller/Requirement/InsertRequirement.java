package controller.Requirement;

import dao.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertRequirement {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String method = request.getMethod();
            if (method.equalsIgnoreCase("GET")) {
                handleGet(request, response);
            } else if (method.equalsIgnoreCase("POST")) {
                handlePost(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InsertRequirement.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    private void handleGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        request.setAttribute("users", requirementDAO.getAllUsers());
        request.setAttribute("complexities", requirementDAO.getComplexitySettings());
        request.setAttribute("statuses", requirementDAO.getStatusSettings());
        request.getRequestDispatcher("/WEB-INF/View/Requirement/insertRequirement.jsp").forward(request, response);
    }

    private void handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int ownerId = parseIdParameter(request, "ownerId");
        int complexityId = parseIdParameter(request, "complexityId");
        int statusId = parseIdParameter(request, "statusId");

        if (ownerId == -1 || complexityId == -1 || statusId == -1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input parameters");
            return;
        }

        boolean inserted = requirementDAO.insertRequirement(title, ownerId, complexityId, statusId, description);
        if (inserted) {
            response.sendRedirect("requirement");
        } else {
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    private int parseIdParameter(HttpServletRequest request, String paramName) {
        try {
            return Integer.parseInt(request.getParameter(paramName));
        } catch (NumberFormatException e) {
            Logger.getLogger(InsertRequirement.class.getName()).log(Level.WARNING, "Invalid ID format", e);
            return -1;
        }
    }
}
