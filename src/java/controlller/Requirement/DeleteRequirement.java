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
import model.Requirement;

/**
 *
 * @author Acer
 */
public class DeleteRequirement extends HttpServlet {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reqId = Integer.parseInt(request.getParameter("reqId"));
        Requirement requirement = requirementDAO.getRequirementDetails(reqId);

        if (requirement != null) {
            request.setAttribute("requirement", requirement);
            request.getRequestDispatcher("/WEB-INF/View/Requirement/confirmDelete.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reqId = Integer.parseInt(request.getParameter("reqId"));
        boolean deleted = requirementDAO.deleteRequirement(reqId);

        if (deleted) {
            response.sendRedirect("listRequirement");
        } else {
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }
}


