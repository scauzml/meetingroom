package com.meetingroom.model;

import java.util.HashSet;
import java.util.Set;

public class Equipment {

	private Integer id;
	private String equipment;
	private Set<Has> hasSet = new HashSet<>();

	public Equipment(Integer id, String equipment) {
		super();
		this.id = id;
		this.equipment = equipment;
	}
	
	

	public String getEquipment() {
		return equipment;
	}



	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}



	public Set<Has> getHasSet() {
		return hasSet;
	}



	public void setHasSet(Set<Has> hasSet) {
		this.hasSet = hasSet;
	}



	public Equipment() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
