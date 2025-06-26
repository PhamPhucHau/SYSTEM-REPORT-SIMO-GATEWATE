/**
 * 
 */
package com.org.shbvn.svbsimo.core.exception;

import java.util.List;

public class ServiceInvalidAgurmentException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4242591666363830698L;
	private transient List<?> listItemError;
	
	/**
	 * 
	 */
	public ServiceInvalidAgurmentException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ServiceInvalidAgurmentException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceInvalidAgurmentException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ServiceInvalidAgurmentException(String message) {
		super(message);
	}
	
	/**
	 * @param cause
	 */
	public ServiceInvalidAgurmentException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param listItemError
	 */
	public ServiceInvalidAgurmentException(List<?> listItemError) {
		super();
		this.listItemError = listItemError;
	}


	/**
	 * @return the listItemError
	 */
	public List<?> getListItemError() {
		return listItemError;
	}

	/**
	 * @param listItemError the listItemError to set
	 */
	public void setListItemError(List<?> listItemError) {
		this.listItemError = listItemError;
	}

}
