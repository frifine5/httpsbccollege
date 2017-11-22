package com.interceptor.s;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Logger.getLogger(this.getClass()).info("SecurityInterceptor preHandle:" + request.getContextPath() + ","
				+ request.getRequestURI() + "," + request.getMethod());
		HttpSession session = request.getSession();
		// 假设用户登录令牌属性是SESSION_USER
		String SESSION_USER = "SESSION_USER";
		// 假设权限功能参数属性名是 PARAM_FUNCTION_ID
		String PARAM_FUNCTION_ID = "PARAM_FUNCTION_ID";
		if (session.getAttribute(SESSION_USER) == null) {
			Logger.getLogger(this.getClass()).info("AuthorizationException:未登录！" + request.getMethod());
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				// 传输json字符串
				out.write("");
				out.flush();
				out.close();
			} else {// 重定向到登录
				response.sendRedirect(request.getContextPath() + "/login");
			}
			return false;
		} else {
			Object obj = request.getParameter(PARAM_FUNCTION_ID);
			if (obj == null) {
				return true;// 没有带功能参数不需要验证
			}
			int functionId = Integer.parseInt(obj.toString());
			String rs = "rs" + functionId;
			Logger.getLogger(this.getClass()).info("校验结果:" + rs);
			if (rs.trim().isEmpty()) {
				return true;// 正常通过
			} else {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				// 传输json字符串
				out.write("");
				out.flush();
				out.close();
				return false;
			}
		}
	}

	// 模块权限值
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		modelAndView.addObject("madule", 1);
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

}
