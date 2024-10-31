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

public class ListRequirement {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Requirement> requirements = requirementDAO.listAllRequirements();
            List<Setting> complexities = requirementDAO.getComplexitySettings();
            List<Setting> statuses = requirementDAO.getStatusSettings();

            request.setAttribute("requirements", requirements);
            request.setAttribute("complexities", complexities);
            request.setAttribute("statuses", statuses);
            request.setAttribute("selectedComplexityId", -1);
            request.setAttribute("selectedStatusId", -1);

            request.getRequestDispatcher("/WEB-INF/View/Requirement/listRequirement.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ListRequirement.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }
}
