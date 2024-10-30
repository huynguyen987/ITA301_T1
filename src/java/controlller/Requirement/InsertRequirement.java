package controlller.Requirement;

import dao.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



/**
 *
 * @author Acer
 */
public class InsertRequirement extends HttpServlet {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt các danh sách cần thiết cho dropdown (users, complexities, statuses)
        request.setAttribute("users", requirementDAO.getAllUsers());
        request.setAttribute("complexities", requirementDAO.getComplexitySettings());
        request.setAttribute("statuses", requirementDAO.getStatusSettings());

        request.getRequestDispatcher("/WEB-INF/View/Requirement/insertRequirement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        int ownerId = Integer.parseInt(request.getParameter("ownerId"));
        int complexityId = Integer.parseInt(request.getParameter("complexityId"));
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        String description = request.getParameter("description");

        boolean inserted = requirementDAO.insertRequirement(title, ownerId, complexityId, statusId, description);

        if (inserted) {
            response.sendRedirect("listRequirement");
        } else {
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }
}

