package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_28_TT_DVCNTT_NGGL_service;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateDataService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_28_TT_DVCNTT_NGGL;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.API_1_28_TT_DVCNTT_NGGL_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("API_1_28_TT_DVCNTT_NGGL")
public class API_1_28_TT_DVCNTT_NGGL_service_Impl implements TemplateDataService<API_1_28_TT_DVCNTT_NGGL>, API_1_28_TT_DVCNTT_NGGL_service {
    @Autowired
    private API_1_28_TT_DVCNTT_NGGL_Repository repository;

    @Override
    public List<API_1_28_TT_DVCNTT_NGGL> insert(List<API_1_28_TT_DVCNTT_NGGL> listData) {
        return repository.saveAll(listData);
    }

    @Override
    public List<API_1_28_TT_DVCNTT_NGGL> getData(String templateID, String monthYear) {
        return repository.findByTemplateIDAndMonthYear(templateID, monthYear);
    }

    @Override
    public void deleteByTemplateIDAndMonthYearAndUsername(String templateId, String monthYear, String username) {
        repository.deleteByTemplateIDAndMonthYearAndUsername(templateId, monthYear, username);
    }
} 