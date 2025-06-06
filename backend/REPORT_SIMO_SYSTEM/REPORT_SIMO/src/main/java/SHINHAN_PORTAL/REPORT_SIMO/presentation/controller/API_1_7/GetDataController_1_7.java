package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller.API_1_7;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.API_1_7_tktt_nnngl_service;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.API_1_7_tktt_nnngl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/API_1_7_TTDS_TKTT_NNGL")
public class GetDataController_1_7 {

    @Autowired
    private API_1_7_tktt_nnngl_service apiService;

    @GetMapping("/getData")
    public ResponseEntity<List<API_1_7_tktt_nnngl>> getData(
            @RequestParam String templateID,
            @RequestParam String monthYear
    ) {
        List<API_1_7_tktt_nnngl> dataList = apiService.getData(templateID, monthYear);
        if (dataList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dataList);
    }
}
