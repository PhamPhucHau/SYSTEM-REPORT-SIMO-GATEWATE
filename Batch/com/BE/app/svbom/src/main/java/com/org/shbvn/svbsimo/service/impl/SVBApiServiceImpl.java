package com.org.shbvn.svbsimo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.model.FileTemplateInfo;
import com.org.shbvn.svbsimo.core.model.SVBRespondOuput;
import com.org.shbvn.svbsimo.core.model.SVBUploadRequest;
import com.org.shbvn.svbsimo.core.model.SVBUploadRequestDaily;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.repository.entities.SimoFileDetailHis;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.org.shbvn.svbsimo.core.common.AuditInfoDTO;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.service.SVBApiService;
import com.org.shbvn.svbsimo.core.utils.TraceNoGenerator;

@Service("svbApiService")
public class SVBApiServiceImpl extends AbstractBankTemplateProcess implements SVBApiService {

    
    @Transactional(readOnly = false, propagation =  Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void uploadFile(SimoFileHis fileHis) throws BaseException {
        //String accessToken = "1234";
       String accessToken = getSvbAccessToken();
       if (accessToken == null || accessToken.isEmpty()) {
           throw new ServiceRuntimeException("Access token is null or empty");
       }
        System.out.println("METHOD:" + APIConstant.SVB_UPLOAD_METHOD);
        AuditInfoDTO.updateLastChangeInfo(fileHis, APIConstant.SYSADM);

        Map<String, Object> inputParams = new HashMap<>();
        List<SimoFileDetailHis> fileDetailHisList = new ArrayList<>();
        List<SimoFileDetailHis> fileDetailHisListToUpload = new ArrayList<>();
        SVBUploadRequest svbUploadRequest = new SVBUploadRequest();
        svbUploadRequest.setFileName(fileHis.getFileName()); 
        svbUploadRequest.setFileType(fileHis.getFileType());
        int maxDataLimit = Integer.parseInt(env.getProperty(APIConstant.SVB_MAX_DATA_LIMIT));
        int totalPage = 1;
        int fetchCount = 10000;
        boolean dataFound = true;
        String fileTamplateJsonStr = simoNamedQueries.get(fileHis.getTemplateCode());
        FileTemplateInfo templateInfo = (FileTemplateInfo) CommonUtils.toPojo(fileTamplateJsonStr, FileTemplateInfo.class);
        while (dataFound) {
            inputParams.put("fileName", fileHis.getFileName());            
            inputParams.put("fileUploadDt", fileHis.getFileUploadDt());
            inputParams.put("page", totalPage);
            inputParams.put("limit", fetchCount);
            inputParams.put("processStatus", "I");
            inputParams.put("templateCode", fileHis.getTemplateCode());
            fileDetailHisList = getRepositoryManageService().getSimoFileDetailHisRepositoryService().fetchByParams(inputParams);
            if (fileDetailHisList == null || fileDetailHisList.isEmpty()) {
                logger.error("File detail not found: " + fileHis.getFileName());
                break;
            }      
            if (fileDetailHisList.size() < fetchCount) {
                dataFound = false;
            }
            for (SimoFileDetailHis e : fileDetailHisList) {
                e.setProcessStatus(APIConstant.FILE_STATEMENT_STATUS_COMPLETE);
                fileDetailHisListToUpload.add(e);
                svbUploadRequest.getDatas().add(e.getFileData());

                if (fileDetailHisListToUpload.size() >= maxDataLimit) {
                    Map<String, String> customHeaders = new HashMap<>();
                    customHeaders.put("maYeuCau", TraceNoGenerator.generateTraceNo());
                    customHeaders.put("kyBaoCao", TraceNoGenerator.getKyBaoCao());
                    logger.info("Before send to SIMO API : " + env.getProperty(APIConstant.SVB_BASE_URL) +templateInfo.getUrlTemplate());
                    SVBRespondOuput svbRespondOuput =callSVBApi(svbUploadRequest, accessToken, env.getProperty(APIConstant.SVB_UPLOAD_METHOD), env.getProperty(APIConstant.SVB_BASE_URL) +templateInfo.getUrlTemplate(), customHeaders );
                    if (svbRespondOuput == null) {
                        logger.error("Error uploading file: " + fileHis.getFileName());
                    }else{
                        logger.info("File uploaded successfully: " + fileHis.getFileName());
                        fileHis.setSuccessCount(fileHis.getSuccessCount() + fileDetailHisListToUpload.size());
                        fileHis.setRemark(svbRespondOuput.toString());
                        fileHis.setRemark(svbRespondOuput.toString());
                    }
                    fileHis.setTotalCount(fileHis.getTotalCount() + fileDetailHisListToUpload.size());
                    fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_COMPLETE);
                    batchFileHisUpdate(fileHis, fileDetailHisListToUpload);
                    fileDetailHisListToUpload.clear();
                    svbUploadRequest.getDatas().clear();
                }
            }
            totalPage+=fetchCount;
        }

        if (!fileDetailHisListToUpload.isEmpty()) {
            for (SimoFileDetailHis e : fileDetailHisListToUpload) {
                e.setProcessStatus(APIConstant.FILE_HIS_DETAIL_STATUS_COMPLETE);
                svbUploadRequest.getDatas().add(e.getFileData());
            }
            Map<String, String> customHeaders = new HashMap<>();
            customHeaders.put("maYeuCau", TraceNoGenerator.generateTraceNo());
            customHeaders.put("kyBaoCao", TraceNoGenerator.getKyBaoCao());
             logger.info("Before send to SIMO API : " + env.getProperty(APIConstant.SVB_BASE_URL) +templateInfo.getUrlTemplate());
            logger.info("METHOD:" + env.getProperty(APIConstant.SVB_UPLOAD_METHOD));
            SVBRespondOuput svbRespondOuput = callSVBApi(svbUploadRequest, accessToken, env.getProperty(APIConstant.SVB_UPLOAD_METHOD), env.getProperty(APIConstant.SVB_BASE_URL) +templateInfo.getUrlTemplate(),customHeaders);
            if (svbRespondOuput == null) {
                logger.error("Error uploading file: " + fileHis.getFileName());
            }else{
                logger.info("File uploaded successfully: " + fileHis.getFileName());
                fileHis.setSuccessCount(fileHis.getSuccessCount() + fileDetailHisListToUpload.size());
                fileHis.setRemark(svbRespondOuput.toString());
                fileHis.setRemark(svbRespondOuput.toString());
            }
            fileHis.setTotalCount(fileHis.getTotalCount() + fileDetailHisListToUpload.size());
            fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_COMPLETE);
            batchFileHisUpdate(fileHis, fileDetailHisListToUpload);
        }

    }

        @Transactional(readOnly = false, propagation =  Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void uploadFileDaily(SimoFileHis fileHis) throws BaseException {
     //   String accessToken = "1234";
       String accessToken = getSvbAccessToken();
       if (accessToken == null || accessToken.isEmpty()) {
           throw new ServiceRuntimeException("Access token is null or empty");
       }
        System.out.println("METHOD:" + APIConstant.SVB_UPLOAD_METHOD);
        AuditInfoDTO.updateLastChangeInfo(fileHis, APIConstant.SYSADM);

        Map<String, Object> inputParams = new HashMap<>();
        List<SimoFileDetailHis> fileDetailHisList = new ArrayList<>();
        List<SimoFileDetailHis> fileDetailHisListToUpload = new ArrayList<>();
        SVBUploadRequestDaily svbUploadRequestDaily = new SVBUploadRequestDaily();
        svbUploadRequestDaily.setFileName(fileHis.getFileName()); 
        svbUploadRequestDaily.setFileType(fileHis.getFileType());
        int maxDataLimit = Integer.parseInt(env.getProperty(APIConstant.SVB_MAX_DATA_LIMIT));
        int totalPage = 1;
        int fetchCount = 10000;
        String fileTamplateJsonStr = simoNamedQueries.get(fileHis.getTemplateCode());
        FileTemplateInfo templateInfo = (FileTemplateInfo) CommonUtils.toPojo(fileTamplateJsonStr, FileTemplateInfo.class);
        
            inputParams.put("fileName", fileHis.getFileName());            
            inputParams.put("fileUploadDt", fileHis.getFileUploadDt());
            inputParams.put("page", totalPage);
            inputParams.put("limit", fetchCount);
            inputParams.put("processStatus", "I");
            inputParams.put("templateCode", fileHis.getTemplateCode());
            fileDetailHisList = getRepositoryManageService().getSimoFileDetailHisRepositoryService().fetchByParams(inputParams);
            if (fileDetailHisList == null || fileDetailHisList.isEmpty()) {
                logger.error("File detail not found: " + fileHis.getFileName());
            }      
            for (SimoFileDetailHis e : fileDetailHisList) {
                e.setProcessStatus(APIConstant.FILE_STATEMENT_HIS_PROCESS_STATUS);
                fileDetailHisListToUpload.add(e);
                svbUploadRequestDaily.setDatas(e.getFileData());
                Map<String, String> customHeaders = new HashMap<>();
                customHeaders.put("maYeuCau", TraceNoGenerator.generateTraceNo());
            logger.info("Before send to SIMO API : " + env.getProperty(APIConstant.SVB_BASE_URL) +templateInfo.getUrlTemplate());
            logger.info("METHOD:" + env.getProperty(APIConstant.SVB_UPLOAD_METHOD));
            SVBRespondOuput svbRespondOuput = callSVBApi(svbUploadRequestDaily, accessToken, env.getProperty(APIConstant.SVB_UPLOAD_METHOD), env.getProperty(APIConstant.SVB_BASE_URL) +templateInfo.getUrlTemplate(),customHeaders);
            if (svbRespondOuput == null) {
                logger.error("Error uploading file: " + fileHis.getFileName());
            }else{
                logger.info("File uploaded successfully: " + fileHis.getFileName());
                fileHis.setSuccessCount(fileHis.getSuccessCount() + fileDetailHisListToUpload.size());
                fileHis.setRemark(svbRespondOuput.toString());
                fileHis.setRemark(svbRespondOuput.toString());
            }
            
            fileHis.setTotalCount(fileHis.getTotalCount() + fileDetailHisListToUpload.size());
            fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_COMPLETE);
            batchFileHisUpdate(fileHis, fileDetailHisListToUpload);
            }
        }



        

    }

