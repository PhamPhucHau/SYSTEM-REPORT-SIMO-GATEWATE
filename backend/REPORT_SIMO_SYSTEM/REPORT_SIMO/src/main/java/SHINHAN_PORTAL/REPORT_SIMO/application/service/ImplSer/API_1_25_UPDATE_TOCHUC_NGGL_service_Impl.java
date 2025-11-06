package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_25_UPDATE_TOCHUC_NGGL_service;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateDataService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_25_UPDATE_TOCHUC_NGGL;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.API_1_25_UPDATE_TOCHUC_NGGL_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("API_1_25_UPDATE_TTDS_TKTT_TC_NGGL")
public class API_1_25_UPDATE_TOCHUC_NGGL_service_Impl implements TemplateDataService<API_1_25_UPDATE_TOCHUC_NGGL>, API_1_25_UPDATE_TOCHUC_NGGL_service {
    @Autowired
    private API_1_25_UPDATE_TOCHUC_NGGL_Repository repository;

    @Override
    public List<API_1_25_UPDATE_TOCHUC_NGGL> insert(List<API_1_25_UPDATE_TOCHUC_NGGL> listData) {
        return repository.saveAll(listData);
    }

    @Override
    public List<API_1_25_UPDATE_TOCHUC_NGGL> getData(String templateID, String monthYear, String status) {
        return repository.findByTemplateIDAndMonthYearAndStatus(templateID, monthYear, status);
    }

    @Override
    public void deleteByTemplateIDAndMonthYearAndUsername(String templateId, String monthYear, String username) {
        repository.deleteByTemplateIDAndMonthYearAndUsername(templateId, monthYear, username);
    }
} 