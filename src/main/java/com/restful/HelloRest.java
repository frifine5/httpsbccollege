/** 
* Project Name:webjsp 
* Version: V1.0
* History:（下面添加每次文件变更人、时间和内容）
* 2017年8月22日下午2:16:39 kingcrab Create
* 
*/  
package com.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类的详细说明:restful ask
 * @author kingcrab
 * @创建日期：2017年8月22日 下午2:16:39
 * 
 */
@RestController
public class HelloRest {
	/*
	 * RestController 用于http协议的restful请求，直接传输json数据。
	 * Controller用于web的http协议，application中配置前后缀对这个注解有效，对restful无效。
	 */
	
	@RequestMapping("/hellorest")
	public String hello(){
		return "This is a restful reply";
	}

	
}
