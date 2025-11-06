package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TemplateDTO;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface TemplateService {
    TemplateDTO create(TemplateDTO dto);
    TemplateDTO update(String id, TemplateDTO dto);
    void delete(String id);
    List<TemplateDTO> getAll();
    ResponseEntity<Resource> downloadTemplate(String templateID);
}