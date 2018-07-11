package com.meetingroom.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.EquipmentDao;
import com.meetingroom.model.Equipment;
import com.meetingroom.service.EquipmentService;

public class EquipmentServiceImpl implements EquipmentService{

	EquipmentDao equipmentDao;
	@Autowired
	public void setEquipmentDao(EquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
	}

	@Override
	public List<Equipment> getEquipment(Equipment equipment) {
		// TODO Auto-generated method stub
		return equipmentDao.getEquipment(equipment);
	}

	@Override
	public void save(Equipment equipment) {
		// TODO Auto-generated method stub
		equipmentDao.save(equipment);
	}

	@Override
	public void delete(Equipment equipment) {
		// TODO Auto-generated method stub
		equipmentDao.delete(equipment);
	}

	@Override
	public void update(Equipment equipment) {
		// TODO Auto-generated method stub
		equipmentDao.update(equipment);
	}

}
