package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.FileUploadRequestDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.*;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/upload_history")
public class ListFileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(ListFileUploadController.class);
    @Autowired
    private FileUploadProcessor fileUploadProcessor;
    @Autowired
    private UploadManagementService uploadManagementService;
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
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId
    ) {
        logger.info("Processing confirm request for id: {}", id);
        LIST_FILE_UPLOAD history = fileUploadProcessor.processConfirm(id);

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

    @GetMapping("/list")
    public Page<LIST_FILE_UPLOAD> getFiles(
            @RequestParam(required = false) String templateID,
            @RequestParam(required = false) String monthYear,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return fileUploadProcessor.getFiles(templateID, monthYear, username, page, size);
    }

}