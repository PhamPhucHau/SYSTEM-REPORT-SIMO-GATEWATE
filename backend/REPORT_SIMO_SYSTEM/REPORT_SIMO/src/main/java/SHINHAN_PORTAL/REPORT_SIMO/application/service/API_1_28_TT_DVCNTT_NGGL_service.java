package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_28_TT_DVCNTT_NGGL;
import java.util.List;

public interface API_1_28_TT_DVCNTT_NGGL_service {
    List<API_1_28_TT_DVCNTT_NGGL> insert(List<API_1_28_TT_DVCNTT_NGGL> listData);
    List<API_1_28_TT_DVCNTT_NGGL> getData(String templateID, String monthYear, String status); // Added status
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}