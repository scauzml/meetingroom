package com.meetingroom.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.LogDao;
import com.meetingroom.model.Has;
import com.meetingroom.model.Log;

public class LogDaoImpl extends HibernateDaoSupport implements LogDao {

	@Override
	public List<Log> getAllLog() {
		Session session =this.getSession();
		Criteria criteria = session.createCriteria(Log.class);
		List<Log> list =criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<Log> searchLog(Log log) {
		Session session=this.getSession();
		Criteria criteria=session.createCriteria(Has.class);
		if(log==null)return null;
		if (log.getId()!=null) {
			criteria.add(Restrictions.eq("id", log.getId()));
		}
		if (log.getBooked()!=null) {
			criteria.add(Restrictions.eq("booked.id", log.getBooked().getId()));
		}
		if (log.getText()!=null) {
			criteria.add(Restrictions.eq("equipment", log.getText()));
		}
		if (log.getTimeto()!=null) {
			criteria.add(Restrictions.eq("timeto", log.getTimeto()));
		}
		List<Log> list=criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public void saveLog(Log log) {
		Session session =this.getSession();
		Transaction ts=session.beginTransaction();
		session.save(log);
		ts.commit();
		session.close();
	}

}
