package com.bankapp.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bankapp.jdbc.TransactionRowMapper;
import com.bankapp.jdbc.UseraccountsRowMapper;
import com.bankapp.model.Transaction;
import com.bankapp.model.Useraccounts;

/**
 * @author sunny
 *
 */
public class MerchantDAOImpl implements MerchantDAO {

	@Autowired
	DataSource dataSource;
	
	/* (non-Javadoc)
	 * @see com.bankapp.dao.MerchantDAO#insertNewTransaction(com.bankapp.model.Transaction)
	 */
	@Override
	public Boolean insertNewTransaction(Transaction transaction, Useraccounts userAccounts) {
		
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
							userAccounts.getUsername() });
		}
		else {
			System.out.println("Inside insert new merchant dao");
			try {
			String sql = "Insert into tbl_transactions (transaction_id,account_id,transaction_type,isCritical,amount,date_of_transaction_initiation,date_of_transaction_approval,internal_user_approval,initiated_by,approved_by) Values(?,?,?,?,?,?,?,?,?,?)";
			res = jdbcTemplate.update(
					sql,
					new Object[] { transaction.getTransactionID(),
							transaction.getAccountId(), transaction.getType(),
							transaction.getIsCritical(),
							transaction.getAmount(),
							transaction.getDateInitiated(), transaction.getDateInitiated(),approvalStatus,
							userAccounts.getUsername(), "Automatic" });
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
	public String getUserName(Long accountId) {
		System.out.println("accountid= "+accountId);
		String sql = "SELECT * FROM tbl_accounts WHERE account_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Useraccounts userAccounts= null;
		try{
			userAccounts = jdbcTemplate.queryForObject(sql,
				new Object[] { accountId }, new UseraccountsRowMapper());
			return userAccounts== null? null : userAccounts.getUsername();
		} catch (EmptyResultDataAccessException e){
			//logger
			return null;
		}
	}

	@Override
	public List<Transaction> getTransactionHistory(String userName) {
		String sql = "SELECT * FROM tbl_transactions WHERE initiated_by = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try{
			List<Transaction> transactions = jdbcTemplate.query(sql,
				new TransactionRowMapper(), new Object[] { userName });
			return transactions;
		} catch (EmptyResultDataAccessException e){
			//logger
			return null;
		}
	}

}
