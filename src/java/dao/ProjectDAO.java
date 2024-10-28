package dao;

import model.Project;
import service.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    private static final String INSERT_PROJECT_SQL = "INSERT INTO project (name, code, start_date, end_date, status, description) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_PROJECTS = "SELECT project_id, name, code, start_date, end_date, status, description FROM project";
    private static final String SELECT_PROJECT_BY_ID = "SELECT project_id, name, code, start_date, end_date, status, description FROM project WHERE project_id = ?";
    private static final String UPDATE_PROJECT_SQL = "UPDATE project SET name = ?, code = ?, start_date = ?, end_date = ?, status = ?, description = ? WHERE project_id = ?";
    private static final String TOGGLE_STATUS_SQL = "UPDATE project SET status = NOT status WHERE project_id = ?";

    public void insertProject(Project project) throws SQLException {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROJECT_SQL)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getCode());
            preparedStatement.setDate(3, project.getStartDate());
            if (project.getEndDate() != null) {
                preparedStatement.setDate(4, project.getEndDate());
            } else {
                preparedStatement.setNull(4, java.sql.Types.DATE);
            }
            preparedStatement.setBoolean(5, project.isStatus());
            preparedStatement.setString(6, project.getDescription());

            preparedStatement.executeUpdate();
            System.out.println("Inserted project: " + project.getName());
        } catch (SQLException e) {
            printSQLException(e);
            throw e;
        }
    }

    public List<Project> selectAllProjects(String sort, String order, String search) {
        List<Project> projects = new ArrayList<>();
        // Validate sort and order parameters to prevent SQL injection
        String sortColumn;
        switch (sort) {
            case "project.name":
                sortColumn = "name";
                break;
            case "project.code":
                sortColumn = "code";
                break;
            case "project.start_date":
                sortColumn = "start_date";
                break;
            case "project.end_date":
                sortColumn = "end_date";
                break;
            case "project.status":
                sortColumn = "status";
                break;
            case "project.project_id":
            default:
                sortColumn = "project_id";
                break;
        }

        String sortOrder = "ASC".equalsIgnoreCase(order) ? "ASC" : "DESC";

        // Start building the query
        StringBuilder queryBuilder = new StringBuilder(SELECT_ALL_PROJECTS);

        // Add WHERE clause if search is provided
        if (search != null && !search.trim().isEmpty()) {
            queryBuilder.append(" WHERE name LIKE ?");
        }

        // Add ORDER BY clause
        queryBuilder.append(" ORDER BY ").append(sortColumn).append(" ").append(sortOrder);

        String query = queryBuilder.toString();

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (search != null && !search.trim().isEmpty()) {
                preparedStatement.setString(1, "%" + search.trim() + "%");
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String name = rs.getString("name");
                String code = rs.getString("code");
                java.sql.Date startDate = rs.getDate("start_date");
                java.sql.Date endDate = rs.getDate("end_date");
                boolean status = rs.getBoolean("status");
                String description = rs.getString("description");

                Project project = new Project(projectId, name, code, startDate, endDate, status, description);
                projects.add(project);
            }
            System.out.println("Fetched " + projects.size() + " projects.");
        } catch (SQLException e) {
            printSQLException(e);
        }

        return projects;
    }

    public Project selectProjectById(int projectId) {
        Project project = null;

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROJECT_BY_ID)) {
            preparedStatement.setInt(1, projectId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String code = rs.getString("code");
                java.sql.Date startDate = rs.getDate("start_date");
                java.sql.Date endDate = rs.getDate("end_date");
                boolean status = rs.getBoolean("status");
                String description = rs.getString("description");

                project = new Project(projectId, name, code, startDate, endDate, status, description);
            }
            System.out.println("Fetched project by ID " + projectId + ": " + (project != null ? project.getName() : "Not Found"));
        } catch (SQLException e) {
            printSQLException(e);
        }

        return project;
    }

    public void updateProject(Project project) throws SQLException {
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROJECT_SQL)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getCode());
            preparedStatement.setDate(3, project.getStartDate());
            if (project.getEndDate() != null) {
                preparedStatement.setDate(4, project.getEndDate());
            } else {
                preparedStatement.setNull(4, java.sql.Types.DATE);
            }
            preparedStatement.setBoolean(5, project.isStatus());
            preparedStatement.setString(6, project.getDescription());
            preparedStatement.setInt(7, project.getProjectId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Updated project ID " + project.getProjectId() + ": " + project.getName());
            } else {
                System.out.println("No project found with ID " + project.getProjectId());
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw e;
        }
    }

    // Phương thức mới để thay đổi trạng thái dự án
    public boolean toggleProjectStatus(int projectId) throws SQLException {
        boolean updated = false;
        try (Connection connection = DBConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TOGGLE_STATUS_SQL)) {
            preparedStatement.setInt(1, projectId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                updated = true;
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw e;
        }
        return updated;
    }

    // Các phương thức khác như deleteProject, ...

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
