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
    public List<SimoFileMas> getListUploadByDateAndTemplateId(String uploadDate, String templateId) {
        // Implementation logic to retrieve the list of uploaded files by date and template ID
        Map<String, Object> params = new HashMap<>();
        params.put(APIConstant.UPLOAD_DATE_KEY, uploadDate);
        params.put(APIConstant.UPLOAD_BANKCODE_KEY, ""); // Ensure bankCode is defined
        params.put(APIConstant.UPLOAD_TRXTYPE_KEY, "");   // Ensure trxType is defined
        try {
            // Call the static method directly using the interface name
            return simoFileMasManagerRepositoryService.getListUploadByDateAndTemplateId(params);
        } catch (BaseException e) {
            // Log the exception and handle it appropriately
            logger.error("Error occurred while fetching upload list: ", e);
            return Collections.emptyList(); // Return an empty list or handle as needed
        }
    }
    
}
