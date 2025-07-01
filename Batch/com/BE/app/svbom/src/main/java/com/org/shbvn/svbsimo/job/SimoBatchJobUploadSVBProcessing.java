package com.org.shbvn.svbsimo.job;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.org.shbvn.svbsimo.core.common.AbstractBasicCommonClass;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;

@Component
public class SimoBatchJobUploadSVBProcessing extends AbstractBasicCommonClass{
    
    @Scheduled(cron = "${spring.job.application.cron.processSimoUploadSVB}") 
    public void processSimoUploadSVB() {
        logger.info("Start Upload SVB");

        List<SimoFileHis> fileHisList = null;
        try {                fileHisList = getRepositoryManageService().getSimoFileHisRepositoryService().getByFileStatusAndUploadDt(APIConstant.FILE_STATEMENT_STATUS_APPROVE, DateUtils.getSystemDateStr(DateUtils.yyyyMMdd));
        } catch (BaseException e) {
            logger.error("Error getting file history", e);
        }
        
        if (fileHisList == null || fileHisList.isEmpty()) {
            logger.info("No file to process");
            return;
        }
        
        for (SimoFileHis fileHis : fileHisList) {
            logger.info("Start Processing File name: " + fileHis.getFileName());
            
            try {
                switch(fileHis.getFileType())
                {
                    case "1":
                    {
                        getProcessManagerService().getSVBApiService().uploadFile(fileHis);
                        break;
                    }
                    case "2":
                    {
                        getProcessManagerService().getSVBApiService().uploadFileDaily(fileHis);
                        break;
                    }
                }
                //getProcessManagerService().getSVBApiService().uploadFile(fileHis);
            } catch (BaseException e) {
                logger.error("Error uploading file", e);
            }

            logger.info("End Processing File name: " + fileHis.getFileName());
        }
        
        logger.info("End Upload SVB");
    }
}
