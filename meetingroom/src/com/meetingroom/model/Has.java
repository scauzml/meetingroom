package com.meetingroom.model;

public class Has {

	private Integer id;
	private Equipment equipment;
	private Meetingroom meetingroom;
	
	public Has(Integer id, Equipment equipment, Meetingroom meetingroom) {
		super();
		this.id = id;
		this.equipment = equipment;
		this.meetingroom = meetingroom;
	}
	public Has() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public Meetingroom getMeetingroom() {
		return meetingroom;
	}
	public void setMeetingroom(Meetingroom meetingroom) {
		this.meetingroom = meetingroom;
	}
	
}
