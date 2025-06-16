package SHINHAN_PORTAL.REPORT_SIMO.application.service;

import org.springframework.data.domain.Page;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.FileUploadRequestDTO;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;

public interface FileUploadProcessor {
public LIST_FILE_UPLOAD processUpload(FileUploadRequestDTO request) ;
public LIST_FILE_UPLOAD processConfirm(String id);
public Page<LIST_FILE_UPLOAD> getFiles(String templateID, String monthYear, String username, int page, int size);
}
