package model;

import java.sql.Timestamp;

public class Setting {
    private int settingId;
    private String name;

    // Optional fields retained in case other parts need them
    private String value;
    private int type;
    private int priority;
    private boolean status;
    private String description;
    private Timestamp createdAt;
    private User createdBy;
    private Timestamp updatedAt;
    private User updatedBy;

    // Constructors
    public Setting() {}

    // Simplified constructor for use in UserDAO
    public Setting(int settingId, String name) {
        this.settingId = settingId;
        this.name = name;
    }

    // Full constructor for broader use
    public Setting(int settingId, String name, String value, int type, int priority, boolean status,
                   String description, Timestamp createdAt, User createdBy, Timestamp updatedAt, User updatedBy) {
        this.settingId = settingId;
        this.name = name;
        this.value = value;
        this.type = type;
        this.priority = priority;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    // Getters and Setters for essential fields
    public int getSettingId() { return settingId; }
    public void setSettingId(int settingId) { this.settingId = settingId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Optional Getters and Setters for broader use
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    public User getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(User updatedBy) { this.updatedBy = updatedBy; }

    @Override
    public String toString() {
        return name;
    }

    
}
