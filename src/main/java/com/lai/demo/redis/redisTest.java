package com.lai.demo.redis;

import com.lai.demo.SpringBootDemoApplication;
import com.lai.demo.mybatis.entity.ssmUser;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest(classes = SpringBootDemoApplication.class)//相当于帮你启动springBoot启动类
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class redisTest {
	
	@Resource
    private RedisTemplate redisTemplate;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
    @Test
    public void testSet() {
    	//使用StringRedisTemplate存取字符串数据
    	stringRedisTemplate.opsForValue().set("redisTest", "redisTemplate");
		System.out.println("已存放key[redisTest]");
		System.out.println("已获取key[redisTest]:value["+stringRedisTemplate.opsForValue().get("redisTest")+"]");
		
		//使用RedisTemplate存取对象
		ssmUser ssmUser_new=new ssmUser();
		ssmUser_new.setUserid(Long.parseLong("100"));//用户ID
	    ssmUser_new.setUsername("redis");//用户名
	    ssmUser_new.setPassword("123");//密码
	    ssmUser_new.setMobile("15266666699");//手机号
	    ssmUser_new.setCreateTime(new Date());//注册时间
	    ssmUser_new.setStatus("1");//用户状态(1有效0无效)
	    
	    JSONObject jsonObject_in = JSONObject.fromObject(ssmUser_new);//对象转json字符串
	    redisTemplate.opsForValue().set("ssmUser:redis", jsonObject_in.toString());
	    
	    JSONObject jsonObject_out=JSONObject.fromObject(redisTemplate.opsForValue().get("redisTest"));//json字符串转对象
	    ssmUser ssmUser=(ssmUser)JSONObject.toBean(jsonObject_out, ssmUser.class);
	    System.out.println("已获取key[ssmUser:redis]:value["+ssmUser.getUsername()+"]");
    }
}


