package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.*;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.SimoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simo")
public class SimoController {

    @Autowired
    private SimoService simoService;
    //@Autowired
    //private final SimoMapper simoMapper;

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

        entity.setCif(String.valueOf(dataMap.get("cif")));
        entity.setSoID(String.valueOf(dataMap.get("soID")));
        
        Object loaiID = dataMap.get("loaiID");
        entity.setLoaiID(loaiID instanceof Number ? ((Number) loaiID).intValue() : 0);
        
        entity.setTenKhachHang(String.valueOf(dataMap.get("tenKhachHang")));
        entity.setNgaySinh(String.valueOf(dataMap.get("ngaySinh")));
        
        Object gioiTinh = dataMap.get("gioiTinh");
        entity.setGioiTinh(gioiTinh instanceof Number ? ((Number) gioiTinh).intValue() : 0);
        
        Object maSoThue = dataMap.get("maSoThue");
        entity.setMaSoThue(maSoThue != null ? String.valueOf(maSoThue) : null);
        
        entity.setSoDienThoaiDangKyDichVu(String.valueOf(dataMap.get("soDienThoaiDangKyDichVu")));
        entity.setDiaChi(String.valueOf(dataMap.get("diaChi")));
        entity.setMaSoNhanDangThietBiDiDong(String.valueOf(dataMap.get("maSoNhanDangThietBiDiDong")));
        entity.setDiaChiKiemSoatTruyCap(String.valueOf(dataMap.get("diaChiKiemSoatTruyCap")));
        entity.setNgayXacThucTaiQuay(String.valueOf(dataMap.get("ngayXacThucTaiQuay")));
        entity.setSoTaiKhoan(String.valueOf(dataMap.get("soTaiKhoan")));
        
        Object loaiTaiKhoan = dataMap.get("loaiTaiKhoan");
        entity.setLoaiTaiKhoan(loaiTaiKhoan instanceof Number ? ((Number) loaiTaiKhoan).intValue() : 0);
        
        Object trangThai = dataMap.get("trangThaiHoatDongTaiKhoan");
        entity.setTrangThaiHoatDongTaiKhoan(trangThai instanceof Number ? ((Number) trangThai).intValue() : 0);
        
        String ngayMoTaiKhoan = String.valueOf(dataMap.get("ngayMoTaiKhoan"));
        entity.setNgayMoTaiKhoan((ngayMoTaiKhoan == null || ngayMoTaiKhoan.trim().isEmpty()) ? null : ngayMoTaiKhoan);
        
        Object phuongThuc = dataMap.get("phuongThucMoTaiKhoan");
        entity.setPhuongThucMoTaiKhoan(phuongThuc instanceof Number ? ((Number) phuongThuc).intValue() : 0);
        
        entity.setQuocTich(String.valueOf(dataMap.get("quocTich")));
        
