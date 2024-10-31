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

public class DetailRequirement {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int reqId = parseIdParameter(request, "reqId");
            if (reqId == -1) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid requirement ID");
                return;
            }

            Requirement requirement = requirementDAO.getRequirementDetails(reqId);
            if (requirement != null) {
                request.setAttribute("requirement", requirement);
                request.getRequestDispatcher("/WEB-INF/View/Requirement/detailRequirement.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requirement not found");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailRequirement.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    private int parseIdParameter(HttpServletRequest request, String paramName) {
        try {
            return Integer.parseInt(request.getParameter(paramName));
        } catch (NumberFormatException e) {
            Logger.getLogger(DetailRequirement.class.getName()).log(Level.WARNING, "Invalid ID format", e);
            return -1;
        }
    }
}
