package com.org.shbvn.svbsimo.repository.services.impl;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.SimoFileMas;
import com.org.shbvn.svbsimo.repository.services.FileMasService ;
import com.org.shbvn.svbsimo.repository.services.SimoFileMasManagerRepositoryService; 
@Service
public class FileMasServiceImpl implements FileMasService  {

    //private final SimoFileMasManagerRepositoryService simoFileMasManagerRepositoryService;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileMasServiceImpl.class);

    // Constructor to inject the dependency
    @Autowired
    SimoFileMasManagerRepositoryService simoFileMasManagerRepositoryService;

    @Override
    public List<SimoFileMas> getListUploadByDateAndTemplateId(String uploadDateStart,String uploadDateEnd, String templateCode,String fileName, String fileStatus) {
        // Implementation logic to retrieve the list of uploaded files by date and template ID
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", fileName);                         // Optional
        params.put("fileUploadDt_", uploadDateStart);              // Từ ngày
        params.put("fileUploadDtEnd", uploadDateEnd);            // Đến ngày (nếu muốn same day)
        params.put("templateCode", templateCode);             // Mã template
        params.put("fileStatus", fileStatus);                       // Optional
    
        try {
            return simoFileMasManagerRepositoryService.getListUploadByDateAndTemplateId(params);
        } catch (BaseException e) {
            logger.error("Error occurred while fetching upload list: ", e);
            return Collections.emptyList();
        }
    }
    
}
