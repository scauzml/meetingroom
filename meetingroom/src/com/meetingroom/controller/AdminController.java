package com.meetingroom.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.sun.swing.internal.plaf.metal.resources.metal;

import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

@Controller
public class AdminController {

	EquipmentService equipmentService;
	MeetingroomService meetingroomService;
	HasService hasService;
	UserService userService;
	BookedService bookedService;
	ParticipatedService participatedService;
	LogService logService;

	@Autowired
	public void setParticipatedService(ParticipatedService participatedService) {
		this.participatedService = participatedService;
	}

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

	@RequestMapping("admin")
	public String gett() {
		return "admin-rooms";
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
				System.out.println("size" + meetingList.size());
				for (int i = 0; i < meetingList.size(); i++) {
					JSONObject json = new JSONObject();
					json.put("roomId", meetingList.get(i).getId());
					json.put("address", meetingList.get(i).getLocate());
					json.put("location", meetingList.get(i).getFloor());
					json.put("limit", meetingList.get(i).getPeoplelimit());
					Has has = new Has();
					has.setMeetingroom(meetingList.get(i));
					List<Has> hass = hasService.searchHas(has);
					String equip = null;
					if (hass.get(0).getEquipment().getEquipment().equals("media")) {
						equip = "多媒体室";
					}
					if (hass.get(0).getEquipment().getEquipment().equals("computer")) {
						equip = "电脑室";
					}
					if (hass.get(0).getEquipment().getEquipment().equals("touying")) {
						equip = "投影室";
					}
					if (hass.get(0).getEquipment().getEquipment().equals("smal")) {
						equip = "小型办公室";
					}
					json.put("equipment",equip);
					array.put(json);
				}
				json2.put("data", array);
				response.getWriter().print(json2.toString());
				System.out.println("denglu  " + json2.toString());
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

		String roomname = null;/* request.getParameter("room-name"); */
		String location = null;/* request.getParameter("room-location"); */
		String floor = null;/* request.getParameter("room-floor"); */
		String capacity = null;/* request.getParameter("room-capacity"); */
		String equipid = null;/* request.getParameter("eq"); */
		/*
		 * String equipid1=request.getParameter("test");
		 * System.out.println(" eq "+equipid); System.out.println(" eq1 "+equipid1);
		 */
		/* String equipment=request.getParameter("equipment"); */
		Map<String, String[]> map = request.getParameterMap();
		for (String key : map.keySet()) {
			System.out.println("key " + key);
			for (String value : map.get(key)) {
				System.out.println("value " + value);
			}
			if (key.equals("room-name")) {// 获取时间date
				for (String value : map.get(key)) {
					roomname = value;
				}
			}
			if (key.equals("room-location")) {// 获取时间date
				for (String value : map.get(key)) {
					location = value;
				}
			}
			if (key.equals("room-floor")) {// 获取楼层

				for (String value : map.get(key)) {
					floor = value;
				}

			}
			if (key.equals("room-capacity")) {// 获取楼层
				for (String value : map.get(key)) {
					capacity = value;
				}
			}
			if (key.equals("equipment")) {// 获取楼层
				for (String value : map.get(key)) {
					equipid = value;
				}
			}
		}
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setRoomname(roomname);
		meetingroom.setLocate(location);
		meetingroom.setFloor(floor);
		meetingroom.setPeoplelimit(capacity);
		meetingroomService.savaMR(meetingroom);

		Equipment equipment2 = new Equipment();
		equipment2.setId(Integer.parseInt(equipid));
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
			System.out.println(json.toString());
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
		Booked booked = new Booked();
		booked.setMeetingroom(meetingroom);
		List<Booked> bookeds = bookedService.searchBkedByCondition(booked);
		if (bookeds.size() > 0) {
			for (int i = 0; i < bookeds.size(); i++) {
				Participated participated = new Participated();
				participated.setBookid(bookeds.get(i).getId());
				List<Participated> list = participatedService.searchParticipated(participated);
				if (list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						participatedService.delete(list.get(j));
					}

				}
				bookedService.deleteBKed(bookeds.get(i));
			}
		}

		hasService.delete(has2);

