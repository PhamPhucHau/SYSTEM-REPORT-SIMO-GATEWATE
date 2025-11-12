package SHINHAN_PORTAL.REPORT_SIMO.common;

import java.util.Map;

import org.springframework.stereotype.Component;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_23_TOCHUC_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_24_TOCHUC_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_25_UPDATE_TOCHUC_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_26_UPDATE_TOCHUC_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_27_TT_DVCNTT_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_28_TT_DVCNTT_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_29_UPDATE_DVCNTT_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_30_UPDATE_DVCNTT_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_31_TT_TNH_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_32_TT_TNH_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_33_UPDATE_TNH_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_34_UPDATE_TNH_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_7_tktt_nggl_DT0;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_8_update_tktt_nggl_DT0;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_9_update_tktt_nggl_DT0;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TKTTRequestDTO;


@Component
public class DataMapperUtils {

    public static String safeString(Object value) {
        return safeString(value, "");
    }

    public static String safeString(Object value, String defaultValue) {
        return safeString(value, defaultValue, false);
    }

    public static String safeString(Object value, String defaultValue, boolean nullIfEmpty) {
        if (value == null) return defaultValue;
        String result = String.valueOf(value);
        if (nullIfEmpty && result.trim().isEmpty()) return null;
        return result;
    }

    public static Integer safeInteger(Object value, Integer defaultValue) {
        if (value == null) return defaultValue;
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }
     // --- Mappers for API_1_27_TT_DVCNTT_DTO ---
     public static API_1_27_TT_DVCNTT_DTO mapTo_API_1_27_TT_DVCNTT(Map<String, Object> dataMap) {
        API_1_27_TT_DVCNTT_DTO dto = new API_1_27_TT_DVCNTT_DTO();
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setMaSoDoanhNghiep(safeString(dataMap.getOrDefault("MaSoDoanhNghiep", dataMap.get("maSoDoanhNghiep"))));
        dto.setSoId(safeString(dataMap.getOrDefault("SoId", dataMap.get("soId"))));
        dto.setLoaiId(safeInteger(dataMap.getOrDefault("LoaiId", dataMap.get("loaiId")), null));
        dto.setHoTenNguoiDaiDien(safeString(dataMap.getOrDefault("HoTenNguoiDaiDien", dataMap.get("hoTenNguoiDaiDien"))));
        dto.setNgaySinh(safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh"))));
        dto.setQuocTich(safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        dto.setTenDvcntt(safeString(dataMap.getOrDefault("TenDvcntt", dataMap.get("tenDvcntt"))));
        dto.setLoaiHinhKinhDoanh(safeString(dataMap.getOrDefault("LoaiHinhKinhDoanh", dataMap.get("loaiHinhKinhDoanh"))));
        dto.setMaSoThue(safeString(dataMap.getOrDefault("MaSoThue", dataMap.get("maSoThue"))));
        dto.setDienThoai(safeString(dataMap.getOrDefault("DienThoai", dataMap.get("dienThoai"))));
        dto.setDiaChi(safeString(dataMap.getOrDefault("DiaChi", dataMap.get("diaChi"))));
        dto.setDiaChiMac(safeString(dataMap.getOrDefault("DiaChiMac", dataMap.get("diaChiMac"))));
        dto.setSoImei(safeString(dataMap.getOrDefault("SoImei", dataMap.get("soImei"))));
        dto.setSoTaiKhoan(safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.get("soTaiKhoan"))));
        dto.setTenChuTaiKhoan(safeString(dataMap.getOrDefault("TenChuTaiKhoan", dataMap.get("tenChuTaiKhoan"))));
        dto.setNganHangMoTk(safeString(dataMap.getOrDefault("NganHangMoTk", dataMap.get("nganHangMoTk"))));
        dto.setLoaiTaiKhoan(safeInteger(dataMap.getOrDefault("LoaiTaiKhoan", dataMap.get("loaiTaiKhoan")), null));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setNgayMoTaiKhoan(safeString(dataMap.getOrDefault("NgayMoTaiKhoan", dataMap.get("ngayMoTaiKhoan")), null, true));
        return dto;
    }

