package com.bankapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.Transaction;
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

	@RequestMapping(value="/adminHome")
	public String adminHomePage(ModelMap model)
	{
		return "adminHome";
	}
	

	 @RequestMapping("/insert")
	 public String inserData(@ModelAttribute("user") UserInfo user) {
	  if (user != null) 
	   userService.insertData(user);
	  return "redirect:/getList";
	 }
	 
	 @RequestMapping("/getList")
	 public ModelAndView getUserLIst() {
	  List<UserInfo> userList = userService.getExternalUserList();
	  return new ModelAndView("userList", "userList", userList);
	 }
	 
	 @RequestMapping("/governmentUser")
	 public ModelAndView getGovernmentRequests() {
	  List<UserInfo> userList = userService.getExternalUserList();
	  return new ModelAndView("userList", "userList", userList);
	 }
	
	

}
