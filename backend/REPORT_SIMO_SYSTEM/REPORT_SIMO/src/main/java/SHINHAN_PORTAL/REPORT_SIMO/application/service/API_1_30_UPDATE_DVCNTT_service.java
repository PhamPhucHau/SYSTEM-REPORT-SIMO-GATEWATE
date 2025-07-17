package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_30_UPDATE_DVCNTT;
import java.util.List;

public interface API_1_30_UPDATE_DVCNTT_service {
    List<API_1_30_UPDATE_DVCNTT> insert(List<API_1_30_UPDATE_DVCNTT> listData);
    List<API_1_30_UPDATE_DVCNTT> getData(String templateID, String monthYear);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
} 