/** 
* Project Name:webjsp 
* Version: V1.0
* History:（下面添加每次文件变更人、时间和内容）
* 2017年8月22日下午1:35:13 kingcrab Create
* 
*/
package com.controller;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 类的详细说明:测试类
 * 
 * @author kingcrab
 * @创建日期：2017年8月22日 下午1:35:13 @历史信息：
 * 
 */
@Controller
public class HelloController {
	
	@RequestMapping("/")
	public String index0(){
		return "index";
	}
	@RequestMapping("/index")
	public String index1(){
		return "index";
	}
	/*
	 * RestController 用于http协议的restful请求，直接传输json数据。
	 * Controller用于web的http协议，application中配置前后缀对这个注解有效，对restful无效。
	 */

	@Value("${application.hello.HelloAngel}")
	private String hello;

	@RequestMapping("/hello")
	public String hello(Map<String, Object> map) {
		System.out.println(Thread.currentThread() + " ::" + hello);
		map.put("hello", hello);

		return "hello";
	}

	@RequestMapping("/ok")
	public String hello1(Map<String, Object> map) {
		String msg = "This is a test message";
		System.out.println(Thread.currentThread() + " ::" + msg);
		map.put("text", msg);

		return "abc/text";
	}

	@RequestMapping("/ko")
	public String hello2(Map<String, Object> map, @Value("${application.hello.HelloAngel}") String hello) {
		String msg = "This is a test message ";
		System.out.println(Thread.currentThread() + " ::" + msg);

		map.put("text", msg + hello);

		return "abc/text";
	}

	@RequestMapping("/put")
	public String put(HttpSession session, @RequestParam("key") String key, @RequestParam("value") String value) {
		System.out.println("key="+key+",value="+value);
		session.setAttribute("key", key);
		session.setAttribute("value", value);
		return "hello";
	}
	
	@RequestMapping("/put2")
	public String put2(HttpSession session, ServletRequest request, ServletResponse response) {

		String key = request.getParameter("key");
		String value = request.getParameter("value");
		System.out.println("key="+key+",value="+value);
		
		session.setAttribute("key", key);
		session.setAttribute("value", value);
		
		return "hello";
	}
	
	@RequestMapping("/put3")
	public String put3(HttpSession session, ServletRequest request, ServletResponse response) {

		String key = request.getParameter("key3");
		String value = request.getParameter("value3");
		System.out.println("key3="+key+",value3="+value);
		
		session.setAttribute("key3", key);
		session.setAttribute("value3", value);
		
		return "hello";
	}
	
	


}
