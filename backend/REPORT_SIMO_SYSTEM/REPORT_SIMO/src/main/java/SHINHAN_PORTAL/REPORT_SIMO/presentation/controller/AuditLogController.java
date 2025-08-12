package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.AuditLogDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.AuditLogSearchDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.AuditLogService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.AuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/audit-logs")
// @PreAuthorize("hasRole('ADMIN')") // Chỉ admin mới có thể xem audit logs
public class AuditLogController {
    
    @Autowired
    private AuditLogService auditLogService;
    
    @GetMapping("/search")
    public ResponseEntity<List<AuditLog>> searchLogs(@ModelAttribute AuditLogSearchDTO searchDTO) {
        try {
            List<AuditLog> logs = auditLogService.searchLogs(searchDTO);
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getLogsByUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        List<AuditLog> logs = auditLogService.getLogsByUser(userId, page, size);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/time-range")
    public ResponseEntity<List<AuditLog>> getLogsByTimeRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        // Parse dates from string
        // Implementation depends on your date format
        List<AuditLog> logs = auditLogService.getLogsByTimeRange(null, null, page, size);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<AuditLogDTO> getLogStatistics(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        
        // Parse dates from string
        AuditLogDTO stats = auditLogService.getLogStatistics(null, null);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportLogs(@ModelAttribute AuditLogSearchDTO searchDTO) {
        byte[] exportData = auditLogService.exportLogs(searchDTO);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=audit-logs.csv")
                .body(exportData);
    }
} 