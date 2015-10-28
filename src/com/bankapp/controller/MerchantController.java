package com.bankapp.controller;

import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.bankapp.model.Useraccounts;
import com.bankapp.services.MerchantServiceImpl;


@Controller
public class MerchantController {

	@Autowired
	MerchantServiceImpl merchantService;
	
	@RequestMapping("/merchHome")
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
		String merchantUserName= SecurityContextHolder.getContext().getAuthentication().getName();
		if(merchantUserName==null){
			System.out.println("username is null. requires logger");
		} else{
			//merchantUserName= "mani";
			List<Transaction> listTransaction= merchantService.getTransactionHistory(merchantUserName);
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
		String type="C";//by default
		
		Long accountId;
		Double amountVal;
		String accountType;
		try{
			accountId= Long.valueOf(request.getParameter("accountnum"));
			amountVal= Double.valueOf(request.getParameter("amount"));
		} catch(NumberFormatException | NullPointerException e){
			System.out.println("Exception "+e);
			modelView.addObject("merchantTxnMsg", "Check Account/AmountNumber Format."
					+ "Credit/Debit Transaction not Initiated");
			modelView.setViewName("extHome");
			return modelView;
		}
		accountType=request.getParameter("accType");

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(merchantService.isAccountValid(accountId, userName) && amountVal>0){ 
			if(request.getParameter("radios") != null) {
	            if(request.getParameter("radios").equals("radio2")) {
	            	type="D";
	            }
	        }
			//get appropriate merchAccounts - Savings or checkings
			Useraccounts merchAccount=null;
			List<Useraccounts> merchAccounts= merchantService.getUserAccountsInfoByUserName(userName);
			for (Useraccounts currUserAcc: merchAccounts){
				if(currUserAcc.getAccountType().equals(accountType)){
					merchAccount = currUserAcc;
					break;
				}
			}
			
			String isCritical; //two possible critical flags for merch
			boolean txnStatus=false; // just to show message on screen
			// credit to user
			if(type=="C"){
				isCritical= "N";
				// check balance of merch a/c in case of credit req
				if(merchAccount.getBalance()-amountVal<500){
					//add some error message
					modelView.addObject("merchantTxnMsg", "Debit Cannot be performed"
							+ " as the Amount after debit is lesser than the minimum balance of $500 !!!");
					modelView.setViewName("extHome");
					return modelView;
				} else{
					txnStatus= merchantService.insertNewTransaction(accountId, amountVal, 
							remark, type, userName, accountType, merchAccount, isCritical);//try-clause
					if(txnStatus){
						//do debit from merch a/c
						Boolean val=merchantService.updateBalance(merchAccount, merchAccount.getBalance()-amountVal);
						if(!val){
							txnStatus= false;
							//delete txn
							// but hope this never happend
						}
					}
				}
			} else{ //type=="D" // debit to user
				isCritical= "M";
				txnStatus = merchantService.insertNewTransaction(accountId, amountVal, 
						   remark, type, userName, accountType, merchAccount, isCritical);
				
			}
			
			if(txnStatus){
				modelView.addObject("merchantTxnMsg", "Credit/Debit Transaction Initiated");
			} else{
				modelView.addObject("merchantTxnMsg", "Credit/Debit Transaction not Initiated");
				System.out.println("something wrong with txn values");
			}
			
		} else{
			//add some error message
			modelView.addObject("merchantTxnMsg", "Account Id/Amount Value is/are not valid."
					+ "Credit/Debit Transaction not Initiated");
		}
		
		modelView.setViewName("extHome");
		return modelView;
		
	}
}
