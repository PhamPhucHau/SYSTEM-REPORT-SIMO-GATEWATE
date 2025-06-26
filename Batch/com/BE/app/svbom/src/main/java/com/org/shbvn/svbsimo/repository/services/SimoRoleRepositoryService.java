package com.org.shbvn.svbsimo.repository.services;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.entities.SimoRole;

public interface SimoRoleRepositoryService {
    
    public Long createRole(Map<String, Object> payload) throws ServiceRuntimeException;
    
    public boolean updateRole(Map<String, Object> payload) throws ServiceRuntimeException;
    
    public SimoRole getRoleById(Long id) throws ServiceRuntimeException;
    
    public SimoRole getRoleByRoleId(String roleId) throws ServiceRuntimeException;
    
    public List<SimoRole> getAllRoles() throws ServiceRuntimeException;
    
    public List<SimoRole> getActiveRoles() throws ServiceRuntimeException;
    
    public boolean deleteRole(String roleId) throws ServiceRuntimeException;

    public List<SimoRole> getRoleByParams(Map<String, Object> inputParams) throws ServiceRuntimeException;
    
}