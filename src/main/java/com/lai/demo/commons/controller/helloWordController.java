package com.lai.demo.commons.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.lai.demo.commons.dto.dataSourceDTO;
import com.lai.demo.commons.dto.resResult;
import com.lai.demo.commons.dto.user;
import com.lai.demo.commons.util.AesUtil;
import com.lai.demo.mybatis.entity.ssmUser;
import com.lai.demo.redis.RedisUtil;
import com.lai.demo.tasks.AsyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

//@RestController
@Controller
@SuppressWarnings("rawtypes")
public class helloWordController {
	
	private final static Logger logger = LoggerFactory.getLogger(helloWordController.class);
	
	@Resource
	private dataSourceDTO ds;
	
	@Resource
	private RedisUtil redisUtil;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
    private AsyncTask task;
	
	/**
	 * 在SpringBootDemoApplication的main方法启动springboot
	 * 访问：http://localhost:8081/springboot/helloWorld测试
	 * @return
	 */
	@RequestMapping(value="/helloWorld")
	public String helloWorld(){
		System.out.println("返回hello视图");
        return "hello";
	}
	
	/**
	 * 在SpringBootDemoApplication的main方法启动springboot
	 * 访问：http://localhost:8081/springboot/helloSpringBoot测试
	 * @return
	 */
	@RequestMapping(value="/helloSpringBoot")
	@ResponseBody
	public resResult helloSpringBoot(){
		logger.info("===========日志输出==========");
		user u=new user();
    	u.setName("蓝**");
    	u.setAge(18);
    	u.setBirthday(new Date());
        return resResult.sucess("请求成功", u);
	}
	
	/**
	 * 测试如何获取application.properties文件内定义的属性内容
	 * 访问：http://localhost:8081/springboot/getDateSource
	 * @return
	 */
	@RequestMapping("/getDateSource")
	@ResponseBody
    public dataSourceDTO getDateSource() {
    	dataSourceDTO result=new dataSourceDTO();
    	BeanUtils.copyProperties(ds, result);
        return result;
    }
	
	/**
	 * 测试redis
	 * 访问：http://localhost:8081/springboot/getRedisKey
	 * @return
	 */
	@RequestMapping("/getRedisKey")
	@ResponseBody
    public String getRedisKey() {
		try {
			System.out.println("已获取key[redisTest]:value["+stringRedisTemplate.opsForValue().get("mystr")+"]");
			System.out.println("已存放key[redisTest]:"+redisUtil.set("redisTest", "redisTemplate模板"));
			System.out.println("已获取key[redisTest]:value["+redisUtil.get("redisTest")+"]");
		} catch (Exception e) {
			System.out.println("异常："+e.getMessage());
		}
		return "已获取key[redisTest]:value["+redisUtil.get("redisTest")+"]";
    }
	
	/**
	 * 测试异步任务
	 * 访问：http://localhost:8081/springboot/asynTest
	 * @return
	 */
	@RequestMapping("/asynTest")
    public String asynTest() {
		long begin = System.currentTimeMillis();
        Future<String> task1;
		try {
			task1 = task.task1();
			Future<String> task2 = task.task2();
	        Future<String> task3 = task.task3();
	        //如果都执行往就可以跳出循环,isDone方法如果此任务完成，true
	        for(;;){
	            if (task1.isDone() && task2.isDone() && task3.isDone()) {
	                break;
	            }
	        }
	        long end = System.currentTimeMillis();    
	        long total = end-begin;
	        System.out.println("执行总耗时="+total);
	        return "执行总耗时="+total+"秒";
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
    }
	
	/**
	 * 测试thymeleaf模板（返回thymeleaf/thymeleafTest.html页面）
	 * 访问：http://localhost:8081/springboot/thymeleafTest
	 * @return
	 */
	@RequestMapping("/thymeleafTest")
    public ModelAndView thymeleafTest(ssmUser inUser) {
		//测试入参
		System.out.println("用户名:"+inUser.getUsername());
		System.out.println("密码:"+inUser.getPassword());
		
		ModelAndView mav=new ModelAndView("thymeleafTest");//返回页面
		ssmUser user=new ssmUser();
		user.setUserid(Long.parseLong("999"));
		user.setUsername("赖国清");//用户名
		user.setSex("1");//性别（1男2女）
		user.setCreateTime(new Date());//注册时间
		mav.addObject("user", user);//返回值
		mav.addObject("htmlShow", "<h3 style=\"color:red;\">使用text只能看到文本内容,使用utext能看到html代码</h3>");//返回值
       
		ssmUser user2=new ssmUser();
		user2.setUsername("测试2");//用户名
		user2.setSex("1");//性别（1男2女）
		user2.setCreateTime(new Date());//注册时间
		
		ssmUser user3=new ssmUser();
		user3.setUsername("测试3");//用户名
		user3.setSex("2");//性别（1男2女）
		user3.setCreateTime(new Date());//注册时间
		
		List<ssmUser> list=new ArrayList<>();
		list.add(user);
		list.add(user2);
		list.add(user3);
		mav.addObject("userList", list);//返回值
		
		return mav;
    }
	
	
	/**
	 * 测试获取前端用aes加密,后端解密
	 * 访问：http://localhost:8081/springboot/aesTest
	 * @return
	 */
	@RequestMapping("/aesTest")
	@ResponseBody
    public resResult aesTest(ssmUser ssmUser) {
    	System.out.println("用户名(未解密):"+ssmUser.getUsername());//未解密时
    	System.out.println("密码(未解密):"+ssmUser.getPassword());
    	
    	System.out.println("用户名(解密):"+AesUtil.aesDecrypt(ssmUser.getUsername()));//解密
    	System.out.println("密码(解密):"+AesUtil.aesDecrypt(ssmUser.getPassword()));
        return resResult.sucess("返回身份证数据", AesUtil.aesEncrypt("350615451245101012"));//返回加密数据
    }

	@RequestMapping("/dounw")
	public void cooperation(HttpServletRequest request, HttpServletResponse response) {
		try {
			ServletOutputStream out = response.getOutputStream();
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			String fileName = new String(("UserInfo " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
					.getBytes(), "UTF-8");
			Sheet sheet1 = new Sheet(1, 0);
			sheet1.setSheetName("第一个sheet");

			List<List<String>> data = new ArrayList<>();
			data.add(Arrays.asList("1111","2221","3313"));
			data.add(Arrays.asList("1112","2222","3332"));

			writer.write0(data, sheet1);
			writer.finish();
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
