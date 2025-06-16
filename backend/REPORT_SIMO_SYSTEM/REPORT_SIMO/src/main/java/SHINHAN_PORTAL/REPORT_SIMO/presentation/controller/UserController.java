package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;


import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDTO_GET;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDTO_REG;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDto;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.UserService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User;
import SHINHAN_PORTAL.REPORT_SIMO.infrastructure.util.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN mới có thể truy cập
    public List<UserDTO_GET> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserDTO_GET> registerUser(@Valid @RequestBody UserDTO_REG userDTO) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.registerUser(userDTO)));
    }
    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserDTO_GET> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(UserMapper.toDTO(userService.getUserByUsername(username)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO_GET> updateUser(
            @PathVariable String id,    
            @Valid @RequestBody UserDTO_REG userDTO) {

        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(UserMapper.toDTO(updatedUser));
    }

}
