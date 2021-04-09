package com.lai.demo.initRun;

import javax.annotation.Resource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.lai.demo.commons.dto.dataSourceDTO;

/**
 * 项目启动完成之前，能够初始化一些操作
 * 如果实现多次ApplicationRunner定义多个启动类，通过@Order(value=1)注解指定多个启动类的执行顺序
 * @author Administrator
 */
@Component
public class TestApplicationRunner implements ApplicationRunner {
 
	@Resource
	private dataSourceDTO ds;//也能使用springIOC的注入了
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("******【服务启动(ApplicationRunner),系统初始化....】**********");
		System.out.println("连接oracle数据库---用户:"+ds.getUsername());
		String[] sourceArgs = args.getSourceArgs();
		if(null != sourceArgs){
			for (String s : sourceArgs) {
				System.out.println(s);
			}
		}
	}
}