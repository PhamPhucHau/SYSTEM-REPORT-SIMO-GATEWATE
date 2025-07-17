package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_27_TT_DVCNTT_DTO {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Cif")
    private String cif;

    @JsonProperty("MaSoDoanhNghiep")
    private String maSoDoanhNghiep;

    @JsonProperty("SoId")
    private String soId;

    @JsonProperty("LoaiId")
    private Integer loaiId;

    @JsonProperty("HoTenNguoiDaiDien")
    private String hoTenNguoiDaiDien;

    @JsonProperty("NgaySinh")
    private String ngaySinh;

    @JsonProperty("QuocTich")
    private String quocTich;

    @JsonProperty("TenDvcntt")
    private String tenDvcntt;

    @JsonProperty("loaiHinhKinhDoanh")
    private String loaiHinhKinhDoanh;

    @JsonProperty("MaSoThue")
    private String maSoThue;

    @JsonProperty("DienThoai")
    private String dienThoai;

    @JsonProperty("DiaChi")
    private String diaChi;

    @JsonProperty("DiaChiMac")
    private String diaChiMac;

    @JsonProperty("SoImei")
    private String soImei;

    @JsonProperty("SoTaiKhoan")
    private String soTaiKhoan;

    @JsonProperty("TenChuTaiKhoan")
    private String tenChuTaiKhoan;

    @JsonProperty("NganHangMoTk")
    private String nganHangMoTk;

    @JsonProperty("LoaiTaiKhoan")
    private Integer loaiTaiKhoan;

    @JsonProperty("TrangThaiTaiKhoan")
    private Integer trangThaiTaiKhoan;

    @JsonProperty("NgayMoTaiKhoan")
    private String ngayMoTaiKhoan;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getMaSoDoanhNghiep() {
        return maSoDoanhNghiep;
    }

    public void setMaSoDoanhNghiep(String maSoDoanhNghiep) {
        this.maSoDoanhNghiep = maSoDoanhNghiep;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public Integer getLoaiId() {
        return loaiId;
    }

    public void setLoaiId(Integer loaiId) {
        this.loaiId = loaiId;
    }

    public String getHoTenNguoiDaiDien() {
        return hoTenNguoiDaiDien;
    }

    public void setHoTenNguoiDaiDien(String hoTenNguoiDaiDien) {
        this.hoTenNguoiDaiDien = hoTenNguoiDaiDien;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getTenDvcntt() {
        return tenDvcntt;
    }

    public void setTenDvcntt(String tenDvcntt) {
        this.tenDvcntt = tenDvcntt;
    }

    public String getLoaiHinhKinhDoanh() {
        return loaiHinhKinhDoanh;
    }

    public void setLoaiHinhKinhDoanh(String loaiHinhKinhDoanh) {
        this.loaiHinhKinhDoanh = loaiHinhKinhDoanh;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDiaChiMac() {
        return diaChiMac;
    }

    public void setDiaChiMac(String diaChiMac) {
        this.diaChiMac = diaChiMac;
    }

    public String getSoImei() {
        return soImei;
    }

    public void setSoImei(String soImei) {
        this.soImei = soImei;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getTenChuTaiKhoan() {
        return tenChuTaiKhoan;
    }

    public void setTenChuTaiKhoan(String tenChuTaiKhoan) {
        this.tenChuTaiKhoan = tenChuTaiKhoan;
    }

    public String getNganHangMoTk() {
        return nganHangMoTk;
    }

    public void setNganHangMoTk(String nganHangMoTk) {
        this.nganHangMoTk = nganHangMoTk;
    }

    public Integer getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(Integer loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public Integer getTrangThaiTaiKhoan() {
        return trangThaiTaiKhoan;
    }

    public void setTrangThaiTaiKhoan(Integer trangThaiTaiKhoan) {
        this.trangThaiTaiKhoan = trangThaiTaiKhoan;
    }

    public String getNgayMoTaiKhoan() {
        return ngayMoTaiKhoan;
    }

    public void setNgayMoTaiKhoan(String ngayMoTaiKhoan) {
        this.ngayMoTaiKhoan = ngayMoTaiKhoan;
    }
}
