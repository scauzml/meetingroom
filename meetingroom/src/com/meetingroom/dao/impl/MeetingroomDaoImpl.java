package com.meetingroom.dao.impl;

import java.util.List;



import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.MeetingroomDao;
import com.meetingroom.model.Meetingroom;
import com.meetingroom.model.User;


public class MeetingroomDaoImpl extends HibernateDaoSupport implements MeetingroomDao{

	@Override
	public List<Meetingroom> getAllMR() {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		Criteria criteria =session.createCriteria(Meetingroom.class);
		List<Meetingroom> list=criteria.list();
		return list;
	}

	@Override
	public List<Meetingroom> searchMRByCondition(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		 Session session=this.getSession();
		 Criteria criteria=session.createCriteria(Meetingroom.class);
		 
		if (meetingroom!=null) {
			criteria.add(Restrictions.eq("id", meetingroom.getId()));		

		}
		 List<Meetingroom> list=criteria.list();
		 return list;
	}

	@Override
	public void savaMR(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.save(meetingroom);
		session.close();
	}

	@Override
	public void UpdateMR(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.update(meetingroom);
		session.flush();
		session.close();
	}

	@Override
	public void deleteMR(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.delete(meetingroom);
		session.flush();
		session.close();
	}

	@Override
	public List<Meetingroom> getMRByPage(int page) {
		// TODO Auto-generated method stub
		 Session session=this.getSession();
		 Criteria criteria=session.createCriteria(Meetingroom.class);
		 criteria.setFirstResult(15*(page-1));
		 criteria.setMaxResults(15);
		 return criteria.list();
		
	}

	@Override
	public List<Meetingroom> searchByLocation(Meetingroom meetingroom) {
		// TODO Auto-generated method stub
		Session session =this.getSession();
		Criteria criteria =session.createCriteria(Meetingroom.class);
		if(meetingroom!=null) {
			criteria.add(Restrictions.like("locate", meetingroom.getLocate(), MatchMode.ANYWHERE));		
		}
		return criteria.list();
	}
	
	

}
