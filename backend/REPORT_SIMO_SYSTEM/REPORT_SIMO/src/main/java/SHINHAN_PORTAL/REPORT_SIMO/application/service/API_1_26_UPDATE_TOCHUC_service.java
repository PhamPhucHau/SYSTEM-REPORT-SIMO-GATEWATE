package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_26_UPDATE_TOCHUC;
import java.util.List;

public interface API_1_26_UPDATE_TOCHUC_service {
    List<API_1_26_UPDATE_TOCHUC> insert(List<API_1_26_UPDATE_TOCHUC> listData);
    List<API_1_26_UPDATE_TOCHUC> getData(String templateID, String monthYear, String status); // Added status
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}