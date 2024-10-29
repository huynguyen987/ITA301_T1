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

/**
 *
 * @author mituz
 */
public class ListRequirement extends HttpServlet {
    private final RequirementDAO requirementDAO = new RequirementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy tất cả các requirements
            List<Requirement> requirements = requirementDAO.listAllRequirements();
            request.setAttribute("requirements", requirements);

            // Lấy danh sách complexities và statuses từ SettingDAO cho bộ lọc
            List<Setting> complexities = requirementDAO.getComplexitySettings();
            List<Setting> statuses = requirementDAO.getStatusSettings();
            request.setAttribute("complexities", complexities);
            request.setAttribute("statuses", statuses);

            // Thiết lập giá trị mặc định cho complexityId và statusId (khi không có lọc)
            request.setAttribute("selectedComplexityId", -1); // -1 biểu thị "All"
            request.setAttribute("selectedStatusId", -1);

            // Chuyển tiếp đến trang listRequirement.jsp
            request.getRequestDispatcher("/WEB-INF/View/Requirement/listRequirement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/View/Error/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Gọi doGet để xử lý cả các yêu cầu POST
    }
}
