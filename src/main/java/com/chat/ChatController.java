package com.chat;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@ServerEndpoint(value = "/FriendWS/{userName}/{receiver}")
@Component
public class ChatController {
	private static Map<String, String> userMap = new ConcurrentHashMap<>();
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	private static Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, @PathParam("receiver") String receiver,
			Session userSession) throws IOException {
		/* save the new user in the map */

		sessionsMap.put(userName, userSession);
		userMap.put(userName, receiver);
		/* Sends all the connected users to the new user */
//		Set<String> userNames = sessionsMap.keySet();
//		State stateMessage = new State("open", userName, userNames);
//		String stateMessageJson = gson.toJson(stateMessage);
//		Collection<Session> sessions = sessionsMap.values();
//		for (Session session : sessions) {
//			if (session.isOpen()) {
//				session.getAsyncRemote().sendText(stateMessageJson);
//			}
//		}

		receiver = receiver.equals("none") ? "" : receiver;
		System.out.println("open receiver: " + receiver);
		ChatMessage msg = new ChatMessage("", userName, receiver, "");

		Set<ReadStatus> userNames = JedisHandleMessage.getChatReceivers(msg);
		
		System.out.println("list of receivers: " + userNames);
		
		State stateMessage = new State("open", userName, userNames);
		String stateMessageJson = gson.toJson(stateMessage);
		userSession.getAsyncRemote().sendText(stateMessageJson);
		
		System.out.println("state message json: " + stateMessageJson);

		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();
		System.out.println("on message controller: sender: " + sender + "; receiver: " + receiver);

		
		if ("delete".equals(chatMessage.getType())) {
			JedisHandleMessage.deleteMsg(chatMessage);
		}
		
		if ("retrieve".equals(chatMessage.getType())) {
			JedisHandleMessage.retrieveMsg(chatMessage);
		}
		
		if ("history".equals(chatMessage.getType()) || "delete".equals(chatMessage.getType()) || "retrieve".equals(chatMessage.getType())) {

			System.out.println("getting chat history with: " + receiver + " for: " + sender);
			userMap.put(sender, receiver);
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver, true);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen() && !receiver.equals("none")) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("user history = " + gson.toJson(cmHistory));
			}

			List<String> historyData2 = JedisHandleMessage.getHistoryMsg(receiver, sender, false);
			String historyMsg2 = gson.toJson(historyData2);
			ChatMessage cmHistory2 = new ChatMessage("history", receiver, sender, historyMsg2);

			Session receiverSession = sessionsMap.get(receiver);
			if (sender.equals(userMap.get(receiver)) && receiverSession != null && receiverSession.isOpen() && !receiver.equals("none")) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(cmHistory2));
				System.out.println("receiver history = " + gson.toJson(cmHistory2));
			}

			return;

		}

		Session receiverSession = sessionsMap.get(receiver);
		chatMessage.setStatus((byte) 1);
		if (receiverSession != null && receiverSession.isOpen()) {
			
			System.out.println("user map: receiver: " + receiver + "; sender: " + sender);
			if (sender.equals(userMap.get(receiver))) {
				chatMessage.setStatus((byte) 2);
			}

			String savedMessage = JedisHandleMessage.saveChatMessage(sender, receiver, chatMessage);
			receiverSession.getAsyncRemote().sendText(savedMessage);
			userSession.getAsyncRemote().sendText(savedMessage);
			System.out.println("Message received: " + savedMessage);

		} else {

			String savedMessage = JedisHandleMessage.saveChatMessage(sender, receiver, chatMessage);
			userSession.getAsyncRemote().sendText(savedMessage);
			System.out.println("Message received: " + savedMessage);
		}
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				userNameClose = userName;
				sessionsMap.remove(userName);
				userMap.remove(userName);
				break;
			}
		}

		if (userNameClose != null) {
//			State stateMessage = new State("close", userNameClose, userNames);
//			String stateMessageJson = gson.toJson(stateMessage);
//			Collection<Session> sessions = sessionsMap.values();
//			for (Session session : sessions) {
//				session.getAsyncRemote().sendText(stateMessageJson);
//			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}
	
	
	public void updateNotification() {
		System.out.println("new notification");
	}
}
