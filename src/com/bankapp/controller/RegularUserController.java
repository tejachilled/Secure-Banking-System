package com.bankapp.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.PIIAccessInfoModel;
import com.bankapp.model.Transaction;
import com.bankapp.model.Transfer;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;
import com.bankapp.services.GovtRequestsService;
import com.bankapp.services.TransactionService;
import com.bankapp.services.UserService;
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

	private static final Logger logger = Logger.getLogger(RegularUserController.class);

	@RequestMapping("/extHome")
	public String externalCustomer(Model model, HttpSession session, HttpServletRequest request) {
		return "extHome";
	}

	@RequestMapping(value = "/Credit")
	public String creditPage(ModelMap model) {
		model.addAttribute("credit", new Transaction());
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
			if (currUserAcc.getAccountType().equals(transaction.getAccType())) {
				accUserAccount = currUserAcc;
			}
		}
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
			modelAndView.addObject("msg",
					"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			return modelAndView;
		}
		return modelAndView;
	}

	@Transactional
	@RequestMapping(value = "/initiateCredit", method = RequestMethod.POST)
	public ModelAndView submitFormCredit(ModelMap model, @ModelAttribute("credit") Transaction transaction,
			BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("credit", new Transaction());
		logger.info(transaction.getAccType());
		modelAndView.setViewName("credit");
		bindAccounts(modelAndView);
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
		return modelAndView;
	}

	@Transactional
	@RequestMapping(value = "/initiateTransfer", method = RequestMethod.POST)
	public ModelAndView submitFormTransfer(ModelMap model, @ModelAttribute("transferAmt") Transfer transfer,
			BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("transferAmt", new Transfer());
		modelAndView.setViewName("transfer");
		logger.info(transfer.getAccType());
		bindAccounts(modelAndView);
		Long accno = transfer.getToAccountNo();
		boolean accStatus = userService.checkAccountExists(accno);
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Inside User Debit-initiated by: " + userName);

		List<Useraccounts> userAccounts = transactionService.getUserAccountsInfoByUserName(userName);
		Useraccounts debUserAccount = null;
		try {
			for (Useraccounts currUserAcc : userAccounts) {
				if (currUserAcc.getAccountType().equalsIgnoreCase(transfer.getAccType())) {
					debUserAccount = currUserAcc;
					logger.info("Checking Null 1");
				}
			}
			Transaction debTrans = new Transaction();
			Transaction credTrans = new Transaction();
			debTrans.setAmount(transfer.getAmountInvolved());
			credTrans.setAmount(transfer.getAmountInvolved());
			debTrans.setAccountId(debUserAccount.getAccountno());
			logger.info("Checking Null 2");
			credTrans.setAccountId(transfer.getToAccountNo());
			logger.info("Checking Null 3");
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
			logger.info("Checking Null 4");
			debTrans.setDateInitiated(new Date());
			logger.info("Checking Null 5");
			credTrans.setDateInitiated(new Date());
			logger.info("Checking Null 6");
			String transId = UUID.randomUUID().toString();
			logger.info("Checking Null 7");
			debTrans.setTransactionID(transId);
			logger.info("Checking Null 8");
			credTrans.setTransactionID(transId);
			logger.info(debUserAccount);

			logger.info(debTrans.getTransactionID());
			logger.info(debTrans.getAccountId());
			logger.info(debTrans.getType());
			logger.info(debTrans.getIsCritical());
			logger.info(debTrans.getAmount());
			logger.info(debTrans.getDateInitiated());
			Boolean debres = null;
			try {
				debres = transactionService.insertNewTransaction(debTrans, debUserAccount);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			logger.info("Checking Null 10");
			Useraccounts credUserAccount = null;
			logger.info("Checking Null 11");
			credUserAccount = transactionService.getUserAccountsInfoByAccid(transfer.getToAccountNo());
			logger.info("Checking Null 12");
			Boolean credres = transactionService.insertNewTransaction(credTrans, credUserAccount);
			logger.info("Checking Null 13");
			if (debres.equals(true) && credres.equals(true) && !debTrans.getIsCritical().equals("N")) {
				modelAndView.addObject("msg",
						"Debit Transaction has been submit the bank..The amount will be debited from your account once the Transaction is Approved!!!");
			} else if (debres.equals(true) && credres.equals(true) && debTrans.getIsCritical().equals("N")) {
				debUserAccount.setBalance(debUserAccount.getBalance() - transfer.getAmountInvolved());
				credUserAccount.setBalance(credUserAccount.getBalance() + transfer.getAmountInvolved());
				Boolean debval = transactionService.updateBalance(debUserAccount);
				Boolean credval = transactionService.updateBalance(credUserAccount);
				if (credval && debval)
					modelAndView.addObject("msg",
							"Transfer was Successful..New Account Balance is " + debUserAccount.getBalance());
				else {
					modelAndView.addObject("msg",
							"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
					transactionService.deleteTransaction(debTrans);
					transactionService.deleteTransaction(credTrans);
				}
			} else {
				modelAndView.addObject("msg",
						"Unexpected Error Occurred or Invalid Input format..Please Try Again.. If problem persists contact the customer support!!!");
			}

		} catch (Exception ex) {
			logger.info(ex);
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
		if (userName.equals("anonymousUser")) {
			model.setViewName("login");
			return model;
		}
		if (userName == null) {
			System.out.println("username is null. requires logger");
			// logger
		} else {
			List<Transaction> listTransaction = transactionService.getTransactionHistory(userName);
			if (listTransaction != null) {
				listTransaction = listTransaction.subList(0, listTransaction.size() > 10 ? 10 : listTransaction.size());
			}

			model.addObject("TransactionList", listTransaction);
		}
		model.setViewName("viewTransactions");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/downloadTransaction", method = RequestMethod.GET)
	public void downloadTransaction(HttpServletRequest request, HttpServletResponse response,
			ServletRequest servletRequest) throws Exception {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userName == null) {
			System.out.println("username is null. requires logger");
			// logger
		} else {
			List<Transaction> listTransaction = transactionService.getTransactionHistory(userName);
			if (listTransaction != null) {
				listTransaction = listTransaction.subList(0, listTransaction.size() > 15 ? 15 : listTransaction.size());
			}
			String filePath = pdfCreator.createPdf(listTransaction);
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

	}

	@RequestMapping(value = "/updatePersonalInfo")
	public String updatePersonalInfo(ModelMap model) {
		
		Boolean piiExists = govtRequestsService
				.isPiiInfoPresent(SecurityContextHolder.getContext().getAuthentication().getName());
		String result = "n";
		if (piiExists.equals(true)) {
			result = "y";
		}
		UserInfo user = userService.getUserInfobyUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		model.addAttribute("piiExists", result);
		model.addAttribute("accessInfo",user);
		return "updatePersonalInfo";
	}

	@Transactional
	@RequestMapping(value = "/confirmUpdate", method = RequestMethod.POST)
	public ModelAndView confirmUpdate(ModelMap modelinfo, @ModelAttribute("accessInfo") UserInfo pii) {
		ModelAndView model = new ModelAndView();
		model.setViewName("updatePersonalInfo");
		Boolean piiExists = govtRequestsService
				.isPiiInfoPresent(SecurityContextHolder.getContext().getAuthentication().getName());
		String result = "n";

		userService.updateUserInfo(pii);
		model.addObject("success", "Updated details successfully");
		if (piiExists.equals(true)) {
			result = "y";
		} else if (piiExists.equals(false)) {
			if (!pii.getSsn().isEmpty() && pii.getSsn().matches("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$")) {
				PIIAccessInfoModel piiInfo = new PIIAccessInfoModel();				
				piiInfo.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
				piiInfo.setPii(pii.getSsn());
				govtRequestsService.insertPersonalInfo(piiInfo);
				model.addObject("success", "Persnoal Info(SSN) Value has been updated successfully!!!");
			} else {
				model.addObject("error", "Persnoal Info(SSN) Value is Invalid... Please Try Again!!!");
			}
		}
		
		model.addObject("piiExists", result);		
		
		return model;
	}
}
