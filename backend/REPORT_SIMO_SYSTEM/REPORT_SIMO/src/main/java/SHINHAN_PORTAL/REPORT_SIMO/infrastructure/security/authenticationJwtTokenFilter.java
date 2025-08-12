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
import SHINHAN_PORTAL.REPORT_SIMO.application.service.AuditLogService;

@Component
public class authenticationJwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(ReportSimoApplication.class);
    private final customUserDetailService userDetailService;
    private final JwtService jwtService;
    
    @Autowired
    private AuditLogService auditLogService;

    public authenticationJwtTokenFilter(JwtService jwtService, customUserDetailService userDetailService  ) {
        this.jwtService = jwtService;
        this.userDetailService = userDetailService ;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        
        logger.info("JWT Filter - Request: {} {}", httpMethod, requestURI);
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; 
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        String userRole = jwtService.extractUserRole(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                
                // Ghi log login thành công (chỉ khi lần đầu xác thực)
                try {
                    auditLogService.logLogin(username, userRole, getIpAddress(request), getUserAgent(request));
                } catch (Exception e) {
                    logger.error("Failed to log login action", e);
                }
            }
                    // Ghi log cho tất cả các request (sau khi xác thực)
        
        filterChain.doFilter(request, response);
        try {
            if (username != null) {
                logUserAction(request, username,userRole, response);
            }
        } catch (Exception e) {
            logger.error("Failed to log user action", e);
    }
    }
    }
        /**
     * Ghi log cho các hành động của user dựa trên endpoint và method
     */
    private void logUserAction(HttpServletRequest request, String username, String userRole, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        String ipAddress = getIpAddress(request);
        String userAgent = getUserAgent(request);
        int Response = response.getStatus();
        
        // Phân loại hành động dựa trên endpoint
        if (requestURI.startsWith("/api/users")) {
            // if (httpMethod.equals("GET")) {
            //     auditLogService.logUserManagementAction("VIEW", "user_list", username, ipAddress, userAgent);
            // } else 
            if (httpMethod.equals("POST")) {
                auditLogService.logUserManagementAction("CREATE", "new_user", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            } else if (httpMethod.equals("PUT")) {
                auditLogService.logUserManagementAction("UPDATE", "user", userRole, username, ipAddress, userAgent,String.valueOf(Response));
            } else if (httpMethod.equals("DELETE")) {
                auditLogService.logUserManagementAction("DELETE", "user", userRole, username, ipAddress, userAgent,String.valueOf(Response));
            }
        } else if (requestURI.startsWith("/api/files") || requestURI.startsWith("/api/upload")) {
            // if (httpMethod.equals("GET")) {
            //     auditLogService.logFileManagementAction("VIEW", "file_list", username, ipAddress, userAgent,String.valueOf(Response));
            // } else 
            if (httpMethod.equals("POST")) {
                auditLogService.logFileManagementAction("UPLOAD", "file", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            } else if (httpMethod.equals("DELETE")) {
                auditLogService.logFileManagementAction("DELETE", "file", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            }
        } else if (requestURI.startsWith("/api/templates")) {
            // if (httpMethod.equals("GET")) {
            //     auditLogService.logTemplateAction("VIEW", "template_list", username, ipAddress, userAgent);
            // } else 
            if (httpMethod.equals("POST")) {
                auditLogService.logTemplateAction("CREATE", "template", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            } else if (httpMethod.equals("PUT")) {
                auditLogService.logTemplateAction("UPDATE", "template", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            } else if (httpMethod.equals("DELETE")) {
                auditLogService.logTemplateAction("DELETE", "template", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            }
        } else if (requestURI.startsWith("/api/audit-logs")) {
            if (httpMethod.equals("GET")) {
                auditLogService.logSearchAction("AUDIT_LOG", "audit_search", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            } else if (requestURI.contains("/export")) {
                auditLogService.logExportAction("AUDIT_LOG", "CSV", username, userRole, ipAddress, userAgent,String.valueOf(Response));
            }
        } else if (requestURI.startsWith("/api/search")) {
            auditLogService.logSearchAction("GENERAL", "search", username, userRole, ipAddress, userAgent,String.valueOf(Response));
        } else if (requestURI.startsWith("/api/export")) {
            auditLogService.logExportAction("DATA", "EXCEL", username, userRole, ipAddress, userAgent,String.valueOf(Response));
        } else if (requestURI.startsWith("/api/")) {
            // Ghi log cho các API khác
            auditLogService.logApiCall(requestURI, userRole, httpMethod,null,ipAddress, String.valueOf(Response));
        }
    }
    
    // Helper methods để lấy IP và User Agent
    private String getIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
    private String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}
