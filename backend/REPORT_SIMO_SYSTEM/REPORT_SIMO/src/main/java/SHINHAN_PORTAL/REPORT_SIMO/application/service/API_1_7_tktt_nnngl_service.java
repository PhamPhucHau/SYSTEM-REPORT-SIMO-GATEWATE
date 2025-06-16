package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_7_tktt_nnngl;
import java.util.List;

public interface API_1_7_tktt_nnngl_service {
    List<API_1_7_tktt_nnngl> insert(List<API_1_7_tktt_nnngl> listData);
    List<API_1_7_tktt_nnngl> getData(String templateID, String monthYear);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}