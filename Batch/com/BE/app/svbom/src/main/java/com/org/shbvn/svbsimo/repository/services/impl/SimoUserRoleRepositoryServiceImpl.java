package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.repository.dao.SimoRoleDAO;
import com.org.shbvn.svbsimo.repository.dao.SimoUserDAO;
import com.org.shbvn.svbsimo.repository.dao.SimoUserRoleDAO;
import com.org.shbvn.svbsimo.repository.entities.SimoRole;
import com.org.shbvn.svbsimo.repository.entities.SimoUser;
import com.org.shbvn.svbsimo.repository.entities.SimoUserRole;
import com.org.shbvn.svbsimo.repository.services.SimoUserRoleRepositoryService;

import jakarta.persistence.Query;

@Service("simoUserRoleRepositoryService")
public class SimoUserRoleRepositoryServiceImpl extends AbstractService implements SimoUserRoleRepositoryService {

    private final SimoUserRoleDAO userRoleDAO;
    private final SimoUserDAO userDAO;
    private final SimoRoleDAO roleDAO;

    @Autowired
    public SimoUserRoleRepositoryServiceImpl(
            SimoUserRoleDAO userRoleDAO,
            SimoUserDAO userDAO,
            SimoRoleDAO roleDAO) {
        this.userRoleDAO = userRoleDAO;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public Long assignRoleToUser(Long userId, Long roleId) throws ServiceRuntimeException {
        try {
            Optional<SimoUser> userOpt = userDAO.findById(userId);
            Optional<SimoRole> roleOpt = roleDAO.findById(roleId);
            
            if (!userOpt.isPresent() || !roleOpt.isPresent()) {
                throw new ServiceRuntimeException("User or Role not found");
            }
            
            return assignRoleToUser(userOpt.get(), roleOpt.get());
        } catch (Exception ex) {
            logger.error("Error assigning role to user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public Long assignRoleToUser(SimoUser user, SimoRole role) throws ServiceRuntimeException {
        try {
            // Check if the assignment already exists
            Optional<SimoUserRole> existingOpt = userRoleDAO.findByUserAndRole(user.getUsername(), role.getRoleId());
            if (existingOpt.isPresent()) {
                SimoUserRole existing = existingOpt.get();
                if ("D".equals(existing.getStatus())) {
                    // Reactivate if it was deleted
                    existing.setStatus("A");
                    existing.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
                    userRoleDAO.save(existing);
                }
                return existing.getId();
            }
            
            // Create new assignment
            SimoUserRole userRole = new SimoUserRole();
            userRole.setUserId(user.getUsername());
            userRole.setRoleId(role.getRoleId());
            userRole.setStatus("A");
            userRole.setRegisInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            
            return userRoleDAO.save(userRole).getId();
        } catch (Exception ex) {
            logger.error("Error assigning role to user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public boolean updateUserRole(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            Long id = Long.valueOf(payload.get("id").toString());
            Optional<SimoUserRole> userRoleOpt = userRoleDAO.findById(id);
            if (!userRoleOpt.isPresent()) {
                return false;
            }
            
            SimoUserRole userRole = userRoleOpt.get();
            
            if (payload.containsKey("status")) {
                userRole.setStatus((String) payload.get("status"));
            }
            
            userRole.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            userRoleDAO.save(userRole);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error updating user role", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public SimoUserRole getUserRoleById(Long id) throws ServiceRuntimeException {
        try {
            return userRoleDAO.findById(id).orElse(null);
        } catch (Exception ex) {
            logger.error("Error getting user role by ID", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    // @Override
    // public List<SimoUserRole> getUserRolesByUserId(Long userId) throws ServiceRuntimeException {
    //     try {
            
    //         return userRoleDAO.findActiveByUserId(userId);
    //     } catch (Exception ex) {
    //         logger.error("Error getting user roles by user ID", ex);
    //         throw new ServiceRuntimeException(env.getProperty("MSG_002"));
    //     }
    // }

    @Override
    public List<SimoUserRole> getUserRolesByRoleId(Long roleId) throws ServiceRuntimeException {
        try {
            Optional<SimoRole> roleOpt = roleDAO.findById(roleId);
            if (!roleOpt.isPresent()) {
                return new ArrayList<>();
            }
            
            return userRoleDAO.findByRoleAndStatus(roleOpt.get().getRoleId(), "A");
        } catch (Exception ex) {
            logger.error("Error getting user roles by role ID", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    // @Override
    // public List<SimoRole> getRolesByUserId(Long userId) throws ServiceRuntimeException {
    //     try {
    //         List<SimoUserRole> userRoles = getUserRolesByUserId(userId);
    //         return userRoles.stream()
    //                 .map(SimoUserRole::getRole)
    //                 .collect(Collectors.toList());
    //     } catch (Exception ex) {
    //         logger.error("Error getting roles by user ID", ex);
    //         throw new ServiceRuntimeException(env.getProperty("MSG_002"));
    //     }
    // }

    // @Override
    // public List<SimoUser> getUsersByRoleId(Long roleId) throws ServiceRuntimeException {
    //     try {
    //         List<SimoUserRole> userRoles = getUserRolesByRoleId(roleId);
    //         return userRoles.stream()
    //                 .map(SimoUserRole::getUserId)
    //                 .collect(Collectors.toList());
    //     } catch (Exception ex) {
    //         logger.error("Error getting users by role ID", ex);
    //         throw new ServiceRuntimeException(env.getProperty("MSG_002"));
    //     }
    // }

    @Override
    public boolean removeRoleFromUser(Long userId, Long roleId) throws ServiceRuntimeException {
        try {
            Optional<SimoUser> userOpt = userDAO.findById(userId);
            Optional<SimoRole> roleOpt = roleDAO.findById(roleId);
            
            if (!userOpt.isPresent() || !roleOpt.isPresent()) {
                return false;
            }
            
            Optional<SimoUserRole> userRoleOpt = userRoleDAO.findByUserAndRole(userOpt.get().getUsername(), roleOpt.get().getRoleId());
            if (!userRoleOpt.isPresent()) {
                return false;
            }
            
            SimoUserRole userRole = userRoleOpt.get();
            userRole.setStatus("D");
            userRole.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            userRoleDAO.save(userRole);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error removing role from user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public boolean removeAllRolesFromUser(Long userId) throws ServiceRuntimeException {
        try {
            Optional<SimoUser> userOpt = userDAO.findById(userId);
            if (!userOpt.isPresent()) {
                return false;
            }
            
            List<SimoUserRole> userRoles = userRoleDAO.findByUserAndStatus(userOpt.get().getUsername(), "A");
            for (SimoUserRole userRole : userRoles) {
                userRole.setStatus("D");
                userRole.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            }
            
            userRoleDAO.saveAll(userRoles);
            return true;
        } catch (Exception ex) {
            logger.error("Error removing all roles from user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public boolean removeAllUsersFromRole(Long roleId) throws ServiceRuntimeException {
        try {
            Optional<SimoRole> roleOpt = roleDAO.findById(roleId);
            if (!roleOpt.isPresent()) {
                return false;
            }
            
            List<SimoUserRole> userRoles = userRoleDAO.findByRoleAndStatus(roleOpt.get().getRoleId(), "A");
            for (SimoUserRole userRole : userRoles) {
                userRole.setStatus("D");
                userRole.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            }
            
            userRoleDAO.saveAll(userRoles);
            return true;
        } catch (Exception ex) {
            logger.error("Error removing all users from role", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public List<SimoUserRole> getUserRoleByParams(Map<String, Object> inputParams) throws ServiceRuntimeException {
        try {
            String query = simoNamedQueries.get("getUserRoleByParams");
            Query nativeQuery = entityManager.createNativeQuery(query, SimoUserRole.class);
            if (inputParams.get("userId") != null) {
                nativeQuery.setParameter("USER_ID", inputParams.get("userId"));
            }
            if (inputParams.get("roleId") != null) {
                nativeQuery.setParameter("ROLE_ID", inputParams.get("roleId"));
            }
            if (inputParams.get("status") != null) {
                nativeQuery.setParameter("status", inputParams.get("status"));
            }
            @SuppressWarnings("unchecked")
            List<SimoUserRole> list = nativeQuery.getResultList();
            return list;
        } catch (Exception ex) {
            logger.error("Error getting user role by params", ex);
            throw new ServiceRuntimeException("MSG_002");
        }
    }

    @Override
    public List<SimoUserRole> getUserRolesByUserId(String userId) throws ServiceRuntimeException {
        try {
            Optional<SimoUser> userOpt = userDAO.findByUsername(userId);
            if (!userOpt.isPresent()) {
                return new ArrayList<>();
            }
            
            return userRoleDAO.findByUserAndStatus(userOpt.get().getUsername(), "A");
        } catch (Exception ex) {
            logger.error("Error getting user roles by user ID", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
}