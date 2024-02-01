package com.notify.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.membership.model.MembershipVO;
import com.venue.model.VenVO;

@Entity
@Table(name = "notification_message")
public class NotifyVO implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notify_id", insertable = false, updatable = false)
	private	Integer	notifyId;
	
	@ManyToOne
    @JoinColumn(name = "mem_id", referencedColumnName = "mem_id")
    private MembershipVO memVO;
	
	@Column(name = "notify_title")
	@NotEmpty(message="請填通知訊息標題")
	private	String	notifyTitle;
	
	@Column(name = "notify_content")
	@NotEmpty(message="請填訊息標題")
	private	String	notifyContent;
	
	@Column(name = "notify_status", insertable = false)
	private	Byte	notifyStatus;
	
	@Column(name = "notify_time", insertable = false, updatable = false)
	private	Timestamp notifyTime;	
	
	
	public NotifyVO() {
		super();
	}	
	
    public NotifyVO(MembershipVO memVO, @NotEmpty(message = "請填通知訊息標題") String notifyTitle,
            @NotEmpty(message = "請填訊息標題") String notifyContent) {
        super();
        this.memVO = memVO;
        this.notifyTitle = notifyTitle;
        this.notifyContent = notifyContent;
    }

    public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}
	
	
	public MembershipVO getMemVO() {
	    return memVO;
	}
	
	public void setMemVO(MembershipVO memVO) {
	    this.memVO = memVO;
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

    @Override
    public String toString() {
        return "NotifyVO [notifyId=" + notifyId + ", notifyTitle=" + notifyTitle + ", notifyContent=" + notifyContent + ", notifyStatus=" + notifyStatus +"]";
    }

	
}
