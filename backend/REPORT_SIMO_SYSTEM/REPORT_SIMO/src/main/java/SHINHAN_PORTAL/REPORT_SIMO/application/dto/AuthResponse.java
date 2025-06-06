package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String access_token;
    private String scope;
    private String token_type;
    private long expires_in;
    private String role;

    public AuthResponse(String access_token, String scope, String token_type, long expires_in, String role) {
        this.access_token = access_token;
        this.scope = scope;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.role = role;
    }

    // Getters
    public String getAccess_token() {
        return access_token;
    }

    public String getScope() {
        return scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    // Setters
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}