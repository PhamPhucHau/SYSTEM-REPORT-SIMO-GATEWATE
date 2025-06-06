package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

public class TokenRequestDTO {
    private String grantType;
    private String username;
    private String password;

    // Constructor
    public TokenRequestDTO(String grantType, String username, String password) {
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}