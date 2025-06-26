/**
 * 
 */
package com.org.shbvn.svbsimo.core.constant;

import com.org.shbvn.svbsimo.core.utils.DateUtils;

public class ResponseOutPut {

	private Object result;
	private String serverDate;
	private String exceptionMessage;
	private	String exceptionCode;
	private boolean status;
	private int statusCode;
	
	public ResponseOutPut() {
		super();
	}

	/**
	 * @param result
	 * @param exceptionMessage
	 * @param status
	 * @param statusCode
	 */
	public ResponseOutPut(Object result, String exceptionMessage, boolean status,
			int statusCode) {
		super();
		
		this.serverDate = DateUtils.getSystemDateStr(DateUtils.yyyy_MM_dd_HH_mm_ss);
		this.result = result;
		this.exceptionMessage = exceptionMessage;
		this.status = status;
		this.statusCode = statusCode;
	}

	public ResponseOutPut(Object result, String exceptionMessage, boolean status,
			int statusCode, String exceptionCode) {
		super();
		
		this.serverDate = DateUtils.getSystemDateStr(DateUtils.yyyy_MM_dd_HH_mm_ss);
		this.result = result;
		this.exceptionMessage = exceptionMessage;
		this.status = status;
		this.statusCode = statusCode;
		this.exceptionCode = exceptionCode;
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
}
