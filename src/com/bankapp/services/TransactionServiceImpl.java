/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import com.bankapp.bean.Transaction;

/**
 * @author manikandan_eshwar
 *
 */
public class TransactionServiceImpl implements TransactionService {

	/**
	 * 
	 */
	public TransactionServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#getPendingTransactionsForRE()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForRE() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#getPendingTransactionsForSM()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForSM() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#getApprovedTransactionsForEmployee()
	 */
	@Override
	public List<Transaction> getApprovedTransactionsForEmployee() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#getTransactionHistory(java.lang.String)
	 */
	@Override
	public List<Transaction> getTransactionHistory(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#downloadTransactionHistory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> downloadTransactionHistory(String accountId,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#approveTransaction(com.bankapp.bean.Transaction)
	 */
	@Override
	public Boolean approveTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#insertNewTransaction(com.bankapp.bean.Transaction)
	 */
	@Override
	public Boolean insertNewTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

}
