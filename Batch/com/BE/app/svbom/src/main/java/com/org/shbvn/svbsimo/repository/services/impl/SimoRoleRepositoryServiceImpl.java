package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.repository.dao.SimoRoleDAO;
import com.org.shbvn.svbsimo.repository.entities.SimoRole;
import com.org.shbvn.svbsimo.repository.services.SimoRoleRepositoryService;

import jakarta.persistence.Query;

@Service("simoRoleRepositoryService")
public class SimoRoleRepositoryServiceImpl extends AbstractService implements SimoRoleRepositoryService {

    private final SimoRoleDAO roleDAO;

    @Autowired
    public SimoRoleRepositoryServiceImpl(SimoRoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public Long createRole(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            String roleId = (String) payload.get("roleId");
            String configuration = (String) payload.get("configuration");
            
            SimoRole role = new SimoRole();
            role.setRoleId(roleId);
            role.setConfiguration(configuration);
            role.setStatus("A");
            
            return roleDAO.save(role).getId();
        } catch (Exception ex) {
            logger.error("Error creating role", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public boolean updateRole(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            String roleId = (String) payload.get("roleId");
            
            Optional<SimoRole> roleOpt = roleDAO.findByRoleId(roleId);
            if (!roleOpt.isPresent()) {
                return false;
            }
            
            SimoRole role = roleOpt.get();
            
            if (payload.containsKey("configuration")) {
                role.setConfiguration((String) payload.get("configuration"));
            }
            
            if (payload.containsKey("status")) {
                role.setStatus((String) payload.get("status"));
            }
            
            role.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            roleDAO.save(role);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error updating role", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public SimoRole getRoleById(Long id) throws ServiceRuntimeException {
        try {
            return roleDAO.findById(id).orElse(null);
        } catch (Exception ex) {
            logger.error("Error getting role by ID", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public SimoRole getRoleByRoleId(String roleId) throws ServiceRuntimeException {
        try {
            return roleDAO.findByRoleId(roleId).orElse(null);
        } catch (Exception ex) {
            logger.error("Error getting role by roleId", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public List<SimoRole> getAllRoles() throws ServiceRuntimeException {
        try {
            return roleDAO.findAll();
        } catch (Exception ex) {
            logger.error("Error getting all roles", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public List<SimoRole> getActiveRoles() throws ServiceRuntimeException {
        try {
            return roleDAO.findActiveRoles();
        } catch (Exception ex) {
            logger.error("Error getting active roles", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public boolean deleteRole(String roleId) throws ServiceRuntimeException {
        try {
            Optional<SimoRole> roleOpt = roleDAO.findByRoleId(roleId);
            if (!roleOpt.isPresent()) {
                return false;
            }
            
            SimoRole role = roleOpt.get();
            role.setStatus("D");
            role.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            roleDAO.save(role);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error deleting role", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    } 

    @Override
    public List<SimoRole> getRoleByParams(Map<String, Object> inputParams) throws ServiceRuntimeException {
        try {
            String sql = simoNamedQueries.get("getRoleByParams");
            Query query = entityManager.createNativeQuery(sql, SimoRole.class);
            query.setParameter("ROLE_ID", inputParams.get("roleId"));
            query.setParameter("status", inputParams.get("status"));
            @SuppressWarnings("unchecked")
            List<SimoRole> list = query.getResultList();
            return list;
        } catch (Exception ex) {
            logger.error("Error getting role by params", ex);
            throw new ServiceRuntimeException("MSG_002");
        }
    }

}