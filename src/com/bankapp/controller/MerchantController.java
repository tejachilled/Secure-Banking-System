package com.bankapp.controller;

import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.Transaction;
import com.bankapp.model.UserInfo;
import com.bankapp.services.MerchantServiceImpl;


@Controller
public class MerchantController {

	@Autowired
	MerchantServiceImpl merchantService;
	
	@RequestMapping("/MerchHome")
	public String externalCustomer(Model model,HttpSession session, HttpServletRequest request)
	{
		return "extHome";
	}
		
	@RequestMapping(value="/newMerchantRequest",method=RequestMethod.GET)
	public String merchantTransaction(ModelMap model){
		return "newMerchantRequest";
	}
	
	@RequestMapping(value="/merchTransactionHistory",method=RequestMethod.GET)
	public ModelAndView merchTransactionHistory(){
		ModelAndView model = new ModelAndView();
		//TODO:- hard-coded as of now
		Long accountId= 654321L;//TODO:- somehow get this
		
		/*Transaction transaction= new Transaction();
		transaction.setAccountId(accountId);
		transaction.setAmount(Double.valueOf(10000));
		transaction.setRemark("debit for Ipad purchase");
		transaction.setType("d");
		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);
		*/
		if(accountId==null){
			System.out.println("account is null. requires logger");
		} else{
			List<Transaction> listTransaction= merchantService.getTransactionHistory(accountId);
			model.addObject("TransactionList", listTransaction);
		}
		model.setViewName("merchTransactionHistory");
		return model;
	}
	
	@Transactional
	@RequestMapping(value="/initiateMerchTrans",method=RequestMethod.POST)
	public ModelAndView submitForm(ModelMap model, @Validated UserInfo UserInfo, BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,ServletRequest servletRequest) throws Exception
	{
		ModelAndView modelView = new ModelAndView();
		String remark= request.getParameter("remark");
		String type="D";//by default

		Long accountId;
		Double amountVal;
		try{
			accountId= Long.valueOf(request.getParameter("accountnum"));
			amountVal= Double.valueOf(request.getParameter("amount"));
		} catch(NumberFormatException | NullPointerException e){
			System.out.println("Exception "+e);
			modelView.setViewName("extHome");
			return modelView;
		}

		if(merchantService.isAccountValid(accountId)){ //TODO:- call appropriate service
			if(request.getParameter("radios") != null) {
	            if(request.getParameter("radios").equals("radio2")) {
	            	type="C";
	            }
	        }
			
		merchantService.insertNewTransaction(accountId, amountVal, remark, type);
		modelView.addObject("merchantTxnMsg", "Credit/Debit Transaction not Initiated");
			
		} else{
			//add some error message
			modelView.addObject("merchantTxnMsg", "Credit/Debit Transaction not Initiated");
		}
		
		modelView.setViewName("extHome");
		return modelView;
		
	}
}
