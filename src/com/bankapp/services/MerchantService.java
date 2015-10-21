/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import com.bankapp.model.Transaction;

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
	public boolean isAccountValid(Long accountId);
	
	/*
	 * insertNewTransaction
	 */
	public boolean insertNewTransaction(Long accountId, Double amount, String remark, String type);
	
	/*
	 * get Transaction History
	 */
	public List<Transaction> getTransactionHistory(Long accountId);
}
