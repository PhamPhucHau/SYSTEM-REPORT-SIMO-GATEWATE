package SHINHAN_PORTAL.REPORT_SIMO.application.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_8_update_tktt_nggl_DT0 {
    @JsonProperty("Cif")
    private String cif;

    @JsonProperty("SoTaiKhoan")
    private String soTaiKhoan;

    @JsonProperty("TenKhachHang")
    private String tenKhachHang;

    @JsonProperty("TrangThaiHoatDongTaiKhoan")
    private Integer trangThaiHoatDongTaiKhoan;

    @JsonProperty("NghiNgo")
    private Integer nghiNgo;

    @JsonProperty("GhiChu")
    private String ghiChu;
    @JsonProperty("LyDoCapNhat")
    private String lyDoCapNhat;
    private String templateID;
    private String monthYear;
    private String status;

    // Getters and Setters

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
