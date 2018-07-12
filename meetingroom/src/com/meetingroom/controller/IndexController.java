package com.meetingroom.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.jndi.url.dns.dnsURLContext;

@Controller
public class IndexController {
	
	@RequestMapping(value="isLogin")
	public String isLogin(HttpServletRequest request,HttpServletResponse response) {
		if(request.getSession().getAttribute("status").equals("login")) {
			StringBuilder sBuilder=new StringBuilder();
			sBuilder.append("login,");
			
			return sBuilder.toString();
		} else {
			return "anon";
		}
		
	}
}
