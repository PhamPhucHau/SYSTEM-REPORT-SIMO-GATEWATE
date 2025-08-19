package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_26_UPDATE_TOCHUC {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String cif;
    private String tenToChuc;
    private String soGiayPhepThanhLap;
    private Integer loaiGiayToThanhLapToChuc;
    private String ngayThanhLap;
    private String diaChiToChuc;
    private String hoTenNguoiDaiDien;
    private String soGiayToTuyThan;
    private Integer loaiGiayToTuyThan;
    private String ngaySinh;
    private Integer gioiTinh;
    private String quocTich;
    private String dienThoai;
    private String soTaiKhoanToChuc;
    private String ngayMoTaiKhoan;
    private Integer trangThaiTaiKhoan;
    private String diaChiMAC;
    private String soImei;
    private String templateID;
    private String monthYear;
    private String username;
    private String status;
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCif() { return cif; }
    public void setCif(String cif) { this.cif = cif; }
    public String getTenToChuc() { return tenToChuc; }
    public void setTenToChuc(String tenToChuc) { this.tenToChuc = tenToChuc; }
    public String getSoGiayPhepThanhLap() { return soGiayPhepThanhLap; }
    public void setSoGiayPhepThanhLap(String soGiayPhepThanhLap) { this.soGiayPhepThanhLap = soGiayPhepThanhLap; }
    public Integer getLoaiGiayToThanhLapToChuc() { return loaiGiayToThanhLapToChuc; }
    public void setLoaiGiayToThanhLapToChuc(Integer loaiGiayToThanhLapToChuc) { this.loaiGiayToThanhLapToChuc = loaiGiayToThanhLapToChuc; }
    public String getNgayThanhLap() { return ngayThanhLap; }
    public void setNgayThanhLap(String ngayThanhLap) { this.ngayThanhLap = ngayThanhLap; }
    public String getDiaChiToChuc() { return diaChiToChuc; }
    public void setDiaChiToChuc(String diaChiToChuc) { this.diaChiToChuc = diaChiToChuc; }
    public String getHoTenNguoiDaiDien() { return hoTenNguoiDaiDien; }
    public void setHoTenNguoiDaiDien(String hoTenNguoiDaiDien) { this.hoTenNguoiDaiDien = hoTenNguoiDaiDien; }
    public String getSoGiayToTuyThan() { return soGiayToTuyThan; }
    public void setSoGiayToTuyThan(String soGiayToTuyThan) { this.soGiayToTuyThan = soGiayToTuyThan; }
    public Integer getLoaiGiayToTuyThan() { return loaiGiayToTuyThan; }
    public void setLoaiGiayToTuyThan(Integer loaiGiayToTuyThan) { this.loaiGiayToTuyThan = loaiGiayToTuyThan; }
    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public Integer getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(Integer gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getQuocTich() { return quocTich; }
    public void setQuocTich(String quocTich) { this.quocTich = quocTich; }
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }
    public String getSoTaiKhoanToChuc() { return soTaiKhoanToChuc; }
    public void setSoTaiKhoanToChuc(String soTaiKhoanToChuc) { this.soTaiKhoanToChuc = soTaiKhoanToChuc; }
    public String getNgayMoTaiKhoan() { return ngayMoTaiKhoan; }
    public void setNgayMoTaiKhoan(String ngayMoTaiKhoan) { this.ngayMoTaiKhoan = ngayMoTaiKhoan; }
    public Integer getTrangThaiTaiKhoan() { return trangThaiTaiKhoan; }
    public void setTrangThaiTaiKhoan(Integer trangThaiTaiKhoan) { this.trangThaiTaiKhoan = trangThaiTaiKhoan; }
    public String getDiaChiMAC() { return diaChiMAC; }
    public void setDiaChiMAC(String diaChiMAC) { this.diaChiMAC = diaChiMAC; }
    public String getSoImei() { return soImei; }
    public void setSoImei(String soImei) { this.soImei = soImei; }
    public String getTemplateID() { return templateID; }
    public void setTemplateID(String templateID) { this.templateID = templateID; }
    public String getMonthYear() { return monthYear; }
    public void setMonthYear(String monthYear) { this.monthYear = monthYear; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 