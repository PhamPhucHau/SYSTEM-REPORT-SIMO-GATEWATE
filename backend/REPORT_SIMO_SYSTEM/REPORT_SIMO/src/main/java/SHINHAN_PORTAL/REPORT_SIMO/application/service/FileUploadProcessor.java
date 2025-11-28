package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import org.springframework.data.domain.Page;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.FileUploadRequestDTO;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;

import java.util.List;
import java.util.Map;

public interface FileUploadProcessor {
public LIST_FILE_UPLOAD processUpload(FileUploadRequestDTO request) ;
public LIST_FILE_UPLOAD processConfirm(String id, String status);
public Page<LIST_FILE_UPLOAD> getFiles(String templateID, String monthYear, String username, int page, int size);
// Get merged data from multiple files for batch approval
public List<Map<String, Object>> getFileDetail(List<String> fileIds, List<String> fileNames, String templateID, String monthYear);

}
