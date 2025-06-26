package com.org.shbvn.svbsimo.repository.model;

public class RoleInfo {
    
    private String roleId;
    private String status;
    private String configuration;
    private String regisInfDt;
    private String lchgInfDt;
    
    public RoleInfo() {
    }

    public RoleInfo(String roleId, String status, String configuration, String regisInfDt, String lchgInfDt) {
        this.roleId = roleId;
        this.status = status;
        this.configuration = configuration;
        this.regisInfDt = regisInfDt;
        this.lchgInfDt = lchgInfDt;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

}
