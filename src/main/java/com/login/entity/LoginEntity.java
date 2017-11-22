package com.login.entity;

/**
 * 登录信息实体
 * @author kingcrab
 * @创建日期：2017年11月21日 下午5:32:47
 * @历史信息：new
 * 
 */

public class LoginEntity {
	
	private Long serId;
	private String name;
	private String pwd;
	public Long getSerId() {
		return serId;
	}
	public void setSerId(Long serId) {
		this.serId = serId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	

}
