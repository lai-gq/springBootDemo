package com.lai.demo.initRun;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动完成之前，能够初始化一些操作
 * @author Administrator
 */
@Component
public class TestCommandLineRunner implements CommandLineRunner {
 
	@Override
    // 实现该接口之后，实现其run方法，可以在run方法中自定义实现任务
	public void run(String... args) throws Exception {
		System.out.println("******【服务启动(CommandLineRunner),系统初始化....】**********");
		if(null != args){
			for (String s : args) {
				System.out.println(s);
			}
		}
	}
}
