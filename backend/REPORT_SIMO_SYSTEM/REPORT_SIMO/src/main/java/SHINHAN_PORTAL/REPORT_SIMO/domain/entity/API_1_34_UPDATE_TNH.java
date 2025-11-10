package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_34_UPDATE_TNH {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String cif;
    private String soId;
    private Integer loaiId;
    private String tenChuTheHoacNguoiUyQuyen;
    private String ngaySinh;
    private Integer gioiTinh;
    private String quocTich;
    private String dienThoai;
    private String diaChi;
    private String diaChiMac;
    private String soImei;
    private String soThe;
    private Integer loaiThe;
    private String ngayPhatHanh;
    private String thoiHanHieuLuc;
    private String bin;
    private Integer trangThaiThe;
    private Integer phuongThucMoThe;
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
    public String getSoId() { return soId; }
    public void setSoId(String soId) { this.soId = soId; }
    public Integer getLoaiId() { return loaiId; }
    public void setLoaiId(Integer loaiId) { this.loaiId = loaiId; }
    public String getTenChuTheHoacNguoiUyQuyen() { return tenChuTheHoacNguoiUyQuyen; }
    public void setTenChuTheHoacNguoiUyQuyen(String tenChuTheHoacNguoiUyQuyen) { this.tenChuTheHoacNguoiUyQuyen = tenChuTheHoacNguoiUyQuyen; }
    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public Integer getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(Integer gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getQuocTich() { return quocTich; }
    public void setQuocTich(String quocTich) { this.quocTich = quocTich; }
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getDiaChiMac() { return diaChiMac; }
    public void setDiaChiMac(String diaChiMac) { this.diaChiMac = diaChiMac; }
    public String getSoImei() { return soImei; }
    public void setSoImei(String soImei) { this.soImei = soImei; }
    public String getSoThe() { return soThe; }
    public void setSoThe(String soThe) { this.soThe = soThe; }
    public Integer getLoaiThe() {  return loaiThe; }
    public void setLoaiThe(Integer loaiThe) { this.loaiThe = loaiThe; }
    public String getNgayPhatHanh() { return ngayPhatHanh; }
    public void setNgayPhatHanh(String ngayPhatHanh) { this.ngayPhatHanh = ngayPhatHanh; }
    public String getThoiHanHieuLuc() { return thoiHanHieuLuc; }
    public void setThoiHanHieuLuc(String thoiHanHieuLuc) { this.thoiHanHieuLuc = thoiHanHieuLuc; }
    public String getBin() { return bin; }
    public void setBin(String bin) { this.bin = bin; }
    public Integer getTrangThaiThe() { return trangThaiThe; }
    public void setTrangThaiThe(Integer trangThaiThe) { this.trangThaiThe = trangThaiThe; }
    public Integer getPhuongThucMoThe() { return phuongThucMoThe; }
    public void setPhuongThucMoThe(Integer phuongThucMoThe) { this.phuongThucMoThe = phuongThucMoThe; }
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