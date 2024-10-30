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

/**
 *
 * @author Acer
 */
public class SearchRequirement extends HttpServlet {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        if (title != null && !title.isEmpty()) {
            List<Requirement> requirements = requirementDAO.searchRequirementsByTitle(title);
            request.setAttribute("requirements", requirements);
        }
        request.getRequestDispatcher("/WEB-INF/View/Requirement/listRequirement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Gọi lại doGet để hiển thị kết quả tìm kiếm
    }
}