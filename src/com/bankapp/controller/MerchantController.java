package com.bankapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.Transaction;
import com.bankapp.services.TransactionServiceImpl;


@Controller
public class MerchantController {

	@Autowired
	TransactionServiceImpl transactionService;
	
	@RequestMapping("/MerchHome")
	public String externalCustomer(Model model,HttpSession session, HttpServletRequest request)
	{
		return "extHome";
	}
		
	@RequestMapping(value="/merchantTransaction",method=RequestMethod.GET)
	public String merchantTransaction(ModelMap model){
		return "merchantTransaction";
	}
	
	@RequestMapping(value="/transactionHistoryM",method=RequestMethod.GET)
	public ModelAndView transactionHistoryM(){
		ModelAndView model = new ModelAndView();
		//TODO:- hard-coded as of now
		Integer accountId= 654321;
		Transaction transaction= new Transaction();
		transaction.setAccountId(accountId);
		transaction.setAmount(Double.valueOf(10000));
		transaction.setRemark("debit for Ipad purchase");
		transaction.setType("d");
		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);
		/*
		if(accountId==null){
			System.out.println("account is null. requires logger");
		} else{
			listTransaction= transactionService.getTransactionHistory(accountId.toString());
		}*/
		model.addObject("TransactionList", listTransaction);
		model.setViewName("transactionHistoryM");
		return model;
	}
	
}
