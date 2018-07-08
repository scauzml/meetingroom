package com.meetingroom.dao;

import java.util.List;

import com.meetingroom.model.Booked;

public interface BookedDao {

	public List<Booked> getAllBKed();//返回所有的预订情况
	public List<Booked> searchBkedByCondition(Booked booked);//根据情况查找会议室
	public void saveBKed(Booked booked);//保存
	public void deleteBKed(Booked booked);//根据id删除
	
	
}
