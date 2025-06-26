package com.org.shbvn.svbsimo.repository.services;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.entities.SimoRole;
import com.org.shbvn.svbsimo.repository.entities.SimoUser;
import com.org.shbvn.svbsimo.repository.entities.SimoUserRole;

public interface SimoUserRoleRepositoryService {
    
    public Long assignRoleToUser(Long userId, Long roleId) throws ServiceRuntimeException;
    
    public Long assignRoleToUser(SimoUser user, SimoRole role) throws ServiceRuntimeException;
    
    public boolean updateUserRole(Map<String, Object> payload) throws ServiceRuntimeException;
    
    public SimoUserRole getUserRoleById(Long id) throws ServiceRuntimeException;
    
    // public List<SimoUserRole>getUserRolesByUserId(Long userId) throws ServiceRuntimeException;

    public List<SimoUserRole> getUserRolesByUserId(String userId) throws ServiceRuntimeException;
    
    public List<SimoUserRole> getUserRolesByRoleId(Long roleId) throws ServiceRuntimeException;
    
    // public List<SimoRole> getRolesByUserId(Long userId) throws ServiceRuntimeException;
    
    // public List<SimoUser> getUsersByRoleId(Long roleId) throws ServiceRuntimeException;
    
    public boolean removeRoleFromUser(Long userId, Long roleId) throws ServiceRuntimeException;
    
    public boolean removeAllRolesFromUser(Long userId) throws ServiceRuntimeException;
    
    public boolean removeAllUsersFromRole(Long roleId) throws ServiceRuntimeException;

    public List<SimoUserRole> getUserRoleByParams(Map<String, Object> inputParams) throws ServiceRuntimeException;
}