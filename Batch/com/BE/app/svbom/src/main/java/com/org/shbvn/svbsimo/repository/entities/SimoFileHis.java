package com.org.shbvn.svbsimo.repository.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class for SIMO_FILE_HIS table
 */
@Entity
@Table(name = "SIMO_FILE_HIS")
public class SimoFileHis implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String templateCode;
    private String fileName;
    private String fileUploadDt;
    private String processStatus;
    private String fileStatus;
    private int totalCount;
    private int successCount;
    private int fileValidCount;
    private String remark;
    private String fileType;
    private String regisUser;
    private String regisInfDt;
    private String lchgUser;
    private String lchgInfDt;

    /**
     * Default constructor
     */
    public SimoFileHis() {
        super();
    }

    /**
     * Constructor with all fields
     */
    public SimoFileHis(Long id, String templateCode, String fileName,
                      String processStatus, String fileStatus, int totalCount, 
                      int successCount, int fileValidCount, String remark, 
                      String fileType, String regisUser, String regisInfDt,
                      String lchgUser, String lchgInfDt) {
        this.id = id;
        this.templateCode = templateCode;
        this.fileName = fileName;
        this.processStatus = processStatus;
        this.fileStatus = fileStatus;
        this.totalCount = totalCount;
        this.successCount = successCount;
        this.fileValidCount = fileValidCount;
        this.remark = remark;
        this.fileType = fileType;
        this.regisUser = regisUser;
        this.regisInfDt = regisInfDt;
        this.lchgUser = lchgUser;
        this.lchgInfDt = lchgInfDt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "template_code")
    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_upload_dt")
    public String getFileUploadDt() {
        return fileUploadDt;
    }

    public void setFileUploadDt(String fileUploadDt) {
        this.fileUploadDt = fileUploadDt;
    }

    @Column(name = "process_status")
    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    @Column(name = "file_status")
    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    @Column(name = "total_count")
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Column(name = "success_count")
    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    @Column(name = "file_valid_count")
    public int getFileValidCount() {
        return fileValidCount;
    }

    public void setFileValidCount(int fileValidCount) {
        this.fileValidCount = fileValidCount;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Column(name = "regis_user")
    public String getRegisUser() {
        return regisUser;
    }

    public void setRegisUser(String regisUser) {
        this.regisUser = regisUser;
    }

    @Column(name = "regis_inf_dt")
    public String getRegisInfDt() {
        return regisInfDt;
    }

    public void setRegisInfDt(String regisInfDt) {
        this.regisInfDt = regisInfDt;
    }

    @Column(name = "lchg_user")
    public String getLchgUser() {
        return lchgUser;
    }

    public void setLchgUser(String lchgUser) {
        this.lchgUser = lchgUser;
    }

    @Column(name = "lchg_inf_dt")
    public String getLchgInfDt() {
        return lchgInfDt;
    }

    public void setLchgInfDt(String lchgInfDt) {
        this.lchgInfDt = lchgInfDt;
    }
}