package com.meetingroom.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.meetingroom.model.Booked;
import com.meetingroom.model.Equipment;
import com.meetingroom.model.Has;
import com.meetingroom.model.Meetingroom;
import com.meetingroom.model.User;
import com.meetingroom.service.BookedService;
import com.meetingroom.service.EquipmentService;
import com.meetingroom.service.HasService;
import com.meetingroom.service.MeetingroomService;
import com.meetingroom.service.UserService;
import com.sun.swing.internal.plaf.metal.resources.metal;

@Controller
public class AdminController {
   
	EquipmentService equipmentService;
	MeetingroomService meetingroomService;
	HasService hasService;
	UserService userService;
	@Autowired 
	public void setUserService(UserService userService) {
		userService = userService;
	}
	@Autowired 
	public void setHasService(HasService hasService) {
		this.hasService = hasService;
	}
	@Autowired 
	public void setMeetingroomService(MeetingroomService meetingroomService) {
		this.meetingroomService = meetingroomService;
	}
	@Autowired
	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	@RequestMapping(value="admin")
	public String get() {
		return "admin-rooms";
	}
	@RequestMapping(value="xinzeng")
	public String getXinZen() {
		return "admin-addRoom";
	}
	@RequestMapping(value="bianji")
	public String getBianji() {
		return "admin-updateRoom";
	} 
	
