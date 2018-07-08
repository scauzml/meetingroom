package com.meetingroom.model;

import java.util.Date;

public class Log {

	private Integer id;
	private Booked booked;
	private String timeto;
	private String text;		
	
	public Log(Integer id, Booked booked, String timeto, String text) {
		super();
		this.id = id;
		this.booked = booked;
		this.timeto = timeto;
		this.text = text;
	}

	public Booked getBooked() {
		return booked;
	}

	public void setBooked(Booked booked) {
		this.booked = booked;
	}

	public Log() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getTimeto() {
		return timeto;
	}

	public void setTimeto(String timeto) {
		this.timeto = timeto;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
