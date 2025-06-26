/**
 * 
 */
package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.dao.TMetadataDAO;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;
import com.org.shbvn.svbsimo.repository.services.UtilityManagerRepositoryService;
import jakarta.persistence.Query;


@Service("utilityManagerRepositoryService")
public class UtilityManagerRepositoryServiceImpl extends AbstractService
		implements UtilityManagerRepositoryService {

	private TMetadataDAO objectMetaDao;
	private GenericCacheService cacheService;
	
	@Autowired
	public UtilityManagerRepositoryServiceImpl(TMetadataDAO objectMetaDao, GenericCacheService cacheService) {
		this.objectMetaDao = objectMetaDao;
		this.cacheService = cacheService;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.shinhan.recon.repository.service.UtilityManagerRepositoryService#
	 * getAllMetadata(java.util.Map)
	 */

	 

	@Override
	public List<TMetadata> getAllMetadata(Map<String, Object> inputParams) throws ServiceRuntimeException {
		String sql = simoNamedQueries.get("getAllMetadata");
		Query query = entityManager.createNativeQuery(sql, TMetadata.class);

		@SuppressWarnings("unchecked")
		List<TMetadata> list = query.getResultList();

		return list;
	}
	
	@Override
	public List<TMetadata> getMetadataByLookupCode(String lookupCode) throws ServiceRuntimeException {
		String cacheKey = "metadata:lookupCode:" + lookupCode;
		
		try {
			return cacheService.getOrComputeList(
				cacheKey,
				() -> {
					String sql = simoNamedQueries.get("getMetadataByLookupCode");
					Query query = entityManager.createNativeQuery(sql, TMetadata.class);
					query.setParameter("LOOKUPCODE", lookupCode);
					@SuppressWarnings("unchecked")
					List<TMetadata> list = query.getResultList();
					return list;
				},
				3600 // Cache for 1 hour
			);
		} catch (Exception ex) {
			throw new ServiceRuntimeException("Error getting metadata by lookup code", ex);
		}
	}

	@Override
	public TMetadata getMetadataByLookupCodeAndId(String lookupCode, String lookupId) throws ServiceRuntimeException {
		String cacheKey = "metadata:lookupCodeAndId:" + lookupCode + ":" + lookupId;
		
		try {
			return cacheService.getOrCompute(
				cacheKey,
				() -> {
					String sql = simoNamedQueries.get("getMetadataByLookupCodeAndId");
					Query query = entityManager.createNativeQuery(sql, TMetadata.class);
					query.setParameter("LOOKUPCODE", lookupCode);
					query.setParameter("LOOKUPCODEID", lookupId);
					@SuppressWarnings("unchecked")
					List<TMetadata> list = query.getResultList();
					if(list.isEmpty()) {	
						return null;
					}
					return list.get(0);
				},
				3600, // Cache for 1 hour
				TMetadata.class
			);
		} catch (Exception ex) {
			throw new ServiceRuntimeException("Error getting metadata by lookup code and id", ex);
		}
	}


	@Override
	public TMetadata createMetadata(TMetadata metadata) throws ServiceRuntimeException {
		if(metadata == null) {
			throw new ServiceRuntimeException("Metadata is null");
		}
		try {
			// Write to database
			TMetadata savedMetadata = objectMetaDao.save(metadata);
			
			// Invalidate cache for this lookup code to ensure fresh data on next read
			String cacheKey = "metadata:lookupCode:" + metadata.getLookupCode();
			cacheService.invalidate(cacheKey);
			
			return savedMetadata;
		} catch (Exception e) {
			throw new ServiceRuntimeException("Error creating metadata", e);
		}
	}
	
	@Override
	public boolean updateMetadata(TMetadata metadata) throws ServiceRuntimeException {
		if(metadata == null) { throw new ServiceRuntimeException("Metadata is null"); }		
		try {
			// Verify metadata exists
			TMetadata existingMetadata = objectMetaDao.findById(metadata.getId())
				.orElseThrow(() -> new ServiceRuntimeException("Metadata not found"));
			
			// Store old lookup code in case it changed
			String oldLookupCode = existingMetadata.getLookupCode();
			
			// Write to database
			objectMetaDao.save(metadata);
			
			// Invalidate cache for this lookup code
			String cacheKey = "metadata:lookupCode:" + metadata.getLookupCode();
			cacheService.invalidate(cacheKey);
			
			// If lookup code changed, also invalidate old lookup code's cache
			if (!metadata.getLookupCode().equals(oldLookupCode)) {
				String oldCacheKey = "metadata:lookupCode:" + oldLookupCode;
				cacheService.invalidate(oldCacheKey);
			}
			
			return true;
		} catch (Exception e) {
			throw new ServiceRuntimeException("Error updating metadata", e);
		}
	}
	
	@Override
	public boolean deleteMetadata(TMetadata metadata) throws ServiceRuntimeException {
		if(metadata == null) { throw new ServiceRuntimeException("Metadata is null"); }
		try {
			// Verify metadata exists
			objectMetaDao.findById(metadata.getId())
				.orElseThrow(() -> new ServiceRuntimeException("Metadata not found"));
			
			// Delete from database
			objectMetaDao.delete(metadata);
			
			// Invalidate cache for this lookup code
			String cacheKey = "metadata:lookupCode:" + metadata.getLookupCode();
			cacheService.invalidate(cacheKey);
			
			return true;
		} catch (Exception e) {
			throw new ServiceRuntimeException("Error deleting metadata", e);
		}
	}

}
