package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UploadManagementDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.UploadManagementService;

@RestController
@RequestMapping("/api")
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(ListFileUploadController.class);
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UploadManagementService uploadManagementService;

    @PostMapping("/upload")
    public String uploadFile(
        @RequestParam("file") MultipartFile file,
        @RequestHeader(value = "X-Username", required = false) String username,
        @RequestHeader(value = "X-User-Role", required = false) String userRole,
        @RequestHeader(value = "X-Template-ID", required = false) String templateID,
        @RequestHeader(value = "X-Month-Year", required = false) String monthYear,
        @RequestHeader(value = "X-Request-Id", required = false) String requestId,
        @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId
    ) {
        try {
            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(Paths.get(uploadDir));

            // Tạo đường dẫn đích để lưu file
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());

            // Ghi file vào thư mục
            file.transferTo(filePath.toFile());

            // Ghi log quản trị upload (WHAT/WHERE/WHEN/WHY/HOW)
            UploadManagementDTO dto = new UploadManagementDTO();
            dto.setUsername(username);
            dto.setUserRole(userRole);
            dto.setTemplateID(templateID);
            dto.setMonthYear(monthYear);
            dto.setActionType("UPLOAD");
            dto.setFileName(file.getOriginalFilename());
            dto.setFilePath(filePath.toString());
            dto.setStatusBefore(null);
            dto.setStatusAfter(null);
            dto.setDescription("User uploaded a file to server storage");
            dto.setRequestId(requestId);
            dto.setCorrelationId(correlationId);
            uploadManagementService.create(dto);

            return "Upload thành công: " + filePath;
            
   } catch (IOException e) {
        logger.error("===== [UPLOAD_FILE] LỖI IO EXCEPTION =====");
        logger.error("[UPLOAD_FILE] Không thể lưu file {} tại đường dẫn: {}",
                     file.getOriginalFilename(), uploadDir);
        logger.error("[UPLOAD_FILE] Nguyên nhân gốc: {}", e.getMessage(), e);

        throw new FileStorageException("Không thể lưu file: " + file.getOriginalFilename(), e);

    } catch (Exception e) {
        logger.error("===== [UPLOAD_FILE] LỖI KHÁC =====");
        logger.error("[UPLOAD_FILE] Lỗi không xác định: {}", e.getMessage(), e);
        throw e;
    }
    }
}