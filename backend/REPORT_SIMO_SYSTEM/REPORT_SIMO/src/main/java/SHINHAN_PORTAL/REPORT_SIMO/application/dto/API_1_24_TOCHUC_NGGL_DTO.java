package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_24_TOCHUC_NGGL_DTO {
    private String key;
    @JsonProperty("Cif")
    private String cif;
    @JsonProperty("TenToChuc")
    private String tenToChuc;
    @JsonProperty("SoGiayPhepThanhLap")
    private String soGiayPhepThanhLap;
    @JsonProperty("SoTaiKhoanToChuc")
    private String soTaiKhoanToChuc;
    @JsonProperty("TrangThaiTaiKhoan")
    private Integer trangThaiTaiKhoan;
    @JsonProperty("NghiNgo")
    private Integer nghiNgo;
      @JsonIgnore
    private String status = "00"; // Giá trị mặc định
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // Getters and Setters
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
} 