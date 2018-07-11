package com.meetingroom.service;

import java.util.List;

import com.meetingroom.dao.EquipmentDao;
import com.meetingroom.model.Equipment;

public interface EquipmentService {

	public List<Equipment> getEquipment(Equipment equipment);//根据情况来获取equipment
	public void save(Equipment equipment);
	public void delete(Equipment equipment);
	public void update(Equipment equipment);
}
