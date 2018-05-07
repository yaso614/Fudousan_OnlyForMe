package com.real.fudousan.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class InteriorInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(InteriorInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("InteriorInterceptor preHandler Start");
		boolean result = super.preHandle(request, response, handler);
		
		HttpSession session = request.getSession();
		Integer permissionId = (Integer) session.getAttribute("permissionId");
		
		logger.debug("InteriorInterceptor preHandler session(permissionId) : " + permissionId);
		if (permissionId != null && (permissionId == 2 || permissionId == 99)) {
			result &= true;
		} else {
			result = false;
		}
		
		logger.debug("InteriorInterceptor preHandler result : " + result);
		if (!result) {
			response.sendRedirect(request.getContextPath()+"/");
		}
		logger.info("InteriorInterceptor preHandler End");
		return result;
	}

	
}
