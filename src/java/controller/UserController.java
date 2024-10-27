package controller;

import dao.SettingDAO;
import dao.UserDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Setting;
import model.User;

@WebServlet("/user")
public class UserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private SettingDAO settingDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        settingDAO = new SettingDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "new":
                    showNewUserForm(request, response);
                    break;
                case "insert":
                    insertUser(request, response);
                    break;
                case "edit":
                    showEditUserForm(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "activate":
                    activateUser(request, response, true);
                    break;
                case "deactivate":
                    activateUser(request, response, false);
                    break;
                case "login":
                    showLoginForm(request, response);
                    break;
                case "authenticate":
                    authenticateUser(request, response);
                    break;
                case "register":
                    showRegisterForm(request, response);
                    break;
                case "create":
                    registerUser(request, response);
                    break;
                case "admin":
                    listUsersForAdmin(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    // Display list of users
    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    // Show form for adding a new user
    private void showNewUserForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Setting> roles = settingDAO.selectAllRoles();
        List<Setting> departments = settingDAO.selectAllDepartments();
        request.setAttribute("roles", roles);
        request.setAttribute("departments", departments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    // Show form for editing an existing user
    private void showEditUserForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        List<Setting> roles = settingDAO.selectAllRoles();
        List<Setting> departments = settingDAO.selectAllDepartments();
        request.setAttribute("user", existingUser);
        request.setAttribute("roles", roles);
        request.setAttribute("departments", departments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    // Insert a new user into the database
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        int deptId = Integer.parseInt(request.getParameter("deptId"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        String note = request.getParameter("note");

        Setting role = settingDAO.selectSetting(roleId);
        Setting department = settingDAO.selectSetting(deptId);
        User user = new User(fullName, userName, email, password, role, department, startDate, status, note);

        userDAO.insertUser(user);
        response.sendRedirect(request.getContextPath() + "/user?action=list");

    }

    // Update an existing user's details
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        int deptId = Integer.parseInt(request.getParameter("deptId"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        String note = request.getParameter("note");

        Setting role = settingDAO.selectSetting(roleId);
        Setting department = settingDAO.selectSetting(deptId);
        User user = new User(id, fullName, userName, email, password, role, department, startDate, status, note, null, null);

        userDAO.updateUser(user);
        response.sendRedirect(request.getContextPath() + "/user?action=list");

    }

    // Delete a user
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect(request.getContextPath() + "/user?action=list");

    }

    // Activate/Deactivate a user
    private void activateUser(HttpServletRequest request, HttpServletResponse response, boolean activate)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userDAO.selectUser(id);
        user.setStatus(activate);  // Set user status to true (activate) or false (deactivate)
        userDAO.updateUser(user);
        response.sendRedirect(request.getContextPath() + "/user?action=list");

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

        User user = userDAO.loginUser(userName, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);

            // Check if the user has an admin role
            if (user.getRole() != null && "Admin".equalsIgnoreCase(user.getRole().getName())) {
                // Redirect to admin page if the user is an admin
                response.sendRedirect(request.getContextPath() + "/user?action=admin");
            } else {
                // Redirect to user dashboard or a default page if not admin
                response.sendRedirect(request.getContextPath() + "/user?action=list");
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
        List<Setting> roles = settingDAO.selectAllRoles();
        List<Setting> departments = settingDAO.selectAllDepartments();
        System.out.println("Roles: " + roles);  // Debug
        System.out.println("Departments: " + departments);  // Debug
        request.setAttribute("roles", roles);
        request.setAttribute("departments", departments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
        dispatcher.forward(request, response);
    }

    // Register a new user with basic information
    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Since roles and departments are set only by admin, set them to null or a default value
        Setting role = null;  // Default role could also be set here if needed
        Setting department = null;

        // Optionally, set a default start date or use a current date
        Date startDate = new Date(System.currentTimeMillis());  // or any other default
        boolean status = true;  // Assuming new users are active by default
        String note = "";

        // Create a user object with null for role and department
        User user = new User(fullName, userName, email, password, role, department, startDate, status, note);

        userDAO.registerUser(user);
        response.sendRedirect(request.getContextPath() + "/user?action=login");
    }

    // List users for the admin page
    private void listUsersForAdmin(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }
}
