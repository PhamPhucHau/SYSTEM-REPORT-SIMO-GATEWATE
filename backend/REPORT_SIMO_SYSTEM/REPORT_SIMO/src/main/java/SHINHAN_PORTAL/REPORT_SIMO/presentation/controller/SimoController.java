package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.*;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.SimoService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_6_tktt_dinh_ky;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_7_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_8_update_tktt_nnngl;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_9_update_tktt_dinh_ky;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simo")
public class SimoController {

    @Autowired
    private SimoService simoService;

    @PostMapping("/token")
    public TokenResponseDTO getToken(@RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String consumerKey,
                                     @RequestParam String consumerSecret) {
        return simoService.getToken(username, password, consumerKey, consumerSecret);
    }

    @PostMapping("/tktt/upload")
    public TKTTResponseDTO uploadTKTTReport(@RequestHeader("maYeuCau") String maYeuCau,
                                            @RequestHeader("kyBaoCao") String kyBaoCao,
                                            @RequestParam String templateID,
                                            @RequestBody List<Map<String, Object>> tkttList) {

        String templateIdUpper = templateID.toUpperCase();
        System.out.println("@@@@"+templateIdUpper);
        switch (templateIdUpper) {
            case "API_1_6_TTDS_TKTT_DK" -> {
                List<TKTTRequestDTO>  formattedData = tkttList.stream()
                        .map(item -> mapToAPI_1_6_tktt_dinh_ky((Map<String, Object>) item))
                        .collect(Collectors.toList());
                return simoService.uploadTKTTReportAutoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_7_TTDS_TKTT_NNGL" -> {
                List<API_1_7_tktt_nggl_DT0>  formattedData = tkttList.stream()
                        .map(item -> mapToAPI_1_7_tktt_nnngl((Map<String, Object>) item))
                        .collect(Collectors.toList());
                return simoService.api_1_7_uploadNGGL_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_9_UPDATE_TTDS_TKTT_DK" -> {
                List<API_1_9_update_tktt_nggl_DT0>  formattedData = tkttList.stream()
                        .map(item ->mapToAPI_1_9_update_tktt_dinh_ky((Map<String, Object>) item))
                        .collect(Collectors.toList());
                return simoService.api_1_9_TKTT_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_8_UPDATE_TTDS_TKTT_NNGL" -> {
                List<API_1_8_update_tktt_nggl_DT0>  formattedData = tkttList.stream()
                        .map(item -> mapToAPI_1_8_update_tktt_nnngl((Map<String, Object>) item))
                        .collect(Collectors.toList());
                return simoService.api_1_8_update_uploadNGGL_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            default -> {
                TKTTResponseDTO response = new TKTTResponseDTO();
                response.setCode("400");
                response.setMessage("TemplateID không hợp lệ: " + templateID);
                response.setSuccess(false);
                return response;
            }
        }
    }
    private TKTTRequestDTO mapToAPI_1_6_tktt_dinh_ky(Map<String, Object> dataMap) {
        TKTTRequestDTO entity = new TKTTRequestDTO();
        entity.setCif((String) dataMap.get("cif"));
        entity.setSoID((String) dataMap.get("soID"));
        entity.setLoaiID(((Number) dataMap.get("loaiID")).intValue());
        entity.setTenKhachHang((String) dataMap.get("tenKhachHang"));
        entity.setNgaySinh((String) dataMap.get("ngaySinh"));

        // Xử lý GioiTinh (chuyển từ String -> Integer)
        Object gioiTinhObj = dataMap.get("gioiTinh");
        if (gioiTinhObj instanceof Integer) {
            entity.setGioiTinh((Integer) gioiTinhObj);
        } else {
            entity.setGioiTinh(0);
        }

        // Xử lý MaSoThue (có thể là Long)
        Object maSoThueObj = dataMap.get("maSoThue");
        if (maSoThueObj instanceof Number) {
            entity.setMaSoThue(String.valueOf(((Number) maSoThueObj).longValue()));
        } else {
            entity.setMaSoThue(String.valueOf(maSoThueObj != null ? Long.parseLong(maSoThueObj.toString()) : null));
        }

        entity.setSoDienThoaiDangKyDichVu((String) dataMap.get("soDienThoaiDangKyDichVu"));
        entity.setDiaChi((String) dataMap.get("diaChi"));
        entity.setSoTaiKhoan((String) dataMap.get("soTaiKhoan"));
        entity.setLoaiTaiKhoan(((Number) dataMap.get("loaiTaiKhoan")).intValue());
        entity.setTrangThaiHoatDongTaiKhoan(((Number) dataMap.get("trangThaiHoatDongTaiKhoan")).intValue());

        // Xử lý NgayMoTaiKhoan (nếu rỗng thì set null)
        String ngayMoTaiKhoan = (String) dataMap.get("ngayMoTaiKhoan");
        entity.setNgayMoTaiKhoan((ngayMoTaiKhoan == null || ngayMoTaiKhoan.isEmpty()) ? null : ngayMoTaiKhoan);

        entity.setPhuongThucMoTaiKhoan(((Number) dataMap.get("phuongThucMoTaiKhoan")).intValue());
        entity.setQuocTich((String) dataMap.get("quocTich")); // Nếu cần, có thể chuẩn hóa về "VN"
        return entity;
    }
    private API_1_7_tktt_nggl_DT0 mapToAPI_1_7_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_7_tktt_nggl_DT0 entity = new API_1_7_tktt_nggl_DT0();

        // Lấy dữ liệu với nhiều key có thể có (theo yêu cầu)
        String cif = (String) dataMap.getOrDefault("Số CIF",
                dataMap.getOrDefault("CIF",
                        dataMap.getOrDefault("Cif", "")));
        entity.setCif(cif);

        String soTaiKhoan = (String) dataMap.getOrDefault("Số tài khoản",
                dataMap.getOrDefault("SoTaiKhoan", ""));
        entity.setSoTaiKhoan(soTaiKhoan);

        String tenKhachHang = (String) dataMap.getOrDefault("Tên khách hàng",
                dataMap.getOrDefault("TenKhachHang", ""));
        entity.setTenKhachHang(tenKhachHang);

        // TrangThaiHoatDongTaiKhoan có thể là Number hoặc String, xử lý tương tự
        Object trangThaiObj = dataMap.getOrDefault("Trạng thái hoạt động của tài khoản",
                dataMap.get("TrangThaiHoatDongTaiKhoan"));
        if (trangThaiObj instanceof Number) {
            entity.setTrangThaiHoatDongTaiKhoan(((Number) trangThaiObj).intValue());
        } else if (trangThaiObj instanceof String) {
            try {
                entity.setTrangThaiHoatDongTaiKhoan(Integer.parseInt((String) trangThaiObj));
            } catch (NumberFormatException e) {
                entity.setTrangThaiHoatDongTaiKhoan(null);
            }
        } else {
            entity.setTrangThaiHoatDongTaiKhoan(null);
        }

        // NghiNgo cũng có thể là Number hoặc String
        Object nghiNgoObj = dataMap.getOrDefault("Nghi ngờ", dataMap.get("NghiNgo"));
        if (nghiNgoObj instanceof Number) {
            entity.setNghiNgo(((Number) nghiNgoObj).intValue());
        } else if (nghiNgoObj instanceof String) {
            try {
                entity.setNghiNgo(Integer.parseInt((String) nghiNgoObj));
            } catch (NumberFormatException e) {
                entity.setNghiNgo(null);
            }
        } else {
            entity.setNghiNgo(null);
        }

        // GhiChu là String, có thể null hoặc rỗng
        String ghiChu = (String) dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"));
        entity.setGhiChu(ghiChu);
        return entity;
    }
    private API_1_9_update_tktt_nggl_DT0 mapToAPI_1_9_update_tktt_dinh_ky(Map<String, Object> dataMap) {
        API_1_9_update_tktt_nggl_DT0 entity = new API_1_9_update_tktt_nggl_DT0();
        entity.setCif((String) dataMap.get("Cif"));
        entity.setSoID((String) dataMap.get("SoID"));
        entity.setLoaiID(((Number) dataMap.get("LoaiID")).intValue());
        entity.setTenKhachHang((String) dataMap.get("TenKhachHang"));
        entity.setNgaySinh((String) dataMap.get("NgaySinh"));

        // Xử lý GioiTinh (chuyển từ String -> Integer)
        String gioiTinhStr = (String) dataMap.get("GioiTinh");
        entity.setGioiTinh(gioiTinhStr != null ? Integer.parseInt(gioiTinhStr) : null);

        // Xử lý MaSoThue (có thể là Long)
        Object maSoThueObj = dataMap.get("MaSoThue");
        if (maSoThueObj instanceof Number) {
            entity.setMaSoThue(String.valueOf(((Number) maSoThueObj).longValue()));
        } else {
            entity.setMaSoThue(String.valueOf(maSoThueObj != null ? Long.parseLong(maSoThueObj.toString()) : null));
        }

        entity.setSoDienThoaiDangKyDichVu((String) dataMap.get("SoDienThoaiDangKyDichVu"));
        entity.setDiaChi((String) dataMap.get("DiaChi"));
        entity.setSoTaiKhoan((String) dataMap.get("SoTaiKhoan"));
        entity.setLoaiTaiKhoan(((Number) dataMap.get("LoaiTaiKhoan")).intValue());
        entity.setTrangThaiHoatDongTaiKhoan(((Number) dataMap.get("TrangThaiHoatDongTaiKhoan")).intValue());

        // Xử lý NgayMoTaiKhoan (nếu rỗng thì set null)
        String ngayMoTaiKhoan = (String) dataMap.get("NgayMoTaiKhoan");
        entity.setNgayMoTaiKhoan((ngayMoTaiKhoan == null || ngayMoTaiKhoan.isEmpty()) ? null : ngayMoTaiKhoan);

        entity.setPhuongThucMoTaiKhoan(((Number) dataMap.get("PhuongThucMoTaiKhoan")).intValue());
        entity.setQuocTich((String) dataMap.get("QuocTich")); // Nếu cần, có thể chuẩn hóa về "VN"
        String ghiChu = (String) dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"));
        entity.setGhiChu(ghiChu);


        return entity;
    }
    private API_1_8_update_tktt_nggl_DT0 mapToAPI_1_8_update_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_8_update_tktt_nggl_DT0 entity = new API_1_8_update_tktt_nggl_DT0();

