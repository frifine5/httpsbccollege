package com.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.interceptor.s.InterceptorDemo;
import com.interceptor.s.SecurityInterceptor;

@Configuration
public class MyWebAppConfigurer 
        extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new InterceptorDemo()).addPathPatterns("/**").excludePathPatterns("/**rest");
        
//        // 登录信息拦截
//        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**")
//        	.excludePathPatterns("/**rest")
//        	.excludePathPatterns("/login")
//        	.excludePathPatterns("/signIn")
//        	.excludePathPatterns("/register");
        super.addInterceptors(registry);
    }
    
    

}
