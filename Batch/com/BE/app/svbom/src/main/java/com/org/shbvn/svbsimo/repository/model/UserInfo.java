package com.org.shbvn.svbsimo.repository.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserInfo implements Serializable{
    
    private String username;
    private String password;
    private String email;
    private String firstLoginYn;
    private String status;
    private Integer pwWrongCnt;
    private String lchPasswordDt;
    private String regisInfDt;
    private String lchgInfDt;
    private List<RoleInfo> roles;
    private List<UserFeatureInfo> features;
    private String token;


    
    public UserInfo() {
    }

    public UserInfo(String username, String password, String email, String firstLoginYn, String status, Integer pwWrongCnt,
            String lchPasswordDt, String regisInfDt, String lchgInfDt) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstLoginYn = firstLoginYn;
        this.status = status;
        this.pwWrongCnt = pwWrongCnt;
        this.lchPasswordDt = lchPasswordDt;
        this.regisInfDt = regisInfDt;    
        this.lchgInfDt = lchgInfDt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstLoginYn() {
        return firstLoginYn;
    }

    public void setFirstLoginYn(String firstLoginYn) {
        this.firstLoginYn = firstLoginYn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPwWrongCnt() {
        return pwWrongCnt;
    }

    public void setPwWrongCnt(Integer pwWrongCnt) {
        this.pwWrongCnt = pwWrongCnt;
    }

    public String getLchPasswordDt() {
        return lchPasswordDt;
    }

    public void setLchPasswordDt(String lchPasswordDt) {
        this.lchPasswordDt = lchPasswordDt;
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

    public List<RoleInfo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleInfo> roles) {
        this.roles = roles;
    }

    public List<UserFeatureInfo> getFeatures() {
        return features;
    }

    public void setFeatures(List<UserFeatureInfo> features) {
        this.features = features;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
