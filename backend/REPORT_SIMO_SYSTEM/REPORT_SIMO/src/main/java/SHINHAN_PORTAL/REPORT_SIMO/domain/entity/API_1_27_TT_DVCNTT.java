package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_27_TT_DVCNTT {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String cif;
    private String maSoDoanhNghiep;
    private String soId;
    private Integer loaiId;
    private String hoTenNguoiDaiDien;
    private String ngaySinh;
    private String quocTich;
    private String tenDvcntt;
    private String loaiHinhKinhDoanh;
    private String maSoThue;
    private String dienThoai;
    private String diaChi;
    private String diaChiMac;
    private String soImei;
    private String soTaiKhoan;
    private String tenChuTaiKhoan;
    private String nganHangMoTk;
    private Integer loaiTaiKhoan;
    private Integer trangThaiTaiKhoan;
    private String ngayMoTaiKhoan;
    // Thêm 3 field tiện check
    private String templateID;
    private String monthYear;
    private String username;
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public String getMaSoDoanhNghiep() { return maSoDoanhNghiep; }
    public void setMaSoDoanhNghiep(String maSoDoanhNghiep) { this.maSoDoanhNghiep = maSoDoanhNghiep; }
    public String getSoId() { return soId; }
    public void setSoId(String soId) { this.soId = soId; }
    public Integer getLoaiId() { return loaiId; }
    public void setLoaiId(Integer loaiId) { this.loaiId = loaiId; }
    public String getHoTenNguoiDaiDien() { return hoTenNguoiDaiDien; }
    public void setHoTenNguoiDaiDien(String hoTenNguoiDaiDien) { this.hoTenNguoiDaiDien = hoTenNguoiDaiDien; }
    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getQuocTich() { return quocTich; }
    public void setQuocTich(String quocTich) { this.quocTich = quocTich; }
    public String getTenDvcntt() { return tenDvcntt; }
    public void setTenDvcntt(String tenDvcntt) { this.tenDvcntt = tenDvcntt; }
    public String getLoaiHinhKinhDoanh() { return loaiHinhKinhDoanh; }
    public void setLoaiHinhKinhDoanh(String loaiHinhKinhDoanh) { this.loaiHinhKinhDoanh = loaiHinhKinhDoanh; }
    public String getMaSoThue() { return maSoThue; }
    public void setMaSoThue(String maSoThue) { this.maSoThue = maSoThue; }
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getDiaChiMac() { return diaChiMac; }
    public void setDiaChiMac(String diaChiMac) { this.diaChiMac = diaChiMac; }
    public String getSoImei() { return soImei; }
    public void setSoImei(String soImei) { this.soImei = soImei; }
    public String getSoTaiKhoan() { return soTaiKhoan; }
    public void setSoTaiKhoan(String soTaiKhoan) { this.soTaiKhoan = soTaiKhoan; }
    public String getTenChuTaiKhoan() { return tenChuTaiKhoan; }
    public void setTenChuTaiKhoan(String tenChuTaiKhoan) { this.tenChuTaiKhoan = tenChuTaiKhoan; }
    public String getNganHangMoTk() { return nganHangMoTk; }
    public void setNganHangMoTk(String nganHangMoTk) { this.nganHangMoTk = nganHangMoTk; }
    public Integer getLoaiTaiKhoan() { return loaiTaiKhoan; }
    public void setLoaiTaiKhoan(Integer loaiTaiKhoan) { this.loaiTaiKhoan = loaiTaiKhoan; }
    public Integer getTrangThaiTaiKhoan() { return trangThaiTaiKhoan; }
    public void setTrangThaiTaiKhoan(Integer trangThaiTaiKhoan) { this.trangThaiTaiKhoan = trangThaiTaiKhoan; }
    public String getNgayMoTaiKhoan() { return ngayMoTaiKhoan; }
    public void setNgayMoTaiKhoan(String ngayMoTaiKhoan) { this.ngayMoTaiKhoan = ngayMoTaiKhoan; }
    public String getTemplateID() { return templateID; }
    public void setTemplateID(String templateID) { this.templateID = templateID; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
} 