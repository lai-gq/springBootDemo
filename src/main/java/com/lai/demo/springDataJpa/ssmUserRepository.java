package com.lai.demo.springDataJpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lai.demo.mybatis.entity.ssmUser;

/**
 * 此接口继承JpaRepository,他有两个参数,
 * 参数一是持久化实体类名称;参数二是主键id的类型
 * JpaRepository： 继承PagingAndSortingRepository，实现一组JPA规范相关的方法;
 * PagingAndSortingRepository继承了CrudRepository
 * CrudRepository： 继承Repository，实现了一组CRUD相关的方法;
 */
public interface ssmUserRepository extends JpaRepository<ssmUser, Long>{
	/*
    * JpaRepository接口的方法
	* 一  查询的方法:
	* 	1,查找所有:findAll()
	* 	2,查找单个:findOne()
	* 	3,分页查询:findAll(Pageable pageable)
	* 	4,排序:findAll(Sort sort)
	* 	5,根据实体类属性查询： findByXXX (type XXX);
	* 	6,条件查询:and/or/findByAgeLessThan/LessThanEqual 等， 
	* 	      例如:findByUsernameAndPassword(String username , String password)
	* 	7,总数查询 count() 或者 根据某个属性的值查询总数countById(int id);
	* 二  添加,删除,修改的方法:
	* 	添加:1,save(T)      保存单个或者批量保存
	* 		2,saveAndFlush 保存并刷新到数据库
	* 	删除:1,delete(T)    删除或者批量删除;
	* 	修改:使用jpa进行update操作主要有两种方式:
	* 		1、调用保存实体的方法
	* 			1）保存一个实体：repository.save(T entity)
	* 			2）保存多个实体：repository.save(Iterable<T> entities)
	* 			3）保存并立即刷新一个实体：repository.saveAndFlush(T entity)
	* 		2、@Query注解，自己写JPQL语句
	*/
	
	/**
	 * 根据[用户名]查询用户信息，注意方法名By后面要与实体类属性一致
	 * @param userName
	 * @return
	 */
	public ssmUser findByusername(String userName);
}
