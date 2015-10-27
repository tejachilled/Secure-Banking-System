package com.bankapp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.dao.TransactionDAO;
import com.bankapp.model.Transaction;

@Service("fundManagementService")
public class FundManagementServiceImpl implements FundManagementService{

	@Autowired
	private TransactionDAO transactionsDao;

	@Transactional
	public void createTransferTransaction(Transaction transactions)
			throws Exception {
		// TODO Auto-generated method stub
		// build logic here
		
		
	}

	@Transactional
	public void createCreditTransaction(Transaction transactions)
			throws Exception {
		// build logic here
	}

	@Transactional
	public void createDebitTransaction(Transaction transactions)
			throws Exception {
		// build logic here
		
	}

}
