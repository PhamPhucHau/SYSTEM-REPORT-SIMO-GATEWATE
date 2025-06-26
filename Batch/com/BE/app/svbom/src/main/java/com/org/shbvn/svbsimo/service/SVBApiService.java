package com.org.shbvn.svbsimo.service;


import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.SimoFileHis;

public interface SVBApiService {
    
    public void uploadFile(SimoFileHis fileHis) throws BaseException;    
    public void uploadFileDaily(SimoFileHis fileHis) throws BaseException;  
}
