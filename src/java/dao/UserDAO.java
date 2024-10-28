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

    private static final String SELECT_USER_BY_ID
            = "SELECT u.*, r.setting_id AS role_id, r.name AS role_name, d.setting_id AS dept_id, d.name AS dept_name "
            + "FROM user u "
            + "LEFT JOIN setting r ON u.role_id = r.setting_id "
            + "LEFT JOIN setting d ON u.dept_id = d.setting_id "
            + "WHERE u.user_id = ?";

    private static final String SELECT_ALL_USERS
            = "SELECT u.*, r.setting_id AS role_id, r.name AS role_name, d.setting_id AS dept_id, d.name AS dept_name "
            + "FROM user u "
            + "LEFT JOIN setting r ON u.role_id = r.setting_id "
            + "LEFT JOIN setting d ON u.dept_id = d.setting_id";

    private static final String UPDATE_USER_SQL = "UPDATE user SET full_name = ?, user_name = ?, email = ?, password = ?, role_id = ?, dept_id = ?, start_date = ?, status = ?, note = ?, updated_by_id = ? WHERE user_id = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM user WHERE user_id = ?";
    private static final String SELECT_USER_BY_USERNAME_PASSWORD = "SELECT u.*, r.setting_id AS role_id, r.name AS role_name, d.setting_id AS dept_id, d.name AS dept_name "
            + "FROM user u "
            + "LEFT JOIN setting r ON u.role_id = r.setting_id "
            + "LEFT JOIN setting d ON u.dept_id = d.setting_id "
            + "WHERE u.user_name = ? AND password = ?";

    // Register a new user with defaults for some fields
    public void registerUser(User user) throws SQLException {
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
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

            // Set created_by_id and updated_by_id to current admin user or default
            preparedStatement.setInt(10, user.getCreatedBy() != null ? user.getCreatedBy().getUserId() : 1);
            preparedStatement.setInt(11, user.getUpdatedBy() != null ? user.getUpdatedBy().getUserId() : 1);

            preparedStatement.executeUpdate();
        }
    }

    // Authenticate user (login)
    public User loginUser(String userName, String password) {
        User user = null;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME_PASSWORD)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");
                Date startDate = rs.getDate("start_date");
                boolean status = rs.getBoolean("status");
                String note = rs.getString("note");

                // Create role and department settings
                Setting role = new Setting(roleId, roleName);
                Setting department = new Setting(deptId, deptName);

                user = new User(userId, fullName, userName, email, password, role, department, startDate, status, note,
                        rs.getTimestamp("created_at"), null, rs.getTimestamp("updated_at"), null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Select user by ID
    public User selectUser(int userId) {
        User user = null;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
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

                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");

                Setting role = new Setting(roleId, roleName);
                Setting department = new Setting(deptId, deptName);

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

            // Handle null role and department
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

                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                int deptId = rs.getInt("dept_id");
                String deptName = rs.getString("dept_name");

                Setting role = new Setting(roleId, roleName);
                Setting department = new Setting(deptId, deptName);

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

            if (user.getRole() != null) {
                statement.setInt(5, user.getRole().getSettingId());
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }

            if (user.getDepartment() != null) {
                statement.setInt(6, user.getDepartment().getSettingId());
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }

            statement.setDate(7, user.getStartDate());
            statement.setBoolean(8, user.isStatus());
            statement.setString(9, user.getNote());
            statement.setInt(10, user.getUpdatedBy() != null ? user.getUpdatedBy().getUserId() : 1);
            statement.setInt(11, user.getUserId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Delete a user completely from the database (hard delete)
    public boolean deleteUser(int userId) throws SQLException {
        boolean rowDeleted = false;
        Connection connection = null;
        PreparedStatement deleteAllocationsStatement = null;
        PreparedStatement deleteUserStatement = null;

        try {
            connection = DBConnect.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Delete related records from the allocation table
            String deleteAllocationsSQL = "DELETE FROM allocation WHERE member_id = ?";
            deleteAllocationsStatement = connection.prepareStatement(deleteAllocationsSQL);
            deleteAllocationsStatement.setInt(1, userId);
            deleteAllocationsStatement.executeUpdate();

            // Delete the user from the user table
            deleteUserStatement = connection.prepareStatement(DELETE_USER_SQL);
            deleteUserStatement.setInt(1, userId);
            rowDeleted = deleteUserStatement.executeUpdate() > 0;

            connection.commit(); // Commit transaction if everything is successful
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback(); // Rollback transaction in case of error
            }
            e.printStackTrace();
        } finally {
            if (deleteAllocationsStatement != null) {
                deleteAllocationsStatement.close();
            }
            if (deleteUserStatement != null) {
                deleteUserStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return rowDeleted;
    }

}
