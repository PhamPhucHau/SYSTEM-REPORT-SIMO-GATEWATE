package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDTO_GET;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDTO_REG;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.UserService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.UserRepository;
import SHINHAN_PORTAL.REPORT_SIMO.infrastructure.security.AuthService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Override
    public List<UserDTO_GET> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO_GET(
                        user.getId().toString(),
                        user.getName(),
                        user.getUsername(),
                        user.getPhoneNo(),
                        user.getEmail(),
                        user.isActive(),
                        user.getRole(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
    @Override
    public User registerUser(UserDTO_REG userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setUsername(userDTO.getUsername());
        newUser.setPasswordHash(authService.encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        newUser.setPhoneNo(userDTO.getPhoneNo());
        newUser.setRole(userDTO.getRole());
        newUser.setCreatedAt(new Date());
        newUser.setActive(true);

        return userRepository.save(newUser);
    }
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
    @Override
    public User updateUser(String id, UserDTO_REG userDTO) {
         ObjectId objectId = new ObjectId(id); // convert String to ObjectId
        User existingUser = userRepository.findById(objectId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + id));


    // Có thể bổ sung check nếu muốn tránh cập nhật trùng username/email với người dùng khác
    // if (!existingUser.getUsername().equals(userDTO.getUsername()) &&
    //         userRepository.existsByUsername(userDTO.getUsername())) {
    //     throw new RuntimeException("Tên đăng nhập đã tồn tại!");
    // }

    if (!existingUser.getEmail().equals(userDTO.getEmail()) &&
            userRepository.existsByEmail(userDTO.getEmail())) {
        throw new RuntimeException("Email đã tồn tại!");
    }

    // Cập nhật thông tin
    //existingUser.setName(userDTO.getName());
    existingUser.setUsername(userDTO.getUsername());
    existingUser.setPhoneNo(userDTO.getPhoneNo());
    existingUser.setEmail(userDTO.getEmail());
    existingUser.setRole(userDTO.getRole());

    // Nếu có password mới, cập nhật
    if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
        existingUser.setPasswordHash(authService.encode(userDTO.getPassword()));
    }

    return userRepository.save(existingUser);
}

}
