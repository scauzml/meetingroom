package com.meetingroom.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.HasDao;
import com.meetingroom.model.Has;
import com.meetingroom.model.Log;

public class HasDaoImpl extends HibernateDaoSupport implements HasDao{

	@Override
	public List<Has> getAllHas() {
		Session session =this.getSession();
		Criteria criteria = session.createCriteria(Has.class);
		List<Has> list =criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Has> searchHas(Has has) {
		Session session=this.getSession();
		Criteria criteria=session.createCriteria(Has.class);
		
		if (has.getId()!=null) {
			criteria.add(Restrictions.eq("id", has.getId()));
		}
		if (has.getEquipment()!=null) {
			criteria.add(Restrictions.eq("equipment.id", has.getEquipment().getId()));
		}
		if (has.getMeetingroom()!=null) {
			criteria.add(Restrictions.eq("meetingroom.id", has.getMeetingroom().getId()));
		}
		
		List<Has> list=criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public void save(Has has) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.merge(has);
		session.flush();
		session.close();
	}

	@Override
	public void delete(Has has) {
		// TODO Auto-generated method stub
		
		Session session = this.getSession();
		session.delete(session);
		session.flush();
		session.close();
	}

	@Override
	public void update(Has has) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.update(has);
		session.flush();
		session.close();
	}

}
