package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import SHINHAN_PORTAL.REPORT_SIMO.application.service.StatusUpdateService;
import SHINHAN_PORTAL.REPORT_SIMO.domain.repository.API_1_6_tktt_dinh_ky_Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusUpdateServiceImpl implements StatusUpdateService {
	@Autowired
	private API_1_6_tktt_dinh_ky_Util api16Util;

	@Override
	public long updateStatus(String templateID, String monthYear, String oldStatus, String newStatus) {
		String template = templateID == null ? "" : templateID.toUpperCase();
		return api16Util.updateStatus(template, monthYear, oldStatus, newStatus);
		// switch (template) {
		// 	case "API_1_6_TTDS_TKTT_DK":
		// 		return api16Util.updateStatus(template, monthYear, oldStatus, newStatus);
		// 	default:
		// 		throw new IllegalArgumentException("Unsupported templateID: " + templateID);
		// }
	}
} 