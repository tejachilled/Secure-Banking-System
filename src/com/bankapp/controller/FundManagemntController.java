package com.bankapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.Transaction;
import com.bankapp.services.FundManagementService;


@Controller
public class FundManagemntController {
	
	@Autowired 
	private FundManagementService fundManagementService ;
	
	
	@RequestMapping(value="/Transfer",method=RequestMethod.GET)
	public ModelAndView transfer()
	{
		Transaction transactions = new Transaction();
		ModelAndView model = new ModelAndView("Transfer");
		model.addObject("transactions", transactions);
		System.out.println("in transfer controller");	
		return model;
	}
	@RequestMapping(value="/transfer", method=RequestMethod.POST)
	public ModelAndView createTransfer(@ModelAttribute("transactions") Transaction transactions, BindingResult result, ModelMap map)
	{
		ModelAndView model;
		try {
			fundManagementService.createTransferTransaction(transactions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			model = new ModelAndView("Transfer");
			e.printStackTrace();
			model.addObject("errorMessage", e.getMessage());
			return model;
		}
		
		model = new ModelAndView("hello");
		return model;
		
	}
}
