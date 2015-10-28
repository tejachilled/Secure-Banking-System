package com.bankapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.bankapp.model.UserInfo;
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
