package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_32_TT_TNH_NGGL_DTO {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Cif")
    private String cif;

    @JsonProperty("TenChuTheHoacNguoiUyQuyen")
    private String tenChuTheHoacNguoiUyQuyen;

    @JsonProperty("SoThe")
    private String soThe;

    @JsonProperty("LoaiThe")
    private Integer loaiThe;

    @JsonProperty("TrangThaiThe")
    private Integer trangThaiThe;

    @JsonProperty("NghiNgo")
    private Integer nghiNgo;

    @JsonProperty("GhiChu")
    private String ghiChu;

    @JsonIgnore
    private String status = "00";

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public String getTenChuTheHoacNguoiUyQuyen() { return tenChuTheHoacNguoiUyQuyen; }
    public void setTenChuTheHoacNguoiUyQuyen(String tenChuTheHoacNguoiUyQuyen) { this.tenChuTheHoacNguoiUyQuyen = tenChuTheHoacNguoiUyQuyen; }
    public String getSoThe() { return soThe; }
    public void setSoThe(String soThe) { this.soThe = soThe; }
    public Integer getLoaiThe() { return loaiThe; }
    public void setLoaiThe(Integer loaiThe) { this.loaiThe = loaiThe; }
    public Integer getTrangThaiThe() { return trangThaiThe; }
    public void setTrangThaiThe(Integer trangThaiThe) { this.trangThaiThe = trangThaiThe; }
    public Integer getNghiNgo() { return nghiNgo; }
    public void setNghiNgo(Integer nghiNgo) { this.nghiNgo = nghiNgo; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}