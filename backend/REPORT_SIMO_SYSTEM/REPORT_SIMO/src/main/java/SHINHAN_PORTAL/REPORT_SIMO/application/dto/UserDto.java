package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

public class UserDto {
    private Long id ;
    private String username ;
    private String password;
    public UserDto() {

    }
    public UserDto(Long id,String username, String password) {
        this.id =id ;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
