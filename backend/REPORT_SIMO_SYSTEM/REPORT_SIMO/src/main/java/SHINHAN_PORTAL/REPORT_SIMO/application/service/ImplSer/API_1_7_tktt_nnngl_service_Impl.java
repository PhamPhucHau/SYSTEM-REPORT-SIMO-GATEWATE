 package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_7_tktt_nnngl_service;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateDataService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_7_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.API_1_7_tktt_nnngl_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("API_1_7_TTDS_TKTT_NNGL")
public class API_1_7_tktt_nnngl_service_Impl implements TemplateDataService<API_1_7_tktt_nnngl>,API_1_7_tktt_nnngl_service {

    @Autowired
    private API_1_7_tktt_nnngl_Repository repository;

    @Override
    public List<API_1_7_tktt_nnngl> insert(List<API_1_7_tktt_nnngl> listData) {
        return repository.saveAll(listData);
    }

    @Override
    public List<API_1_7_tktt_nnngl> getData(String templateID, String monthYear) {
        return repository.findByTemplateIDAndMonthYear(templateID, monthYear);
    }
    @Override
    public void deleteByTemplateIDAndMonthYearAndUsername(String templateId, String monthYear, String username) {
        repository.deleteByTemplateIDAndMonthYearAndUsername(templateId, monthYear, username);
    }
}

