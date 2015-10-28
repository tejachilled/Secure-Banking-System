
package com.bankapp.services;

import java.util.List;




/**
 * @author manikandan_eshwar
 *
 */
import com.bankapp.model.Transaction;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;

public interface TransactionService {
    
	//Only-Non Critical Transaction
	public List<Transaction> getPendingTransactionsForRE();
	
	//All Transaction
	public List<Transaction> getPendingTransactionsForSM();
	
	//Approved Transactions can be viewed by all employees
	public List<Transaction> getApprovedTransactionsForEmployee();
	
	//get the transaction history for an account
	public List<Transaction> getTransactionHistory(String userName);
	
	//method to facilitate the download of the transaction as pdf
	public List<Transaction> downloadTransactionHistory(String accountId, String startDate, String endDate);
	
	//to approve the transaction
	public Boolean approveTransactions(String[] tIdList, String approvedBy, String Status);
	
	public List<Transaction> getMerchTransactions(Long accid);
	
	public Boolean insertNewTransaction(Transaction transaction, Useraccounts userAccounts);
	
	public List<Useraccounts> getUserAccountsInfoByUserName(String UserName);
	
	public Useraccounts getUserAccountsInfoByAccid(Long accid);
	
	public Boolean updateBalance(Useraccounts userAccounts);
	
	public void deleteTransaction(Transaction transaction);
}
