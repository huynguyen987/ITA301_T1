package dao;

import model.Project;
import model.Setting;
import model.User;
import service.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho thực thể Project.
 */
public class ProjectDAO {

    // Danh sách các cột được phép sắp xếp để ngăn ngừa SQL Injection
    private static final List<String> ALLOWED_SORT_COLUMNS = List.of(
            "project.project_id",
            "project.name",
            "project.code",
            "project.start_date",
            "project.end_date",
            "project.status"
    );

    /**
     * Lấy danh sách các dự án đã sắp xếp và phân trang.
     *
     * @param sortColumn    Cột để sắp xếp (ví dụ: "project.name")
     * @param sortOrder     Thứ tự sắp xếp ("ASC" hoặc "DESC")
     * @param limit         Số bản ghi trên mỗi trang
     * @param offset        Vị trí bắt đầu lấy dữ liệu
     * @return Danh sách các dự án
     * @throws SQLException Nếu có lỗi truy cập cơ sở dữ liệu
     */
    public List<Project> getProjects(String sortColumn, String sortOrder, int limit, int offset) throws SQLException {
        // Kiểm tra tính hợp lệ của cột sắp xếp
        if (!ALLOWED_SORT_COLUMNS.contains(sortColumn)) {
            sortColumn = "project.project_id"; // Mặc định sắp xếp theo ID
        }

        // Kiểm tra tính hợp lệ của thứ tự sắp xếp
        if (!sortOrder.equalsIgnoreCase("DESC")) {
            sortOrder = "ASC"; // Mặc định sắp xếp tăng dần
        }

        String sql = "SELECT " +
                "project.project_id, project.name, project.code, project.start_date, project.end_date, " +
                "project.status, project.description, project.created_at, project.updated_at, " +
                "dept.setting_id AS dept_id, dept.name AS dept_name, " +
                "created_by_user.user_id AS created_by_id, created_by_user.full_name AS created_by_full_name, " +
                "updated_by_user.user_id AS updated_by_id, updated_by_user.full_name AS updated_by_full_name " +
                "FROM project " +
                "LEFT JOIN setting AS dept ON project.dept_id = dept.setting_id " +
                "LEFT JOIN user AS created_by_user ON project.created_by_id = created_by_user.user_id " +
                "LEFT JOIN user AS updated_by_user ON project.updated_by_id = updated_by_user.user_id " +
                "ORDER BY " + sortColumn + " " + sortOrder + " " +
                "LIMIT ? OFFSET ?";

        List<Project> projects = new ArrayList<>();

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Project project = mapResultSetToProject(rs);
                    projects.add(project);
                }
            }
        }

        return projects;
    }

    /**
     * Lấy tổng số dự án trong cơ sở dữ liệu.
     *
     * @return Tổng số dự án
     * @throws SQLException Nếu có lỗi truy cập cơ sở dữ liệu
     */
    public int getTotalProjects() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM project";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }

        return 0;
    }

    /**
     * Lấy thông tin chi tiết của một dự án dựa trên projectId.
     *
     * @param projectId ID của dự án cần lấy thông tin.
     * @return Đối tượng Project chứa thông tin chi tiết, hoặc null nếu không tìm thấy.
     * @throws SQLException Nếu có lỗi truy cập cơ sở dữ liệu.
     */
    public Project getProject(int projectId) throws SQLException {
        String sql = "SELECT " +
                "project.project_id, project.name, project.code, project.start_date, project.end_date, " +
                "project.status, project.description, project.created_at, project.updated_at, " +
                "dept.setting_id AS dept_id, dept.name AS dept_name, " +
                "created_by_user.user_id AS created_by_id, created_by_user.full_name AS created_by_full_name, " +
                "updated_by_user.user_id AS updated_by_id, updated_by_user.full_name AS updated_by_full_name " +
                "FROM project " +
                "LEFT JOIN setting AS dept ON project.dept_id = dept.setting_id " +
                "LEFT JOIN user AS created_by_user ON project.created_by_id = created_by_user.user_id " +
                "LEFT JOIN user AS updated_by_user ON project.updated_by_id = updated_by_user.user_id " +
                "WHERE project.project_id = ?";

        Project project = null;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    project = mapResultSetToProject(rs);
                }
            }
        }

        return project;
    }

    /**
     * Phương thức trợ giúp để map ResultSet thành đối tượng Project.
     *
     * @param rs ResultSet từ truy vấn cơ sở dữ liệu
     * @return Đối tượng Project
     * @throws SQLException Nếu có lỗi khi đọc dữ liệu từ ResultSet
     */
    private Project mapResultSetToProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectId(rs.getInt("project_id"));
        project.setName(rs.getString("name"));
        project.setCode(rs.getString("code"));
        project.setStartDate(rs.getDate("start_date"));
        project.setEndDate(rs.getDate("end_date"));
        project.setStatus(rs.getBoolean("status"));
        project.setDescription(rs.getString("description"));
        project.setCreatedAt(rs.getTimestamp("created_at"));
        project.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Thiết lập Department
        int deptId = rs.getInt("dept_id");
        String deptName = rs.getString("dept_name");
        if (deptId > 0 && deptName != null) {
            Setting department = new Setting(deptId, deptName);
            project.setDepartment(department);
        }

        // Thiết lập CreatedBy User
        int createdById = rs.getInt("created_by_id");
        String createdByFullName = rs.getString("created_by_full_name");
        if (createdById > 0 && createdByFullName != null) {
            User createdBy = new User();
            createdBy.setUserId(createdById);
            createdBy.setFullName(createdByFullName);
            project.setCreatedBy(createdBy);
        }

        // Thiết lập UpdatedBy User
        int updatedById = rs.getInt("updated_by_id");
        String updatedByFullName = rs.getString("updated_by_full_name");
        if (updatedById > 0 && updatedByFullName != null) {
            User updatedBy = new User();
            updatedBy.setUserId(updatedById);
            updatedBy.setFullName(updatedByFullName);
            project.setUpdatedBy(updatedBy);
        }

        return project;
    }
}
