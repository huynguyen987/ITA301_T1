package dao;

import service.DBConnect;
import model.Setting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingDAO {

    private static final Logger LOGGER = Logger.getLogger(SettingDAO.class.getName());

    private static final String SELECT_SETTING_BY_ID = "SELECT * FROM setting WHERE setting_id = ?";
    private static final String SELECT_ALL_SETTINGS = "SELECT * FROM setting";
    private static final String SELECT_ALL_ROLES = "SELECT * FROM setting WHERE type = ?";
    private static final String SELECT_ALL_DEPARTMENTS = "SELECT * FROM setting WHERE type = ?";
    private static final String SELECT_SETTING_BY_NAME = "SELECT * FROM setting WHERE name = ?";
    private static final String SELECT_MAX_PRIORITY = "SELECT MAX(priority) FROM setting";
    private static final String INSERT_SETTING = "INSERT INTO setting (name, value, type, priority, status, description) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SETTING = "UPDATE setting SET name = ?, value = ?, type = ?, priority = ?, status = ?, description = ? WHERE setting_id = ?";
    private static final String DELETE_SETTING = "DELETE FROM setting WHERE setting_id = ?";

    private static final int ROLE_TYPE = 2;         // Assuming `type = 2` for roles
    private static final int DEPARTMENT_TYPE = 3;   // Assuming `type = 3` for departments

    public List<Setting> searchSettings(String keyword) throws SQLException {
        List<Setting> settings = new ArrayList<>();
        String query = "SELECT * FROM setting WHERE name LIKE ? OR value LIKE ?";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                settings.add(mapResultSetToSetting(rs));
            }
        }
        return settings;
    }

    public int getMaxPriority() throws SQLException {
        int maxPriority = 0;
        String query = "SELECT MAX(priority) AS max_priority FROM setting";

        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet rs = preparedStatement.executeQuery()) {

            if (rs.next()) {
                maxPriority = rs.getInt("max_priority");
            }
        }
        return maxPriority;
    }

    // Fetch a single setting by ID
    public Setting selectSetting(int settingId) {
        Setting setting = null;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SETTING_BY_ID)) {
            preparedStatement.setInt(1, settingId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                setting = mapResultSetToSetting(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching setting by ID: " + settingId, e);
        }
        return setting;
    }

    // Fetch all settings
    public List<Setting> selectAllSettings() {
        List<Setting> settings = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SETTINGS); ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                settings.add(mapResultSetToSetting(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all settings", e);
        }
        return settings;
    }

    // Insert a new setting
    public boolean insertSetting(Setting setting) {
        boolean rowInserted = false;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SETTING)) {

            preparedStatement.setString(1, setting.getName());
            preparedStatement.setString(2, setting.getValue());
            preparedStatement.setInt(3, setting.getType());
            preparedStatement.setInt(4, setting.getPriority());
            preparedStatement.setBoolean(5, setting.isStatus());
            preparedStatement.setString(6, setting.getDescription());

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting new setting", e);
        }
        return rowInserted;
    }

    // Update an existing setting
    public boolean updateSetting(Setting setting) {
        boolean rowUpdated = false;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SETTING)) {

            preparedStatement.setString(1, setting.getName());
            preparedStatement.setString(2, setting.getValue());
            preparedStatement.setInt(3, setting.getType());
            preparedStatement.setInt(4, setting.getPriority());
            preparedStatement.setBoolean(5, setting.isStatus());
            preparedStatement.setString(6, setting.getDescription());
            preparedStatement.setInt(7, setting.getSettingId());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating setting with ID: " + setting.getSettingId(), e);
        }
        return rowUpdated;
    }

    // Delete a setting by ID
    public boolean deleteSetting(int settingId) {
        boolean rowDeleted = false;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SETTING)) {
            preparedStatement.setInt(1, settingId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting setting with ID: " + settingId, e);
        }
        return rowDeleted;
    }

    // Fetch all settings representing roles
    public List<Setting> selectAllRoles() {
        List<Setting> roles = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES)) {
            preparedStatement.setInt(1, ROLE_TYPE);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                roles.add(mapResultSetToSetting(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all roles", e);
        }
        return roles;
    }

    // Fetch all settings representing departments
    public List<Setting> selectAllDepartments() {
        List<Setting> departments = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DEPARTMENTS)) {
            preparedStatement.setInt(1, DEPARTMENT_TYPE);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                departments.add(mapResultSetToSetting(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all departments", e);
        }
        return departments;
    }

    // Fetch the default role for new users (e.g., "Pending Approval")
    public Setting selectDefaultRole() {
        return selectSettingByName("Pending Approval"); // Adjust to match your default role name
    }

    // Fetch the default department for new users (e.g., "Unassigned")
    public Setting selectDefaultDepartment() {
        return selectSettingByName("Unassigned"); // Adjust to match your default department name
    }

    // Fetch a setting by name
    private Setting selectSettingByName(String name) {
        Setting setting = null;
        try (Connection connection = DBConnect.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SETTING_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                setting = mapResultSetToSetting(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching setting by name: " + name, e);
        }
        return setting;
    }

    // Helper method to map ResultSet to Setting object
    private Setting mapResultSetToSetting(ResultSet rs) throws SQLException {
        int settingId = rs.getInt("setting_id");
        String name = rs.getString("name");
        String value = rs.getString("value");
        int type = rs.getInt("type");
        int priority = rs.getInt("priority");
        boolean status = rs.getBoolean("status");
        String description = rs.getString("description");
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");

        return new Setting(settingId, name, value, type, priority, status, description, createdAt, null, updatedAt, null);
    }
}
