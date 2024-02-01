package com.notify.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class NotifyRedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactoryDb7() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();

//      lettuceConnectionFactory.setHostName("localhost");
//      lettuceConnectionFactory.setPort(6379);
        lettuceConnectionFactory.setDatabase(7); // 使用數據庫7

        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplateDb7(RedisConnectionFactory redisConnectionFactoryDb7) {
        return new StringRedisTemplate(redisConnectionFactoryDb7);
    }
}
