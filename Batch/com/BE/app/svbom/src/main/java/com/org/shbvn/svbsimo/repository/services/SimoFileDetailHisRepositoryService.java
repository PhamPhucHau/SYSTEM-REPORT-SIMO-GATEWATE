package com.org.shbvn.svbsimo.repository.services;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.SimoFileDetailHis;

public interface SimoFileDetailHisRepositoryService {
    
    public boolean create(SimoFileDetailHis fileDetailHis) throws BaseException;
    
    public boolean createAll(List<SimoFileDetailHis> fileDetailHisList) throws BaseException;
    
    public boolean update(SimoFileDetailHis fileDetailHis) throws BaseException;
    
    public boolean updateAll(List<SimoFileDetailHis> fileDetailHisList) throws BaseException;
    
    public SimoFileDetailHis getOne(Long id) throws BaseException;
    
    public List<SimoFileDetailHis> getAll() throws BaseException;
    
    public List<SimoFileDetailHis> getByTemplateCode(String templateCode) throws BaseException;
    
    public List<SimoFileDetailHis> getByFileName(String fileName) throws BaseException;
    
    /**
     * Fetches file detail history records with pagination based on input parameters
     * 
     * @param inputParams Map containing search parameters:
     *   - fileName: File name (optional)
     *   - fileUploadDt: File upload date (optional)
     *   - processStatus: Process status (optional)
     *   - templateCode: Template code (optional)
     *   - cif: Customer ID (optional)
     *   - accountNo: Account number (optional)
     *   - personalId: Personal ID (optional)
     *   - page: Page number (1-based, defaults to 1)
     *   - limit: Number of records per page (defaults to 10)
     * @return List of SimoFileDetailHis records matching the criteria
     * @throws BaseException If an error occurs during retrieval
     */
    public List<SimoFileDetailHis> fetchByParams(Map<String, Object> inputParams) throws BaseException;
    
    /**
     * Counts the total number of file detail history records matching the given criteria
     * 
     * @param inputParams Map containing search parameters (same as fetchByParams, excluding pagination)
     * @return Total count of matching records
     * @throws BaseException If an error occurs during counting
     */
    public long countByParams(Map<String, Object> inputParams) throws BaseException;
}
