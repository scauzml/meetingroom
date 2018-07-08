package com.meetingroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.BookedDao;
import com.meetingroom.model.Booked;
import com.meetingroom.service.BookedService;

public class BookedServiceImpl implements BookedService{

	@Autowired
	BookedDao bookedDao;
	@Override
	public List<Booked> getAllBKed() {
		// TODO Auto-generated method stub
		return bookedDao.getAllBKed();
	}

	@Override
	public List<Booked> searchBkedByCondition(Booked booked) {
		// TODO Auto-generated method stub
		return bookedDao.searchBkedByCondition(booked);
	}

	@Override
	public void saveBKed(Booked booked) {
		// TODO Auto-generated method stub
		bookedDao.saveBKed(booked);
	}

	@Override
	public void deleteBKed(Booked booked) {
		// TODO Auto-generated method stub
		bookedDao.deleteBKed(booked);
	}

}
