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
       
        logger.info("[DownloadTemplate] Request download for templateID={}", templateID);

        try {
            Path filePath = Paths.get(templateFilePath, templateID + ".xlsx");
            logger.info("[DownloadTemplate] Full file path resolved: {}", filePath.toAbsolutePath());

            if (!Files.exists(filePath)) {
                logger.warn("[DownloadTemplate] File NOT FOUND for templateID={} at path={}",
                        templateID, filePath.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            logger.info("[DownloadTemplate] File found, preparing response → filename={}", filePath.getFileName());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                    .body(resource);

        } catch (Exception e) {
            logger.error("[DownloadTemplate] ERROR while processing templateID={}. Message={}",
                    templateID, e.getMessage(), e);

            return ResponseEntity.internalServerError().build();        }
        }
}
    
