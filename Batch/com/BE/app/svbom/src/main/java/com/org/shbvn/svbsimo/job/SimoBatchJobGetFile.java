package com.org.shbvn.svbsimo.job;

import java.io.File;
import java.util.Collection;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.org.shbvn.svbsimo.core.common.AuditInfoDTO;
import com.org.shbvn.svbsimo.core.common.AbstractBasicCommonClass;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.core.utils.FtpUtils;
import com.org.shbvn.svbsimo.core.utils.NFsUtils;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;

@Component
public class SimoBatchJobGetFile extends AbstractBasicCommonClass{
    
     @Scheduled(cron = "${spring.job.application.cron.scanSimoFile}") 
    public void scanSimoFile() {
        logger.info("***** Start Scan Simo File To Process Task :: Execution Time - {} *****" , DateUtils.getSystemDateStr(DateUtils.ddMMyyyy_HH_mm_ss));
        
        // try {
        //     NFsUtils.downloadViaNFS(env.getProperty(APIConstant.nfs_client_host) , 
        //                         env.getProperty(APIConstant.nfs_client_username), 
        //                         env.getProperty(APIConstant.nfs_client_password), 
        //                         env.getProperty(APIConstant.nfs_client_remotedirectory) , 
        //                         env.getProperty(APIConstant.PATH_SCAN_SIMO_FTP_FOLDER) );

        // } catch (Exception e) {
        //     logger.error("Error downloading file from NFS", e);
        // }
        // Change NFS to FTP
        try {
    FtpUtils.downloadAllFilesAndArchive(
        env.getProperty(APIConstant.ftp_server_url),
        Integer.parseInt(env.getProperty(APIConstant.ftp_server_port)),
        env.getProperty(APIConstant.ftp_username),
        env.getProperty(APIConstant.ftp_password),
        env.getProperty(APIConstant.ftp_remote_directory),
        env.getProperty(APIConstant.ftp_local_download_directory)
    );
} catch (Exception e) {
    logger.error("Error downloading file from FTP", e);
}
        logger.info("Scan Simo File In Folder {}" , env.getProperty(APIConstant.PATH_SCAN_SIMO_FTP_FOLDER));

        Collection<File> files = CommonUtils.getFilesInFolder(env.getProperty(APIConstant.PATH_SCAN_SIMO_FTP_FOLDER));
        for (File file : files) {
            logger.info("Start Moving File name: " + file.getName());

            try {
                SimoFileHis fileHis = new SimoFileHis();
                fileHis.setFileName(file.getName());
                fileHis.setFileUploadDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMdd));
                fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_UPLOAD);
                fileHis.setProcessStatus("I");
                fileHis.setFileType("1");
                fileHis.setTemplateCode(file.getName().subSequence(0, 16).toString());
                AuditInfoDTO.setCreationInfo(fileHis, APIConstant.SYSADM);
                getRepositoryManageService().getSimoFileHisRepositoryService().create(fileHis);
                
                CommonUtils.moveFileToDirectory(file, new File(env.getProperty(APIConstant.PATH_SCAN_SIMO_PROCESS_FOLDER)));
                
            } catch (Exception e) {
                logger.error("Error Moving file to Processing folder", e);
            }
            logger.info("End Moving File name: " + file.getName());
            
        }

        logger.info("***** End Scan Simo File To Process Task :: Execution Time - {} *****" , DateUtils.getSystemDateStr(DateUtils.ddMMyyyy_HH_mm_ss));
    }

}
