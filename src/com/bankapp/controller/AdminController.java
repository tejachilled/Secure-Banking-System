package com.bankapp.controller;

import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.bankapp.model.UserInfo;
import com.bankapp.services.UserService;
import com.bankapp.services.UserValidator;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.UserAccountExist;
import com.bankapp.userexceptions.UserNameExists;

@Controller
public class AdminController {
	@Autowired
	UserService userService;
 
	@Autowired 
	PasswordEncoder encoder;

	@Autowired
	UserValidator userValidator;


	// Admin functionalities
	@RequestMapping(value="/ViewInternalEmpProfile",method=RequestMethod.GET)
	public String viewInternalEmpProfile(Model model)
	{
		model.addAttribute("accessInfo", new UserInfo());
		return "viewInternalEmpProfile";
	}
	
	@RequestMapping(value="/ViewInternalEmpProfile",method=RequestMethod.POST)
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
				return "viewInternalEmpProfile";
			}
			else
			{
				UserInfo ui = userService.getUserInfobyUserName(userInfo.getUserName());
				//validate if reasonable request and username exists
				if(ui==null)
				{
					model.addAttribute("usernameerror","Specified username does not exist");
					return "viewInternalEmpProfile";
				}
				else
				{
					if(ui.getUserName().equals("ROLE_RE") || ui.getUserName().equals("ROLE_SM"))
					{
						model.addAttribute("accessInfo", ui);
						return "viewInternalEmpProfile";
					}
					else
					{
						model.addAttribute("usernameerror", "Not a valid customer");
						return "viewInternalEmpProfile";
					}
				}
			}
		}
		else
		{
			model.addAttribute("usernameerror","Please enter the username");
			return "viewInternalEmpProfile";
		}
	}
	@Transactional
	@RequestMapping(value="/addInternalUser")
	public String registerAInternalUser(ModelMap model)
	{
		model.addAttribute("intUser", new UserInfo());
		return "addInternalUser";
	}

	@Transactional
	@RequestMapping(value="/addInternalUser",method=RequestMethod.POST)
	public String addInternalUser(ModelMap model, @ModelAttribute ("intUser") @Validated UserInfo UserInfo, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		String role=request.getParameter("role").toString();
		userValidator.validate(UserInfo, result);

		if(result.hasErrors())
		{
			System.out.println("error");
			return "addInternalUser";
		}
		System.out.println(UserInfo.getFirstName());

		UserInfo.setPassword(encoder.encode(UserInfo.getPassword()));
		try{
			userService.addNewInternaluser(UserInfo,role);
			model.addAttribute("success", "Added new user successfully!");
		}catch(CustomException exception){
			model.addAttribute("exception", exception.getMessage());
		} catch (UserNameExists exception) {
			model.addAttribute("exception", exception.getMessage());
		}
		return "addInternalUser";

	}
	
	//Edit Employees
		@RequestMapping(value="/EditInternalEmpProfile",method=RequestMethod.GET)
		public String editEmpProfile(Model model)
		{
			model.addAttribute("accessInfo", new UserInfo());
			return "editInternalEmpProfile";
		}

		@RequestMapping(value="/EditInternalEmpProfile",method=RequestMethod.POST)
		public String editEmpProfile(@ModelAttribute ("accessInfo") @Validated UserInfo UserInfo, BindingResult result, SessionStatus status,Model model)
		{
			//add objects to model
			model.addAttribute("accessInfo", UserInfo);
			System.out.println("editInternalEmpProfile: "+UserInfo.toString());
			model.addAttribute("usernameerror",null);
			model.addAttribute("addresserror",null);
			model.addAttribute("phoneNumber",null);
			model.addAttribute("emailid",null);

			//validate input format
			if(UserInfo.getUserName()!=null)
			{
				if(UserInfo.getFirstName()!=null)
				{
					if(UserInfo.getAddress1()==null || UserInfo.getAddress1().length() ==0 ){
						model.addAttribute("addresserror","Please enter a valid address having characters numbers and #");
						return "editInternalEmpProfile";
					}
					if(UserInfo.getEmaiID()==null ){
						model.addAttribute("emailid","Please enter a valid email id");
						return "editInternalEmpProfile";
					} 
					String phoneNumber = ""+UserInfo.getPhoneNumber();
					if(UserInfo.getPhoneNumber()== null || !phoneNumber.matches("\\d{10}")){
						model.addAttribute("phoneNumber","Please enter a valid 10 digit phone number");
						return "editInternalEmpProfile";
					}
					
					UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName()); 
					if(ui.getAddress1()!=null){
						if(!(ui.getAddress1()).matches("^[a-zA-Z0-9_#]*$"))
						{
							model.addAttribute("addresserror","Please enter a valid address having characters numbers and #");
							return "editInternalEmpProfile";
						}
					}
					if( UserInfo.getAddress1() != ui.getAddress1())
					{
						ui.setAddress1(UserInfo.getAddress1());
					} 
					if(UserInfo.getAddress2() != ui.getAddress1())
					{
						ui.setAddress2(UserInfo.getAddress2());
					}
					if(UserInfo.getEmaiID() != ui.getEmaiID())
					{
						ui.setEmaiID(UserInfo.getEmaiID());
					}
					if( UserInfo.getPhoneNumber()!= ui.getPhoneNumber())
					{
						ui.setPhoneNumber(UserInfo.getPhoneNumber());
					}
					System.out.println("editEmpProfile: updating info with "+ui.toString());
					userService.updateInternalUserInfo(ui);
					System.out.println("editInternalEmpProfile : updated user info ");
					model.addAttribute("success", "Updated details successfully");
					UserInfo = userService.getUserInfobyUserName(UserInfo.getUserName());
					UserInfo.setRole(userService.setRole(UserInfo.getRole()));
					model.addAttribute("accessInfo", UserInfo);
					return "editInternalEmpProfile";
				}
				if(!(UserInfo.getUserName()).matches("^[a-z0-9_-]{3,16}$"))
				{
					model.addAttribute("usernameerror","Please enter a valid username");
					return "editInternalEmpProfile";
				}
				else
				{
					//validate if reasonable request and username exists
					if(userService.getUserInfobyUserName(UserInfo.getUserName())==null)
					{
						model.addAttribute("usernameerror","Specified username does not exist");
						return "editInternalEmpProfile";
					}
					else
					{
						UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
						//check if the user is an Internal user
						if(ui.getRole().equals("ROLE_RE") || ui.getRole().equals("ROLE_SM"))
						{
							ui.setRole(userService.setRole(ui.getRole()));
							model.addAttribute("accessInfo", ui);
							return "editInternalEmpProfile";
						}
						else
						{
							model.addAttribute("usernameerror", "Not a valid user");
							return "editInternalEmpProfile";
						}
					}
				}
			}
			else
			{
				model.addAttribute("usernameerror","Please enter the username");
				return "viewInternalEmpProfile";
			}
		}
		
		//Delete Employees
		@RequestMapping(value="/DeleteInternalEmpProfile",method=RequestMethod.GET)
		public String deleteEmpProfile(Model model)
		{
			model.addAttribute("accessInfo", new UserInfo());
			return "deleteInternalEmpProfile";
		}

		@RequestMapping(value="/DeleteInternalEmpProfile",method=RequestMethod.POST)
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
					return "deleteInternalEmpProfile";
				}
				else
				{
					//validate if reasonable request and username exists
					if(userService.getUserInfobyUserName(UserInfo.getUserName())==null)
					{
						model.addAttribute("usernameerror","Specified username does not exist");
						return "deleteInternalEmpProfile";
					}
					else
					{
						UserInfo ui = userService.getUserInfobyUserName(UserInfo.getUserName());
						if(!(UserInfo.getEmaiID()==null))
						{
							userService.deleteUserInfo(ui);
							model.addAttribute("deleteMessage","Delete Successfull!");
							model.addAttribute("accessInfo", new UserInfo());
							return "deleteInternalEmpProfile";
						}
						//check if the user is an external user
						String ur = userService.getUserRoleType(ui.getUserName());
						if(ur.equals("ROLE_RE")|| ur.equals("ROLE_SM"))
						{
							model.addAttribute("accessInfo", ui);
							return "deleteInternalEmpProfile";
						}
						else
						{
							model.addAttribute("usernameerror", "Not a valid employee");
							return "deleteInternalEmpProfile";
						}
					}
				}
			}
			else
			{
				model.addAttribute("usernameerror","Please enter the username");
				return "deleteInternalEmpProfile";
			}
		}
}
