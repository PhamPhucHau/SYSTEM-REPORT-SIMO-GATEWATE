package com.org.shbvn.svbsimo.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SIMO_ROLE")
public class SimoRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "role_id", length = 50, nullable = false, unique = true)
    private String roleId;
    
    @Column(name = "status", length = 1)
    private String status = "A";
    
    @Column(name = "configuration")
    private String configuration;
    
    @Column(name = "regis_inf_dt", length = 14)
    private String regisInfDt;
    
    @Column(name = "lchg_inf_dt", length = 14)
    private String lchgInfDt;

    public SimoRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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