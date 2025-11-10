package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TemplateDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.Template;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.TemplateRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TemplateServi implements TemplateService {
    @Autowired
    private TemplateRepository templateRepository;
    private static final Logger logger = LoggerFactory.getLogger(TemplateServi.class);
    @Value("${template.file.path}")
    private String templateFilePath;

    @Override
    public TemplateDTO create(TemplateDTO dto) {
        logger.debug("Debug log create");
        System.out.println("AAAA Before"+ dto.toString());
        Template template = Template.builder()
                .templateID(dto.getTemplateID())
                .name(dto.getName())
                .schemaJson(dto.getSchemaJson())
                .build();

        System.out.println("AAAA Convert"+ template.toString());
        return mapToDTO(templateRepository.save(template));
    }

    @Override
    public TemplateDTO update(String id, TemplateDTO dto) {
        Template template = templateRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Template không tồn tại"));

        template.setTemplateID(dto.getTemplateID());
        template.setName(dto.getName());
        template.setSchemaJson(dto.getSchemaJson());

        return mapToDTO(templateRepository.save(template));
    }

    @Override
    public void delete(String id) {
        templateRepository.deleteById(new ObjectId(id));
    }

    @Override
    public List<TemplateDTO> getAll() {
        return templateRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TemplateDTO mapToDTO(Template template) {
        logger.info("Template @@@"+ template.toString());
        return TemplateDTO.builder()
                .id(template.getId().toHexString()) // giữ dạng String
                .templateID(template.getTemplateID())
                .name(template.getName())
                .schemaJson(template.getSchemaJson())
                .build();
    }
    @Override
    public ResponseEntity<Resource> downloadTemplate(String templateID) {
        try {
            // 🔹 1. Tạo đường dẫn tuyệt đối đến file template Excel dựa trên ID được truyền vào
            //      - "templateStoragePath" là thư mục gốc chứa file (được cấu hình trong application.yml)
            //      - "templateID" là mã template mà frontend gửi lên, ví dụ: "TEMPLATE_001"
            //      - ".xlsx" là phần mở rộng file Excel
            Path filePath = Paths.get(templateFilePath, templateID + ".xlsx");
    
            // 🔹 2. Kiểm tra file có tồn tại trong thư mục lưu trữ hay không
            //      - Nếu không tồn tại, trả về HTTP 404 (Not Found)
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }
    
            // 🔹 3. Tạo đối tượng "Resource" trỏ đến file cần tải
            //      - "UrlResource" cho phép Spring Boot stream dữ liệu từ đường dẫn file (URI)
            //      - Đây là cách an toàn & hiệu quả để trả file qua HTTP response
            Resource resource = new UrlResource(filePath.toUri());
    
            // 🔹 4. Lấy tên file thực tế (ví dụ: "TEMPLATE_001.xlsx")
            //      - Sẽ được dùng trong header Content-Disposition để gợi ý tên khi tải xuống
            String fileName = filePath.getFileName().toString();
    
            // 🔹 5. Trả về ResponseEntity chứa file (Resource)
            //      - ResponseEntity cho phép tùy chỉnh toàn bộ phần header và body HTTP
            //      - MediaType: chỉ định kiểu MIME của file (Excel .xlsx)
            //      - Header Content-Disposition: cho trình duyệt hiểu đây là file tải về, không phải hiển thị trực tiếp
            //      - Body: chính là file resource
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
    
        } catch (Exception e) {
            // 🔹 6. Nếu có lỗi bất ngờ (VD: không đọc được file, sai permission, đường dẫn lỗi, v.v.)
            //      - Trả về HTTP 500 (Internal Server Error)
            //      - Trong thực tế, bạn có thể log lỗi để tiện debug
            return ResponseEntity.internalServerError().build();
        }
    }
}
    
