package com.bankapp.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.services.UserService;

@Controller
public class LoginController {

	@Autowired
	 UserService userService;
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@RequestMapping(value = { "/welcome"}, method = RequestMethod.GET)
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
	@RequestMapping(value="/403",method=RequestMethod.GET)
	 public String accessDenied(ModelMap model) {
	  return "403";
	 }

	@RequestMapping(value={ "/","/login"},method=RequestMethod.GET)
	public String login(ModelMap model)
	{
		System.out.println("in login controller");
		logger.info("In Login Controller");
		return "login";
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(ModelMap model)
	{
		System.out.println("logout!!");
		 SecurityContextHolder.getContext().setAuthentication(null);
		return "login";
	}
	@RequestMapping(value="/loginFailed", method=RequestMethod.GET)
	public String loginFailed(ModelMap model, Principal user)
	{
		System.out.println("login failed");
		model.addAttribute("error", "Invalid credentials");
		return "login";
	}


	
}
