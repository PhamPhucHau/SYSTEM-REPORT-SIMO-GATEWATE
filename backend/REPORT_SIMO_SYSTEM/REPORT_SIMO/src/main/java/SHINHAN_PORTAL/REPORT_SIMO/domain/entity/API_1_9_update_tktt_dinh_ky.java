package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "API_1_6_tktt_dinh_ky")
public class API_1_9_update_tktt_dinh_ky {
    @Id
    private String id;
    private String cif;
    private String soID;
    private int loaiID;
    private String tenKhachHang;
    private String ngaySinh;
    private int gioiTinh;
    private String maSoThue;
    private String soDienThoaiDangKyDichVu;
    private String diaChi;
    private String diaChiKiemSoatTruyCap;
    private String maSoNhanDangThietBiDiDong;
    private String soTaiKhoan;
    private int loaiTaiKhoan;
    private int trangThaiHoatDongTaiKhoan;
    private String ngayMoTaiKhoan;
    private int phuongThucMoTaiKhoan;
    private String ngayXacThucTaiQuay;
    private String quocTich;
    private String ghiChu;
    private String templateID;
    private String monthYear;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getSoID() {
        return soID;
    }

    public void setSoID(String soID) {
        this.soID = soID;
    }

    public int getLoaiID() {
        return loaiID;
    }

    public void setLoaiID(int loaiID) {
        this.loaiID = loaiID;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public String getSoDienThoaiDangKyDichVu() {
        return soDienThoaiDangKyDichVu;
    }

    public void setSoDienThoaiDangKyDichVu(String soDienThoaiDangKyDichVu) {
        this.soDienThoaiDangKyDichVu = soDienThoaiDangKyDichVu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDiaChiKiemSoatTruyCap() {
        return diaChiKiemSoatTruyCap;
    }

    public void setDiaChiKiemSoatTruyCap(String diaChiKiemSoatTruyCap) {
        this.diaChiKiemSoatTruyCap = diaChiKiemSoatTruyCap;
    }

    public String getMaSoNhanDangThietBiDiDong() {
        return maSoNhanDangThietBiDiDong;
    }

    public void setMaSoNhanDangThietBiDiDong(String maSoNhanDangThietBiDiDong) {
        this.maSoNhanDangThietBiDiDong = maSoNhanDangThietBiDiDong;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public int getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(int loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public int getTrangThaiHoatDongTaiKhoan() {
        return trangThaiHoatDongTaiKhoan;
    }

    public void setTrangThaiHoatDongTaiKhoan(int trangThaiHoatDongTaiKhoan) {
        this.trangThaiHoatDongTaiKhoan = trangThaiHoatDongTaiKhoan;
    }

    public String getNgayMoTaiKhoan() {
        return ngayMoTaiKhoan;
    }

    public void setNgayMoTaiKhoan(String ngayMoTaiKhoan) {
        this.ngayMoTaiKhoan = ngayMoTaiKhoan;
    }

    public int getPhuongThucMoTaiKhoan() {
        return phuongThucMoTaiKhoan;
    }

    public void setPhuongThucMoTaiKhoan(int phuongThucMoTaiKhoan) {
        this.phuongThucMoTaiKhoan = phuongThucMoTaiKhoan;
    }

    public String getNgayXacThucTaiQuay() {
        return ngayXacThucTaiQuay;
    }

    public void setNgayXacThucTaiQuay(String ngayXacThucTaiQuay) {
        this.ngayXacThucTaiQuay = ngayXacThucTaiQuay;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
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


    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
