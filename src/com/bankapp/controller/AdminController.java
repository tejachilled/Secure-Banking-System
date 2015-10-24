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

		UserInfo.setPassword(encoder.encode(UserInfo.getPassword()));
		//	UserInfo.setEnable(false);


		UUID uniqueToken =UUID.randomUUID();

		return "addInternalUserAccount";

	}
}
