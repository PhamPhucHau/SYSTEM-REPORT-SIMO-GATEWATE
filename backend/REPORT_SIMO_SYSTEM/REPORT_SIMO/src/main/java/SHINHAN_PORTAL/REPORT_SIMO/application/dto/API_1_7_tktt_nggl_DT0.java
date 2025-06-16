package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_7_tktt_nggl_DT0 {
    
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
}
