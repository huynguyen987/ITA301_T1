package service;

import dao.SettingDAO;
import dao.UserDAO;
import model.Setting;
import model.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class UserService {

    private final UserDAO userDAO;
    private final SettingDAO settingDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.settingDAO = new SettingDAO();
    }

    // List all users
    public List<User> listAllUsers() throws SQLException {
        return userDAO.selectAllUsers();
    }

    // Show form for adding a new user
    public void showNewUserForm(HttpServletRequest request) throws SQLException {
        List<Setting> roles = settingDAO.selectAllRoles();
        List<Setting> departments = settingDAO.selectAllDepartments();
        List<User> listUser = userDAO.selectAllUsers(); // For admin view

        request.setAttribute("roles", roles);
        request.setAttribute("departments", departments);
        request.setAttribute("listUser", listUser);
        request.setAttribute("action", "new");
    }

    // Show form for editing an existing user
    public void showEditUserForm(HttpServletRequest request, int userId) throws SQLException {
        User existingUser = userDAO.selectUser(userId);
        List<Setting> roles = settingDAO.selectAllRoles();
        List<Setting> departments = settingDAO.selectAllDepartments();
        List<User> listUser = userDAO.selectAllUsers(); // For admin view

        request.setAttribute("user", existingUser);
        request.setAttribute("roles", roles);
        request.setAttribute("departments", departments);
        request.setAttribute("listUser", listUser);
        request.setAttribute("action", "edit");
    }

    // Insert a new user into the database
    public void insertUser(HttpServletRequest request) throws SQLException {
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

        HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("loggedUser");
        user.setCreatedBy(adminUser);
        user.setUpdatedBy(adminUser);

        userDAO.insertUser(user);
    }

    // Update an existing user's details
    public void updateUser(HttpServletRequest request) throws SQLException {
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

        HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("loggedUser");
        user.setUpdatedBy(adminUser);

        userDAO.updateUser(user);
    }

    // Delete a user (hard delete)
    public void deleteUser(int userId) throws SQLException {
        userDAO.deleteUser(userId);
    }

    // Activate/Deactivate a user
    public void activateUser(int userId, boolean activate, User adminUser) throws SQLException {
        User user = userDAO.selectUser(userId);
        if (user != null) {
            user.setStatus(activate);
            user.setUpdatedBy(adminUser);
            userDAO.updateUser(user);
        }
    }

    // Authenticate user login with role check
    public User authenticateUser(String userName, String password) {
        return userDAO.loginUser(userName, password);
    }

    // Register a new user with basic information
    public void registerUser(HttpServletRequest request) throws SQLException {
        String fullName = request.getParameter("fullName");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Setting role = null;
        Setting department = null;
        Date startDate = new Date(System.currentTimeMillis());
        boolean status = true;
        String note = "";

        User user = new User(fullName, userName, email, password, role, department, startDate, status, note);
        userDAO.registerUser(user);
    }

    // Validate password (optional: can be moved to a utility class)
    public boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        return password != null && password.matches(passwordPattern);
    }
}
