package com.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.filter.s.UriFilter;
/**
 * 
 * 类的详细说明: 配置web的过滤器实例bean
 * @author kingcrab
 * @创建日期：2017年11月16日 上午11:51:29
 * @历史信息：new
 *
 */
@Configuration
public class WebConfiguration {
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
    
    /**
     * 启动拦截器uri打印
     * @return
     */
    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new UriFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("uriFilter");
        registration.setOrder(1);
        return registration;
    }
    
    
}
