package com.meetingroom.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.HasDao;
import com.meetingroom.model.Has;
import com.meetingroom.service.HasService;

public class HasServiceImpl implements HasService{

	@Autowired
	HasDao hasDao;
	@Autowired
	public void setHasDao(HasDao hasDao) {
		this.hasDao = hasDao;
	}

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

	@Override
	public void save(Has has) {
		// TODO Auto-generated method stub
		hasDao.save(has);
	}

	@Override
	public void delete(Has has) {
		// TODO Auto-generated method stub
		hasDao.delete(has);
	}

	@Override
	public void update(Has has) {
		// TODO Auto-generated method stub
		
		hasDao.update(has);
	}

	
}
