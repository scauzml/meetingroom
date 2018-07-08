package com.meetingroom.dao;

import java.util.List;

import com.meetingroom.model.Meetingroom;

public interface MeetingroomDao {
	
	public List<Meetingroom> getAllMR();//获取所有会议室
	public List<Meetingroom> searchMRByCondition(Meetingroom meetingroom);//根据条件来查询会议室
	public void savaMR(Meetingroom meetingroom);//增加
	public void UpdateMR(Meetingroom meetingroom);//更新
	public void deleteMR(Meetingroom meetingroom);//根据Id
	
	

}
