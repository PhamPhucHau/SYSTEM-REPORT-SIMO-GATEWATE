package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_24_TOCHUC_NGGL {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String cif;
    private String tenToChuc;
    private String soGiayPhepThanhLap;
    private String soTaiKhoanToChuc;
    private Integer trangThaiTaiKhoan;
    private Integer nghiNgo;
    private String templateID;
    private String monthYear;
    private String username;
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public String getTenToChuc() { return tenToChuc; }
    public void setTenToChuc(String tenToChuc) { this.tenToChuc = tenToChuc; }
    public String getSoGiayPhepThanhLap() { return soGiayPhepThanhLap; }
    public void setSoGiayPhepThanhLap(String soGiayPhepThanhLap) { this.soGiayPhepThanhLap = soGiayPhepThanhLap; }
    public String getSoTaiKhoanToChuc() { return soTaiKhoanToChuc; }
    public void setSoTaiKhoanToChuc(String soTaiKhoanToChuc) { this.soTaiKhoanToChuc = soTaiKhoanToChuc; }
    public Integer getTrangThaiTaiKhoan() { return trangThaiTaiKhoan; }
    public void setTrangThaiTaiKhoan(Integer trangThaiTaiKhoan) { this.trangThaiTaiKhoan = trangThaiTaiKhoan; }
    public Integer getNghiNgo() { return nghiNgo; }
    public void setNghiNgo(Integer nghiNgo) { this.nghiNgo = nghiNgo; }
    public String getTemplateID() { return templateID; }
    public void setTemplateID(String templateID) { this.templateID = templateID; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
} 