package dao;

import service.DBConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Setting;
import model.User;

public class UserDAO {

    private final SettingDAO settingDAO = new SettingDAO();

    private static final String INSERT_USER_SQL = "INSERT INTO user (full_name, user_name, email, password, role_id, dept_id, start_date, status, note, created_by_id, updated_by_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_USER_BY_ID =
            "SELECT u.*, r.name AS role_name, d.name AS dept_name " +
            "FROM user u " +
            "LEFT JOIN setting r ON u.role_id = r.setting_id " +
            "LEFT JOIN setting d ON u.dept_id = d.setting_id " +
            "WHERE u.user_id = ?";
    
    private static final String SELECT_ALL_USERS =
            "SELECT u.*, r.name AS role_name, d.name AS dept_name " +
            "FROM user u " +
            "LEFT JOIN setting r ON u.role_id = r.setting_id " +
            "LEFT JOIN setting d ON u.dept_id = d.setting_id";

    private static final String UPDATE_USER_SQL = "UPDATE user SET full_name = ?, user_name = ?, email = ?, password = ?, role_id = ?, dept_id = ?, start_date = ?, status = ?, note = ?, updated_by_id = ? WHERE user_id = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM user WHERE user_id = ?";
    private static final String SELECT_USER_BY_USERNAME_PASSWORD = "SELECT * FROM user WHERE user_name = ? AND password = ?";

    // Register a new user with defaults for some fields
public void registerUser(User user) throws SQLException {
    try (Connection connection = DBConnect.getConnection(); 
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
        preparedStatement.setString(1, user.getFullName());
        preparedStatement.setString(2, user.getUserName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());

        // Handle null role and department by setting SQL NULL
        if (user.getRole() != null) {
            preparedStatement.setInt(5, user.getRole().getSettingId());
        } else {
            preparedStatement.setNull(5, java.sql.Types.INTEGER);
        }

        if (user.getDepartment() != null) {
            preparedStatement.setInt(6, user.getDepartment().getSettingId());
        } else {
            preparedStatement.setNull(6, java.sql.Types.INTEGER);
        }

        preparedStatement.setDate(7, user.getStartDate());
        preparedStatement.setBoolean(8, user.isStatus());
        preparedStatement.setString(9, user.getNote());

        // Assuming created_by_id and updated_by_id can be defaulted to a certain value, like 1 (Admin)
        preparedStatement.setInt(10, 1);
        preparedStatement.setInt(11, 1);

        preparedStatement.executeUpdate();
    }
}



    // Authenticate user (login)
    public User loginUser(String userName, String password) {
        User user = null;
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME_PASSWORD)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                int deptId = rs.getInt("dept_id");
                Date startDate = rs.getDate("start_date");
                boolean status = rs.getBoolean("status");
                String note = rs.getString("note");

                // Retrieve role and department names
                Setting role = new Setting(deptId, note);
                Setting department = new Setting(deptId, note);

                user = new User(userId, fullName, userName, email, password, role, department, startDate, status, note, rs.getTimestamp("created_at"), null, rs.getTimestamp("updated_at"), null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Select user by ID
    public User selectUser(int userId) {
        User user = null;
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String fullName = rs.getString("full_name");
                String userName = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Date startDate = rs.getDate("start_date");
                boolean status = rs.getBoolean("status");
                String note = rs.getString("note");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                // Retrieve only the name of role and department
                Setting role = new Setting(userId, note);
                Setting department = new Setting(userId, note);

                user = new User(userId, fullName, userName, email, password, role, department, startDate, status, note, createdAt, null, updatedAt, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Insert a new user into the database
    public void insertUser(User user) throws SQLException {
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getRole().getSettingId());
            preparedStatement.setInt(6, user.getDepartment().getSettingId());
            preparedStatement.setDate(7, user.getStartDate());
            preparedStatement.setBoolean(8, user.isStatus());
            preparedStatement.setString(9, user.getNote());
            preparedStatement.setInt(10, user.getCreatedBy() != null ? user.getCreatedBy().getUserId() : 1);
            preparedStatement.setInt(11, user.getUpdatedBy() != null ? user.getUpdatedBy().getUserId() : 1);
            preparedStatement.executeUpdate();
        }
    }

    // Select all users
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String userName = rs.getString("user_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Date startDate = rs.getDate("start_date");
                boolean status = rs.getBoolean("status");
                String note = rs.getString("note");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                // Role and Department with only name information
                Setting role = new Setting(userId, note);
                Setting department = new Setting(userId, note);

                User user = new User(userId, fullName, userName, email, password, role, department, startDate, status, note, createdAt, null, updatedAt, null);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Update an existing user
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getRole().getSettingId());
            statement.setInt(6, user.getDepartment().getSettingId());
            statement.setDate(7, user.getStartDate());
            statement.setBoolean(8, user.isStatus());
            statement.setString(9, user.getNote());
            statement.setInt(10, user.getUpdatedBy() != null ? user.getUpdatedBy().getUserId() : 1);
            statement.setInt(11, user.getUserId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Delete a user
    public boolean deleteUser(int userId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            statement.setInt(1, userId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    // Test to show all users
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.selectAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
