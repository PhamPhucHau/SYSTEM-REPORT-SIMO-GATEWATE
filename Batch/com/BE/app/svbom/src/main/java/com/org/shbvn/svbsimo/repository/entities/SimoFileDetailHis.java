package com.org.shbvn.svbsimo.repository.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class for SIMO_FILE_DETAIL_HIS table
 */
@Entity
@Table(name = "SIMO_FILE_DETAIL_HIS")
public class SimoFileDetailHis implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String templateCode;
    private String fileName;
    private String fileUploadDt;
    private String processStatus;
    private String rowNumber;
    private String cif;
    private String accountNo;
    private String accountName;
    private String personalId;
    private String remark;
    private String fileType;
    private String fileData;
    private String regisUser;
    private String regisInfDt;
    private String lchgUser;
    private String lchgInfDt;

    /**
     * Default constructor
     */
    public SimoFileDetailHis() {
        super();
    }

    /**
     * Constructor with all fields
     */
    public SimoFileDetailHis(Long id, String templateCode, String fileName,
                           String processStatus, String rowNumber, String cif,
                           String accountNo, String accountName, String personalId,
                           String remark, String fileType, String regisUser,
                           String regisInfDt, String lchgUser, String lchgInfDt) {
        this.id = id;
        this.templateCode = templateCode;
        this.fileName = fileName;
        this.processStatus = processStatus;
        this.rowNumber = rowNumber;
        this.cif = cif;
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.personalId = personalId;
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

    @Column(name = "row_number")
    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Column(name = "cif")
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    @Column(name = "acount_no")
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Column(name = "account_name")
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Column(name = "personal_id")
    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
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

    @Column(name = "file_data")
    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
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