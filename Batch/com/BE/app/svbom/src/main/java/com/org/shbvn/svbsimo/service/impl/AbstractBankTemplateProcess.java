package com.org.shbvn.svbsimo.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.org.shbvn.svbsimo.api.DTO.TokenResponse;
import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.common.ExcelDTO;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.model.BankCommonTemplate;
import com.org.shbvn.svbsimo.core.model.DailyCommonTemplate;
import com.org.shbvn.svbsimo.core.model.FileTemplateInfo;
import com.org.shbvn.svbsimo.core.model.SVBRespondOuput;
import com.org.shbvn.svbsimo.core.model.SVBUploadRequest;
import com.org.shbvn.svbsimo.core.model.SVBUploadRequestDaily;
import com.org.shbvn.svbsimo.core.model.SVBUserPermission;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.core.utils.ReadFromExcel;
import com.org.shbvn.svbsimo.repository.entities.SimoFileDetailHis;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;

public abstract class AbstractBankTemplateProcess extends AbstractService {
    
    @SuppressWarnings({ "rawtypes", "hiding" })
	protected <T extends BankCommonTemplate> List<T> processBankTemplate(File file, FileTemplateInfo templateInfo, Class<T> clazz) throws BaseException {
        ReadFromExcel rw = new ReadFromExcel(file.getAbsolutePath());
		ArrayList<Map> lsArrMapObject = (ArrayList<Map>) rw.readDataFromExcel(templateInfo.getSheetIndex(), templateInfo.getFromRow(), templateInfo.getHeaderRow());
        logger.info("File template found {}" , lsArrMapObject.size());
        if(lsArrMapObject.isEmpty()) {
			return new ArrayList<T>();
		}
        JsonArray jsonArrayDocument = (JsonArray) CommonUtils.toPojo(lsArrMapObject, JsonArray.class);
		
		List<String> excelColumn = Arrays.asList(templateInfo.getTemplateColName().split(","));
		Field[] bankStatementProperty =  CommonUtils.getAllDeclaredFields(clazz).toArray(new Field[0]);
        for (Field field : bankStatementProperty) {
            logger.info("Field name {}", field.getName());
        }
		Map<String, Entry<String, UnaryOperator<String>>> bankStatementMapping = ExcelDTO.buildBankTemplateMapping(excelColumn, bankStatementProperty);
		List<T> bankStatements = ExcelDTO.mapListExcelDataToListBankTemplate(jsonArrayDocument, bankStatementMapping, () -> {
			try {
				return clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

        if(bankStatements.isEmpty()) {
            return new ArrayList<T>();
        }
        return bankStatements;
    }
    @SuppressWarnings({ "rawtypes", "hiding" })
	protected <T extends DailyCommonTemplate, E> T processDailyTemplate(
    File file,
    FileTemplateInfo templateInfo,
    Class<T> parentClass,
    Class<E> childClass,
    Map<String, Entry<String, UnaryOperator<String>>> childMapping,
    BiConsumer<T, List<E>> setChildListMethod
) throws BaseException {
ReadFromExcel rw = new ReadFromExcel(file.getAbsolutePath());
    ArrayList<Map> lsArrMapObject = (ArrayList<Map>) rw.readDataFromExcel(
        templateInfo.getSheetIndex(), templateInfo.getFromRow(), templateInfo.getHeaderRow()
    );
    logger.info("File template found {}" , lsArrMapObject.size());

    if (lsArrMapObject.isEmpty()) {
        return null; // Vì trả về object duy nhất
    }

    // Convert thành JsonArray để xử lý dễ hơn
    JsonArray jsonArrayDocument = (JsonArray) CommonUtils.toPojo(lsArrMapObject, JsonArray.class);

    // Khởi tạo đối tượng cha
    T parentObject;
    
    try {
        parentObject = parentClass.getDeclaredConstructor().newInstance();
      
    } catch (Exception e) {
        throw new RuntimeException("Cannot instantiate parent class", e);
    }
    parentObject.setThoigiandulieu(jsonArrayDocument.get(0).getAsJsonObject().get("ThoiGianDuLieu").getAsString());
    // Format Date time 
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    parentObject.setThoigianguibaocao(formattedDateTime);
    // Ánh xạ dữ liệu từng dòng trong file vào danh sách con
    List<E> childList = new ArrayList<>();
    for (JsonElement element : jsonArrayDocument) {
        E childObject;
        try {
            childObject = childClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate child class", e);
        }

        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            String columnName = entry.getKey().trim().toLowerCase();
            String columnValue = entry.getValue().getAsString();

            if (!childMapping.containsKey(columnName)) {
                continue;
            }

            Entry<String, UnaryOperator<String>> mapper = childMapping.get(columnName);
            String valueToAdd = mapper.getValue().apply(columnValue);
            String fieldName = mapper.getKey();

            CommonUtils.setPropertyIntoObject(childObject, valueToAdd, fieldName);
        }

        childList.add(childObject);
    }

    // Gán danh sách con vào object cha
    setChildListMethod.accept(parentObject, childList);

    return parentObject;
}
	protected String getSvbAccessToken() {
		/**
         *  
         * Get SVB Access Token
         * Below method will get SVB access token from cache if it is not expired.
         * If it is expired( before access token ttl 60mins), it will call SVB API to get new access token and store it in cache.
         */
        //TODO : correct external endpoint information (url, method, username, password, ttl, request/response object model)
        long ttl = Long.parseLong(env.getProperty(APIConstant.SVB_ACCESS_TOKEN_TTL));
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "password");
        body.put("username", env.getProperty(APIConstant.SVB_ACCESS_TOKEN_USERNAME));
        body.put("password", env.getProperty(APIConstant.SVB_ACCESS_TOKEN_PASSWORD));
        String authString = env.getProperty(APIConstant.SVB_CONSUMER_KEY) + ":" + env.getProperty(APIConstant.SVB_CONSUMER_SECRET);
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + encodedAuth);

        
        SVBRespondOuput svbRespondOuput = getGenericCacheService().getOrCompute("svb_auth_token", () -> {
                SVBRespondOuput output = null;
                try {
                    TokenResponse tokenResponse = CommonUtils.invokeRestTemplateService(env.getProperty(APIConstant.SVB_BASE_URL) + env.getProperty(APIConstant.SVB_ACCESS_TOKEN_URL), env.getProperty(APIConstant.SVB_ACCESS_TOKEN_METHOD), ""
                      	, headers ,body, TokenResponse.class, true);
                    // Kiểm tra nếu output là null
                    output = new SVBRespondOuput(tokenResponse, null, true, 200);

                    
                } catch (ServiceRuntimeException e) {
                    logger.error("Error getting SVB access token", e);
                }
                return output;  
            }, ttl - 60, SVBRespondOuput.class);
        
        if (svbRespondOuput == null || svbRespondOuput.getResult() == null) {
            return null;
        }
        TokenResponse tokenResponse = (TokenResponse) svbRespondOuput.getResult();
        String accessToken = tokenResponse.getAccess_token();
        return accessToken;
	}

