/**
 * 
 */
package com.org.shbvn.svbsimo.repository.services;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.SimoFileMas;


public interface SimoFileMasManagerRepositoryService {

	public List<SimoFileMas> getListUploadByDateAndStatus(Map<String, Object> inputParams) throws BaseException;

	public List<SimoFileMas> getDeleteListUploadByBTemplateId(Map<String, Object> inputParams) throws BaseException;
	
	public List<SimoFileMas> getListUploadByDate(Map<String, Object> inputParams) throws BaseException;
	
	public List<SimoFileMas> getListUploadByDateAndTemplateId(Map<String, Object> inputParams) throws BaseException;
	
	public boolean create(Map<String, Object> inputParams) throws BaseException;
	
	public boolean createAll(Map<String, Object> inputParams) throws BaseException;
	
	public SimoFileMas getOne(Map<String, Object> inputParams) throws BaseException;
	
	public boolean update(Map<String, Object> inputParams) throws BaseException;
	
	public boolean updateAll(Map<String, Object> inputParams) throws BaseException;
	
}
