package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_24_TOCHUC_NGGL;
import java.util.List;

public interface API_1_24_TOCHUC_NGGL_service {
    List<API_1_24_TOCHUC_NGGL> insert(List<API_1_24_TOCHUC_NGGL> listData);
    List<API_1_24_TOCHUC_NGGL> getData(String templateID, String monthYear);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
} 