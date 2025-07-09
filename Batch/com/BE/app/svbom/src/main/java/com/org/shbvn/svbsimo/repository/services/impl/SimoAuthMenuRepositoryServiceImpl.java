package com.org.shbvn.svbsimo.repository.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.core.utils.DateUtils;
import com.org.shbvn.svbsimo.core.utils.EntityModelConverter;
import com.org.shbvn.svbsimo.repository.dao.SimoAuthMenuDAO;
import com.org.shbvn.svbsimo.repository.entities.SimoAuthMenu;
import com.org.shbvn.svbsimo.repository.model.RoleInfo;
import com.org.shbvn.svbsimo.repository.model.UserFeatureInfo;
import com.org.shbvn.svbsimo.repository.model.UserInfo;
import com.org.shbvn.svbsimo.repository.services.SimoAuthMenuRepositoryService;
import jakarta.persistence.Query;

@Service("simoAuthMenuRepositoryService")
public class SimoAuthMenuRepositoryServiceImpl extends AbstractService implements SimoAuthMenuRepositoryService {

    private final SimoAuthMenuDAO menuDAO;
    private final GenericCacheService cacheService;

    @Autowired
    public SimoAuthMenuRepositoryServiceImpl(SimoAuthMenuDAO menuDAO, GenericCacheService cacheService) {
        this.menuDAO = menuDAO;
        this.cacheService = cacheService;
    }

