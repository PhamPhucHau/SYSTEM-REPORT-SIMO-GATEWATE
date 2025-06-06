package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HistoryService {
   LIST_FILE_UPLOAD saveUploadHistory(LIST_FILE_UPLOAD requestBody) ;
   public Page<LIST_FILE_UPLOAD> getFiles(String templateID, String monthYear, int page, int size);
}
