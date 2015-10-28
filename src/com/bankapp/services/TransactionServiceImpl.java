

package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bankapp.dao.TransactionDAO;
import com.bankapp.dao.UserDAO;
import com.bankapp.model.Transaction;
import com.bankapp.model.UserInfo;
import com.bankapp.model.Useraccounts;

/**
 * @author manikandan_eshwar
 *
 */
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionDAO transactionDAO;

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#getPendingTransactionsForRE()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForRE() {
		// TODO Auto-generated method stub
		return transactionDAO.getPendingTransactionsForRE();
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#getPendingTransactionsForSM()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForSM() {
		// TODO Auto-generated method stub
		return transactionDAO.getPendingTransactionsForSM();
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
	public List<Transaction> getTransactionHistory(String userName) {
		return transactionDAO.getTransactionHistory(userName);
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
	public Boolean approveTransactions(String[] tIdList, String approvedBy, String status) {
		return transactionDAO.approveTransactions(tIdList,approvedBy, status);
	}

	/* (non-Javadoc)
	 * @see com.bankapp.services.TransactionService#insertNewTransaction(com.bankapp.bean.Transaction)
	 */
	@Override
	public Boolean insertNewTransaction(Transaction transaction, Useraccounts userAccounts) {
		return transactionDAO.insertNewTransaction(transaction, userAccounts);
	}

	@Override
	public List<Useraccounts> getUserAccountsInfoByUserName(String UserName) {
		//returns the Useraccounts object based on the username
		return transactionDAO.getUserAccountsInfoByUserName(UserName);
	}

	@Override
	public Boolean updateBalance(Useraccounts userAccounts) {
		// TODO Auto-generated method stub
		return transactionDAO.updateBalance(userAccounts);
	}

	@Override
	public void deleteTransaction(Transaction transaction) {
		transactionDAO.deleteTransaction(transaction);
		
	}

	@Override
	public Useraccounts getUserAccountsInfoByAccid(Long accid) {
		
		return transactionDAO.getUserAccountsInfoByAccid(accid);
	}

	@Override
	public List<Transaction> getMerchTransactions(Long accid) {
		return transactionDAO.getMerchTransactions(accid);
	}


}
