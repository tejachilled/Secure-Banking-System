package com.bankapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.UserInfo;
import com.bankapp.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value="/intHome")
	public String homePage(ModelMap model)
	{
		return "internalHome";
	}


	 @RequestMapping("/insert")
	 public String inserData(@ModelAttribute("user") UserInfo user) {
	  if (user != null) 
	   userService.insertData(user);
	  return "redirect:/getList";
	 }
	 
	 @RequestMapping("/getList")
	 public ModelAndView getUserLIst() {
	  List<UserInfo> userList = userService.getUserList();
	  return new ModelAndView("userList", "userList", userList);
	 }
	 
	 @RequestMapping("/governmentUser")
	 public ModelAndView getGovernmentRequests() {
	  List<UserInfo> userList = userService.getUserList();
	  return new ModelAndView("userList", "userList", userList);
	 } 

	@RequestMapping(value="/register")
	public String registerAUser(ModelMap model)
	{
		model.addAttribute("extUser", new UserInfo());
		return "addExternalUserAccount";
	}
	
	@RequestMapping(value="/home")
	public String home(ModelMap model)
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String ur = "";//userService.getUserRoleType(username);
		if(ur.equals("ROLE_ADMIN")||ur.equals("ROLE_EMPLOYEE"))
		   return "internalHome";
		else 
			return "login";
	}
}
