package com.org.shbvn.svbsimo.service;

import java.util.Map;

import com.org.shbvn.svbsimo.core.exception.BaseException;
import com.org.shbvn.svbsimo.repository.model.UserInfo;

public interface AuthService {

    public UserInfo generateUserToken(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo getUserProfile(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo updateUserProfilePasswordByToken(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo checkPermissionURL(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo getUserProfileByUserName(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo createUserProfile(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo activeUserProfile(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo deactiveUserProfile(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo updateUserProfilePassword(Map<String, Object> inputParams) throws BaseException;
	
	public UserInfo updateUserProfileRole(Map<String, Object> inputParams) throws BaseException;

	public String generateHashingValue(String sourceStr) throws BaseException; // Generate Hashing Value

}
