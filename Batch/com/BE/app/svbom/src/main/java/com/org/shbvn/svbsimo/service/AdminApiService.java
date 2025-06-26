package com.org.shbvn.svbsimo.service;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;
import com.org.shbvn.svbsimo.repository.model.MetaDataInfo;
import com.org.shbvn.svbsimo.repository.model.UserFeatureInfo;

public interface AdminApiService {
	
	
	public List<UserFeatureInfo> getListFeatureByRole(Map<String, Object> inputParams) throws BaseException;
	
	public UserFeatureInfo createFeatureForRole(Map<String, Object> inputParams) throws BaseException;
	
	public List<TMetadata> getListFeature(Map<String, Object> inputParams) throws BaseException;
	
	public MetaDataInfo createFeature(Map<String, Object> inputParams) throws BaseException;
	
	public MetaDataInfo updateFeature(Map<String, Object> inputParams) throws BaseException;
	
	public MetaDataInfo deleteFeature(Map<String, Object> inputParams) throws BaseException;

    
}
