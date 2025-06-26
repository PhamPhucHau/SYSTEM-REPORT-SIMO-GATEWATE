/**
 * 
 */
package com.org.shbvn.svbsimo.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.org.shbvn.svbsimo.core.common.AbstractBasicCommonClass;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.constant.ResponseOutPut;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;

public class BaseController extends AbstractBasicCommonClass{

	protected ResponseEntity<Object> triggerSuccessOutPut(Object data, Class<?> classT, String messageStr) throws ServiceRuntimeException {
		try {
			ResponseOutPut output;
			if (data == null) {
				if (CommonUtils.isBlank(messageStr)) {
					messageStr = APIConstant.NO_RECORD_FOUND_KEY;
				}
				output = new ResponseOutPut(new HashMap<>(), messageStr, true, HttpStatus.OK.value());
			} else {
				if (data instanceof String && classT == String.class) {
					output = new ResponseOutPut(data.toString(), "MSG_000", true, HttpStatus.OK.value());
				} else {
					output = new ResponseOutPut(data, "MSG_000", true, HttpStatus.OK.value());
				}
			}
			return new ResponseEntity<Object>(output, HttpStatus.OK);
		} catch (Exception e) {
			throw new ServiceRuntimeException("MSG_004");
		}
	}
	
	protected ResponseEntity<Object> triggerSuccessOutPut(List<?> data, Object count) throws ServiceRuntimeException {
		ResponseOutPut output = null;
		try {
			if (data == null || data.isEmpty() == true || data.size() == 0) {
				// No record found
				output = new ResponseOutPut(new HashMap<>(), APIConstant.NO_RECORD_FOUND_KEY, true, HttpStatus.OK.value());
			} else {
				HashMap<String, Object> map = new HashMap<>();
				map.put(APIConstant.DATA, data);
				map.put(APIConstant.COUNT, count.toString());
				output = new ResponseOutPut(map, "MSG_000", true, HttpStatus.OK.value());
			}
			return new ResponseEntity<Object>(output, HttpStatus.OK);
			
		} catch (Exception e) {
			throw new ServiceRuntimeException("MSG_004");
		}
	}
	
	protected ResponseEntity<Object> triggerSuccessResponseFile(File file) throws BaseException, FileNotFoundException{
		String contentType = httpServletRequest.getHeader(APIConstant.CONTENT_TYPE_REQUEST_HEADER);
		
		if(!CommonUtils.isBlank(contentType) && MediaType.APPLICATION_PDF_VALUE.equalsIgnoreCase(contentType)){
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName())
					// Content-Type
					.contentType(MediaType.APPLICATION_PDF)
					// Contet-Length
					.contentLength(file.length()) //
					.body(resource);
		} else {
		//} else if(MediaType.APPLICATION_OCTET_STREAM_VALUE.equalsIgnoreCase(contentType)){
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			return ResponseEntity.ok()
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
					// Content-Type
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					// Contet-Length
					.contentLength(file.length()) //
					.body(resource);
		}
		
	}
}
