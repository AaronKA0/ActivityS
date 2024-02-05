package com.post;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.chat.JedisPoolUtil;
import com.google.gson.Gson;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PostService {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	private static Gson gson = new Gson();

	public Post addPost(Post post) {
		
		String key = new StringBuilder("post:" + post.getMemId()).toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		
		Integer id = (int) (jedis.llen(key) + 1);		
		post.setPostId(id);
		post.setPostTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(LocalDateTime.now())));
		String postString = gson.toJson(post);		
		jedis.rpush(key, postString);
		
		jedis.close();

		return post;
	}
	
	public static Post editPost(Post post) {
		
		System.out.println("edit post: " + post);
		
		String key = new StringBuilder("post:" + post.getMemId()).toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		
		List<String> postStrings = jedis.lrange(key, 0, -1);

		for (int i = 0; i < postStrings.size(); i++) {
			Post p = gson.fromJson(postStrings.get(i), Post.class);
			if (p.getPostId() == post.getPostId()) {
				p.setPostTitle(post.getPostTitle());
				p.setPostContent(post.getPostContent());
				p.setPostStatus(post.getPostStatus());
				String updatedPost = gson.toJson(p);
				jedis.lset(key, i, updatedPost);
				break;
			}
		}
		
		jedis.close();
		
		return post;
	}
	
	public static List<Post> getPosts(Integer memId) {
		
		String key = new StringBuilder("post:" + memId).toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		
		List<String> postStrings = jedis.lrange(key, 0, -1);	
		List<Post> posts = postStrings.stream()
				.map(e -> gson.fromJson(e, Post.class))
				.filter(post -> post.getPostStatus() != 3)
				.toList();   
	
		jedis.close();
		
		return posts;
	}
	
	
	public static Post getPost(Integer memId, Integer postId) {
		
		String key = new StringBuilder("post:" + memId).toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		
		List<String> postStrings = jedis.lrange(key, 0, -1);	
		List<Post> posts = postStrings.stream()
				.map(e -> gson.fromJson(e, Post.class))
				.filter(p -> p.getPostId() == postId)
				.toList();   
	
		jedis.close();
		
		return posts.get(0);
	}
	
	

}

