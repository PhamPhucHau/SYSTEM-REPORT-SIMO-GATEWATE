package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.*;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.SimoService;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.StatusUpdateService;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.UploadManagementService;
import SHINHAN_PORTAL.REPORT_SIMO.common.UploadLogHelper;
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
    @Autowired
    private UploadManagementService uploadManagementService;
    @Autowired
    private UploadLogHelper uploadLogHelper;

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
                                            @RequestBody List<Map<String, Object>> tkttList,
                                            @RequestHeader(value = "X-Username", required = false) String username,
                                            @RequestHeader(value = "X-User-Role", required = false) String userRole,
                                            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
                                            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {

        String templateIdUpper = templateID.toUpperCase();
        System.out.println("@@@@"+templateIdUpper);
        //uploadLogHelper.logSendAction(templateIdUpper, maYeuCau, kyBaoCao, username, userRole, requestId, correlationId, "10", "", "Start sending to SIMO detail","");                                        
        switch (templateIdUpper) {
            case "API_1_6_TTDS_TKTT_DK" -> {
                List<TKTTRequestDTO>  formattedData = tkttList.stream()
                        //.map(item -> mapToAPI_1_6_tktt_dinh_ky((Map<String, Object>) item))
                        .map(DataMapperUtils::mapToAPI_1_6_tktt_dinh_ky)
                        .collect(Collectors.toList());
                return simoService.uploadTKTTReportAutoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_7_TTDS_TKTT_NNGL" -> {
                List<API_1_7_tktt_nggl_DT0>  formattedData = tkttList.stream()
                        //.map(item -> mapToAPI_1_7_tktt_nnngl((Map<String, Object>) item))
                        .map(DataMapperUtils::mapToAPI_1_7_tktt_nnngl)
                        .collect(Collectors.toList());
                return simoService.api_1_7_uploadNGGL_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_9_UPDATE_TTDS_TKTT_DK" -> {
                List<API_1_9_update_tktt_nggl_DT0>  formattedData = tkttList.stream()
                        //.map(item ->mapToAPI_1_9_update_tktt_dinh_ky((Map<String, Object>) item))
                        .map(DataMapperUtils::mapToAPI_1_9_update_tktt_dinh_ky)
                        .collect(Collectors.toList());
                return simoService.api_1_9_TKTT_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_8_UPDATE_TTDS_TKTT_NNGL" -> {
                List<API_1_8_update_tktt_nggl_DT0>  formattedData = tkttList.stream()
                        //.map(item -> mapToAPI_1_8_update_tktt_nnngl((Map<String, Object>) item))
                        .map(DataMapperUtils::mapToAPI_1_8_update_tktt_nnngl)
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
            case "API_1_31_TT_TNH" -> {
                // SRP: Controller chỉ điều phối dữ liệu → DTO và gọi service, không chứa logic khác
                List<API_1_31_TT_TNH_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_31_TT_TNH)
                        .collect(Collectors.toList());
                return simoService.api_1_31_tt_tnh_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_32_TT_TNH_NGGL" -> {
                List<API_1_32_TT_TNH_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_32_TT_TNH_NGGL)
                        .collect(Collectors.toList());
                return simoService.api_1_32_tt_tnh_nggl_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_33_UPDATE_TNH_NGGL" -> {
                List<API_1_33_UPDATE_TNH_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_33_UPDATE_TNH_NGGL)
                        .collect(Collectors.toList());
                return simoService.api_1_33_update_tnh_nggl_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_34_UPDATE_TNH" -> {
                List<API_1_34_UPDATE_TNH_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_34_UPDATE_TNH)
                        .collect(Collectors.toList());
                return simoService.api_1_34_update_tnh_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_23_TTDS_TKTT_TC_DK" -> {
                List<API_1_23_TOCHUC_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_23_TOCHUC)
                        .collect(Collectors.toList());
                // TODO: Gọi service xử lý upload cho API_1_23
                return simoService.api_1_23_uploadToChuc_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_24_TTDS_TKTT_TC_NGGL" -> {
                List<API_1_24_TOCHUC_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_24_TOCHUC_NGGL)
                        .collect(Collectors.toList());
                // TODO: Gọi service xử lý upload cho API_1_24
                return simoService.api_1_24_uploadToChucNGGL_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_25_UPDATE_TTDS_TKTT_TC_NGGL" -> {
                List<API_1_25_UPDATE_TOCHUC_NGGL_DTO> formattedData = tkttList.stream()
                        .map(DataMapperUtils::mapTo_API_1_25_UPDATE_TOCHUC_NGGL)
                        .collect(Collectors.toList());
                // TODO: Gọi service xử lý upload cho API_1_25
                return simoService.api_1_25_update_uploadToChucNGGL_autoToken(maYeuCau, kyBaoCao, formattedData);
            }
            case "API_1_26_UPDATE_TTDS_TKTT_TC" -> {
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

   
    enum TemplateID {
        API_1_6_TTDS_TKTT_DK,
        API_1_7_TTDS_TKTT_NNGL,
        API_1_9_UPDATE_TTDS_TKTT_DK,
        API_1_8_UPDATE_TTDS_TKTT_NNGL,
        API_1_23_TTDS_TKTT_TC_DK,
        API_1_24_TTDS_TKTT_TC_NGGL,
        API_1_25_UPDATE_TTDS_TKTT_TC_NGGL,
        API_1_26_UPDATE_TTDS_TKTT_TC,
        API_1_27_TT_DVCNTT,
        API_1_28_TT_DVCNTT_NGGL,
        API_1_29_UPDATE_DVCNTT_NGGL,
        API_1_30_UPDATE_DVCNTT,
        API_1_31_TT_TNH,
        API_1_32_TT_TNH_NGGL,
        API_1_33_UPDATE_TNH_NGGL,
        API_1_34_UPDATE_TNH;
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