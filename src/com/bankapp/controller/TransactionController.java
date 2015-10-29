
/**
 * 
 */
package com.bankapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bankapp.model.Transaction;
import com.bankapp.model.TransactionList;
import com.bankapp.services.TransactionService;

/**
 * @author manikandan_eshwar
 */

@Controller
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	private static final Logger logger = Logger.getLogger(TransactionController.class);
	
	@RequestMapping("/pendingTransactionsRE")
	public ModelAndView displayLowPriorityTransactions(@ModelAttribute("transactionIdList") TransactionList TidList) {
		ModelAndView model = new ModelAndView();
		model.setViewName("authorizeTransactions");
		List<Transaction> trList = transactionService.getPendingTransactionsForRE();
		model.addObject("trList", trList);
		return model;
	}

	@RequestMapping("/pendingTransactionsSM")
	public ModelAndView displayAllPriorityTransactions(@ModelAttribute("transactionIdList") TransactionList TidList) {
		ModelAndView model = new ModelAndView();
		model.setViewName("authorizeTransactions");
		List<Transaction> trList = transactionService.getPendingTransactionsForSM();
		model.addObject("trList", trList);
		return model;
	}

	@RequestMapping("/approveTransactionsRE")
	public ModelAndView approveTransactionsRE(@ModelAttribute("transactionIdList") TransactionList TidList,
			@RequestParam String toDo) {
		
		logger.info("In Approve Transactions for Regular Employee.");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("authorizeTransactions");
		String status = "";
		try {
			if (toDo.equalsIgnoreCase("Approve")) {
				status = "A";
				transactionService.approveTransactions(TidList.getTidList(),
						SecurityContextHolder.getContext().getAuthentication().getName(), status);
				model.addObject("msg", "Selected Transactions has been Approved and Processed !!!");
			} else if (toDo.equalsIgnoreCase("Reject")) {
				status = "R";
				transactionService.approveTransactions(TidList.getTidList(),
						SecurityContextHolder.getContext().getAuthentication().getName(), status);
				model.addObject("msg", "Selected Transactions has been Rejected !!!");
			}
		} catch (Exception e) {
			model.addObject("msg", "Unexpected Error Occurred in the System.. Please Try Again!!!");
		}

		List<Transaction> trList = transactionService.getPendingTransactionsForRE();
		model.addObject("trList", trList);

		return model;
	}

	@RequestMapping("/approveTransactionsSM")
	public ModelAndView approveTransactionsSM(@ModelAttribute("transactionIdList") TransactionList TidList,
			@RequestParam String toDo) {
		
		logger.info("In Approve Transactions for System Managers.");
		
		ModelAndView model = new ModelAndView();
		model.setViewName("authorizeTransactions");
		String status = "";
		try {
			if (toDo.equalsIgnoreCase("Approve")) {
				status = "A";
				transactionService.approveTransactions(TidList.getTidList(),
						SecurityContextHolder.getContext().getAuthentication().getName(), status);
				model.addObject("msg", "Selected Transactions has been Approved and Processed !!!");
			} else if (toDo.equalsIgnoreCase("Reject")) {
				status = "R";
				transactionService.approveTransactions(TidList.getTidList(),
						SecurityContextHolder.getContext().getAuthentication().getName(), status);
				model.addObject("msg", "Selected Transactions has been Rejected !!!");
			}
		} catch (Exception e) {
			model.addObject("msg", "Unexpected Error Occurred in the System.. Please Try Again!!!");
		}

		List<Transaction> trList = transactionService.getPendingTransactionsForSM();
		model.addObject("trList", trList);

		return model;
	}

}
