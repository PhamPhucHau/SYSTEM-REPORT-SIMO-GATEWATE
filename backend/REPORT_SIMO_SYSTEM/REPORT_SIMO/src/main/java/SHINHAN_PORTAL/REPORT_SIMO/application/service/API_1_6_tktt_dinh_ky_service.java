package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_6_tktt_dinh_ky;

import java.util.List;

public interface API_1_6_tktt_dinh_ky_service {
    List<API_1_6_tktt_dinh_ky> insert(List<API_1_6_tktt_dinh_ky> listData);
    List<API_1_6_tktt_dinh_ky> getData(String template_id, String period, String status);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateID, String monthYear, String username);
}
