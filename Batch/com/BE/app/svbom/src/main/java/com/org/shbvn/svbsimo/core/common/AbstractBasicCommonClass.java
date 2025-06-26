/**
 * 
 */
package com.org.shbvn.svbsimo.core.common;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;


public abstract class AbstractBasicCommonClass extends AbstractService{
	@Autowired
	protected HttpServletRequest httpServletRequest;

	@Autowired
	protected HttpServletResponse httpServletResponse;
	
	protected String testException() throws ServiceRuntimeException {
		try {
			String a = "";
			String c = "";
			int b = Integer.parseInt(a) + Integer.parseInt(c);
			return String.valueOf(b);
		} catch (Exception e) {
			throw new ServiceRuntimeException("MSG_001");
		}
	}
}
