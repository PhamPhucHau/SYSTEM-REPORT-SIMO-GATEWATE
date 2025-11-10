package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_31_TT_TNH_service;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateDataService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_31_TT_TNH;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.API_1_31_TT_TNH_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("API_1_31_TT_TNH")
public class API_1_31_TT_TNH_service_Impl implements TemplateDataService<API_1_31_TT_TNH>, API_1_31_TT_TNH_service {
    @Autowired
    private API_1_31_TT_TNH_Repository repository;

    @Override
    public List<API_1_31_TT_TNH> insert(List<API_1_31_TT_TNH> listData) {
        return repository.saveAll(listData);
    }

    @Override
    public List<API_1_31_TT_TNH> getData(String templateID, String monthYear, String status) {
        return repository.findByTemplateIDAndMonthYearAndStatus(templateID, monthYear, status);
    }

    @Override
    public void deleteByTemplateIDAndMonthYearAndUsername(String templateId, String monthYear, String username) {
        repository.deleteByTemplateIDAndMonthYearAndUsername(templateId, monthYear, username);
    }
}


