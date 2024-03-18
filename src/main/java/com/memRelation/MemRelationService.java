package com.memRelation;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.JedisPoolUtil;
import com.google.gson.Gson;
import com.post.Post;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("memRelationService")
public class MemRelationService {
	
	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	@Autowired
	Gson gson;
	
	public MemRelation getRelationStatus(MemRelation relation) {
		
		String key = new StringBuilder("relation:" + relation.getMemIdA() + ":" + relation.getMemIdB()).toString();
		
		Jedis jedis = pool.getResource();
		jedis.select(14);

		MemRelation memRelation2 = gson.fromJson(jedis.get(key), MemRelation.class); 
		if(memRelation2 == null) {
			memRelation2 = new MemRelation();
			memRelation2.setStatus((byte)0);
		}

		jedis.close();
		
		return memRelation2;

	}

	public void sendFriendRequest(MemRelation relation) {
		
		String key1 = new StringBuilder("relation:" + relation.getMemIdA() + ":" + relation.getMemIdB()).toString();
		String key2 = new StringBuilder("relation:" + relation.getMemIdB() + ":" + relation.getMemIdA()).toString();
		
		Jedis jedis = pool.getResource();
		jedis.select(14);
			
		relation.setStatus((byte)1);
		String relationString = gson.toJson(relation);

		MemRelation memRelation2 = gson.fromJson(jedis.get(key2), MemRelation.class); 
		if(memRelation2 == null || memRelation2.getStatus() != 3) {
			jedis.set(key1, relationString);
		}

		jedis.close();

	}
	
	// unsend friend request, unfriend, unblock
	public void removeRelation(MemRelation relation) {
		
		String key = new StringBuilder("relation:" + relation.getMemIdA() + ":" + relation.getMemIdB()).toString();
		String key2 = new StringBuilder("relation:" + relation.getMemIdB() + ":" + relation.getMemIdA()).toString();
		
		Jedis jedis = pool.getResource();
		jedis.select(14);

		jedis.del(key);
		
		MemRelation memRelation2 = gson.fromJson(jedis.get(key2), MemRelation.class); 
		if(memRelation2 != null && memRelation2.getStatus() == 2) {
			jedis.del(key2);
		}

		jedis.close();

	}
	
	public void acceptRequest(MemRelation relation) {
		
		String key1 = new StringBuilder("relation:" + relation.getMemIdA() + ":" + relation.getMemIdB()).toString();
		String key2 = new StringBuilder("relation:" + relation.getMemIdB() + ":" + relation.getMemIdA()).toString();
		
		Jedis jedis = pool.getResource();
		jedis.select(14);

		relation.setStatus((byte)2);
		String relationString = gson.toJson(relation);
		
		MemRelation memRelation2 = gson.fromJson(jedis.get(key2), MemRelation.class); 
		if(memRelation2 != null && memRelation2.getStatus() == 1) {
			jedis.set(key1, relationString);	
			
			memRelation2.setStatus((byte)2);
			String relationString2 = gson.toJson(memRelation2);
			jedis.set(key2, relationString2);
		}
		
		jedis.close();

	}
	
	public void blockMember(MemRelation relation) {
		
		String key = new StringBuilder("relation:" + relation.getMemIdA() + ":" + relation.getMemIdB()).toString();
		String key2 = new StringBuilder("relation:" + relation.getMemIdB() + ":" + relation.getMemIdA()).toString();
		
		Jedis jedis = pool.getResource();
		jedis.select(14);
		
		relation.setStatus((byte)3);
		String relationString = gson.toJson(relation);
		jedis.set(key, relationString);
		
		MemRelation memRelation2 = gson.fromJson(jedis.get(key2), MemRelation.class); 
		if(memRelation2 != null && (memRelation2.getStatus() == 1 || memRelation2.getStatus() == 2)) {
			jedis.del(key2);
		}
		jedis.close();
	}
	
	public List<MemRelation> getFriends(Integer memId) {
		
		Jedis jedis = pool.getResource();
		jedis.select(14);
		Set<String> keys = jedis.keys("relation:" + memId + "*");
		
		List<MemRelation> friends = keys.stream()
				.map(key -> gson.fromJson(jedis.get(key), MemRelation.class))
				.filter(relation -> relation.getStatus() == 2)
				.toList();   
	
		jedis.close();
		
		return friends;
	}
		
	public List<MemRelation> getBlocks(Integer memId) {
		
		Jedis jedis = pool.getResource();
		jedis.select(14);
		Set<String> keys = jedis.keys("relation:" + memId + "*");
		
		List<MemRelation> blocks = keys.stream()
				.map(key -> gson.fromJson(jedis.get(key), MemRelation.class))
				.filter(relation -> relation.getStatus() == 3)
				.toList();   
	
		jedis.close();
		
		return blocks;
	}
	
	public List<MemRelation> getFriendRequests(Integer memId) {
		
		Jedis jedis = pool.getResource();
		jedis.select(14);
		Set<String> keys = jedis.keys("relation:" + "*" + ":" + memId );
		
		List<MemRelation> friendRequests = keys.stream()
				.map(key -> gson.fromJson(jedis.get(key), MemRelation.class))
				.filter(relation -> relation.getStatus() == 1)
				.toList();   
	
		jedis.close();
		
		return friendRequests;
	}

}
