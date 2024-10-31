package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Requirement;
import model.Setting;
import model.User;
import service.DBConnect;

public class RequirementDAO {

    private Connection connection = null;

    public RequirementDAO() {
        try {
            connection = DBConnect.getConnection();
        } catch (Exception e) {
        }
    }

    // 1. List all requirements with owner, complexity, and status details
    public List<Requirement> listAllRequirements() throws SQLException {
        List<Requirement> requirements = new ArrayList<>();
        String sql = """
                SELECT requirement.req_id,requirement.title, user.full_name AS owner_name, 
                       complexity.name AS complexity_level, status.name AS requirement_status, 
                       requirement.description
                FROM requirement
                LEFT JOIN user ON user.user_id = requirement.owner_id
                LEFT JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id
                LEFT JOIN setting AS status ON status.setting_id = requirement.status_id;
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Requirement req = new Requirement();
                req.setReqId(rs.getInt("req_id"));
                req.setTitle(rs.getString("title"));
                req.setDescription(rs.getString("description"));

                User owner = new User();
                owner.setFullName(rs.getString("owner_name"));
                req.setOwner(owner);

                Setting complexity = new Setting();
                complexity.setName(rs.getString("complexity_level"));
                req.setComplexity(complexity);

                Setting status = new Setting();
                status.setName(rs.getString("requirement_status"));
                req.setStatus(status);

                requirements.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requirements;
    }
    //done

    // 2. Requirement details with all information
    public Requirement getRequirementDetails(int reqId) throws SQLException {
        String sql = """
                SELECT requirement.title, requirement.description, requirement.created_at, requirement.req_id,
                       requirement.updated_at, owner.full_name AS owner_name, complexity.name AS complexity_level,
                       status.name AS requirement_status, created_by.full_name AS created_by_name,
                       updated_by.full_name AS updated_by_name
                FROM requirement
                JOIN user AS owner ON owner.user_id = requirement.owner_id
                JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id
                JOIN setting AS status ON status.setting_id = requirement.status_id
                LEFT JOIN user AS created_by ON created_by.user_id = requirement.created_by_id
                LEFT JOIN user AS updated_by ON updated_by.user_id = requirement.updated_by_id
                WHERE requirement.req_id = ?;
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reqId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Requirement req = new Requirement();
                    req.setReqId(rs.getInt("req_id"));
                    req.setTitle(rs.getString("title"));
                    req.setDescription(rs.getString("description"));
                    req.setCreatedAt(rs.getTimestamp("created_at"));
                    req.setUpdatedAt(rs.getTimestamp("updated_at"));

                    User owner = new User();
                    owner.setFullName(rs.getString("owner_name"));
                    req.setOwner(owner);

                    Setting complexity = new Setting();
                    complexity.setName(rs.getString("complexity_level"));
                    req.setComplexity(complexity);

                    Setting status = new Setting();
                    status.setName(rs.getString("requirement_status"));
                    req.setStatus(status);

                    User createdBy = new User();
                    createdBy.setFullName(rs.getString("created_by_name"));
                    req.setCreatedBy(createdBy);

                    User updatedBy = new User();
                    updatedBy.setFullName(rs.getString("updated_by_name"));
                    req.setUpdatedBy(updatedBy);

                    return req;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //done

    // 3. Update a requirement
    public boolean updateRequirement(int reqId, String title, int ownerId, String description, int complexityId, int statusId) throws SQLException {
        String sql = """
            UPDATE requirement
            SET title = ?, owner_id = ?, description = ?, complexity_id = ?, status_id = ?, updated_at = CURRENT_TIMESTAMP
            WHERE req_id = ?;
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setInt(2, ownerId);
            stmt.setString(3, description);
            stmt.setInt(4, complexityId);
            stmt.setInt(5, statusId);
            stmt.setInt(6, reqId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, full_name FROM user";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // 4. Delete a requirement
    public boolean deleteRequirement(int reqId) throws SQLException {
        String deleteIssuesSql = "DELETE FROM issue WHERE req_id = ?;";
        String deleteRequirementSql = "DELETE FROM requirement WHERE req_id = ?;";
        try {
            // Xóa các bản ghi trong bảng issue trước
            connection.setAutoCommit(false);
            try (PreparedStatement issueStmt = connection.prepareStatement(deleteIssuesSql)) {
                issueStmt.setInt(1, reqId);
                issueStmt.executeUpdate();
            }

            // Xóa requirement
            try (PreparedStatement reqStmt = connection.prepareStatement(deleteRequirementSql)) {
                reqStmt.setInt(1, reqId);
                int rowsAffected = reqStmt.executeUpdate();
                connection.commit();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean canDeleteRequirement(int reqId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM issue WHERE req_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reqId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // Trả về true nếu không có bản ghi nào trong bảng issue
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Insert a new requirement
    public boolean insertRequirement(String title, int ownerId, int complexityId, int statusId, String description) throws SQLException {
        String sql = """
                INSERT INTO requirement (title, owner_id, complexity_id, status_id, created_at, description)
                VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?);
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setInt(2, ownerId);
            stmt.setInt(3, complexityId);
            stmt.setInt(4, statusId);
            stmt.setString(5, description);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Filter requirements
    public List<Requirement> filterRequirements(int complexityId, int statusId) throws SQLException {
        List<Requirement> requirements = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT requirement.req_id, requirement.title, user.full_name AS owner_name, "
                + "complexity.name AS complexity_level, status.name AS requirement_status, "
                + "requirement.description "
                + "FROM requirement "
                + "LEFT JOIN user ON user.user_id = requirement.owner_id "
                + "LEFT JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id "
                + "LEFT JOIN setting AS status ON status.setting_id = requirement.status_id "
                + "WHERE 1=1 ");

        // Thêm điều kiện lọc vào câu SQL
        if (complexityId != -1) {
            sql.append("AND requirement.complexity_id = ? ");
        }
        if (statusId != -1) {
            sql.append("AND requirement.status_id = ? ");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Thiết lập giá trị cho các tham số của câu truy vấn
            if (complexityId != -1) {
                stmt.setInt(paramIndex++, complexityId);
            }
            if (statusId != -1) {
                stmt.setInt(paramIndex, statusId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Requirement req = new Requirement();
                    req.setReqId(rs.getInt("req_id"));
                    req.setTitle(rs.getString("title"));
                    req.setDescription(rs.getString("description"));

                    User owner = new User();
                    owner.setFullName(rs.getString("owner_name"));
                    req.setOwner(owner);

                    Setting complexity = new Setting();
                    complexity.setName(rs.getString("complexity_level"));
                    req.setComplexity(complexity);

                    Setting status = new Setting();
                    status.setName(rs.getString("requirement_status"));
                    req.setStatus(status);

                    requirements.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requirements;
    }

    // 7. Search requirements by title
    public List<Requirement> searchRequirementsByTitle(String title) throws SQLException {
        String sql = """
                SELECT requirement.req_id,requirement.title, user.full_name AS owner_name, complexity.name AS complexity_level,
                       status.name AS requirement_status, requirement.description
                FROM requirement
                LEFT JOIN user ON user.user_id = requirement.owner_id
                LEFT JOIN setting AS complexity ON complexity.setting_id = requirement.complexity_id
                LEFT JOIN setting AS status ON status.setting_id = requirement.status_id
                WHERE LOWER(requirement.title) LIKE LOWER(?);
                """;
        List<Requirement> requirements = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Requirement req = new Requirement();
                    req.setReqId(rs.getInt("req_id"));
                    req.setTitle(rs.getString("title"));
                    req.setDescription(rs.getString("description"));

                    User owner = new User();
                    owner.setFullName(rs.getString("owner_name"));
                    req.setOwner(owner);

                    Setting complexity = new Setting();
                    complexity.setName(rs.getString("complexity_level"));
                    req.setComplexity(complexity);

                    Setting status = new Setting();
                    status.setName(rs.getString("requirement_status"));
                    req.setStatus(status);

                    requirements.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requirements;
    }

    // Lấy danh sách Setting cho Complexity (loại 4)
    public List<Setting> getComplexitySettings() throws SQLException {
        return getSettingsByType(4); // Giả định rằng type 4 là cho complexity
    }

    // Lấy danh sách Setting cho Status (loại 5)
    public List<Setting> getStatusSettings() throws SQLException {
        return getSettingsByType(5); // Giả định rằng type 5 là cho status
    }

    private List<Setting> getSettingsByType(int type) throws SQLException {
        List<Setting> settings = new ArrayList<>();
        String sql = "SELECT setting_id, name FROM setting WHERE type = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Setting setting = new Setting();
                    setting.setSettingId(rs.getInt("setting_id"));
                    setting.setName(rs.getString("name"));
                    settings.add(setting);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settings;
    }

}
