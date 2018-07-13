package com.meetingroom.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.classmate.util.ResolvedTypeCache.Key;
import com.meetingroom.model.Booked;
import com.meetingroom.model.Log;
import com.meetingroom.model.User;
import com.meetingroom.service.BookedService;
import com.meetingroom.service.LogService;
import com.meetingroom.service.UserService;
@Controller
public class UserMessageController{

	@Autowired
    UserService userService;
	@Autowired
	BookedService bookedService;
	@Autowired
	LogService logService;
	@Autowired
    public void setLogService(LogService logService) {
		this.logService = logService;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
    @Autowired
    public void setBookedService(BookedService bookedService) {
		this.bookedService = bookedService;
	}

	@RequestMapping(value="usergetinformation")//个人信息
    public void getMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
    	User user=(User)request.getSession().getAttribute("user");
    	if(user == null) {
    		System.out.println("usser"+user.getUserid());
    		response.getWriter().println("{\"status\":\"anon\"}" );
    	}
    	
    	Integer user_id=Integer.valueOf(user.getUserid());
    
    	user.setUserid(user_id);
    	List<User> userList=userService.searchUserByIdOrName(user);
    	User myuser=userList.get(0);
    	String name=myuser.getName();
    	String sex=myuser.getSex();
    	String email=myuser.getEmail();
    	String phonenum=myuser.getPhonenum();
    	String address=myuser.getLocation();
    	JSONObject json=new JSONObject();
        try {
        	JSONObject object=new JSONObject();
        	object.put("username", name);
        	object.put("email", email);
        	object.put("phone", phonenum);
        	object.put("sex", sex);
        	object.put("address",address);
        	json.put("data", object);
        	response.getWriter().println(json.toString());
        	System.out.println("个人"+json.toString());
        }catch(JSONException e) {
        	e.printStackTrace();
        }
    }
    
    @RequestMapping(value="user-updateinformation")//更改信息
    public void updateMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	User user=(User)request.getSession().getAttribute("user");
    	if(user == null) {
    		response.getWriter().println("{\"status\":\"anon\"}" );
    	}
    	Integer user_id=Integer.valueOf(user.getUserid());
        user.setUserid(user_id);
        String name=request.getParameter("username").toString();
        String sex=request.getParameter("sex").toString();
        String email=request.getParameter("email").toString();
        String location=request.getParameter("address");
        String phonenum=request.getParameter("phone");
        List<User> userList=userService.searchUserByIdOrName(user);
        user=userList.get(0);
        user.setName(name);
        user.setEmail(email);
        user.setLocation(location);
        user.setPhonenum(phonenum);
        userService.updateUser(user);
        request.getSession().setAttribute("user",user);
        
        try {
			 response.getWriter().println("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @RequestMapping(value="user-updatePSD")//更改密码
    public void updatePsw(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	User user=(User)request.getSession().getAttribute("user");
    	System.out.println("user "+user);
    	if(user == null) {
    		response.getWriter().println("{\"status\":\"anon\"}" );
    	}
    	
        
    	String oldpassword=null;
    	String newpassword=null;
    	Map<String, String[]> map=request.getParameterMap();
    	for(String key : map.keySet()) {
    		for(String value :map.get(key)) {
    			if(key.equals("oldPassword"))
    				oldpassword=value;
    			if(key.equals("newPassword")) {
    				newpassword=value;
    			}
    		}
    	}
        System.out.println("oldpassword "+oldpassword);
         System.out.println("nwepassword "+newpassword);
        
         JSONObject json=new JSONObject();
         if(oldpassword.equals(user.getPsd())) {//密码匹配
        	 System.out.println("user "+user.getPsd());
        		try {
        			     user.setPsd(newpassword);
        			     userService.updateUser(user);    
        			     json.put("status", true);
        	 			 response.getWriter().println("Success");
        	 		} catch (JSONException e) {
        	 			// TODO Auto-generated catch block
        	 			e.printStackTrace();
        	 		}
           
         } else {
        	 //原密码错误
        	 try {
				json.put("status", "anon");
				response.getWriter().println(json.toString());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
    }
    
    @RequestMapping(value="user-history")
    public void history(HttpServletRequest request,HttpServletResponse response) {
    	User user=(User)request.getSession().getAttribute("user");
    	if(user == null) {
    		try {
				response.getWriter().println("{\"status\":\"anon\"}" );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	Integer user_id=Integer.valueOf(user.getUserid());
    	user.setUserid(user_id);
    	Log log=new Log();
    	Booked booked=new Booked();
    	booked.setUser(user);
    	log.setBooked(booked);
        List<Log> logList=logService.searchLog(log); 
    	JSONObject js=new JSONObject();
    	try {
			js.put("length", logList.size());
			JSONArray array=new JSONArray();
			for(int i=0;i<logList.size();i++) {
				JSONObject object = new JSONObject();
				object.put("roomId", logList.get(i).getBooked().getMeetingroom().getId());
				object.put("time", logList.get(i).getTimeto());
				object.put("address", logList.get(i).getBooked().getMeetingroom().getLocate());
				object.put("book_user", logList.get(i).getBooked().getUser().getName());
				object.put("status", "finished");
				array.put(object);
			}
			js.put("data", array);
			System.out.println("test:"+js.toString());
			response.getWriter().println(js.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    
}
