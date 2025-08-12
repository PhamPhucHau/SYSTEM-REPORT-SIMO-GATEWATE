package backend.REPORT_SIMO_SYSTEM.REPORT_SIMO.application.mapper;

import backend.REPORT_SIMO_SYSTEM.REPORT_SIMO.application.utils.DataMapperUtils;
import backend.REPORT_SIMO_SYSTEM.REPORT_SIMO.domain.model.API_1_23_TOCHUC;
import backend.REPORT_SIMO_SYSTEM.REPORT_SIMO.domain.model.API_1_24_TOCHUC_NGGL;
import backend.REPORT_SIMO_SYSTEM.REPORT_SIMO.domain.model.API_1_25_UPDATE_TOCHUC_NGGL;
import backend.REPORT_SIMO_SYSTEM.REPORT_SIMO.domain.model.API_1_26_UPDATE_TOCHUC;

import java.util.Map;

public class FileUploadMapper {

    public API_1_23_TOCHUC mapToAPI_1_23_TOCHUC(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_23_TOCHUC entity = new API_1_23_TOCHUC();
        try {
            entity.setId(DataMapperUtils.safeString(dataMap.get("Key")));
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setTenToChuc(DataMapperUtils.safeString(dataMap.get("TenToChuc")));
            entity.setSoGiayPhepThanhLap(DataMapperUtils.safeString(dataMap.get("SoGiayPhepThanhLap")));
            entity.setLoaiGiayToThanhLapToChuc(DataMapperUtils.safeInteger(dataMap.get("LoaiGiayToThanhLapToChuc"), null));
            entity.setNgayThanhLap(DataMapperUtils.safeString(dataMap.get("NgayThanhLap")));
            entity.setDiaChiToChuc(DataMapperUtils.safeString(dataMap.get("DiaChiToChuc")));
            entity.setHoTenNguoiDaiDien(DataMapperUtils.safeString(dataMap.get("HoTenNguoiDaiDien")));
            entity.setSoGiayToTuyThan(DataMapperUtils.safeString(dataMap.get("SoGiayToTuyThan")));
            entity.setLoaiGiayToTuyThan(DataMapperUtils.safeInteger(dataMap.get("LoaiGiayToTuyThan"), null));
            entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("NgaySinh")));
            entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.get("GioiTinh"), null));
            entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("QuocTich")));
            entity.setDienThoai(DataMapperUtils.safeString(dataMap.get("DienThoai")));
            entity.setSoTaiKhoanToChuc(DataMapperUtils.safeString(dataMap.get("SoTaiKhoanToChuc")));
            entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("NgayMoTaiKhoan")));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setDiaChiMAC(DataMapperUtils.safeString(dataMap.get("DiaChiMAC")));
            entity.setSoImei(DataMapperUtils.safeString(dataMap.get("SO_IMEI")));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_23_TOCHUC: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }

    public API_1_24_TOCHUC_NGGL mapToAPI_1_24_TOCHUC_NGGL(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_24_TOCHUC_NGGL entity = new API_1_24_TOCHUC_NGGL();
        try {
            entity.setId(DataMapperUtils.safeString(dataMap.get("Key")));
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setTenToChuc(DataMapperUtils.safeString(dataMap.get("TenToChuc")));
            entity.setSoGiayPhepThanhLap(DataMapperUtils.safeString(dataMap.get("SoGiayPhepThanhLap")));
            entity.setSoTaiKhoanToChuc(DataMapperUtils.safeString(dataMap.get("SoTaiKhoanToChuc")));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.get("NghiNgo"), null));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_24_TOCHUC_NGGL: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }

    public API_1_25_UPDATE_TOCHUC_NGGL mapToAPI_1_25_UPDATE_TOCHUC_NGGL(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_25_UPDATE_TOCHUC_NGGL entity = new API_1_25_UPDATE_TOCHUC_NGGL();
        try {
            entity.setId(DataMapperUtils.safeString(dataMap.get("Key")));
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setTenToChuc(DataMapperUtils.safeString(dataMap.get("TenToChuc")));
            entity.setSoGiayPhepThanhLap(DataMapperUtils.safeString(dataMap.get("SoGiayPhepThanhLap")));
            entity.setSoTaiKhoanToChuc(DataMapperUtils.safeString(dataMap.get("SoTaiKhoanToChuc")));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.get("NghiNgo"), null));
            entity.setLyDoCapNhat(DataMapperUtils.safeString(dataMap.get("LyDoCapNhat")));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_25_UPDATE_TOCHUC_NGGL: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }

    public API_1_26_UPDATE_TOCHUC mapToAPI_1_26_UPDATE_TOCHUC(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_26_UPDATE_TOCHUC entity = new API_1_26_UPDATE_TOCHUC();
        try {
            entity.setId(DataMapperUtils.safeString(dataMap.get("Key")));
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setTenToChuc(DataMapperUtils.safeString(dataMap.get("TenToChuc")));
            entity.setSoGiayPhepThanhLap(DataMapperUtils.safeString(dataMap.get("SoGiayPhepThanhLap")));
            entity.setLoaiGiayToThanhLapToChuc(DataMapperUtils.safeInteger(dataMap.get("LoaiGiayToThanhLapToChuc"), null));
            entity.setNgayThanhLap(DataMapperUtils.safeString(dataMap.get("NgayThanhLap")));
            entity.setDiaChiToChuc(DataMapperUtils.safeString(dataMap.get("DiaChiToChuc")));
            entity.setHoTenNguoiDaiDien(DataMapperUtils.safeString(dataMap.get("HoTenNguoiDaiDien")));
            entity.setSoGiayToTuyThan(DataMapperUtils.safeString(dataMap.get("SoGiayToTuyThan")));
            entity.setLoaiGiayToTuyThan(DataMapperUtils.safeInteger(dataMap.get("LoaiGiayToTuyThan"), null));
            entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("NgaySinh")));
            entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.get("GioiTinh"), null));
            entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("QuocTich")));
            entity.setDienThoai(DataMapperUtils.safeString(dataMap.get("DienThoai")));
            entity.setSoTaiKhoanToChuc(DataMapperUtils.safeString(dataMap.get("SoTaiKhoanToChuc")));
            entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("NgayMoTaiKhoan")));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setDiaChiMAC(DataMapperUtils.safeString(dataMap.get("DiaChiMAC")));
            entity.setSoImei(DataMapperUtils.safeString(dataMap.get("SO_IMEI")));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_26_UPDATE_TOCHUC: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }

}
