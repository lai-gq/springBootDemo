package com.lai.demo.expection;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lai.demo.commons.dto.user;

@Controller
public class expectionController {
	
	/**
	 * 异常测试
	 * 访问：http://localhost:8081/expectionTest 测试
	 * @return
	 */
	@RequestMapping(value="/expectionTest")
	public String expectionTest(ModelMap modelMap){
		//测试从异常处理器的addAttributes取参数,也可以在本方法参数获取如:(@ModelAttribute("author") String author)
		System.out.println("从expectionHandle.addAttributes方法获取到的参数"+modelMap.get("author"));
		//报异常
		int a=8/0;
		return null;
	}
}
