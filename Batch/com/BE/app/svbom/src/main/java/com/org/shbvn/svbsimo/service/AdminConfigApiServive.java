package com.org.shbvn.svbsimo.service;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;
import com.org.shbvn.svbsimo.repository.model.AuthMenuInfo;
import com.org.shbvn.svbsimo.repository.model.MetaDataInfo;
import com.org.shbvn.svbsimo.repository.model.RoleInfo;

public interface AdminConfigApiServive {
    
    public List<RoleInfo> getListRole(Map<String, Object> inputParams) throws BaseException;
    
    public RoleInfo createRole(Map<String, Object> inputParams) throws BaseException;

    public RoleInfo updateRole(Map<String, Object> inputParams) throws BaseException;
    
    public RoleInfo deleteRole(Map<String, Object> inputParams) throws BaseException;

    public List<MetaDataInfo> getListMetadatas(Map<String, Object> inputParams) throws BaseException;

	public MetaDataInfo createMetadata(TMetadata metadata) throws BaseException;
	
	public boolean updateMetadata(TMetadata metadata) throws BaseException;
	
	public boolean deleteMetadata(TMetadata metadata) throws BaseException;

    public List<AuthMenuInfo> getListAuthMenu(Map<String, Object> inputParams) throws BaseException;

    public AuthMenuInfo createAuthMenu(Map<String, Object> inputParams) throws BaseException;
    
    public boolean updateAuthMenu(Map<String, Object> inputParams) throws BaseException;
    
    public boolean deleteAuthMenu(Map<String, Object> inputParams) throws BaseException;

}
