package com.bankapp.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.bankapp.model.UserInfo;
import com.bankapp.services.EmailService;
import com.bankapp.services.UserService;
import com.bankapp.services.UserValidator;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired 
	PasswordEncoder encoder;
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	EmailService emailService;
	

	@RequestMapping(value="/atFirstLogin")
	public String userAtFirstLogin(Model model){
		return "changePassword";
	}
	
	
	@Transactional
	@RequestMapping(value="/addExtUser",method=RequestMethod.POST)
	public String submitForm(ModelMap model, @ModelAttribute ("extUser") @Validated UserInfo UserInfo, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		String role=request.getParameter("role").toString();
		userValidator.validate(UserInfo, result);

		if(result.hasErrors())
		{
			System.out.println("error");
			return "addExternalUserAccount";
		}
		System.out.println(UserInfo.getFirstName());
		String decodedPwd = emailService.generatePassword();
		UserInfo.setPassword(encoder.encode(decodedPwd));

		int accno=userService.addNewExternalUuser(UserInfo,role);

		UUID uniqueToken =UUID.randomUUID();
	//	pkiService.generateKeyPairAndToken(UserInfo.getUserName(),uniqueToken.toString());
		emailService.sendEmailWithAttachment(UserInfo.getEmaiID(),UserInfo.getUserName(),decodedPwd,uniqueToken.toString());
		model.addAttribute("accno", accno);
		return "addExternalUserAccount";
	}
	
	
	@Transactional
	@RequestMapping(value="/addInternalUserAccount",method=RequestMethod.POST)
	public String addInternalUser(ModelMap model, @ModelAttribute ("extUser") @Validated UserInfo UserInfo, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		String role=request.getParameter("role").toString();
		userValidator.validate(UserInfo, result);

		if(result.hasErrors())
		{
			System.out.println("error");
			return "addInternalUserAccount";
		}
		System.out.println(UserInfo.getFirstName());
		String decodedPwd = emailService.generatePassword();
		UserInfo.setPassword(encoder.encode(decodedPwd));
	//	UserInfo.setEnable(false);


		UUID uniqueToken =UUID.randomUUID();
	//	pkiService.generateKeyPairAndToken(UserInfo.getUserName(),uniqueToken.toString());
		emailService.sendEmailWithAttachment(UserInfo.getEmaiID(),UserInfo.getUserName(),decodedPwd,uniqueToken.toString());
		
		return "addInternalUserAccount";

	}

	// Manager functionalities
	@RequestMapping(value="/ViewEmpProfile",method=RequestMethod.GET)
	public String viewEmpProfile(Model model)
	{
		model.addAttribute("accessInfo", new UserInfo());
		return "viewEmpProfile";
	}
	
	@RequestMapping(value="/ViewEmpProfile",method=RequestMethod.POST)
	public String viewEmpProfile(@ModelAttribute ("accessInfo") @Validated UserInfo userInfo, BindingResult result, SessionStatus status,Model model)
	{
		//add objects to model
		model.addAttribute("accessInfo", userInfo);
		model.addAttribute("usernameerror",null);
		//validate input format
		if(userInfo.getUserName()!=null)
		{
			if(!(userInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$"))
			{
				model.addAttribute("usernameerror","Please enter a valid username");
				return "viewEmpProfile";
			}
			else
			{
				//validate if reasonable request and username exists
				if(userService.getUserInfobyUserName(userInfo.getUserName())==null)
				{
					model.addAttribute("usernameerror","Specified username does not exist");
					return "viewEmpProfile";
				}
				else
				{
					UserInfo ui = userService.getUserInfobyUserName(userInfo.getUserName());
					//check if the user is an external user
					String ur = userService.getUserRoleType(ui.getUserName());
					if(ur.equals("ROLE_EMPLOYEE"))
					{
						model.addAttribute("accessInfo", ui);
						return "viewEmpProfile";
					}
					else
					{
						model.addAttribute("usernameerror", "Not a valid employee");
						return "viewEmpProfile";
					}
				}
			}
		}
		else
		{
			model.addAttribute("usernameerror","Please enter the username");
			return "viewEmpProfile";
		}
	}
	
	//Edit Employees
		@RequestMapping(value="/EditEmpProfile",method=RequestMethod.GET)
		public String editEmpProfile(Model model)
		{
			model.addAttribute("accessInfo", new UserInfo());
			return "editEmpProfile";
		}

		@RequestMapping(value="/EditEmpProfile",method=RequestMethod.POST)
		public String editEmpProfile(@ModelAttribute ("accessInfo") @Validated UserInfo UserInfo, BindingResult result, SessionStatus status,Model model)
		{
			//add objects to model
			model.addAttribute("accessInfo", UserInfo);
			model.addAttribute("usernameerror",null);
			model.addAttribute("addresserror",null);
			//validate input format
			if(UserInfo.getUserName()!=null)
			{
				if(UserInfo.getEmaiID()!=null)
				{
					UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName()); 
					if(ui.getAddress1()!=null){
						if(!(ui.getAddress1()).matches("^[a-zA-Z0-9_#]*$"))
						{
							model.addAttribute("addresserror","Please enter a valid address having characters numbers and #");
							return "editEmpProfile";
						}
					}
					if(UserInfo.getAddress1() != ui.getAddress1())
					{
						ui.setAddress1(UserInfo.getAddress1());
					}
					if(UserInfo.getAddress2() != ui.getAddress1())
					{
						ui.setAddress2(UserInfo.getAddress2());
					}
					userService.updateUserInfo(ui);
					model.addAttribute("accessInfo", userService.getUserInfobyUserName(UserInfo.getUserName()));
					return "editEmpProfile";
				}
				if(!(UserInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$"))
				{
					model.addAttribute("usernameerror","Please enter a valid username");
					return "editEmpProfile";
				}
				else
				{
					//validate if reasonable request and username exists
					if(userService.getUserInfobyUserName(UserInfo.getUserName())==null)
					{
						model.addAttribute("usernameerror","Specified username does not exist");
						return "editEmpProfile";
					}
					else
					{
						UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
						//check if the user is an external user
						String ur = userService.getUserRoleType(ui.getUserName());
						if(ur.equals("ROLE_EMPLOYEE"))
						{
							model.addAttribute("accessInfo", ui);
							return "editEmpProfile";
						}
						else
						{
							model.addAttribute("usernameerror", "Not a valid employee");
							return "editEmpProfile";
						}
					}
				}
			}
			else
			{
				model.addAttribute("usernameerror","Please enter the username");
				return "viewEmpProfile";
			}
		}

		//Delete Employees
		@RequestMapping(value="/DeleteEmpProfile",method=RequestMethod.GET)
		public String deleteEmpProfile(Model model)
		{
			model.addAttribute("accessInfo", new UserInfo());
			return "deleteEmpProfile";
		}

		@RequestMapping(value="/DeleteEmpProfile",method=RequestMethod.POST)
		public String deleteEmpProfile(@ModelAttribute ("accessInfo") @Validated UserInfo UserInfo, BindingResult result, SessionStatus status,Model model)
		{
			//add objects to model
			model.addAttribute("accessInfo", UserInfo);
			model.addAttribute("usernameerror",null);
			model.addAttribute("deleteMessage",null);
			//validate input format
			if(UserInfo.getUserName()!=null)
			{
				if(!(UserInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$"))
				{
					model.addAttribute("usernameerror","Please enter a valid username");
					return "deleteEmpProfile";
				}
				else
				{
					//validate if reasonable request and username exists
					if(userService.getUserInfobyUserName(UserInfo.getUserName())==null)
					{
						model.addAttribute("usernameerror","Specified username does not exist");
						return "deleteEmpProfile";
					}
					else
					{
						UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
						if(!(UserInfo.getEmaiID()==null))
							{
							   userService.deleteUserInfo(ui);
							   model.addAttribute("deleteMessage","Delete Successfull!");
							   model.addAttribute("accessInfo", new UserInfo());
							   return "deleteEmpProfile";
							}
						//check if the user is an external user
						String ur = userService.getUserRoleType(ui.getUserName());
						if(ur.equals("ROLE_EMPLOYEE"))
						{
							model.addAttribute("accessInfo", ui);
							return "deleteEmpProfile";
						}
						else
						{
							model.addAttribute("usernameerror", "Not a valid employee");
							return "deleteEmpProfile";
						}
					}
				}
			}
			else
			{
				model.addAttribute("usernameerror","Please enter the username");
				return "deleteEmpProfile";
			}


		}
	// Admin functionalities
	@RequestMapping(value="/ViewInternalEmpProfile",method=RequestMethod.GET)
	public String viewInternalEmpProfile(Model model)
	{
		model.addAttribute("accessInfo", new UserInfo());
		return "viewInternalEmpProfile";
	}
	
	
	
}