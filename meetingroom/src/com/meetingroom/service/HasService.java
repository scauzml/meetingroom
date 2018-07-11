package com.meetingroom.service;

import java.util.List;

import com.meetingroom.model.Has;

public interface HasService {
	public List<Has> getAllHas();//取所有has数据
	public List<Has> searchHas(Has has);//根据情况来查找
	public void save(Has has);
	public void delete(Has has);
	public void update(Has has);
}
