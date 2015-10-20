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
	
	@RequestMapping(value="/extHome")
	public String exthomePage(ModelMap model)
	{
		return "extHome";
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
		String ur = userService.getUserRoleType(username);
		if(ur.equals("ROLE_SM")||ur.equals("ROLE_RE"))
		   return "internalHome";
		else 
			return "login";
	}
	
	@RequestMapping(value="/forgotpassword",method=RequestMethod.GET)
	public String forgotPasswordClicked(Model model)
	{
		System.out.println("in forgot password");
		return "forgotPassword";
	}

	@RequestMapping(value="/forgotpassword",method=RequestMethod.POST)
	public ModelAndView forgotPasswordNextClicked(@RequestParam("usernamegiven") String usernamegiven)
	{
		System.out.println("in forgot password Post"+" "+usernamegiven);
		ModelAndView model = null;
//
//		if(check if present in db - usernamegiven))
//		{
//		//	otpService.generateOTP(usernamegiven);
//			ModelAndView model1 = new ModelAndView("forgotPassword");
//			model1.addObject("type", "forgotpassword");
//			model1.addObject("username", usernamegiven);
//			model1.setViewName("otpVerify");
//		}
//		else
//		{
//			model.addObject("errorMessage", "Username is not valid!");
//		}

		return model;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/merchHome")
	public String mercHomePage(ModelMap model)
	{
		return "merchHome";
	}
}
