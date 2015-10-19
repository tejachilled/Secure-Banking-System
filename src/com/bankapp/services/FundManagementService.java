package com.bankapp.services;

import com.bankapp.model.Transaction;

public interface FundManagementService {
	public void createTransferTransaction(Transaction transactions) throws Exception;

	public void createCreditTransaction(Transaction transactions) throws Exception;

	public void createDebitTransaction(Transaction transactions)throws Exception;

}
