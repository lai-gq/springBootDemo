package com.lai.demo.redis;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * redis配置类
 * 解释：SpringBoot提供了对Redis的自动配置功能，
 *      在RedisAutoConfiguration中默认为我们配置了JedisConnectionFactory（客户端连接）
 * 		、RedisTemplate以及StringRedisTemplate（数据操作模板）
 */
@Configuration
public class RedisConfig {
	/**
	 * 缓存配置
	 * @param factory
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		//生成一个默认配置，通过config对象即可对缓存进行自定义配置
	    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
	    //设置缓存的默认过期时间、不缓存空值(也是使用Duration设置)  import java.time.Duration;  一个Duration对象表示两个Instant间的一段时间，是在Java 8中加入的新功能。
	    config = config.entryTtl(Duration.ofMinutes(1)).disableCachingNullValues();

	    // 设置一个初始化的缓存空间set集合
	    Set<String> cacheNames =  new HashSet<>();
	    cacheNames.add("my-redis-cache1");
	    cacheNames.add("my-redis-cache2");

	    // 对每个缓存空间应用不同的配置
	    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
	    configMap.put("my-redis-cache1", config);
	    configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(60)));

	    RedisCacheManager cacheManager = RedisCacheManager.builder(factory) // 使用自定义的缓存配置初始化一个cacheManager
	            .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
	            .withInitialCacheConfigurations(configMap)
	            .build();
	    return cacheManager;
	}

	/**
	 * Spring Data Redis提供了两个模板：
	 * 		RedisTemplat 和 StringRedisTemplate
	 * 当保存一条数据的时候，key和value都要被序列化成json数据，取出来的时候被序列化成对象，key和value都会使用序列化器进行序列化，
	 * spring data redis提供多个序列化器:
	 * 		1. GenericToStringSerializer：使用Spring转换服务进行序列化；
	 * 		2. JacksonJsonRedisSerializer：使用Jackson 1，将对象序列化为JSON；
	 * 		3. Jackson2JsonRedisSerializer：使用Jackson 2，将对象序列化为JSON；
	 * 		4. JdkSerializationRedisSerializer：使用Java序列化；
	 * 		5. OxmSerializer：使用Spring O/X映射的编排器和解排器（marshaler和unmarshaler）实现序列化，用于XML序列化；
	 * 		6. StringRedisSerializer：序列化String类型的key和value。
	 * 所以:
	 * 		RedisTemplate会默认使用JdkSerializationRedisSerializer，这意味着key和value都会通过Java进行序列化。
	 * 		StringRedisTemplate默认会使用StringRedisSerializer
	 * @param factory
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        // 1. 创建一个模板类
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        // 2. 将刚才的redis连接工厂设置到模板类中
        template.setConnectionFactory(factory);
        // 3. 设置key的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        // 4. 设置value的序列化器
        // 4.1 使用Jackson 2，将对象序列化为JSON
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 4.2 json转对象类，不设置默认的会将json转成hashmap
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }
    /**
     * StringRedisTemplate模板只针对[键/值对都是字符型]的数据进行操作
     * @param factory
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }
}