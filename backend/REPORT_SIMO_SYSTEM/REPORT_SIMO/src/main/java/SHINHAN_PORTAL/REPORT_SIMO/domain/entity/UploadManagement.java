package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "upload_management")
public class UploadManagement {

    @Id
    private String id;

    // Thời gian phát sinh sự kiện
    @Indexed
    private Date timestamp;

    // Ai thực hiện
    @Indexed
    private String username;
    private String userRole;

    // Template và ngữ cảnh
    @Indexed
    private String templateID;
    @Indexed
    private String monthYear; // kỳ báo cáo, dạng MM/yyyy hoặc yyyyMM theo hệ thống bạn

    // Loại hành động: UPLOAD, CONFIRM, SEND, ROLLBACK
    @Indexed
    private String actionType;

    // Thông tin file
    private String fileName;
    private String filePath;

    // Trạng thái dữ liệu: trước và sau khi thao tác (ví dụ 00 -> 90)
    private String statusBefore;
    private String statusAfter;

    // Mô tả/chi tiết nghiệp vụ
    private String description;

    // Thông tin gọi SIMO (nếu có)
    private String simoEndpoint;
    private String simoResponseStatus;
    private String errorMessage;

    // Theo dõi liên kết, phục vụ trace
    private String requestId;
    private String correlationId;

    public UploadManagement() {
        this.timestamp = new Date();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    public String getTemplateID() { return templateID; }
    public void setTemplateID(String templateID) { this.templateID = templateID; }

    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getStatusBefore() { return statusBefore; }
    public void setStatusBefore(String statusBefore) { this.statusBefore = statusBefore; }

    public String getStatusAfter() { return statusAfter; }
    public void setStatusAfter(String statusAfter) { this.statusAfter = statusAfter; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSimoEndpoint() { return simoEndpoint; }
    public void setSimoEndpoint(String simoEndpoint) { this.simoEndpoint = simoEndpoint; }

    public String getSimoResponseStatus() { return simoResponseStatus; }
    public void setSimoResponseStatus(String simoResponseStatus) { this.simoResponseStatus = simoResponseStatus; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}


