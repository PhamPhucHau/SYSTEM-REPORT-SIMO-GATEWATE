/**
 * 
 */
package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Map;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.repository.dao.SimoFileMasDAO;
import com.org.shbvn.svbsimo.repository.entities.SimoFileMas;
import com.org.shbvn.svbsimo.repository.services.SimoFileMasManagerRepositoryService;

import jakarta.persistence.Query;


@Service("simoFileMasManagerRepositoryService")
public class SimoFileMasManagerRepositoryServiceImpl extends AbstractService
		implements SimoFileMasManagerRepositoryService {

	private SimoFileMasDAO objectDao;
	
	@Autowired
	public SimoFileMasManagerRepositoryServiceImpl(SimoFileMasDAO objectDao) {
		this.objectDao = objectDao;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shinhan.recon.repository.service.TOmsStmtFileMasManagerRepositoryService#
	 * getListUploadByDate(java.util.Map)
	 */
	@Override
	public List<SimoFileMas> getListUploadByDate(Map<String, Object> inputParams) throws ServiceRuntimeException {
		String uploadDT = inputParams.get(APIConstant.UPLOAD_DATE_KEY).toString();
		String status = inputParams.get(APIConstant.UPLOAD_BANKSTATUS_KEY).toString();
		long trxType = inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY) == null
				|| CommonUtils.isBlank(inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY).toString())
				? 0
				: Integer.parseInt(inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY).toString());
		String sql = simoNamedQueries.get("getListUploadByDate");
		Query query = entityManager.createNativeQuery(sql, SimoFileMas.class);
		
		query.setParameter(APIConstant.UPLOAD_DATE_KEY, uploadDT);
		query.setParameter(APIConstant.UPLOAD_BANKSTATUS_KEY, status);
		query.setParameter(APIConstant.UPLOAD_TRXTYPE_KEY, trxType);
		
		@SuppressWarnings("unchecked")
		List<SimoFileMas> lStatementFiles = query.getResultList();
		return lStatementFiles;
	}
	@Override
	public List<SimoFileMas> getListUploadByDateAndStatus(Map<String, Object> inputParams) throws ServiceRuntimeException {
		String uploadDT = inputParams.get(APIConstant.UPLOAD_DATE_KEY).toString();
		String status = inputParams.get(APIConstant.UPLOAD_BANKSTATUS_KEY).toString();
		long trxType = inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY) == null
				|| CommonUtils.isBlank(inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY).toString())
				? 0
				: Integer.parseInt(inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY).toString());
		String sql = simoNamedQueries.get("getListUploadByDateAndStatus");
		Query query = entityManager.createNativeQuery(sql, SimoFileMas.class);
		
		query.setParameter(APIConstant.UPLOAD_DATE_KEY, uploadDT);
		query.setParameter(APIConstant.UPLOAD_BANKSTATUS_KEY, status);
		query.setParameter(APIConstant.UPLOAD_TRXTYPE_KEY, trxType);
		
		@SuppressWarnings("unchecked")
		List<SimoFileMas> lStatementFiles = query.getResultList();
		return lStatementFiles;
	}
	

	@Override
	public List<SimoFileMas> getListUploadByDateAndTemplateId(Map<String, Object> inputParams) throws ServiceRuntimeException {
		String uploadDT = inputParams.get(APIConstant.UPLOAD_DATE_KEY).toString();
		String bankCode = inputParams.get(APIConstant.UPLOAD_BANKCODE_KEY).toString();
		long trxType = inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY) == null
				|| CommonUtils.isBlank(inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY).toString())
				? 0
				: Integer.parseInt(inputParams.get(APIConstant.UPLOAD_TRXTYPE_KEY).toString());
		String sql = simoNamedQueries.get("getListUploadByDateAndBankCode");
		Query query = entityManager.createNativeQuery(sql, ServiceRuntimeException.class);
		
		query.setParameter(APIConstant.UPLOAD_DATE_KEY, uploadDT);
		query.setParameter(APIConstant.UPLOAD_BANKCODE_KEY, bankCode);
		query.setParameter(APIConstant.UPLOAD_TRXTYPE_KEY, trxType);
		@SuppressWarnings("unchecked")
		List<SimoFileMas> lStatementFiles = query.getResultList();
		return lStatementFiles;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shinhan.recon.repository.service.TOmsStmtFileMasManagerRepositoryService#
	 * create(java.util.Map)
	 */
	@Override
	public boolean create(Map<String, Object> inputParams) throws ServiceRuntimeException {
		try {
			SimoFileMas item = (SimoFileMas) inputParams.get(APIConstant.DOCUMENT);
			if (item != null) {
				objectDao.save(item);
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_002"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shinhan.recon.repository.service.TOmsStmtFileMasManagerRepositoryService#
	 * createAll(java.util.Map)
	 */
	@Override
	public boolean createAll(Map<String, Object> inputParams) throws ServiceRuntimeException {
		try {
			@SuppressWarnings("unchecked")
			List<SimoFileMas> items = (List<SimoFileMas>) inputParams.get(APIConstant.DOCUMENT);
			if (CollectionUtils.isNotEmpty(items)) {
				objectDao.saveAll(items);
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_002"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shinhan.recon.repository.service.TOmsStmtFileMasManagerRepositoryService#
	 * getOne(java.util.Map)
	 */
	@Override
	public SimoFileMas getOne(Map<String, Object> inputParams) throws ServiceRuntimeException {
		String omsId = inputParams.get(APIConstant.OMSID).toString();
		if (CommonUtils.isBlank(omsId)) {
			throw new ServiceRuntimeException(env.getProperty("MSG_003"));
		}
		try {
			SimoFileMas item = objectDao.findById(Long.valueOf(omsId)).orElse(null);
			return item;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_002") + "." + omsId + "");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shinhan.recon.repository.service.TOmsStmtFileMasManagerRepositoryService#
	 * update(java.util.Map)
	 */
	@Override
	public boolean update(Map<String, Object> inputParams) throws ServiceRuntimeException {
		try {
			SimoFileMas item = (SimoFileMas) inputParams.get(APIConstant.DOCUMENT);
			if (item != null) {
				objectDao.save(item);
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_002"));
		}
	}
	@Override
	public boolean updateAll(Map<String, Object> inputParams) throws ServiceRuntimeException {
		try {
			@SuppressWarnings("unchecked")
			List<SimoFileMas> items = (List<SimoFileMas>) inputParams.get(APIConstant.DOCUMENT);
			if (CollectionUtils.isNotEmpty(items)) {
				objectDao.saveAll(items);
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceRuntimeException(env.getProperty("MSG_002"));
		}
	}
	
	@Override
	public List<SimoFileMas> getDeleteListUploadByBTemplateId(Map<String, Object> inputParams) throws BaseException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getDeleteListUploadByBTemplateId'");
	}

}
