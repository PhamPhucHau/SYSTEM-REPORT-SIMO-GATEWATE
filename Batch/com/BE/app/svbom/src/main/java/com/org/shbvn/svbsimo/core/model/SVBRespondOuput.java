package com.org.shbvn.svbsimo.core.model;

import java.io.Serializable;

import com.org.shbvn.svbsimo.core.utils.DateUtils;

public class SVBRespondOuput implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Object result;
	private String serverDate;
	private String exceptionMessage;
	private	String exceptionCode;
	private boolean status;
	private int statusCode;
	private String code;
	private String message;
	private boolean success;

	public SVBRespondOuput() {
		super();
	}

	/**
	 * @param result
	 * @param exceptionMessage
	 * @param status
	 * @param statusCode
	 */
	public SVBRespondOuput(Object result, String exceptionMessage, boolean status,
			int statusCode) {
		super();
		
		this.serverDate = DateUtils.getSystemDateStr(DateUtils.yyyy_MM_dd_HH_mm_ss);
		this.result = result;
		this.exceptionMessage = exceptionMessage;
		this.status = status;
		this.statusCode = statusCode;
	}

	public SVBRespondOuput(Object result, String exceptionMessage, boolean status,
			int statusCode, String exceptionCode) {
		super();
		
		this.serverDate = DateUtils.getSystemDateStr(DateUtils.yyyy_MM_dd_HH_mm_ss);
		this.result = result;
		this.exceptionMessage = exceptionMessage;
		this.status = status;
		this.statusCode = statusCode;
		this.exceptionCode = exceptionCode;
	}
	public SVBRespondOuput(String code, String message, boolean success, Object result) {
		this.serverDate = DateUtils.getSystemDateStr(DateUtils.yyyy_MM_dd_HH_mm_ss);
		this.code = code;
		this.message = message;
		this.success = success;
		this.result = result;
	}
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	/**
	 * @return the serverDate
	 */
	public String getServerDate() {
		return serverDate;
	}
	/**
	 * @param serverDate the serverDate to set
	 */
	public void setServerDate(String serverDate) {
		this.serverDate = serverDate;
	}
	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	/**
 * @return the code
 */
public String getCode() {
    return code;
}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return this.success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	@Override
	public String toString() {
		return "SVBRespondOuput{" +
				"result=" + result +
				", serverDate='" + serverDate + '\'' +
				", exceptionMessage='" + exceptionMessage + '\'' +
				", exceptionCode='" + exceptionCode + '\'' +
				", status=" + status +
				", statusCode=" + statusCode +
				", code='" + code + '\'' +
				", message='" + message + '\'' +
				", success=" + success +
				'}';
	}
	
}
