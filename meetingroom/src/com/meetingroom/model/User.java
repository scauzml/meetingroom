package com.meetingroom.model;

import java.util.HashSet;
import java.util.Set;

public class User {

	private Integer userid;
	private String name;
	private String psd;
	private String email;
	private String userType;
	private String sex;
	private String location;
	private String phonenum;
	private String picture;
	
	private Set<Booked> bookedset=new HashSet<>();
	private Set<Participated> participated=new HashSet<>();
	
	public User(Integer userid, String psd,String name, String email, String userType, String sex, String location, String phonenum,
			String picture) {
		super();
		this.userid = userid;
		this.name=name;
		this.psd = psd;
		this.email = email;
		this.userType = userType;
		this.sex = sex;
		this.location = location;
		this.phonenum = phonenum;
		this.picture = picture;
	}
	public User() {
		super();
	}
	
	
	public Set<Participated> getParticipated() {
		return participated;
	}
	public void setParticipated(Set<Participated> participated) {
		this.participated = participated;
	}
	public Set<Booked> getBookedset() {
		return bookedset;
	}
	public void setBookedset(Set<Booked> bookedset) {
		this.bookedset = bookedset;
	}
	
	public Integer getUserid() {
		return userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getPsd() {
		return psd;
	}
	public void setPsd(String psd) {
		this.psd = psd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
