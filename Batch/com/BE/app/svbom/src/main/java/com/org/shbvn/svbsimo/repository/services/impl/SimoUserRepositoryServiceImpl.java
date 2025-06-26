package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.core.utils.SecurUtils;
import com.org.shbvn.svbsimo.repository.dao.SimoUserDAO;
import com.org.shbvn.svbsimo.repository.entities.SimoUser;
import com.org.shbvn.svbsimo.repository.services.SimoUserRepositoryService;

@Service("simoUserRepositoryService")
public class SimoUserRepositoryServiceImpl extends AbstractService implements SimoUserRepositoryService {

    private final SimoUserDAO userDAO;

    @Autowired
    public SimoUserRepositoryServiceImpl(SimoUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Long registerUser(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            String username = (String) payload.get("username");
            String password = (String) payload.get("password");
            String email = (String) payload.get("email");
            
            SimoUser user = new SimoUser();
            user.setUsername(username);
            user.setPassword(SecurUtils.encrytePassword(password));
            user.setEmail(email);
            user.setStatus("A");
            return userDAO.save(user).getId();
            
        } catch (Exception ex) {
            logger.error("Error registering user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public SimoUser verifyUser(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            String username = (String) payload.get("username");
            String email = (String) payload.get("email");
            String password = (String) payload.get("password");
            
            Optional<SimoUser> userOpt = userDAO.findByUsernameOrEmail(username, email);
            if (!userOpt.isPresent()) {
                logger.error("User Not Found");
                return null;
            }
            
            SimoUser user = userOpt.get();
            if (SecurUtils.decrytePassword(password, user.getPassword())) {
                return user;
            }
            logger.error("Invalid Password {} - {}" , password, user.getPassword());
            return null;
        } catch (Exception ex) {
            logger.error("Error verifying user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public SimoUser getUserById(Long id) throws ServiceRuntimeException {
        try {
            return userDAO.findById(id).orElse(null);
        } catch (Exception ex) {
            logger.error("Error getting user by ID", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public SimoUser getUserByUsername(String username) throws ServiceRuntimeException {
        try {
            return userDAO.findByUsername(username).orElse(null);
        } catch (Exception ex) {
            logger.error("Error getting user by username", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public SimoUser getUserByEmail(String email) throws ServiceRuntimeException {
        try {
            return userDAO.findByEmail(email).orElse(null);
        } catch (Exception ex) {
            logger.error("Error getting user by email", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public List<SimoUser> getAllUsers() throws ServiceRuntimeException {
        try {
            return userDAO.findAll();
        } catch (Exception ex) {
            logger.error("Error getting all users", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public boolean updateUser(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            Long id = Long.valueOf(payload.get("id").toString());
            Optional<SimoUser> userOpt = userDAO.findById(id);
            if (!userOpt.isPresent()) {
                return false;
            }
            
            SimoUser user = userOpt.get();
            
            if (payload.containsKey("email")) {
                user.setEmail((String) payload.get("email"));
            }
            
            if (payload.containsKey("status")) {
                user.setStatus((String) payload.get("status"));
            }
            
            user.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            userDAO.save(user);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error updating user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public boolean deleteUser(Long id) throws ServiceRuntimeException {
        try {
            Optional<SimoUser> userOpt = userDAO.findById(id);
            if (!userOpt.isPresent()) {
                return false;
            }
            
            SimoUser user = userOpt.get();
            user.setStatus("D");
            user.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            userDAO.save(user);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error deleting user", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) throws ServiceRuntimeException {
        try {
            Optional<SimoUser> userOpt = userDAO.findByUsername(username);
            if (!userOpt.isPresent()) {
                return false;
            }
            
            SimoUser user = userOpt.get();
            if (!SecurUtils.decrytePassword(oldPassword, user.getPassword())) {
                return false;
            }
            
            user.setPassword(SecurUtils.encrytePassword(newPassword));
            user.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            userDAO.save(user);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error changing password", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
}
