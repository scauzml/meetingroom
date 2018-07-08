package com.meetingroom.dao;

import java.util.List;

import com.meetingroom.model.Participated;

public interface ParticipatedDao {

	public List<Participated> getAllParticipated();
	public List<Participated> searchParticipated(Participated participated);
}
