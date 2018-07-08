package com.meetingroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.MeetingroomDao;
import com.meetingroom.model.Meetingroom;
import com.meetingroom.service.MeetingroomService;

public class MeetingroomServiceImpl implements MeetingroomService {

	@Autowired
	MeetingroomDao meetingroomDao;
	@Override
	public List<Meetingroom> getAllMR() {
		// TODO Auto-generated method stub
		return meetingroomDao.getAllMR();
	}

	@Override
	public List<Meetingroom> searchMRByCondition(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		return meetingroomDao.searchMRByCondition(meetingroom);
	}

	@Override
	public void savaMR(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		meetingroomDao.savaMR(meetingroom);
	}

	@Override
	public void UpdateMR(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
	 meetingroomDao.UpdateMR(meetingroom);
	}

	@Override
	public void deleteMR(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		meetingroomDao.deleteMR(meetingroom);
	}

}
