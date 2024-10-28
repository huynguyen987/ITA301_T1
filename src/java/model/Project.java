package model;

import java.sql.Date;

public class Project {
    private int projectId;
    private String name;
    private String code;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private String description;

    // Constructors
    public Project() {
    }

    public Project(String name, String code, Date startDate, Date endDate, boolean status, String description) {
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }

    public Project(int projectId, String name, String code, Date startDate, Date endDate, boolean status, String description) {
        this.projectId = projectId;
        this.name = name;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }

    // Getters and Setters
    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
