package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_8_update_tktt_nnngl {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String cif;
    private String soTaiKhoan;
    private String tenKhachHang;
    private Integer trangThaiHoatDongTaiKhoan;
    private Integer nghiNgo;
    private String ghiChu;
    private String lyDoCapNhat;
    private String templateID;
    private String monthYear;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public Integer getTrangThaiHoatDongTaiKhoan() {
        return trangThaiHoatDongTaiKhoan;
    }

    public void setTrangThaiHoatDongTaiKhoan(Integer trangThaiHoatDongTaiKhoan) {
        this.trangThaiHoatDongTaiKhoan = trangThaiHoatDongTaiKhoan;
    }

    public Integer getNghiNgo() {
        return nghiNgo;
    }

    public void setNghiNgo(Integer nghiNgo) {
        this.nghiNgo = nghiNgo;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getLyDoCapNhat() {
        return lyDoCapNhat;
    }

    public void setLyDoCapNhat(String lyDoCapNhat) {
        this.lyDoCapNhat = lyDoCapNhat;
    }
}
