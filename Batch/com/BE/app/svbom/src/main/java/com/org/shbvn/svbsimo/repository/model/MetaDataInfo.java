package com.org.shbvn.svbsimo.repository.model;

public class MetaDataInfo {
    private String lookupCode;
    private String lookupCodeId;
    private String language;
    private String value;
    private String orderBy;
    private String serviceName;
    private String showYn;
    
    public MetaDataInfo() {
    }

    public MetaDataInfo(String lookupCode, String lookupCodeId, String language, String value, String orderBy,
            String serviceName, String showYn) {
        this.lookupCode = lookupCode;
        this.lookupCodeId = lookupCodeId;
        this.language = language;
        this.value = value;
        this.orderBy = orderBy;
        this.serviceName = serviceName;
        this.showYn = showYn;
    }

    public String getLookupCode() {
        return lookupCode;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getLookupCodeId() {
        return lookupCodeId;
    }

    public void setLookupCodeId(String lookupCodeId) {
        this.lookupCodeId = lookupCodeId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getShowYn() {
        return showYn;
    }

    public void setShowYn(String showYn) {
        this.showYn = showYn;
    }
}
