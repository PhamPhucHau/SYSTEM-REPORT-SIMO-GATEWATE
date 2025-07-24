package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(Paths.get(uploadDir));

            // Tạo đường dẫn đích để lưu file
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());

            // Ghi file vào thư mục
            file.transferTo(filePath.toFile());

            return "Upload thành công: " + filePath;
        } catch (IOException e) {
            throw new FileStorageException("Không thể lưu file: " + file.getOriginalFilename() + "Path:" + uploadDir, e);
        }
    }
}