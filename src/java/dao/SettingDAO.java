package dao;

import service.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Setting;

public class SettingDAO {

    private static final Logger LOGGER = Logger.getLogger(SettingDAO.class.getName());

    private static final String SELECT_SETTING_BY_ID = "SELECT * FROM setting WHERE setting_id = ?";
    private static final String SELECT_ALL_ROLES = "SELECT * FROM setting WHERE type = ?";
    private static final String SELECT_ALL_DEPARTMENTS = "SELECT * FROM setting WHERE type = ?";
    private static final String SELECT_SETTING_BY_NAME = "SELECT * FROM setting WHERE name = ?";

    private static final int ROLE_TYPE = 2;         // Assuming `type = 2` for roles
    private static final int DEPARTMENT_TYPE = 3;   // Assuming `type = 3` for departments

    // Fetch a single setting by ID
    public Setting selectSetting(int settingId) {
        Setting setting = null;
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SETTING_BY_ID)) {
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

    // Fetch all settings representing roles
    public List<Setting> selectAllRoles() {
        List<Setting> roles = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROLES)) {
            preparedStatement.setInt(1, ROLE_TYPE); // Set the type for roles
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
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DEPARTMENTS)) {
            preparedStatement.setInt(1, DEPARTMENT_TYPE); // Set the type for departments
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
        return selectSettingByName("Pending Approval"); // Adjust "Pending Approval" to match your default role name
    }

    // Fetch the default department for new users (e.g., "Unassigned")
    public Setting selectDefaultDepartment() {
        return selectSettingByName("Unassigned"); // Adjust "Unassigned" to match your default department name
    }

    // Fetch a setting by name
    private Setting selectSettingByName(String name) {
        Setting setting = null;
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SETTING_BY_NAME)) {
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
