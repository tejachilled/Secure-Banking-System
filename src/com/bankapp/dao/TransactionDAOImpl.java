/**
 * 
 */
package com.bankapp.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bankapp.jdbc.UseraccountsRowMapper;
import com.bankapp.model.Transaction;
import com.bankapp.model.Useraccounts;

/**
 * @author manikandan_eshwar
 *
 */
public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankapp.dao.TransactionDAO#getPendingTransactionsForRE()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForRE() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankapp.dao.TransactionDAO#getPendingTransactionsForSM()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForSM() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankapp.dao.TransactionDAO#getApprovedTransactionsForEmployee()
	 */
	@Override
	public List<Transaction> getApprovedTransactionsForEmployee() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankapp.dao.TransactionDAO#getTransactionHistory(java.lang.String)
	 */
	@Override
	public List<Transaction> getTransactionHistory(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankapp.dao.TransactionDAO#downloadTransactionHistory(java.lang.String
	 * , java.lang.String, java.lang.String)
	 */
	@Override
	public List<Transaction> downloadTransactionHistory(String accountId,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankapp.dao.TransactionDAO#approveTransaction(com.bankapp.bean.
	 * Transaction)
	 */
	@Override
	public Boolean approveTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bankapp.dao.TransactionDAO#insertNewTransaction(com.bankapp.bean.
	 * Transaction)
	 */
	@Override
	public Boolean insertNewTransaction(Transaction transaction,
			Useraccounts accounts) {
		int res = 0;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String approvalStatus = "A";
		if (transaction.getAmount() >= 1000) {
			approvalStatus = "P";
			String sql = "Insert into tbl_transactions (transaction_id,account_id,transaction_type,isCritical,amount,date_of_transaction_initiation,internal_user_approval,initiated_by) Values(?,?,?,?,?,?,?,?)";
			res = jdbcTemplate.update(
					sql,
					new Object[] { transaction.getTransactionID(),
							transaction.getAccountId(), transaction.getType(),
							transaction.getIsCritical(),
							transaction.getAmount(),
							transaction.getDateInitiated(), approvalStatus,
							accounts.getUsername() });
		}
		else {
			System.out.println("Inside insert new transaction dao: mani");
			try {
			String sql = "Insert into tbl_transactions (transaction_id,account_id,transaction_type,isCritical,amount,date_of_transaction_initiation,date_of_transaction_approval,internal_user_approval,initiated_by,approved_by) Values(?,?,?,?,?,?,?,?,?,?)";
			res = jdbcTemplate.update(
					sql,
					new Object[] { transaction.getTransactionID(),
							transaction.getAccountId(), transaction.getType(),
							transaction.getIsCritical(),
							transaction.getAmount(),
							transaction.getDateInitiated(), transaction.getDateInitiated(),approvalStatus,
							accounts.getUsername(), "Automatic" });
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		if (res > 0) {
			
			return true;

		}
		return false;
	}

	@Override
	public Useraccounts getUserAccountsInfoByUserName(String UserName) {
		String sql = "SELECT * FROM tbl_accounts WHERE user_name = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Useraccounts userAccounts = jdbcTemplate.queryForObject(sql,
				new Object[] { UserName }, new UseraccountsRowMapper());
		return userAccounts;
	}

	@Override
	public Boolean updateBalance(Useraccounts userAccounts) {
		String query = "UPDATE tbl_accounts SET balance = ? WHERE account_id=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int res=jdbcTemplate.update(query, new Object[] { userAccounts.getBalance(),userAccounts.getAccountno() });
		if(res>0)
			return true;
		return false;
	}

	@Override
	public void deleteTransaction(Transaction transaction) {
		String query = "Delete from tbl_transactions WHERE transaction_id=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, new Object[] {transaction.getTransactionID() });
	}

}
