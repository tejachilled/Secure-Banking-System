/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.Transaction;
import com.bankapp.model.Useraccounts;

/**
 * @author sunny
 *
 */
public interface MerchantDAO {
	
	// call tbl_transaction
	public Boolean insertNewTransaction(Transaction transaction, Useraccounts userAccounts);
	
	// call tbl_accounts
	public String getUserName(Long accountId);
	
	// call tbl_accounts
	public List<Transaction> getTransactionHistory(String userName);
	
	public List<Useraccounts> getUserAccountsInfoByUserName(String UserName);

}
