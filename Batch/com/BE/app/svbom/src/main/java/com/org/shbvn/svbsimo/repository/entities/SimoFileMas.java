/**
 * 
 */
package com.org.shbvn.svbsimo.repository.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "SIMO_FILE_MAS")
public class SimoFileMas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String templateCode;
	private int seqNo;
	private String fileName;
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
	 * 
	 */
	public SimoFileMas() {
		super();
	}

	public SimoFileMas(Long id, String templateCode, int seqNo, String fileName,
			String processStatus, String fileStatus, int totalCount, int successCount, int fileValidCount, String remark, String fileType, String regisUser) {
		super();
		this.id = id;
		this.templateCode = templateCode;
		this.seqNo = seqNo;
		this.fileName = fileName;
		this.processStatus = processStatus;
		this.fileStatus = fileStatus;
		this.totalCount = totalCount;
		this.successCount = successCount;
		this.fileValidCount = fileValidCount;
		this.remark = remark;
		this.fileType = fileType;
		this.regisUser = regisUser;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIMO_FILE_MAS_SEQ_GEN")
	@SequenceGenerator(name = "SIMO_FILE_MAS_SEQ_GEN", sequenceName = "SIMO_FILE_MAS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the templateCode
	 */
	@Column(name = "TEMPLATE_CODE")
	public String getTemplateCode() {
		return templateCode;
	}
	
	/**
	 * @param templateCode the templateCode to set
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @return the seqNo
	 */
	@Column(name = "SEQ_NO")
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo the seqNo to set
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return the fileName
	 */
	@Column(name = "FILE_NAME")
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the processStatus
	 */
	@Column(name = "PROCESS_STATUS")
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * @return the fileStatus
	 */
	@Column(name = "FILE_STATUS")
	public String getFileStatus() {
		return fileStatus;
	}

	/**
	 * @param fileStatus the fileStatus to set
	 */
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	/**
	 * @return the totalCount
	 */
	@Column(name = "TOTAL_CNT")
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the successCount
	 */
	@Column(name = "SUCCESS_CNT")
	public int getSuccessCount() {
		return successCount;
	}

	/**
	 * @param successCount the successCount to set
	 */
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	/**
	 * @return the fileValidCount
	 */
	@Column(name = "FILE_VALID_CNT")
	public int getFileValidCount() {
		return fileValidCount;
	}

	/**
	 * @param fileValidCount the fileValidCount to set
	 */
	public void setFileValidCount(int fileValidCount) {
		this.fileValidCount = fileValidCount;
	}

	/**
	 * @return the remark
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the fileType
	 */
	@Column(name = "FILE_TYPE")
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the regisUser
	 */
	@Column(name = "REGIS_USER")
	public String getRegisUser() {
		return regisUser;
	}

	/**
	 * @param regisUser the regisUser to set
	 */
	public void setRegisUser(String regisUser) {
		this.regisUser = regisUser;
	}

	/**
	 * @return the regisInfDt
	 */
	@Column(name = "REGIS_INF_DT")
	public String getRegisInfDt() {
		return regisInfDt;
	}

	/**
	 * @param regisInfDt the regisInfDt to set
	 */
	public void setRegisInfDt(String regisInfDt) {
		this.regisInfDt = regisInfDt;
	}

	/**
	 * @return the lchgUser
	 */
	@Column(name = "LCHG_USER")
	public String getLchgUser() {
		return lchgUser;
	}

	/**
	 * @param lchgUser the lchgUser to set
	 */
	public void setLchgUser(String lchgUser) {
		this.lchgUser = lchgUser;
	}

	/**
	 * @return the lchgInfDt
	 */
	@Column(name = "LCHG_INF_DT")
	public String getLchgInfDt() {
		return lchgInfDt;
	}

	/**
	 * @param lchgInfDt the lchgInfDt to set
	 */
	public void setLchgInfDt(String lchgInfDt) {
		this.lchgInfDt = lchgInfDt;
	}

}
