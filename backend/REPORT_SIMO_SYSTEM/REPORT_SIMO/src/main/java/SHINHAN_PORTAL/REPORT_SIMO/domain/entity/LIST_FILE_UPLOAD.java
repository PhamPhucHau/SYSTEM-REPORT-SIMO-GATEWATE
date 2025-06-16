package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Document(collection = "LIST_FILE_UPLOAD")
public class LIST_FILE_UPLOAD {
    @Id
    private String id;
    private String fileName;
    private String fileType;
    private String userId;
    private String username;
    private String templateID;
    private String templateName;
    private String monthYear;
    private Long total_record;
    private String inf_regis_dt; // Ngày đăng ký (yyyyMMdd)
    private String inf_regis_time; // Giờ đăng ký (HHmmss)
    private String inf_chg_dt; // Ngày đăng ký (yyyyMMdd)
    private String inf_chg_time; // Giờ đăng ký (HHmmss) 

    private String data_ledg_s;
    private List<Map<String, Object>> data;

    // Default Constructor
    public LIST_FILE_UPLOAD() {
    }
    public String getInf_chg_dt() {
        return inf_chg_dt;
    }

    public void setInf_chg_dt(String inf_chg_dt) {
        this.inf_chg_dt = inf_chg_dt;
    }
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public Long getTotal_record() {
        return total_record;
    }

    public void setTotal_record(Long total_record) {
        this.total_record = total_record;
    }

    public String getInf_regis_dt() {
        return inf_regis_dt;
    }

    public void setInf_regis_dt(String inf_regis_dt) {
        this.inf_regis_dt = inf_regis_dt;
    }

    public String getInf_regis_time() {
        return inf_regis_time;
    }

    public void setInf_regis_time(String inf_regis_time) {
        this.inf_regis_time = inf_regis_time;
    }
    public String getInf_chg_time() {
        return inf_chg_time;
    }

    public void setInf_chg_time(String inf_chg_time) {
        this.inf_chg_time = inf_chg_time;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
    public String getData_ledg_s() {
        return data_ledg_s;
    }
    public void setData_ledg_s(String data_ledg_s) {
        this.data_ledg_s = data_ledg_s;
    }

}
