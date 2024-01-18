package com.membership.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "membership")
public class MembershipVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public MembershipVO() {
	}

//  ----------------------登入-----------------------	 
	public MembershipVO(String memAcc, String memPwd) {
		this.memAcc = memAcc;
		this.memPwd = memPwd;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mem_id", insertable = false, updatable = false)
	private Integer memId;

	@Column(name = "mem_acc", unique = true)
	private String memAcc;

	@Column(name = "mem_email", unique = true)
	private String memEmail;

	@Column(name = "mem_pwd")
	private String memPwd;

	@Column(name = "mem_name")
	private String memName;

	@Column(name = "mem_gender")
	private Byte memGender;

	@Column(name = "mem_birthdate")
	private Date memBirthdate;

	@Column(name = "mem_username")
	private String memUsername;

	@Column(name = "mem_pic", columnDefinition = "longblob")
	private byte[] memPic;

	@Column(name = "mem_intro", nullable = true)
	private String memIntro;

	@Column(name = "mem_phone", nullable = true)
	private String memPhone;

	@Column(name = "block_start_time")
	private Timestamp blockStartTime;

	@Column(name = "block_end_time")
	private Timestamp blockEndTime;

	@Column(name = "is_acc_ena", insertable = false)
	private Byte isAccEna;

	@Column(name = "is_part_ena", insertable = false)
	private Byte isPartEna;

	@Column(name = "is_host_ena", insertable = false)
	private Byte isHostEna;

	@Column(name = "is_rent_ena", insertable = false)
	private Byte isRentEna;

	@Column(name = "is_msg_ena", insertable = false)
	private Byte isMsgEna;

	@Column(name = "mem_cr_time", columnDefinition = "TINYINT DEFAULT 2")
	private Timestamp memCrTime = Timestamp.valueOf(LocalDateTime.now());

	@Column(name = "mem_login_time")
	private Timestamp memLoginTime;

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public String getMemAcc() {
		return memAcc;
	}

	public void setMemAcc(String memAcc) {
		this.memAcc = memAcc;
	}

	public String getMemEmail() {
		return memEmail;
	}

	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}

	public String getMemPwd() {
		return memPwd;
	}

	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public Byte getMemGender() {
		return memGender;
	}

	public void setMemGender(Byte memGender) {
		this.memGender = memGender;
	}

	public Date getMemBirthdate() {
		return memBirthdate;
	}

	public void setMemBirthdate(Date memBirthdate) {
		this.memBirthdate = memBirthdate;
	}

	public String getMemUsername() {
		return memUsername;
	}

	public void setMemUsername(String memUsername) {
		this.memUsername = memUsername;
	}

	public byte[] getMemPic() {
		return memPic;
	}

	public void setMemPic(byte[] memPic) {
		this.memPic = memPic;
	}

	public String getMemIntro() {
		return memIntro;
	}

	public void setMemIntro(String memIntro) {
		this.memIntro = memIntro;
	}

	public String getMemPhone() {
		return memPhone;
	}

	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}

	public Timestamp getBlockStartTime() {
		return blockStartTime;
	}

	public void setBlockStartTime(Timestamp blockStartTime) {
		this.blockStartTime = blockStartTime;
	}

	public Timestamp getBlockEndTime() {
		return blockEndTime;
	}

	public void setBlockEndTime(Timestamp blockEndTime) {
		this.blockEndTime = blockEndTime;
	}

	public Byte getIsAccEna() {
		return isAccEna;
	}

	public void setIsAccEna(Byte isAccEna) {
		this.isAccEna = isAccEna;
	}

	public Byte getIsPartEna() {
		return isPartEna;
	}

	public void setIsPartEna(Byte isPartEna) {
		this.isPartEna = isPartEna;
	}

	public Byte getIsHostEna() {
		return isHostEna;
	}

	public void setIsHostEna(Byte isHostEna) {
		this.isHostEna = isHostEna;
	}

	public Byte getIsRentEna() {
		return isRentEna;
	}

	public void setIsRentEna(Byte isRentEna) {
		this.isRentEna = isRentEna;
	}

	public Byte getIsMsgEna() {
		return isMsgEna;
	}

	public void setIsMsgEna(Byte isMsgEna) {
		this.isMsgEna = isMsgEna;
	}

	public Timestamp getMemCrTime() {
		return memCrTime;
	}

	public void setMemCrTime(Timestamp memCrTime) {
		this.memCrTime = memCrTime;
	}

	public Timestamp getMemLoginTime() {
		return memLoginTime;
	}

	public void setMemLoginTime(Timestamp memLoginTime) {
		this.memLoginTime = memLoginTime;
	}

//	@Override
//	public String toString() {
//		return "MembershipVO [memId=" + memId + ", memAcc=" + memAcc + ", memEmail=" + memEmail + ", memPwd=" + memPwd
//				+ ", memName=" + memName + ", memGender=" + memGender + ", memBirthdate=" + memBirthdate
//				+ ", memUsername=" + memUsername + ", memIntro=" + memIntro + ", memPhone=" + memPhone
//				+ ", blockStartTime=" + blockStartTime + ", blockEndTime=" + blockEndTime + ", isAccEna=" + isAccEna
//				+ ", isPartEna=" + isPartEna + ", isHostEna=" + isHostEna + ", isRentEna=" + isRentEna + ", isMsgEna="
//				+ isMsgEna + ", memCrTime=" + memCrTime + ", memLoginTime=" + memLoginTime + "]";
//	}

}
