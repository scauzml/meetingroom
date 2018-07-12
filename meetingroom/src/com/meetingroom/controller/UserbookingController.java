/**
 * 
 */
package com.meetingroom.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.hibernate.Criteria;
import net.sf.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.sf.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.meetingroom.model.Booked;
import com.meetingroom.model.Equipment;
import com.meetingroom.model.Has;
import com.meetingroom.model.Meetingroom;
import com.meetingroom.model.Participated;
import com.meetingroom.model.User;
import com.meetingroom.service.BookedService;
import com.meetingroom.service.HasService;
import com.meetingroom.service.ParticipatedService;
import com.meetingroom.service.UserService;
import com.sun.scenario.effect.Blend;

@Controller
public class UserbookingController {

	@Autowired
	UserService userService;
	@Autowired
	public void setUserDao(UserService userService) {
		this.userService = userService;
	}
	
	HasService hasService;	
	@Autowired
	public void setHasService(HasService hasService) {
		this.hasService = hasService;
	}
	@Autowired
	BookedService bookedService;
	@Autowired
	public void setBookedService(BookedService bookedService) {
		this.bookedService = bookedService;
	}
	
	
	ParticipatedService participatedService;
	@Autowired
	public void setParticipatedService(ParticipatedService participatedService) {
		this.participatedService=participatedService;
	}
	
	@RequestMapping(value="user-booking")
	public String userBooking() {
		return "user-booking";
	}
	@RequestMapping(value="booking")
	public void bookingMessage(HttpServletRequest request,HttpServletResponse response) {
		
		User user1 =(User)request.getSession().getAttribute("user");//获取id
		Integer userid=user1.getUserid();
		if(userid.equals("")||userid==null)return;
		User user=new User();
		user.setUserid(userid);
		Booked booked=new Booked();
		booked.setUser(user);
		//根据ID查询当前登陆用户
		List<Booked> bookedlist=bookedService.searchBkedByCondition(booked);
		if(bookedlist.size()>0) {//根据当前用户，查询信息
			try {
				org.json.JSONObject js =new org.json.JSONObject();
				js.put("length", bookedlist.size());
				js.put("status", "login");
				org.json.JSONArray array=new org.json.JSONArray();
				for(int i=0;i<bookedlist.size();i++) {
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("booked_id", bookedlist.get(i).getId());
					jsonObject.put("roomId", bookedlist.get(i).getMeetingroom().getId());
					jsonObject.put("time", bookedlist.get(i).getTimeto());
					jsonObject.put("address", bookedlist.get(i).getMeetingroom().getLocate());
					jsonObject.put("book_user", bookedlist.get(i).getUser().getUserid());
					jsonObject.put("participant", bookedlist.get(i).getId());
					array.put(jsonObject);
				}
				js.put("data", array);
				System.out.println("bookmessage:"+js.toString());
				PrintWriter writer=null;
				writer=response.getWriter();
				writer.print(js.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
		}
		
	}
	@RequestMapping(value="deleteBook")
	public void deleteBook(HttpServletRequest request,HttpServletResponse response) {
		if(!request.getSession().getAttribute("status").equals("login"))return;
		
		String bookedid=request.getParameter("bookedid");	
		System.out.println("bookedid "+bookedid);
		Participated participated=new Participated();				
		participated.setBookid(Integer.parseInt(bookedid));				
		List<Participated> list=participatedService.searchParticipated(participated);
		System.out.println(list.get(0).getBookid());
		participatedService.delete(list.get(0));
		Booked booked=new Booked();
		booked.setId(Integer.parseInt(bookedid));
		bookedService.deleteBKed(booked);
		//bookedService.deleteBKed(booked);
		//返回删除后的数据	
		
		
	}
	@RequestMapping(value="participant")
	public void getParticipant(HttpServletRequest request,HttpServletResponse response) {
		if(!request.getSession().getAttribute("status").equals("login"))return;
	    User user =new User();
	    JSONObject js=new JSONObject();
	    org.json.JSONArray array=new org.json.JSONArray();
	    Participated p=new Participated();
	    Booked b=new Booked();
	    b.setId(1);
	  /*  p.setBooked(b);*/
		List<Participated> list= participatedService.searchParticipated(p);
		try {
			js.put("length", list.size());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		for(int i=0;i<list.size();i++) {
			int id=list.get(i).getId();
			/*try {
				JSONObject json=new JSONObject();
			json.put("name", list.get(i).getUser().getName());
			array.put(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}*/
		}
		try {
			js.put("data",array);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("getParticipant:"+ js.toString());
		PrintWriter writer=null;
		try {
			writer=response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.print(js.toString());
	}
	
	@RequestMapping(value="userBook")
	public String userBook() {
		return "user-Book";
	}
	
	@RequestMapping(value="newbooking")
	public void userBooking(HttpServletRequest request,HttpServletResponse response) {
		Map<String, String[]> map = request.getParameterMap();		
		List<Has> list=new ArrayList<>();//记录最初每一条记录，进行后续匹配，一个equipment，为一条记录
        for (String key : map.keySet()) {
        	if(key.equals("media")||key.equals("computer")||key.equals("touying")||key.equals("smal")) {
        		Equipment equipment=new Equipment();
        		equipment.setEquipment(key);
        		map.remove(key);//取得后移除
        		Has has=new Has();
        		has.setEquipment(equipment);
        		List<Has> list2=hasService.searchHas(has);
        		
        	}
        	
        		for (String value : map.get(key)) {
               
            }
        }

        /*if(key.equals("floor1")||key.equals("floor2")||key.equals("floor3")) {
		
    	}
       if(key.equals("date")) {
    		
    	}
       if(key.equals("time")) {
    	   
       }*/
		
	}
}
