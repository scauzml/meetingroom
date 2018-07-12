package com.meetingroom.model;

public class Participated {

	private Integer id;
	private Integer bookid;
	private Integer userid;
	public Participated(Integer id, Integer bookid, Integer userid) {
		super();
		this.id = id;
		this.bookid = bookid;
		this.userid = userid;
	}
	
	public Participated() {
		super();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBookid() {
		return bookid;
	}
	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
	
}
