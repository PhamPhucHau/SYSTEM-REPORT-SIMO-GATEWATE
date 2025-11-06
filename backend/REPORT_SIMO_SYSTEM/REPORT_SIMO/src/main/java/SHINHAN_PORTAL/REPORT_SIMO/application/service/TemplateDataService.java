package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import java.util.List;

public interface TemplateDataService<T> 
{
    List<T> insert(List<T> listData);
    List<T> getData(String templateID, String period, String status);
    void deleteByTemplateIDAndMonthYearAndUsername(String templateId, String monthYear, String username);
}