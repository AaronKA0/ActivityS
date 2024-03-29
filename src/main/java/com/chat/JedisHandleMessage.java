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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.memRelation.MemRelation;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisHandleMessage {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	private static Gson gson = new Gson();

	public List<String> getHistoryMsg(String sender, String receiver, Boolean update) {

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
			} else if (msg.getStatus() != 3) {
				updatedData.add(data);
			}
		}

		jedis.close();
		return updatedData;
	}

	public String saveChatMessage(String sender, String receiver, ChatMessage message) {
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

	public Set<ReadStatus> getChatReceivers(ChatMessage message) {
		
		Jedis jedis = pool.getResource();
		jedis.select(14);

		Set<String> keys = jedis.keys("msg:" + message.getSender() + ":" + "*");
		Set<String> keys2 = jedis.keys("msg:" + message.getReceiver() + ":" + "*");

		TreeSet<ReadStatus> receivers = new TreeSet<ReadStatus>();
		if (message.getReceiver() != null && !message.getReceiver().isEmpty()) {
			boolean exists = jedis.exists("msg:" + message.getSender() + ":" + message.getReceiver())
					|| jedis.exists("msg:" + message.getReceiver() + ":" + message.getSender());
			if (!exists) {
				
				String relationString = jedis.get("relation:" + message.getReceiver() + ":" + message.getSender());
				MemRelation relation = gson.fromJson(relationString, MemRelation.class);
				if (relation == null || relation.getStatus() != 3) {
					ReadStatus rs = new ReadStatus();
					rs.setUserName(message.getReceiver());
					rs.setIsRead(true);
					rs.setNumUnread(0);
					rs.setIsSelected(true);
					receivers.add(rs);
				}
			}
		}

		for (String key : keys) {
			String receiver = key.split(":")[2];
			List<String> messages = jedis.lrange(key, 0, -1);
			Boolean allRead = true;
			Integer numUnread = 0;
			String time = null;
			for (int i = 0; i < messages.size(); i++) {
				ChatMessage cm = (ChatMessage) gson.fromJson(messages.get(i), ChatMessage.class);
				if (cm.getStatus() == 1 && !cm.getSender().equals(message.getSender())) {
					numUnread++;
					allRead = false;
				}
				if (i == messages.size() - 1) {
					time = cm.getTime();
				}
			}

			ReadStatus rs = new ReadStatus();
			rs.setUserName(receiver);
			rs.setIsRead(allRead);
			rs.setNumUnread(numUnread);
			rs.setTime(time);
			rs.setIsSelected(false);
			if (rs.getUserName().equals(message.getReceiver())) {
				rs.setIsRead(true);
				rs.setNumUnread(0);
				rs.setIsSelected(true);
			}

			// cannot chat with any user who blocked you
			String relationString = jedis.get("relation:" + receiver + ":" + message.getSender());
			MemRelation relation = gson.fromJson(relationString, MemRelation.class);
			if (relation == null || relation.getStatus() != 3) {
				receivers.add(rs);
			}
		}

		jedis.close();

		Supplier<TreeSet<ReadStatus>> treeSet = () -> new TreeSet<ReadStatus>();
		TreeSet<ReadStatus> result = receivers.stream().sorted().collect(Collectors.toCollection(treeSet));

		return result;
	}

	public void deleteMsg(ChatMessage message) {

		String key = new StringBuilder("msg:" + message.getSender()).append(":").append(message.getReceiver())
				.toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);

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

	public void retrieveMsg(ChatMessage message) {

		String key = new StringBuilder("msg:" + message.getSender()).append(":").append(message.getReceiver())
				.toString();
		String key2 = new StringBuilder("msg:" + message.getReceiver()).append(":").append(message.getSender())
				.toString();
		Jedis jedis = pool.getResource();
		jedis.select(14);

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
