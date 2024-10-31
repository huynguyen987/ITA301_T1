package controller;

import service.UserService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/user")
public class UserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Delegate POST to GET
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "new" -> showNewUserForm(request, response);
                case "insert" -> insertUser(request, response);
                case "edit" -> showEditUserForm(request, response);
                case "update" -> updateUser(request, response);
                case "delete" -> deleteUser(request, response);
                case "activate" -> activateUser(request, response, true);
                case "deactivate" -> activateUser(request, response, false);
                case "login" -> showLoginForm(request, response);
                case "authenticate" -> authenticateUser(request, response);
                case "register" -> showRegisterForm(request, response);
                case "create" -> registerUser(request, response);
                case "admin" -> listUsersForAdmin(request, response);
                case "userDashboard" -> showUserDashboard(request, response);
                case "logout" -> logoutUser(request, response);
                default -> listUser(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    // Display list of users (Default action)
    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userService.listAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }

    // Show form for adding a new user
    private void showNewUserForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        userService.showNewUserForm(request);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }

    // Show form for editing an existing user
    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.showEditUserForm(request, id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }

    // Insert a new user into the database
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        userService.insertUser(request);
        response.sendRedirect(request.getContextPath() + "/user?action=admin");
    }

    // Update an existing user's details
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        userService.updateUser(request);
        response.sendRedirect(request.getContextPath() + "/user?action=admin");
    }

    // Delete a user (hard delete)
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect(request.getContextPath() + "/user?action=admin");
    }

    // Activate/Deactivate a user
    private void activateUser(HttpServletRequest request, HttpServletResponse response, boolean activate)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("loggedUser");
        userService.activateUser(id, activate, adminUser);
        response.sendRedirect(request.getContextPath() + "/user?action=admin");
    }

    // Show login form
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
    }

    // Authenticate user login with role check
    private void authenticateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        User user = userService.authenticateUser(userName, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);

            // Check user role and redirect accordingly
            if (user.getRole() != null && "Admin".equalsIgnoreCase(user.getRole().getName())) {
                // Redirect to admin dashboard if the user is an admin
                response.sendRedirect(request.getContextPath() + "/user?action=admin");
            } else {
                // Redirect to user dashboard or a default page if the user is not admin
                response.sendRedirect(request.getContextPath() + "/user?action=userDashboard");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Show registration form
    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
        dispatcher.forward(request, response);
    }

    // Register a new user with basic information
    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String password = request.getParameter("password");

        // Server-side password validation using UserService
        if (!userService.isPasswordValid(password)) {
            request.setAttribute("errorMessage", "Password must be at least 8 characters, contain uppercase, lowercase, a number, and a special character.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        userService.registerUser(request);
        response.sendRedirect(request.getContextPath() + "/user?action=login");
    }

    // List users for the admin page
    private void listUsersForAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userService.listAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }

    // Display user dashboard for non-admin users
    private void showUserDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/user-dashboard.jsp");
        dispatcher.forward(request, response);
    }

    // Method to handle logout
    private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false); // Get existing session, do not create a new one
        if (session != null) {
            session.invalidate(); // Invalidate the session, logging the user out
        }
        response.sendRedirect(request.getContextPath() + "/user?action=login"); // Redirect to login page
    }
}
