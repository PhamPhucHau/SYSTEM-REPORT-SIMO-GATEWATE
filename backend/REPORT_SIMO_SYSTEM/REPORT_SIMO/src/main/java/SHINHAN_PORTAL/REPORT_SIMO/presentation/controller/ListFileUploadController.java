package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.*;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/upload_history")
public class ListFileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(ListFileUploadController.class);
    @Autowired
    private HistoryService historyService;
    @Autowired
    private API_1_6_tktt_dinh_ky_service api_1_6_tktt_dinh_ky_service;
    @Autowired
    private API_1_7_tktt_nnngl_service api_1_7_tktt_nnngl_service;
    @Autowired
    private API_1_9_update_tktt_dinh_ky_service api_1_9_update_tktt_dinh_ky_service;
    @Autowired
    private API_1_8_update_tktt_nnngl_service api_1_8_update_tktt_nnngl_service;
    @PostMapping()
    public ResponseEntity<LIST_FILE_UPLOAD> uploadFileHistory(
            @RequestBody Map<String, Object> requestBody// Lấy username từ JWT token
    ) {
        if (requestBody == null) {
            logger.error("Request body is null!");
            return ResponseEntity.badRequest().build();
        }
        logger.info("@ Request Upload Come!");
        LIST_FILE_UPLOAD fileUpload = new LIST_FILE_UPLOAD();

        fileUpload.setTemplateID((String) requestBody.get("templateID"));
        fileUpload.setTemplateName((String) requestBody.get("templateName"));
        fileUpload.setMonthYear((String) requestBody.get("monthYear"));
        fileUpload.setTotal_record(((Number) requestBody.get("total_record")).longValue());
        fileUpload.setUserId(String.valueOf(requestBody.get("userId")));
        fileUpload.setUsername((String) requestBody.get("username"));
        fileUpload.setFileName((String) requestBody.get("fileName"));
        fileUpload.setFileType((String) requestBody.get("fileType"));

        // Lưu danh sách dữ liệu từ "data"
        List<Map<String, Object>> rawData = (List<Map<String, Object>>) requestBody.get("data");
        fileUpload.setData(rawData);
        LIST_FILE_UPLOAD history = historyService.saveUploadHistory(fileUpload);

        String templateID = ((String) requestBody.get("templateID")).toUpperCase();

        switch (templateID) {
                case "API_1_6_TTDS_TKTT_DK" -> {
                    List<API_1_6_tktt_dinh_ky> formattedData = rawData.stream()
                            .map(data -> mapToAPI_1_6_tktt_dinh_ky(data, fileUpload.getTemplateID(), fileUpload.getMonthYear()))
                            .collect(Collectors.toList());
                    api_1_6_tktt_dinh_ky_service.insert(formattedData);
                }
            case "API_1_7_TTDS_TKTT_NNGL" -> {
                List<API_1_7_tktt_nnngl> formattedData = rawData.stream()
                        .map(data -> mapToAPI_1_7_tktt_nnngl(data, fileUpload.getTemplateID(), fileUpload.getMonthYear()))
                        .collect(Collectors.toList());
                api_1_7_tktt_nnngl_service.insert(formattedData);
            }
            case "API_1_9_UPDATE_TTDS_TKTT_DK" -> {
                List<API_1_9_update_tktt_dinh_ky> formattedData = rawData.stream()
                        .map(data -> mapToAPI_1_9_update_tktt_dinh_ky(data, fileUpload.getTemplateID(), fileUpload.getMonthYear()))
                        .collect(Collectors.toList());
                api_1_9_update_tktt_dinh_ky_service.insert(formattedData);
            }
            case "API_1_8_UPDATE_TTDS_TKTT_NNGL" -> {
                List<API_1_8_update_tktt_nnngl> formattedData = rawData.stream()
                        .map(data -> mapToAPI_1_8_update_tktt_nnngl(data, fileUpload.getTemplateID(), fileUpload.getMonthYear()))
                        .collect(Collectors.toList());
                api_1_8_update_tktt_nnngl_service.insert(formattedData);
            }

            // You can add more cases here as needed
            default -> {
                // Optional: handle unsupported templateID
                throw new IllegalArgumentException("Unsupported templateID: " + templateID);
            }
        }

        return ResponseEntity.ok(history);
    }
    private API_1_6_tktt_dinh_ky mapToAPI_1_6_tktt_dinh_ky(Map<String, Object> dataMap,String templateID, String monthYear) {
        API_1_6_tktt_dinh_ky entity = new API_1_6_tktt_dinh_ky();
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
        entity.setTemplateID(templateID);
        entity.setMonthYear(monthYear);


        return entity;
    }
    private API_1_7_tktt_nnngl mapToAPI_1_7_tktt_nnngl(Map<String, Object> dataMap, String templateID, String monthYear) {
        API_1_7_tktt_nnngl entity = new API_1_7_tktt_nnngl();

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

        // Set thêm các trường TemplateID và MonthYear
        entity.setTemplateID(templateID);
        entity.setMonthYear(monthYear);

        return entity;
    }
    private API_1_9_update_tktt_dinh_ky mapToAPI_1_9_update_tktt_dinh_ky(Map<String, Object> dataMap,String templateID, String monthYear) {
        API_1_9_update_tktt_dinh_ky entity = new API_1_9_update_tktt_dinh_ky();
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
        entity.setTemplateID(templateID);
        entity.setMonthYear(monthYear);


        return entity;
    }
    private API_1_8_update_tktt_nnngl mapToAPI_1_8_update_tktt_nnngl(Map<String, Object> dataMap, String templateID, String monthYear) {
        API_1_8_update_tktt_nnngl entity = new API_1_8_update_tktt_nnngl();

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
        entity.setTemplateID(templateID);
        entity.setMonthYear(monthYear);

        return entity;
    }
    @GetMapping("/list")
    public Page<LIST_FILE_UPLOAD> getFiles(
            @RequestParam(required = false) String templateID,
            @RequestParam(required = false) String monthYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return historyService.getFiles(templateID, monthYear, page, size);
    }
}