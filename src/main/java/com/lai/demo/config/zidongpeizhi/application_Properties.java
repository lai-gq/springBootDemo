package com.lai.demo.config.zidongpeizhi;

import org.springframework.stereotype.Component;

@Component //如果打开,相当于你在application.properties配置了server.port=8090,没打开就相当于没写会有默认值
public class application_Properties {
	
	 private String port = "8090";//如果自动配置没有读入成功，那么为默认值

	/**
	 * //为我们服务的方法
	 * @return
	 */
	public String showPort() {
	     return "当前访问端口: " + port;
	 }

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	 
}
