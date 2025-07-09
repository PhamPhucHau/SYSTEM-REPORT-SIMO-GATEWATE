package com.org.shbvn.svbsimo.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.org.shbvn.svbsimo.core.common.AbstractService;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.core.exception.UnauthorizedException;
import com.org.shbvn.svbsimo.core.exception.ServiceInvalidAgurmentException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.core.utils.SecurUtils;
import com.org.shbvn.svbsimo.core.utils.UserContextHolder;
import com.org.shbvn.svbsimo.repository.entities.SimoRole;
import com.org.shbvn.svbsimo.repository.model.UserInfo;
import com.org.shbvn.svbsimo.service.AuthService;

@Service("authService")
public class AuthServiceImpl extends AbstractService implements AuthService {

    @Override
    public UserInfo generateUserToken(Map<String, Object> inputParams) throws BaseException {

        String password = (String) inputParams.get("password");
        String username = (String) inputParams.get("username");

        if (CommonUtils.isBlank(password) || CommonUtils.isBlank(username)) {
            throw new UnauthorizedException("MSG_121");
        }

        // Get user profile from cache
        UserInfo userInfo = UserContextHolder.getUserProfile();
        if (userInfo == null) {
            userInfo = getRepositoryManageService().getSimoAuthMenuRepositoryService().getUserProfileByUserName(username);
        }
        
        if (userInfo == null || getRepositoryManageService().getSimoUserRepositoryService().verifyUser(inputParams) == null) {
            throw new UnauthorizedException("MSG_121");
        }
        
        String token = SecurUtils.getCryptUser(env.getProperty(APIConstant.PUBLIC_KEY), userInfo.getUsername());
        userInfo.setToken(token);
        
        return userInfo;
    }

    @Override
    public UserInfo getUserProfile(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfile'");
    }

    @Override
    public UserInfo updateUserProfilePasswordByToken(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfilePasswordByToken'");
    }

    @Override
    public UserInfo checkPermissionURL(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkPermissionURL'");
    }

    @Override
    public UserInfo getUserProfileByUserName(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProfileByUserName'");
    }

    @Override
    public UserInfo createUserProfile(Map<String, Object> inputParams) throws BaseException {
        String username = (String) inputParams.get("username");
        String password = (String) inputParams.get("password");
        String email = (String) inputParams.get("email");
        String rolename = (String) inputParams.get("role");
        // Validate đầu vào
        if (CommonUtils.isBlank(username) || CommonUtils.isBlank(password) || CommonUtils.isBlank(email)) {
            throw new ServiceInvalidAgurmentException("MSG_121"); // hoặc define mã riêng ví dụ "MSG_201" cho tạo user lỗi
        }
    
        try {
            // Đăng ký user mới
            Long userId = getRepositoryManageService()
                .getSimoUserRepositoryService()
                .registerUser(inputParams);
    
            if (userId == null || userId <= 0) {
                throw new ServiceInvalidAgurmentException("MSG_002"); // Không lưu được user
            }
            //Long roleId = getRepositoryManageService().getSimoRoleRepositoryService().getRoleById(userId)
            // Create Role user 

            SimoRole role = getRepositoryManageService().getSimoRoleRepositoryService().getRoleByName(rolename);
            
            getRepositoryManageService().getSimoUserRoleRepositoryService().assignRoleToUser(userId,  role.getId() );
                // Lấy thông tin user đã tạo
                UserInfo userInfo = getRepositoryManageService()
                .getSimoAuthMenuRepositoryService()
                .getUserProfileByUserName(username);
    
            if (userInfo == null) {
                throw new ServiceInvalidAgurmentException("MSG_121"); // Không tìm thấy user vừa tạo
            }
            return userInfo;
    
        } catch (BaseException bex) {
            throw bex; // nếu đã là BaseException, ném lại
        } catch (Exception ex) {
            logger.error("Error creating user profile", ex);
            throw new ServiceInvalidAgurmentException("MSG_002"); // lỗi hệ thống
        }
    }

    @Override
    public UserInfo activeUserProfile(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activeUserProfile'");
    }

    @Override
    public UserInfo deactiveUserProfile(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deactiveUserProfile'");
    }

    @Override
    public UserInfo updateUserProfilePassword(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfilePassword'");
    }

    @Override
    public UserInfo updateUserProfileRole(Map<String, Object> inputParams) throws BaseException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfileRole'");
    }

    @Override
    public String generateHashingValue(String sourceStr) throws BaseException {
        return SecurUtils.encrytePassword(sourceStr);
    }
    
}
