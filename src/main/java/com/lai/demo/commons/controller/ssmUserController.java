package com.lai.demo.commons.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lai.demo.commons.service.ssmUserBS;
import com.lai.demo.mybatis.PageBean;
import com.lai.demo.mybatis.entity.ssmUser;

@RestController
public class ssmUserController {
	private final static Logger logger = LoggerFactory.getLogger(ssmUserController.class);

	@Resource
	private ssmUserBS ssmUserBS;
	
	/**
	 * 测试springDataJpa的使用(增删改查)
	 * @return
	 */
	@RequestMapping(value="/springDataJpaTest")
	public List<ssmUser> springDataJpaTest(){
		List<ssmUser> list=new ArrayList<>();
		try {
			list=ssmUserBS.springDataJpaTest();
		} catch (Exception e) {
			System.out.println("springDataJpaTest发生异常："+e.getMessage());
		}
		return list;
	}
	
	/**
	 * 测试Mybatis的使用(增删改查)
	 * http://localhost:8081/springboot/springBootMybatisTest?pageNum=2&pageSize=2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/springBootMybatisTest")
	public PageBean<ssmUser> springBootMybatisTest(int pageNum,int pageSize){
		PageBean<ssmUser> list=new PageBean<ssmUser>();
		try {
			logger.info("==========【日志打印测试】============");
			list=ssmUserBS.springBootMybatisTest(pageNum,pageSize);
		} catch (Exception e) {
			System.out.println("springBootMybatisTest发生异常："+e.getMessage());
		}
		return list;
	}
	
	/**
	 * 测试mybatis的使用(增删改查)redis缓存注解
	 * http://localhost:8081/springBootRedisTest
	 * @return
	 */
	@RequestMapping(value="/springBootRedisTest")
	public ssmUser springBootRedisTest(){
		ssmUser sU=new ssmUser();
		try {
			//查询用户：redis
			sU=ssmUserBS.redisSelectTest("redis");
			//更新用户
			//sU=ssmUserBS.redisUpdateTest("redis");
			//删除用户
			//ssmUserBS.redisDelTest("redis");
		} catch (Exception e) {
			System.out.println("springBootMybatisTest发生异常："+e.getMessage());
		}
		return sU;
	}
}
