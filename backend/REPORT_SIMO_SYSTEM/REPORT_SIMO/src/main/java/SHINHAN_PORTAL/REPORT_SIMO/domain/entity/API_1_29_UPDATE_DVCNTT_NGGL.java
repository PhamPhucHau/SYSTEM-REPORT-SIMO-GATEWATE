package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_29_UPDATE_DVCNTT_NGGL {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String cif;
    private String tenDvcntt;
    private String maSoDoanhNghiep;
    private String soTaiKhoan;
    private Integer trangThaiTaiKhoan;
    private Integer nghiNgo;
    private String lyDoCapNhat;
    private String ghiChu;
    private String templateID;
    private String monthYear;
    private String username;
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public String getTenDvcntt() { return tenDvcntt; }
    public void setTenDvcntt(String tenDvcntt) { this.tenDvcntt = tenDvcntt; }
    public String getMaSoDoanhNghiep() { return maSoDoanhNghiep; }
    public void setMaSoDoanhNghiep(String maSoDoanhNghiep) { this.maSoDoanhNghiep = maSoDoanhNghiep; }
    public String getSoTaiKhoan() { return soTaiKhoan; }
    public void setSoTaiKhoan(String soTaiKhoan) { this.soTaiKhoan = soTaiKhoan; }
    public Integer getTrangThaiTaiKhoan() { return trangThaiTaiKhoan; }
    public void setTrangThaiTaiKhoan(Integer trangThaiTaiKhoan) { this.trangThaiTaiKhoan = trangThaiTaiKhoan; }
    public Integer getNghiNgo() { return nghiNgo; }
    public void setNghiNgo(Integer nghiNgo) { this.nghiNgo = nghiNgo; }
    public String getLyDoCapNhat() { return lyDoCapNhat; }
    public void setLyDoCapNhat(String lyDoCapNhat) { this.lyDoCapNhat = lyDoCapNhat; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public String getTemplateID() { return templateID; }
    public void setTemplateID(String templateID) { this.templateID = templateID; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
} 