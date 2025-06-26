package com.org.shbvn.svbsimo.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SIMO_USER")
public class SimoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;
    
    @Column(name = "password_hash", nullable = false)
    private String password;
    
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(name = "fist_login_yn", length = 1)
    private String firstLoginYn = "Y";
    
    @Column(name = "status", length = 1)
    private String status = "A";
    
    @Column(name = "pw_wrong_cnt")
    private Integer pwWrongCnt = 0;
    
    @Column(name = "lch_password_dt", length = 14)
    private String lchPasswordDt;
    
    @Column(name = "regis_inf_dt", length = 14)
    private String regisInfDt;
    
    @Column(name = "lchg_inf_dt", length = 14)
    private String lchgInfDt;

    public SimoUser() {
    }

    public SimoUser(Long id, String username, String password, String email, 
                   String firstLoginYn, String status, Integer pwWrongCnt,
                   String lchPasswordDt, String regisInfDt, String lchgInfDt) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
}
