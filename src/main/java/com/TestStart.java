package com;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.Jedis;

@SpringBootApplication
public class TestStart implements CommandLineRunner {
    
    // Redis 連線測試
//    @Autowired
//    @Qualifier("stringRedisTemplateDb7")
//    StringRedisTemplate stringRedisTemplate;
//       
//    public void Redistry (String key, String value, long expirationInSeconds){
//        
//        stringRedisTemplate.opsForValue().set(key, value);
//        stringRedisTemplate.expire(key, expirationInSeconds, TimeUnit.SECONDS);
//    }

    
    @Override
    public void run(String...args) throws Exception {
		
        
        System.out.println(" ***************** ");
        System.out.println(" *               * ");
        System.out.println(" *   啟 動 成 功   * ");
        System.out.println(" *               * ");
        System.out.println(" ***************** ");
        
        
        
        // Redis 連線測試
//        String key = "TestConnect";
//        String value = "Success";
//        
//        Redistry(key, value, 1800L);      
//        String getValue = stringRedisTemplate.opsForValue().get(key);
//        
//        System.out.println("Redis connection > " + getValue);
        
	}

}
