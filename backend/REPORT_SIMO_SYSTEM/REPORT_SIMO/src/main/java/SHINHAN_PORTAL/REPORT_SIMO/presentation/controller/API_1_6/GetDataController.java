package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller.API_1_6;


import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam String monthYear
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

        List<?> data = service.getData(templateID, monthYear);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }
}