package com.lai.demo.dubbo.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lai.demo.dubbo.provider.provideBS;

//@Service(version = "1.0.0")
public class provideBSImpl implements provideBS{

	/**
	 * 测试dubbo的服务提供
	 * 即该方法为dubbo的被调用的方法
	 */
	@Override
	public String testDubboProvide(String name) {
		return name + "你好! Springboot-Dubbo test success!";
	}

}
