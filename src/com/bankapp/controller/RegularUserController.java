package com.bankapp.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.Transaction;
import com.bankapp.model.Transfer;
import com.bankapp.model.Useraccounts;
import com.bankapp.services.TransactionService;
import com.bankapp.services.UserService;
import com.bankapp.userexceptions.MinimumBalanceException;
import com.bankapp.userexceptions.NegativeAmountException;

@Controller
public class RegularUserController {

	@Autowired
	TransactionService transactionService;
	@Autowired
	UserService userService;
	
	private static final Logger logger = Logger.getLogger(RegularUserController.class);
	
	@RequestMapping("/extHome")
	public String externalCustomer(Model model,HttpSession session, HttpServletRequest request)
	{
		return "extHome";
	}
	
	@RequestMapping(value="/Credit")
	public String creditPage(ModelMap model) {
		model.addAttribute("credit", new Transaction());
		logger.info("Inside User Credit");
		return "credit";
	}
	
	@RequestMapping(value="/Debit")
	public String debitPage(ModelMap model) {
		model.addAttribute("debit", new Transaction());
		logger.info("Inside User Debit");
		return "debit";
	}
	
	@RequestMapping(value="/Transfer")
	public String transferPage(ModelMap model) {
		model.addAttribute("transferAmt", new Transfer());
		return "transfer";
	}
	
	@Transactional
	@RequestMapping(value="/initiateDebit",method=RequestMethod.POST)
	public ModelAndView submitFormDebit(ModelMap model, @ModelAttribute("debit") Transaction transaction, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("debit", new Transaction());
		modelAndView.setViewName("debit");
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Inside User Debit-initiated by: "+userName);
		Useraccounts userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		try {
			if(transaction.getAmount()<0){
				throw new NegativeAmountException("Amount cannot be a negative Value!!!");
			}
			if((userAccounts.getBalance()-transaction.getAmount()<500)){
				throw new MinimumBalanceException("Debit Cannot be performed as the Amount after debit is lesser than the minimum balance of $500 !!!");
			}
		Transaction trans = new Transaction();
		trans.setAmount(transaction.getAmount());
		trans.setType("D");
		if(transaction.getAmount()>=10000) {
			trans.setIsCritical("H");
		}
		else if (transaction.getAmount()<10000 && transaction.getAmount()>=1000){
			trans.setIsCritical("L");
		}
		else {
			trans.setIsCritical("N");
		}
		trans.setDateInitiated(new Date());
		trans.setAccountId(userAccounts.getAccountno());
		trans.setTransactionID(UUID.randomUUID().toString());
		Boolean res= transactionService.insertNewTransaction(trans, userAccounts);
		if(res.equals(true)&&!trans.getIsCritical().equals("N")) {
			modelAndView.addObject("msg","Debit Transaction has been submit the bank..The amount will be debited from your account once the Transaction is Approved!!!");
		}
		else if(res.equals(true)&&trans.getIsCritical().equals("N")) {
			userAccounts.setBalance(userAccounts.getBalance()-transaction.getAmount());
			Boolean val=transactionService.updateBalance(userAccounts);
			if(val)
				modelAndView.addObject("msg", "Debit was Successful..New Account Balance is "+userAccounts.getBalance());
			else {
				modelAndView.addObject("msg","Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
				transactionService.deleteTransaction(transaction);
			}
		}
		else {
			modelAndView.addObject("msg","Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
		}
		}
		catch(NegativeAmountException ne) {
			modelAndView.addObject("msg",ne.getMessage());
			return modelAndView;
		}
		catch(MinimumBalanceException mi) {
			modelAndView.addObject("msg",mi.getMessage());
			return modelAndView;
		}
		catch(Exception e) {
			modelAndView.addObject("msg","Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			return modelAndView;
		}
		return modelAndView;
	}
	
	@Transactional
	@RequestMapping(value="/initiateCredit",method=RequestMethod.POST)
	public ModelAndView submitFormCredit(ModelMap model, @ModelAttribute("credit") Transaction transaction, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("credit", new Transaction());
		modelAndView.setViewName("credit");
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		try {
			Useraccounts userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
			if(transaction.getAmount()<0){
				throw new NegativeAmountException("Amount cannot be a negative Value!!!");
			}
		Transaction trans = new Transaction();
		trans.setAmount(transaction.getAmount());
		trans.setType("C");
		if(transaction.getAmount()>=10000) {
			trans.setIsCritical("H");
		}
		else if (transaction.getAmount()<10000 && transaction.getAmount()>=1000){
			trans.setIsCritical("L");
		}
		else {
			trans.setIsCritical("N");
		}
		trans.setDateInitiated(new Date());
		trans.setAccountId(userAccounts.getAccountno());
		trans.setTransactionID(UUID.randomUUID().toString());
		Boolean res= transactionService.insertNewTransaction(trans, userAccounts);
		
		if(res.equals(true)&&trans.getIsCritical()!="N") {
			modelAndView.addObject("msg","Credit Transaction has been submit the bank..The amount will be credited from your account once the Transaction is Approved!!!");
		}
		else if(res.equals(true)&&trans.getIsCritical()=="N") {

			userAccounts.setBalance(userAccounts.getBalance()+transaction.getAmount());
			Boolean val=transactionService.updateBalance(userAccounts);
			if(val) {
				modelAndView.addObject("msg", "Credit was Successful..New Account Balance is "+userAccounts.getBalance());
			}
			else {
				modelAndView.addObject("msg","Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
				transactionService.deleteTransaction(transaction);
			}
		}
		else {
			modelAndView.addObject("msg","Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
		}
		}
		catch(NegativeAmountException ne) {
			modelAndView.addObject("msg",ne.getMessage());
			return modelAndView;
		}
		catch(Exception e) {
			modelAndView.addObject("msg","Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			return modelAndView;
		}
		return modelAndView;
	}
	
	@Transactional
	@RequestMapping(value="/initiateTransfer",method=RequestMethod.POST)
	public ModelAndView submitFormTransfer(ModelMap model, @ModelAttribute("transferAmt") Transfer transfer, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("transferAmt", new Transfer());
		modelAndView.setViewName("transfer");
		Long accno = transfer.getToAccountNo();
		boolean accStatus = userService.checkAccountExists(accno);
		if (accStatus){
			return modelAndView;
		}
		else {
			modelAndView.addObject("errorMessage", "Account Does not exist Chal Chutiye!!!");
			return modelAndView;
		}
		
	}
}
