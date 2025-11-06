package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller ;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TemplateDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.util.List;


@RequestMapping("/api/templates")
@RestController
public class TemplateController {
    @Autowired
    private  TemplateService templateService;

    @PostMapping
    public ResponseEntity<TemplateDTO> createTemplate(@RequestBody TemplateDTO dto) {
        return ResponseEntity.ok(templateService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateDTO> updateTemplate(@PathVariable String id, @RequestBody TemplateDTO dto) {
        return ResponseEntity.ok(templateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable String id) {
        templateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TemplateDTO>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAll());
    }
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadTemplate(@RequestParam String templateID) {
        return templateService.downloadTemplate(templateID);
    }
    
}
