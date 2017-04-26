package com.supermy.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class UserSecurityInterceptor implements HandlerInterceptor{
	
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	//在请求处理之前进行调用（Controller方法调用之前）
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//登录验证
		Object verifyUser = request.getSession().getAttribute("verify_user");
		verifyUser=handler;
		if(verifyUser!=null){
			//tel存在验证
			Object tel = request.getSession().getAttribute("tel");
			if(tel==null){
				response.sendRedirect(request.getContextPath()+"/tocheck");
				return false;
			}else{
				return true;
			}
		}else{
			response.sendRedirect(request.getContextPath()+"/static/interface.html");
			return false;
		}
	}
	
}
