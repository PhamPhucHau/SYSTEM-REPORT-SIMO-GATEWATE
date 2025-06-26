package com.org.shbvn.svbsimo.core.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.org.shbvn.svbsimo.repository.model.UserInfo;
import com.org.shbvn.svbsimo.core.constant.APIConstant;

/**
 * Utility class to store and retrieve user profile information from the request context
 */
public class UserContextHolder {

    /**
     * Store user profile in the current request context
     * 
     * @param userInfo The user profile to store
     */
    public static void setUserProfile(UserInfo userInfo) {
        RequestContextHolder.currentRequestAttributes()
            .setAttribute(APIConstant.USERPROFILE_KEY, userInfo, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Get user profile from the current request context
     * 
     * @return The user profile or null if not found
     */
    public static UserInfo getUserProfile() {
        return (UserInfo) RequestContextHolder.currentRequestAttributes()
            .getAttribute(APIConstant.USERPROFILE_KEY, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * Get username from the current request context
     * 
     * @return The username or null if not found
     */
    public static String getUsername() {
        UserInfo userInfo = getUserProfile();
        return userInfo != null ? userInfo.getUsername() : null;
    }
}