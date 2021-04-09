package com.lai.demo.commons.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 代码内的注入1(使用@Value注解):
 * 		@Value("${name}")
 * 		private String name;
 * 注入方式2:
 * 通过注解@ConfigurationProperties(prefix="配置文件中的key的前缀")可以将配置文件中的配置自动与实体进行映射,需要提供get和set方法
 */
@Component
public class dataSourceDTO {
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driverClassName}")
	private String driverclassname;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriverclassname() {
		return driverclassname;
	}
	public void setDriverclassname(String driverclassname) {
		this.driverclassname = driverclassname;
	}
	
}
