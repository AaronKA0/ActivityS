package com.chat;

import java.util.Objects;

public class ReadStatus implements Comparable<ReadStatus> {
	
	private String userName;
	private Boolean isRead;
	private String time;
	private Boolean isSelected;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	
	@Override
	public String toString() {
		return "ReadStatus [userName=" + userName + ", isRead=" + isRead + ", time=" + time + ", isSelected="
				+ isSelected + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReadStatus other = (ReadStatus) obj;
		return Objects.equals(userName, other.userName);
	}
	@Override
	public int compareTo(ReadStatus o) {	
		if(this.time == null) return -1;
		if(o.time == null) return 1; 
//		if(this.isSelected) return -1;
//		if(o.isSelected) return 1;
		if(this.time.compareTo(o.getTime()) < 0) return 1;
		if(this.time.compareTo(o.getTime()) > 0) return -1;	
		if(this.userName.compareTo(o.getUserName()) < 0) return 1;
		if(this.userName.compareTo(o.getUserName()) > 0) return -1;
		return 0;
	}
	
	

}
