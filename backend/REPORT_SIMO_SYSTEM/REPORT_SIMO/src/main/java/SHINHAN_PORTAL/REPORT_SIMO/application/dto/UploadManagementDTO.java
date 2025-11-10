package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import java.util.Date;

public class UploadManagementDTO {
    private String id;
    private Date timestamp;
    private String username;
    private String userRole;
    private String templateID;
    private String monthYear;
    private String actionType;
    private String fileName;
    private String filePath;
    private String statusBefore;
    private String statusAfter;
    private String description;
    private String simoEndpoint;
    private String simoResponseStatus;
    private String errorMessage;
    private String requestId;
    private String correlationId;

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


