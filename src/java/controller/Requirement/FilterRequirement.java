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
import model.Setting;
import java.util.List;

public class FilterRequirement {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int complexityId = parseIdParameter(request, "complexityId");
            int statusId = parseIdParameter(request, "statusId");

            if (complexityId == -1 || statusId == -1) { //Tu m code cho no ko ra ma loi gi em
                response.sendRedirect("requirement");
                return;
            }

            List<Requirement> requirements = requirementDAO.filterRequirements(complexityId, statusId);
            List<Setting> complexities = requirementDAO.getComplexitySettings();
            List<Setting> statuses = requirementDAO.getStatusSettings();

            request.setAttribute("requirements", requirements);
            request.setAttribute("complexities", complexities);
            request.setAttribute("statuses", statuses);
            request.setAttribute("selectedComplexityId", complexityId);
            request.setAttribute("selectedStatusId", statusId);

            request.getRequestDispatcher("/WEB-INF/View/Requirement/listRequirement.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FilterRequirement.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    private int parseIdParameter(HttpServletRequest request, String paramName) {
        try {
            return Integer.parseInt(request.getParameter(paramName));
        } catch (NumberFormatException e) {
            Logger.getLogger(FilterRequirement.class.getName()).log(Level.WARNING, "Invalid ID format", e);
            return -1;
        }
    }
}
