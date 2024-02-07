package com.venue.service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.chat.JedisPoolUtil;
import com.google.gson.Gson;
import com.venue.model.RecentVen;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RecentVenService {
	
	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	private static Gson gson = new Gson();
	
	public static void addVen(RecentVen ven) {		
		String key = "recentVens:" + ven.getMemId();	
		Jedis jedis = pool.getResource();
		jedis.select(14);
		String savedVen = gson.toJson(ven);
		jedis.rpush(key, savedVen);
		jedis.close();
	}
	
	
	public static List<RecentVen> getVens(Integer memId) {
		
		Jedis jedis = pool.getResource();
		jedis.select(14);
		String key = "recentVens:" + memId;	
		
		List<String> venData = jedis.lrange(key, 0, -1);
		Collections.reverse(venData);
		List<RecentVen> vens = venData.stream()
				.map(ven -> gson.fromJson(ven, RecentVen.class))
				.toList();   
	
		Set<RecentVen> venSet = new LinkedHashSet<RecentVen>(vens);
		List<RecentVen> recentVens = venSet.stream().limit(6).collect(Collectors.toList());
		 
		jedis.close();
		
		return recentVens;
	}

	

}
