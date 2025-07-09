package SHINHAN_PORTAL.REPORT_SIMO.application.mapper;

import SHINHAN_PORTAL.REPORT_SIMO.common.DataMapperUtils;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_6_tktt_dinh_ky;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_7_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_8_update_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_9_update_tktt_dinh_ky;

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
}
