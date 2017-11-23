package com.login.controller;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用于login logout signIn register的请求处理
 * @author kingcrab
 * @创建日期：2017年11月16日 下午4:09:01
 * @历史信息：new
 * 
 */
@Controller
public class LoginController {
	
	/**
	 * 跳转登录页面
	 */
	@RequestMapping("/login")
	public String alogin(Map<String, Object> map) {
		map.put("text", "登录失效，请重新登录");
		return "auth/login";
	}
	
	/**
	 * 登录请求 只返回数据时加上@ResponseBody
	 */
	@RequestMapping("/signIn")
	@ResponseBody
	public String aloginCheck(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("username="+request.getParameter("username")
				+ "\tpassword="+request.getParameter("password"));

//		map.put("text", "登录成功");
		return "登录成功";
	}
	
	/**
	 * 跳转注册页面
	 */
	@RequestMapping("/register")
	public String aRegister(Map<String, Object> map) {
		map.put("text", "注册页面");
		return "auth/register";
	}
	
	/**
	 * 提交注册请求
	 */
	@RequestMapping("/registerCommit")
	public String aRgsCommit(Map<String, Object> map) {
		map.put("text", "提交注册");
		return "abc/text";
	}
	
	

}
