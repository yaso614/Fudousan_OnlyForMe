package com.real.fudousan.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("AdminInterceptor preHandler Start");
		boolean result = super.preHandle(request, response, handler);
		
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("loginId");
		
		logger.debug("AdminInterceptor preHandler session(loginId) : " + id);
		if (id != null && id == 1) {
			result &= true;
		} else {
			result = false;
		}
		
		logger.debug("AdminInterceptor preHandler result : " + result);
		if (!result) {
			response.sendRedirect(request.getContextPath()+"/");
		}
		logger.info("AdminInterceptor preHandler End");
		return result;
	}

	
}
