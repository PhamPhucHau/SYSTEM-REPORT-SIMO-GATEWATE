package com.org.shbvn.svbsimo.core.model;

public class API19TTTKINSTTemplate extends BankCommonTemplate {
    private String SoID;
    private String LoaiID;
    private String TenKhachHang;
    private String NgaySinh;
    private String GioiTinh;
    private String QuocTich;
    private String MaSoThue;
    private String SoDienThoaiDangKyDichVu;
    private String DiaChi;
    private String DiaChiKiemSoatTruyCap;
    private String MaSoNhanDangThietBiDiDong;
    private String SoTaiKhoan;
    private String LoaiTaiKhoan;
    private String TrangThaiHoatDongTaiKhoan;
    private String NgayMoTaiKhoan;
    private String PhuongThucMoTaiKhoan;
    private String NgayXacThucTaiQuay;
    private String GhiChu;

    public API19TTTKINSTTemplate() {
        super();
    }

    public API19TTTKINSTTemplate(String rowNumber, String cif, String soID, String loaiID, String tenKhachHang,
                                 String ngaySinh, String gioiTinh, String quocTich, String maSoThue,
                                 String soDienThoaiDangKyDichVu, String diaChi, String diaChiKiemSoatTruyCap,
                                 String maSoNhanDangThietBiDiDong, String soTaiKhoan, String loaiTaiKhoan,
                                 String trangThaiHoatDongTaiKhoan, String ngayMoTaiKhoan,
                                 String phuongThucMoTaiKhoan, String ngayXacThucTaiQuay, String ghiChu) {
        super(rowNumber, cif);
        this.SoID = soID;
        this.LoaiID = loaiID;
        this.TenKhachHang = tenKhachHang;
        this.NgaySinh = ngaySinh;
        this.GioiTinh = gioiTinh;
        this.QuocTich = quocTich;
        this.MaSoThue = maSoThue;
        this.SoDienThoaiDangKyDichVu = soDienThoaiDangKyDichVu;
        this.DiaChi = diaChi;
        this.DiaChiKiemSoatTruyCap = diaChiKiemSoatTruyCap;
        this.MaSoNhanDangThietBiDiDong = maSoNhanDangThietBiDiDong;
        this.SoTaiKhoan = soTaiKhoan;
        this.LoaiTaiKhoan = loaiTaiKhoan;
        this.TrangThaiHoatDongTaiKhoan = trangThaiHoatDongTaiKhoan;
        this.NgayMoTaiKhoan = ngayMoTaiKhoan;
        this.PhuongThucMoTaiKhoan = phuongThucMoTaiKhoan;
        this.NgayXacThucTaiQuay = ngayXacThucTaiQuay;
        this.GhiChu = ghiChu;
    }

    public String getSoID() {
        return SoID;
    }

    public void setSoID(String soID) {
        SoID = soID;
    }

    public String getLoaiID() {
        return LoaiID;
    }

    public void setLoaiID(String loaiID) {
        LoaiID = loaiID;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getQuocTich() {
        return QuocTich;
    }

    public void setQuocTich(String quocTich) {
        QuocTich = quocTich;
    }

    public String getMaSoThue() {
        return MaSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        MaSoThue = maSoThue;
    }

    public String getSoDienThoaiDangKyDichVu() {
        return SoDienThoaiDangKyDichVu;
    }

    public void setSoDienThoaiDangKyDichVu(String soDienThoaiDangKyDichVu) {
        SoDienThoaiDangKyDichVu = soDienThoaiDangKyDichVu;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getDiaChiKiemSoatTruyCap() {
        return DiaChiKiemSoatTruyCap;
    }

    public void setDiaChiKiemSoatTruyCap(String diaChiKiemSoatTruyCap) {
        DiaChiKiemSoatTruyCap = diaChiKiemSoatTruyCap;
    }

    public String getMaSoNhanDangThietBiDiDong() {
        return MaSoNhanDangThietBiDiDong;
    }

    public void setMaSoNhanDangThietBiDiDong(String maSoNhanDangThietBiDiDong) {
        MaSoNhanDangThietBiDiDong = maSoNhanDangThietBiDiDong;
    }

    public String getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }

    public String getLoaiTaiKhoan() {
        return LoaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        LoaiTaiKhoan = loaiTaiKhoan;
    }

    public String getTrangThaiHoatDongTaiKhoan() {
        return TrangThaiHoatDongTaiKhoan;
    }

    public void setTrangThaiHoatDongTaiKhoan(String trangThaiHoatDongTaiKhoan) {
        TrangThaiHoatDongTaiKhoan = trangThaiHoatDongTaiKhoan;
    }

    public String getNgayMoTaiKhoan() {
        return NgayMoTaiKhoan;
    }

    public void setNgayMoTaiKhoan(String ngayMoTaiKhoan) {
        NgayMoTaiKhoan = ngayMoTaiKhoan;
    }

    public String getPhuongThucMoTaiKhoan() {
        return PhuongThucMoTaiKhoan;
    }

    public void setPhuongThucMoTaiKhoan(String phuongThucMoTaiKhoan) {
        PhuongThucMoTaiKhoan = phuongThucMoTaiKhoan;
    }

    public String getNgayXacThucTaiQuay() {
        return NgayXacThucTaiQuay;
    }

    public void setNgayXacThucTaiQuay(String ngayXacThucTaiQuay) {
        NgayXacThucTaiQuay = ngayXacThucTaiQuay;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}
