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
	
	@Transactional
	@RequestMapping(value="/addExtUser",method=RequestMethod.POST)
	public String submitForm(ModelMap model, @ModelAttribute ("extUser") @Validated UserInfo userInfo, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		String role=request.getParameter("role").toString();
		userValidator.validate(userInfo, result);

		if(result.hasErrors())
		{
			System.out.println("error");
			return "addExternalUserAccount";
		}
		System.out.println(userInfo.getFirstName());
		String decodedPwd = emailService.generatePassword();
		userInfo.setPassword(encoder.encode(decodedPwd));
	//	userInfo.setEnable(false);

		int accno=userService.addNewExternalUuser(userInfo,role);

		UUID uniqueToken =UUID.randomUUID();
	//	pkiService.generateKeyPairAndToken(userInfo.getUserName(),uniqueToken.toString());
		emailService.sendEmailWithAttachment(userInfo.getEmaiID(),userInfo.getUserName(),decodedPwd,uniqueToken.toString());
		model.addAttribute("accno", accno);
		return "addExternalUserAccount";

	}


	@RequestMapping(value="/atFirstLogin")
	public String userAtFirstLogin(Model model){
		return "changePassword";
	}
}