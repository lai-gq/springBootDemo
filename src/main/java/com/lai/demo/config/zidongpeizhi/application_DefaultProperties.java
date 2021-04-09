package com.lai.demo.config.zidongpeizhi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 默认配置对象类
 * 原本有application.properties,即使我们什么也没写,他是有默认的内容的。该类相当于配置默认值的类
 * @author Administrator
 */
@ConfigurationProperties
//@Component //如果这里添加了注解那么在自动配置类的时候就不用添加@enableConfigurationProperties(HelloProperties.class)注解.(两种选一种)
public class application_DefaultProperties {
	private String port="8080";// 原本的端口默认8080,server.port=8080

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
