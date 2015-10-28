
package com.bankapp.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.UserInfo;
import com.bankapp.services.UserService;
import com.bankapp.services.UserValidator;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	UserValidator userValidator;

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

	@RequestMapping(value="/403",method=RequestMethod.GET)
	public String accessDenied(ModelMap model,Principal user) {

		if (user != null) {
			model.addAttribute("username",user.getName());
		} 

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
	@RequestMapping(value="/atFirstLogin")
	public String userAtFirstLogin(Model model,HttpServletRequest request){
		return "changePassword";
	}
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ModelAndView changePassword(HttpServletRequest request,HttpSession session) {
		ModelAndView model = null;
		try
		{
			String password = (String)request.getParameter("newPassword");
			String confirmPassword = (String)request.getParameter("confirmPassword");
			System.out.println("pass="+password);
			System.out.println("conf pass="+confirmPassword);
			if(!password.equals(confirmPassword))
				throw new Exception("Passwords do not match!");
			else if(!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,10}$")){
				throw new Exception("Invalid password, must be 4-10 characters and contain one capital letter and a number. Special characters are not allowed");
			}
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			model= new ModelAndView("login");
			if(userName!=null || request.getSession().getAttribute("userName")!=null){	
				if(request.getSession().getAttribute("userName")!=null){
					userName = request.getSession().getAttribute("userName").toString();
				}
				System.out.println("in changepassword method un : "+userName);
				userService.changePassword(confirmPassword, userName);
				SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
				model.addObject("success","Successfully updated");
				
			}
			return model;
		}
		catch(Exception e)
		{
			model = new ModelAndView("changePassword");
			model.addObject("errorMessage", e.getMessage());
			return model;

		}

	}
	@RequestMapping(value="/forgotpassword",method=RequestMethod.GET)
	public String forgotPasswordClicked(Model model)
	{
		model.addAttribute("extUser", new UserInfo());
		System.out.println("in forgot password");
		return "forgotPassword";
	}

	@RequestMapping(value="/forgotpassword",method=RequestMethod.POST)
	public String forgotPasswordNextClicked(ModelMap model,@ModelAttribute ("extUser") @Validated UserInfo UserInfo, BindingResult result,HttpSession session)
	{
		System.out.println("in forgot password Post"+" "+UserInfo);
		if(!UserInfo.getUserName().matches("^[a-z0-9_-]{3,16}$"))
		{
			model.addAttribute("error", "Username is not valid!");
			model.addAttribute("extUser", new UserInfo());
			System.out.println("error");
			return "forgotPassword";
		}
		UserInfo user = userService.getUserInfobyUserName(UserInfo.getUserName());
		if(user!=null)
		{			
			model.addAttribute("username", UserInfo.getUserName());

			if(UserInfo.getEmaiID()!=null){
				if(!(UserInfo.getSq3().equalsIgnoreCase(user.getSq3())  && UserInfo.getSq2().equalsIgnoreCase(user.getSq2())  && UserInfo.getSq1().equalsIgnoreCase(user.getSq1())  && UserInfo.getEmaiID().equalsIgnoreCase(user.getEmaiID()))){
					model.addAttribute("error", "Provided information didnot match!");
					return "forgotPassword";
				}
				session.setAttribute("userName", UserInfo.getUserName());
				return "changePassword";
			}
		}
		else
		{
			model.addAttribute("error", "Username is not valid!");
		}

		return "forgotPassword";
	}

}