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
			if(participated.getBookid()!=null) {
				criteria.add(Restrictions.eq("bookid",participated.getBookid()));
			}
			if(participated.getUserid()!=null) {
				criteria.add(Restrictions.eq("userid",participated.getUserid()));
			}
		}
		return criteria.list();
	}

	@Override
	public void delete(Participated participated) {
		// TODO Auto-generated method stub
		
		Session session=this.getSession();		
		session.delete(participated);
		session.flush();
		
		System.out.println("a "+participated.getBookid());
		
		
	}

	@Override
	public void save(Participated participated) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.save(participated);
		session.flush();
		session.close();
		
	}

}
