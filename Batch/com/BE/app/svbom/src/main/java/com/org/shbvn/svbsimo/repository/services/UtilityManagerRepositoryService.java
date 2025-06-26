/**
 * 
 */
package com.org.shbvn.svbsimo.repository.services;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;


public interface UtilityManagerRepositoryService {

	public List<TMetadata> getAllMetadata(Map<String, Object> inputParams) throws BaseException;
	
	public List<TMetadata> getMetadataByLookupCode(String lookupCode) throws BaseException;

	public TMetadata getMetadataByLookupCodeAndId(String lookupCode, String lookupId) throws BaseException;

	public TMetadata createMetadata(TMetadata metadata) throws BaseException;
	
	public boolean updateMetadata(TMetadata metadata) throws BaseException;
	
	public boolean deleteMetadata(TMetadata metadata) throws BaseException;
}
