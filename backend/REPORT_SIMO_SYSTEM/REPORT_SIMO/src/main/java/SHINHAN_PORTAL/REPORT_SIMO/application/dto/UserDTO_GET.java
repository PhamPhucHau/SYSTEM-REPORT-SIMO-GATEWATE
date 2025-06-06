package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import java.util.Date;

public class UserDTO_GET {
    private String id;
    private String name;
    private String username;
    private String phoneNo;
    private String email;
    private boolean isActive;
    private String role;
    private Date createdAt;

    // Constructor
    public UserDTO_GET(String id, String name, String username, String phoneNo, String email, boolean isActive, String role, Date createdAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
        this.isActive = isActive;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
