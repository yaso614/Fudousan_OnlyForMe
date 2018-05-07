package com.real.fudousan.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandler Start");
		boolean result = super.preHandle(request, response, handler);
		
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("loginId");
		
		logger.debug("preHandler session(loginId) : " + id);
		if (id != null) {
			result &= true;
		} else {
			result = false;
		}
		
		logger.debug("preHandler result : " + result);
		if (!result) {
			response.sendRedirect(request.getContextPath()+"/");
		}
		logger.info("preHandler End");
		return result;
	}

	
}
