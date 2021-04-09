package com.lai.demo.config.zidongpeizhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * @author Administrator
 */
@Configuration //配置类
@EnableConfigurationProperties(application_DefaultProperties.class)//这里就是前面(application_DefaultProperties)说的，这个注解读入我们的默认配置对象类
@ConditionalOnClass(application_Properties.class)//当application_Properties类存在时(即application.properties你有添加自己的配置)才会起作用扫描
public class autoConfiguration {
	
	@Autowired
    private application_DefaultProperties application_DefaultProperties;

    @Bean
    @ConditionalOnMissingBean(application_Properties.class)//这个配置就是SpringBoot可以优先使用自定义Bean的核心所在，如果没有我们的自定义Bean那么才会自动配置一个新的Bean
    public application_Properties auto(){
    	application_Properties application_Properties =new application_Properties();
    	application_Properties.setPort(application_DefaultProperties.getPort());//有配置application.properties就不会走到这里,没有配置就使用默认值
        return application_Properties;
    }
    
    /**
     * 这还会有其他配置的bean
     * 最主要的注解就是@enableAutoConfiguration,而这个注解会导入一个EnableAutoConfigurationImportSelector的类,
     * 而这个类会去读取一个spring.factories下key为EnableAutoConfiguration全限定名对应值.
     */
}
