package SHINHAN_PORTAL.REPORT_SIMO.common;

import java.util.Map;

import org.springframework.stereotype.Component;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_27_TT_DVCNTT_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_28_TT_DVCNTT_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_29_UPDATE_DVCNTT_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_30_UPDATE_DVCNTT_DTO;


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
        dto.setKey(safeString(dataMap.get("key")));
        dto.setCif(safeString(dataMap.get("cif")));
        dto.setMaSoDoanhNghiep(safeString(dataMap.get("maSoDoanhNghiep")));
        dto.setSoId(safeString(dataMap.get("soId")));
        dto.setLoaiId(safeInteger(dataMap.get("loaiId"), null)); // Updated
        dto.setHoTenNguoiDaiDien(safeString(dataMap.get("hoTenNguoiDaiDien")));
        dto.setNgaySinh(safeString(dataMap.get("ngaySinh")));
        dto.setQuocTich(safeString(dataMap.get("quocTich")));
        dto.setTenDvcntt(safeString(dataMap.get("tenDvcntt")));
        dto.setLoaiHinhKinhDoanh(safeString(dataMap.get("loaiHinhKinhDoanh")));
        dto.setMaSoThue(safeString(dataMap.get("maSoThue")));
        dto.setDienThoai(safeString(dataMap.get("dienThoai")));
        dto.setDiaChi(safeString(dataMap.get("diaChi")));
        dto.setDiaChiMac(safeString(dataMap.get("diaChiMac")));
        dto.setSoImei(safeString(dataMap.get("soImei")));
        dto.setSoTaiKhoan(safeString(dataMap.get("soTaiKhoan")));
        dto.setTenChuTaiKhoan(safeString(dataMap.get("tenChuTaiKhoan")));
        dto.setNganHangMoTk(safeString(dataMap.get("nganHangMoTk")));
        dto.setLoaiTaiKhoan(safeInteger(dataMap.get("loaiTaiKhoan"), null)); // Updated
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.get("trangThaiTaiKhoan"), null)); // Updated
        dto.setNgayMoTaiKhoan(safeString(dataMap.get("ngayMoTaiKhoan"), null, true));
        return dto;
    }

    // --- Mappers for API_1_28_TT_DVCNTT_NGGL_DTO ---
    public static API_1_28_TT_DVCNTT_NGGL_DTO mapTo_API_1_28_TT_DVCNTT_NGGL(Map<String, Object> dataMap) {
        API_1_28_TT_DVCNTT_NGGL_DTO dto = new API_1_28_TT_DVCNTT_NGGL_DTO();
        dto.setKey(safeString(dataMap.get("key")));
        dto.setCif(safeString(dataMap.get("cif")));
        dto.setTenDvcntt(safeString(dataMap.get("tenDvcntt")));
        dto.setMaSoDoanhNghiep(safeString(dataMap.get("maSoDoanhNghiep")));
        dto.setSoTaiKhoan(safeString(dataMap.get("soTaiKhoan")));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.get("trangThaiTaiKhoan"), null)); // Updated
        dto.setNghiNgo(safeInteger(dataMap.get("nghiNgo"), null)); // Updated
        dto.setGhiChu(safeString(dataMap.get("ghiChu")));
        return dto;
    }

    // --- Mappers for API_1_29_UPDATE_DVCNTT_NGGL_DTO ---
    public static API_1_29_UPDATE_DVCNTT_NGGL_DTO mapTo_API_1_29_UPDATE_DVCNTT_NGGL(Map<String, Object> dataMap) {
        API_1_29_UPDATE_DVCNTT_NGGL_DTO dto = new API_1_29_UPDATE_DVCNTT_NGGL_DTO();
        dto.setKey(safeString(dataMap.get("key")));
        dto.setCif(safeString(dataMap.get("cif")));
        dto.setTenDvcntt(safeString(dataMap.get("tenDvcntt")));
        dto.setMaSoDoanhNghiep(safeString(dataMap.get("maSoDoanhNghiep")));
        dto.setSoTaiKhoan(safeString(dataMap.get("soTaiKhoan")));
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.get("trangThaiTaiKhoan"), null)); // Updated
        dto.setNghiNgo(safeInteger(dataMap.get("nghiNgo"), null)); // Updated
        dto.setLyDoCapNhat(safeString(dataMap.get("lyDoCapNhat")));
        dto.setGhiChu(safeString(dataMap.get("ghiChu")));
        return dto;
    }

    // --- Mappers for API_1_30_UPDATE_DVCNTT_DTO ---
    public static API_1_30_UPDATE_DVCNTT_DTO mapTo_API_1_30_UPDATE_DVCNTT(Map<String, Object> dataMap) {
        API_1_30_UPDATE_DVCNTT_DTO dto = new API_1_30_UPDATE_DVCNTT_DTO();
        dto.setKey(safeString(dataMap.get("key")));
        dto.setCif(safeString(dataMap.get("cif")));
        dto.setMaSoDoanhNghiep(safeString(dataMap.get("maSoDoanhNghiep")));
        dto.setSoId(safeString(dataMap.get("soId")));
        dto.setLoaiId(safeInteger(dataMap.get("loaiId"), null)); // Updated
        dto.setHoTenNguoiDaiDien(safeString(dataMap.get("hoTenNguoiDaiDien")));
        dto.setNgaySinh(safeString(dataMap.get("ngaySinh")));
        dto.setQuocTich(safeString(dataMap.get("quocTich")));
        dto.setTenDvcntt(safeString(dataMap.get("tenDvcntt")));
        dto.setLoaiHinhKinhDoanh(safeString(dataMap.get("loaiHinhKinhDoanh")));
        dto.setMaSoThue(safeString(dataMap.get("maSoThue")));
        dto.setDienThoai(safeString(dataMap.get("dienThoai")));
        dto.setDiaChi(safeString(dataMap.get("diaChi")));
        dto.setDiaChiMac(safeString(dataMap.get("diaChiMac")));
        dto.setSoImei(safeString(dataMap.get("soImei")));
        dto.setSoTaiKhoan(safeString(dataMap.get("soTaiKhoan")));
        dto.setTenChuTaiKhoan(safeString(dataMap.get("tenChuTaiKhoan")));
        dto.setNganHangMoTk(safeString(dataMap.get("nganHangMoTk")));
        dto.setLoaiTaiKhoan(safeInteger(dataMap.get("loaiTaiKhoan"), null)); // Updated
        dto.setTrangThaiTaiKhoan(safeInteger(dataMap.get("trangThaiTaiKhoan"), null)); // Updated
        dto.setNgayMoTaiKhoan(safeString(dataMap.get("ngayMoTaiKhoan"), null, true));
        dto.setGhiChu(safeString(dataMap.get("ghiChu")));
        return dto;
    }
}