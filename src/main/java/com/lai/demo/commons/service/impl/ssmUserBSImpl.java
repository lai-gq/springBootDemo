package com.lai.demo.commons.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lai.demo.commons.service.ssmUserBS;
import com.lai.demo.mybatis.PageBean;
import com.lai.demo.mybatis.entity.ssmUser;
import com.lai.demo.mybatis.mapper.ssmUserMapper;
import com.lai.demo.springDataJpa.ssmUserRepository;

@Service
@Transactional //事物管理只需要加上该注解
public class ssmUserBSImpl implements ssmUserBS{
	
	@Resource
	private ssmUserRepository sssmUserDao;
	
	@Resource
	private ssmUserMapper ssmUserMapper;
	
	/* 
	 * 测试springDataJpa的使用(增删改查)
	 * @see com.lai.demo.service.ssmUserBS#springDataJpaTest()
	 */
	public List<ssmUser> springDataJpaTest() throws Exception{
		//增
		/*ssmUser ssmUser_new=new ssmUser();
	    ssmUser_new.setUsername("springDataJpa测试用户");//用户名
	    ssmUser_new.setPassword("123");//密码
	    ssmUser_new.setMobile("15266666666");//手机号
	    ssmUser_new.setSex("1");//性别（1男2女）
	    ssmUser_new.setCreateTime(new Date());//注册时间
	    ssmUser_new.setStatus("1");//用户状态(1有效0无效)
	    System.out.println("增加ssmUser返回结果:"+sssmUserDao.save(ssmUser_new));*/
	    
	    /*ssmUser ssmUser=sssmUserDao.findById(28L).get();//查询单个ssmUser来删改操作*/
		//删
	    /*sssmUserDao.delete(ssmUser);*/
	    //改
	    /*ssmUser.setPassword("456");//密码
	    ssmUser.setMobile("15288888888");//手机号
	    //System.out.println("修改ssmUser返回结果:"+ sssmUserDao.saveAndFlush(ssmUser));*/
		//查
		List<ssmUser> list=sssmUserDao.findAll();
		//总数量
		System.out.println("ssmUser总用户数:"+sssmUserDao.count()+"个");
		
		//测试简单自定义查询
		/*ssmUser ssmUser_by=sssmUserDao.findByusername("springDataJpa测试用户");//根据[用户名]查询用户信息
		System.out.println("用户名:"+ssmUser_by.getUsername()+",密码:"+ssmUser_by.getPassword());*/
		
		return list;
	}
	
	
	/* 
	 * 测试springBoot整合Mybatis的使用(增删改查)
	 */
	public PageBean<ssmUser> springBootMybatisTest(Integer pageNum,Integer pageSize) throws Exception{
		if(null==pageNum){
			pageNum=1;//不传默认第一页
		}
		if(null==pageSize){
			pageSize=30;//默认一页30条
		}
		//增
		ssmUser ssmUser_new=new ssmUser();
		ssmUser_new.setUserid(Long.parseLong("89"));//用户ID
	    ssmUser_new.setUsername("redis");//用户名
	    ssmUser_new.setPassword("123");//密码
	    ssmUser_new.setMobile("15266666699");//手机号
	    ssmUser_new.setCreateTime(new Date());//注册时间
	    ssmUser_new.setStatus("1");//用户状态(1有效0无效)
	    //ssmUserMapper.addUser(ssmUser_new);
	    
	    //查
	    ssmUser ssm_User=ssmUserMapper.getUserByUsername("springBootMybatis测试用户");
	    
	    //改
	    ssm_User.setPassword("8888");
	    ssmUserMapper.updateUser(ssm_User);
	    int a=8/0;
	    //删
	    //ssmUserMapper.delUser("springBootMybatis测试用户");
	    
	    PageHelper.startPage(pageNum,pageSize); //pageNum=2, pageSize=2 ,表示每页的大小为2，查询第二页的结果
        PageHelper.orderBy("username DESC "); //进行分页结果的排序，username为字段名，排序规则DESC/ASC
		List<ssmUser> list=ssmUserMapper.queryAll();//查询用户列表(会根据上行的参数自动返回分页结果)
		System.out.println("Page对象:"+list.toString()); //输出的是Page对象(有page的属性)
        for(int i=0;i<list.size();i++){
            System.out.println("==============用户:"+list.get(i).getUsername()); //循环显示分页结果
        }
        //封装进返回结果
        PageBean<ssmUser> pageData = new PageBean<>(pageNum, pageSize, list.size());//PageBean是为了能够统一返回结果,有当前页/总页数/分页结果等参数
        pageData.setItems(list);//分页结果参数
		return pageData;
	}
	
	/**
	 * 利用maybatis测试redis的注解 [查询缓存]
	 * 使用@Cacheable碰到的问题:第一次查询到数据，存到了redis中，第二次查询从缓存里取数据，报错cannot be cast to(类型转换异常)
	 * 					  解决:是因为热部署的依赖导致的。注释掉就好了。
	 * @param username
	 * @return
	 * @throws Exception
	 * 注解说明:
	 * @Cacheable:第一次将查询方法返回值加进缓存,后面查询先取缓存，缓存没有在查数据库
	 * 属性说明:
	 * 	cacheNames(value):存入缓存的大键,:后面可以有不同的子键(即key),
	 * 					 也可以统一写在类名上面 @CacheConfig(cacheNames ="ssmUserBSImpl"),具体的方法上就不用写啦。
	 * 	key: 可以看作为 value 的子键(Key可以动态设置为方法的参数如#username,返回值如#reslut,动态的类名等#root.method，自定义key)
	 * 	condition:设置条件为true才会缓存，false不会
	 * 	unless:设置条件为true才会缓存，false不会
	 */
	@Cacheable(cacheNames = "ssmUserBSImpl", key="#username",condition="5 > 3",unless = "3 != 3")
	public ssmUser redisSelectTest(String username) throws Exception{
		System.out.println("redisSelectTest:如果第二次没有走到这里说明缓存被添加了");
		ssmUser ssm_User=ssmUserMapper.getUserByUsername(username);
		return ssm_User;
	}
	/**
	 * 利用maybatis测试redis的注解 [更新缓存]
	 * @return
	 * @throws Exception
	 * 注解说明:
	 * @CachePut:更新 Redis 中对应键的值即更新缓存。
	 * 属性说明:
	 *   与@Cacheable一样，不过
	 *   cacheNames 和 key 要跟 @Cacheable() 里的一致，才会正确更新。
	 *	 @CachePut() 和 @Cacheable() 注解的方法返回值要一致
	 *   #result 方法返回值不能用在@Cacheable上，只能用在@CachePut
	 */
	@CachePut(cacheNames = "ssmUserBSImpl", key = "#username")
	public ssmUser redisUpdateTest(String username) throws Exception{
		System.out.println("redisUpdateTest:更新缓存");
		ssmUser ssm_User=ssmUserMapper.getUserByUsername(username);
		ssm_User.setPassword("99999999");//修改密码
		ssmUserMapper.updateUser(ssm_User);
		return ssm_User;
	}
	/**
	 * 利用maybatis测试redis的注解 [删除缓存]
	 * @param username
	 * @throws Exception
	 * 注解说明:
	 * @CacheEvict:删除 Redis 中对应键的值。
	 * 属性说明:
	 * 	  只有cacheNames，key
	 *   执行完这个方法之后会将 Redis 中对应的记录删除。
	 */
	@CacheEvict(cacheNames = "ssmUserBSImpl", key = "#username")
	public void redisDelTest(String username) throws Exception{
		System.out.println("redisDelTest:删除缓存");
		ssmUserMapper.delUser(username);
	}
}
