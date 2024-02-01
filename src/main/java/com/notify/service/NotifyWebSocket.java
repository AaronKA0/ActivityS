package com.notify.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.notify.model.NotifyVO;

@ServerEndpoint(value = "/notify/{userId}")
@Component
public class NotifyWebSocket {
	private static Map<Integer, Session> sessionsMap = new ConcurrentHashMap<>();
	private static Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userId") Integer userId, Session userSession) throws IOException {
		/* save the new user in the map */
		System.out.println("userId: " + userId);
		sessionsMap.put(userId, userSession);
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
	    System.out.println("closing websocket");
		Set<Integer> userIds = sessionsMap.keySet();
		for (Integer userId : userIds) {
			if (sessionsMap.get(userId).equals(userSession)) {
				sessionsMap.remove(userId);
				break;
			}
		}
	}
	
	
	public void sendNotification(NotifyVO notifyVO) {
		System.out.println("sending notification");
		Session session = sessionsMap.get(notifyVO.getMemVO().getMemId());
		if(session != null) {
			String notifyString = gson.toJson(notifyVO);
			session.getAsyncRemote().sendText(notifyString);
			System.out.println("live notification sent");
		}
	}
}
