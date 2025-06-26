package com.org.shbvn.svbsimo.job;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.org.shbvn.svbsimo.core.common.AbstractBasicCommonClass;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.model.API17TTDSTKTTNNGLTemplate;
import com.org.shbvn.svbsimo.core.model.API18UPDATETTDSTKTTNNGLTemplate;
import com.org.shbvn.svbsimo.core.model.API19TTTKINSTTemplate;
import com.org.shbvn.svbsimo.core.model.FileTemplateInfo;
import com.org.shbvn.svbsimo.core.model.API122WDRTemplate;
import com.org.shbvn.svbsimo.core.model.API16TTDSTKTTDKTemplate;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;

@Component
public class SimoBatchJobProcessingFile extends AbstractBasicCommonClass{
    
    @Scheduled(cron = "${spring.job.application.cron.processSimoFile}") 
    public void processSimoFile() {
        logger.info("***** Start Processing Simo File Task :: Execution Time - {} *****" , DateUtils.getSystemDateStr(DateUtils.ddMMyyyy_HH_mm_ss));
        try {
            List<SimoFileHis> fileHisList = getRepositoryManageService().getSimoFileHisRepositoryService().getByFileStatusAndUploadDt(APIConstant.FILE_STATEMENT_STATUS_UPLOAD, DateUtils.getSystemDateStr(DateUtils.yyyyMMdd));
            if (fileHisList == null || fileHisList.isEmpty()) {
                logger.info("No file to process");
                return;
            }
            for (SimoFileHis fileHis : fileHisList) {
                logger.info("Start Processing File name: " + fileHis.getFileName());
                File file = new File(env.getProperty(APIConstant.PATH_SCAN_SIMO_PROCESS_FOLDER) + "/" + fileHis.getFileName()); 
                if (!file.exists()) {
                    logger.error("File not found: " + fileHis.getFileName());
                    continue;
                }
                execute(fileHis.getTemplateCode(), file);
                logger.info("End Processing File name: " + fileHis.getFileName());
            }
        } catch (Exception e) {
            logger.error("Error processing file", e);
        }
        logger.info("***** End Processing Simo File Task :: Execution Time - {} *****" , DateUtils.getSystemDateStr(DateUtils.ddMMyyyy_HH_mm_ss));
    }


    private void execute(String templateCode, File file) throws BaseException{
        logger.info("@@ {} {}", APIConstant.LOOKUP_CODE_FILE_TEMPLATE_CODE, templateCode);

        String fileTamplateJsonStr =  simoNamedQueries.get(templateCode);
        if(fileTamplateJsonStr == null || fileTamplateJsonStr.isEmpty()) {
            throw new ServiceRuntimeException("File template not found " + templateCode);
        }
        FileTemplateInfo templateInfo = (FileTemplateInfo) CommonUtils.toPojo(fileTamplateJsonStr, FileTemplateInfo.class);
        
        Collection<File> files 
        
        
        = CommonUtils.getFilesInFolder(env.getProperty(APIConstant.PATH_SCAN_SIMO_PROCESS_FOLDER));
        for (File filetmp : files) {
            if(filetmp.getName().startsWith(templateCode)) {
                templateInfo.setFileCnt(templateInfo.getFileCnt() -1 );
            }
        }
        if(templateInfo.getFileCnt() > 0) {
            throw new ServiceRuntimeException("File not enough to process " + templateCode);
        }

        switch (templateCode) {
            case APIConstant.TEMPLATE_CODE_INST_API1_6_TTTK:
                getProcessManagerService().getBankTemplateAccountInfo16Process().process(file, new API16TTDSTKTTDKTemplate());
                break;
            case APIConstant.TEMPLATE_CODE_INST_API1_9_TTTK:
                getProcessManagerService().getBankTemplateAccountInfo16Process().process(file, new API19TTTKINSTTemplate());
                break;
                case APIConstant.TEMPLATE_CODE_INST_API1_7_NGGL:
                getProcessManagerService().getBankTemplateAccountInfo16Process().process(file, new API17TTDSTKTTNNGLTemplate());
                break;
                 case APIConstant.TEMPLATE_CODE_INST_API1_8_NGGL:
                getProcessManagerService().getBankTemplateAccountInfo16Process().process(file, new API18UPDATETTDSTKTTNNGLTemplate());
                break;
                case APIConstant.TEMPLATE_CODE_INST_API1_22_WDR:
                getProcessManagerService().getBankTemplateAccountInfo16Process().process(file, new API122WDRTemplate());
                break;
            default:
                throw new ServiceRuntimeException("Template code not support");
        }
        CommonUtils.moveFileToDirectory(file, new File(env.getProperty(APIConstant.PATH_SCAN_SIMO_DONE_FOLDER)));
    }
}
