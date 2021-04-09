package com.lai.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.lai.demo.Interceptor.oneInterceptor;
import com.lai.demo.Interceptor.twoInterceptor;
/**
 * 拦截器配置类
 * 改成继承WebMvcConfigurationSupport这个类，在扩展的类中重写父类的方法即可，但是这种方式是有问题的，
 * 这种方式会屏蔽Spring Boot的@EnableAutoConfiguration中的设置。这时候启动项目时会发现映射根本没有成功，
 * 读取不到静态的资源也就是说application.properties中添加配置的映射配置没有启动作。
 * @author Administrator。
 */
@Configuration
public class webMvcCongigurer extends WebMvcConfigurationSupport{
	/**
	 * mvc拦截器
	 * 先配置oneInterceptor，后twoInterceptor,访问/helloSpringBoot:执行顺序
	 * oneInterceptor.preHandle放行==》twoInterceptor.preHandle放行==》twoInterceptor.postHandle
	 * 		==》oneInterceptor.postHandle==》twoInterceptor.afterCompletion==》oneInterceptor.afterCompletion=》结束
	 * 
	 * oneInterceptor.preHandle放行==》twoInterceptor.preHandle不放行==》oneInterceptor.afterCompletion=》结束
	 */ 
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new oneInterceptor()).addPathPatterns("/**");//拦截所有路径(oneInterceptor里面会放行)
        registry.addInterceptor(new twoInterceptor()).addPathPatterns("/helloSpringBoot");//拦截helloSpringBoot路径(twoInterceptor不会放行)
        super.addInterceptors(registry);
    }
	
	
	/**
	 * 静态资源访问 
	 * WebMvcConfigurationAdapter在spring boot 2.0被废弃以后，可以使用系提供的类：WebMvcConfigurationSupport，
	 * 来替换之前的WebMvcConfigurationAdapter 。 但是替换之后之前的静态资源文件 会被拦截，导致无法可用。
	 * 解决办法：重写 addResourceHandlers（）方法，加入静态文件路径即可
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
		        .addResourceLocations("classpath:/META-INF/resources/")
		        .addResourceLocations("classpath:/resources/")
		        .addResourceLocations("classpath:/static/")
		        .addResourceLocations("classpath:/public/");
		super.addResourceHandlers(registry);
	}
	
	/**
	 * 视图映射
	 * application.properties中的spring.mvc.view.prefix=/配置失效
	 * 
	 */
	 @Override
	 protected void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/").setViewName("hello");
	        // registry.addViewController("/login.html").setViewName("login");
	 }

}
