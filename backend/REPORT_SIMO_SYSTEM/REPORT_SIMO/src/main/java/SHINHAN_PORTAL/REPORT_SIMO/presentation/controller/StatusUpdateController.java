package SHINHAN_PORTAL.REPORT_SIMO.presentation.controller;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.TKTTResponseDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.StatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tktt")
public class StatusUpdateController {
	@Autowired
	private StatusUpdateService statusUpdateService;

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