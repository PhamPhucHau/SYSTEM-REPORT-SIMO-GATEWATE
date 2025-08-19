package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_23_TOCHUC_DTO {
    private String key;
    @JsonProperty("Cif")
    private String cif;
    @JsonProperty("TenToChuc")
    private String tenToChuc;
    @JsonProperty("SoGiayPhepThanhLap")
    private String soGiayPhepThanhLap;
    @JsonProperty("LoaiGiayToThanhLapToChuc")
    private Integer loaiGiayToThanhLapToChuc;
    @JsonProperty("NgayThanhLap")
    private String ngayThanhLap;
    @JsonProperty("DiaChiToChuc")
    private String diaChiToChuc;
    @JsonProperty("HoTenNguoiDaiDien")
    private String hoTenNguoiDaiDien;
    @JsonProperty("SoGiayToTuyThan")
    private String soGiayToTuyThan;
    @JsonProperty("LoaiGiayToTuyThan")
    private Integer loaiGiayToTuyThan;
    @JsonProperty("NgaySinh")
    private String ngaySinh;
    @JsonProperty("GioiTinh")
    private Integer gioiTinh;
    @JsonProperty("QuocTich")
    private String quocTich;
    @JsonProperty("DienThoai")
    private String dienThoai;
    @JsonProperty("SoTaiKhoanToChuc")
    private String soTaiKhoanToChuc;
    @JsonProperty("NgayMoTaiKhoan")
    private String ngayMoTaiKhoan;
    @JsonProperty("TrangThaiTaiKhoan")
    private Integer trangThaiTaiKhoan;
    @JsonProperty("DiaChiMAC")
    private String diaChiMAC;
    @JsonProperty("SO_IMEI")
    private String soImei;
      @JsonIgnore
    private String status = "00"; // Giá trị mặc định
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // Getters and Setters
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
} 