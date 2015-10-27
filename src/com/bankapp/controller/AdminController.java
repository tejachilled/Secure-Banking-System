package com.bankapp.controller;

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
	
	@RequestMapping(value = "/ViewInternalEmpProfile", method = {RequestMethod.POST}, params = "Delete")
	public String deleteEmpProfile1(@ModelAttribute ("accessInfo") @Validated UserInfo userInfo,HttpServletRequest request, BindingResult result, SessionStatus status,Model model){
		String role=request.getParameter("role").toString();
		System.out.println("in dele method with user role : "+role);
		if(role!=null){
			userInfo.setRole(role);
			userService.deleteUserInfo(userInfo);
			model.addAttribute("success", "Successfully deleted!");
		}
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
					//System.out.println("Admin controller user role : "+ui.getRole());
					if(ui.getRole().equalsIgnoreCase("ROLE_RE") || ui.getRole().equalsIgnoreCase("ROLE_SM"))
					{
						ui.setRole(userService.setRoleToDisplayUI(ui.getRole()));
						model.addAttribute("accessInfo", ui);
						return "viewInternalEmpProfile";
					}
					else
					{
						model.addAttribute("usernameerror", "Not a valid User");
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
					UserInfo.setRole(userService.setRoleToDisplayUI(UserInfo.getRole()));
					model.addAttribute("accessInfo", UserInfo);
					model.addAttribute("success", "updated Successfully!");
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
							ui.setRole(userService.setRoleToDisplayUI(ui.getRole()));
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
		
		
}
