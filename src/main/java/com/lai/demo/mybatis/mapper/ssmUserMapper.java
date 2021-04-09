package com.lai.demo.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.lai.demo.mybatis.entity.ssmUser;

public interface ssmUserMapper {
	/**
	 * 查询所有用户Dao
	 * @Select是查询类的注解，所有的查询均使用这个
	 * @Result修饰返回的结果集，关联[实体类属性]和[数据库字段]一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
	 * @return
	 */
	@Select("select * from SSM_USER")
	@Results({
		@Result(property = "createTime",  column = "CREATE_TIME"),
		@Result(property = "lastLoginTime", column = "LAST_LOGIN_TIME")
	})
	public List<ssmUser> queryAll();
	
	/**
	 * 根据用户名查询单条记录
	 * @Select("select * from SSM_USER where USERNAME=#{username}"):类似于select*from SSM_USER where name=？(#{}防sql注入)
	 * @Select("select * from SSM_USER where USERNAME=${username}"):类似于select*from SSM_USER where name='?'(${}用于拼接)
	 * @param username
	 * @return
	 */
	@Select("select * from SSM_USER where USERNAME=#{username}")
	@Results({
		@Result(property = "createTime",  column = "CREATE_TIME"),
		@Result(property = "lastLoginTime", column = "LAST_LOGIN_TIME")
	})
	public ssmUser getUserByUsername(String username);
	
	/**
	 * 增加用户记录
	 * @Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
	 * @param ssmUser
	 */
	@Insert("insert into SSM_USER(USERID,USERNAME,PASSWORD,MOBILE,CREATE_TIME,STATUS) values (#{userid},#{username},#{password},#{mobile},#{createTime},#{status})")
	public void addUser(ssmUser ssmUser);
	
	/**
	 * 根据用户名修改密码
	 * @Update 负责修改，也可以直接传入对象
	 * @param ssmUser
	 */
	@Update("update SSM_USER set PASSWORD=#{password} where USERNAME=#{username}")
	public void updateUser(ssmUser ssmUser);
	
	/**
	 * 根据用户名删除密码
	 * @delete 负责删除
	 * @param username
	 */
	@Delete("delete from SSM_USER where USERNAME=#{username}")
	public void delUser(String username);
}