    @Override
    public Long createMenu(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            String menuId = (String) payload.get("menuId");
            String roleId = (String) payload.get("roleId");
            String menuName = (String) payload.get("menuName");
            
            SimoAuthMenu menu = new SimoAuthMenu();
            menu.setMenuId(menuId);
            menu.setRoleId(roleId);
            menu.setMenuName(menuName);
            menu.setStatus("A");
            
            Long id = menuDAO.save(menu).getId();
            
            // Invalidate related caches
            invalidateMenuCaches(menu);
            
            return id;
        } catch (Exception ex) {
            logger.error("Error creating menu", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public boolean updateMenu(Map<String, Object> payload) throws ServiceRuntimeException {
        try {
            String menuId = (String) payload.get("menuId");
            
            Optional<SimoAuthMenu> menuOpt = menuDAO.findByMenuId(menuId);
            if (!menuOpt.isPresent()) {
                return false;
            }
            
            SimoAuthMenu menu = menuOpt.get();
            String oldRoleId = menu.getRoleId(); // Store old roleId for cache invalidation
            
            if (payload.containsKey("menuName")) {
                menu.setMenuName((String) payload.get("menuName"));
            }
            
            if (payload.containsKey("roleId")) {
                menu.setRoleId((String) payload.get("roleId"));
            }
            
            if (payload.containsKey("status")) {
                menu.setStatus((String) payload.get("status"));
            }
            
            menu.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            menuDAO.save(menu);
            
            // Invalidate related caches
            invalidateMenuCaches(menu);
            
            // If roleId changed, also invalidate old role's cache
            if (oldRoleId != null && !oldRoleId.equals(menu.getRoleId())) {
                cacheService.invalidate("menus:roleId:" + oldRoleId);
            }
            
            return true;
        } catch (Exception ex) {
            logger.error("Error updating menu", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public SimoAuthMenu getMenuById(Long id) throws ServiceRuntimeException {
        String cacheKey = "menu:id:" + id;
        
        try {
            return cacheService.getOrCompute(
                cacheKey,
                () -> menuDAO.findById(id).orElse(null),
                3600, // Cache for 1 hour
                SimoAuthMenu.class
            );
        } catch (Exception ex) {
            logger.error("Error getting menu by ID", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public SimoAuthMenu getMenuByMenuId(String menuId) throws ServiceRuntimeException {
        String cacheKey = "menu:menuId:" + menuId;
        
        try {
            return cacheService.getOrCompute(
                cacheKey,
                () -> menuDAO.findByMenuId(menuId).orElse(null),
                3600, // Cache for 1 hour
                SimoAuthMenu.class
            );
        } catch (Exception ex) {
            logger.error("Error getting menu by menuId", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public List<SimoAuthMenu> getMenusByRoleId(String roleId) throws ServiceRuntimeException {
        String cacheKey = "menus:roleId:" + roleId;
        
        try {
            return cacheService.getOrComputeList(
                cacheKey,
                () -> menuDAO.findActiveByRoleId(roleId),
                1800 // Cache for 30 minutes
            );
        } catch (Exception ex) {
            logger.error("Error getting menus by roleId", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public List<SimoAuthMenu> getMenusByRoleIds(List<String> roleIds) throws ServiceRuntimeException {
        // Create a cache key based on sorted role IDs to ensure consistency
        String roleIdsStr = roleIds.stream().sorted().collect(Collectors.joining(","));
        String cacheKey = "menus:roleIds:" + roleIdsStr;
        
        try {
            return cacheService.getOrComputeList(
                cacheKey,
                () -> menuDAO.findActiveByRoleIds(roleIds),
                1800 // Cache for 30 minutes
            );
        } catch (Exception ex) {
            logger.error("Error getting menus by roleIds", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public boolean deleteMenu(String menuId) throws ServiceRuntimeException {
        try {
            Optional<SimoAuthMenu> menuOpt = menuDAO.findByMenuId(menuId);
            if (!menuOpt.isPresent()) {
                return false;
            }
            
            SimoAuthMenu menu = menuOpt.get();
            menu.setStatus("D");
            menu.setLchgInfDt(DateUtils.getSystemDateStr(DateUtils.yyyyMMddHHmmss));
            menuDAO.save(menu);
            
            // Invalidate related caches
            invalidateMenuCaches(menu);
            
            return true;
        } catch (Exception ex) {
            logger.error("Error deleting menu", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    @Override
    public List<SimoAuthMenu> getListAuthMenuByUserId(String userId) throws ServiceRuntimeException {
        try {
            String sql = simoNamedQueries.get("getMenuAuthByUserId");
            Query query = entityManager.createNativeQuery(sql, SimoAuthMenu.class);
            query.setParameter("USER_ID", userId);
            @SuppressWarnings("unchecked")
            List<SimoAuthMenu> list = query.getResultList();
            logger.info("getListAuthMenuByUserId: {}", CommonUtils.toJson(list));
            return list;
        } catch (Exception ex) {
            logger.error("Error getting menus by user ID", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }
    
    @Override
    public UserInfo getUserProfileByUserName(String username) throws ServiceRuntimeException {
        // Cache key for user profile
        String cacheKey = "user:profile:" + username;
        
        try {
            // Try to get from cache first
            return cacheService.getOrCompute(
                cacheKey,
                () -> {
                    // Cache miss - fetch from database
                    try {
                        UserInfo  userInfo = EntityModelConverter.toModel(
                            getRepositoryManageService().getSimoUserRepositoryService().getUserByUsername(username), UserInfo.class);

                            if (userInfo == null) {
                            return null;
                        }
                        List<UserFeatureInfo> list = EntityModelConverter.toModelList(
                                                        getListAuthMenuByUserId(username), UserFeatureInfo.class);
                        if (list == null || list.isEmpty()) {
                            return null;
                        }
                        
                        List<RoleInfo> listRole = EntityModelConverter.toModelList(
                                                        getRepositoryManageService().getSimoUserRoleRepositoryService().getUserRolesByUserId(username), RoleInfo.class);
                        if (listRole == null || listRole.isEmpty()) {
                            return null;
                        }

                        
                        userInfo.setFeatures(list);
                        userInfo.setRoles(listRole);
                        
                        return userInfo;
                    } catch (Exception ex) {
                        logger.error("Error getting user profile by username from database", ex);
                        return null;
                    }
                },
                3600, // Cache for 1 hour
                UserInfo.class
            );
        } catch (Exception ex) {
            logger.error("Error getting user profile by username", ex);
            throw new ServiceRuntimeException(env.getProperty("MSG_002"));
        }
    }

    /**
     * Invalidate user profile cache
     * 
     * @param username Username whose cache should be invalidated
     */
    public void invalidateUserProfileCache(String username) {
        if (username != null && !username.isEmpty()) {
            String cacheKey = "user:profile:" + username;
            cacheService.invalidate(cacheKey);
            logger.debug("Invalidated cache for user profile: {}", username);
        }
    }

    /**
     * Invalidate user menus cache
     * 
     * @param userId User ID whose menu cache should be invalidated
     */
    public void invalidateUserMenusCache(String userId) {
        if (userId != null && !userId.isEmpty()) {
            String cacheKey = "user:menus:" + userId;
            cacheService.invalidate(cacheKey);
            logger.debug("Invalidated cache for user menus: {}", userId);
        }
    }

    /**
     * Helper method to invalidate all caches related to a menu
     * 
     * @param menu The menu whose caches should be invalidated
     */
    private void invalidateMenuCaches(SimoAuthMenu menu) {
        if (menu == null) return;
        
        // Invalidate specific menu caches
        if (menu.getId() != null) {
            cacheService.invalidate("menu:id:" + menu.getId());
        }
        
        if (menu.getMenuId() != null) {
            cacheService.invalidate("menu:menuId:" + menu.getMenuId());
        }
        
        // Invalidate role-based menu caches
        if (menu.getRoleId() != null) {
            cacheService.invalidate("menus:roleId:" + menu.getRoleId());
        }
        
        // Invalidate any user menu caches that might contain this menu
        // This is a pattern-based deletion that might be expensive if there are many users
        // Consider more targeted invalidation in a production environment
        Set<String> userMenuKeys = cacheService.getRedisCacheService().keys("user:menus:*");
        if (userMenuKeys != null && !userMenuKeys.isEmpty()) {
            userMenuKeys.forEach(key -> cacheService.invalidate(key));
        }
        
        // Also invalidate role IDs list caches that might contain this role
        Set<String> roleIdsMenuKeys = cacheService.getRedisCacheService().keys("menus:roleIds:*");
        if (roleIdsMenuKeys != null && !roleIdsMenuKeys.isEmpty()) {
            roleIdsMenuKeys.forEach(key -> cacheService.invalidate(key));
        }
    }

}
