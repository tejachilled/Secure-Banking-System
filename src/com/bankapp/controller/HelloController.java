package com.bankapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.UserModel;
import com.bankapp.services.UserService;

@Controller
public class HelloController {

	@Autowired
	 UserService userService;
	
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		System.out.println("coming");
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is welcome page!");
		model.setViewName("hello");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}


	 @RequestMapping("/register")
	 public ModelAndView registerUser(@ModelAttribute("user") UserModel user) {
	  return new ModelAndView("register");
	 }

	 @RequestMapping("/insert")
	 public String inserData(@ModelAttribute("user") UserModel user) {
	  if (user != null)
	   userService.insertData(user);
	  return "redirect:/getList";
	 }
	 
	 @RequestMapping("/getList")
	 public ModelAndView getUserLIst() {
	  List<UserModel> userList = userService.getUserList();
	  return new ModelAndView("userList", "userList", userList);
	 }

}
