package com.org.shbvn.svbsimo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;
import com.org.shbvn.svbsimo.repository.model.MetaDataInfo;
import com.org.shbvn.svbsimo.repository.model.UserFeatureInfo;
import com.org.shbvn.svbsimo.service.AdminApiService;

@Service("adminApiService")
public class AdminApiServiceImpl extends AbstractService implements AdminApiService{

    @Override
    public List<TMetadata> getListFeature(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListFeature'");
    }

    @Override
    public MetaDataInfo createFeature(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFeature'");
    }

    @Override
    public MetaDataInfo updateFeature(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFeature'");
    }

    @Override
    public MetaDataInfo deleteFeature(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFeature'");
    }

    @Override
    public List<UserFeatureInfo> getListFeatureByRole(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListFeatureByRole'");
    }

    @Override
    public UserFeatureInfo createFeatureForRole(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFeatureForRole'");
    }
    
    
}
