package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.*;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.SimoService;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.StatusUpdateService;
import SHINHAN_PORTAL.REPORT_SIMO.common.DataMapperUtils;

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
    @Autowired
	private StatusUpdateService statusUpdateService;

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
            case "API_1_27_TT_DVCNTT" -> {
                List<API_1_27_TT_DVCNTT_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_27_TT_DVCNTT)
                        .collect(Collectors.toList());
                return simoService.api_1_27_uploadDVCNTT_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_28_TT_DVCNTT_NGGL" -> {
                List<API_1_28_TT_DVCNTT_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_28_TT_DVCNTT_NGGL)
                        .collect(Collectors.toList());
                return simoService.api_1_28_uploadNGGL_DVCNTT_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_29_UPDATE_DVCNTT_NGGL" -> {
                List<API_1_29_UPDATE_DVCNTT_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_29_UPDATE_DVCNTT_NGGL)
                        .collect(Collectors.toList());
                return simoService.api_1_29_updateNGGL_DVCNTT_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_30_UPDATE_DVCNTT" -> {
                List<API_1_30_UPDATE_DVCNTT_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_30_UPDATE_DVCNTT)
                        .collect(Collectors.toList());
                return simoService.api_1_30_updateDVCNTT_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_23_TOCHUC" -> {
                List<API_1_23_TOCHUC_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_23_TOCHUC)
                        .collect(Collectors.toList());
                // TODO: Gọi service xử lý upload cho API_1_23
                return simoService.api_1_23_uploadToChuc_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_24_TOCHUC_NGGL" -> {
                List<API_1_24_TOCHUC_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_24_TOCHUC_NGGL)
                        .collect(Collectors.toList());
                // TODO: Gọi service xử lý upload cho API_1_24
                return simoService.api_1_24_uploadToChucNGGL_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_25_UPDATE_TOCHUC_NGGL" -> {
                List<API_1_25_UPDATE_TOCHUC_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_25_UPDATE_TOCHUC_NGGL)
                        .collect(Collectors.toList());
                // TODO: Gọi service xử lý upload cho API_1_25
                return simoService.api_1_25_update_uploadToChucNGGL_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_26_UPDATE_TOCHUC" -> {
                List<API_1_26_UPDATE_TOCHUC_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_26_UPDATE_TOCHUC)
                        .collect(Collectors.toList());
                // TODO: Gọi service xử lý upload cho API_1_26
                return simoService.api_1_26_update_uploadToChuc_autoToken(maYeuCau, kyBaoCao, formattedData);
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

    
        entity.setCif(DataMapperUtils.safeString(dataMap.get("cif")));
        entity.setSoID(DataMapperUtils.safeString(dataMap.get("soID")));
        entity.setLoaiID(DataMapperUtils.safeInteger(dataMap.get("loaiID"), null));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.get("tenKhachHang")));
        entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("ngaySinh")));
        entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.get("gioiTinh"), null));
        entity.setMaSoThue(DataMapperUtils.safeString(dataMap.get("maSoThue")));
        entity.setSoDienThoaiDangKyDichVu(DataMapperUtils.safeString(dataMap.get("soDienThoaiDangKyDichVu")));
        entity.setDiaChi(DataMapperUtils.safeString(dataMap.get("diaChi")));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("soTaiKhoan")));
        entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("loaiTaiKhoan"), null));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("trangThaiHoatDongTaiKhoan"), null));
        entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("ngayMoTaiKhoan"), null, true));
        entity.setPhuongThucMoTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("phuongThucMoTaiKhoan"), null));
        entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("quocTich")));
        entity.setDiaChiKiemSoatTruyCap(DataMapperUtils.safeString(dataMap.get("diaChiKiemSoatTruyCap")));
        entity.setMaSoNhanDangThietBiDiDong(DataMapperUtils.safeString(dataMap.get("maSoNhanDangThietBiDiDong")));
        entity.setNgayXacThucTaiQuay(DataMapperUtils.safeString(dataMap.get("ngayXacThucTaiQuay"), null, true));
    
        return entity;
    }
    private API_1_7_tktt_nggl_DT0 mapToAPI_1_7_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_7_tktt_nggl_DT0 entity = new API_1_7_tktt_nggl_DT0();

        entity.setCif(DataMapperUtils.safeString(dataMap.get("cif")));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("Số tài khoản", dataMap.get("soTaiKhoan"))));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("Tên khách hàng", dataMap.get("tenKhachHang"))));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("Trạng thái hoạt động của tài khoản", dataMap.get("trangThaiHoatDongTaiKhoan")), null));
        entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("Nghi ngờ", dataMap.get("nghiNgo")), null));
        entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("Ghi chú", dataMap.get("ghiChu"))));
        return entity;
    }
    private API_1_9_update_tktt_nggl_DT0 mapToAPI_1_9_update_tktt_dinh_ky(Map<String, Object> dataMap) {
        API_1_9_update_tktt_nggl_DT0 entity = new API_1_9_update_tktt_nggl_DT0();
       
        entity.setCif(DataMapperUtils.safeString(dataMap.get("cif")));
        entity.setSoID(DataMapperUtils.safeString(dataMap.get("soID")));
        entity.setLoaiID(DataMapperUtils.safeInteger(dataMap.get("loaiID"), null));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.get("tenKhachHang")));
        entity.setNgaySinh(DataMapperUtils.safeString(dataMap.get("ngaySinh")));
        entity.setGioiTinh(DataMapperUtils.safeInteger(dataMap.get("gioiTinh"), null));
        entity.setMaSoThue(DataMapperUtils.safeString(dataMap.get("maSoThue")));
        entity.setSoDienThoaiDangKyDichVu(DataMapperUtils.safeString(dataMap.get("soDienThoaiDangKyDichVu")));
        entity.setDiaChi(DataMapperUtils.safeString(dataMap.get("diaChi")));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.get("soTaiKhoan")));
        entity.setLoaiTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("loaiTaiKhoan"), null));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("trangThaiHoatDongTaiKhoan"), null));
        entity.setNgayMoTaiKhoan(DataMapperUtils.safeString(dataMap.get("ngayMoTaiKhoan"), null, true));
        entity.setPhuongThucMoTaiKhoan(DataMapperUtils.safeInteger(dataMap.get("phuongThucMoTaiKhoan"), null));
        entity.setQuocTich(DataMapperUtils.safeString(dataMap.get("quocTich")));
        entity.setDiaChiKiemSoatTruyCap(DataMapperUtils.safeString(dataMap.get("diaChiKiemSoatTruyCap")));
        entity.setMaSoNhanDangThietBiDiDong(DataMapperUtils.safeString(dataMap.get("maSoNhanDangThietBiDiDong")));
        entity.setNgayXacThucTaiQuay(DataMapperUtils.safeString(dataMap.get("ngayXacThucTaiQuay"), null, true));
        entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("Ghi chú", dataMap.get("ghiChu"))));
        
        return entity;
    }
    private API_1_8_update_tktt_nggl_DT0 mapToAPI_1_8_update_tktt_nnngl(Map<String, Object> dataMap) {
        API_1_8_update_tktt_nggl_DT0 entity = new API_1_8_update_tktt_nggl_DT0();
        entity.setCif(DataMapperUtils.safeString(dataMap.get("cif")));
        entity.setSoTaiKhoan(DataMapperUtils.safeString(dataMap.getOrDefault("Số tài khoản", dataMap.get("soTaiKhoan"))));
        entity.setTenKhachHang(DataMapperUtils.safeString(dataMap.getOrDefault("Tên khách hàng", dataMap.get("tenKhachHang"))));
        entity.setTrangThaiHoatDongTaiKhoan(DataMapperUtils.safeInteger(dataMap.getOrDefault("Trạng thái hoạt động của tài khoản", dataMap.get("trangThaiHoatDongTaiKhoan")), null));
        entity.setNghiNgo(DataMapperUtils.safeInteger(dataMap.getOrDefault("Nghi ngờ", dataMap.get("nghiNgo")), null));
        entity.setGhiChu(DataMapperUtils.safeString(dataMap.getOrDefault("Ghi chú", dataMap.get("ghiChu"))));
        entity.setLyDoCapNhat(DataMapperUtils.safeString(dataMap.getOrDefault("Lý do cập nhật", dataMap.get("lyDoCapNhat"))));
        return entity;
    }
    private String safeString(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
    enum TemplateID {
        API_1_6_TTDS_TKTT_DK,
        API_1_7_TTDS_TKTT_NNGL,
        API_1_9_UPDATE_TTDS_TKTT_DK,
        API_1_8_UPDATE_TTDS_TKTT_NNGL,
        API_1_27_TT_DVCNTT,
        API_1_28_TT_DVCNTT_NGGL,
        API_1_29_UPDATE_DVCNTT_NGGL,
        API_1_30_UPDATE_DVCNTT;
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