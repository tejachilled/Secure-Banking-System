/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.bankapp.bean.Transaction;

/**
 * @author manikandan_eshwar
 *
 */
public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	 DataSource dataSource;
	
	/* (non-Javadoc)
	 * @see com.bankapp.dao.TransactionDAO#getPendingTransactionsForRE()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForRE() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.dao.TransactionDAO#getPendingTransactionsForSM()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForSM() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.dao.TransactionDAO#getApprovedTransactionsForEmployee()
	 */
	@Override
	public List<Transaction> getApprovedTransactionsForEmployee() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.dao.TransactionDAO#getTransactionHistory(java.lang.String)
	 */
	@Override
	public List<Transaction> getTransactionHistory(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.dao.TransactionDAO#downloadTransactionHistory(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> downloadTransactionHistory(String accountId,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.dao.TransactionDAO#approveTransaction(com.bankapp.bean.Transaction)
	 */
	@Override
	public Boolean approveTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bankapp.dao.TransactionDAO#insertNewTransaction(com.bankapp.bean.Transaction)
	 */
	@Override
	public Boolean insertNewTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

}
