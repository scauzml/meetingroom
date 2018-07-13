package com.meetingroom.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.BookedDao;
import com.meetingroom.model.Booked;

public class BookedDaoImpl extends HibernateDaoSupport implements BookedDao{

	@Override
	public List<Booked> getAllBKed() {
		Session session=null;
		try {
			
			 session= this.getSession();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ajslkdfjlaskfjlasdfj\n\n\n\n\n\n\n");
		}
		
		Criteria criteria = session.createCriteria(Booked.class);
		List<Booked> bookeds=criteria.list();
		System.out.println(bookeds.get(0).getId());
		session.flush();
		session.close();
		return bookeds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Booked> searchBkedByCondition(Booked booked) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(Booked.class);
		if(booked!=null) {
			if(booked.getId()!=null) {
				System.out.println("bookedgetid "+booked.getId());
				criteria.add(Restrictions.eq("id", booked.getId()));
			}
			if(booked.getUser()!=null) {
				System.out.println("user "+booked.getUser().getUserid());
				criteria.add(Restrictions.eq("user.userid", booked.getUser().getUserid()));
			}
			if(booked.getMeetingroom()!=null) {
				System.out.println("meetingroom "+booked.getMeetingroom().getId());
				criteria.add(Restrictions.eq("meetingroom.id", booked.getMeetingroom().getId()));
			}
			if(booked.getDate()!=null) {
				System.out.println("date "+booked.getDate().getTime());
				criteria.add(Restrictions.eq("date", booked.getDate()));
			}
			if(booked.getTimeto()!=null&&!booked.getTimeto().equals("")) {
				System.out.println("timeto "+ booked.getTimeto());
				criteria.add(Restrictions.eq("timeto", booked.getTimeto()));
			}
			if(booked.getNumofparticipant()!=null&&!booked.getNumofparticipant().equals("")) {
				System.out.println("numofparticipant "+ booked.getNumofparticipant());
				criteria.add(Restrictions.eq("numofparticipant", booked.getNumofparticipant()));
			}
		}
		
	
		
		
		return criteria.list();
	}

	@Override
	public void saveBKed(Booked booked) {
		Session session = this.getSession();
		Transaction ts =session.beginTransaction();
		session.save(booked);
		ts.commit();
		session.close();
	}

	@Override
	public void deleteBKed(Booked booked) {
		Session session = this.getSession();
		Transaction ts=session.beginTransaction();
		session.delete(booked);
		ts.commit();
		session.close();
	}

}
