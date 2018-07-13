package com.meetingroom.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.ParticipatedDao;
import com.meetingroom.model.Participated;
import com.meetingroom.service.ParticipatedService;

public class ParticipatedServiceImpl implements ParticipatedService  {

	@Autowired
	ParticipatedDao participatedDao;
	@Autowired
	public void setParticipatedDao(ParticipatedDao participatedDao) {
		this.participatedDao = participatedDao;
	}

	@Override
	public List<Participated> getAllParticipated() {
		// TODO Auto-generated method stub
		return participatedDao.getAllParticipated();
	}

	@Override
	public List<Participated> searchParticipated(Participated participated) {
		// TODO Auto-generated method stub
		return participatedDao.searchParticipated(participated);
	}

	@Override
	public void delete(Participated participated) {
		// TODO Auto-generated method stub
		participatedDao.delete(participated);
	}

	@Override
	public void save(Participated participated) {
		// TODO Auto-generated method stub
		participatedDao.save(participated);
	}

	@Override
	public int Count(Participated participated) {
		// TODO Auto-generated method stub		
		return participatedDao.Count(participated);
	}

}
