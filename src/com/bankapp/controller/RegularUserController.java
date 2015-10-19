package com.bankapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegularUserController {

	
	@RequestMapping("/ExtHome")
	public String externalCustomer(Model model,HttpSession session, HttpServletRequest request)
	{
		return "extHome";
	}
}
