package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_23_TOCHUC_service;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateDataService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_23_TOCHUC;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.API_1_23_TOCHUC_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("API_1_23_TOCHUC")
public class API_1_23_TOCHUC_service_Impl implements TemplateDataService<API_1_23_TOCHUC>, API_1_23_TOCHUC_service {
    @Autowired
    private API_1_23_TOCHUC_Repository repository;

    @Override
    public List<API_1_23_TOCHUC> insert(List<API_1_23_TOCHUC> listData) {
        return repository.saveAll(listData);
    }

    @Override
    public List<API_1_23_TOCHUC> getData(String templateID, String monthYear) {
        return repository.findByTemplateIDAndMonthYear(templateID, monthYear);
    }

    @Override
    public void deleteByTemplateIDAndMonthYearAndUsername(String templateId, String monthYear, String username) {
        repository.deleteByTemplateIDAndMonthYearAndUsername(templateId, monthYear, username);
    }
} 