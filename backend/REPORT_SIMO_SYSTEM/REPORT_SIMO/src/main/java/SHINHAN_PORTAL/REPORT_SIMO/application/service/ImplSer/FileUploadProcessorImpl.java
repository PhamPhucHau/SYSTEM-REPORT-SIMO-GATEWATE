package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import SHINHAN_PORTAL.REPORT_SIMO.application.dto.FileUploadRequestDTO;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.FileUploadProcessor;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.HistoryService;
import SHINHAN_PORTAL.REPORT_SIMO.application.service.TemplateProcessor;
import SHINHAN_PORTAL.REPORT_SIMO.domain.entity.LIST_FILE_UPLOAD;
import SHINHAN_PORTAL.REPORT_SIMO.domain.enums.TemplateID;
@Service
public class FileUploadProcessorImpl implements FileUploadProcessor {

    // Logger để ghi log thông tin

private static final Logger logger = LoggerFactory.getLogger(FileUploadProcessor.class);

    @Autowired
    private HistoryService historyService;

    // @Autowired
    // private API_1_6_Processor api_1_6_processor;
    // @Autowired
    // private API_1_7_Processor api_1_7_processor;
    // @Autowired
    // private API_1_8_Processor api_1_8_processor;
    // @Autowired
    // private API_1_9_Processor api_1_9_processor;
    // Thêm các processor khác
    private final Map<TemplateID, TemplateProcessor> templateProcessors;
    
	public FileUploadProcessorImpl(
        API_1_6_Processor api_1_6_processor,
        API_1_7_Processor api_1_7_processor,
        API_1_8_Processor api_1_8_processor,
        API_1_9_Processor api_1_9_processor,
        API_1_23_Processor api_1_23_processor,
        API_1_24_Processor api_1_24_processor,
        API_1_25_Processor api_1_25_processor,
        API_1_26_Processor api_1_26_processor,
        API_1_27_Processor api_1_27_processor,
        API_1_28_Processor api_1_28_processor,
        API_1_29_Processor api_1_29_processor,
		API_1_30_Processor api_1_30_processor,
		API_1_31_Processor api_1_31_processor,
		API_1_32_Processor api_1_32_processor,
		API_1_33_Processor api_1_33_processor,
		API_1_34_Processor api_1_34_processor
    ) {
        this.templateProcessors = new HashMap<>();
    
        // Existing mappings
        this.templateProcessors.put(TemplateID.API_1_6_TTDS_TKTT_DK, api_1_6_processor);
        this.templateProcessors.put(TemplateID.API_1_7_TTDS_TKTT_NNGL, api_1_7_processor);
        this.templateProcessors.put(TemplateID.API_1_8_UPDATE_TTDS_TKTT_NNGL, api_1_8_processor);
        this.templateProcessors.put(TemplateID.API_1_9_UPDATE_TTDS_TKTT_DK, api_1_9_processor);
        this.templateProcessors.put(TemplateID.API_1_23_TTDS_TKTT_TC_DK, api_1_23_processor);
        this.templateProcessors.put(TemplateID.API_1_24_TTDS_TKTT_TC_NGGL, api_1_24_processor);
        this.templateProcessors.put(TemplateID.API_1_25_UPDATE_TTDS_TKTT_TC_NGGL, api_1_25_processor);
        this.templateProcessors.put(TemplateID.API_1_26_UPDATE_TTDS_TKTT_TC, api_1_26_processor);
    
		// New mappings
        this.templateProcessors.put(TemplateID.API_1_27_TT_DVCNTT, api_1_27_processor);
        this.templateProcessors.put(TemplateID.API_1_28_TT_DVCNTT_NGGL, api_1_28_processor);
        this.templateProcessors.put(TemplateID.API_1_29_UPDATE_DVCNTT_NGGL, api_1_29_processor);
        this.templateProcessors.put(TemplateID.API_1_30_UPDATE_DVCNTT, api_1_30_processor);
		this.templateProcessors.put(TemplateID.API_1_31_TT_TNH, api_1_31_processor);
		this.templateProcessors.put(TemplateID.API_1_32_TT_TNH_NGGL, api_1_32_processor);
		this.templateProcessors.put(TemplateID.API_1_33_UPDATE_TNH_NGGL, api_1_33_processor);
		this.templateProcessors.put(TemplateID.API_1_34_UPDATE_TNH, api_1_34_processor);
    }
    @Override
    public LIST_FILE_UPLOAD processUpload(FileUploadRequestDTO request) {
        LIST_FILE_UPLOAD fileUpload = new LIST_FILE_UPLOAD();
        fileUpload.setTemplateID(request.getTemplateID());
        fileUpload.setTemplateName(request.getTemplateName());
        fileUpload.setMonthYear(request.getMonthYear());
        fileUpload.setTotal_record(request.getTotal_record());
        fileUpload.setUserId(request.getUserId());
        fileUpload.setUsername(request.getUsername());
        fileUpload.setFileName(request.getFileName());
        fileUpload.setFileType(request.getFileType());
        fileUpload.setData(request.getData());
        fileUpload.setData_ledg_s("00");
        return historyService.saveUploadHistory(fileUpload);
    }
    @Override
    public LIST_FILE_UPLOAD processConfirm(String id, String status) {
        LIST_FILE_UPLOAD fileUpload = historyService.findById(id, status);
        TemplateID templateID = TemplateID.fromString(fileUpload.getTemplateID());
        TemplateProcessor processor = templateProcessors.getOrDefault(templateID, (f, data) -> {
            throw new IllegalArgumentException("Unsupported templateID: " + fileUpload.getTemplateID());
        });
        processor.process(fileUpload, fileUpload.getData());
            return historyService.updateById(id, "10");
    }
    @Override
    public Page<LIST_FILE_UPLOAD> getFiles(String templateID, String monthYear, String username, int page, int size) {
        return historyService.getFiles(templateID, monthYear, username, page, size);
    }

