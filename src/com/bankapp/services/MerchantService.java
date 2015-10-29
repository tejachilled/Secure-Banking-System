/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import com.bankapp.model.Transaction;
import com.bankapp.model.Useraccounts;

/**
 * @author sunny
 *
 */
public interface MerchantService {
	
	/**
	 * 
	 * @param accountId- check it is valid
	 * @return
	 */
	public boolean isAccountValid(Long accountId, String userName);
	
	/*
	 * insertNewTransaction
	 * should have used map
	 */
	public boolean insertNewTransaction(Long accountId, Double amount, String remark, String type, 
			String userName, String accountType, Useraccounts accUserAccount, String isCritical);
	
	/*
	 * get Transaction History
	 */
	public List<Transaction> getTransactionHistory(String userName);
	
	public List<Useraccounts> getUserAccountsInfoByUserName(String UserName);

}
