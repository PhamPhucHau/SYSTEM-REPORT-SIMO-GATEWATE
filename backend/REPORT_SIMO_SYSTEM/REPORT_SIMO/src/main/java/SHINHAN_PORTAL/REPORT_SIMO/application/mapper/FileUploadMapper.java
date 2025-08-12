package SHINHAN_PORTAL.REPORT_SIMO.application.mapper;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_27_TT_DVCNTT_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_28_TT_DVCNTT_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_29_UPDATE_DVCNTT_NGGL_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.dto.API_1_30_UPDATE_DVCNTT_DTO;
import SHINHAN_PORTAL.REPORT_SIMO.common.DataMapperUtils;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_23_TOCHUC;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_24_TOCHUC_NGGL;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_25_UPDATE_TOCHUC_NGGL;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_26_UPDATE_TOCHUC;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_27_TT_DVCNTT;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_6_tktt_dinh_ky;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_7_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_8_update_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_9_update_tktt_dinh_ky;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_28_TT_DVCNTT_NGGL;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_29_UPDATE_DVCNTT_NGGL;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_30_UPDATE_DVCNTT;

import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class FileUploadMapper {
   public API_1_6_tktt_dinh_ky mapToAPI_1_6_tktt_dinh_ky(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_6_tktt_dinh_ky entity = new API_1_6_tktt_dinh_ky();
        try {
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setSoID(DataMapperUtils.safeString(dataMap.get("SoID")));
            entity.setLoaiID(DataMapperUtils.safeInteger(dataMap.get("LoaiID"), null));
            entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.get("TenKhachHang")));
            entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("NgaySinh")));
            entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.get("GioiTinh"), null));
            entity.setMaSoThue(DataMapperUtils.safeString(dataMap.get("MaSoThue")));
            entity.setSoDienThoaiDangKyDichVu(DataMapperUtils.safeString(dataMap.get("SoDienThoaiDangKyDichVu")));
            entity.setDiaChi(DataMapperUtils.safeString(dataMap.get("DiaChi")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("SoTaiKhoan")));
            entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("LoaiTaiKhoan"), null));
            entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiHoatDongTaiKhoan"), null));
            entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("NgayMoTaiKhoan"), null, true));
            entity.setPhuongThucMoTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("PhuongThucMoTaiKhoan"), null));
            entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("QuocTich")));
            entity.setDiaChiKiemSoatTruyCap(DataMapperUtils.safeString(dataMap.get("DiaChiKiemSoatTruyCap")));
            entity.setMaSoNhanDangThietBiDiDong(DataMapperUtils.safeString(dataMap.get("MaSoNhanDangThietBiDiDong")));
            entity.setNgayXacThucTaiQuay(DataMapperUtils.safeString(dataMap.get("NgayXacThucTaiQuay"), null, true));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_6_tktt_dinh_ky: CIF: [" + DataMapperUtils.safeString(dataMap.get("Cif")) + "]", e);
        }
        return entity;
    }

    public API_1_7_tktt_nnngl mapToAPI_1_7_tktt_nnngl(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_7_tktt_nnngl entity = new API_1_7_tktt_nnngl();
        try {
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("Số tài khoản", dataMap.get("SoTaiKhoan"))));
            entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("Tên khách hàng", dataMap.get("TenKhachHang"))));
            entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("Trạng thái hoạt động của tài khoản", dataMap.get("TrangThaiHoatDongTaiKhoan")), null));
            entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("Nghi ngờ", dataMap.get("NghiNgo")), null));
            entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"))));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_7_tktt_nnngl: CIF: [" + DataMapperUtils.safeString(dataMap.get("Cif")) + "]", e);
        }
        return entity;
    }
    public API_1_8_update_tktt_nnngl mapToAPI_1_8_update_tktt_nnngl(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_8_update_tktt_nnngl entity = new API_1_8_update_tktt_nnngl();
        try {
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("Số tài khoản", dataMap.get("SoTaiKhoan"))));
            entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("Tên khách hàng", dataMap.get("TenKhachHang"))));
            entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("Trạng thái hoạt động của tài khoản", dataMap.get("TrangThaiHoatDongTaiKhoan")), null));
            entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("Nghi ngờ", dataMap.get("NghiNgo")), null));
            entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"))));
            entity.setLyDoCapNhat(DataMapperUtils.safeString(dataMap.getOrDefault("Lý do cập nhật", dataMap.get("LyDoCapNhat"))));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_8_update_tktt_nnngl: CIF: [" + DataMapperUtils.safeString(dataMap.get("Cif")) + "]", e);
        }
        return entity;
    }

    public API_1_9_update_tktt_dinh_ky mapToAPI_1_9_update_tktt_dinh_ky(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_9_update_tktt_dinh_ky entity = new API_1_9_update_tktt_dinh_ky();
        try {
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setSoID(DataMapperUtils.safeString(dataMap.get("SoID")));
            entity.setLoaiID(DataMapperUtils.safeInteger(dataMap.get("LoaiID"), null));
            entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.get("TenKhachHang")));
            entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("NgaySinh")));
            entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.get("GioiTinh"), null));
            entity.setMaSoThue(DataMapperUtils.safeString(dataMap.get("MaSoThue")));
            entity.setSoDienThoaiDangKyDichVu(DataMapperUtils.safeString(dataMap.get("SoDienThoaiDangKyDichVu")));
            entity.setDiaChi(DataMapperUtils.safeString(dataMap.get("DiaChi")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("SoTaiKhoan")));
            entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("LoaiTaiKhoan"), null));
            entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiHoatDongTaiKhoan"), null));
            entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("NgayMoTaiKhoan"), null, true));
            entity.setPhuongThucMoTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("PhuongThucMoTaiKhoan"), null));
            entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("QuocTich")));
            entity.setDiaChiKiemSoatTruyCap(DataMapperUtils.safeString(dataMap.get("DiaChiKiemSoatTruyCap")));
            entity.setMaSoNhanDangThietBiDiDong(DataMapperUtils.safeString(dataMap.get("MaSoNhanDangThietBiDiDong")));
            entity.setNgayXacThucTaiQuay(DataMapperUtils.safeString(dataMap.get("NgayXacThucTaiQuay"), null, true));
            entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"))));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_9_update_tktt_dinh_ky: CIF: [" + DataMapperUtils.safeString(dataMap.get("Cif")) + "]", e);
        }
        return entity;
    }
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

    public API_1_27_TT_DVCNTT mapToAPI_1_27_TT_DVCNTT(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_27_TT_DVCNTT entity = new API_1_27_TT_DVCNTT();
        try {
            
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setMaSoDoanhNghiep(DataMapperUtils.safeString(dataMap.get("MaSoDoanhNghiep")));
            entity.setSoId(DataMapperUtils.safeString(dataMap.get("SoId")));
            entity.setLoaiId(DataMapperUtils.safeInteger(dataMap.get("LoaiId"), null));
            entity.setHoTenNguoiDaiDien(DataMapperUtils.safeString(dataMap.get("HoTenNguoiDaiDien")));
            entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("NgaySinh")));
            entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("QuocTich")));
            entity.setTenDvcntt(DataMapperUtils.safeString(dataMap.get("TenDvcntt")));
            entity.setLoaiHinhKinhDoanh(DataMapperUtils.safeString(dataMap.get("loaiHinhKinhDoanh")));
            entity.setMaSoThue(DataMapperUtils.safeString(dataMap.get("MaSoThue")));
            entity.setDienThoai(DataMapperUtils.safeString(dataMap.get("DienThoai")));
            entity.setDiaChi(DataMapperUtils.safeString(dataMap.get("DiaChi")));
            entity.setDiaChiMac(DataMapperUtils.safeString(dataMap.get("DiaChiMac")));
            entity.setSoImei(DataMapperUtils.safeString(dataMap.get("SoImei")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("SoTaiKhoan")));
            entity.setTenChuTaiKhoan(DataMapperUtils.safeString(dataMap.get("TenChuTaiKhoan")));
            entity.setNganHangMoTk(DataMapperUtils.safeString(dataMap.get("NganHangMoTk")));
            entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("LoaiTaiKhoan"), null));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("NgayMoTaiKhoan")));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_27_TT_DVCNTT: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }

    public API_1_28_TT_DVCNTT_NGGL mapToAPI_1_28_TT_DVCNTT_NGGL(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_28_TT_DVCNTT_NGGL entity = new API_1_28_TT_DVCNTT_NGGL();
        try {
            
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setTenDvcntt(DataMapperUtils.safeString(dataMap.get("TenDvcntt")));
            entity.setMaSoDoanhNghiep(DataMapperUtils.safeString(dataMap.get("MaSoDoanhNghiep")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("SoTaiKhoan")));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.get("NghiNgo"), null));
            entity.setGhiChu(DataMapperUtils.safeString(dataMap.get("GhiChu")));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_28_TT_DVCNTT_NGGL: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }

    public API_1_29_UPDATE_DVCNTT_NGGL mapToAPI_1_29_UPDATE_DVCNTT_NGGL(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_29_UPDATE_DVCNTT_NGGL entity = new API_1_29_UPDATE_DVCNTT_NGGL();
        try {
            
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setTenDvcntt(DataMapperUtils.safeString(dataMap.get("TenDvcntt")));
            entity.setMaSoDoanhNghiep(DataMapperUtils.safeString(dataMap.get("MaSoDoanhNghiep")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("SoTaiKhoan")));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.get("NghiNgo"), null));
            entity.setLyDoCapNhat(DataMapperUtils.safeString(dataMap.get("LyDoCapNhat")));
            entity.setGhiChu(DataMapperUtils.safeString(dataMap.get("GhiChu")));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_29_UPDATE_DVCNTT_NGGL: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }

    public API_1_30_UPDATE_DVCNTT mapToAPI_1_30_UPDATE_DVCNTT(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
        API_1_30_UPDATE_DVCNTT entity = new API_1_30_UPDATE_DVCNTT();
        try {
            
            entity.setCif(DataMapperUtils.safeString(dataMap.get("Cif")));
            entity.setMaSoDoanhNghiep(DataMapperUtils.safeString(dataMap.get("MaSoDoanhNghiep")));
            entity.setSoId(DataMapperUtils.safeString(dataMap.get("SoId")));
            entity.setLoaiId(DataMapperUtils.safeInteger(dataMap.get("LoaiId"), null));
            entity.setHoTenNguoiDaiDien(DataMapperUtils.safeString(dataMap.get("HoTenNguoiDaiDien")));
            entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("NgaySinh")));
            entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("QuocTich")));
            entity.setTenDvcntt(DataMapperUtils.safeString(dataMap.get("TenDvcntt")));
            entity.setLoaiHinhKinhDoanh(DataMapperUtils.safeString(dataMap.get("loaiHinhKinhDoanh")));
            entity.setMaSoThue(DataMapperUtils.safeString(dataMap.get("MaSoThue")));
            entity.setDienThoai(DataMapperUtils.safeString(dataMap.get("DienThoai")));
            entity.setDiaChi(DataMapperUtils.safeString(dataMap.get("DiaChi")));
            entity.setDiaChiMac(DataMapperUtils.safeString(dataMap.get("DiaChiMac")));
            entity.setSoImei(DataMapperUtils.safeString(dataMap.get("SoImei")));
            entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("SoTaiKhoan")));
            entity.setTenChuTaiKhoan(DataMapperUtils.safeString(dataMap.get("TenChuTaiKhoan")));
            entity.setNganHangMoTk(DataMapperUtils.safeString(dataMap.get("NganHangMoTk")));
            entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("LoaiTaiKhoan"), null));
            entity.setTrangThaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("TrangThaiTaiKhoan"), null));
            entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("NgayMoTaiKhoan")));
            entity.setGhiChu(DataMapperUtils.safeString(dataMap.get("GhiChu")));
            entity.setTemplateID(templateID);
            entity.setMonthYear(monthYear);
            entity.setUsername(username);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error mapping to API_1_30_UPDATE_DVCNTT: Key: [" + DataMapperUtils.safeString(dataMap.get("Key")) + "]", e);
        }
        return entity;
    }
}
