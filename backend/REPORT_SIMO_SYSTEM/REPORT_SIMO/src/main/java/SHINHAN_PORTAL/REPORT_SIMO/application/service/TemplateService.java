package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TemplateDTO;
import java.util.List;

public interface TemplateService {
    TemplateDTO create(TemplateDTO dto);
    TemplateDTO update(String id, TemplateDTO dto);
    void delete(String id);
    List<TemplateDTO> getAll();
}