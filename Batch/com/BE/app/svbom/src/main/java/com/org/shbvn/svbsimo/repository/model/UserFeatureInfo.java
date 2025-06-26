package com.org.shbvn.svbsimo.repository.model;

public class UserFeatureInfo {
    private String menuId;
    private String menuName;
    private String menuUrl;
    private String description;
    private String mode;
    private String status;
    private String configuration;
    private String regisInfDt;
    private String lchgInfDt;
    private String method;
    private String roleId;
    
    public UserFeatureInfo() {
    }

    public UserFeatureInfo(String menuId, String menuName, String menuUrl, String description, String mode,
            String status, String configuration, String regisInfDt, String lchgInfDt  , String method, String roleId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.description = description;
        this.mode = mode;
        this.status = status;
        this.configuration = configuration;
        this.regisInfDt = regisInfDt;
        this.lchgInfDt = lchgInfDt;
        this.method = method;
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getRegisInfDt() {
        return regisInfDt;
    }

    public void setRegisInfDt(String regisInfDt) {
        this.regisInfDt = regisInfDt;
    }

    public String getLchgInfDt() {
        return lchgInfDt;
    }

    public void setLchgInfDt(String lchgInfDt) {
        this.lchgInfDt = lchgInfDt;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
