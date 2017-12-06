package com;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/** 
* Project Name:webjsp 
* Version: V1.0
* History:（下面添加每次文件变更人、时间和内容）
* 2017年8月22日下午1:44:07 kingcrab Create
* 
*/

/**
 * 启动类
 * @author kingcrab
 * @创建日期：2017年8月22日 下午1:44:07
 * 
 */
@ComponentScan("com.*")
@EnableScheduling
@SpringBootApplication
@MapperScan({"com.**.dao"})
public class App extends SpringBootServletInitializer {
	
	/* (non-Javadoc)
	 * @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
