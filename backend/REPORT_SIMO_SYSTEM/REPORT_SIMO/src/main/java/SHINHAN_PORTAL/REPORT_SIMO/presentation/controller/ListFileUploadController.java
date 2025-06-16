package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.FileUploadRequestDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.*;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.*;
import jakarta.validation.Valid;

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
    // @Autowired
    // private HistoryService historyService;
    // @Autowired
    // private API_1_6_tktt_dinh_ky_service api_1_6_tktt_dinh_ky_service;
    // @Autowired
    // private API_1_7_tktt_nnngl_service api_1_7_tktt_nnngl_service;
    // @Autowired
    // private API_1_9_update_tktt_dinh_ky_service api_1_9_update_tktt_dinh_ky_service;
    // @Autowired
    // private API_1_8_update_tktt_nnngl_service api_1_8_update_tktt_nnngl_service;
    @Autowired
    private FileUploadProcessor fileUploadProcessor;
    @PostMapping
    public ResponseEntity<LIST_FILE_UPLOAD> uploadFileHistory(@Valid @RequestBody FileUploadRequestDTO request) {
        logger.info("Processing upload request for templateID: {}", request.getTemplateID());
        LIST_FILE_UPLOAD history = fileUploadProcessor.processUpload(request);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/confirm")
    public ResponseEntity<LIST_FILE_UPLOAD> uploadDataMas(@RequestParam String id) {
        logger.info("Processing confirm request for id: {}", id);
        LIST_FILE_UPLOAD history = fileUploadProcessor.processConfirm(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/list")
    public Page<LIST_FILE_UPLOAD> getFiles(
            @RequestParam(required = false) String templateID,
            @RequestParam(required = false) String monthYear,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        return fileUploadProcessor.getFiles(templateID, monthYear, username, page, size);
    }
    // @PostMapping()
    // public ResponseEntity<LIST_FILE_UPLOAD> uploadFileHistory(
    //         @RequestBody Map<String, Object> requestBody// Lấy username từ JWT token
    // ) {
    //     if (requestBody == null) {
    //         logger.error("Request body is null!");
    //         return ResponseEntity.badRequest().build();
    //     }
    //     logger.info("@ Request Upload Come!");
    //     LIST_FILE_UPLOAD fileUpload = new LIST_FILE_UPLOAD();

    //     fileUpload.setTemplateID((String) requestBody.get("templateID"));
    //     fileUpload.setTemplateName((String) requestBody.get("templateName"));
    //     fileUpload.setMonthYear((String) requestBody.get("monthYear"));
    //     fileUpload.setTotal_record(((Number) requestBody.get("total_record")).longValue());
    //     fileUpload.setUserId(String.valueOf(requestBody.get("userId")));
    //     fileUpload.setUsername((String) requestBody.get("username"));
    //     fileUpload.setFileName((String) requestBody.get("fileName"));
    //     fileUpload.setFileType((String) requestBody.get("fileType"));
        
    //     // Delete Old File on the key Template ID, Month Year and UserName. 
    //     api_1_6_tktt_dinh_ky_service.deleteByTemplateIDAndMonthYearAndUsername(fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername());
    //     // Lưu danh sách dữ liệu từ "data"
    //     List<Map<String, Object>> rawData = (List<Map<String, Object>>) requestBody.get("data");
    //     fileUpload.setData(rawData);
    //     fileUpload.setData_ledg_s("00");
    //     LIST_FILE_UPLOAD history = historyService.saveUploadHistory(fileUpload);

    //     // String templateID = ((String) requestBody.get("templateID")).toUpperCase();

    //     // switch (templateID) {
    //     //         case "API_1_6_TTDS_TKTT_DK" -> {
    //     //             List<API_1_6_tktt_dinh_ky> formattedData = rawData.stream()
    //     //                     .map(data -> mapToAPI_1_6_tktt_dinh_ky(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //     //                     .collect(Collectors.toList());
    //     //             api_1_6_tktt_dinh_ky_service.insert(formattedData);
    //     //         }
    //     //     case "API_1_7_TTDS_TKTT_NNGL" -> {
    //     //         List<API_1_7_tktt_nnngl> formattedData = rawData.stream()
    //     //                 .map(data -> mapToAPI_1_7_tktt_nnngl(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //     //                 .collect(Collectors.toList());
    //     //         api_1_7_tktt_nnngl_service.insert(formattedData);
    //     //     }
    //     //     case "API_1_9_UPDATE_TTDS_TKTT_DK" -> {
    //     //         List<API_1_9_update_tktt_dinh_ky> formattedData = rawData.stream()
    //     //                 .map(data -> mapToAPI_1_9_update_tktt_dinh_ky(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //     //                 .collect(Collectors.toList());
    //     //         api_1_9_update_tktt_dinh_ky_service.insert(formattedData);
    //     //     }
    //     //     case "API_1_8_UPDATE_TTDS_TKTT_NNGL" -> {
    //     //         List<API_1_8_update_tktt_nnngl> formattedData = rawData.stream()
    //     //                 .map(data -> mapToAPI_1_8_update_tktt_nnngl(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //     //                 .collect(Collectors.toList());
    //     //         api_1_8_update_tktt_nnngl_service.insert(formattedData);
    //     //     }

    //         // You can add more cases here as needed
    //     //     default -> {
    //     //         // Optional: handle unsupported templateID
    //     //         throw new IllegalArgumentException("Unsupported templateID: " + templateID);
    //     //     }
    //     // }

    //     return ResponseEntity.ok(history);
    // }
    // @PostMapping("confirm")
    // public ResponseEntity<LIST_FILE_UPLOAD> uploadDataMas( @RequestParam String id)
    // {
    //     logger.info("@ Request Upload Come!");
    //     LIST_FILE_UPLOAD fileUpload = historyService.findById(id,"00");
        
    //     // Delete Old File on the key Template ID, Month Year and UserName. 
    //     api_1_6_tktt_dinh_ky_service.deleteByTemplateIDAndMonthYearAndUsername(fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername());
    //     // Lưu danh sách dữ liệu từ "data"

    //     LIST_FILE_UPLOAD history = historyService.updateById(id,"10");

    //     String templateID = fileUpload.getTemplateID().toUpperCase();
    //     List<Map<String, Object>> rawData = fileUpload.getData();
    //     switch (templateID) {
    //             case "API_1_6_TTDS_TKTT_DK" -> {
    //                 List<API_1_6_tktt_dinh_ky> formattedData = rawData.stream()
    //                         .map(data -> mapToAPI_1_6_tktt_dinh_ky(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //                         .collect(Collectors.toList());
    //                 api_1_6_tktt_dinh_ky_service.insert(formattedData);
    //             }
    //         case "API_1_7_TTDS_TKTT_NNGL" -> {
    //             List<API_1_7_tktt_nnngl> formattedData = rawData.stream()
    //                     .map(data -> mapToAPI_1_7_tktt_nnngl(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //                     .collect(Collectors.toList());
    //             api_1_7_tktt_nnngl_service.insert(formattedData);
    //         }
    //         case "API_1_9_UPDATE_TTDS_TKTT_DK" -> {
    //             List<API_1_9_update_tktt_dinh_ky> formattedData = rawData.stream()
    //                     .map(data -> mapToAPI_1_9_update_tktt_dinh_ky(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //                     .collect(Collectors.toList());
    //             api_1_9_update_tktt_dinh_ky_service.insert(formattedData);
    //         }
    //         case "API_1_8_UPDATE_TTDS_TKTT_NNGL" -> {
    //             List<API_1_8_update_tktt_nnngl> formattedData = rawData.stream()
    //                     .map(data -> mapToAPI_1_8_update_tktt_nnngl(data, fileUpload.getTemplateID(), fileUpload.getMonthYear(), fileUpload.getUsername()))
    //                     .collect(Collectors.toList());
    //             api_1_8_update_tktt_nnngl_service.insert(formattedData);
    //         }

    //         // You can add more cases here as needed
    //         default -> {
    //             // Optional: handle unsupported templateID
    //             throw new IllegalArgumentException("Unsupported templateID: " + templateID);
    //         }
    //     }

    //     return ResponseEntity.ok(history);
    // }
    // private API_1_6_tktt_dinh_ky mapToAPI_1_6_tktt_dinh_ky(Map<String, Object> dataMap,String templateID, String monthYear, String username) {
    //     API_1_6_tktt_dinh_ky entity = new API_1_6_tktt_dinh_ky();
    //     try {
        
    //     entity.setCif(String.valueOf(dataMap.get("Cif")));
    //     entity.setSoID(String.valueOf(dataMap.get("SoID")));
    //     entity.setLoaiID(((Number) dataMap.get("LoaiID")).intValue());
    //     entity.setTenKhachHang((String) dataMap.get("TenKhachHang"));
    //     entity.setNgaySinh((String) dataMap.get("NgaySinh"));

    //     // Xử lý GioiTinh (chuyển từ String -> Integer)
    //     String gioiTinhStr = String.valueOf(dataMap.get("GioiTinh"));
    //     entity.setGioiTinh(gioiTinhStr != null ? Integer.parseInt(gioiTinhStr) : null);

    //     // Xử lý MaSoThue (có thể là Long)
    //     Object maSoThueObj = dataMap.get("MaSoThue");
    //     if (maSoThueObj instanceof Number) {
    //         entity.setMaSoThue(String.valueOf(((Number) maSoThueObj).longValue()));
    //     } else {
    //         entity.setMaSoThue(String.valueOf(maSoThueObj != null ? Long.parseLong(maSoThueObj.toString()) : null));
    //     }

    //     entity.setSoDienThoaiDangKyDichVu((String) dataMap.get("SoDienThoaiDangKyDichVu"));
    //     entity.setDiaChi((String) dataMap.get("DiaChi"));
    //     entity.setSoTaiKhoan((String) dataMap.get("SoTaiKhoan"));
    //     entity.setLoaiTaiKhoan(((Number) dataMap.get("LoaiTaiKhoan")).intValue());
    //     entity.setTrangThaiHoatDongTaiKhoan(((Number) dataMap.get("TrangThaiHoatDongTaiKhoan")).intValue());

    //     // Xử lý NgayMoTaiKhoan (nếu rỗng thì set null)
    //     String ngayMoTaiKhoan = (String) dataMap.get("NgayMoTaiKhoan");
    //     entity.setNgayMoTaiKhoan((ngayMoTaiKhoan == null || ngayMoTaiKhoan.isEmpty()) ? null : ngayMoTaiKhoan);

    //     entity.setPhuongThucMoTaiKhoan(((Number) dataMap.get("PhuongThucMoTaiKhoan")).intValue());
    //     entity.setQuocTich((String) dataMap.get("QuocTich")); // Nếu cần, có thể chuẩn hóa về "VN"
    //     //entity.setDiaChiKiemSoatTruyCap(String.valueOf(dataMap.get("DiaChiKiemSoatTruyCap")));
    //     Object rawValue = dataMap.get("DiaChiKiemSoatTruyCap");
    //     entity.setDiaChiKiemSoatTruyCap(rawValue != null ? rawValue.toString() : "");
    //     Object rawValue1 = dataMap.get("MaSoNhanDangThietBiDiDong");
    //     entity.setMaSoNhanDangThietBiDiDong(rawValue1 != null ? rawValue1.toString() : "");
    //     //Object rawValue2 = dataMap.get("NgayXacThucTaiQuay");
    //     //entity.setNgayXacThucTaiQuay(rawValue2 != null ? rawValue2.toString() : "");
    //     entity.setTemplateID(templateID);
    //     entity.setMonthYear(monthYear);
    //     entity.setUsername(username);

    // } catch (Exception e) {
    //     logger.error(e.getMessage(), e);
    //     throw new IllegalArgumentException("Lỗi khi map dữ liệu sang API_1_6_tktt_dinh_ky: CIF: [" + String.valueOf(entity.getCif()) +"]s" , e);
    // }
    //     return entity;
    // }
    // private API_1_7_tktt_nnngl mapToAPI_1_7_tktt_nnngl(Map<String, Object> dataMap, String templateID, String monthYear,String username) {
    //     API_1_7_tktt_nnngl entity = new API_1_7_tktt_nnngl();

    //     // Lấy dữ liệu với nhiều key có thể có (theo yêu cầu)
    //     try {
    //     entity.setCif(String.valueOf(dataMap.get("Cif")));

    //     String soTaiKhoan = (String) dataMap.getOrDefault("Số tài khoản",
    //             dataMap.getOrDefault("SoTaiKhoan", ""));
    //     entity.setSoTaiKhoan(soTaiKhoan);

    //     String tenKhachHang = (String) dataMap.getOrDefault("Tên khách hàng",
    //             dataMap.getOrDefault("TenKhachHang", ""));
    //     entity.setTenKhachHang(tenKhachHang);

    //     // TrangThaiHoatDongTaiKhoan có thể là Number hoặc String, xử lý tương tự
    //     Object trangThaiObj = dataMap.getOrDefault("Trạng thái hoạt động của tài khoản",
    //             dataMap.get("TrangThaiHoatDongTaiKhoan"));
    //     if (trangThaiObj instanceof Number) {
    //         entity.setTrangThaiHoatDongTaiKhoan(((Number) trangThaiObj).intValue());
    //     } else if (trangThaiObj instanceof String) {
    //         try {
    //             entity.setTrangThaiHoatDongTaiKhoan(Integer.parseInt((String) trangThaiObj));
    //         } catch (NumberFormatException e) {
    //             entity.setTrangThaiHoatDongTaiKhoan(null);
    //         }
    //     } else {
    //         entity.setTrangThaiHoatDongTaiKhoan(null);
    //     }

    //     // NghiNgo cũng có thể là Number hoặc String
    //     Object nghiNgoObj = dataMap.getOrDefault("Nghi ngờ", dataMap.get("NghiNgo"));
    //     if (nghiNgoObj instanceof Number) {
    //         entity.setNghiNgo(((Number) nghiNgoObj).intValue());
    //     } else if (nghiNgoObj instanceof String) {
    //         try {
    //             entity.setNghiNgo(Integer.parseInt((String) nghiNgoObj));
    //         } catch (NumberFormatException e) {
    //             entity.setNghiNgo(null);
    //         }
    //     } else {
    //         entity.setNghiNgo(null);
    //     }

    //     // GhiChu là String, có thể null hoặc rỗng
    //     String ghiChu = (String) dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"));
    //     entity.setGhiChu(ghiChu);

    //     // Set thêm các trường TemplateID và MonthYear
    //     entity.setTemplateID(templateID);
    //     entity.setMonthYear(monthYear);
    //     entity.setUsername(username);
    // } catch (Exception e) {
    //     logger.error(e.getMessage(), e);
    //     throw new IllegalArgumentException("Lỗi khi map dữ liệu sang API_1_7_tktt_nnngl: CIF: [" + String.valueOf(entity.getCif()) +"]", e);
    // }
    //     return entity;
    // }
    // private API_1_9_update_tktt_dinh_ky mapToAPI_1_9_update_tktt_dinh_ky(Map<String, Object> dataMap,String templateID, String monthYear,String username) {
    //     API_1_9_update_tktt_dinh_ky entity = new API_1_9_update_tktt_dinh_ky();
    //     try {
    //     entity.setCif(String.valueOf(dataMap.get("Cif")));
    //     entity.setSoID((String) dataMap.get("SoID"));
    //     entity.setLoaiID(((Number) dataMap.get("LoaiID")).intValue());
    //     entity.setTenKhachHang((String) dataMap.get("TenKhachHang"));
    //     entity.setNgaySinh((String) dataMap.get("NgaySinh"));

    //     // Xử lý GioiTinh (chuyển từ String -> Integer)
    //     String gioiTinhStr = (String) dataMap.get("GioiTinh");
    //     entity.setGioiTinh(gioiTinhStr != null ? Integer.parseInt(gioiTinhStr) : null);

    //     // Xử lý MaSoThue (có thể là Long)
    //     Object maSoThueObj = dataMap.get("MaSoThue");
    //     if (maSoThueObj instanceof Number) {
    //         entity.setMaSoThue(String.valueOf(((Number) maSoThueObj).longValue()));
    //     } else {
    //         entity.setMaSoThue(String.valueOf(maSoThueObj != null ? Long.parseLong(maSoThueObj.toString()) : null));
    //     }

    //     entity.setSoDienThoaiDangKyDichVu((String) dataMap.get("SoDienThoaiDangKyDichVu"));
    //     entity.setDiaChi((String) dataMap.get("DiaChi"));
    //     entity.setSoTaiKhoan((String) dataMap.get("SoTaiKhoan"));
    //     entity.setLoaiTaiKhoan(((Number) dataMap.get("LoaiTaiKhoan")).intValue());
    //     entity.setTrangThaiHoatDongTaiKhoan(((Number) dataMap.get("TrangThaiHoatDongTaiKhoan")).intValue());

    //     // Xử lý NgayMoTaiKhoan (nếu rỗng thì set null)
    //     String ngayMoTaiKhoan = (String) dataMap.get("NgayMoTaiKhoan");
    //     entity.setNgayMoTaiKhoan((ngayMoTaiKhoan == null || ngayMoTaiKhoan.isEmpty()) ? null : ngayMoTaiKhoan);

    //     entity.setPhuongThucMoTaiKhoan(((Number) dataMap.get("PhuongThucMoTaiKhoan")).intValue());
    //     entity.setQuocTich((String) dataMap.get("QuocTich")); // Nếu cần, có thể chuẩn hóa về "VN"
    //     String ghiChu = (String) dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"));
    //     entity.setGhiChu(ghiChu);
    //     entity.setTemplateID(templateID);
    //     entity.setMonthYear(monthYear);
    //     entity.setUsername(username);
    // } catch (Exception e) {
    //     logger.error(e.getMessage(), e);
    //     throw new IllegalArgumentException("Lỗi khi map dữ liệu sang API_1_9_update_tktt_dinh_ky: CIF: [" + String.valueOf(entity.getCif()) +"]", e);
    // }

    //     return entity;
    // }
    // private API_1_8_update_tktt_nnngl mapToAPI_1_8_update_tktt_nnngl(Map<String, Object> dataMap, String templateID, String monthYear, String username) {
    //     API_1_8_update_tktt_nnngl entity = new API_1_8_update_tktt_nnngl();
    //     try {
    //     // Lấy dữ liệu với nhiều key có thể có (theo yêu cầu)
    //     entity.setCif(String.valueOf(dataMap.get("Cif")));

    //     String soTaiKhoan = (String) dataMap.getOrDefault("Số tài khoản",
    //             dataMap.getOrDefault("SoTaiKhoan", ""));
    //     entity.setSoTaiKhoan(soTaiKhoan);

    //     String tenKhachHang = (String) dataMap.getOrDefault("Tên khách hàng",
    //             dataMap.getOrDefault("TenKhachHang", ""));
    //     entity.setTenKhachHang(tenKhachHang);

    //     // TrangThaiHoatDongTaiKhoan có thể là Number hoặc String, xử lý tương tự
    //     Object trangThaiObj = dataMap.getOrDefault("Trạng thái hoạt động của tài khoản",
    //             dataMap.get("TrangThaiHoatDongTaiKhoan"));
    //     if (trangThaiObj instanceof Number) {
    //         entity.setTrangThaiHoatDongTaiKhoan(((Number) trangThaiObj).intValue());
    //     } else if (trangThaiObj instanceof String) {
    //         try {
    //             entity.setTrangThaiHoatDongTaiKhoan(Integer.parseInt((String) trangThaiObj));
    //         } catch (NumberFormatException e) {
    //             entity.setTrangThaiHoatDongTaiKhoan(null);
    //         }
    //     } else {
    //         entity.setTrangThaiHoatDongTaiKhoan(null);
    //     }

    //     // NghiNgo cũng có thể là Number hoặc String
    //     Object nghiNgoObj = dataMap.getOrDefault("Nghi ngờ", dataMap.get("NghiNgo"));
    //     if (nghiNgoObj instanceof Number) {
    //         entity.setNghiNgo(((Number) nghiNgoObj).intValue());
    //     } else if (nghiNgoObj instanceof String) {
    //         try {
    //             entity.setNghiNgo(Integer.parseInt((String) nghiNgoObj));
    //         } catch (NumberFormatException e) {
    //             entity.setNghiNgo(null);
    //         }
    //     } else {
    //         entity.setNghiNgo(null);
    //     }

    //     // GhiChu là String, có thể null hoặc rỗng
    //     String ghiChu = (String) dataMap.getOrDefault("Ghi chú", dataMap.get("GhiChu"));
    //     entity.setGhiChu(ghiChu);
    //     String lyDoCapNhat = (String) dataMap.getOrDefault("Lý do cập nhật", dataMap.get("LyDoCapNhat"));
    //     entity.setLyDoCapNhat(lyDoCapNhat);
    //     // Set thêm các trường TemplateID và MonthYear
    //     entity.setTemplateID(templateID);
    //     entity.setMonthYear(monthYear);
    //     entity.setUsername(username);
        
    // } catch (Exception e) {
    //     logger.error(e.getMessage(), e);
    //     throw new IllegalArgumentException("Lỗi khi map dữ liệu sang API_1_8_update_tktt_nnngl: CIF: [" + String.valueOf(entity.getCif()) +"]", e);
    // }
    //     return entity;
    // }
    // @GetMapping("/list")
    // public Page<LIST_FILE_UPLOAD> getFiles(
    //         @RequestParam(required = false) String templateID,
    //         @RequestParam(required = false) String monthYear,
    //         @RequestParam(defaultValue = "") String username,
    //         @RequestParam(defaultValue = "0") int page,
    //         @RequestParam(defaultValue = "100") int size) {
    //             return historyService.getFiles(templateID, monthYear, username, page, size);
    // }
}