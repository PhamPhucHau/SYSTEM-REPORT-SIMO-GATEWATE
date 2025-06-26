package com.org.shbvn.svbsimo.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SIMO_AUTH_MENU")
public class SimoAuthMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "menu_id", length = 50, nullable = false, unique = true)
    private String menuId;
    
    @Column(name = "menu_name", length = 100, nullable = false)
    private String menuName;
    
    @Column(name = "menu_url", length = 200, nullable = false)
    private String menuUrl;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status", length = 1)
    private String status = "A";
    
    @Column(name = "role_id", length = 50, nullable = false)
    private String roleId;
    
    @Column(name = "configuration")
    private String configuration;
    
    @Column(name = "regis_inf_dt", length = 14)
    private String regisInfDt;
    
    @Column(name = "lchg_inf_dt", length = 14)
    private String lchgInfDt;

    @Column(name = "mode", length = 4)
    private String mode = "AUTH";

    @Column(name = "method", length = 10)
    private String method;

    public SimoAuthMenu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}