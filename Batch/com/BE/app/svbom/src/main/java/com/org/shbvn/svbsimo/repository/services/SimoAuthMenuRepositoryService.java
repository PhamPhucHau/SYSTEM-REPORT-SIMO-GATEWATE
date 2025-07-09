package com.org.shbvn.svbsimo.repository.services;

import java.util.List;
import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.repository.entities.SimoAuthMenu;
import com.org.shbvn.svbsimo.repository.model.UserInfo;

public interface SimoAuthMenuRepositoryService {
    
    public Long createMenu(Map<String, Object> payload) throws ServiceRuntimeException;
    
    public boolean updateMenu(Map<String, Object> payload) throws ServiceRuntimeException;
    
    public SimoAuthMenu getMenuById(Long id) throws ServiceRuntimeException;
    
    public SimoAuthMenu getMenuByMenuId(String menuId) throws ServiceRuntimeException;
    
    public List<SimoAuthMenu> getMenusByRoleId(String roleId) throws ServiceRuntimeException;
    
    public List<SimoAuthMenu> getMenusByRoleIds(List<String> roleIds) throws ServiceRuntimeException;
    
    public boolean deleteMenu(String menuId) throws ServiceRuntimeException;

    public List<SimoAuthMenu> getListAuthMenuByUserId(String userId) throws ServiceRuntimeException;

    public UserInfo getUserProfileByUserName(String username) throws ServiceRuntimeException;

}