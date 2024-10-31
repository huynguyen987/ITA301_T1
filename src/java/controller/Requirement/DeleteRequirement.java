package controller.Requirement;

import dao.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Requirement;

public class DeleteRequirement {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String method = request.getMethod();
            int reqId = parseIdParameter(request, "reqId");
            if (reqId == -1) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid requirement ID");
                return;
            }

            if (method.equalsIgnoreCase("GET")) {
                handleGet(request, response, reqId);
            } else if (method.equalsIgnoreCase("POST")) {
                handlePost(request, response, reqId);
            } else {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeleteRequirement.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    private void handleGet(HttpServletRequest request, HttpServletResponse response, int reqId)
            throws ServletException, IOException, SQLException {
        Requirement requirement = requirementDAO.getRequirementDetails(reqId);

        if (requirement != null) {
            request.setAttribute("requirement", requirement);
            request.getRequestDispatcher("/WEB-INF/View/Requirement/confirmDelete.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requirement not found");
        }
    }

    private void handlePost(HttpServletRequest request, HttpServletResponse response, int reqId)
            throws ServletException, IOException, SQLException {
        boolean deleted = requirementDAO.deleteRequirement(reqId);

        if (deleted) {
            response.sendRedirect("requirement");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete requirement");
        }
    }

    private int parseIdParameter(HttpServletRequest request, String paramName) {
        try {
            return Integer.parseInt(request.getParameter(paramName));
        } catch (NumberFormatException e) {
            Logger.getLogger(DeleteRequirement.class.getName()).log(Level.WARNING, "Invalid ID format", e);
            return -1;
        }
    }
}
