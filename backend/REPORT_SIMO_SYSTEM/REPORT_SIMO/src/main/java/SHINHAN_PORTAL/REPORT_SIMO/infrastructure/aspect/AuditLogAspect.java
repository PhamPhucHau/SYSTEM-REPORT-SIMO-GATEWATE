package SHINHAN_PORTAL.REPORT_SIMO.infrastructure.aspect;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.AuditLogService;
import SHINHAN_PORTAL.REPORT_SIMO.infrastructure.security.JwtService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuditLogAspect {
    
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private  JwtService jwtService;
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object logAction(ProceedingJoinPoint joinPoint) throws Throwable {
        
        long startTime = System.currentTimeMillis();
        Object result = null;
        String actionType = "UNKNOWN";
        String resourceType = "UNKNOWN";
        String resourceId = null;
        String description = "Action executed";
        String userRole = "UNKNOWN";

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        // Extract user information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userRole = authentication.getAuthorities().toString();
        }

        // ---- 🔑 Handle Authorization Header ----
        String authHeader = request.getHeader("Authorization");
        String username = "ANONYMOUS";
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
                userRole = jwtService.extractUserRole(token);
            } catch (Exception e) {
                // Nếu token lỗi thì gán là INVALID
                username = "INVALID_TOKEN";
                userRole = "UNKNOWN";
            }
        }

        try {
            // Xác định action type dựa trên method name
            String methodName = joinPoint.getSignature().getName();
            if (methodName.contains("create") || methodName.contains("add")) {
                actionType = "CREATE";
            } else if (methodName.contains("update") || methodName.contains("modify")) {
                actionType = "UPDATE";
            } else if (methodName.contains("delete") || methodName.contains("remove")) {
                actionType = "DELETE";
            } else if (methodName.contains("upload")) {
                actionType = "UPLOAD";
            }

            // Xác định resource type dựa trên class name
            String className = joinPoint.getTarget().getClass().getSimpleName();
            if (className.contains("User")) {
                resourceType = "USER";
            } else if (className.contains("Template")) {
                resourceType = "TEMPLATE";
            } else if (className.contains("File")) {
                resourceType = "FILE";
            }

            // Thực thi method
            result = joinPoint.proceed();
            description = result != null ? result.toString() : "No result";

            // Ghi log thành công
            auditLogService.logAction(actionType, userRole, resourceType, resourceId,
                    description + " - Success");

        } catch (Exception e) {
            // Ghi log lỗi
            auditLogService.logAction(actionType, userRole, resourceType, resourceId,
                    description + " - Error: " + e.getMessage());
            throw e;
        }

        return result;
    }
} 