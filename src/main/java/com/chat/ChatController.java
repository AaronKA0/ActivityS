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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@ServerEndpoint(value = "/FriendWS/{userName}/{receiver}")
@Component
public class ChatController {
	private static Map<String, String> userMap = new ConcurrentHashMap<>();
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	
	private static JedisHandleMessage messageSvc = new JedisHandleMessage();
	private static Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, @PathParam("receiver") String receiver,
			Session userSession) throws IOException {
		/* save the new user in the map */
		
		sessionsMap.put(userName, userSession);
		userMap.put(userName, receiver);

		receiver = receiver.equals("none") ? "" : receiver;

		ChatMessage msg = new ChatMessage("", userName, receiver, "");
		
		Set<ReadStatus> userNames = messageSvc.getChatReceivers(msg);
		
		State stateMessage = new State("open", userName, userNames);
		
		String stateMessageJson = gson.toJson(stateMessage);
	
		userSession.getAsyncRemote().sendText(stateMessageJson);

		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				userName, userNames);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();

		if ("delete".equals(chatMessage.getType())) {
			messageSvc.deleteMsg(chatMessage);
		}
		
		if ("retrieve".equals(chatMessage.getType())) {
			messageSvc.retrieveMsg(chatMessage);
		}
		
		if ("history".equals(chatMessage.getType()) || "delete".equals(chatMessage.getType()) || "retrieve".equals(chatMessage.getType())) {

			userMap.put(sender, receiver);
			List<String> historyData = messageSvc.getHistoryMsg(sender, receiver, true);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen() && !receiver.equals("none")) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
			}

			List<String> historyData2 = messageSvc.getHistoryMsg(receiver, sender, false);
			String historyMsg2 = gson.toJson(historyData2);
			ChatMessage cmHistory2 = new ChatMessage("history", receiver, sender, historyMsg2);

			Session receiverSession = sessionsMap.get(receiver);
			if (sender.equals(userMap.get(receiver)) && receiverSession != null && receiverSession.isOpen() && !receiver.equals("none")) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(cmHistory2));
			}

			return;

		}

		Session receiverSession = sessionsMap.get(receiver);
		chatMessage.setStatus((byte) 1);
		if (receiverSession != null && receiverSession.isOpen()) {
			
			if (sender.equals(userMap.get(receiver))) {
				chatMessage.setStatus((byte) 2);
			}

			String savedMessage = messageSvc.saveChatMessage(sender, receiver, chatMessage);
			receiverSession.getAsyncRemote().sendText(savedMessage);
			userSession.getAsyncRemote().sendText(savedMessage);

		} else {

			String savedMessage = messageSvc.saveChatMessage(sender, receiver, chatMessage);
			userSession.getAsyncRemote().sendText(savedMessage);
		}
	}

	@OnError
	public void onError(Session userSession, Throwable e) {

	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {

		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				sessionsMap.remove(userName);
				userMap.remove(userName);
				break;
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNames);
	}
	
}
