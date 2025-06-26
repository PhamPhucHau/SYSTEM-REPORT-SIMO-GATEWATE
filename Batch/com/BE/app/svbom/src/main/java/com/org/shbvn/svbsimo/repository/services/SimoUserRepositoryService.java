package com.org.shbvn.svbsimo.repository.services;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.entities.SimoUser;


public interface SimoUserRepositoryService {

    public Long registerUser(Map<String, Object> payload) throws ServiceRuntimeException;

    public SimoUser verifyUser(Map<String, Object> payload) throws ServiceRuntimeException;
    
    public SimoUser getUserById(Long id) throws ServiceRuntimeException;
    
    public SimoUser getUserByUsername(String username) throws ServiceRuntimeException;
    
    public SimoUser getUserByEmail(String email) throws ServiceRuntimeException;
    
    public List<SimoUser> getAllUsers() throws ServiceRuntimeException;
    
    public boolean updateUser(Map<String, Object> payload) throws ServiceRuntimeException;
    
    public boolean deleteUser(Long id) throws ServiceRuntimeException;
    
    public boolean changePassword(String username, String oldPassword, String newPassword) throws ServiceRuntimeException;
}
