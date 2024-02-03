package com.chat;

import java.sql.Timestamp;

public class ChatMessage {
	private String type;
	private String sender;
	private String receiver;
	private String message;
	private Integer id;
	private Byte status;
	private String time;

	public ChatMessage(String type, String sender, String receiver, String message) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ChatMessage [type=" + type + ", sender=" + sender + ", receiver=" + receiver + ", message=" + message
				+ ", id=" + id + ", status=" + status + ", time=" + time + "]";
	}
	
	
}