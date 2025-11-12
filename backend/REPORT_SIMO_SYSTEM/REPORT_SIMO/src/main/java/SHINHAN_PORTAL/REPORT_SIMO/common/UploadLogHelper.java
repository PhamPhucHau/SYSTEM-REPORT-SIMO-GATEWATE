package SHINHAN_PORTAL.REPORT_SIMO.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.UploadManagementDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.UploadManagementService;

@Component
public class UploadLogHelper {

    @Autowired
    private UploadManagementService uploadManagementService;

    public void logSendAction(String templateID, String maYeuCau, String kyBaoCao,
                              String username, String userRole, String requestId, String correlationId, String statusBefore, String statusAfter, String description, String fileName) {
        try {
            UploadManagementDTO dto = new UploadManagementDTO();
            dto.setUsername(username);
            dto.setUserRole(userRole);
            dto.setTemplateID(templateID);
            dto.setMonthYear(kyBaoCao);
            dto.setActionType("SEND");
            dto.setDescription("Send data to SIMO - maYeuCau=" + maYeuCau + ", kyBaoCao=" + kyBaoCao + " " + description);
            dto.setRequestId(requestId);
            dto.setCorrelationId(correlationId);
            dto.setStatusBefore(statusBefore);
            dto.setStatusAfter(statusAfter);
            dto.setFileName(fileName);
            uploadManagementService.create(dto);
        } catch (Exception ignored) {
            // avoid blocking flow if logging fails
        }
    }

    // Log tất cả thuộc tính từ DTO truyền vào, dùng lại service để tạo entity
    public void logAll(UploadManagementDTO dto) {
        try {
            uploadManagementService.create(dto);
        } catch (Exception ignored) {
            // avoid blocking flow if logging fails
        }
    }
}


