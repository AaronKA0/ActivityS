package com.post;

public class Post {
	
	private Integer postId;
	private Integer memId;
	private String postTitle;
	private String postContent;
	private Byte postStatus;
	private String postTime;
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
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public Byte getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(Byte postStatus) {
		this.postStatus = postStatus;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	@Override
	public String toString() {
		return "Post [postId=" + postId + ", memId=" + memId + ", postTitle=" + postTitle + ", postContent="
				+ postContent + ", postStatus=" + postStatus + ", postTime=" + postTime + "]";
	}
}
