package com.meetingroom.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.UserDao;
import com.meetingroom.model.Meetingroom;
import com.meetingroom.model.User;


public class UserDaoImpl extends HibernateDaoSupport implements UserDao{

	@Override
	public List<User> getUserList() {
		Session session =this.getSession();
		Criteria criteria =session.createCriteria(User.class);
	    List<User> userList=criteria.list();
	    return userList;
	}

	@Override
	public List<User> searchUser(User user) {
		 Session session=this.getSession();
		 Criteria criteria=session.createCriteria(User.class);
		 List<User> list=null;
		 try {
			 if(user.getUserid()!=null&&!user.getUserid().equals("")) {
				 criteria.add(Restrictions.eq("id", user.getUserid()));
			 }
			 if(user.getPsd()!=null&&!user.getPsd().equals("")) {
				 criteria.add(Restrictions.like("psd", user.getPsd(),MatchMode.EXACT));
			 }
		} catch (Exception e) {
			System.err.println("slkjadfklsajlfkd");// TODO: handle exception
		}
		 System.out.println("username:"+user.getUserid());
		 System.out.println("psd:"+user.getPsd());
		 list =criteria.list();
		 session.close();
		 return list;
	}

	@Override
	public List<User> searchUserByIdOrName(User user) {
		Session session=this.getSession();
		 Criteria criteria=session.createCriteria(User.class);
		 if(user!=null) {
			 if(user.getName()!=null||!user.getName().equals("")) {
				 
				 criteria.add(Restrictions.eq("name",user.getName()));
			 }
		 }
		return criteria.list(); 
	}

	@Override
	public void saveUser(User user) {
		 Session session=this.getSession();
 	     session.save(user);
 	     session.close();
	}

	@Override
	public void updateUser(User user) {
		Session session=this.getSession();
		session.update(user);
		session.flush();
		session.close();
	}

	@Override
	public void deleteUser(User user) {
		Session session=this.getSession();
		session.delete(user);
		session.flush();
		session.close();
	}

	@Override
	public List<User> getUserByPage(int page) {
		// TODO Auto-generated method stub
		 Session session=this.getSession();
		 Criteria criteria=session.createCriteria(User.class);
		 criteria.setFirstResult(15*(page-1));
		 criteria.setMaxResults(15);
		 return criteria.list();
	}

	
}
