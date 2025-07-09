/**
 * 
 */
package com.org.shbvn.svbsimo.configure;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.org.shbvn.svbsimo.core.constant.APIConstant;
import com.org.shbvn.svbsimo.core.exception.UnauthorizedException;
import com.org.shbvn.svbsimo.core.utils.CommonUtils;
import com.org.shbvn.svbsimo.core.utils.SecurUtils;
import com.org.shbvn.svbsimo.core.utils.UserContextHolder;
import com.org.shbvn.svbsimo.repository.model.UserFeatureInfo;
import com.org.shbvn.svbsimo.repository.model.UserInfo;
import com.org.shbvn.svbsimo.repository.services.SimoAuthMenuRepositoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SimoInterceptor implements HandlerInterceptor {
	
	@Autowired
	public Environment env;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private SimoAuthMenuRepositoryService simoAuthMenuRepositoryService;

	@Autowired
	public void setSimoAuthMenuRepositoryService(SimoAuthMenuRepositoryService simoAuthMenuRepositoryService) {
		this.simoAuthMenuRepositoryService = simoAuthMenuRepositoryService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(request.getAttribute(APIConstant.HTTP_REQUEST_BODY_STR) != null && HttpStatus.OK.value() != response.getStatus()){
			//logger.info("Body : " + request.getAttribute(APIConstant.HTTP_REQUEST_BODY_STR));
			saveTrackingLogRequestToDB(request, response.getStatus());
		}
		
		logger.info("***** After completion handle *****");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		int statusCode = response.getStatus();
		saveTrackingLogRequestToDB(request, statusCode);
		logger.info("***** Post completion handle *****");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		logger.info("***** Begin Pre Interceptor *****");
		logger.info("======= Begin Transaction =======");
		logger.info(" ***** URL : " + request.getRequestURI() + " ***** ");
		logger.info(" ***** Method : " + request.getMethod() + " ***** ");
		logger.info(" ***** At : " + new Date());
		long startTime = System.currentTimeMillis();
		String userName = request.getHeader(APIConstant.USERNAME_KEY);
		String URI = request.getRequestURI();
		boolean flag = URI.contains(APIConstant.CONTEXT_FILTER_PATH);
		UserInfo userInfo = null;
		logger.info(" ***** userName : " + userName + " ***** ");
		String whitelist = env.getProperty(APIConstant.SVB_SECURITY_WHITELIST);		
		String[] whitelistArray = whitelist != null ? whitelist.split(",") : new String[0];
		// Check whitelist 

		// ✅ Bypass các URL nằm trong whitelist cấu hình
		for (String path : whitelistArray) {
			if (URI.equals(path) || URI.startsWith(path + "/")) {
				
			request.setAttribute(APIConstant.REQUEST_URI_STR, request.getRequestURI());
			writeLogHttpSerlvetRequest(request);
			request.setAttribute(APIConstant.EXECUTION_TIME_KEY, startTime);
				return true;
			}
		}
		
		if (flag) {
			
			
			if (CommonUtils.isBlank(userName)) {
				throw new UnauthorizedException("MSG_121");
			}
			
			userInfo = simoAuthMenuRepositoryService.getUserProfileByUserName(userName);
			if (userInfo == null || 
					userInfo.getRoles() == null || 
					userInfo.getRoles().isEmpty() || 
					userInfo.getFeatures() == null || 
					userInfo.getFeatures().isEmpty()) {
				throw new UnauthorizedException("MSG_123");
			}

			
			UserFeatureInfo userFeatureInfo = userInfo.getFeatures().stream()
				.filter(f ->  URI.endsWith(f.getMenuUrl()) && f.getMethod().equals(request.getMethod()))
				.findFirst().orElse(null);
			if (userFeatureInfo == null) {
				throw new UnauthorizedException("MSG_123");
			}

			if (userFeatureInfo.getMode().equals(APIConstant.AUTH_MODE)) {
				String token = request.getHeader(APIConstant.ACCESS_TOKEN_KEY);
				if (CommonUtils.isBlank(token)) {
					throw new UnauthorizedException(APIConstant.THE_TOKEN_IS_BLANK_ERROR);
				}

				JsonObject jsonToken = (JsonObject) CommonUtils.toPojo(
					SecurUtils.checkSecureTheToken(token, env.getProperty(APIConstant.PUBLIC_KEY)), 
					JsonObject.class);
			
				// Parse the token to get userName;
				userName = jsonToken.get(APIConstant.USERNAME_KEY).getAsString();
				if (CommonUtils.isBlank(userName) || !userName.equalsIgnoreCase(userInfo.getUsername())) {
					throw new UnauthorizedException("MSG_123");
				}
			}
		} else {
			throw new UnauthorizedException("MSG_122");
		}
		
		// Store in both request attributes and context holder
		request.setAttribute(APIConstant.USERPROFILE_KEY, userInfo);
		UserContextHolder.setUserProfile(userInfo);
		
		request.setAttribute(APIConstant.REQUEST_URI_STR, request.getRequestURI());
		writeLogHttpSerlvetRequest(request);
		request.setAttribute(APIConstant.EXECUTION_TIME_KEY, startTime);

		return true;
	}
	
	private void saveTrackingLogRequestToDB(HttpServletRequest request, int statusCode){
		long starttime = (long) request.getAttribute(APIConstant.EXECUTION_TIME_KEY);
		long endTime = System.currentTimeMillis();
		long spentTime = (endTime - starttime);
		if(request.getMethod().equals(HttpMethod.POST.toString()) || request.getMethod().equals(HttpMethod.PATCH.toString())){
			if(request.getAttribute(APIConstant.HTTP_REQUEST_BODY_STR) != null){
				ObjectMapper objectMapper = new ObjectMapper();
				String json = request.getAttribute(APIConstant.HTTP_REQUEST_BODY_STR).toString();
				try{
					JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
					logger.info("Body : " + jsonNode.toString());
				}catch(Exception e){
					logger.info("Body : " + json);
				}
			}
		}
		logger.info("Time Execution : " + spentTime);
		logger.info("At : " + new Date());
		logger.info("======= End Transaction =======");
	}
	
	public void writeLogHttpSerlvetRequest(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {
		Map<?, ?> params = httpServletRequest.getParameterMap();
		Iterator<?> i = params.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			if(params.containsKey(key)){
				String[] strs = (String[]) params.get(key);
				if(strs != null && strs.length > 0){
					for(String str : strs){
						logger.info("Key : " + key);
						logger.info("Value : " + str);
						logger.info("Value Decode UTF-8 : " + URLDecoder.decode(str, APIConstant.UTF_8_CHARSET_TYPE));
						logger.info("Value Encode UTF-8 : " + URLEncoder.encode(str, APIConstant.UTF_8_CHARSET_TYPE));
					}
				}
			}
			//String value = params.get(key) != null ? ((String[]) params.get(key))[0] : "";
			
		}
	}
}
