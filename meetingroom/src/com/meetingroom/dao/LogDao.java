package com.meetingroom.dao;

import java.util.List;

import com.meetingroom.model.Log;



public interface LogDao {

	public List<Log> getAllLog();
	public List<Log> searchLog(Log log);//根据情况来查找
	public void saveLog(Log log);
	
}
