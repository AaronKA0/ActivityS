package com.chat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.memRelation.MemRelation;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	private static Gson gson = new Gson();

	public static List<String> getHistoryMsg(String sender, String receiver, Boolean update) {

		System.out.println("jedis get history msg: sender: " + sender + "; receiver: " + receiver);
		String key = new StringBuilder("msg:" + sender).append(":").append(receiver).toString();
		String key2 = new StringBuilder("msg:" + receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		List<String> historyData = jedis.lrange(key, 0, -1);

		if (!update) {
			List<String> validData = new ArrayList<String>();
			for (int i = 0; i < historyData.size(); i++) {
				String data = historyData.get(i);
				ChatMessage msg = gson.fromJson(data, ChatMessage.class);
				if (msg.getStatus() != 3) {
					validData.add(data);
				}
			}
			jedis.close();
			return validData;
		}

		List<String> updatedData = new ArrayList<String>();
		for (int i = 0; i < historyData.size(); i++) {
			String data = historyData.get(i);
			ChatMessage msg = gson.fromJson(data, ChatMessage.class);
			if (msg.getStatus() == 1 && msg.getReceiver().equals(sender)) {
				msg.setStatus((byte) 2);
				String updatedMsg = gson.toJson(msg);
				updatedData.add(updatedMsg);
				jedis.lset(key, i, updatedMsg);
				jedis.lset(key2, i, updatedMsg);
				System.out.println("updated messages: " + updatedMsg);
			} else if (msg.getStatus() != 3) {
				updatedData.add(data);
				System.out.println("non-deleted messages: " + data);
			}
		}

		jedis.close();
		return updatedData;
	}

	public static String saveChatMessage(String sender, String receiver, ChatMessage message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		String senderKey = new StringBuilder("msg:" + sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder("msg:" + receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		Integer id = (int) (jedis.llen(senderKey) + 1);
		message.setId(id);
		message.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(LocalDateTime.now())));
		String savedMessage = gson.toJson(message);

		jedis.rpush(senderKey, savedMessage);
		jedis.rpush(receiverKey, savedMessage);

		jedis.close();
		return savedMessage;
	}

	public static Set<ReadStatus> getChatReceivers(ChatMessage message) {

		System.out.println("current receiver: " + message.getReceiver());

		Jedis jedis = pool.getResource();
		jedis.select(14);
		System.out.println("receivers: sender: " + message.getSender() + "; receiver: " + message.getReceiver());
		Set<String> keys = jedis.keys("msg:" + message.getSender() + "*");
		Set<String> keys2 = jedis.keys("msg:" + message.getReceiver() + "*");
		TreeSet<ReadStatus> receivers = new TreeSet<ReadStatus>();
		if (message.getReceiver() != null && !message.getReceiver().isEmpty() && keys2.isEmpty()) {
			ReadStatus rs = new ReadStatus();
			rs.setUserName(message.getReceiver());	
			rs.setIsRead(true);
			rs.setIsSelected(true);
			receivers.add(rs);
		}
		for (String key : keys) {
			String receiver = key.split(":")[2];
			List<String> messages = jedis.lrange(key, 0, -1);
			Boolean allRead = true;
			String time = null;
			for (int i = 0; i < messages.size(); i++) {
				ChatMessage cm = (ChatMessage) gson.fromJson(messages.get(i), ChatMessage.class);
				if (cm.getStatus() == 1 && !cm.getSender().equals(message.getSender())) {
					allRead = false;
				}
				if(i == messages.size() - 1) {
					time = cm.getTime();
					System.out.println("latest message at: " + time);
				}
			}
			System.out.println("number of messages: " + messages.size());
			System.out.println("all read for: " + receiver + "? " + allRead);
			ReadStatus rs = new ReadStatus();
			rs.setUserName(receiver);
			rs.setIsRead(allRead);
			rs.setTime(time);
			rs.setIsSelected(false);
			if(rs.getUserName().equals(message.getReceiver())) {
				rs.setIsRead(true);
				rs.setIsSelected(true);
			}
			System.out.println("receiver: " + rs);
			
			// cannot chat with any user who blocked you
			String relationString = jedis.get("relation:" + receiver + ":" + message.getSender());
			MemRelation relation = gson.fromJson(relationString, MemRelation.class);
			if(relation == null || relation.getStatus() != 3) {
				receivers.add(rs);
			}
		}

		jedis.close();
		
		Supplier<TreeSet<ReadStatus>> treeSet = () -> new TreeSet<ReadStatus>();
		TreeSet<ReadStatus> result = receivers.stream().sorted().collect(Collectors.toCollection(treeSet));
		
		return result;
	}

	public static void deleteMsg(ChatMessage message) {

		String key = new StringBuilder("msg:" + message.getSender()).append(":").append(message.getReceiver())
				.toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		System.out.println("delete msg; id: " + message.getId() + "; sender: " + message.getSender() + "; receiver: "
				+ message.getReceiver());
		List<String> historyData = jedis.lrange(key, 0, -1);

		for (int i = 0; i < historyData.size(); i++) {
			ChatMessage cm = (ChatMessage) gson.fromJson(historyData.get(i), ChatMessage.class);
			if (message.getId() == cm.getId()) {
				cm.setStatus((byte) 3);
				String updatedMsg = gson.toJson(cm);
				jedis.lset(key, i, updatedMsg);
				break;
			}
		}

		jedis.close();

	}

	public static void retrieveMsg(ChatMessage message) {

		String key = new StringBuilder("msg:" + message.getSender()).append(":").append(message.getReceiver())
				.toString();
		String key2 = new StringBuilder("msg:" + message.getReceiver()).append(":").append(message.getSender())
				.toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);
		System.out.println("delete msg; id: " + message.getId() + "; sender: " + message.getSender() + "; receiver: "
				+ message.getReceiver());
		List<String> historyData = jedis.lrange(key, 0, -1);

		for (int i = 0; i < historyData.size(); i++) {
			ChatMessage cm = (ChatMessage) gson.fromJson(historyData.get(i), ChatMessage.class);
			if (message.getId() == cm.getId()) {
				cm.setStatus((byte) 4);
				String updatedMsg = gson.toJson(cm);
				jedis.lset(key, i, updatedMsg);
				jedis.lset(key2, i, updatedMsg);
				break;
			}
		}

		jedis.close();

	}

}
