/**
 * 
 */
package com.meetingroom.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.meetingroom.model.Log;
import com.meetingroom.model.Meetingroom;
import com.meetingroom.model.Participated;
import com.meetingroom.model.User;
import com.meetingroom.service.BookedService;
import com.meetingroom.service.EquipmentService;
import com.meetingroom.service.HasService;
import com.meetingroom.service.LogService;
import com.meetingroom.service.MeetingroomService;
import com.meetingroom.service.ParticipatedService;
import com.meetingroom.service.UserService;
import com.meetingroom.util.QQEmailAPI;
import com.sun.imageio.plugins.common.I18N;
import com.sun.scenario.effect.Blend;

@Controller
public class UserbookingController {

	
	UserService userService;
    EquipmentService equipmentSerivce;
    MeetingroomService meetingroomService;
    LogService logService;
    @Autowired
    public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Autowired
	public void setMeetingroomService(MeetingroomService meetingroomService) {
		this.meetingroomService = meetingroomService;
	}

	@Autowired
	public void setEquipmentSerivce(EquipmentService equipmentSerivce) {
		this.equipmentSerivce = equipmentSerivce;
	}

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
	@RequestMapping(value="user-bookHistory")
	public String userBookHistory() {
		return "user-bookHistory";
	}
	@RequestMapping(value="user-information")
	public String userInformation() {
		return "user-information";
	}
	@RequestMapping(value="user-updatepsd")
	public String userUpdate() {
		return "user-updatepsd";
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
					String now = new SimpleDateFormat("yyyy-MM-dd").format(bookedlist.get(i).getDate());
					now=now+" "+bookedlist.get(i).getTimeto();
					Participated participated=new Participated();
					participated.setBookid(bookedlist.get(i).getId());
					int count=participatedService.Count(participated);
					System.out.println("count "+count);
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("booked_id", bookedlist.get(i).getId());
					jsonObject.put("roomId", bookedlist.get(i).getMeetingroom().getRoomname());
					jsonObject.put("time",now);
					jsonObject.put("address", bookedlist.get(i).getMeetingroom().getLocate());
					jsonObject.put("book_user", bookedlist.get(i).getUser().getName());
					jsonObject.put("participant", count);
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
		System.out.println(list.size());
		if(list.size()>0) {
			for(int i=0;i<list.size();i++) {
				participatedService.delete(list.get(i));
			}
		}
		
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
		List<Meetingroom> listmeetingroom=new ArrayList<>();//返回可以选择的room
		Map<String, String[]> map = request.getParameterMap();		
		List<Has> haslist=new ArrayList<>();//记录最初每一条记录，进行后续匹配，一个equipment，为一条记录
        String date="";
        String time="";
        
	    List<String> floorList=new ArrayList<>();
        for (String key : map.keySet()) {
        	System.out.println("key "+key);
        	for (String value : map.get(key)) {
        		System.out.println("value "+value);
			}
			if(key.equals("date")) {//获取时间date
        		for (String value : map.get(key)) {
        			date=value;
        		}
        	}
			if(key.equals("floor1")||key.equals("floor2")||key.equals("floor3")) {//获取楼层
				
					floorList.add(key);
				
			}
			if(key.equals("time")) {//获取楼层
				for (String value : map.get(key)) {
					time=value;
				}
			}
        	if(key.equals("media")||key.equals("computer")||key.equals("touying")||key.equals("smal")) {
        		
        		Equipment equipment=new Equipment();
        		equipment.setEquipment(key);
        		List<Equipment> liste=equipmentSerivce.getEquipment(equipment);
        		Equipment equipment1=liste.get(0);
        		System.out.println("equipment"+equipment1.getEquipment());
        		Has has=new Has();
        		has.setEquipment(equipment1);
        		List<Has> list2=hasService.searchHas(has);//找到需要的条目
        		System.out.println("hassize"+list2.size());
        		if(list2.size()>0) {
        			for(Has has2:list2) {
            			haslist.add(has2);
            		} 	
        		}
        		      		
        	} 
        	
        }
		 Date date2=null;
	    SimpleDateFormat format =new SimpleDateFormat("dddd-mm-yy");
	    
	      try {
			date2=format.parse(date);
			System.out.println(date2.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Meetingroom> listM=new ArrayList<>();    
		List<Meetingroom> listM1=new ArrayList<>();
		
        List<Booked> listB=new ArrayList<>(); //查询符合条件的会议室的预定情况
        
        if(haslist.size()>0) {
        	System.out.println("haslist"+haslist.size());
        	 for(Has has:haslist) {//获取符合设备的meetingroom
             	listM.add(has.getMeetingroom());
             	System.out.println("mm"+has.getMeetingroom().getId()+" "+has.getMeetingroom().getFloor());
             }
        }
    	System.out.println("listM "+listM.size()+" ");
        //筛选符合楼层的会议室
        for(String floor:floorList) {
        	String floors="";
        	if (floor.equals("floor1")) {
				floors="1";
			}
          if (floor.equals("floor2")) {
        	  floors="2";
        	  System.out.println("floor2 "+floors);
			}
         if (floor.equals("floor3")) {
        	 floors="3";
           }
        if (floor.equals("floor4")) {
        	floors="4";
          }
        	for(Meetingroom m:listM) {
        		if(m.getFloor().equals(floors)) {
        			listM1.add(m);
        		}
        	}
        }
        System.out.println("listM1 "+listM1.size());
       if(listM1.size()>0){//查询符合条件的会议室的预定情况
    	   
    	   for(Meetingroom m:listM1) {
    		   Booked booked =new Booked();
    		   booked.setMeetingroom(m);
    		   booked.setDate(date2);
    		   booked.setTimeto(time);
    		   List<Booked> list=bookedService.searchBkedByCondition(booked); 
    		   if(!(list.size()>0)) {
    				System.out.println("boolist "+list.size());
    			  listmeetingroom.add(m);//获取最终适合这个时间的会议室
    		   } 		   
    	   }
       }
       
       if (listmeetingroom.size()>0) {
    	   JSONObject object=new JSONObject();   
           try {
    		object.put("len", listmeetingroom.size());
    		 org.json.JSONArray jsonArray=new org.json.JSONArray();
    	       for(int i=0;i<listmeetingroom.size();i++) {
    	    	   System.out.println("listmeetingroom "+listmeetingroom.size());   
    	    	   JSONObject json=new JSONObject();
    	    	   json.put("ID", listmeetingroom.get(i).getId());
    	    	   json.put("location", listmeetingroom.get(i).getLocate());
    	    	   json.put("roomname", listmeetingroom.get(i).getRoomname());
    	    	   json.put("floor", listmeetingroom.get(i).getFloor());
    	    	   json.put("limit", listmeetingroom.get(i).getPeoplelimit());
    	    	   for(int j=0;j<haslist.size();j++) {
    	    		   if(listmeetingroom.get(i).getId()==(haslist.get(j).getMeetingroom().getId())) {
    	    			   String equipment1="";
    	    			   String equipment=haslist.get(j).getEquipment().getEquipment();
    	    			   if(equipment.equals("media")) {
    	    				   equipment1="多媒体室";
    	    			   }
                           if(equipment.equals("computer")) {
    	    				   equipment1="电脑室";
    	    			   }
                          if(equipment.equals("touying")) {
    	    				   equipment1="投影室";
    	    			   }
                          if(equipment.equals("smal")) {
                        	  equipment1="小型办公室";
    	    			   }
    	    			   json.put("equipment", equipment1);
    	    		   }
    	    	   }
    	    	   jsonArray.put(json);
    	      	    
    	       }
    	       object.put("data", jsonArray);
    	       //data2
    	       jsonArray=new org.json.JSONArray();
    	       List<User> listuser=userService.getUserList();
    	       object.put("len2", listuser.size());
    	       for(int i=0;i<listuser.size();i++) {
    	    	   
    	    	   JSONObject json=new JSONObject();
    	    	   json.put("userid", listuser.get(i).getUserid());
    	    	   json.put("username", listuser.get(i).getName());   	  
    	    	   json.put("email", listuser.get(i).getEmail());
    	    	   jsonArray.put(json);
    	      	    
    	       }
    	       object.put("data2", jsonArray);
    	       System.out.println(object.toString());
    	       try {
    			response.getWriter().println(object.toString());
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
	
	@RequestMapping(value="addBook")
	public void addBook(HttpServletRequest request,HttpServletResponse response) {
		
		String roomId=null;
		String date=null;
		String time=null;
		String[] useridList=null;
		Map<String, String[]> map = request.getParameterMap();
		for (String key : map.keySet()) {
			if(key.equals("roomid")) {
				for (String value : map.get(key)) {
	        		roomId=value;
	        		System.out.println("roomid "+roomId);
				}
			}
			if(key.equals("date")) {
				for (String value : map.get(key)) {
	        		date=value;
				}
			}
			if(key.equals("time")) {
				for (String value : map.get(key)) {
	        		time=value;
				}
			}
			
			if(key.equals("participants")) {
				for (String value : map.get(key)) {
	        		useridList=value.split(",");
	        		System.out.println("useridlist "+useridList.toString());
	        		System.out.println("useridlist "+useridList.length);
				}
			}
			
		}
		
		 Date date2=null;
		 System.out.println("date "+date);
		    SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
		    
		      try {
				date2=format.parse(date);
				 System.out.println("date2 "+date2.toGMTString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      User user=(User) request.getSession().getAttribute("user");
		      
		      Meetingroom meetingroom=new Meetingroom();
		      meetingroom.setId(Integer.parseInt(roomId));
		      List<Meetingroom> listM=meetingroomService.searchMRByCondition(meetingroom);
		      Meetingroom meetingroom1=listM.get(0);
		      Booked booked=new Booked();
		      booked.setUser(user);
		      booked.setDate(date2);
		      booked.setMeetingroom(meetingroom1);
		      booked.setNumofparticipant(meetingroom1.getPeoplelimit());
		      booked.setTimeto(time);
		      bookedService.saveBKed(booked);
		      
		     		     
		      Integer bookid=booked.getId();
		      for(int i=0;i<useridList.length;i++) {
		    	   System.out.println("participated ");
		    	  Participated participated=new Participated();
		    	  participated.setBookid(bookid);
		    	  participated.setUserid(Integer.parseInt(useridList[i]));
		    	  participatedService.save(participated);
		    	  
		      }
		      getEmails(booked.getId());
		      JSONObject jsonObject=new JSONObject();
			  try {
				jsonObject.put("status", "success");
				response.getWriter().println(jsonObject.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		      
	}
	/*@RequestMapping(value = "sendEmail")*/
	public void getEmails(Integer bookid) {
		
		Booked booked = new Booked();
		booked.setId(bookid);
		Participated par = new Participated();
		List<Booked> lBookeds=bookedService.searchBkedByCondition(booked);
		Booked booked2=lBookeds.get(0);
		par.setBookid(bookid);
		List<Participated> participateds = participatedService.searchParticipated(par);
		 List<String> list=new LinkedList<String>();
//		 Map<String,String> map=new HashMap<String,String>();
		 QQEmailAPI qqEmailAPI=new QQEmailAPI();
			qqEmailAPI.init("763829015@qq.com","mlfrqagshnttbejc");//发件人邮箱及授权码
		for(int i=0;i<participateds.size();i++) {
			Integer userid=participateds.get(i).getUserid();
			User user=new User();
			user.setUserid(userid);
			List<User> lUsers=userService.searchUser(user);
			User user2=lUsers.get(0);
			
			String email =user2.getEmail();
			if(email==null || email.equals(""))continue;
			//此处填写调用发送邮件的代码
			System.out.println(i+1+":"+email);
//			list.add(email);
//			map.put(email, participateds.get(i).getUser().getName());
			qqEmailAPI.sendToOne(email,"会议室预订通知","管理员： <br/>" + user2.getName()+
                "，您的会议室预订成功，您即将参与会议，请于"+booked2.getDate()+"到"
					+booked2.getMeetingroom().getLocate() +"参加会议<br/>");
			//
		}
//        qqEmailAPI.sendToMany(list,"","管理员： <br/>" +
//                "会议室预订成功，您即将参与会议<br/>");
	}

	@RequestMapping(value="watchp")
	public void watchp(HttpServletRequest request, HttpServletResponse response) {
		
		String bookid=request.getParameter("booked_id");
		System.out.println("wbookid "+bookid);
		Participated participated=new Participated();
		participated.setBookid(Integer.parseInt(bookid));
		List<Participated> listP=participatedService.searchParticipated(participated);
		System.out.println("listP "+listP.size());
		List<User> listuser=new ArrayList<>();
		for(int i=0;i<listP.size();i++) {
			Integer userid=listP.get(i).getUserid();
			User user=new User();
			user.setUserid(userid);
			List<User> list=userService.searchUser(user);
			System.out.println("listP "+listP.size());
			listuser.add(list.get(0));
		}
		 JSONObject object=new JSONObject(); 
		  try {
	    		object.put("length",listuser.size() );
	    		 org.json.JSONArray jsonArray=new org.json.JSONArray();
	    	       for(int i=0;i<listuser.size();i++) {
	    	    	   System.out.println("参会人员: "+listuser.size());   
	    	    	   JSONObject json=new JSONObject();
	    	    	   json.put("username", listuser.get(i).getName());
	    	    	   json.put("useremail", listuser.get(i).getEmail());	    	    	    
	    	    	   jsonArray.put(json);	    	      	    
	    	       }
	    	       object.put("data", jsonArray);
	    	      
	    	       try {
	    			response.getWriter().println(object.toString());
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
