package com.membership.service;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.model.MembershipVO;

@Service
public class RedisService {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	MembershipService membershipSvc;

	// 一個執行緒池
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	// MEMBERSHIP_NAMESPACE 是一個RedisService類別聲明的常數，用來定義redis中的命名空間
	// 用來區分不同的redis鍵(key) 避免衝突
	// MemberNewpassword-memAcc: 為會員驗證碼-帳號:
	private static final String MEMBERSHIP_NAMESPACE = "MemberNewpassword-memAcc:";

	// MemberBlockStartTime-memAcc: 為權限封鎖開始時間-帳號:
	private static final String MEMBERSHIP_Block_Start_Time = "MemberBlockStartTime-memAcc:";

	public void saveToRedis(String key, String value, long expirationInSeconds) {
		// 在 key 前添加 "membership:"
		String prefixedKey = MEMBERSHIP_NAMESPACE + key;

		// 修正這裡的 key 為 prefixedKey
		stringRedisTemplate.opsForValue().set(prefixedKey, value);

		// 設定過期時間，expirationInSeconds 為過期的秒數
		stringRedisTemplate.expire(prefixedKey, expirationInSeconds, TimeUnit.SECONDS);
	}

	public String getFromRedis(String key) {
		// 在 key 前添加 "membership:"
		String prefixedKey = MEMBERSHIP_NAMESPACE + key;

		// 這裡的 key 為 prefixedKey
		return stringRedisTemplate.opsForValue().get(prefixedKey);
	}

	// redis 刪除指定鍵
	public void removeFromRedis(String key) {
		// 在 key 前添加 "membership:"
		String prefixedKey = MEMBERSHIP_NAMESPACE + key;

		// 使用 delete 方法刪除 key
		stringRedisTemplate.delete(prefixedKey);

	}

//=====================================================================================

//	-------------------------isAccEna帳戶狀態封鎖(開始--->結束)-----------------------------

	public void saveToRedis2(String key, String value, LocalDateTime unlockTime) {
		// 在 key 前添加 "membership:"
		String prefixedKey = MEMBERSHIP_Block_Start_Time + key;

		System.out.println("schedule");

		// 設定狀態值
		stringRedisTemplate.opsForValue().set(prefixedKey, value);

		// 計算 unlockTime 與當前時間的差距，轉換為秒數
		long secondsUntilUnlock = Duration.between(LocalDateTime.now(), unlockTime).getSeconds();
		stringRedisTemplate.expire(prefixedKey, secondsUntilUnlock, TimeUnit.SECONDS);

		// 安排任務在解鎖時間後將數值更新為 2
		scheduleUnlockTask(prefixedKey, unlockTime);
	}

	// Helper 方法，設定一個定時任務，在 unlockTime 後將值設定為2
	private void scheduleUnlockTask(String key, LocalDateTime unlockTime) {
		ScheduledFuture<?> future = scheduler.schedule(() -> {

			// redis更新值為2
			stringRedisTemplate.opsForValue().set(key, "2");

			// 同時更新MySql的資料庫狀態
			MembershipVO membership = membershipSvc.findByMemAcc(key.substring(28));
			membership.setIsAccEna((byte) 2);
			membershipSvc.updateMembership(membership);

			System.out.println("memAcc : " + key.substring(28));

		}, Duration.between(LocalDateTime.now(), unlockTime).toSeconds(), TimeUnit.SECONDS);
	}

	// 關閉執行緒池
	public void closeScheduler() {
		scheduler.shutdown();
	}

	// 來獲取redis中所有的資料
	public String getFromRedis2(String key) {
		String prefixedKey = MEMBERSHIP_Block_Start_Time + key;

		return stringRedisTemplate.opsForValue().get(prefixedKey);
	}

	// 新增更新 Redis 資料的方法 (這個方法用於刪除 Redis 中的相應資料)
	public void removeFromRedis2(String memAcc) {
		String prefixedKey = MEMBERSHIP_Block_Start_Time + memAcc;

		// 取得當前狀態
		String currentStatus = stringRedisTemplate.opsForValue().get(prefixedKey);

		// 如果當前狀態是 "1"(停用)，修改為 "2"(啟用)，否則不做任何處理
		if (currentStatus != null && currentStatus.equals("1")) {
			// 修改為 "2"(啟用)
			stringRedisTemplate.opsForValue().set(prefixedKey, "2");
			System.out.println("Status updated to 2 for: " + memAcc);
		} else {
			System.out.println("No update needed for: " + memAcc);
		}
	}
}
