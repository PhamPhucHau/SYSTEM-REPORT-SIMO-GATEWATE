package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDTO_GET;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDTO_REG;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface  UserService {
    List<UserDTO_GET> getAllUsers();
    User registerUser(UserDTO_REG userDTO_REG);
    User getUserByUsername(String id);
    void deleteUser(String id);
    Optional<User> findByUsername(String username);
    User updateUser(String id , UserDTO_REG userDTO);
}