        // Lấy dữ liệu với nhiều key có thể có (theo yêu cầu)
        String cif = (String) dataMap.getOrDefault("Số CIF",
                dataMap.getOrDefault("CIF",
                        dataMap.getOrDefault("Cif", "")));
        entity.setCif(cif);

        String soTaiKhoan = (String) dataMap.getOrDefault("Số tài khoản",
                dataMap.getOrDefault("SoTaiKhoan", ""));
        entity.setSoTaiKhoan(soTaiKhoan);

        String tenKhachHang = (String) dataMap.getOrDefault("Tên khách hàng",
                dataMap.getOrDefault("TenKhachHang", ""));
        entity.setTenKhachHang(tenKhachHang);

        // TrangThaiHoatDongTaiKhoan có thể là Number hoặc String, xử lý tương tự
        Object trangThaiObj = dataMap.getOrDefault("Trạng thái hoạt động của tài khoản",
                dataMap.get("TrangThaiHoatDongTaiKhoan"));
        if (trangThaiObj instanceof Number) {
            entity.setTrangThaiHoatDongTaiKhoan(((Number) trangThaiObj).intValue());
        } else if (trangThaiObj instanceof String) {
            try {
                entity.setTrangThaiHoatDongTaiKhoan(Integer.parseInt((String) trangThaiObj));
            } catch (NumberFormatException e) {
                entity.setTrangThaiHoatDongTaiKhoan(null);
            }
        } else {
            entity.setTrangThaiHoatDongTaiKhoan(null);
        }

        // NghiNgo cũng có thể là Number hoặc String
        Object nghiNgoObj = dataMap.getOrDefault("Nghi ngờ", dataMap.get("NghiNgo"));
        if (nghiNgoObj instanceof Number) {
            entity.setNghiNgo(((Number) nghiNgoObj).intValue());
        } else if (nghiNgoObj instanceof String) {
            try {
                entity.setNghiNgo(Integer.parseInt((String) nghiNgoObj));
            } catch (NumberFormatException e) {
                entity.setNghiNgo(null);
            }
        } else {
            entity.setNghiNgo(null);
        }

        // GhiChu là String, có thể null hoặc rỗng
        String ghiChu = (String) dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"));
        entity.setGhiChu(ghiChu);
        String lyDoCapNhat = (String) dataMap.getOrDefault("Lý do cập nhật", dataMap.get("LyDoCapNhat"));
        entity.setLyDoCapNhat(lyDoCapNhat);
        // Set thêm các trường TemplateID và MonthYear

        return entity;
    }
}