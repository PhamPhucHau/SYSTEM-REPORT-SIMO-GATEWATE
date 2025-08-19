package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_28_TT_DVCNTT_NGGL_DTO {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Cif")
    private String cif;

    @JsonProperty("TenDvcntt")
    private String tenDvcntt;

    @JsonProperty("MaSoDoanhNghiep")
    private String maSoDoanhNghiep;

    @JsonProperty("SoTaiKhoan")
    private String soTaiKhoan;

    @JsonProperty("TrangThaiTaiKhoan")
    private Integer trangThaiTaiKhoan;

    @JsonProperty("NghiNgo")
    private Integer nghiNgo;

    @JsonProperty("GhiChu")
    private String ghiChu;
      @JsonIgnore
    private String status = "00"; // Giá trị mặc định
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getTenDvcntt() {
        return tenDvcntt;
    }

    public void setTenDvcntt(String tenDvcntt) {
        this.tenDvcntt = tenDvcntt;
    }

    public String getMaSoDoanhNghiep() {
        return maSoDoanhNghiep;
    }

    public void setMaSoDoanhNghiep(String maSoDoanhNghiep) {
        this.maSoDoanhNghiep = maSoDoanhNghiep;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public Integer getTrangThaiTaiKhoan() {
        return trangThaiTaiKhoan;
    }

    public void setTrangThaiTaiKhoan(Integer trangThaiTaiKhoan) {
        this.trangThaiTaiKhoan = trangThaiTaiKhoan;
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
