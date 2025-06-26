package com.org.shbvn.svbsimo.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.org.shbvn.svbsimo.core.common.AuditInfoDTO;
import com.org.shbvn.svbsimo.core.common.ExcelDTO;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.model.FileTemplateInfo;
import com.org.shbvn.svbsimo.core.model.TkdbtttainhhtItem;
import com.org.shbvn.svbsimo.core.model.API16TTDSTKTTDKTemplate;
import com.org.shbvn.svbsimo.core.model.BankCommonTemplate;
import com.org.shbvn.svbsimo.core.model.DailyCommonTemplate;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.core.utils.EntityModelConverter;
import com.org.shbvn.svbsimo.repository.entities.SimoFileDetailHis;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;
import com.org.shbvn.svbsimo.service.BankTemplateAccountInfo16Process;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;

@Service("bankTemplateAccountInfo16Process")
public class BankTemplateAccountInfo16ProcessImpl extends AbstractBankTemplateProcess implements BankTemplateAccountInfo16Process{
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void process(File file) throws BaseException {
        String fileUploadDate = DateUtils.getSystemDateStr(DateUtils.yyyyMMdd);
        FileTemplateInfo templateInfo = verifyFileTemplate(file);
        logger.info("File template found {}" , CommonUtils.toJson(templateInfo));
        List<API16TTDSTKTTDKTemplate> bankStatements = processBankTemplate(file, templateInfo, API16TTDSTKTTDKTemplate.class);
        logger.info("Bank statements found {}" , CommonUtils.toJson(bankStatements));
        if(bankStatements.isEmpty()) {
            return;
        }
        SimoFileHis fileHis = getRepositoryManageService().getSimoFileHisRepositoryService().getByFileNameAndUploadDt(file.getName(), fileUploadDate);
        if (fileHis == null) {
            throw new ServiceRuntimeException("File not found in history " + file.getName());
        }
        
        fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_PROCESS);
        fileHis.setTotalCount(bankStatements.size());
  

