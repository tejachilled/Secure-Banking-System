
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
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bankapp.model.UserInfo;
import com.bankapp.services.UserService;
import com.bankapp.services.UserValidator;
import com.bankapp.userexceptions.ResourceNotFoundException;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	UserValidator userValidator;

	private static final Logger logger = Logger.getLogger(LoginController.class);

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(ModelMap model, Principal user) {

		if (user != null) {
			model.addAttribute("username", user.getName());
		}

		return "403";
	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(ModelMap model) {
		SecurityContextHolder.getContext().setAuthentication(null);
		logger.info("In Login Controller");
		return "login";
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(ModelMap model)
	{
		SecurityContextHolder.getContext().setAuthentication(null);
		logger.info("Logged out");
		return "login";
	}
	@RequestMapping(value="/loginFailed", method=RequestMethod.GET)
	public String loginFailed(ModelMap model, Principal user){
		SecurityContextHolder.getContext().setAuthentication(null);
		logger.info("Invalid credentials or Captcha");
		model.addAttribute("error", "Invalid Credentials/Captcha");
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
	
	@ExceptionHandler(HttpSessionRequiredException.class)
	@ResponseBody
	public String handleSessionExpired(ModelMap model) {
		model.addAttribute("error", "Session expired");
		return "login";
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public String handleException1(NullPointerException ex, Model model) {
		System.out.println("Handle exception");
		model.addAttribute("error", "There was an error");
		return "login";
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public String handle1(Exception ex,Model model) {
		System.out.println("handle1");
		model.addAttribute("error", "No page found");

		return "login";
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handle2() {
		System.out.println("handle2");
		return "login";
	}

}