    // --- Mappers for API_1_28_TT_DVCNTT_NGGL_DTO ---
    public static API_1_28_TT_DVCNTT_NGGL_DTO mapTo_API_1_28_TT_DVCNTT_NGGL(Map<String, Object> dataMap) {
        API_1_28_TT_DVCNTT_NGGL_DTO dto = new API_1_28_TT_DVCNTT_NGGL_DTO();
        dto.setKey(safeString(dataMap.getOrDefault("Key", dataMap.get("key"))));
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenDvcntt(safeString(dataMap.getOrDefault("TenDvcntt", dataMap.get("tenDvcntt"))));
        dto.setMaSoDoanhNghiep(safeString(dataMap.getOrDefault("MaSoDoanhNghiep", dataMap.get("maSoDoanhNghiep"))));
        dto.setSoTaiKhoan(safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.get("soTaiKhoan"))));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setNghiNgo(safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.get("nghiNgo")), null));
        dto.setGhiChu(safeString(dataMap.getOrDefault("GhiChu", dataMap.get("ghiChu"))));
        return dto;
    }

    // --- Mappers for API_1_29_UPDATE_DVCNTT_NGGL_DTO ---
    public static API_1_29_UPDATE_DVCNTT_NGGL_DTO mapTo_API_1_29_UPDATE_DVCNTT_NGGL(Map<String, Object> dataMap) {
        API_1_29_UPDATE_DVCNTT_NGGL_DTO dto = new API_1_29_UPDATE_DVCNTT_NGGL_DTO();
        dto.setKey(safeString(dataMap.getOrDefault("Key", dataMap.get("key"))));
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenDvcntt(safeString(dataMap.getOrDefault("TenDvcntt", dataMap.get("tenDvcntt"))));
        dto.setMaSoDoanhNghiep(safeString(dataMap.getOrDefault("MaSoDoanhNghiep", dataMap.get("maSoDoanhNghiep"))));
        dto.setSoTaiKhoan(safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.get("soTaiKhoan"))));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setNghiNgo(safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.get("nghiNgo")), null));
        dto.setLyDoCapNhat(safeString(dataMap.getOrDefault("LyDoCapNhat", dataMap.get("lyDoCapNhat"))));
        dto.setGhiChu(safeString(dataMap.getOrDefault("GhiChu", dataMap.get("ghiChu"))));
        return dto;
    }

    // --- Mappers for API_1_30_UPDATE_DVCNTT_DTO ---
    public static API_1_30_UPDATE_DVCNTT_DTO mapTo_API_1_30_UPDATE_DVCNTT(Map<String, Object> dataMap) {
        API_1_30_UPDATE_DVCNTT_DTO dto = new API_1_30_UPDATE_DVCNTT_DTO();
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setMaSoDoanhNghiep(safeString(dataMap.getOrDefault("MaSoDoanhNghiep", dataMap.get("maSoDoanhNghiep"))));
        dto.setSoId(safeString(dataMap.getOrDefault("SoId", dataMap.get("soId"))));
        dto.setLoaiId(safeInteger(dataMap.getOrDefault("LoaiId", dataMap.get("loaiId")), null));
        dto.setHoTenNguoiDaiDien(safeString(dataMap.getOrDefault("HoTenNguoiDaiDien", dataMap.get("hoTenNguoiDaiDien"))));
        dto.setNgaySinh(safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh"))));
        dto.setQuocTich(safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        dto.setTenDvcntt(safeString(dataMap.getOrDefault("TenDvcntt", dataMap.get("tenDvcntt"))));
        dto.setLoaiHinhKinhDoanh(safeString(dataMap.getOrDefault("LoaiHinhKinhDoanh", dataMap.get("loaiHinhKinhDoanh"))));
        dto.setMaSoThue(safeString(dataMap.getOrDefault("MaSoThue", dataMap.get("maSoThue"))));
        dto.setDienThoai(safeString(dataMap.getOrDefault("DienThoai", dataMap.get("dienThoai"))));
        dto.setDiaChi(safeString(dataMap.getOrDefault("DiaChi", dataMap.get("diaChi"))));
        dto.setDiaChiMac(safeString(dataMap.getOrDefault("DiaChiMac", dataMap.get("diaChiMac"))));
        dto.setSoImei(safeString(dataMap.getOrDefault("SoImei", dataMap.get("soImei"))));
        dto.setSoTaiKhoan(safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.get("soTaiKhoan"))));
        dto.setTenChuTaiKhoan(safeString(dataMap.getOrDefault("TenChuTaiKhoan", dataMap.get("tenChuTaiKhoan"))));
        dto.setNganHangMoTk(safeString(dataMap.getOrDefault("NganHangMoTk", dataMap.get("nganHangMoTk"))));
        dto.setLoaiTaiKhoan(safeInteger(dataMap.getOrDefault("LoaiTaiKhoan", dataMap.get("loaiTaiKhoan")), null));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setNgayMoTaiKhoan(safeString(dataMap.getOrDefault("NgayMoTaiKhoan", dataMap.get("ngayMoTaiKhoan")), null, true));
        dto.setGhiChu(safeString(dataMap.getOrDefault("GhiChu", dataMap.get("ghiChu"))));
        return dto;
    }

    public static API_1_23_TOCHUC_DTO mapTo_API_1_23_TOCHUC(Map<String, Object> dataMap) {
        API_1_23_TOCHUC_DTO dto = new API_1_23_TOCHUC_DTO();
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenToChuc(safeString(dataMap.getOrDefault("TenToChuc", dataMap.get("tenToChuc"))));
        dto.setSoGiayPhepThanhLap(safeString(dataMap.getOrDefault("SoGiayPhepThanhLap", dataMap.get("soGiayPhepThanhLap"))));
        dto.setLoaiGiayToThanhLapToChuc(safeInteger(dataMap.getOrDefault("LoaiGiayToThanhLapToChuc", dataMap.get("loaiGiayToThanhLapToChuc")), null));
        dto.setNgayThanhLap(safeString(dataMap.getOrDefault("NgayThanhLap", dataMap.get("ngayThanhLap"))));
        dto.setDiaChiToChuc(safeString(dataMap.getOrDefault("DiaChiToChuc", dataMap.get("diaChiToChuc"))));
        dto.setHoTenNguoiDaiDien(safeString(dataMap.getOrDefault("HoTenNguoiDaiDien", dataMap.get("hoTenNguoiDaiDien"))));
        dto.setSoGiayToTuyThan(safeString(dataMap.getOrDefault("SoGiayToTuyThan", dataMap.get("soGiayToTuyThan"))));
        dto.setLoaiGiayToTuyThan(safeInteger(dataMap.getOrDefault("LoaiGiayToTuyThan", dataMap.get("loaiGiayToTuyThan")), null));
        dto.setNgaySinh(safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh"))));
        dto.setGioiTinh(safeInteger(dataMap.getOrDefault("GioiTinh", dataMap.get("gioiTinh")), null));
        dto.setQuocTich(safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        dto.setDienThoai(safeString(dataMap.getOrDefault("DienThoai", dataMap.get("dienThoai"))));
        dto.setSoTaiKhoanToChuc(safeString(dataMap.getOrDefault("SoTaiKhoanToChuc", dataMap.get("soTaiKhoanToChuc"))));
        dto.setNgayMoTaiKhoan(safeString(dataMap.getOrDefault("NgayMoTaiKhoan", dataMap.get("ngayMoTaiKhoan"))));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setDiaChiMAC(safeString(dataMap.getOrDefault("DiaChiMAC", dataMap.get("diaChiMAC"))));
        dto.setSoImei(safeString(dataMap.getOrDefault("SoImei", dataMap.get("soImei"))));
        return dto;
    }
    
    public static API_1_24_TOCHUC_NGGL_DTO mapTo_API_1_24_TOCHUC_NGGL(Map<String, Object> dataMap) {
        API_1_24_TOCHUC_NGGL_DTO dto = new API_1_24_TOCHUC_NGGL_DTO();
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenToChuc(safeString(dataMap.getOrDefault("TenToChuc", dataMap.get("tenToChuc"))));
        dto.setSoGiayPhepThanhLap(safeString(dataMap.getOrDefault("SoGiayPhepThanhLap", dataMap.get("soGiayPhepThanhLap"))));
        dto.setSoTaiKhoanToChuc(safeString(dataMap.getOrDefault("SoTaiKhoanToChuc", dataMap.get("soTaiKhoanToChuc"))));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setNghiNgo(safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.get("nghiNgo")), null));
        return dto;
    }
    
    public static API_1_25_UPDATE_TOCHUC_NGGL_DTO mapTo_API_1_25_UPDATE_TOCHUC_NGGL(Map<String, Object> dataMap) {
        API_1_25_UPDATE_TOCHUC_NGGL_DTO dto = new API_1_25_UPDATE_TOCHUC_NGGL_DTO();
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenToChuc(safeString(dataMap.getOrDefault("TenToChuc", dataMap.get("tenToChuc"))));
        dto.setSoGiayPhepThanhLap(safeString(dataMap.getOrDefault("SoGiayPhepThanhLap", dataMap.get("soGiayPhepThanhLap"))));
        dto.setSoTaiKhoanToChuc(safeString(dataMap.getOrDefault("SoTaiKhoanToChuc", dataMap.get("soTaiKhoanToChuc"))));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setNghiNgo(safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.get("nghiNgo")), null));
        dto.setLyDoCapNhat(safeString(dataMap.getOrDefault("LyDoCapNhat", dataMap.get("lyDoCapNhat"))));
        return dto;
    }
    
    public static API_1_26_UPDATE_TOCHUC_DTO mapTo_API_1_26_UPDATE_TOCHUC(Map<String, Object> dataMap) {
        API_1_26_UPDATE_TOCHUC_DTO dto = new API_1_26_UPDATE_TOCHUC_DTO();
        dto.setCif(safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenToChuc(safeString(dataMap.getOrDefault("TenToChuc", dataMap.get("tenToChuc"))));
        dto.setSoGiayPhepThanhLap(safeString(dataMap.getOrDefault("SoGiayPhepThanhLap", dataMap.get("soGiayPhepThanhLap"))));
        dto.setLoaiGiayToThanhLapToChuc(safeInteger(dataMap.getOrDefault("LoaiGiayToThanhLapToChuc", dataMap.get("loaiGiayToThanhLapToChuc")), null));
        dto.setNgayThanhLap(safeString(dataMap.getOrDefault("NgayThanhLap", dataMap.get("ngayThanhLap"))));
        dto.setDiaChiToChuc(safeString(dataMap.getOrDefault("DiaChiToChuc", dataMap.get("diaChiToChuc"))));
        dto.setHoTenNguoiDaiDien(safeString(dataMap.getOrDefault("HoTenNguoiDaiDien", dataMap.get("hoTenNguoiDaiDien"))));
        dto.setSoGiayToTuyThan(safeString(dataMap.getOrDefault("SoGiayToTuyThan", dataMap.get("soGiayToTuyThan"))));
        dto.setLoaiGiayToTuyThan(safeInteger(dataMap.getOrDefault("LoaiGiayToTuyThan", dataMap.get("loaiGiayToTuyThan")), null));
        dto.setNgaySinh(safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh"))));
        dto.setGioiTinh(safeInteger(dataMap.getOrDefault("GioiTinh", dataMap.get("gioiTinh")), null));
        dto.setQuocTich(safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        dto.setDienThoai(safeString(dataMap.getOrDefault("DienThoai", dataMap.get("dienThoai"))));
        dto.setSoTaiKhoanToChuc(safeString(dataMap.getOrDefault("SoTaiKhoanToChuc", dataMap.get("soTaiKhoanToChuc"))));
        dto.setNgayMoTaiKhoan(safeString(dataMap.getOrDefault("NgayMoTaiKhoan", dataMap.get("ngayMoTaiKhoan"))));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.getOrDefault("TrangThaiTaiKhoan", dataMap.get("trangThaiTaiKhoan")), null));
        dto.setDiaChiMAC(safeString(dataMap.getOrDefault("DiaChiMAC", dataMap.get("diaChiMAC"))));
        dto.setSoImei(safeString(dataMap.getOrDefault("SoImei", dataMap.get("soImei"))));
        return dto;
    }
     // Clean code: tiny helper to avoid duplication and centralize SEND logging
    
     public static TKTTRequestDTO mapToAPI_1_6_tktt_dinh_ky(Map<String, Object> dataMap) {
        TKTTRequestDTO entity = new TKTTRequestDTO();

    
        entity.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        entity.setSoID(DataMapperUtils.safeString(dataMap.getOrDefault("SoID", dataMap.get("soID"))));
        entity.setLoaiID(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiID", dataMap.get("loaiID")), null));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("TenKhachHang", dataMap.get("tenKhachHang"))));
        entity.setNgaySinh(DataMapperUtils.safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh"))));
        entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.getOrDefault("GioiTinh", dataMap.get("gioiTinh")), null));
        entity.setMaSoThue(DataMapperUtils.safeString(dataMap.getOrDefault("MaSoThue", dataMap.get("maSoThue"))));
        entity.setSoDienThoaiDangKyDichVu(DataMapperUtils.safeString(dataMap.getOrDefault("SoDienThoaiDangKyDichVu", dataMap.get("soDienThoaiDangKyDichVu"))));
        entity.setDiaChi(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChi", dataMap.get("diaChi"))));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.get("soTaiKhoan"))));
        entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiTaiKhoan", dataMap.get("loaiTaiKhoan")), null));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiHoatDongTaiKhoan", dataMap.get("trangThaiHoatDongTaiKhoan")), null));
        entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("NgayMoTaiKhoan", dataMap.get("ngayMoTaiKhoan")), null, true));
        entity.setPhuongThucMoTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("PhuongThucMoTaiKhoan", dataMap.get("phuongThucMoTaiKhoan")), null));
        entity.setQuocTich(DataMapperUtils.safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        entity.setDiaChiKiemSoatTruyCap(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChiKiemSoatTruyCap", dataMap.get("diaChiKiemSoatTruyCap"))));
        entity.setMaSoNhanDangThietBiDiDong(DataMapperUtils.safeString(dataMap.getOrDefault("MaSoNhanDangThietBiDiDong", dataMap.get("maSoNhanDangThietBiDiDong"))));
        entity.setNgayXacThucTaiQuay(DataMapperUtils.safeString(dataMap.getOrDefault("NgayXacThucTaiQuay", dataMap.get("ngayXacThucTaiQuay")), null, true));
    
        return entity;
    }
    public static API_1_7_tktt_nggl_DT0 mapToAPI_1_7_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_7_tktt_nggl_DT0 entity = new API_1_7_tktt_nggl_DT0();

        entity.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.getOrDefault("Số tài khoản", dataMap.get("soTaiKhoan")))));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("TenKhachHang", dataMap.getOrDefault("Tên khách hàng", dataMap.get("tenKhachHang")))));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiHoatDongTaiKhoan", dataMap.getOrDefault("Trạng thái hoạt động của tài khoản", dataMap.get("trangThaiHoatDongTaiKhoan"))), null));
        entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.getOrDefault("Nghi ngờ", dataMap.get("nghiNgo"))), null));
        entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("GhiChu", dataMap.getOrDefault("Ghi chú", dataMap.get("ghiChu")))));
        return entity;
    }
    public static API_1_9_update_tktt_nggl_DT0 mapToAPI_1_9_update_tktt_dinh_ky(Map<String, Object> dataMap) {
        API_1_9_update_tktt_nggl_DT0 entity = new API_1_9_update_tktt_nggl_DT0();
       
        entity.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        entity.setSoID(DataMapperUtils.safeString(dataMap.getOrDefault("SoID", dataMap.get("soID"))));
        entity.setLoaiID(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiID", dataMap.get("loaiID")), null));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("TenKhachHang", dataMap.get("tenKhachHang"))));
        entity.setNgaySinh(DataMapperUtils.safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh"))));
        entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.getOrDefault("GioiTinh", dataMap.get("gioiTinh")), null));
        entity.setMaSoThue(DataMapperUtils.safeString(dataMap.getOrDefault("MaSoThue", dataMap.get("maSoThue"))));
        entity.setSoDienThoaiDangKyDichVu(DataMapperUtils.safeString(dataMap.getOrDefault("SoDienThoaiDangKyDichVu", dataMap.get("soDienThoaiDangKyDichVu"))));
        entity.setDiaChi(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChi", dataMap.get("diaChi"))));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.get("soTaiKhoan"))));
        entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiTaiKhoan", dataMap.get("loaiTaiKhoan")), null));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiHoatDongTaiKhoan", dataMap.get("trangThaiHoatDongTaiKhoan")), null));
        entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("NgayMoTaiKhoan", dataMap.get("ngayMoTaiKhoan")), null, true));
        entity.setPhuongThucMoTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("PhuongThucMoTaiKhoan", dataMap.get("phuongThucMoTaiKhoan")), null));
        entity.setQuocTich(DataMapperUtils.safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        entity.setDiaChiKiemSoatTruyCap(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChiKiemSoatTruyCap", dataMap.get("diaChiKiemSoatTruyCap"))));
        entity.setMaSoNhanDangThietBiDiDong(DataMapperUtils.safeString(dataMap.getOrDefault("MaSoNhanDangThietBiDiDong", dataMap.get("maSoNhanDangThietBiDiDong"))));
        entity.setNgayXacThucTaiQuay(DataMapperUtils.safeString(dataMap.getOrDefault("NgayXacThucTaiQuay", dataMap.get("ngayXacThucTaiQuay")), null, true));
        entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("GhiChu", dataMap.getOrDefault("Ghi chú", dataMap.get("ghiChu")))));
        
        return entity;
    }
    public static API_1_8_update_tktt_nggl_DT0 mapToAPI_1_8_update_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_8_update_tktt_nggl_DT0 entity = new API_1_8_update_tktt_nggl_DT0();
        entity.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("SoTaiKhoan", dataMap.getOrDefault("Số tài khoản", dataMap.get("soTaiKhoan")))));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("TenKhachHang", dataMap.getOrDefault("Tên khách hàng", dataMap.get("tenKhachHang")))));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiHoatDongTaiKhoan", dataMap.getOrDefault("Trạng thái hoạt động của tài khoản", dataMap.get("trangThaiHoatDongTaiKhoan"))), null));
        entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.getOrDefault("Nghi ngờ", dataMap.get("nghiNgo"))), null));
        entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("GhiChu", dataMap.getOrDefault("Ghi chú", dataMap.get("ghiChu")))));
        entity.setLyDoCapNhat(DataMapperUtils.safeString(dataMap.getOrDefault("LyDoCapNhat", dataMap.getOrDefault("Lý do cập nhật", dataMap.get("lyDoCapNhat")))));
        return entity;
    }
    // Clean code: 4 mapper methods below are cohesive, tiny, and reuse DataMapperUtils for validation/coercion
    // They also accept both Capitalized and camelCase keys to be resilient to source variations
    public static API_1_31_TT_TNH_DTO mapTo_API_1_31_TT_TNH(Map<String, Object> dataMap) {
        API_1_31_TT_TNH_DTO dto = new API_1_31_TT_TNH_DTO();
        dto.setKey(DataMapperUtils.safeString(dataMap.getOrDefault("Key", dataMap.get("key"))));
        dto.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setSoId(DataMapperUtils.safeString(dataMap.getOrDefault("SoId", dataMap.get("soId"))));
        dto.setLoaiId(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiId", dataMap.get("loaiId")), null));
        dto.setTenChuTheHoacNguoiUyQuyen(DataMapperUtils.safeString(dataMap.getOrDefault("TenChuTheHoacNguoiUyQuyen", dataMap.get("tenChuTheHoacNguoiUyQuyen"))));
        dto.setNgaySinh(DataMapperUtils.safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh")), null, true));
        dto.setGioiTinh(DataMapperUtils.safeInteger(dataMap.getOrDefault("GioiTinh", dataMap.get("gioiTinh")), null));
        dto.setQuocTich(DataMapperUtils.safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        dto.setDienThoai(DataMapperUtils.safeString(dataMap.getOrDefault("DienThoai", dataMap.get("dienThoai"))));
        dto.setDiaChi(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChi", dataMap.get("diaChi"))));
        dto.setDiaChiMac(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChiMac", dataMap.get("diaChiMac"))));
        dto.setSoImei(DataMapperUtils.safeString(dataMap.getOrDefault("SoImei", dataMap.get("soImei"))));
        dto.setSoThe(DataMapperUtils.safeString(dataMap.getOrDefault("SoThe", dataMap.get("soThe"))));
        dto.setLoaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiThe", dataMap.get("loaiThe")), null));
        dto.setNgayPhatHanh(DataMapperUtils.safeString(dataMap.getOrDefault("NgayPhatHanh", dataMap.get("ngayPhatHanh"))));
        dto.setThoiHanHieuLuc(DataMapperUtils.safeString(dataMap.getOrDefault("ThoiHanHieuLuc", dataMap.get("thoiHanHieuLuc"))));
        dto.setBin(DataMapperUtils.safeString(dataMap.getOrDefault("BIN", dataMap.getOrDefault("Bin", dataMap.get("bin")))));
        dto.setTrangThaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiThe", dataMap.get("trangThaiThe")), null));
        dto.setPhuongThucMoThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("PhuongThucMoThe", dataMap.get("phuongThucMoThe")), null));
        return dto;
    }
    public static API_1_32_TT_TNH_NGGL_DTO mapTo_API_1_32_TT_TNH_NGGL(Map<String, Object> dataMap) {
        API_1_32_TT_TNH_NGGL_DTO dto = new API_1_32_TT_TNH_NGGL_DTO();
        dto.setKey(DataMapperUtils.safeString(dataMap.getOrDefault("Key", dataMap.get("key"))));
        dto.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenChuTheHoacNguoiUyQuyen(DataMapperUtils.safeString(dataMap.getOrDefault("TenChuTheHoacNguoiUyQuyen", dataMap.get("tenChuTheHoacNguoiUyQuyen"))));
        dto.setSoThe(DataMapperUtils.safeString(dataMap.getOrDefault("SoThe", dataMap.get("soThe"))));
        dto.setLoaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiThe", dataMap.get("loaiThe")), null));
        dto.setTrangThaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiThe", dataMap.get("trangThaiThe")), null));
        dto.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.get("nghiNgo")), null));
        dto.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("GhiChu", dataMap.get("ghiChu"))));
        return dto;
    }
    public static API_1_33_UPDATE_TNH_NGGL_DTO mapTo_API_1_33_UPDATE_TNH_NGGL(Map<String, Object> dataMap) {
        API_1_33_UPDATE_TNH_NGGL_DTO dto = new API_1_33_UPDATE_TNH_NGGL_DTO();
        dto.setKey(DataMapperUtils.safeString(dataMap.getOrDefault("Key", dataMap.get("key"))));
        dto.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setTenChuTheHoacNguoiUyQuyen(DataMapperUtils.safeString(dataMap.getOrDefault("TenChuTheHoacNguoiUyQuyen", dataMap.get("tenChuTheHoacNguoiUyQuyen"))));
        dto.setSoThe(DataMapperUtils.safeString(dataMap.getOrDefault("SoThe", dataMap.get("soThe"))));
        dto.setLoaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiThe", dataMap.get("loaiThe")), null));
        dto.setTrangThaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiThe", dataMap.get("trangThaiThe")), null));
        dto.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("NghiNgo", dataMap.get("nghiNgo")), null));
        dto.setLyDoCapNhat(DataMapperUtils.safeString(dataMap.getOrDefault("LyDoCapNhat", dataMap.get("lyDoCapNhat"))));
        dto.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("GhiChu", dataMap.get("ghiChu"))));
        return dto;
    }
    public static API_1_34_UPDATE_TNH_DTO mapTo_API_1_34_UPDATE_TNH(Map<String, Object> dataMap) {
        API_1_34_UPDATE_TNH_DTO dto = new API_1_34_UPDATE_TNH_DTO();
        dto.setKey(DataMapperUtils.safeString(dataMap.getOrDefault("Key", dataMap.get("key"))));
        dto.setCif(DataMapperUtils.safeString(dataMap.getOrDefault("Cif", dataMap.get("cif"))));
        dto.setSoId(DataMapperUtils.safeString(dataMap.getOrDefault("SoId", dataMap.get("soId"))));
        dto.setLoaiId(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiId", dataMap.get("loaiId")), null));
        dto.setTenChuTheHoacNguoiUyQuyen(DataMapperUtils.safeString(dataMap.getOrDefault("TenChuTheHoacNguoiUyQuyen", dataMap.get("tenChuTheHoacNguoiUyQuyen"))));
        dto.setNgaySinh(DataMapperUtils.safeString(dataMap.getOrDefault("NgaySinh", dataMap.get("ngaySinh")), null, true));
        dto.setGioiTinh(DataMapperUtils.safeInteger(dataMap.getOrDefault("GioiTinh", dataMap.get("gioiTinh")), null));
        dto.setQuocTich(DataMapperUtils.safeString(dataMap.getOrDefault("QuocTich", dataMap.get("quocTich"))));
        dto.setDienThoai(DataMapperUtils.safeString(dataMap.getOrDefault("DienThoai", dataMap.get("dienThoai"))));
        dto.setDiaChi(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChi", dataMap.get("diaChi"))));
        dto.setDiaChiMac(DataMapperUtils.safeString(dataMap.getOrDefault("DiaChiMac", dataMap.get("diaChiMac"))));
        dto.setSoImei(DataMapperUtils.safeString(dataMap.getOrDefault("SoImei", dataMap.get("soImei"))));
        dto.setSoThe(DataMapperUtils.safeString(dataMap.getOrDefault("SoThe", dataMap.get("soThe"))));
        dto.setLoaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("LoaiThe", dataMap.get("loaiThe")), null));
        dto.setNgayPhatHanh(DataMapperUtils.safeString(dataMap.getOrDefault("NgayPhatHanh", dataMap.get("ngayPhatHanh"))));
        dto.setThoiHanHieuLuc(DataMapperUtils.safeString(dataMap.getOrDefault("ThoiHanHieuLuc", dataMap.get("thoiHanHieuLuc"))));
        dto.setBin(DataMapperUtils.safeString(dataMap.getOrDefault("BIN", dataMap.getOrDefault("Bin", dataMap.get("bin")))));
        dto.setTrangThaiThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("TrangThaiThe", dataMap.get("trangThaiThe")), null));
        dto.setPhuongThucMoThe(DataMapperUtils.safeInteger(dataMap.getOrDefault("PhuongThucMoThe", dataMap.get("phuongThucMoThe")), null));
        dto.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("GhiChu", dataMap.get("ghiChu"))));
        return dto;
    }
    
}