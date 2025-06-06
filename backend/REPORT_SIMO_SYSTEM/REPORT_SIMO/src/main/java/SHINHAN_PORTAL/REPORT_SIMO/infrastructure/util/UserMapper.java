package SHINHAN_PORTAL.REPORT_SIMO.infrastructure.util;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDTO_GET;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User;

public class UserMapper {
    public static UserDTO_GET toDTO(User user) {
        return new UserDTO_GET(
                user.getId().toString(),
                user.getName(),
                user.getUsername(),
                user.getPhoneNo(),
                user.getEmail(),
                user.isActive(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}