package com.postreport.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post_report")
public class PostReportVO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	public PostReportVO() {
	}
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rep_id", insertable = false, updatable = false)
	private Integer repId;

	@Column(name = "post_id", updatable = false)
	private Integer postId;

	@Column(name = "mem_id", updatable = false)
	private Integer memId;

	@Column(name = "emp_id")
	private Integer empId;

	@Column(name = "rep_title", updatable = false)
	private String repTitle;

	@Column(name = "rep_content", updatable = false)
	private String repContent;

	@Column(name = "rep_pic", columnDefinition = "longblob", updatable = false)
	private byte[] repPic;

	@Column(name = "rep_status", insertable = false)
	private Byte repStatus;

	@Column(name = "rep_time", insertable = false, updatable = false)
	private Timestamp repTime;

	public Integer getRepId() {
		return repId;
	}

	public void setRepId(Integer repId) {
		this.repId = repId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getRepTitle() {
		return repTitle;
	}

	public void setRepTitle(String repTitle) {
		this.repTitle = repTitle;
	}

	public String getRepContent() {
		return repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
	}

	public byte[] getRepPic() {
		return repPic;
	}

	public void setRepPic(byte[] repPic) {
		this.repPic = repPic;
	}

	public Byte getRepStatus() {
		return repStatus;
	}

	public void setRepStatus(Byte repStatus) {
		this.repStatus = repStatus;
	}

	public Timestamp getRepTime() {
		return repTime;
	}

	public void setRepTime(Timestamp repTime) {
		this.repTime = repTime;
	}

}

