package com.meetingroom.dao;

import java.util.List;

import com.meetingroom.model.Participated;

public interface ParticipatedDao {

	public List<Participated> getAllParticipated();
	public List<Participated> searchParticipated(Participated participated);
	public void delete(Participated participated);
	public void save(Participated participated);
	public int Count(Participated participated);
	
}
