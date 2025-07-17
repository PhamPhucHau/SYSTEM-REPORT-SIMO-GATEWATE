package SHINHAN_PORTAL.REPORT_SIMO.application.service.ImplSer;

import java.util.HashMap;
import java.util.Map;

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
        API_1_27_Processor api_1_27_processor,
        API_1_28_Processor api_1_28_processor,
        API_1_29_Processor api_1_29_processor,
        API_1_30_Processor api_1_30_processor
    ) {
        this.templateProcessors = new HashMap<>();
    
        // Existing mappings
        this.templateProcessors.put(TemplateID.API_1_6_TTDS_TKTT_DK, api_1_6_processor);
        this.templateProcessors.put(TemplateID.API_1_7_TTDS_TKTT_NNGL, api_1_7_processor);
        this.templateProcessors.put(TemplateID.API_1_8_UPDATE_TTDS_TKTT_NNGL, api_1_8_processor);
        this.templateProcessors.put(TemplateID.API_1_9_UPDATE_TTDS_TKTT_DK, api_1_9_processor);
    
        // New mappings
        this.templateProcessors.put(TemplateID.API_1_27_TT_DVCNTT, api_1_27_processor);
        this.templateProcessors.put(TemplateID.API_1_28_TT_DVCNTT_NGGL, api_1_28_processor);
        this.templateProcessors.put(TemplateID.API_1_29_UPDATE_DVCNTT_NGGL, api_1_29_processor);
        this.templateProcessors.put(TemplateID.API_1_30_UPDATE_DVCNTT, api_1_30_processor);
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
    public LIST_FILE_UPLOAD processConfirm(String id) {
        LIST_FILE_UPLOAD fileUpload = historyService.findById(id, "00");
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
}
