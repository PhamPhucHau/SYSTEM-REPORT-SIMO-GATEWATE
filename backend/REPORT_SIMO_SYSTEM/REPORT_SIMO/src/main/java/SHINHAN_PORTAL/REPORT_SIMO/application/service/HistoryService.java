package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HistoryService {
   LIST_FILE_UPLOAD saveUploadHistory(LIST_FILE_UPLOAD requestBody) ;
   public Page<LIST_FILE_UPLOAD> getFiles(String templateID, String monthYear,String username, int page, int size);
   LIST_FILE_UPLOAD updateById(String id, String data_ledg_s );
   LIST_FILE_UPLOAD findById(String id, String data_ledg_s );
   // Find multiple files by IDs and status for batch processing
   List<LIST_FILE_UPLOAD> findByIdsAndStatus(List<String> ids, String status);
}
