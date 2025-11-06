package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_33_UPDATE_TNH_NGGL {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String cif;
    private String tenChuTheHoacNguoiUyQuyen;
    private String soThe;
    private Integer loaiThe;
    private Integer trangThaiThe;
    private Integer nghiNgo;
    private String lyDoCapNhat;
    private String ghiChu;

    // Thêm 4 field tiện check
    private String templateID;
    private String monthYear;
    private String username;
    private String status;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}