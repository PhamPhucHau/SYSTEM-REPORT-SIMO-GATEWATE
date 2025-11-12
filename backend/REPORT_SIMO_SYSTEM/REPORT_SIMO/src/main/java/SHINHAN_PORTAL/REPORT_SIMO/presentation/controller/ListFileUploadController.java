package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.BatchApproveRequestDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.FileUploadRequestDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TKTTResponseDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.*;
import SHINHAN_PORTAL.REPORT_SIMO.common.UploadLogHelper;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.*;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UploadManagementDTO;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/upload_history")
public class ListFileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(ListFileUploadController.class);
    @Autowired
    private FileUploadProcessor fileUploadProcessor;
    @Autowired
    private UploadManagementService uploadManagementService;
    @Autowired
    private SimoController simoController;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private UploadLogHelper uploadLogHelper;
    @PostMapping
    public ResponseEntity<LIST_FILE_UPLOAD> uploadFileHistory(
            @Valid @RequestBody FileUploadRequestDTO request,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId
    ) {
        logger.info("Processing upload request for templateID: {}", request.getTemplateID());
        LIST_FILE_UPLOAD history = fileUploadProcessor.processUpload(request);

        // Log Upload action into UploadManagement
        UploadManagementDTO dto = new UploadManagementDTO();
        dto.setUsername(request.getUsername());
        dto.setUserRole(userRole);
        dto.setTemplateID(request.getTemplateID());
        dto.setMonthYear(request.getMonthYear());
        dto.setActionType("UPLOAD");
        dto.setFileName(request.getFileName());
        dto.setFilePath(null); // Not known here; file path is handled by FileUploadController
        dto.setStatusBefore(null);
        dto.setStatusAfter("00"); // After creating history, initial data status is 00
        dto.setDescription("Create upload history record");
        dto.setRequestId(requestId);
        dto.setCorrelationId(correlationId);
        uploadManagementService.create(dto);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/confirm")
    public ResponseEntity<LIST_FILE_UPLOAD> uploadDataMas(
            @RequestParam String id,
            @RequestHeader(value = "X-Username", required = false) String username,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @RequestHeader(value = "X-Status", required = false) String status
    ) {
        logger.info("Processing confirm request for id: {}", id);
        LIST_FILE_UPLOAD history = fileUploadProcessor.processConfirm(id,status);

        // Log CONFIRM action into UploadManagement (00 -> 10 for file upload record)
        UploadManagementDTO dto = new UploadManagementDTO();
        dto.setUsername(username != null ? username : history.getUsername());
        dto.setUserRole(userRole);
        dto.setTemplateID(history.getTemplateID());
        dto.setMonthYear(history.getMonthYear());
        dto.setActionType("CONFIRM");
        dto.setFileName(history.getFileName());
        dto.setFilePath(null);
        dto.setStatusBefore("00");
        dto.setStatusAfter("10");
        dto.setDescription("Confirm upload history and trigger processing");
        dto.setRequestId(requestId);
        dto.setCorrelationId(correlationId);
        uploadManagementService.create(dto);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/reject")
    public ResponseEntity<LIST_FILE_UPLOAD> rejectUpload(
            @RequestParam String id,
            @RequestHeader(value = "X-Username", required = false) String username,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId,
            @RequestHeader(value = "X-Status", required = false) String status
    ) {
        logger.info("Processing reject request for id: {}", id);
        LIST_FILE_UPLOAD history = historyService.findById(id, status);
        LIST_FILE_UPLOAD updatedHistory = historyService.updateById(id, "90");

        UploadManagementDTO dto = new UploadManagementDTO();
        dto.setUsername(username != null ? username : history.getUsername());
        dto.setUserRole(userRole);
        dto.setTemplateID(history.getTemplateID());
        dto.setMonthYear(history.getMonthYear());
        dto.setActionType("REJECT");
        dto.setFileName(history.getFileName());
        dto.setFilePath(null);
        dto.setStatusBefore(status);
        dto.setStatusAfter("90");
        dto.setDescription("Reject upload history and revert status to 90");
        dto.setRequestId(requestId);
        dto.setCorrelationId(correlationId);
        uploadManagementService.create(dto);

        return ResponseEntity.ok(updatedHistory);
    }

    @GetMapping("/list")
    public Page<LIST_FILE_UPLOAD> getFiles(
            @RequestParam(required = false) String templateID,
            @RequestParam(required = false) String monthYear,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return fileUploadProcessor.getFiles(templateID, monthYear, username, page, size);
    }

    /**
     * Batch approve and send files to SIMO
     * SRP: This endpoint handles batch approval workflow - getting data, merging, and sending to SIMO
     * DIP: Depends on abstractions (FileUploadProcessor, SimoController) not concrete implementations
     * 
     * @param templateID Template ID from query parameter
     * @param request Request body containing fileIds and fileNames
     * @param maYeuCau Mã yêu cầu from header
     * @param kyBaoCao Kỳ báo cáo from header (format: MM/yyyy)
     * @param username Username from header
     * @param userRole User role from header
     * @param requestId Request ID for tracing
     * @param correlationId Correlation ID for tracing
     * @return Response from SIMO
     */
    @PostMapping("/batch-approve")
    public ResponseEntity<TKTTResponseDTO> batchApprove(
            @RequestParam String templateID,
            @Valid @RequestBody BatchApproveRequestDTO request,
            @RequestHeader("maYeuCau") String maYeuCau,
            @RequestHeader("kyBaoCao") String kyBaoCao,
            @RequestHeader(value = "X-Username", required = false) String username,
            @RequestHeader(value = "X-User-Role", required = false) String userRole,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        
        logger.info("Processing batch approve request for templateID: {}, {} files", 
                    templateID, request.getFileIds().size());
        
        // Validate request
        if (request.getFileIds() == null || request.getFileIds().isEmpty()) {
            logger.error("FileIds list is empty");
            TKTTResponseDTO errorResponse = new TKTTResponseDTO();
            errorResponse.setCode("400");
            errorResponse.setMessage("FileIds list cannot be empty");
            errorResponse.setSuccess(false);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // Extract monthYear from kyBaoCao (format: MM/yyyy -> yyyyMM)
        // Example: "10/2025" -> "102025"
        String monthYear;
        if (kyBaoCao.contains("/")) {
            String[] parts = kyBaoCao.split("/");
            if (parts.length == 2) {
                String month = parts[0]; // MM
                String year = parts[1];  // yyyy
                monthYear = month+ year ; // yyyyMM
            } else {
                logger.error("Invalid kyBaoCao format: {}", kyBaoCao);
                TKTTResponseDTO errorResponse = new TKTTResponseDTO();
                errorResponse.setCode("400");
                errorResponse.setMessage("Invalid kyBaoCao format. Expected MM/yyyy");
                errorResponse.setSuccess(false);
                return ResponseEntity.badRequest().body(errorResponse);
            }
        } else {
            // Assume already in yyyyMM format
            monthYear = kyBaoCao;
        }
        
        logger.info("Extracted monthYear: {} from kyBaoCao: {}", monthYear, kyBaoCao);
        
        // Get merged data from all files
        List<Map<String, Object>> mergedData;
        try {
            mergedData = fileUploadProcessor.getFileDetail(
                request.getFileIds(), 
                request.getFileNames(), 
                templateID, 
                monthYear
            );
            logger.info("Successfully retrieved and merged {} records from {} files", 
                       mergedData.size(), request.getFileIds().size());
        } catch (Exception e) {
            logger.error("Error getting file details: {}", e.getMessage(), e);
            TKTTResponseDTO errorResponse = new TKTTResponseDTO();
            errorResponse.setCode("500");
            errorResponse.setMessage("Error retrieving file data: " + e.getMessage());
            errorResponse.setSuccess(false);
            return ResponseEntity.status(500).body(errorResponse);
        }
        
        // Send merged data to SIMO via SimoController
        try {
            TKTTResponseDTO simoResponse = simoController.uploadTKTTReport(
                maYeuCau,
                kyBaoCao,
                templateID,
                mergedData,
                username,
                userRole,
                requestId,
                correlationId
            );
            
            logger.info("Successfully sent batch data to SIMO. Response code: {}",
                    simoResponse.getCode());
            uploadLogHelper.logSendAction(templateID, maYeuCau, kyBaoCao, username, userRole, requestId, correlationId, "10", "20", "Successfully sent batch data to SIMO. Response code:" + simoResponse.getCode(),request.getFileNames().toString());  
            if ("00".equals(simoResponse.getCode())) {
                // Update status to 20 of list file upload and refresh modification timestamps
                for (String fileId : request.getFileIds()) {
                    try {
                        historyService.updateById(fileId, "20");
                       
                    } catch (Exception ex) {
                        logger.error("Failed to update LIST_FILE_UPLOAD status for id {}: {}", fileId, ex.getMessage(), ex);
                    }
                }
                for (String fileName : request.getFileNames()) {
                    try {
                        
                        uploadLogHelper.logSendAction(templateID, maYeuCau, kyBaoCao, username, userRole, requestId, correlationId, "10", "20", fileName+" Send To SIMO Success Response code:" + simoResponse.getCode(), fileName);  
                    } catch (Exception ex) {
                        logger.error("Failed to update LIST_FILE_UPLOAD status for fileName {}: {}", fileName, ex.getMessage(), ex);
                    }
                }
            }

            return ResponseEntity.ok(simoResponse);
        } catch (Exception e) {
            logger.error("Error sending data to SIMO: {}", e.getMessage(), e);
            uploadLogHelper.logSendAction(templateID, maYeuCau, kyBaoCao, username, userRole, requestId, correlationId, "10", "20", "Error sending data to SIMO: {}"+ e.getMessage(),request.getFileNames().toString());
            TKTTResponseDTO errorResponse = new TKTTResponseDTO();
            errorResponse.setCode("500");
            errorResponse.setMessage("Error sending data to SIMO: " + e.getMessage());
            errorResponse.setSuccess(false);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}