/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
import java.sql.Date;
import java.sql.Timestamp;

public class User {
    private int userId;
    private String fullName;
    private String userName;
    private String email;
    private String password;
    private Setting role;
    private Setting department;
    private Date startDate;
    private boolean status;
    private String note;
    private Timestamp createdAt;
    private User createdBy;
    private Timestamp updatedAt;
    private User updatedBy;

    // Constructors
    public User() {}

    public User(int userId, String fullName, String userName, String email, String password, 
                Setting role, Setting department, Date startDate, boolean status, 
                String note, Timestamp createdAt, User createdBy, Timestamp updatedAt, User updatedBy) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.department = department;
        this.startDate = startDate;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Setting getRole() { return role; }
    public void setRole(Setting role) { this.role = role; }

    public Setting getDepartment() { return department; }
    public void setDepartment(Setting department) { this.department = department; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public User getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(User updatedBy) { this.updatedBy = updatedBy; }
}

