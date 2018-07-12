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
	
	@RequestMapping(value="loginup")
	public void loginup(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		/*if(status.equals("login"))return "index";*/
		User user=new User();
		PrintWriter writer=null;
		
		String id = request.getParameter("userid");
		System.out.println("test:"+id);
		String psd=request.getParameter("psd");
		System.out.println("psd "+psd);
		int userid =0;
		try {
			userid=Integer.valueOf(id);
		} catch (NumberFormatException e) {
			org.json.JSONObject object2 =new org.json.JSONObject();
			try {
				object2.put("status", "anon");
				object2.put("username", "anon");
				object2.put("userID", "anon");
				object2.put("type", "anon");
				writer=response.getWriter();
				writer.print(object2.toString());
			} catch (JSONException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		user.setUserid(userid);
		user.setPsd(psd);
		List<User> list = userService.searchUser(user);
		if(list.size()>0) {
//			JSONArray jsonArray=new JSONArray();
			try {
				
				request.getSession().setAttribute("user", list.get(0));//保存用户
				request.getSession().setAttribute("status","login");	
				
				org.json.JSONObject object2 =new org.json.JSONObject();
				object2.put("status", "login");
				object2.put("username", list.get(0).getName());
				object2.put("userID", list.get(0).getUserid());
				object2.put("type", list.get(0).getUserType());
				writer=response.getWriter();
				writer.print(object2.toString());
				
			} catch (NestableRuntimeException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
						
		}else {
			
			try {
				org.json.JSONObject object2 =new org.json.JSONObject();
				object2.put("status", "anon");
				object2.put("username", "anon");
				object2.put("userID", "anon");
				object2.put("type", "anon");
				writer=response.getWriter();
				writer.print(object2.toString());
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	@RequestMapping(value="user-index")
	public String get() {
		return "user-index";
	}
	
}
