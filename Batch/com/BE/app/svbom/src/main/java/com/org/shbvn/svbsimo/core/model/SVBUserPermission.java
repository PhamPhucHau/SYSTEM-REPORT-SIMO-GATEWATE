/**
 * 
 */
package com.org.shbvn.svbsimo.core.model;

/**
 * @author shds01
 *
 */
public class SVBUserPermission {

	private String userName;
	private String password;

	/**
	 * @param url
	 * @param method
	 * @param contentType
	 */
	public SVBUserPermission(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 
	 */
	public SVBUserPermission() {
		super();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
