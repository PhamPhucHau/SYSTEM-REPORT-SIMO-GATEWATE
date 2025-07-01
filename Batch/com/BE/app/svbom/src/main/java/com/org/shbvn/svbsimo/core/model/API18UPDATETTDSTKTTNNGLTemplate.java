package com.org.shbvn.svbsimo.core.model;

public class API18UPDATETTDSTKTTNNGLTemplate extends BankCommonTemplate {
    private String TenKhachHang;
    private String SoTaiKhoan;
    private String TrangThaiHoatDongTaiKhoan;
    private String NghiNgo;
    private String GhiChu;
    private String LyDoCapNhat;

    public API18UPDATETTDSTKTTNNGLTemplate() {
        super();
    }
    public API18UPDATETTDSTKTTNNGLTemplate(String rowNumber, String cif) {
        super(rowNumber, cif);
    }
    public API18UPDATETTDSTKTTNNGLTemplate(String rowNumber, String cif, String soTaiKhoan, String tenKhachHang,
                                            String trangThaiHoatDongTaiKhoan, String nghiNgo, String ghiChu, String lyDoCapNhat) {
        super(rowNumber, cif);
        this.SoTaiKhoan = soTaiKhoan;
        this.TenKhachHang = tenKhachHang;
        this.TrangThaiHoatDongTaiKhoan = trangThaiHoatDongTaiKhoan;
        this.NghiNgo = nghiNgo;
        this.GhiChu = ghiChu;
        this.LyDoCapNhat = lyDoCapNhat;
    }


    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getSoTaiKhoan() {
        return SoTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
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

    public String getLyDoCapNhat() {
        return LyDoCapNhat;
    }

    public void setLyDoCapNhat(String lyDoCapNhat) {
        LyDoCapNhat = lyDoCapNhat;
    }
}
