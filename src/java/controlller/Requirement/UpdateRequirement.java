/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controlller.Requirement;

import dao.RequirementDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Requirement;
import model.Setting;
import model.User;

/**
 *
 * @author Acer
 */
public class UpdateRequirement extends HttpServlet {
  private final RequirementDAO requirementDAO = new RequirementDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reqId = Integer.parseInt(request.getParameter("reqId"));
        Requirement requirement = requirementDAO.getRequirementDetails(reqId);

        if (requirement != null) {
            List<Setting> complexities = requirementDAO.getComplexitySettings();
            List<Setting> statuses = requirementDAO.getStatusSettings();
            List<User> users = requirementDAO.getAllUsers();

            request.setAttribute("requirement", requirement);
            request.setAttribute("complexities", complexities);
            request.setAttribute("statuses", statuses);
            request.setAttribute("users", users);

            request.getRequestDispatcher("/WEB-INF/View/Requirement/updateRequirement.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
            int reqId = Integer.parseInt(request.getParameter("reqId"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            int ownerId = Integer.parseInt(request.getParameter("ownerId"));
            int complexityId = Integer.parseInt(request.getParameter("complexityId"));
            int statusId = Integer.parseInt(request.getParameter("statusId"));
            System.out.println(reqId);
            System.out.println(title);
            System.out.println(description);
            System.out.println(ownerId);
            System.out.println(complexityId);
            System.out.println(statusId);
            boolean updated = requirementDAO.updateRequirement(reqId, title, ownerId, description, complexityId, statusId);
            System.out.println(updated);
            if (updated) {
                response.sendRedirect("listRequirement");
            } else {
                request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
            }
    }

}