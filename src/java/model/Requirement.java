/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
import java.sql.Timestamp;

public class Requirement {
    private int reqId;
    private String title;
    private User owner;
    private Setting complexity;
    private Setting status;
    private String description;
    private Timestamp createdAt;
    private User createdBy;
    private Timestamp updatedAt;
    private User updatedBy;

    // Constructors, Getters, and Setters
    public Requirement() {}

    public Requirement(int reqId, String title, User owner, Setting complexity, Setting status, String description, 
                       Timestamp createdAt, User createdBy, Timestamp updatedAt, User updatedBy) {
        this.reqId = reqId;
        this.title = title;
        this.owner = owner;
        this.complexity = complexity;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Setting getComplexity() {
        return complexity;
    }

    public void setComplexity(Setting complexity) {
        this.complexity = complexity;
    }

    public Setting getStatus() {
        return status;
    }

    public void setStatus(Setting status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

