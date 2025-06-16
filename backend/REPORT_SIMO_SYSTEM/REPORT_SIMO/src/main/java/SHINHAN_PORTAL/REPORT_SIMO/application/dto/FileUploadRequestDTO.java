package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import java.util.List;
import java.util.Map;

public class FileUploadRequestDTO {


    private String templateID;

    private String templateName;

    private String monthYear;

    private Long total_record;

    private String userId;

    private String username;

    private String fileName;

    private String fileType;

    private List<Map<String, Object>> data;

    // Getters and Setters
    public String getTemplateID() { return templateID; }
    public void setTemplateID(String templateID) { this.templateID = templateID; }
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public Long getTotal_record() { return total_record; }
    public void setTotal_record(Long total_record) { this.total_record = total_record; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public List<Map<String, Object>> getData() { return data; }
    public void setData(List<Map<String, Object>> data) { this.data = data; }
}