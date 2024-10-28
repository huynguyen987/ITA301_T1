package controller;

import dao.ProjectDAO;
import model.Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet xử lý các yêu cầu liên quan đến dự án.
 */
@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProjectDAO projectDAO;

    /**
     * Phương thức khởi tạo servlet và khởi tạo ProjectDAO.
     */
    @Override
    public void init() {
        projectDAO = new ProjectDAO();
    }

    /**
     * Xử lý các yêu cầu GET để hiển thị danh sách dự án.
     *
     * @param request  Yêu cầu từ client
     * @param response Phản hồi tới client
     * @throws ServletException Nếu có lỗi servlet
     * @throws IOException      Nếu có lỗi nhập xuất
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy các tham số sắp xếp từ yêu cầu
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String pageStr = request.getParameter("page");
        
        // Thiết lập giá trị mặc định nếu các tham số bị thiếu
        if (sort == null || sort.isEmpty()) {
            sort = "project.project_id";
        }

        if (order == null || order.isEmpty()) {
            order = "ASC";
        }

        // Thiết lập phân trang
        int page = 1;
        int recordsPerPage = 10; // Số bản ghi mỗi trang

        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * recordsPerPage;

        try {
            // Lấy danh sách dự án đã sắp xếp và phân trang từ DAO
            List<Project> projects = projectDAO.getProjects(sort, order, recordsPerPage, offset);
            int totalProjects = projectDAO.getTotalProjects();

            // Tính tổng số trang
            int totalPages = (int) Math.ceil((double) totalProjects / recordsPerPage);

            // Thiết lập các thuộc tính yêu cầu để chuyển sang JSP
            request.setAttribute("projects", projects);
            request.setAttribute("totalProjects", totalProjects);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentSort", sort);
            request.setAttribute("currentOrder", order);

            // Xác định thứ tự sắp xếp tiếp theo để toggle
            String nextOrder = order.equalsIgnoreCase("ASC") ? "DESC" : "ASC";
            request.setAttribute("nextOrder", nextOrder);

            // Chuyển tiếp yêu cầu tới trang JSP
            request.getRequestDispatcher("/WEB-INF/jsp/view_projects.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi một cách thích hợp
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi truy cập cơ sở dữ liệu.");
        }
    }

    /**
     * Xử lý các yêu cầu POST. Trong trường hợp này, POST sẽ chuyển tiếp tới doGet.
     *
     * @param request  Yêu cầu từ client
     * @param response Phản hồi tới client
     * @throws ServletException Nếu có lỗi servlet
     * @throws IOException      Nếu có lỗi nhập xuất
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Có thể xử lý thêm các hành động như tạo mới, cập nhật dự án ở đây
        // Hiện tại, chúng ta sẽ chuyển tiếp POST tới doGet
        doGet(request, response);
    }
}
