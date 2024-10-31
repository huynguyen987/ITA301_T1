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
import java.util.List;

public class SearchRequirement {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");

        try {
            if (title != null && !title.isEmpty()) {
                List<Requirement> requirements = requirementDAO.searchRequirementsByTitle(title);
                request.setAttribute("requirements", requirements);
            }
            request.getRequestDispatcher("/WEB-INF/View/Requirement/listRequirement.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SearchRequirement.class.getName()).log(Level.SEVERE, null, ex);
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }
}
