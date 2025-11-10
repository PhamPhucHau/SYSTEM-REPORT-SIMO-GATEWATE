package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_34_UPDATE_TNH;
import java.util.List;

public interface API_1_34_UPDATE_TNH_service {
    List<API_1_34_UPDATE_TNH> insert(List<API_1_34_UPDATE_TNH> listData);
    List<API_1_34_UPDATE_TNH> getData(String templateID, String monthYear, String status); // Added status
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}
