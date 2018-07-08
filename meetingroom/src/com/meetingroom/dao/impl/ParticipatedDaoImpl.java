package com.meetingroom.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.ParticipatedDao;
import com.meetingroom.model.Participated;

public class ParticipatedDaoImpl extends HibernateDaoSupport implements ParticipatedDao {

	@Override
	public List<Participated> getAllParticipated() {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		Criteria criteria=session.createCriteria(Participated.class);
		List<Participated> list=criteria.list();
		return list;
	}

	@Override
	public List<Participated> searchParticipated(Participated participated) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		Criteria criteria=session.createCriteria(Participated.class);
		if(participated!=null) {
			if(participated.getBooked().getId()!=null) {
				criteria.add(Restrictions.eq("booked.id",participated.getBooked().getId()));
			}
		}
		return criteria.list();
	}

}
