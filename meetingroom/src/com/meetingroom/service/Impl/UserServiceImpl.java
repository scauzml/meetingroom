package com.meetingroom.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meetingroom.dao.UserDao;
import com.meetingroom.model.User;
import com.meetingroom.service.UserService;

public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return userDao.getUserList();
	}

	@Override
	public List<User> searchUser(User user) {
		// TODO Auto-generated method stub
		return userDao.searchUser(user);
	}

	@Override
	public List<User> searchUserByIdOrName(User user) {
		// TODO Auto-generated method stub
		return userDao.searchUserByIdOrName(user);
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		userDao.saveUser(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		userDao.deleteUser(user);
	}

	@Override
	public List<User> getUserByPage(int page) {
		// TODO Auto-generated method stub
		return userDao.getUserByPage(page);
	}

}
