package com.meetingroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meetingroom.dao.UserDao;
import com.meetingroom.model.User;

@Controller
public class HelloController {
    @Autowired
	UserDao userDao;
	@Autowired
	 public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@RequestMapping(value="hello")
	public String hello() {
	
		List<User> list=userDao.getUserList();
		System.out.println(list.get(0).getEmail());
		return "NewFile";
	}
	
}