    /**
     * Get merged data from multiple files for batch approval
     * SRP: This method is responsible for aggregating data from multiple files
     * OCP: Can be extended to add validation or filtering logic without modifying existing code
     * 
     * @param fileIds List of file IDs to retrieve
     * @param fileNames List of file names (for validation/logging)
     * @param templateID Template ID to validate against
     * @param monthYear Month/Year to validate against
     * @return Merged list of all data from the specified files
     */
    @Override
    public List<Map<String, Object>> getFileDetail(List<String> fileIds, List<String> fileNames, String templateID, String monthYear) {
        logger.info("Getting file details for {} files with templateID: {} and monthYear: {}", 
                    fileIds.size(), templateID, monthYear);
        
        // Query files with status "10" (confirmed/ready to send)
        List<LIST_FILE_UPLOAD> files = historyService.findByIdsAndStatus(fileIds, "10");
        
        // Validate that all files were found
        if (files.size() != fileIds.size()) {
            logger.warn("Expected {} files but found {} files with status '10'", fileIds.size(), files.size());
        }
        
        // Validate templateID and monthYear match
        List<LIST_FILE_UPLOAD> invalidFiles = files.stream()
            .filter(file -> !file.getTemplateID().equals(templateID) || !file.getMonthYear().equals(monthYear))
            .collect(Collectors.toList());
        
        if (!invalidFiles.isEmpty()) {
            logger.error("Found {} files with mismatched templateID or monthYear", invalidFiles.size());
            throw new IllegalArgumentException("Some files have mismatched templateID or monthYear:" + templateID + "-"+monthYear);
        }
        
        // Merge all data from all files into a single list
        List<Map<String, Object>> mergedData = new ArrayList<>();
        for (LIST_FILE_UPLOAD file : files) {
            if (file.getData() != null && !file.getData().isEmpty()) {
                mergedData.addAll(file.getData());
                logger.debug("Added {} records from file: {}", file.getData().size(), file.getFileName());
            }
        }
        
        logger.info("Successfully merged data from {} files, total records: {}", files.size(), mergedData.size());
        return mergedData;
    }
}
