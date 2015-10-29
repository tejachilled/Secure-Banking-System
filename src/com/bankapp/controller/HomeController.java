package com.bankapp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bankapp.services.UserService;

@Controller
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	UserService userService;

	@RequestMapping(value = "/intHome")
	public String homePage(ModelMap model) {
		logger.info("Intenal Employee Logged in.");
		return "internalHome";
	}

	@RequestMapping(value = "/adminHome")
	public String adminHomePage(ModelMap model) {
		logger.info("System Admin Logged in.");
		return "adminHome";
	}

}
