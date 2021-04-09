package com.lai.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
/*
 * @SpringBootApplication 程序启动的注解
 * @SpringBootApplication = @SpringBootConfiguration( 配置类@Configuration) + @EnableAutoConfiguration(自动配置) + @ComponentScan(扫描路径)
 *		@SpringBootConfiguration：
 *				继承了Configuration,表示当前是注解配置类(相当于ssm项目中的applicationContext.xml,只不过这边是用注解代替xml配置文件)。
 *				项目中可以多个类使用@Configuration注解,因为启动项目时@Import注解可以在全项目中把所有@Configuration的类整理成一份配置类(spring.factories)
 * 		@EnableAutoConfiguration：
 * 				开启springboot的注解功能,将所有符合自动配置条件的bean定义加载到IoC容器(相当于ssm项目中的applicationContext.xml中的<aop:aspectj-autoproxy/>)。
 * 				【实现】:会自动去maven中读取每个starter(起步装置)中的spring.factories(工厂)文件  该文件里配置了所有需要被创建spring容器中的bean
 * 		@ComponentScan：
 * 				默认的扫描包启动类(本类)下及其子包(com.lao.demo)下标有@Component的类并注册成bean。(相当于ssm项目中的applicationContext.xml中的<context:component-scan base-package="com.study.code">)
 * 				表示将该类自动发现扫描组件。如果扫描到有@Component、@Controller、@Service等这些注解的类，并注册为Bean，可以自动收集所有的Spring组件
 */

//程序启动的注解
@SpringBootApplication
//开启定时任务(会自动去扫描)
@EnableScheduling
//开启异步任务
@EnableAsync   
//开启缓存
@EnableCaching 
//mybatis扫描dao接口的注解(或者直接在每个Mapper类上面添加注解@Mapper,但也挺麻烦的)
@MapperScan("com.lai.demo.mybatis.mapper") 
//spring扫描包路径
//@ComponentScan(basePackages={"com.lai.demo"})
//dubbo配置文件引入
@ImportResource(locations = {"classpath:dubbo/consumers.xml"}) // 使用 providers.xml 配置； "classpath:dubbo/providers.xml",
@EnableJms //启动消息队列
public class SpringBootDemoApplication {
	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors","false");
		//run方法表示运行SpringBoot的引导类,参数是SpringBoot引导类的字节码文件
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
