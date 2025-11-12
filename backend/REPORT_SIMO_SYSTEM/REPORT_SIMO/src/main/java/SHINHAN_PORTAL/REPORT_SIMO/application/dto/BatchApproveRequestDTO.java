package SHINHAN_PORTAL.REPORT_SIMO.application.dto;

import java.util.List;

/**
 * DTO for batch approve request
 * SRP: Single responsibility - only holds data for batch approval request
 */
public class BatchApproveRequestDTO {
    private List<String> fileIds;
    private List<String> fileNames;

    // Getters and Setters
    public List<String> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}

