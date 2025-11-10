package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class API_1_31_TT_TNH_DTO {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Cif")
    private String cif;

    @JsonProperty("SoId")
    private String soId;

    @JsonProperty("LoaiId")
    private Integer loaiId;

    @JsonProperty("TenChuTheHoacNguoiUyQuyen")
    private String tenChuTheHoacNguoiUyQuyen;

    @JsonProperty("NgaySinh")
    private String ngaySinh;

    @JsonProperty("GioiTinh")
    private Integer gioiTinh;

    @JsonProperty("QuocTich")
    private String quocTich;

    @JsonProperty("DienThoai")
    private String dienThoai;

    @JsonProperty("DiaChi")
    private String diaChi;

    @JsonProperty("DiaChiMac")
    private String diaChiMac;

    @JsonProperty("SoImei")
    private String soImei;

    @JsonProperty("SoThe")
    private String soThe;

    @JsonProperty("LoaiThe")
    private Integer loaiThe;

    @JsonProperty("NgayPhatHanh")
    private String ngayPhatHanh;

    @JsonProperty("ThoiHanHieuLuc")
    private String thoiHanHieuLuc;

    @JsonProperty("BIN")
    private String bin;

    @JsonProperty("TrangThaiThe")
    private Integer trangThaiThe;

    @JsonProperty("PhuongThucMoThe")
    private Integer phuongThucMoThe;

    @JsonIgnore
    private String status = "00";

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
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
    public Integer getLoaiThe() { return loaiThe; }
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
}