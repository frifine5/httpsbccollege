package com.filter.s;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * 拦截uri的过滤器
* @author: kingcrab
* @create 2017年6月28日 上午10:01:14
* @version V1.0
* @since JDK 1.8
*
 */
public class UriFilter implements Filter {
	
	@Override
	public void destroy() {
		Logger.getLogger(this.getClass()).info(  "拦截器销毁" );
	}

	/**
	 * 这里可以做很多过滤，比如登录用户拦截、访问url拦截、日志记录等
	 */
	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		String  msg = "["+this.getClass().getSimpleName()+"]"+request.getRequestURI() ;
		Logger.getLogger(this.getClass()).info(msg);
		filterChain.doFilter(srequest, sresponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		Logger.getLogger(this.getClass()).info("初始化自定义拦截器"+this.getClass().getSimpleName());
	}
}
