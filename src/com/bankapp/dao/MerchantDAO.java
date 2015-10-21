/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import com.bankapp.model.Transaction;

/**
 * @author sunny
 *
 */
public interface MerchantDAO {
	
	// call tbl_transaction
	public Boolean insertNewTransaction(Transaction transaction);
	
	// call tbl_accounts
	public String getUserName(Long accountId);
	
	// call tbl_accounts
	public List<Transaction> getTransactionHistory(Long accountId);

}
