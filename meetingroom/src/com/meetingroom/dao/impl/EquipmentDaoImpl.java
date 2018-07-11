package com.meetingroom.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.EquipmentDao;
import com.meetingroom.model.Equipment;

public class EquipmentDaoImpl extends HibernateDaoSupport implements EquipmentDao{

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Equipment> getEquipment(Equipment equipment) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		Criteria criteria=session.createCriteria(Equipment.class);
		if(equipment!=null) {
			if(equipment.getId()!=null&&equipment.getId()!=0) {
				criteria.add(Restrictions.eq("id",equipment.getId()));
			}
			if(equipment.getEquipment()!=null&&!equipment.getEquipment().equals("")) {
				criteria.add(Restrictions.eq("equipment",equipment.getEquipment()));
			}
		}
		return criteria.list();
	}

	@Override
	public void save(Equipment equipment) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("deprecation")
		Session session=this.getSession();
		session.save(equipment);
		session.flush();
		session.close();
	}

	@Override
	public void delete(Equipment equipment) {
		// TODO Auto-generated method stub
		@SuppressWarnings("deprecation")
		Session session=this.getSession();
		session.delete(equipment);
		session.flush();
		session.close();
	}

	@Override
	public void update(Equipment equipment) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.update(equipment);
		session.flush();
		session.close();
	}

}
