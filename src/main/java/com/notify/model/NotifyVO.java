package com.notify.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notification_message")
public class NotifyVO implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notify_id", insertable = false, updatable = false)
	private	Integer	notifyId;
	
	@Column(name = "mem_id")
	@NotNull(message="請選擇編號")
	private	Integer	memId;
	
	@Column(name = "notify_title")
	@NotEmpty(message="請填通知訊息標題")
	private	String	notifyTitle;
	
	@Column(name = "notify_content")
	@NotEmpty(message="請填訊息標題")
	private	String	notifyContent;
	
	@Column(name = "notify_status", insertable = false, updatable = false)
	private	Byte	notifyStatus;
	
	@Column(name = "notify_time", insertable = false, updatable = false)
	private	Timestamp notifyTime;	
	
	public NotifyVO() {
		super();
	}

	public NotifyVO(Integer notifyId, Integer memId, String notifyTitle, String notifyContent, Byte notifyStatus,
			Timestamp notifyTime) {
		super();
		this.notifyId = notifyId;
		this.memId = memId;
		this.notifyTitle = notifyTitle;
		this.notifyContent = notifyContent;
		this.notifyStatus = notifyStatus;
		this.notifyTime = notifyTime;
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getNotifyTitle() {
		return notifyTitle;
	}

	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle = notifyTitle;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public Byte getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Byte notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public Timestamp getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	
}
