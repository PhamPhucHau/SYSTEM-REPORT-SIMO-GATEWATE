package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import java.util.List;
import java.util.Map;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;

public interface TemplateProcessor {
    void process(LIST_FILE_UPLOAD fileUpload, List<Map<String, Object>> rawData);
}
