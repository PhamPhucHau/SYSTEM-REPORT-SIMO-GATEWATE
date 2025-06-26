/**
 * 
 */
package com.org.shbvn.svbsimo.repository.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "SIMO_METADATA")
public class TMetadata implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5988804665611585304L;
	
	private Long id;
	private String lookupCode;
	private String lookupCodeId;
	private String language;
	private String value;
	private String orderBy;
	private String serviceName;
	private String showYn;
	
	/**
	 * 
	 */
	public TMetadata() {
		super();
	}
	
	public TMetadata(Long id, String lookupCode, String lookupCodeId, String language, String value, String orderBy, String serviceName, String showYn) {
		super();
		this.id = id;
		this.lookupCode = lookupCode;
		this.lookupCodeId = lookupCodeId;
		this.language = language;
		this.value = value;
		this.orderBy = orderBy;
		this.serviceName = serviceName;
		this.showYn =showYn;
	}

	/**
	 * @return the id
	 */
	@Id
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
	 * @return the lookupCode
	 */
	@Column(name = "LOOKUPCODE")
	public String getLookupCode() {
		return lookupCode;
	}
	/**
	 * @param lookupCode the lookupCode to set
	 */
	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}
	/**
	 * @return the lookupCodeId
	 */
	@Column(name = "LOOKUPCODEID")
	public String getLookupCodeId() {
		return lookupCodeId;
	}
	/**
	 * @param lookupCodeId the lookupCodeId to set
	 */
	public void setLookupCodeId(String lookupCodeId) {
		this.lookupCodeId = lookupCodeId;
	}
	/**
	 * @return the language
	 */
	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the value
	 */
	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the orderBy
	 */
	@Column(name = "ORDERBY")
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "SERVICENAME")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "SHOW_YN")
	public String getShowYn() {
		return showYn;
	}

	public void setShowYn(String showYn) {
		this.showYn = showYn;
	}
	
	@Override
	public String toString() {
		return "TMetadata [lookupCode=" + lookupCode + ", lookupCodeId=" + lookupCodeId + ", language=" + language
				+ ", value=" + value + ", orderBy=" + orderBy + "]";
	}
	
}

