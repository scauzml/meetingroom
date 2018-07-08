package com.meetingroom.model;

public class Participated {

	private Integer id;
	private Booked booked;
	private User user;
	
	public Participated(Integer id, Booked booked, User user) {
		super();
		this.id = id;
		this.booked = booked;
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Participated() {
		super();
	}
	public Booked getBooked() {
		return booked;
	}
	public void setBooked(Booked booked) {
		this.booked = booked;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
