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

	@Override
	public List<Has> searchHas(Has has) {
		Session session=this.getSession();
		Criteria criteria=session.createCriteria(Has.class);
		if(has==null)return null;
		if (has.getId()!=null) {
			criteria.add(Restrictions.eq("id", has.getId()));
		}
		if (has.getEquipment()!=null) {
			criteria.add(Restrictions.eq("equipment", has.getEquipment()));
		}
		if (has.getMeetingroom()!=null) {
			criteria.add(Restrictions.eq("equipment", has.getMeetingroom()));
		}
		
		List<Has> list=criteria.list();
		session.flush();
		session.close();
		return list;
	}

}
