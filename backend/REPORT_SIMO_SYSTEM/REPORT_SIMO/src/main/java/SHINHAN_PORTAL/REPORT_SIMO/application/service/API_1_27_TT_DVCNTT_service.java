package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_27_TT_DVCNTT;
import java.util.List;

public interface API_1_27_TT_DVCNTT_service {
    List<API_1_27_TT_DVCNTT> insert(List<API_1_27_TT_DVCNTT> listData);
    List<API_1_27_TT_DVCNTT> getData(String templateID, String monthYear, String status); // Added status
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}