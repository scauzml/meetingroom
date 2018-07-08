package com.meetingroom.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Booked {

	private Integer id;
	private User user;
	private Meetingroom meetingroom;
	private Date date;
	private String timeto;	
	private String numofparticipant;
		
	
	public Booked(Integer id, User user, Meetingroom meetingroom, Date date, String timeto, String numofparticipant) {
		super();
		this.id = id;
		this.user = user;
		this.meetingroom = meetingroom;
		this.date = date;
		this.timeto = timeto;
		this.numofparticipant = numofparticipant;
	}

	
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getTimeto() {
		return timeto;
	}


	public void setTimeto(String timeto) {
		this.timeto = timeto;
	}



	public Booked() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Meetingroom getMeetingroom() {
		return meetingroom;
	}
	public void setMeetingroom(Meetingroom meetingroom) {
		this.meetingroom = meetingroom;
	}
	
	public String getNumofparticipant() {
		return numofparticipant;
	}
	public void setNumofparticipant(String numofparticipant) {
		this.numofparticipant = numofparticipant;
	}
	
}
