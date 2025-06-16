package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import SHINHAN_PORTAL.REPORT_SIMO.application.mapper.FileUploadMapper;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_6_tktt_dinh_ky_service;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_8_update_tktt_nnngl_service;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateProcessor;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_6_tktt_dinh_ky;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_8_update_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;
@Component
public class API_1_8_Processor implements TemplateProcessor {

    @Autowired
    private API_1_8_update_tktt_nnngl_service service;

    @Autowired
    private FileUploadMapper mapper;

    @Override
    public void process(LIST_FILE_UPLOAD fileUpload, List<Map<String, Object>> rawData) {
        service.deleteByTemplateIDAndMonthYearAndUsername(
            fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername());
        List<API_1_8_update_tktt_nnngl> formattedData = rawData.stream()
            .map(data -> mapper.mapToAPI_1_8_update_tktt_nnngl(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
            .collect(Collectors.toList());
        service.insert(formattedData);
    }
}
