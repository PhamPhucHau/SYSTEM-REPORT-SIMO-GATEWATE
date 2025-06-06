package SHINHAN_PORTAL.REPORT_SIMO.infrastructure.security;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository ;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private JwtService jwtService ;
    public Map<String, String> login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));

        if(!passwordEncoder.matches(password,user.getPasswordHash()))
        {
            throw new BadCredentialsException("Wrong password");
        }
        // 3 Generate Token
        // Return output
        Map<String, String> res = new HashMap<String, String>();
        res.put("token", jwtService.generateToken(user.getUsername(), user.getRole()));
        res.put("role", user.getRole());
        return res ;
    }
    public Map<String, String> refreshToken(String refreshToken) {
        // Kiểm tra refreshToken có hợp lệ không
        String username = jwtService.validateRefreshToken(refreshToken);
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if (username == null ) {
            return null;
        }

        // Tạo accessToken mới
        String newAccessToken = jwtService.generateToken(user.getUsername(), user.getRole());

        Map<String, String> res = new HashMap<>();
        res.put("token", newAccessToken);
        res.put("role", "USER"); // Role của user
        return res;
    }
    public String encode(String password)
    {
        return passwordEncoder.encode(password);
    }
}
