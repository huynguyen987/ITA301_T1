/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlller.User;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author Predator
 */
public class ListUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListUser at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        ArrayList<User> userList = dao.listUser();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/WEB-INF/View/User/ListUser.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();

        // Kiểm tra xem có yêu cầu thay đổi trạng thái (từ AJAX) không
        String userIdParam = request.getParameter("userId");
        String statusParam = request.getParameter("status");

        if (userIdParam != null && statusParam != null) {
            // Nếu có tham số userId và status, xử lý việc thay đổi trạng thái
            int userId = Integer.parseInt(userIdParam);
            int newStatus = Integer.parseInt(statusParam);

            // Cập nhật trạng thái người dùng
            boolean isUpdated = dao.updateUserStatus(userId, newStatus == 1);

            if (isUpdated) {
                response.setStatus(HttpServletResponse.SC_OK); // Thành công
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Thất bại
            }
            return; // Kết thúc xử lý cho AJAX, không thực hiện phần còn lại
        }

        // Nếu không phải là yêu cầu AJAX, thực hiện tìm kiếm và sắp xếp danh sách người dùng
        String department = request.getParameter("department");
        String userName = request.getParameter("userName");

        ArrayList<User> userList;

        // Kiểm tra nếu có ít nhất một trong hai tham số là không rỗng
        if ((department != null && !department.trim().isEmpty())
                || (userName != null && !userName.trim().isEmpty())) {

            userList = dao.searchUser(department, userName);
        } else {
            userList = dao.listUser(); // Lấy danh sách người dùng nếu không có tham số nào
        }
        System.out.println(userList);

        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/WEB-INF/View/User/ListUser.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
