package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TKTTRequestDTO {
    @JsonProperty("Cif")
    private String cif;

    @JsonProperty("SoID")
    private String soID;

    @JsonProperty("LoaiID")
    private int loaiID;

    @JsonProperty("TenKhachHang")
    private String tenKhachHang;

    @JsonProperty("NgaySinh")
    private String ngaySinh;

    @JsonProperty("GioiTinh")
    private int gioiTinh;

    @JsonProperty("MaSoThue")
    private String maSoThue;

    @JsonProperty("SoDienThoaiDangKyDichVu")
    private String soDienThoaiDangKyDichVu;

    @JsonProperty("DiaChi")
    private String diaChi;

    @JsonProperty("DiaChiKiemSoatTruyCap")
    private String diaChiKiemSoatTruyCap;

    @JsonProperty("MaSoNhanDangThietBiDiDong")
    private String maSoNhanDangThietBiDiDong;

    @JsonProperty("SoTaiKhoan")
    private String soTaiKhoan;

    @JsonProperty("LoaiTaiKhoan")
    private int loaiTaiKhoan;

    @JsonProperty("TrangThaiHoatDongTaiKhoan")
    private int trangThaiHoatDongTaiKhoan;

    @JsonProperty("NgayMoTaiKhoan")
    private String ngayMoTaiKhoan;

    @JsonProperty("PhuongThucMoTaiKhoan")
    private int phuongThucMoTaiKhoan;

    @JsonProperty("NgayXacThucTaiQuay")
    private String ngayXacThucTaiQuay;

    @JsonProperty("QuocTich")
    private String quocTich;
    // Getters and Setters
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
}