package SHINHAN_PORTAL.REPORT_SIMO.infrastructure.security;

import SHINHAN_PORTAL.REPORT_SIMO.ReportSimoApplication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
@Component
public class authenticationJwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(ReportSimoApplication.class);
    private final customUserDetailService userDetailService;
    private final JwtService jwtService;

    public authenticationJwtTokenFilter(JwtService jwtService, customUserDetailService userDetailService  ) {
        this.jwtService = jwtService;
        this.userDetailService = userDetailService ;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.error("URL: "+request.getRequestURI());
        System.out.println("URL: "+request.getRequestURI());
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
    String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token); // Lấy username từ token

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                // Kiểm tra token có hợp lệ không
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        // 3. Tiếp tục xử lý request
        filterChain.doFilter(request, response);
    }
}
