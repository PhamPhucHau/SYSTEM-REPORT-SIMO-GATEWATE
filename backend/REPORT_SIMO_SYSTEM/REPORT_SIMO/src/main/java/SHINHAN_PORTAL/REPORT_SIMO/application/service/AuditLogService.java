package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.AuditLog;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.AuditLogDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.AuditLogSearchDTO;

import java.util.Date;
import java.util.List;

public interface AuditLogService {
    
    // Ghi log hành động
    AuditLog logAction(String actionType,String userRole, String resourceType, String resourceId, String description);
    
    // Ghi log với thông tin đầy đủ
    AuditLog logActionWithDetails(String actionType, String userRole, String resourceType, String resourceId, String description, String endpoint, String httpMethod, String ipAddress, String userAgent, String responseStatus);
    
    // Ghi log login
    AuditLog logLogin(String username,String userRole, String ipAddress, String userAgent);
    
    // Ghi log logout
    AuditLog logLogout(String username,String userRole, String ipAddress);
    
    // Ghi log upload file
    AuditLog logFileUpload(String filename,String userRole, String fileSize, String ipAddress);
    
    // Ghi log API call
    AuditLog logApiCall(String apiEndpoint, String userRole, String method, String requestBody, String ipAddress, String responseStatus);
    
    // Tìm kiếm log
    List<AuditLog> searchLogs(AuditLogSearchDTO searchDTO);
    
    // Lấy log theo user
    List<AuditLog> getLogsByUser(String userId, int page, int size);
    
    // Lấy log theo khoảng thời gian
    List<AuditLog> getLogsByTimeRange(Date startDate, Date endDate, int page, int size);
    
    // Lấy thống kê log
    AuditLogDTO getLogStatistics(Date startDate, Date endDate);
    
    // Export log
    byte[] exportLogs(AuditLogSearchDTO searchDTO);
    //
    // Ghi log cho các hành động trên frontend
    AuditLog logPageAccess(String pageName, String username, String userRole, String ipAddress, String userAgent, String responseStatus);
    AuditLog logFileManagementAction(String action, String filename, String username, String userRole, String ipAddress, String userAgent, String responseStatus);
    AuditLog logUserManagementAction(String action, String targetUser, String username, String userRole, String ipAddress, String userAgent, String responseStatus);
    AuditLog logTemplateAction(String action, String templateName, String username, String userRole, String ipAddress, String userAgent, String responseStatus);
    AuditLog logDataUploadAction(String action, String dataType, String username, String userRole, String ipAddress, String userAgent, String responseStatus);
    AuditLog logSearchAction(String searchType, String searchTerm, String username, String userRole, String ipAddress, String userAgent, String responseStatus);
    AuditLog logExportAction(String exportType, String format, String username, String userRole, String ipAddress, String userAgent, String responseStatus);
} 