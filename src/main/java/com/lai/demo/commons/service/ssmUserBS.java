package com.lai.demo.commons.service;

import java.util.List;

import com.lai.demo.mybatis.PageBean;
import com.lai.demo.mybatis.entity.ssmUser;

public interface ssmUserBS {
	/**
	 * 测试springDataJpa的使用(增删改查)
	 * @return
	 * @throws Exception
	 */
	public List<ssmUser> springDataJpaTest() throws Exception;
	
	/**
	 * 测试springBoot和Mybatis的使用(增删改查)
	 * @return
	 * @throws Exception
	 */
	public PageBean springBootMybatisTest(Integer pageNum,Integer pageSize) throws Exception;
	
	/**
	 * 
	 * 利用maybatis测试redis的注解 查询缓存
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public ssmUser redisSelectTest(String username) throws Exception;
	/**
	 * 利用maybatis测试redis的注解 更新缓存
	 * @return
	 * @throws Exception
	 */
	public ssmUser redisUpdateTest(String username) throws Exception;
	/**
	 * 利用maybatis测试redis的注解 删除缓存
	 * @param username
	 * @throws Exception
	 */
	public void redisDelTest(String username) throws Exception;
}