    /**
         *  
         * Get SVB Access Token
         * Below method will get SVB access token from cache if it is not expired.
         * If it is expired( before access token ttl 60mins), it will call SVB API to get new access token and store it in cache.
         */
        //TODO : correct external endpoint information (url, method, username, password, ttl, request/response object model)
	protected SVBRespondOuput callSVBApi(SVBUploadRequest svbUploadRequest, String accessToken, String apiMethod, String apiUrl,Map<String, String> customHeaders) {
        try {
            return CommonUtils.invokeRestTemplateService(apiUrl, apiMethod, accessToken
                    ,customHeaders, svbUploadRequest.getDatas(), SVBRespondOuput.class, false);
        } catch (ServiceRuntimeException e) {
            logger.error("Error calling SVB API", e);
        }
        return new SVBRespondOuput();
    }
       //TODO : correct external endpoint information (url, method, username, password, ttl, request/response object model)
	protected SVBRespondOuput callSVBApi(SVBUploadRequestDaily svbUploadRequest, String accessToken, String apiMethod, String apiUrl,Map<String, String> customHeaders) {
        try {
            return CommonUtils.invokeRestTemplateService(apiUrl, apiMethod, accessToken
                    ,customHeaders, svbUploadRequest.getDatas(), SVBRespondOuput.class, false);
        } catch (ServiceRuntimeException e) {
            logger.error("Error calling SVB API", e);
        }
        return new SVBRespondOuput();
    }
	@Transactional(readOnly = false, propagation =  Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void batchFileHisUpdate(SimoFileHis fileHis, List<SimoFileDetailHis> fileDetailHisList) throws BaseException {
		getRepositoryManageService().getSimoFileHisRepositoryService().update(fileHis);
		getRepositoryManageService().getSimoFileDetailHisRepositoryService().updateAll(fileDetailHisList);
	}

}
