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
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(Booked.class);
		List<Booked> bookeds=criteria.list();
		System.out.println(bookeds.get(0).getId());
		session.flush();
		session.close();
		return bookeds;
	}

	@Override
	public List<Booked> searchBkedByCondition(Booked booked) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(Booked.class);
		if(booked==null)return null;
		if(booked.getId()!=null) {
			criteria.add(Restrictions.eq("id", booked.getId()));
		}
		if(booked.getUser()!=null) {
			criteria.add(Restrictions.eq("user", booked.getUser()));
		}
		if(booked.getMeetingroom()!=null) {
			criteria.add(Restrictions.eq("meetingroom", booked.getMeetingroom()));
		}
		if(booked.getDate()!=null) {
			criteria.add(Restrictions.eq("date", booked.getDate()));
		}
		if(booked.getTimeto()!=null) {
			criteria.add(Restrictions.eq("timeto", booked.getTimeto()));
		}
		if(booked.getNumofparticipant()!=null) {
			criteria.add(Restrictions.eq("numofparticipant", booked.getNumofparticipant()));
		}
		List<Booked> list =criteria.list();
		session.flush();
		session.close();
		return list;
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
