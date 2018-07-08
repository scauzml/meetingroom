package com.meetingroom.dao;

import java.util.List;

import com.meetingroom.model.Has;

public interface HasDao {

	public List<Has> getAllHas();
	public List<Has> searchHas(Has has);//根据情况来查找
	
}
