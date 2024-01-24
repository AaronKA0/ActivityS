package com.membership.service;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	//MEMBERSHIP_NAMESPACE 是一個RedisService類別聲明的常數，用來定義redis中的命名空間
	//用來區分不同的redis鍵(key) 避免衝突
	private static final String MEMBERSHIP_NAMESPACE = "memAcc:";


	public void saveToRedis(String key, String value, long expirationInSeconds) {
		// 在 key 前添加 "membership:" 前綴
		String prefixedKey = MEMBERSHIP_NAMESPACE + key;

		// 修正這裡的 key 為 prefixedKey
		stringRedisTemplate.opsForValue().set(prefixedKey, value);

		// 設定過期時間，expirationInSeconds 為過期的秒數
		stringRedisTemplate.expire(prefixedKey, expirationInSeconds, TimeUnit.SECONDS);
	}

	public String getFromRedis(String key) {
		// 在 key 前添加 "membership:" 前綴
		String prefixedKey = MEMBERSHIP_NAMESPACE + key;

		// 修正這裡的 key 為 prefixedKey
		return stringRedisTemplate.opsForValue().get(prefixedKey);
	}

	// redis 刪除指定鍵
	public void removeFromRedis(String key) {
		// 在 key 前添加 "membership:" 前綴
		String prefixedKey = MEMBERSHIP_NAMESPACE + key;

		// 使用 delete 方法刪除 key
		stringRedisTemplate.delete(prefixedKey);

	}

}
