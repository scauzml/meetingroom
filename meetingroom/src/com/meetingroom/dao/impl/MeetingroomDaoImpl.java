package com.meetingroom.dao.impl;

import java.util.List;



import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
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
		 
		 Example example=Example.create(meetingroom);
		 criteria.add(example);
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
		session.delete(session);
		session.flush();
		session.close();
	}
	
	

}