        List<SimoFileDetailHis> fileDetailHisList = bankStatements.stream().map(e -> {
            SimoFileDetailHis fileDetailHis = EntityModelConverter.toModel(e, SimoFileDetailHis.class);
            fileDetailHis.setFileName(file.getName());
            fileDetailHis.setFileUploadDt(fileUploadDate);
            fileDetailHis.setTemplateCode(templateInfo.getTemplateCode());
            fileDetailHis.setFileType(fileHis.getFileType());
            fileDetailHis.setProcessStatus("I");
            AuditInfoDTO.setCreationInfo(fileDetailHis, APIConstant.SYSADM);
            try {
                fileDetailHis.setFileData(CommonUtils.toJson(e));
            } catch (ServiceRuntimeException ex) {
                logger.error("Error converting to JSON", ex);
                // Either set a default value or rethrow as a runtime exception
                fileDetailHis.setFileData("{}");
            }
            return fileDetailHis;
        }).toList();
        fileHis.setSuccessCount(fileDetailHisList.size());
        AuditInfoDTO.updateLastChangeInfo(fileHis, APIConstant.SYSADM);
        logger.info("File detail history list found {}" , CommonUtils.toJson(fileDetailHisList));
        getRepositoryManageService().getSimoFileDetailHisRepositoryService().createAll(fileDetailHisList);
         
    }
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public <T extends BankCommonTemplate> void process(File file, T extend) throws BaseException {
        process(file,extend ,(Class<T>) extend.getClass());
    }
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public <T extends DailyCommonTemplate> void process(File file, T extend) throws BaseException {
        process(file,extend ,(Class<T>) extend.getClass());
    }
    private <T extends BankCommonTemplate> void process(File file, T extend, Class<T> clazz) throws BaseException {
        String fileUploadDate = DateUtils.getSystemDateStr(DateUtils.yyyyMMdd);
        FileTemplateInfo templateInfo = verifyFileTemplate(file);
        logger.info("File template found {}" , CommonUtils.toJson(templateInfo));
        List<T> bankStatements = processBankTemplate(file, templateInfo, clazz);
        logger.info("Bank statements found {}" , CommonUtils.toJson(bankStatements));
        if(bankStatements.isEmpty()) {
            logger.info("Bank statements is Empty" );
            return;
        }
        SimoFileHis fileHis = getRepositoryManageService().getSimoFileHisRepositoryService().getByFileNameAndUploadDt(file.getName(), fileUploadDate);
        if (fileHis == null) {
            throw new ServiceRuntimeException("File not found in history " + file.getName());
        }
        
        fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_PROCESS);
         fileHis.setTotalCount(bankStatements.size());
        AuditInfoDTO.updateLastChangeInfo(fileHis, APIConstant.SYSADM);

        List<SimoFileDetailHis> fileDetailHisList = bankStatements.stream().map(e -> {
            SimoFileDetailHis fileDetailHis = EntityModelConverter.toModel(e, SimoFileDetailHis.class);
            fileDetailHis.setFileName(file.getName());
            fileDetailHis.setFileUploadDt(fileUploadDate);
            fileDetailHis.setTemplateCode(templateInfo.getTemplateCode());
            fileDetailHis.setFileType(fileHis.getFileType());
            fileDetailHis.setProcessStatus("I");
            AuditInfoDTO.setCreationInfo(fileDetailHis, APIConstant.SYSADM);
            try {
                fileDetailHis.setFileData(CommonUtils.toJson(e));
            } catch (ServiceRuntimeException ex) {
                logger.error("Error converting to JSON", ex);
                // Either set a default value or rethrow as a runtime exception
                fileDetailHis.setFileData("{}");
            }
            return fileDetailHis;
        }).toList();
        logger.info("File detail history list found {}" , CommonUtils.toJson(fileDetailHisList));
        getRepositoryManageService().getSimoFileDetailHisRepositoryService().createAll(fileDetailHisList);
    }
     private <T extends DailyCommonTemplate> void process(File file, T extend, Class<T> clazz) throws BaseException {
        String fileUploadDate = DateUtils.getSystemDateStr(DateUtils.yyyyMMdd);
        FileTemplateInfo templateInfo = verifyFileTemplate(file);
        logger.info("File template found {}" , CommonUtils.toJson(templateInfo));
        //T bankStatements = processDailyTemplate(file, templateInfo, clazz,"daily");
            // Call modified processDailyTemplate
            Field[] childFields = CommonUtils.getAllDeclaredFields(TkdbtttainhhtItem.class).toArray(new Field[0]);
    Map<String, Entry<String, UnaryOperator<String>>> childMapping =
        ExcelDTO.buildDailyTemplateMapping(Arrays.asList(templateInfo.getTemplateColName().split(",")), childFields);

    T bankStatement = processDailyTemplate(
        file,
        templateInfo,
        clazz,
        TkdbtttainhhtItem.class,
        childMapping,
        (parent, list) -> parent.setTkdbttTaiNHHT((List<TkdbtttainhhtItem>) list) // cast đúng kiểu
    );
        logger.info("Bank statements found {}" , CommonUtils.toJson(bankStatement));
        if(bankStatement == null ) {
            logger.info("Bank statements is Empty" );
            return;
        }
        SimoFileHis fileHis = getRepositoryManageService().getSimoFileHisRepositoryService().getByFileNameAndUploadDt(file.getName(), fileUploadDate);
        if (fileHis == null) {
            throw new ServiceRuntimeException("File not found in history " + file.getName());
        }
        
        fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_PROCESS);
        AuditInfoDTO.updateLastChangeInfo(fileHis, APIConstant.SYSADM);
        SimoFileDetailHis fileDetailHis = EntityModelConverter.toModel("", SimoFileDetailHis.class);
        fileDetailHis.setFileName(file.getName());
        fileDetailHis.setFileUploadDt(fileUploadDate);
        fileDetailHis.setTemplateCode(templateInfo.getTemplateCode());
        fileDetailHis.setFileType(fileHis.getFileType());
        fileDetailHis.setProcessStatus("I");
        fileDetailHis.setRowNumber(
            bankStatement.getThoigianguibaocao() // Set row number based on index
        );
        fileDetailHis.setCif(file.getName());
        AuditInfoDTO.setCreationInfo(fileDetailHis, APIConstant.SYSADM);
        try {
            fileDetailHis.setFileData(CommonUtils.toJson(bankStatement)); // serialize cả object cha
        } catch (ServiceRuntimeException ex) {
            logger.error("Error converting to JSON", ex);
            fileDetailHis.setFileData("{}");
        }
    //     List<SimoFileDetailHis> fileDetailHisList = bankStatement.getTkdbttTaiNHHT().stream().map(e -> {
    //     SimoFileDetailHis fileDetailHis = EntityModelConverter.toModel(e, SimoFileDetailHis.class);
    //     fileDetailHis.setFileName(file.getName());
    //     fileDetailHis.setFileUploadDt(fileUploadDate);
    //     fileDetailHis.setTemplateCode(templateInfo.getTemplateCode());
    //     fileDetailHis.setFileType(fileHis.getFileType());
    //     fileDetailHis.setProcessStatus("I");
    //     fileDetailHis.setRowNumber(
    //         bankStatement.getThoigianguibaocao() // Set row number based on index
    //     );
    //     fileDetailHis.setCif(file.getName());
    //     AuditInfoDTO.setCreationInfo(fileDetailHis, APIConstant.SYSADM);
    //     try {
    //         fileDetailHis.setFileData(CommonUtils.toJson(bankStatement)); // serialize cả object cha
    //     } catch (ServiceRuntimeException ex) {
    //         logger.error("Error converting to JSON", ex);
    //         fileDetailHis.setFileData("{}");
    //     }
    //     return fileDetailHis;
    // }).toList();
    List<SimoFileDetailHis> fileDetailHisList = List.of(fileDetailHis);
        fileHis.setSuccessCount(fileDetailHisList.size());
        if(templateInfo.getAutoAppr() == 1)
        {
            fileHis.setFileStatus(APIConstant.FILE_STATEMENT_STATUS_APPROVE);
        }
        AuditInfoDTO.updateLastChangeInfo(fileHis, APIConstant.SYSADM);
        logger.info("File detail history list found {}" , CommonUtils.toJson(fileDetailHisList));
        getRepositoryManageService().getSimoFileDetailHisRepositoryService().createAll(fileDetailHisList);
    }
    

    private FileTemplateInfo verifyFileTemplate(File file) throws BaseException {
        String fileTamplateCode = file.getName().subSequence(0, 16).toString();
        TMetadata tMetadata = getRepositoryManageService().getUtilityManagerRepositoryService().getMetadataByLookupCodeAndId(APIConstant.LOOKUP_CODE_FILE_TEMPLATE_CODE, fileTamplateCode);
        if(tMetadata == null) {
            throw new ServiceRuntimeException("File template not found " + fileTamplateCode);
        }
        String fileTamplateJsonStr = simoNamedQueries.get(fileTamplateCode);
        FileTemplateInfo templateInfo = (FileTemplateInfo) CommonUtils.toPojo(fileTamplateJsonStr, FileTemplateInfo.class);
        if (templateInfo == null) {
            templateInfo = new FileTemplateInfo();
        }
        return templateInfo;
    }

    

}
