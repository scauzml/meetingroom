package com.meetingroom.util;

import java.util.ArrayList;
import java.util.List;

public class Time {

	private static List<String> timeList=new ArrayList<>();
	static {
		timeList.add("9:00-10:00");
		timeList.add("10:00-11:00");
		timeList.add("11:00-12:00");
		timeList.add("12:00-13:00");
		timeList.add("13:00-14:00");
		timeList.add("14:00-15:00");
		timeList.add("15:00-16:00");
		timeList.add("16:00-17:00");
		
	}
	public static List<String> getTimeList() {
		return timeList;
	}
	
}