		// 再删除meetingroom
		meetingroomService.deleteMR(meetingroom);

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
		System.out.println("roomid" + roomId);
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setId(Integer.valueOf(roomId));
		JSONObject json = new JSONObject();
		List<Meetingroom> meetingList = meetingroomService.searchMRByCondition(meetingroom);// 首页查询第一页内容
		System.out.println("size " + meetingList.size());
		try {
			for (int i = 0; i < meetingList.size(); i++) {
				System.out.println("name" + meetingList.get(i).getRoomname());
				json.put("roomId", meetingList.get(i).getRoomname());
				json.put("address", meetingList.get(i).getLocate());
				json.put("floor", meetingList.get(i).getFloor());
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
		String roomname = request.getParameter("roomname");
		System.out.println("romnaeme" + roomname);
		String location = request.getParameter("address");
		String floor = request.getParameter("floor");
		String capacity = request.getParameter("limit");
		String equipment = request.getParameter("equipment");
		Integer id = Integer.parseInt((String) request.getSession().getAttribute("roomId"));
		System.out.println("romname" + roomname + " location" + location + " capacity" + capacity);
		Meetingroom meetingroom = new Meetingroom();
		meetingroom.setId(id);
		meetingroom.setRoomname(roomname);
		meetingroom.setLocate(location);
		meetingroom.setFloor(floor);
		meetingroom.setPeoplelimit(capacity);
		meetingroomService.UpdateMR(meetingroom);
		// Has has = new Has();
		// has.setMeetingroom(meetingroom);
		// List<Has> listH = hasService.searchHas(has);
		// Has has2 = listH.get(0);
		// Equipment equipment2 = has2.getEquipment();
		// equipmentService.update(equipment2);
		// 取出刚才的meetingroom和equipment，获取他们的对象
		JSONObject json = new JSONObject();
		try {
			json.put("status", "save");
			response.getWriter().println(json.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fdf");
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

		String userid = request.getParameter("id").trim();
		System.out.println("userid " + userid);
		Integer id = Integer.parseInt(userid);
		User user = new User();
		user.setUserid(id);
		Booked booked = new Booked();
		booked.setUser(user);
		List<Booked> listb = bookedService.searchBkedByCondition(booked);
		if (listb.size() > 0) {
			for (int i = 0; i < listb.size(); i++) {

				Participated participated = new Participated();
				participated.setBookid(listb.get(i).getId());
				List<Participated> list = participatedService.searchParticipated(participated);
				if (list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						participatedService.delete(list.get(j));
					}

				}
				bookedService.deleteBKed(listb.get(i));

			}
		}
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

	@RequestMapping(value = "expExcel")
	public void expExcel(HttpServletRequest request, HttpServletResponse response) {
		// 导出到桌面
		// FileSystemView fsv = FileSystemView.getFileSystemView();
		// String desktop = fsv.getHomeDirectory().getPath();
		// String filePath = desktop + "/template.xls";
		// File file = new File(filePath);
		// File file=new File("");
		// OutputStream output = null;
		// try {
		// output = new FileOutputStream(file);
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = sheet.createRow(sheet.getLastRowNum());
		row.createCell(0).setCellValue("num");
		row.createCell(1).setCellValue("userid");
		row.createCell(2).setCellValue("roomid");
		row.createCell(3).setCellValue("timeto");
		row.createCell(4).setCellValue("number of members");
		List<Booked> bookeds = bookedService.getAllBKed();
		List<Log> logs = logService.getAllLog();
		if (bookeds.size() == 0 && logs.size() == 0)
			return;
		int num = 1;
		for (int i = 0; i < bookeds.size(); i++) {// 写入未完成的预订
			HSSFRow r = sheet.createRow(sheet.getLastRowNum() + 1);
			r.createCell(0).setCellValue(num);
			r.createCell(1).setCellValue(bookeds.get(i).getUser().getName());
			r.createCell(2).setCellValue(bookeds.get(i).getMeetingroom().getRoomname());
			r.createCell(3).setCellValue(bookeds.get(i).getTimeto());
			r.createCell(4).setCellValue(bookeds.get(i).getNumofparticipant());
			num++;
		}
		for (int i = 0; i < logs.size(); i++) {// 写入已完成的预订
			HSSFRow r = sheet.createRow(sheet.getLastRowNum() + 1);
			r.createCell(0).setCellValue(num);
			r.createCell(1).setCellValue(logs.get(i).getBooked().getUser().getUserid());
			r.createCell(2).setCellValue(logs.get(i).getBooked().getMeetingroom().getId());
			r.createCell(3).setCellValue(logs.get(i).getTimeto());
			r.createCell(4).setCellValue(logs.get(i).getBooked().getNumofparticipant());
			num++;
		}
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=template.xls");
			// response.setHeader("Content-Disposition", "attachment; filename=\"" +
			// "template.xls" + "\"");// 这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			System.out.println("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @RequestMapping(value = "adminusers.do") public String adminusers() {
	 * 
	 * }
	 */

	@RequestMapping(value="logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("roomid");
		return "redirect:login";
	}
	
	
}
