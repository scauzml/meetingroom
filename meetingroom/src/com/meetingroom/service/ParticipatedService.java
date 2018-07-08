package com.meetingroom.service;

import java.util.List;

import com.meetingroom.model.Participated;

public interface ParticipatedService {

	public List<Participated> getAllParticipated();
	public List<Participated> searchParticipated(Participated participated);
}
