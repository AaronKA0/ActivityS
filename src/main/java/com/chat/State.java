package com.chat;

import java.util.Map;
import java.util.Set;

public class State {
	private String type;
	// the user changing the state
	private String user;
	// total users
	private Set<String> users;
	
	private Set<ReadStatus> readStatuses;

//	public State(String type, String user, Set<String> users) {
//		super();
//		this.type = type;
//		this.user = user;
//		this.users = users;
//	}
	
	public State(String type, String user, Set<ReadStatus> readStatus) {
		super();
		this.type = type;
		this.user = user;
		this.readStatuses = readStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}

	public Set<ReadStatus> getReadStatuses() {
		return readStatuses;
	}

	public void setReadStatuses(Set<ReadStatus> readStatuses) {
		this.readStatuses = readStatuses;
	}
	
	



}