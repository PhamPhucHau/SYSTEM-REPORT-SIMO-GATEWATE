package com.org.shbvn.svbsimo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.utils.EntityModelConverter;
import com.org.shbvn.svbsimo.repository.entities.SimoRole;
import com.org.shbvn.svbsimo.repository.entities.TMetadata;
import com.org.shbvn.svbsimo.repository.model.AuthMenuInfo;
import com.org.shbvn.svbsimo.repository.model.MetaDataInfo;
import com.org.shbvn.svbsimo.repository.model.RoleInfo;
import com.org.shbvn.svbsimo.service.AdminConfigApiServive;

@Service("adminConfigApiService")
public class AdminConfigApiServiceImpl extends AbstractService implements AdminConfigApiServive{

     @Override
    public List<RoleInfo> getListRole(Map<String, Object> inputParams) throws BaseException {
        List<SimoRole> listRole = getRepositoryManageService().getSimoRoleRepositoryService().getRoleByParams(inputParams);
        if (listRole == null || listRole.isEmpty()) {
            return null;
        }
        return EntityModelConverter.toModelList(listRole, RoleInfo.class);
    }

    @Override
    public RoleInfo createRole(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRole'");
    }

    @Override
    public RoleInfo updateRole(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRole'");
    }

    @Override
    public RoleInfo deleteRole(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRole'");
    }

    @Override
    public List<MetaDataInfo> getListMetadatas(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListMetadatas'");
    }

    @Override
    public MetaDataInfo createMetadata(TMetadata metadata) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMetadata'");
    }

    @Override
    public boolean updateMetadata(TMetadata metadata) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMetadata'");
    }

    @Override
    public boolean deleteMetadata(TMetadata metadata) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteMetadata'");
    }

    @Override
    public List<AuthMenuInfo> getListAuthMenu(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListAuthMenu'");
    }

    @Override
    public AuthMenuInfo createAuthMenu(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAuthMenu'");
    }

    @Override
    public boolean updateAuthMenu(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAuthMenu'");
    }

    @Override
    public boolean deleteAuthMenu(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAuthMenu'");
    }
    
}
