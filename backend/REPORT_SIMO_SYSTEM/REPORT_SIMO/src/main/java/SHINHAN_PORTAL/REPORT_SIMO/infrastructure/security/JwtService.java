package SHINHAN_PORTAL.REPORT_SIMO.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {
    private final String secretKey = "Sqvxsf9+JZVqi3b+pHSPsji7GpsFZMbkOI49gWozulcYCEa/bxSPngVpzE5V1wPcAPxMrhFyJH27dyx0YYrwow==";
    private final long expirationsMS = 86400000; // One Day
    private static final long REFRESH_EXPIRATION_TIME = 7 * 24 * 3600 * 1000; // 7 days
    public String generateToken(String username,String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationsMS))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            return !claims.getExpiration().before(new Date()); // Kiểm tra token hết hạn
        } catch (Exception ex) {
            return false;
        }
    }
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public  String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public  String validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return claims.getSubject(); // Trả về username nếu token hợp lệ
        } catch (Exception e) {
            return null; // Token không hợp lệ
        }
    }
}
