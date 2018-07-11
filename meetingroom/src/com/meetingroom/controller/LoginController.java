package com.meetingroom.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.meetingroom.model.User;
import com.meetingroom.service.UserService;
import com.sun.corba.se.spi.orb.StringPair;
import org.apache.commons.lang.exception.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.json.*;


@Controller
public class LoginController {

	public  String status="anon";
	public static User user=new User();
	@Autowired
	UserService userService;
	@Autowired
	 public void setUserDao(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping(value="index")
	public String Index(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		return "index";
	}
	@RequestMapping(value="login")
	public String Login(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		//if(status.equals("login"))return "";
		//session.setAttribute("status", status);		
		return "login";
	}
	@RequestMapping(value="signup")
	public String signUp(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		if(!status.equals("login"))return "index";
		status="anon";
		LoginController.user=null;
		return "index";
	}
	@RequestMapping(value="loginup")
	public String loginup(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		if(status.equals("login"))return "index";
		User user=new User();
		PrintWriter writer=null;
		
		String id = request.getParameter("userid");
		System.out.println("test:"+id);
		String psd=request.getParameter("psd");
		System.out.println("test:"+psd);
		int userid =0;
		try {
			userid=Integer.valueOf(id);
		} catch (NumberFormatException e) {
			System.out.println("账户名输入有误，请勿输入字母");
		}
		if(userid==0)return "Login"; 
		user.setUserid(userid);
		user.setPsd(psd);
		List<User> list = userService.searchUser(user);
		if(list.size()>0) {
//			JSONArray jsonArray=new JSONArray();
			try {
				LoginController.user=user;
				org.json.JSONObject object2 =new org.json.JSONObject();
				object2.put("status", "login");
				object2.put("username", list.get(0).getName());
				object2.put("userID", list.get(0).getUserid());
				System.out.println("org.json::str:"+object2.toString());
				writer=response.getWriter();
				writer.print(object2);
			} catch (NestableRuntimeException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			session.setAttribute("status", "login");
			status="login";
			switch (list.get(0).getUserType()) {
			case "0":
				return "Login";
			case "1":
				return "redirect:booking";
			default:
				break;
			}
		}
		return "Login";
	}
	
}
