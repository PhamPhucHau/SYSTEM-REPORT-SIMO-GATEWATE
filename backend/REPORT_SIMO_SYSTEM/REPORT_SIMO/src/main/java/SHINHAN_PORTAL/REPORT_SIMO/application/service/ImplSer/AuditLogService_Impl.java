package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.AuditLogDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.AuditLogSearchDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.AuditLogService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.AuditLog;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuditLogService_Impl implements AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    private static final SimpleDateFormat[] DATE_FORMATS = {
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
        new SimpleDateFormat("yyyy-MM-dd")
    };
    
    @Override
    public AuditLog logAction(String actionType, String userRole, String resourceType, String resourceId, String description) {
        HttpServletRequest request = getCurrentRequest();
        return logActionWithDetails(actionType, userRole, resourceType, resourceId, description, 
                                 getEndpoint(request), getHttpMethod(request), 
                                 getIpAddress(request), getUserAgent(request), null);
    }
    
    @Override
    public AuditLog logActionWithDetails(String actionType, String userRole, String resourceType, String resourceId, 
                                       String description, String endpoint, String httpMethod, 
                                       String ipAddress, String userAgent, String responseStatus) {
        
        AuditLog auditLog = new AuditLog();
        
        // Lấy thông tin user hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            // Lấy thông tin user từ database nếu cần
            auditLog.setUsername(username);
            // Có thể set userId và userRole nếu cần
        }
        auditLog.setUserRole(userRole);
        // Set thông tin hành động
        auditLog.setActionType(actionType);
        auditLog.setResourceType(resourceType);
        auditLog.setResourceId(resourceId);
        auditLog.setDescription(description);
        
        // Set thông tin request
        auditLog.setEndpoint(endpoint);
        auditLog.setHttpMethod(httpMethod);
        auditLog.setIpAddress(ipAddress);
        auditLog.setUserAgent(userAgent);
        auditLog.setResponseStatus(responseStatus);
        
        // Set timestamp
        auditLog.setTimestamp(new Date());
        
        return auditLogRepository.save(auditLog);
    }
    
    @Override
    public AuditLog logLogin(String username, String ipAddress, String userAgent,String userRole) {
        return logActionWithDetails("LOGIN",userRole, "AUTH", null, 
                                  "User logged in successfully", 
                                  "/login", "POST", ipAddress, userAgent, null);
    }
    
    @Override
    public AuditLog logLogout(String username, String ipAddress,String userRole) {
        return logActionWithDetails("LOGOUT",userRole, "AUTH", null, 
                                  "User logged out", 
                                  "/logout", "POST", ipAddress, null, null);
    }
    
    @Override
    public AuditLog logFileUpload(String filename, String fileSize, String ipAddress,String userRole) {
        return logActionWithDetails("UPLOAD",userRole, "FILE", filename, 
                                  "File uploaded: " + filename + " (" + fileSize + ")", 
                                  "/api/upload", "POST", ipAddress, null, null);
    }
    
    @Override
    public AuditLog logApiCall(String apiEndpoint,String userRole, String method, String requestBody, String ipAddress, String responseStatus) {
        return logActionWithDetails("API_CALL", userRole, "API", apiEndpoint, "API called: " + method + " " + apiEndpoint, apiEndpoint, method, ipAddress, null, responseStatus);
    }
    
    @Override
    public List<AuditLog> searchLogs(AuditLogSearchDTO searchDTO) {
        try {
            // Convert string dates to Date objects
            Date startDate = parseDate(searchDTO.getStartDate());
            Date endDate = parseDate(searchDTO.getEndDate());
            
            System.out.println("Search params - username: " + searchDTO.getUsername() + 
                              ", actionType: " + searchDTO.getActionType() + 
                              ", startDate: " + startDate + 
                              ", endDate: " + endDate);
            
            // Priority order for search:
            // 1. Date range search (most common) - Ưu tiên cao nhất

            
            // 2. Username search
            if (searchDTO.getUsername() != null && !searchDTO.getUsername().trim().isEmpty()) {
                System.out.println("Username search: " + searchDTO.getUsername());
                List<AuditLog> results = auditLogRepository.findByUsernameOrderByTimestampDesc(searchDTO.getUsername());
                System.out.println("Found " + results.size() + " logs for username");
                return results;
            }
            
            // 3. Action type search
            if (searchDTO.getActionType() != null && !searchDTO.getActionType().trim().isEmpty()) {
                System.out.println("Action type search: " + searchDTO.getActionType());
                List<AuditLog> results = auditLogRepository.findByActionTypeOrderByTimestampDesc(searchDTO.getActionType());
                System.out.println("Found " + results.size() + " logs for action type");
                return results;
            }
            
            // 4. Resource type search
            if (searchDTO.getResourceType() != null && !searchDTO.getResourceType().trim().isEmpty()) {
                System.out.println("Resource type search: " + searchDTO.getResourceType());
                List<AuditLog> results = auditLogRepository.findByResourceTypeOrderByTimestampDesc(searchDTO.getResourceType());
                System.out.println("Found " + results.size() + " logs for resource type");
                return results;
            }
            
            // 5. Search term
            if (searchDTO.getSearchTerm() != null && !searchDTO.getSearchTerm().trim().isEmpty()) {
                System.out.println("Search term: " + searchDTO.getSearchTerm());
                List<AuditLog> results = auditLogRepository.findByUsernameOrDescriptionContaining(searchDTO.getSearchTerm());
                System.out.println("Found " + results.size() + " logs for search term");
                return results;
            }
            if (startDate != null && endDate != null) {
                System.out.println("Date range search: " + startDate + " to " + endDate);
                List<AuditLog> results = auditLogRepository.findByTimestampBetweenOrderByTimestampDesc(startDate, endDate);
                System.out.println("Found " + results.size() + " logs in date range");
                return results;
            }
            // 6. Default: return all logs (limit to recent 1000 for performance)
            System.out.println("Default search: all logs");
            List<AuditLog> results = auditLogRepository.findAllOrderByTimestampDesc();
            System.out.println("Found " + results.size() + " total logs");
            return results;
            
        } catch (Exception e) {
            System.out.println("Error in searchLogs: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error searching audit logs", e);
        }
    }
    
    @Override
    public List<AuditLog> getLogsByUser(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }
    
    @Override
    public List<AuditLog> getLogsByTimeRange(Date startDate, Date endDate, int page, int size) {
        if (startDate != null && endDate != null) {
            return auditLogRepository.findByTimestampBetweenOrderByTimestampDesc(startDate, endDate);
        }
        return auditLogRepository.findAll();
    }
    
    @Override
    public AuditLogDTO getLogStatistics(Date startDate, Date endDate) {
        // Implement statistics logic
        AuditLogDTO stats = new AuditLogDTO();
        // Add your statistics logic here
        return stats;
    }
    
    @Override
    public byte[] exportLogs(AuditLogSearchDTO searchDTO) {
        try {
            // Reuse search logic to get logs
            List<AuditLog> logs = searchLogs(searchDTO);

            StringBuilder sb = new StringBuilder();
            // CSV header
            sb.append("timestamp,username,userRole,actionType,resourceType,resourceId,description,endpoint,httpMethod,ipAddress,userAgent,responseStatus,errorMessage\n");

            for (AuditLog log : logs) {
                sb.append(csv(log.getTimestamp()))
                  .append(',').append(csv(log.getUsername()))
                  .append(',').append(csv(log.getUserRole()))
                  .append(',').append(csv(log.getActionType()))
                  .append(',').append(csv(log.getResourceType()))
                  .append(',').append(csv(log.getResourceId()))
                  .append(',').append(csv(log.getDescription()))
                  .append(',').append(csv(log.getEndpoint()))
                  .append(',').append(csv(log.getHttpMethod()))
                  .append(',').append(csv(log.getIpAddress()))
                  .append(',').append(csv(log.getUserAgent()))
                  .append(',').append(csv(log.getResponseStatus()))
                  .append(',').append(csv(log.getErrorMessage()))
                  .append('\n');
            }

            // Return UTF-8 bytes
            return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export audit logs", e);
        }
    }

    private String csv(Object value) {
        if (value == null) return "";
        String s = String.valueOf(value);
        // Escape quotes by doubling them, wrap in quotes if needed
        boolean needQuotes = s.contains(",") || s.contains("\n") || s.contains("\r") || s.contains("\"");
        if (s.contains("\"")) {
            s = s.replace("\"", "\"\"");
        }
        return needQuotes ? ('"' + s + '"') : s;
    }
    
    // Helper methods
    private HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private String getEndpoint(HttpServletRequest request) {
        return request != null ? request.getRequestURI() : null;
    }
    
    private String getHttpMethod(HttpServletRequest request) {
        return request != null ? request.getMethod() : null;
    }
    
    private String getIpAddress(HttpServletRequest request) {
        if (request == null) return null;
        
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
        return request != null ? request.getHeader("User-Agent") : null;
    }

    // Helper method to parse date strings
    private Date parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        
        System.out.println("Parsing date string: " + dateString);
        
        // Handle ISO 8601 format with timezone
        if (dateString.contains("T") && (dateString.endsWith("Z") || dateString.contains("+"))) {
            try {
                // Use ISO 8601 parser
                java.time.Instant instant = java.time.Instant.parse(dateString);
                Date result = Date.from(instant);
                System.out.println("Parsed ISO date: " + result + " (UTC)");
                return result;
            } catch (Exception e) {
                System.out.println("Failed to parse ISO date: " + e.getMessage());
            }
        }
        
        // Try other formats
        for (SimpleDateFormat format : DATE_FORMATS) {
            try {
                Date result = format.parse(dateString);
                System.out.println("Parsed date with format: " + result);
                return result;
            } catch (ParseException e) {
                // Continue to next format
            }
        }
        
        // If none of the formats work, return null
        System.out.println("Failed to parse date: " + dateString);
        return null;
    }
     // Ghi log cho các hành động trên frontend
     @Override
     public AuditLog logPageAccess(String pageName, String username, String userRole, String ipAddress, String userAgent, String responseStatus) {
         return logActionWithDetails("PAGE_ACCESS", userRole, "PAGE", pageName, 
                                   "User accessed page: " + pageName, 
                                   "/frontend/" + pageName.toLowerCase(), "GET", ipAddress, userAgent, responseStatus);
     }
     
     @Override
     public AuditLog logFileManagementAction(String action, String filename, String username, String userRole, String ipAddress, String userAgent, String responseStatus) {
         return logActionWithDetails("FILE_MANAGEMENT", userRole, "FILE", filename, 
                                   "File management action: " + action + " - " + filename, 
                                   "/api/files/" + action.toLowerCase(), "POST", ipAddress, userAgent, responseStatus);
     }
     
     @Override
     public AuditLog logUserManagementAction(String action, String targetUser, String username, String userRole, String ipAddress, String userAgent, String responseStatus) {
         return logActionWithDetails("USER_MANAGEMENT", userRole, "USER", targetUser, 
                                   "User management action: " + action + " - " + targetUser, 
                                   "/api/users/" + action.toLowerCase(), "POST", ipAddress, userAgent, responseStatus);
     }
     
     @Override
     public AuditLog logTemplateAction(String action, String templateName, String username, String userRole, String ipAddress, String userAgent, String responseStatus) {
         return logActionWithDetails("TEMPLATE_ACTION", userRole, "TEMPLATE", templateName, 
                                   "Template action: " + action + " - " + templateName, 
                                   "/api/templates/" + action.toLowerCase(), "POST", ipAddress, userAgent, responseStatus);
     }
     
     @Override
     public AuditLog logDataUploadAction(String action, String dataType, String username, String userRole, String ipAddress, String userAgent, String responseStatus) {
         return logActionWithDetails("DATA_UPLOAD", userRole, "DATA", dataType, 
                                   "Data upload action: " + action + " - " + dataType, 
                                   "/api/upload/" + action.toLowerCase(), "POST", ipAddress, userAgent, responseStatus);
     }
     
     @Override
     public AuditLog logSearchAction(String searchType, String searchTerm, String username, String userRole, String ipAddress, String userAgent, String responseStatus) {
         return logActionWithDetails("SEARCH", userRole, "SEARCH", searchType, 
                                   "Search action: " + searchType + " - " + searchTerm, 
                                   "/api/search/" + searchType.toLowerCase(), "GET", ipAddress, userAgent, responseStatus);
     }
     
     @Override
     public AuditLog logExportAction(String exportType, String format, String username, String userRole, String ipAddress, String userAgent, String responseStatus) {
         return logActionWithDetails("EXPORT", userRole, "EXPORT", exportType, 
                                   "Export action: " + exportType + " - " + format, 
                                   "/api/export/" + exportType.toLowerCase(), "GET", ipAddress, userAgent, responseStatus);
     }
} 