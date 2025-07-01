package com.org.shbvn.svbsimo.core.model;

public class API17TTDSTKTTNNGLTemplate extends BankCommonTemplate {
    private String SoTaiKhoan;
    private String TenKhachHang;
    private String TrangThaiHoatDongTaiKhoan;
    private String NghiNgo;
    private String GhiChu;

    public API17TTDSTKTTNNGLTemplate() {
        super();
    }
    public API17TTDSTKTTNNGLTemplate(String rowNumber, String cif) {
        super(rowNumber, cif);
    }
    public API17TTDSTKTTNNGLTemplate(String rowNumber, String cif, String soTaiKhoan, String tenKhachHang,
                                      String trangThaiHoatDongTaiKhoan, String nghiNgo, String ghiChu) {
        super(rowNumber, cif);
        this.SoTaiKhoan = soTaiKhoan;
        this.TenKhachHang = tenKhachHang;
        this.TrangThaiHoatDongTaiKhoan = trangThaiHoatDongTaiKhoan;
        this.NghiNgo = nghiNgo;
        this.GhiChu = ghiChu;
    }


    public String getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getTrangThaiHoatDongTaiKhoan() {
        return TrangThaiHoatDongTaiKhoan;
    }

    public void setTrangThaiHoatDongTaiKhoan(String trangThaiHoatDongTaiKhoan) {
        TrangThaiHoatDongTaiKhoan = trangThaiHoatDongTaiKhoan;
    }

    public String getNghiNgo() {
        return NghiNgo;
    }

    public void setNghiNgo(String nghiNgo) {
        NghiNgo = nghiNgo;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}
