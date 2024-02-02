package com.comment.config;

import com.comment.model.CommentVO;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	
	@Bean(name = "lettuceConnectionFactory")
	@Primary
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        lettuceConnectionFactory.setDatabase(1); // 指定存到DB1
        lettuceConnectionFactory.afterPropertiesSet(); // 初始化
        return lettuceConnectionFactory;
    }
	
    @Bean(name = "commentRedisTemplate")
    public RedisTemplate<String, CommentVO> redisTemplate(@Qualifier("lettuceConnectionFactory")LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, CommentVO> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置key的序列化方式
        template.setKeySerializer(new StringRedisSerializer());

        // 设置value的序列化方式
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置hash key和value的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean(name = "redisTemplateForObject")
    public RedisTemplate<String, Object> redisTemplateForObject(@Qualifier("lettuceConnectionFactory")LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置key的序列化方式
        template.setKeySerializer(new StringRedisSerializer());

        // 设置value的序列化方式为 Jackson2JsonRedisSerializer
        // 这里使用 Jackson2JsonRedisSerializer 而不是 GenericJackson2JsonRedisSerializer
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置hash key和value的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(@Qualifier("lettuceConnectionFactory")LettuceConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}

