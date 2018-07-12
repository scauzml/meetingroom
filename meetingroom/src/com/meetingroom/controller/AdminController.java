package com.meetingroom.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import org.json.JSONArray;
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

import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

@Controller
public class AdminController {

	EquipmentService equipmentService;
	MeetingroomService meetingroomService;
	HasService hasService;
	UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
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

	@RequestMapping(value = "admin")
	public String get() {
		return "admin-rooms";
	}

	@RequestMapping("admin-rooms")
	public String gett() {
		return "admin-rooms";
	}

	@RequestMapping(value = "xinzeng")
	public String getXinZen() {
		return "admin-addRoom";
	}

	@RequestMapping(value = "admin-updateRoom")
	public String getBianji() {
		return "admin-updateRoom";
	}

	@RequestMapping(value = "adminrooms")
	public void adminFirstPage(HttpServletRequest request, HttpServletResponse response, Model model) {

		// 返回当前用户预定数据
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			try {
				response.getWriter().println("{\"status\":\"anon\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// String viewString="login";
		// String isLosin=request.getParameter("login").trim();
		if (user != null) {

			List<Meetingroom> meetingList = meetingroomService.getAllMR();// 首页查询第一页内容
			try {
				JSONObject json2 = new JSONObject();
				JSONArray array = new JSONArray();
				json2.put("length", meetingList.size());
				for (int i = 0; i < meetingList.size(); i++) {
					JSONObject json = new JSONObject();
					json.put("roomId", meetingList.get(i).getId());
					json.put("address", meetingList.get(i).getLocate());
					json.put("location", meetingList.get(i).getFloor());
					json.put("limit", meetingList.get(i).getPeoplelimit());
					Has has = new Has();
					has.setMeetingroom(meetingList.get(i));
					List<Has> hass = hasService.searchHas(has);
					json.put("equipment", hass.get(0).getEquipment().getEquipment());
					array.put(json);
				}
				json2.put("data", array);
				response.getWriter().print(json2.toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	@RequestMapping(value = "adminusers.do")
	public String adminUsers() {

		return "admin-users";
	}

	@RequestMapping(value = "adminaddRoom")
	public String addM() {
		return "admin-addRoom";
	}

	@RequestMapping(value = "addRoom", method = RequestMethod.GET)
	public void addRoom(HttpServletRequest request, HttpServletResponse response) {

		String roomname = request.getParameter("room-name");
		String location = request.getParameter("room-location");
		String floor = request.getParameter("room-floor");
		String capacity = request.getParameter("room-capacity");
		/* String equipment=request.getParameter("equipment"); */

		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setRoomname(roomname);
		meetingroom.setLocate(location);
		meetingroom.setFloor(floor);
		meetingroom.setPeoplelimit(capacity);
		meetingroomService.savaMR(meetingroom);

		Equipment equipment2 = new Equipment();
		equipment2.setId(1);
		;

		// 取出刚才的meetingroom和equipment，获取他们的对象
		/*
		 * List<Equipment> liste=equipmentService.getEquipment(equipment2); Equipment
		 * equipment3=liste.get(0);
		 */
		List<Meetingroom> listm = meetingroomService.searchMRByCondition(meetingroom);
		Meetingroom meetingroom2 = listm.get(0);
		Has has = new Has();
		has.setEquipment(equipment2);
		has.setMeetingroom(meetingroom2);
		hasService.save(has);
		JSONObject json = new JSONObject();
		try {
			json.put("status", "true");
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "deleteRoom")
	public void deleteRoom(HttpServletRequest request, HttpServletResponse response) {

		Integer roomid = Integer.parseInt(request.getParameter("roomid").trim());
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setId(roomid);
		Has has = new Has();
		has.setMeetingroom(meetingroom);
		List<Has> listH = hasService.searchHas(has);
		Has has2 = listH.get(0);
		Equipment equipment = has2.getEquipment();
		// 先删除Has
		hasService.delete(has2);
		// 再删除meetingroom
		meetingroomService.deleteMR(meetingroom);
		// 再删除设备
		equipmentService.delete(equipment);

		JSONObject json = new JSONObject();
		try {
			json.put("status", "true");
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("updateRoomSaveId") // 保存来自room页面的要修改的ID，不返回数据
	public void saveId(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("roomId", request.getParameter("roomid"));
	}

	@RequestMapping("searchUpdateRoom") // 当页面跳转至修改会议室的页面，需要在session中找出roomId然后返回room的数据
	public void searchUpdate(HttpServletRequest request, HttpServletResponse response) {
		String roomId = (String) request.getSession().getAttribute("roomId");
		System.out.println("roomid"+ roomId);
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setId(Integer.valueOf(roomId));
		JSONObject json = new JSONObject();
		List<Meetingroom> meetingList = meetingroomService.searchMRByCondition(meetingroom);// 首页查询第一页内容
		System.out.println("size "+ meetingList.size());
		try {
			for (int i = 0; i < meetingList.size(); i++) {
				System.out.println("name"+ meetingList.get(i).getRoomname());
				json.put("roomId", meetingList.get(i).getRoomname());
				json.put("address", meetingList.get(i).getLocate());
				json.put("location", meetingList.get(i).getFloor());
				json.put("limit", meetingList.get(i).getPeoplelimit());
				Has has = new Has();
				has.setMeetingroom(meetingList.get(i));
				List<Has> hass = hasService.searchHas(has);
				json.put("equipment", hass.get(0).getEquipment().getEquipment());
				response.getWriter().print(json.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = "updateRoom")
	public void updateRoom1(HttpServletRequest request, HttpServletResponse response) {
		// 用于首次点击修改会议室返回的数据
		Integer roomid = Integer.parseInt(request.getParameter("roomid"));
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setId(roomid);
		List<Meetingroom> listM = meetingroomService.searchMRByCondition(meetingroom);
		String pageIndex = request.getParameter("page");
		if (pageIndex == null) {
			pageIndex = "1";
		}
		Integer page = Integer.parseInt(pageIndex);
		// 按页码请求数据,每页15行
		Meetingroom meetingroom2 = listM.get(page);

		JSONObject json = new JSONObject();
		try {
			json.put("meetingroom", meetingroom2);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 保存修改的信息,对应admin-updateRoom里面的save方法的url
	@RequestMapping(value = "saveUpdate")
	public void updateRoom(HttpServletResponse response, HttpServletRequest request) {
		String  roomid = (String)(request.getParameter("roomId"));
		String location = request.getParameter("location");
		String floor = request.getParameter("floor");
		String capacity = request.getParameter("limit");
		String equipment = request.getParameter("equipment");
        Integer id=Integer.parseInt((String)request.getSession().getAttribute("roomId"));
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setId(id);
		meetingroom.setRoomname(roomid);
		meetingroom.setLocate(location);
		meetingroom.setFloor(floor);
		meetingroom.setPeoplelimit(capacity);
		meetingroomService.UpdateMR(meetingroom);
//		Has has = new Has();
//		has.setMeetingroom(meetingroom);
//		List<Has> listH = hasService.searchHas(has);
//		Has has2 = listH.get(0);
//		Equipment equipment2 = has2.getEquipment();
//		equipmentService.update(equipment2);
		// 取出刚才的meetingroom和equipment，获取他们的对象
		JSONObject json = new JSONObject();
		try {
			json.put("status", "true");
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "search")
	public void search(HttpServletRequest request, HttpServletResponse response) {

		String searchthing = request.getParameter("searchthing");
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setLocate(searchthing);
		List<Meetingroom> listm = meetingroomService.searchByLocation(meetingroom);

		JSONObject json = new JSONObject();
		try {
			json.put("meetingroomlist", listm);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "admin-users") // 返回用户管理时的所有用户
	public void getUser(HttpServletResponse response, HttpServletRequest request) {

		
		List<User> userList = userService.getUserList();// 首页查询第一页内容
		try {
			JSONObject json2 = new JSONObject();
			JSONArray array = new JSONArray();
			json2.put("length", userList.size());
			for (int i = 0; i < userList.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("Id", userList.get(i).getUserid());
				json.put("username", userList.get(i).getName());
				json.put("email", userList.get(i).getEmail());
				json.put("phone", userList.get(i).getPhonenum());
				json.put("address", userList.get(i).getLocation());

				array.put(json);
			}
			json2.put("data", array);
			response.getWriter().print(json2.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@RequestMapping(value = "adminaddUser")
	public String seeaddUser() {
		return "admin-addUser";
	}

	@RequestMapping(value = "addUser")
	public void addUser(HttpServletResponse response, HttpServletRequest request) {

		String username = request.getParameter("user-username");
		String sex = request.getParameter("user-sex");
		String email = request.getParameter("user-email");
		String telephone = request.getParameter("user-phone");
		String address = request.getParameter("user-address");

		User user = new User();
		user.setName(username);
		user.setEmail(email);
		user.setPhonenum(telephone);
		user.setLocation(address);
		user.setSex(sex);
		user.setPsd("123456");
		user.setUserType("0");

		userService.saveUser(user);
		JSONObject json = new JSONObject();
		try {
			json.put("status", true);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "deleteUser")
	public void deleteUser(HttpServletResponse response, HttpServletRequest request) {

		String userid = request.getParameter("userid");
		Integer id = Integer.parseInt(userid);
		User user = new User();
		user.setUserid(id);
		userService.deleteUser(user);
		JSONObject json = new JSONObject();
		try {
			json.put("status", true);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "searchUser") // 按用户名找
	public void searchUser(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("username");
		User user = new User();
		user.setName(name);
		List<User> listuser = userService.searchUserByIdOrName(user);
		JSONObject json = new JSONObject();
		try {
			json.put("listuser", listuser);
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*@RequestMapping(value = "adminusers.do")
	public String adminusers() {

	}*/

}
