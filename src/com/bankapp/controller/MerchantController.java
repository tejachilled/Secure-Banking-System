package com.bankapp.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.services.UserService;

@Controller
public class MerchantController {

	@RequestMapping(value="/merchantTransaction",method=RequestMethod.GET)
	public String merchantTransaction(ModelMap model){
		return "merchantTransaction";
	}
	
	@RequestMapping(value="/transactionHistoryM",method=RequestMethod.GET)
	public String transactionHistoryM(ModelMap model){
		return "transactionHistoryM";
	}
	
}
