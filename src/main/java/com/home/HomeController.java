package com.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	/**
	 * 首页根跳转
	 */
	@RequestMapping("/")
	public String index0(){
		return "home";
	}


}
