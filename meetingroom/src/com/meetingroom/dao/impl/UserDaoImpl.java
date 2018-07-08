package com.meetingroom.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.meetingroom.dao.UserDao;
import com.meetingroom.model.User;


public class UserDaoImpl extends HibernateDaoSupport implements UserDao{

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		Session session =this.getSession();
		Criteria criteria =session.createCriteria(User.class);
	    List<User> userList=criteria.list();
	    return userList;
	}

	@Override
	public List<User> searchUser(User user) {
		// TODO Auto-generated method stub
		 Session session=this.getSession();
		 Criteria criteria=session.createCriteria(User.class);
		 
		 Example example=Example.create(user);
		 criteria.add(example);
		 List<User> list=criteria.list();
		 return list;
	}

	@Override
	public List<User> searchUserByIdOrName(User user) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		 Criteria criteria=session.createCriteria(User.class);
		 if(user!=null) {
			 if(user.getName()!=null&&!user.getName().equals("")) {
				 
				 criteria.add(Restrictions.eq("name",user.getName()));
			 }
		 }
		return criteria.list(); 
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		 Session session=this.getSession();
 	     session.save(user);
 	     session.close();
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.update(user);
		session.flush();
		session.close();
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		Session session=this.getSession();
		session.delete(user);
		session.flush();
		session.close();
	}

	
}
