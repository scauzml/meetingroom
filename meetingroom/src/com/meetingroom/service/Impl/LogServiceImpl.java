package com.meetingroom.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.LogDao;
import com.meetingroom.model.Log;
import com.meetingroom.service.LogService;

public class LogServiceImpl implements LogService{

	@Autowired
	LogDao logDao;
	@Autowired
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	@Override
	public List<Log> getAllLog() {
		// TODO Auto-generated method stub
		return logDao.getAllLog();
	}

	@Override
	public List<Log> searchLog(Log log) {
		// TODO Auto-generated method stub
		return logDao.searchLog(log);
	}

	@Override
	public void saveLog(Log log) {
		// TODO Auto-generated method stub
		logDao.saveLog(log);
	}

}
