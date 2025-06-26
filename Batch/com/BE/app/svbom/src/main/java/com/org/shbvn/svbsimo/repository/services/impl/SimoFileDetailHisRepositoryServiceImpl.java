package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.configure.SimoNamedQueries;
import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.dao.SimoFileDetailHisDAO;
import com.org.shbvn.svbsimo.repository.entities.SimoFileDetailHis;
import com.org.shbvn.svbsimo.repository.services.SimoFileDetailHisRepositoryService;

import jakarta.persistence.Query;

@Service("simoFileDetailHisRepositoryService")
public class SimoFileDetailHisRepositoryServiceImpl extends AbstractService implements SimoFileDetailHisRepositoryService {
    
    private final SimoFileDetailHisDAO fileDetailHisDAO;

    
    @Autowired
    private SimoNamedQueries simoNamedQueries;

    @Autowired
    public SimoFileDetailHisRepositoryServiceImpl(SimoFileDetailHisDAO fileDetailHisDAO) {
        this.fileDetailHisDAO = fileDetailHisDAO;
    }

    @Override
    public boolean create(SimoFileDetailHis fileDetailHis) throws BaseException {

        if (fileDetailHis == null) {
            throw new ServiceRuntimeException("File detail history is null");
        }

        try {
            fileDetailHisDAO.save(fileDetailHis);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error creating file detail history", e);
        }
    }

    @Override
    public boolean createAll(List<SimoFileDetailHis> fileDetailHisList) throws BaseException {
        
        if (fileDetailHisList == null || fileDetailHisList.isEmpty()) {
            throw new ServiceRuntimeException("File detail history list is null");
        }

        try {
            // Use saveAll for batch insert
            fileDetailHisDAO.saveAll(fileDetailHisList);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error creating file detail history list", e);
        }
    }

    @Override
    public boolean update(SimoFileDetailHis fileDetailHis) throws BaseException {

        if (fileDetailHis == null) {
            throw new ServiceRuntimeException("File detail history is null");
        }

        try {
            fileDetailHisDAO.save(fileDetailHis);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error updating file detail history", e);
        }
    }

    @Override
    public boolean updateAll(List<SimoFileDetailHis> fileDetailHisList) throws BaseException {

        if (fileDetailHisList == null || fileDetailHisList.isEmpty()) {
            throw new ServiceRuntimeException("File detail history list is null");
        }

        try {
            // Use saveAll for batch insert
            fileDetailHisDAO.saveAll(fileDetailHisList);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error updating file detail history list", e);
        }
    }

    @Override
    public SimoFileDetailHis getOne(Long id) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public List<SimoFileDetailHis> getAll() throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public List<SimoFileDetailHis> getByTemplateCode(String templateCode) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByTemplateCode'");
    }

    @Override
    public List<SimoFileDetailHis> getByFileName(String fileName) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByFileName'");
    }

    @Override
    public List<SimoFileDetailHis> fetchByParams(Map<String, Object> inputParams) throws BaseException {
        if (inputParams == null) {
            throw new ServiceRuntimeException("Input parameters map is null");
        }
        
        try {
            // Get the SQL query from named queries
            String sql = simoNamedQueries.get("fetchSimoFileDetailHisByParams");
            
            // Create native query with entity mapping
            Query query = entityManager.createNativeQuery(sql, SimoFileDetailHis.class);
            
            // Set pagination parameters
            int page = inputParams.get("page") == null 
                    ? 1 
                    : Integer.parseInt(inputParams.get("page").toString());
            int limit = inputParams.get("limit") == null 
                    ? 10 
                    : Integer.parseInt(inputParams.get("limit").toString());
            int offset = (page - 1) * limit;
            
            // Set query parameters
            query.setParameter("fileName", inputParams.get("fileName"));
            query.setParameter("fileUploadDt", inputParams.get("fileUploadDt"));
            query.setParameter("processStatus", inputParams.get("processStatus"));
            query.setParameter("templateCode", inputParams.get("templateCode"));
// Truyền null nếu không có giá trị
            query.setParameter("cif", Optional.ofNullable(inputParams.get("cif"))
                    .filter(v -> !v.toString().isBlank()).orElse(null));
            query.setParameter("accountNo", Optional.ofNullable(inputParams.get("accountNo"))
                .filter(v -> !v.toString().isBlank()).orElse(null));
            query.setParameter("personalId", Optional.ofNullable(inputParams.get("personalId"))
                .filter(v -> !v.toString().isBlank()).orElse(null));
            query.setParameter("offset", offset);
            query.setParameter("limit", limit);
            System.out.println(query.toString());
            // Execute query and return results
            @SuppressWarnings("unchecked")
            List<SimoFileDetailHis> results = query.getResultList();
            
            return results;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error fetching file detail history records by parameters", e);
        }
    }
    
    @Override
    public long countByParams(Map<String, Object> inputParams) throws BaseException {
        if (inputParams == null) {
            throw new ServiceRuntimeException("Input parameters map is null");
        }
        
        try {
            // Create count query based on the same conditions
            StringBuilder countSql = new StringBuilder();
            countSql.append("SELECT COUNT(*) FROM SIMO_FILE_DETAIL_HIS fd ");
            countSql.append("WHERE fd.FILE_NAME = coalesce(:fileName, fd.FILE_NAME) ");
            countSql.append("AND fd.FILE_UPLOAD_DT = coalesce(:fileUploadDt, fd.FILE_UPLOAD_DT) ");
            countSql.append("AND fd.PROCESS_STATUS = coalesce(:processStatus, fd.PROCESS_STATUS) ");
            countSql.append("AND fd.TEMPLATE_CODE = coalesce(:templateCode, fd.TEMPLATE_CODE) ");
            countSql.append("AND (:cif is null OR fd.CIF = :cif) ");
            countSql.append("AND (:accountNo is null OR fd.ACOUNT_NO = :accountNo) ");
            countSql.append("AND (:personalId is null OR fd.PERSONAL_ID = :personalId) ");
            
            // Create native query for count
            Query countQuery = entityManager.createNativeQuery(countSql.toString());
            
            // Set query parameters (same as fetch query except pagination)
            countQuery.setParameter("fileName", inputParams.get("fileName"));
            countQuery.setParameter("fileUploadDt", inputParams.get("fileUploadDt"));
            countQuery.setParameter("processStatus", inputParams.get("processStatus"));
            countQuery.setParameter("templateCode", inputParams.get("templateCode"));
            countQuery.setParameter("cif", inputParams.get("cif"));
            countQuery.setParameter("accountNo", inputParams.get("accountNo"));
            countQuery.setParameter("personalId", inputParams.get("personalId"));
            
            // Execute count query
            Number count = (Number) countQuery.getSingleResult();
            
            return count.longValue();
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error counting file detail history records by parameters", e);
        }
    }
}