	@RequestMapping(value="adminrooms.")
	public void adminFirstPage(HttpServletRequest request ,HttpServletResponse response,Model model) {
		
		//返回当前用户预定数据
		String viewString="login";
		String isLosin=request.getParameter("login").trim();
	    if(isLosin.equals("login")) {
	    	JSONObject json=new JSONObject();
	   	   List<Meetingroom> meetingList=meetingroomService.getMRByPage(1);//首页查询第一页内容
	   	   try {
			json.put("meetingroomList", meetingList);
			try {
				
				response.getWriter().println(json.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
		
	}
	
	@RequestMapping(value="adminusers.do")
	public String adminUsers() {
		
		return "adminusers";
	}
	
	@RequestMapping(value="adminaddRoom.do")
	public String addM() {
		return "admin-addRoom";
	}
	
	@RequestMapping(value="addRoom" , method=RequestMethod.GET)
	public void addRoom(HttpServletRequest request,HttpServletResponse response) {
		
		
		String location=request.getParameter("location");
		String  floor=request.getParameter("floor");
		String capacity=request.getParameter("capacity");
		String equipment=request.getParameter("equipment");
		
		Meetingroom meetingroom=new Meetingroom();	
		meetingroom.setLocate(location);
		meetingroom.setFloor(floor);
		meetingroom.setClassnum(capacity);
		meetingroomService.savaMR(meetingroom);
		
		Equipment equipment2 = new Equipment();
		equipment2.setEquipment(equipment);
		equipmentService.save(equipment2);
		
		//取出刚才的meetingroom和equipment，获取他们的对象
		List<Equipment> liste=equipmentService.getEquipment(equipment2);
		Equipment equipment3=liste.get(0);
		List<Meetingroom> listm=meetingroomService.searchMRByCondition(meetingroom);
		Meetingroom meetingroom2=listm.get(0);
		Has has = new Has();
		has.setEquipment(equipment3);
		has.setMeetingroom(meetingroom2);
		hasService.save(has);
		JSONObject json=new JSONObject();
		try {
			json.put("status", "true");
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping(value="deleteRoom")
	public void deleteRoom(HttpServletRequest request,HttpServletResponse response) {
		
		Integer roomid=Integer.parseInt(request.getParameter("roomid").trim());
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setId(roomid);
		Has has=new Has();
		has.setMeetingroom(meetingroom);
		List<Has> listH=hasService.searchHas(has);
		Has has2=listH.get(0);
		Equipment equipment=has2.getEquipment();
		//先删除Has
		hasService.delete(has2);
		//再删除meetingroom
		meetingroomService.deleteMR(meetingroom);
		//再删除设备
		equipmentService.delete(equipment);
		
		
		
		JSONObject json=new JSONObject();
		try {
			json.put("status", "true");
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="adminupdateRoom")
	public void updateRoom1(HttpServletRequest request,HttpServletResponse response) {
        //用于首次点击修改会议室返回的数据
		Integer roomid=Integer.parseInt(request.getParameter("roomid"));
		Meetingroom meetingroom =new Meetingroom();
		meetingroom.setId(roomid);
		List<Meetingroom> listM=meetingroomService.searchMRByCondition(meetingroom);
		String pageIndex=request.getParameter("page");
		if(pageIndex==null) {
			pageIndex="1";
		}
		Integer page=Integer.parseInt(pageIndex);
		//按页码请求数据,每页15行
		Meetingroom meetingroom2=listM.get(page);
		
		JSONObject json=new JSONObject();
		try {
			json.put("meetingroom", meetingroom2);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="updateRoom")
	public void updateRoom(HttpServletResponse response,HttpServletRequest request) {
		Integer roomid=Integer.parseInt(request.getParameter("roomid"));
		String location=request.getParameter("location");
		String  floor=request.getParameter("floor");
		String capacity=request.getParameter("capacity");
		String equipment=request.getParameter("equipment");
		
		Meetingroom meetingroom=new Meetingroom();	
		meetingroom.setId(roomid);
		meetingroom.setLocate(location);
		meetingroom.setFloor(floor);
		meetingroom.setClassnum(capacity);
		meetingroomService.UpdateMR(meetingroom);
		Has has=new Has();
		has.setMeetingroom(meetingroom);
		List<Has> listH=hasService.searchHas(has);
		Has has2=listH.get(0);
		Equipment equipment2=has2.getEquipment();
		equipmentService.update(equipment2);	
		//取出刚才的meetingroom和equipment，获取他们的对象		
		JSONObject json=new JSONObject();
		try {
			json.put("status", "true");
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="search")
	public void search(HttpServletRequest request,HttpServletResponse response) {
		
		String searchthing=request.getParameter("searchthing");
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setLocate(searchthing);
		List<Meetingroom> listm=meetingroomService.searchByLocation(meetingroom);
		
		JSONObject json=new JSONObject();
		try {
			json.put("meetingroomlist", listm);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping(value="adminuser")//返回用户管理时的所有用户
	public void getUser(HttpServletResponse response ,HttpServletRequest request) {
		
		String pageIndex = request.getParameter("page");
		if(pageIndex==null) {
			pageIndex="1";
		}
		Integer page=Integer.parseInt(pageIndex);		
		List<User> listUser=userService.getUserByPage(page);
		JSONObject json=new JSONObject();
		try {
			json.put("listUser", listUser);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="adminaddUser")
	public String seeaddUser() {
		return "admin-addUser";
	}
	
	@RequestMapping(value="addUser")
	public void addUser(HttpServletResponse response ,HttpServletRequest request) {
		
	  String username=request.getParameter("usernamer");
	  String email=request.getParameter("email");
	  String telephone=request.getParameter("telephone");
	  String address=request.getParameter("address");
	  String sex=request.getParameter("sex");
	  
	  User user =new User();
	  user.setName(username);
	  user.setEmail(email);
	  user.setPhonenum(telephone);
	  user.setLocation(address);
	  user.setSex(sex);
	  user.setPsd("123456");
	  user.setUserType("0");
	  
	  userService.saveUser(user);
	  JSONObject json=new JSONObject();
		try {
			json.put("status", true);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	}
	
	@RequestMapping(value="deleteUser")
	public void deleteUser(HttpServletResponse response ,HttpServletRequest request) {
	
		String userid=request.getParameter("userid");
		Integer id=Integer.parseInt(userid);
		User user=new User();
		user.setUserid(id);
		userService.deleteUser(user);
		 JSONObject json=new JSONObject();
			try {
				json.put("status", true);
				response.getWriter().println(json.toString());
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	@RequestMapping(value="searchUser")//按用户名找
	public void searchUser(HttpServletRequest request,HttpServletResponse response) {
		String name=request.getParameter("username");
		User user=new User();
		user.setName(name);
		List<User> listuser=userService.searchUserByIdOrName(user);
		 JSONObject json=new JSONObject();
			try {
				json.put("listuser", listuser);
				response.getWriter().println(json.toString());
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
