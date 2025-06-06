package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.ReportSimoApplication;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.AuthResponse;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.RefreshTokenRequest;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UserDto;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.User;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.UserRepository;
import SHINHAN_PORTAL.REPORT_SIMO.infrastructure.security.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class login {
    private static final Logger logger = LoggerFactory.getLogger(ReportSimoApplication.class);
    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public Map<String, Object> greeting() {
        return Collections.singletonMap("message", "Hello world!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody UserDto user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "1234";
        String hashedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Hashed Password: " + hashedPassword);
        Map<String, String> res = authService.login(user.getUsername(), user.getPassword());
        AuthResponse response = new AuthResponse(res.get("token"), "SHBVN", "Bearer", 3600, res.get("role"));
        System.out.println(response);
//        ResponseCookie jwtCookie = ResponseCookie.from("authToken",token)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(24*60*60)
//                .sameSite("Strict")
//                .build();
//            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        // Kiểm tra refreshToken hợp lệ hay không
        Map<String, String> res = authService.refreshToken(refreshToken);
        if (res == null) {
            return ResponseEntity.status(403).body(null);
        }

        // Trả về accessToken mới
        AuthResponse response = new AuthResponse(res.get("token"), "SHBVN", "Bearer", 3600, res.get("role"));
        return ResponseEntity.ok(response);
    }
}
