package com.org.shbvn.svbsimo.repository.services;

import java.util.List;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;

public interface SimoFileHisRepositoryService {
    
    public boolean create(SimoFileHis fileHis) throws BaseException;
    
    public boolean createAll(List<SimoFileHis> fileHisList) throws BaseException;
    
    public boolean update(SimoFileHis fileHis) throws BaseException;
    
    public boolean updateAll(List<SimoFileHis> fileHisList) throws BaseException;
    
    public SimoFileHis getOne(Long id) throws BaseException;
    
    public List<SimoFileHis> getAll() throws BaseException;
    
    public List<SimoFileHis> getByTemplateCode(String templateCode) throws BaseException;
    
    public List<SimoFileHis> getByFileName(String fileName) throws BaseException;

    public List<SimoFileHis> getByFileStatusAndUploadDt(String fileStatus,String fileUploadDt) throws BaseException;

    public SimoFileHis getByFileNameAndUploadDt(String fileName, String fileUploadDt) throws BaseException;
}
