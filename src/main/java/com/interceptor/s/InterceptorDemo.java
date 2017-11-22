package com.interceptor.s;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义拦截器Demo
 *
 */
public class InterceptorDemo implements HandlerInterceptor {

	/**
	 * 拦截的handle前处理
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	Logger.getLogger(this.getClass()).info(">>>InterceptorDemo>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
    	
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    /**
     * 拦截的handle处理后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	HttpSession session = request.getSession();
    	String seId = session.getId();
    	Logger.getLogger(this.getClass()).info("sessionId=" + seId);
        Logger.getLogger(this.getClass()).info(">>>InterceptorDemo>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    /**
     * 视图渲染之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Logger.getLogger(this.getClass()).info(">>>InterceptorDemo>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

    
}
