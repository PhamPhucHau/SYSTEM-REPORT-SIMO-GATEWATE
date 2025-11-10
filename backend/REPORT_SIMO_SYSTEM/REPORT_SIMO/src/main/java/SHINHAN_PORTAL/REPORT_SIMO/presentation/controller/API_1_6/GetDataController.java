package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller.API_1_6;


import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TKTTResponseDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.StatusUpdateService;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/API_1_6_TTDS_TKTT_DK")
public class GetDataController {
    private final Map<String, TemplateDataService<?>> serviceMap;

    @Autowired
    public GetDataController(Map<String, TemplateDataService<?>> serviceMap) {
        this.serviceMap = serviceMap;
    }
    @Autowired
	private StatusUpdateService statusUpdateService;
    // @Autowired
    // private API_1_6_tktt_dinh_ky_service api_1_6_tktt_dinh_ky_service;

    // @Autowired
    // private API_1_7_tktt_nnngl_service api_1_7_tktt_nnngl_service;
    // @Autowired
    // private API_1_9_update_tktt_dinh_ky_service api_1_9_update_tktt_dinh_ky_service;

    // @Autowired
    // private API_1_8_update_tktt_nnngl_service api_1_8_update_tktt_nnngl_service;


    @GetMapping("/getData")
    public ResponseEntity<?> getData(
            @RequestParam String templateID,
            @RequestParam String monthYear,
            @RequestParam String status
    ) {
        //String templateIdUpper = templateID.toUpperCase();

        // switch (templateIdUpper) {
        //     case "API_1_6_TTDS_TKTT_DK" -> {
        //         List<API_1_6_tktt_dinh_ky> data = api_1_6_tktt_dinh_ky_service.getData(templateID, monthYear);
        //         if (data.isEmpty()) {
        //             return ResponseEntity.noContent().build();
        //         }
        //         return ResponseEntity.ok(data);
        //     }
        //     case "API_1_7_TTDS_TKTT_NNGL" -> {
        //         List<API_1_7_tktt_nnngl> data = api_1_7_tktt_nnngl_service.getData(templateID, monthYear);
        //         if (data.isEmpty()) {
        //             return ResponseEntity.noContent().build();
        //         }
        //         return ResponseEntity.ok(data);
        //     }
        //     case "API_1_9_UPDATE_TTDS_TKTT_DK" -> {
        //         List<API_1_9_update_tktt_dinh_ky> data = api_1_9_update_tktt_dinh_ky_service.getData(templateID, monthYear);
        //         if (data.isEmpty()) {
        //             return ResponseEntity.noContent().build();
        //         }
        //         return ResponseEntity.ok(data);
        //     }
        //     case "API_1_8_UPDATE_TTDS_TKTT_NNGL" -> {
        //         List<API_1_8_update_tktt_nnngl> data = api_1_8_update_tktt_nnngl_service.getData(templateID, monthYear);
        //         if (data.isEmpty()) {
        //             return ResponseEntity.noContent().build();
        //         }
        //         return ResponseEntity.ok(data);
        //     }
        //     default -> {
        //         return ResponseEntity.badRequest()
        //                 .body("Unsupported templateID: " + templateID);
        //     }
        // }
        TemplateDataService<?> service = serviceMap.get(templateID.toUpperCase());
        if (service == null) {
            return ResponseEntity.badRequest().body("Unsupported templateID: " + templateID);
        }

        List<?> data = service.getData(templateID, monthYear,status);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }


	@PostMapping("/update-status")
	public TKTTResponseDTO updateStatus(@RequestHeader("maYeuCau") String maYeuCau,
	                                   @RequestHeader("kyBaoCao") String kyBaoCao,
	                                   @RequestParam String templateID,
	                                   @RequestParam(required = false, defaultValue = "90") String oldStatus,
	                                   @RequestParam(required = false, defaultValue = "00") String newStatus) {
		String monthYear = kyBaoCao == null ? "" : kyBaoCao.replace("/", "");
		long updated = statusUpdateService.updateStatus(templateID, monthYear, oldStatus, newStatus);

		TKTTResponseDTO response = new TKTTResponseDTO();
		response.setCode("200");
		response.setSuccess(true);
		response.setMessage("Updated " + updated + " record(s) for " + templateID + " - " + monthYear);
		return response;
	}
}