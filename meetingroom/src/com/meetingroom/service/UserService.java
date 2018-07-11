package com.meetingroom.service;

import java.util.List;

import com.meetingroom.model.User;

public interface UserService {

	public List<User> getUserList();//获取所有用户
	public List<User> getUserByPage(int page);
	public List<User> searchUser(User user);//根据id，password查找用户
	public List<User> searchUserByIdOrName(User user);//根据ID,或者名字查找用户
	public void saveUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
}
