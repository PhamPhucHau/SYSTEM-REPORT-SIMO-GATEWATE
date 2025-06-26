package com.org.shbvn.svbsimo.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class for SIMO_CONFIG table
 */
@Entity
@Table(name = "SIMO_CONFIG")
public class SimoConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "config_key", length = 100, nullable = false)
    private String configKey;
    
    @Column(name = "config_value", length = 100, nullable = false)
    private String configValue;
    
    @Column(name = "config_desc")
    private String configDesc;
    
    @Column(name = "regis_inf_dt", length = 14)
    private String regisInfDt;
    
    @Column(name = "lchg_inf_dt", length = 14)
    private String lchgInfDt;

    /**
     * Default constructor
     */
    public SimoConfig() {
    }

    /**
     * Constructor with fields
     */
    public SimoConfig(Long id, String configKey, String configValue, String configDesc) {
        this.id = id;
        this.configKey = configKey;
        this.configValue = configValue;
        this.configDesc = configDesc;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
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