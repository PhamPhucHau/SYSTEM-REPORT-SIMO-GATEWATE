package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_31_TT_TNH;
import java.util.List;

public interface API_1_31_TT_TNH_service {
    List<API_1_31_TT_TNH> insert(List<API_1_31_TT_TNH> listData);
    List<API_1_31_TT_TNH> getData(String templateID, String monthYear, String status); // Added status
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}
