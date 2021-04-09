package com.lai.demo.config.zidongpeizhi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
	
	 @Autowired
	 private application_Properties application_Properties;

    /**
     * 自动配置测试,http://localhost:8081/springboot/zdpz
     * 如果我们有自己配置application.properties[server.port=8090](有配置类加上@Component),显示:当前访问端口: 8090
     * 如果没配置 显示:当前访问端口: 8080
     * @return
     */
    @RequestMapping("/zdpz")
    public String zdpz(){
    	System.out.println("========");
        return application_Properties.showPort();
    }
}