        return entity;
    }
    private API_1_7_tktt_nggl_DT0 mapToAPI_1_7_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_7_tktt_nggl_DT0 entity = new API_1_7_tktt_nggl_DT0();

        // Lấy dữ liệu với nhiều key có thể có (theo yêu cầu)
        entity.setCif(String.valueOf(dataMap.get("cif")));
        entity.setSoTaiKhoan(String.valueOf( dataMap.get("soTaiKhoan")));

        entity.setTenKhachHang(String.valueOf(dataMap.getOrDefault("tenKhachHang", "")));

        // TrangThaiHoatDongTaiKhoan có thể là Number hoặc String, xử lý tương tự
        Object trangThaiObj = dataMap.get("trangThaiHoatDongTaiKhoan");
        if (trangThaiObj instanceof Number) {
            entity.setTrangThaiHoatDongTaiKhoan(((Number) trangThaiObj).intValue());
        } else if (trangThaiObj instanceof String) {
            try {
                entity.setTrangThaiHoatDongTaiKhoan(Integer.parseInt((String) trangThaiObj));
            } catch (NumberFormatException e) {
                entity.setTrangThaiHoatDongTaiKhoan(0);
            }
        } else {
            entity.setTrangThaiHoatDongTaiKhoan(0);
        }
        // NghiNgo cũng có thể là Number hoặc String
        Object nghiNgoObj = dataMap.get("nghiNgo");
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
        String ghiChu = String.valueOf(dataMap.get("ghiChu"));
        entity.setGhiChu(ghiChu);
        return entity;
    }
    private API_1_9_update_tktt_nggl_DT0 mapToAPI_1_9_update_tktt_dinh_ky(Map<String, Object> dataMap) {
        API_1_9_update_tktt_nggl_DT0 entity = new API_1_9_update_tktt_nggl_DT0();
       
        // entity.setCif(String.valueOf(dataMap.get("cif")));
        // entity.setSoID(String.valueOf(dataMap.get("soID")));
        
        // Object loaiID = dataMap.get("loaiID");
        // entity.setLoaiID(loaiID instanceof Number ? ((Number) loaiID).intValue() : 0);
        
        // entity.setTenKhachHang(String.valueOf(dataMap.get("tenKhachHang")));
        // entity.setNgaySinh(String.valueOf(dataMap.get("ngaySinh")));
        
        // Object gioiTinh = dataMap.get("gioiTinh");
        // entity.setGioiTinh(gioiTinh instanceof Number ? ((Number) gioiTinh).intValue() : 0);
        
        // Object maSoThue = dataMap.get("maSoThue");
        // entity.setMaSoThue(maSoThue != null ? String.valueOf(maSoThue) : "");
        
        // entity.setSoDienThoaiDangKyDichVu(String.valueOf(dataMap.get("soDienThoaiDangKyDichVu")));
        // entity.setDiaChi(String.valueOf(dataMap.get("diaChi")));
        // entity.setMaSoNhanDangThietBiDiDong(String.valueOf(dataMap.get("maSoNhanDangThietBiDiDong")));
        // entity.setDiaChiKiemSoatTruyCap(String.valueOf(dataMap.get("diaChiKiemSoatTruyCap")));
        // entity.setNgayXacThucTaiQuay(String.valueOf(dataMap.get("ngayXacThucTaiQuay")));
        // entity.setSoTaiKhoan(String.valueOf(dataMap.get("soTaiKhoan")));
        
        // Object loaiTaiKhoan = dataMap.get("loaiTaiKhoan");
        // entity.setLoaiTaiKhoan(loaiTaiKhoan instanceof Number ? ((Number) loaiTaiKhoan).intValue() : 0);
        
        // Object trangThai = dataMap.get("trangThaiHoatDongTaiKhoan");
        // entity.setTrangThaiHoatDongTaiKhoan(trangThai instanceof Number ? ((Number) trangThai).intValue() : 0);
        
        // String ngayMoTaiKhoan = String.valueOf(dataMap.get("ngayMoTaiKhoan"));
        // entity.setNgayMoTaiKhoan((ngayMoTaiKhoan == null || ngayMoTaiKhoan.trim().isEmpty()) ? null : ngayMoTaiKhoan);
        
        // Object phuongThuc = dataMap.get("phuongThucMoTaiKhoan");
        // entity.setPhuongThucMoTaiKhoan(phuongThuc instanceof Number ? ((Number) phuongThuc).intValue() : 0);
        
        // entity.setQuocTich(String.valueOf(dataMap.get("quocTich")));
        // String ghiChu = String.valueOf(dataMap.get("GhiChu"));
        // entity.setGhiChu(ghiChu);
        entity.setCif(safeString(dataMap.get("cif")));
        entity.setSoID(safeString(dataMap.get("soID")));
        
        Object loaiID = dataMap.get("loaiID");
        entity.setLoaiID(loaiID instanceof Number ? ((Number) loaiID).intValue() : 0);
        
        entity.setTenKhachHang(safeString(dataMap.get("tenKhachHang")));
        entity.setNgaySinh(safeString(dataMap.get("ngaySinh")));
        
        Object gioiTinh = dataMap.get("gioiTinh");
        entity.setGioiTinh(gioiTinh instanceof Number ? ((Number) gioiTinh).intValue() : 0);
        
        entity.setMaSoThue(safeString(dataMap.get("maSoThue")));
        entity.setSoDienThoaiDangKyDichVu(safeString(dataMap.get("soDienThoaiDangKyDichVu")));
        entity.setDiaChi(safeString(dataMap.get("diaChi")));
        entity.setMaSoNhanDangThietBiDiDong(safeString(dataMap.get("maSoNhanDangThietBiDiDong")));
        entity.setDiaChiKiemSoatTruyCap(safeString(dataMap.get("diaChiKiemSoatTruyCap")));
        entity.setNgayXacThucTaiQuay(safeString(dataMap.get("ngayXacThucTaiQuay")));
        entity.setSoTaiKhoan(safeString(dataMap.get("soTaiKhoan")));
        
        Object loaiTaiKhoan = dataMap.get("loaiTaiKhoan");
        entity.setLoaiTaiKhoan(loaiTaiKhoan instanceof Number ? ((Number) loaiTaiKhoan).intValue() : 0);
        
        Object trangThai = dataMap.get("trangThaiHoatDongTaiKhoan");
        entity.setTrangThaiHoatDongTaiKhoan(trangThai instanceof Number ? ((Number) trangThai).intValue() : 0);
        
        String ngayMoTaiKhoan = safeString(dataMap.get("ngayMoTaiKhoan"));
        entity.setNgayMoTaiKhoan(ngayMoTaiKhoan.isEmpty() ? null : ngayMoTaiKhoan);  // giữ logic null nếu trống
        
        Object phuongThuc = dataMap.get("phuongThucMoTaiKhoan");
        entity.setPhuongThucMoTaiKhoan(phuongThuc instanceof Number ? ((Number) phuongThuc).intValue() : 0);
        
        entity.setQuocTich(safeString(dataMap.get("quocTich")));
        entity.setGhiChu(safeString(dataMap.get("GhiChu")));
        
        return entity;
    }
    private API_1_8_update_tktt_nggl_DT0 mapToAPI_1_8_update_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_8_update_tktt_nggl_DT0 entity = new API_1_8_update_tktt_nggl_DT0();

        // // Lấy dữ liệu với nhiều key có thể có (theo yêu cầu)
        // entity.setCif(String.valueOf(dataMap.get("cif")));
        // entity.setSoTaiKhoan(String.valueOf( dataMap.get("soTaiKhoan")));

        // entity.setTenKhachHang(String.valueOf(dataMap.getOrDefault("tenKhachHang", "")));

        // // TrangThaiHoatDongTaiKhoan có thể là Number hoặc String, xử lý tương tự
        // Object trangThaiObj = dataMap.get("trangThaiHoatDongTaiKhoan");
        // if (trangThaiObj instanceof Number) {
        //     entity.setTrangThaiHoatDongTaiKhoan(((Number) trangThaiObj).intValue());
        // } else if (trangThaiObj instanceof String) {
        //     try {
        //         entity.setTrangThaiHoatDongTaiKhoan(Integer.parseInt((String) trangThaiObj));
        //     } catch (NumberFormatException e) {
        //         entity.setTrangThaiHoatDongTaiKhoan(null);
        //     }
        // } else {
        //     entity.setTrangThaiHoatDongTaiKhoan(null);
        // }
        // // NghiNgo cũng có thể là Number hoặc String
        // Object nghiNgoObj = dataMap.get("nghiNgo");
        // if (nghiNgoObj instanceof Number) {
        //     entity.setNghiNgo(((Number) nghiNgoObj).intValue());
        // } else if (nghiNgoObj instanceof String) {
        //     try {
        //         entity.setNghiNgo(Integer.parseInt((String) nghiNgoObj));
        //     } catch (NumberFormatException e) {
        //         entity.setNghiNgo(null);
        //     }
        // } else {
        //     entity.setNghiNgo(null);
        // }

        // // GhiChu là String, có thể null hoặc rỗng
        // String ghiChu = String.valueOf(dataMap.get("ghiChu"));
        // entity.setGhiChu(ghiChu);
        // String lyDoCapNhat =String.valueOf(dataMap.get("lyDoCapNhat"));
        // entity.setLyDoCapNhat(lyDoCapNhat);
        // // Set thêm các trường TemplateID và MonthYear
 // Lấy dữ liệu với nhiều key có thể có (theo yêu cầu)
 entity.setCif(safeString(dataMap.get("cif")));
 entity.setSoTaiKhoan(safeString(dataMap.get("soTaiKhoan")));
 entity.setTenKhachHang(safeString(dataMap.get("tenKhachHang")));

 // TrangThaiHoatDongTaiKhoan có thể là Number hoặc String
 Object trangThaiObj = dataMap.get("trangThaiHoatDongTaiKhoan");
 if (trangThaiObj instanceof Number) {
     entity.setTrangThaiHoatDongTaiKhoan(((Number) trangThaiObj).intValue());
 } else if (trangThaiObj instanceof String) {
     try {
         entity.setTrangThaiHoatDongTaiKhoan(Integer.parseInt((String) trangThaiObj));
     } catch (NumberFormatException e) {
         entity.setTrangThaiHoatDongTaiKhoan(0); // ✅ set 0 thay vì null
     }
 } else {
     entity.setTrangThaiHoatDongTaiKhoan(0); // ✅ set 0 thay vì null
 }

 // NghiNgo cũng có thể là Number hoặc String
 Object nghiNgoObj = dataMap.get("nghiNgo");
 if (nghiNgoObj instanceof Number) {
     entity.setNghiNgo(((Number) nghiNgoObj).intValue());
 } else if (nghiNgoObj instanceof String) {
     try {
         entity.setNghiNgo(Integer.parseInt((String) nghiNgoObj));
     } catch (NumberFormatException e) {
         entity.setNghiNgo(0); // ✅ set 0 thay vì null
     }
 } else {
     entity.setNghiNgo(0); // ✅ set 0 thay vì null
 }

 // GhiChu và lyDoCapNhat là String, không để null
 entity.setGhiChu(safeString(dataMap.get("ghiChu")));
 entity.setLyDoCapNhat(safeString(dataMap.get("lyDoCapNhat")));
        return entity;
    }
    private String safeString(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
    enum TemplateID {
        API_1_6_TTDS_TKTT_DK,
    API_1_7_TTDS_TKTT_NNGL,
    API_1_9_UPDATE_TTDS_TKTT_DK,
    API_1_8_UPDATE_TTDS_TKTT_NNGL;
    public static TemplateID fromString(String templateId)
    {
        try{
            return valueOf(templateId.toUpperCase());

        }
        catch(IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Invalid Template ID: " + templateId);
        }
    }
    }
}