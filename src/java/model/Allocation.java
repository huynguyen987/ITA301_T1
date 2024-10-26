
package model;



import java.sql.Date;
import java.sql.Timestamp;

public class Allocation {
    private int allocationId;
    private User member;
    private Project project;
    private Setting projectRole;
    private Date fromDate;
    private Date toDate;
    private double effortRate;
    private String description;
    private boolean status;
    private Timestamp createdAt;
    private User createdBy;
    private Timestamp updatedAt;
    private User updatedBy;

    // Constructors, Getters, and Setters
    public Allocation() {}

    public Allocation(int allocationId, User member, Project project, Setting projectRole, Date fromDate, Date toDate, 
                      double effortRate, String description, boolean status, Timestamp createdAt, 
                      User createdBy, Timestamp updatedAt, User updatedBy) {
        this.allocationId = allocationId;
        this.member = member;
        this.project = project;
        this.projectRole = projectRole;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.effortRate = effortRate;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(int allocationId) {
        this.allocationId = allocationId;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Setting getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(Setting projectRole) {
        this.projectRole = projectRole;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public double getEffortRate() {
        return effortRate;
    }

    public void setEffortRate(double effortRate) {
        this.effortRate = effortRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    
}

