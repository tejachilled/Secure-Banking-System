package com.bankapp.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.OTP;
import com.bankapp.model.PIIAccessInfoModel;
import com.bankapp.model.Transaction;
import com.bankapp.model.TransactionList;
import com.bankapp.model.Transfer;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;
import com.bankapp.services.CertService;
import com.bankapp.services.GovtRequestsService;
import com.bankapp.services.OTPService;
import com.bankapp.services.TransactionService;
import com.bankapp.services.UserService;
import com.bankapp.userexceptions.CustomException;
import com.bankapp.userexceptions.MinimumBalanceException;
import com.bankapp.userexceptions.NegativeAmountException;
import com.bankapp.util.PdfCreator;

@Controller
public class RegularUserController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserService userService;

	@Autowired
	PdfCreator pdfCreator;

	@Autowired
	GovtRequestsService govtRequestsService;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	CertService certService;

	private static final Logger logger = Logger.getLogger(RegularUserController.class);

	@RequestMapping("/extHome")
	public String externalCustomer(Model model, HttpSession session, HttpServletRequest request) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);	
		
		for (Useraccounts userAccount : userAccounts) {
			String accountid = String.valueOf(userAccount.getAccountno());
			logger.info(accountid);
			if (userAccount.getAccountType().equalsIgnoreCase("savings")) {
				model.addAttribute("accountIdSavings", "Savings Account ID: "+ accountid);
				model.addAttribute("accountBalSavings",
						"Savings Account Balance: $ " +userAccount.getBalance());
			} else if (userAccount.getAccountType().equalsIgnoreCase("checking")) {
				model.addAttribute("accountIdCheckings", "Checkings Account ID:"+accountid);
				model.addAttribute("accountBalCheckings",
						"Checking Account Balance: $ " +userAccount.getBalance());
			}
		}
		return "extHome";
	}

	@RequestMapping(value = "/Credit")
	public String creditPage(ModelMap model, HttpServletRequest request) throws Exception{
		model.addAttribute("credit", new Transaction());
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		String encryptedString=null;
		try {
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		String s = String.valueOf(n);
		System.out.println("Plain Text: "+ s);
		request.getSession().setAttribute("plainText", s);
		encryptedString = certService.getEncryptedString(SecurityContextHolder.getContext().getAuthentication().getName(),s);
		}
		
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Error in encrypting the Plain Text");
		}
		System.out.println("Mani: "+encryptedString);
		request.getSession().setAttribute("encryptedString", encryptedString);
		model.addAttribute("encMsg",encryptedString);
		bindAccounts(model);
	  
		logger.info("Inside User Credit");
		return "credit";
	}

	@RequestMapping(value = "/Debit")
	public String debitPage(ModelMap model) {
		model.addAttribute("debit", new Transaction());

		bindAccounts(model);
		logger.info("Inside User Debit");
		return "debit";
	}

	@RequestMapping(value = "/Transfer")
	public String transferPage(ModelMap model) {
		model.addAttribute("transferAmt", new Transfer());
		bindAccounts(model);
		return "transfer";
	}

	@Transactional
	@RequestMapping(value = "/initiateDebit", method = RequestMethod.POST)
	public ModelAndView submitFormDebit(ModelMap model, @ModelAttribute("debit") Transaction transaction,
			BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("debit", new Transaction());
		modelAndView.setViewName("debit");
		logger.info(transaction.getAccType());
		bindAccounts(modelAndView);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Inside User Debit-initiated by: " + userName);

		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		Useraccounts accUserAccount = null;

		for (Useraccounts currUserAcc : userAccounts) {
			if (currUserAcc.getAccountType().equalsIgnoreCase(transaction.getAccType())) {
				accUserAccount = currUserAcc;
			}
		}
		
		System.out.println("Mani came here");
		try {
			if (transaction.getAmount() < 0) {
				throw new NegativeAmountException("Amount cannot be a negative Value!!!");
			}
			if (accUserAccount.getBalance() - transaction.getAmount() < 500) {
				throw new MinimumBalanceException(
						"Debit Cannot be performed as the Amount after debit is lesser than the minimum balance of $500 !!!");
			}
			Transaction trans = new Transaction();
			trans.setAmount(transaction.getAmount());
			trans.setType("D");
			if (transaction.getAmount() >= 10000) {
				trans.setIsCritical("H");
			} else if (transaction.getAmount() < 10000 && transaction.getAmount() >= 1000) {
				trans.setIsCritical("L");
			} else {
				trans.setIsCritical("N");
			}
			trans.setDateInitiated(new Date());
			trans.setAccountId(accUserAccount.getAccountno());
			trans.setTransactionID(UUID.randomUUID().toString());
			Boolean res = transactionService.insertNewTransaction(trans, accUserAccount);
			if (res.equals(true) && !trans.getIsCritical().equals("N")) {
				modelAndView.addObject("msg",
						"Debit Transaction has been submit the bank..The amount will be debited from your account once the Transaction is Approved!!!");
			} else if (res.equals(true) && trans.getIsCritical().equals("N")) {
				accUserAccount.setBalance(accUserAccount.getBalance() - transaction.getAmount());
				Boolean val = transactionService.updateBalance(accUserAccount);
				if (val)
					modelAndView.addObject("msg",
							"Debit was Successful..New Account Balance is " + accUserAccount.getBalance());
				else {
					modelAndView.addObject("msg",
							"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
					transactionService.deleteTransaction(transaction);
				}
			} else {
				modelAndView.addObject("msg",
						"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			}
		} catch (NegativeAmountException ne) {
			modelAndView.addObject("msg", ne.getMessage());
			return modelAndView;
		} catch (MinimumBalanceException mi) {
			modelAndView.addObject("msg", mi.getMessage());
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("msg",
					"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			return modelAndView;
		}
		return modelAndView;
	}

	@Transactional
	@RequestMapping(value = "/initiateCredit", method = RequestMethod.POST)
	public ModelAndView submitFormCredit(ModelMap model, @ModelAttribute("credit") Transaction transaction,@RequestParam String plainText,
			BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("credit", new Transaction());
		logger.info(transaction.getAccType());
		modelAndView.setViewName("credit");
		bindAccounts(modelAndView);
		 String generatedEncMsg = (String) request.getSession().getAttribute("plainText");
		 String userInputDecMsg = plainText;
		 
		 System.out.println("--------");
		 System.out.println(generatedEncMsg);
		 System.out.println("--------");
		 System.out.println(userInputDecMsg);
		if(generatedEncMsg.equals(userInputDecMsg)) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		try {
			List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
			if (transaction.getAmount() < 0) {
				throw new NegativeAmountException("Amount cannot be a negative Value!!!");
			}
			Useraccounts accUserAccount = null;
			for (Useraccounts currUserAcc : userAccounts) {
				if (currUserAcc.getAccountType().equalsIgnoreCase(transaction.getAccType())) {
					accUserAccount = currUserAcc;
					logger.info("Trans" + transaction.getAccType());
					logger.info("Curr" + currUserAcc.getAccountType());
				}
			}
			Transaction trans = new Transaction();
			trans.setAmount(transaction.getAmount());
			trans.setType("C");
			if (transaction.getAmount() >= 10000) {
				trans.setIsCritical("H");
			} else if (transaction.getAmount() < 10000 && transaction.getAmount() >= 1000) {
				trans.setIsCritical("L");
			} else {
				trans.setIsCritical("N");
			}
			trans.setDateInitiated(new Date());
			trans.setAccountId(accUserAccount.getAccountno());
			trans.setTransactionID(UUID.randomUUID().toString());
			Boolean res = transactionService.insertNewTransaction(trans, accUserAccount);

			if (res.equals(true) && trans.getIsCritical() != "N") {
				modelAndView.addObject("msg",
						"Credit Transaction has been submit the bank..The amount will be credited from your account once the Transaction is Approved!!!");
			} else if (res.equals(true) && trans.getIsCritical() == "N") {

				accUserAccount.setBalance(accUserAccount.getBalance() + transaction.getAmount());
				Boolean val = transactionService.updateBalance(accUserAccount);
				if (val) {
					String encryptedString=null;
					try {
					Random rnd = new Random();
					int n = 100000 + rnd.nextInt(900000);
					String s = String.valueOf(n);
					System.out.println("Plain Text: "+ s);
					request.getSession().setAttribute("plainText", s);
					encryptedString = certService.getEncryptedString(SecurityContextHolder.getContext().getAuthentication().getName(),s);
					}
					
					catch(Exception e) {
						e.printStackTrace();
						logger.error("Error in encrypting the Plain Text");
					}
					System.out.println("Mani: "+encryptedString);
					request.getSession().setAttribute("encryptedString", encryptedString);
					model.addAttribute("encMsg",encryptedString);
					
					modelAndView.addObject("msg",
							"Credit was Successful..New Account Balance is " + accUserAccount.getBalance());
				} else {
					// modelAndView.addObject("msg","Unexpected Error Occurred
					// or Invalid Input format..Please Try Again.. If problem
					// persists contact the customer support!!!");
					transactionService.deleteTransaction(transaction);
				}
			} else {
				modelAndView.addObject("msg",
						"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			}
		} catch (NegativeAmountException ne) {
			modelAndView.addObject("msg", ne.getMessage());
			return modelAndView;
		} catch (Exception e) {
			modelAndView.addObject("msg",
					"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			return modelAndView;
		}
		
		}
		
		else {
			modelAndView.addObject("msg", "The decrypted plain text does not match with the corresponding encrypted string!!! Please Try Again!!!");
		}
		
		return modelAndView;
	}

	@Transactional
	@RequestMapping(value = "/initiateTransfer", method = RequestMethod.POST)
	public ModelAndView submitFormTransfer(ModelMap model, @ModelAttribute("transferAmt") Transfer transfer,
			BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {
		    
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			try 
			{
			otpService.sendOTP(userService.getUserAndAccuntInfobyUserName(userName).getEmaiID(), userName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("otp", new OTP());
		modelAndView.setViewName("enterOTP");
		logger.info(transfer.getAccType());
		bindAccounts(modelAndView);
		Long accno = transfer.getToAccountNo();
		boolean accStatus = userService.checkAccountExists(accno);
		logger.info("Inside User Debit-initiated by: " + userName);

		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		Useraccounts debUserAccount = null;
		try {
			for (Useraccounts currUserAcc : userAccounts) {
				if (currUserAcc.getAccountType().equalsIgnoreCase(transfer.getAccType())) {
					debUserAccount = currUserAcc;
				}
			}
			if (transfer.getAmountInvolved() <= 0) {
				throw new NegativeAmountException("Amount cannot be a negative Value!!!");
			}
			if (debUserAccount.getBalance() - transfer.getAmountInvolved() < 500) {
				throw new MinimumBalanceException(
						"Debit Cannot be performed as the Amount after debit is lesser than the minimum balance of $500 !!!");
			}
			if (transfer.getAmountInvolved()== null || transfer.getToAccountNo()== 0)
			{
				modelAndView.setViewName("transfer");
				modelAndView.addObject("errorMessage", "Both the fields are Mandatory!!!");
				return modelAndView;
			}
			Transaction debTrans = new Transaction();
			Transaction credTrans = new Transaction();
			debTrans.setAmount(transfer.getAmountInvolved());
			credTrans.setAmount(transfer.getAmountInvolved());
			debTrans.setAccountId(debUserAccount.getAccountno());
			credTrans.setAccountId(transfer.getToAccountNo());
			debTrans.setType("D");
			credTrans.setType("C");
			if (transfer.getAmountInvolved() >= 10000) {
				debTrans.setIsCritical("H");
				credTrans.setIsCritical("H");
			} else if (transfer.getAmountInvolved() < 10000 && transfer.getAmountInvolved() >= 1000) {
				debTrans.setIsCritical("L");
				credTrans.setIsCritical("L");
			} else {
				debTrans.setIsCritical("N");
				credTrans.setIsCritical("N");
			}
			
			debTrans.setDateInitiated(new Date());
			
			credTrans.setDateInitiated(new Date());
			
			String transId = UUID.randomUUID().toString();
			
			debTrans.setTransactionID(transId);
			credTrans.setTransactionID(transId);
			Useraccounts credUserAccount = transactionService.getUserAccountsInfoByAccid(transfer.getToAccountNo()); 
			request.getSession().setAttribute("creditTransaction",credTrans);
			request.getSession().setAttribute("debitTransaction",credTrans);
			request.getSession().setAttribute("debitAccount",debUserAccount);
			request.getSession().setAttribute("creditAccount",credUserAccount);
			request.getSession().setAttribute("transfer",transfer );
			

		} catch (Exception ex) {
			modelAndView.setViewName("transfer");
			modelAndView.addObject("errorMessage", "Unexpected Error Occurred!!! Invalid or Null Input");
			return modelAndView;
		}

		if (accStatus) {
			return modelAndView;
		} else {
			modelAndView.addObject("errorMessage", "Account Does not exist in Our System !!!");
			return modelAndView;
		}
	}

	private void bindAccounts(ModelMap model) {

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Inside User");
		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		for (Useraccounts userAccount : userAccounts) {
			String accountid = String.valueOf(userAccount.getAccountno());
			logger.info(accountid);
			if (userAccount.getAccountType().equalsIgnoreCase("savings")) {
				model.addAttribute("account_savings",
						"SAVINGS - " + accountid.substring(accountid.length() - 4, accountid.length()));
			} else if (userAccount.getAccountType().equalsIgnoreCase("checking")) {
				model.addAttribute("account_checking",
						"CHECKING - " + accountid.substring(accountid.length() - 4, accountid.length()));
			}
		}

	}

	private void bindAccounts(ModelAndView modelAndView) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Inside User");
		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		for (Useraccounts userAccount : userAccounts) {
			String accountid = String.valueOf(userAccount.getAccountno());
			logger.info(accountid);
			if (userAccount.getAccountType().equalsIgnoreCase("savings")) {
				modelAndView.addObject("account_savings",
						"SAVINGS - " + accountid.substring(accountid.length() - 4, accountid.length()));
			} else if (userAccount.getAccountType().equalsIgnoreCase("checking")) {
				modelAndView.addObject("account_checking",
						"CHECKING - " + accountid.substring(accountid.length() - 4, accountid.length()));
			}
		}

	}

	@RequestMapping(value = "/viewMyProfile", method = RequestMethod.GET)
	public String viewMyself(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserInfo user = userService.getUserAndAccuntInfobyUserName(username);
		model.addAttribute("accessInfo", user);
		return "viewExtInfo";
	}

	@RequestMapping(value = "/viewTransactions", method = RequestMethod.GET)
	public ModelAndView viewTransaction() {
		ModelAndView model = new ModelAndView();
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info(userName);
		if (userName==null || userName.equals("anonymousUser")) {
			model.setViewName("login");
			return model;
		}
		List<Transaction> listTransaction = transactionService.getTransactionHistory(userName);
		if (listTransaction != null) {
			listTransaction = listTransaction.subList(0, listTransaction.size() > 10 ? 10 : listTransaction.size());
		}

		model.addObject("TransactionList", listTransaction);
		model.setViewName("viewTransactions");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/downloadTransaction", method = RequestMethod.GET)
	public void downloadTransaction(HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		String filePath="";
		if (userName == null) {
			logger.fatal("username is null");
		} else {
			List<Transaction> listTransaction = transactionService.getTransactionHistory(userName);
			if (listTransaction != null) {
				listTransaction = listTransaction.subList(0, listTransaction.size() > 15 ? 15 : listTransaction.size());
			}
			filePath = pdfCreator.createPdf(listTransaction);
			FileSystemResource fsr = new FileSystemResource(filePath);
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=TransactionHistory.pdf");
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			InputStream inputStream = fsr.getInputStream();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
		}
		// delete that file
		try{
			File file= new File(filePath);
			//FileUtils.forceDelete(file);
		} catch(Exception e){
			logger.error("cannot delete pdf file"+e);
		}
	}

	@RequestMapping(value = "/updatePersonalInfo")
	public String updatePersonalInfo(ModelMap model) {
		
		String piiExists = govtRequestsService
				.isPiiInfoPresent(SecurityContextHolder.getContext().getAuthentication().getName());
		String result = "n";
		UserInfo user = userService.getUserInfobyUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (piiExists!=null && piiExists.length()>3) {
			result = "y";
			user.setSsn(piiExists);
		}		
		model.addAttribute("piiExists", result);
		model.addAttribute("accessInfo",user);
		return "updatePersonalInfo";
	}

	@Transactional
	@RequestMapping(value = "/confirmUpdate", method = RequestMethod.POST)
	public ModelAndView confirmUpdate(ModelMap modelinfo, @ModelAttribute("accessInfo") UserInfo pii) {
		ModelAndView model = new ModelAndView();
		model.setViewName("updatePersonalInfo");
		String piiExists = govtRequestsService
				.isPiiInfoPresent(SecurityContextHolder.getContext().getAuthentication().getName());
		String result = "n";

		userService.updateUserInfo(pii);
		model.addObject("success", "Updated details successfully");
		if (piiExists!=null && piiExists.length()>3) {
			result = "y";
		} else{
			System.out.println("Pii input: "+pii.getSsn());
			if (!pii.getSsn().isEmpty() && pii.getSsn().matches("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$")) {
				PIIAccessInfoModel piiInfo = new PIIAccessInfoModel();				
				piiInfo.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
				String ssn = pii.getSsn();
				piiInfo.setPii(ssn);
				System.out.println("Inserting Pii information");
				govtRequestsService.insertPersonalInfo(piiInfo);
				pii = userService.getUserInfobyUserName(SecurityContextHolder.getContext().getAuthentication().getName());
				pii.setSsn(ssn);
				model.addObject("accessInfo",pii);
				result = "y";
				model.addObject("success", "Persnoal Info(SSN) Value has been updated successfully!!!");
			} else {
				model.addObject("error", "Persnoal Info(SSN) Value is Invalid... Please Try Again!!!");
			}
		}
		
		model.addObject("piiExists", result);		
		
		return model;
	}
	
	@Transactional
	@RequestMapping(value = "/validateOTP", method = RequestMethod.POST)
	public ModelAndView validateOTP(ModelMap modelinfo, @ModelAttribute("otp") OTP otp, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("transfer");
		modelAndView.addObject("transferAmt", new Transfer());
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Transaction credTrans = (Transaction) request.getSession().getAttribute("creditTransaction");
		Transaction debTrans = (Transaction)  request.getSession().getAttribute("debitTransaction");
		Useraccounts debUserAccount = (Useraccounts)request.getSession().getAttribute("debitAccount");
		Useraccounts credUserAccount = (Useraccounts) request.getSession().getAttribute("creditAccount");
		Transfer transfer = (Transfer)request.getSession().getAttribute("transfer");
		
		bindAccounts(modelAndView);
		
		try {
			if(otpService.checkOTP(userName, otp.getOtpValue())){
				Boolean debres = null;
				try {
					debres = transactionService.insertNewTransaction(debTrans, debUserAccount);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				Boolean credres = transactionService.insertNewTransaction(credTrans, credUserAccount);
				
				if (debres.equals(true) && credres.equals(true) && !debTrans.getIsCritical().equals("N")) {
					modelAndView.addObject("msg","Debit Transaction has been submit the bank..The amount will be debited from your account once the Transaction is Approved!!!");
				} else if (debres.equals(true) && credres.equals(true) && debTrans.getIsCritical().equals("N")) {
					debUserAccount.setBalance(debUserAccount.getBalance() - transfer.getAmountInvolved());
					credUserAccount.setBalance(credUserAccount.getBalance() + transfer.getAmountInvolved());
					Boolean debval = transactionService.updateBalance(debUserAccount);
					Boolean credval = transactionService.updateBalance(credUserAccount);
					if (credval && debval)
						modelAndView.addObject("msg","Transfer was Successful..New Account Balance is " + debUserAccount.getBalance());
					else {
						modelAndView.addObject("msg",
								"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
						transactionService.deleteTransaction(debTrans);
						transactionService.deleteTransaction(credTrans);
					}
				} else {
					modelAndView.addObject("msg","Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
				}
				
				return modelAndView;
				
			}
			else {
				modelAndView.addObject("msg","The OTP You Entered is Invalid. Please Try Again!!!");
			}
		} catch (CustomException e) {
				e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/transactionReviewRequest", method = RequestMethod.GET)
	public ModelAndView transactionReviewRequest(Model model) {
		ModelAndView modelView= new ModelAndView();
		modelView.setViewName("reviewTransactions");
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		List<Transaction> listTransaction= new ArrayList<>();
		for(Useraccounts account: userAccounts){
			List<Transaction> txn= transactionService.getMerchTransactions(account.getAccountno());
			if(txn!=null){
				listTransaction.addAll(txn);
			}
		}

		modelView.addObject("trList", listTransaction);
		modelView.addObject("transactionIdList", new TransactionList());
		return modelView;
	}
	
	@Transactional
	@RequestMapping(value = "/approveTransactionsMerchant", method = RequestMethod.POST)
	public ModelAndView approveTransactionsMerchant(ModelMap modelinfo, 
			@ModelAttribute("transactionIdList") TransactionList TidList,
			@RequestParam String toDo) {
		ModelAndView model = new ModelAndView();
		model.setViewName("reviewTransactions");
		String status = "";
		try{
			if(toDo.equalsIgnoreCase("Approve")) {
				status = "A";
				transactionService.approveTransactions(TidList.getTidList(),
						SecurityContextHolder.getContext().getAuthentication().getName(), status);
				model.addObject("msg", "Selected Transactions has been Approved and Processed !!!");
			} else if(toDo.equalsIgnoreCase("Reject")){
				status = "R";
				transactionService.approveTransactions(TidList.getTidList(),
						SecurityContextHolder.getContext().getAuthentication().getName(), status);
				model.addObject("msg", "Selected Transactions has been Rejected !!!");
			}
		} catch(Exception e){
			model.addObject("msg", "Unexpected Error Occurred in the System.. Please Try Again!!!");
			logger.error("something happened while approve/reject txn:: "+ e);
		}
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		List<Transaction> listTransaction= new ArrayList<>();
		for(Useraccounts account: userAccounts){
			List<Transaction> txn= transactionService.getMerchTransactions(account.getAccountno());
			if(txn!=null){
				listTransaction.addAll(txn);
			}
		}

		model.addObject("trList", listTransaction);
		model.addObject("transactionIdList", new TransactionList());
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/downloadPrivKey", method = RequestMethod.GET)
	public void downloadPrivKey(HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		String filePath="/"+userName+"userPfx.pfx";
		if (userName == null) {
			logger.fatal("username is null");
		} else {
			List<Transaction> listTransaction = transactionService.getTransactionHistory(userName);
			if (listTransaction != null) {
				listTransaction = listTransaction.subList(0, listTransaction.size() > 15 ? 15 : listTransaction.size());
			}
			FileSystemResource fsr = new FileSystemResource(filePath);
			response.setContentType("application/pkcs-12");
			response.setHeader("Content-Disposition", "attachment; filename="+filePath);
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			InputStream inputStream = fsr.getInputStream();
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
		}
		// delete that file
		try{
			File file= new File(filePath);
			//FileUtils.forceDelete(file);
		} catch(Exception e){
			logger.error("cannot delete pdf file"+e);
		}
	}
	
}
