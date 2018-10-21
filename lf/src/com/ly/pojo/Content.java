package com.ly.pojo;

import java.util.List;

public class Content {

	private String cid;
	private String title;
	private String addTime;
	private String contents;
	private User user;
	private List<Comment> commentList;
	private List<ReDu> reduList;
	private Integer redu;
	private Integer totalCommentSum;
	private Integer count;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRedu() {
		return redu;
	}

	public void setRedu(Integer redu) {
		this.redu = redu;
	}

	public Integer getTotalCommentSum() {
		return totalCommentSum;
	}

	public void setTotalCommentSum(Integer totalCommentSum) {
		this.totalCommentSum = totalCommentSum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ReDu> getReduList() {
		return reduList;
	}

	public void setReduList(List<ReDu> reduList) {
		this.reduList = reduList;
	}

}
