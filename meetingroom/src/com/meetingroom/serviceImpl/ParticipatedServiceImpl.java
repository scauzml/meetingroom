package com.meetingroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.ParticipatedDao;
import com.meetingroom.model.Participated;
import com.meetingroom.service.ParticipatedService;

public class ParticipatedServiceImpl implements ParticipatedService  {

	@Autowired
	ParticipatedDao participatedDao;
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

}
