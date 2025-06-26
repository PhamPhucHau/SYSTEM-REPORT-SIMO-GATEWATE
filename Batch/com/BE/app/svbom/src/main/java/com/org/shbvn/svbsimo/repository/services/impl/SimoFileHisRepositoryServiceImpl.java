package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.dao.SimoFileHisDAO;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;
import com.org.shbvn.svbsimo.repository.services.SimoFileHisRepositoryService;

import jakarta.persistence.Query;


@Service("simoFileHisRepositoryService")
public class SimoFileHisRepositoryServiceImpl extends AbstractService implements SimoFileHisRepositoryService{

    private final SimoFileHisDAO fileHisDAO;

    @Autowired
    public SimoFileHisRepositoryServiceImpl(SimoFileHisDAO fileHisDAO) {
        this.fileHisDAO = fileHisDAO;
    }

    @Override
    public boolean create(SimoFileHis fileHis) throws BaseException {

        if (fileHis == null) {
            throw new ServiceRuntimeException("File history is null");
        }

        try {
            fileHisDAO.save(fileHis);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error creating file history", e);
        }
    }

    @Override
    public boolean createAll(List<SimoFileHis> fileHisList) throws BaseException {
        
        if (fileHisList == null || fileHisList.isEmpty()) {
            throw new ServiceRuntimeException("File history list is null");
        }

        try {
            // Use saveAll for batch insert
            fileHisDAO.saveAll(fileHisList);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error creating file history list", e);
        }
    }

    @Override
    public boolean update(SimoFileHis fileHis) throws BaseException {

        if (fileHis == null) {
            throw new ServiceRuntimeException("File history is null");
        }

        try {
            fileHisDAO.save(fileHis);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error updating file history", e);
        }
    }

    @Override
    public boolean updateAll(List<SimoFileHis> fileHisList) throws BaseException {

        if (fileHisList == null || fileHisList.isEmpty()) {
            throw new ServiceRuntimeException("File history list is null");
        }

        try {
            // Use saveAll for batch insert
            fileHisDAO.saveAll(fileHisList);
            return true;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error updating file history list", e);
        }
    }

    @Override
    public SimoFileHis getOne(Long id) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOne'");
    }

    @Override
    public List<SimoFileHis> getAll() throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public List<SimoFileHis> getByTemplateCode(String templateCode) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByTemplateCode'");
    }

    @Override
    public List<SimoFileHis> getByFileName(String fileName) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByFileName'");
    }
    
    @Override
    public SimoFileHis getByFileNameAndUploadDt(String fileName, String fileUploadDt) throws BaseException {
        Optional<SimoFileHis> fileHis = fileHisDAO.findByFileNameAndUploadDt(fileName, fileUploadDt);
        if (fileHis.isPresent()) {
            return fileHis.get();
        } else {
            return null;
        }
    }

    @Override
    public List<SimoFileHis> getByFileStatusAndUploadDt(String fileStatus, String fileUploadDt) throws BaseException {
        if (fileStatus == null || fileUploadDt == null) {
            throw new ServiceRuntimeException("File status or file upload date is null");
        }
        try {
            String sql = simoNamedQueries.get("getSimoFileHisByParams");
            Query query = entityManager.createNativeQuery(sql, SimoFileHis.class);
            query.setParameter("fileStatus", fileStatus);
            query.setParameter("fileUploadDt", fileUploadDt);
            query.setParameter("fileName", null);
            @SuppressWarnings("unchecked")
            List<SimoFileHis> list = query.getResultList();
            return list;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error getting file history by file status and upload date", e);
        }
    }

}
