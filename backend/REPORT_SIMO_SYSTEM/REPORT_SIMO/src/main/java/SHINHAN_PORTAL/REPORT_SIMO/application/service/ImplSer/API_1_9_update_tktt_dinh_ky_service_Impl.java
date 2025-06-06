package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_9_update_tktt_dinh_ky_service;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_9_update_tktt_dinh_ky;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.API_1_9_update_tktt_dinh_ky_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class API_1_9_update_tktt_dinh_ky_service_Impl implements API_1_9_update_tktt_dinh_ky_service {
    @Autowired
    private API_1_9_update_tktt_dinh_ky_Repository listFileUploadRepository ;
    @Override
    public List<API_1_9_update_tktt_dinh_ky> insert(List<API_1_9_update_tktt_dinh_ky> listData) {
        return listFileUploadRepository.saveAll(listData);
    }

    @Override
    public List<API_1_9_update_tktt_dinh_ky> getData(String template_id, String period) {
        return listFileUploadRepository.findByTemplateIDAndMonthYear(template_id, period);
    }
}
