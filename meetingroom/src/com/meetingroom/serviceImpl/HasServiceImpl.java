package com.meetingroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.HasDao;
import com.meetingroom.model.Has;
import com.meetingroom.service.HasService;

public class HasServiceImpl implements HasService{

	@Autowired
	HasDao hasDao;
	@Override
	public List<Has> getAllHas() {
		// TODO Auto-generated method stub
		return hasDao.getAllHas();
	}

	@Override
	public List<Has> searchHas(Has has) {
		// TODO Auto-generated method stub
		return hasDao.searchHas(has);
	}

	
}
