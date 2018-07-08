package com.meetingroom.model;

import java.util.HashSet;
import java.util.Set;

public class Meetingroom {

	private Integer id;
	private String roomname;
	private String peoplelimit;
	private String locate;
	private String floor;
	private String classnum;
	private Set<Has> hasSet = new HashSet<>();
	
	public Meetingroom(Integer id, String roomname, String peoplelimit, String locate, String floor, String classnum) {
		super();
		this.id = id;
		this.roomname = roomname;
		this.peoplelimit = peoplelimit;
		this.locate = locate;
		this.floor = floor;
		this.classnum = classnum;
	}
	
	

	public Set<Has> getHasSet() {
		return hasSet;
	}



	public void setHasSet(Set<Has> hasSet) {
		this.hasSet = hasSet;
	}



	public Meetingroom() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getPeoplelimit() {
		return peoplelimit;
	}
	public void setPeoplelimit(String peoplelimit) {
		this.peoplelimit = peoplelimit;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getClassnum() {
		return classnum;
	}
	public void setClassnum(String classnum) {
		this.classnum = classnum;
	}
	
	
}
