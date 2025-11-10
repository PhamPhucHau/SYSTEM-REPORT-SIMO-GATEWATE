package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UploadManagementDTO;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.UploadManagement;

import java.util.Date;
import java.util.List;

public interface UploadManagementService {
    UploadManagement create(UploadManagementDTO dto);

    // Truy vấn linh hoạt theo thời gian và bộ lọc phổ biến
    List<UploadManagement> findByDateRange(Date start, Date end);
    List<UploadManagement> findByUsername(String username);
    List<UploadManagement> findByTemplate(String templateID);
    List<UploadManagement> findByActionType(String actionType);
    List<UploadManagement> searchByTemplateAndAction(Date start, Date end, String templateID, String actionType);

    // Flexible search: any combination of start/end with optional templateID/actionType
    List<UploadManagement> search(Date start, Date end, String templateID, String actionType);
}


