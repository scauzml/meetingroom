package com.meetingroom.service;

import java.util.List;

import com.meetingroom.model.Participated;

public interface ParticipatedService {

	public List<Participated> getAllParticipated();
	public List<Participated> searchParticipated(Participated participated);
	public void delete(Participated participated);
	public void save(Participated participated);
	public int Count(Participated participated);
}
