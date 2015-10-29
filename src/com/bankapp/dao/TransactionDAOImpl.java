package com.bankapp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.helpers.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bankapp.jdbc.GovtRequestRowMapper;
import com.bankapp.jdbc.TransactionRowMapper;
import com.bankapp.jdbc.UseraccountsRowMapper;
import com.bankapp.model.GovtRequestsModel;
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
		List<Transaction> trList = new ArrayList<Transaction>();
		String query = "SELECT transaction_id, account_id, transaction_type, isCritical, amount, date_of_transaction_initiation,  date_of_transaction_approval, remark FROM tbl_transactions where isCritical='L' and internal_user_approval='P' ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		trList = jdbcTemplate.query(query, new TransactionRowMapper());
		return trList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankapp.dao.TransactionDAO#getPendingTransactionsForSM()
	 */
	@Override
	public List<Transaction> getPendingTransactionsForSM() {
		// TODO Auto-generated method stub
		List<Transaction> trList = new ArrayList<Transaction>();
		String query = "SELECT transaction_id, account_id, transaction_type, isCritical, amount, date_of_transaction_initiation,  date_of_transaction_approval,remark FROM tbl_transactions where internal_user_approval='P' ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		trList = jdbcTemplate.query(query, new TransactionRowMapper());
		return trList;
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
	public List<Transaction> getTransactionHistory(String userName) {
		String sql = "SELECT * FROM tbl_transactions WHERE (initiated_by = ? or account_id IN (SELECT account_id FROM tbl_accounts WHERE user_name= ?)) AND internal_user_approval <> 'R'";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			List<Transaction> transactions = jdbcTemplate.query(sql,
				new TransactionRowMapper(), new Object[] { userName, userName });
			return transactions;
		} catch (EmptyResultDataAccessException e) {
			// logger
			return null;
		}
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
	public Boolean approveTransactions(String[] tIdList, String approvedBy,
			String status) {
		Boolean result = false;
		try {
			for (String tId : tIdList) {

				List<Transaction> tempList = getTransaction(tId);
				for (Transaction trans : tempList) {
					approveTransaction(trans, approvedBy, status);
				}

			}

			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		} else {

			System.out.println("Inside insert new transaction dao: mani");
			try {
				String sql = "Insert into tbl_transactions (transaction_id,account_id,transaction_type,isCritical,amount,date_of_transaction_initiation,date_of_transaction_approval,internal_user_approval,initiated_by,approved_by) Values(?,?,?,?,?,?,?,?,?,?)";
				res = jdbcTemplate.update(
						sql,
						new Object[] { transaction.getTransactionID(),
								transaction.getAccountId(),
								transaction.getType(),
								transaction.getIsCritical(),
								transaction.getAmount(),
								transaction.getDateInitiated(),
								transaction.getDateInitiated(), approvalStatus,
								accounts.getUsername(), "Automatic" });
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (res > 0) {

			return true;

		}
		return false;
	}

	@Override
	public List<Useraccounts> getUserAccountsInfoByUserName(String UserName) {
		String sql = "SELECT * FROM tbl_accounts WHERE user_name = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Useraccounts> userAccounts = jdbcTemplate.query(sql,
				new Object[] { UserName }, new UseraccountsRowMapper());
		System.out.println("In DAO");
		return userAccounts;
	}

	@Override
	public Boolean updateBalance(Useraccounts userAccounts) {
		String query = "UPDATE tbl_accounts SET balance = ? WHERE account_id=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int res = jdbcTemplate.update(
				query,
				new Object[] { userAccounts.getBalance(),
						userAccounts.getAccountno() });
		if (res > 0)
			return true;
		return false;
	}

	@Override
	public void deleteTransaction(Transaction transaction) {
		String query = "Delete from tbl_transactions WHERE transaction_id=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query,
				new Object[] { transaction.getTransactionID() });
	}

	@Override
	public List<Transaction> getTransaction(String tId) {
		String query = "SELECT transaction_id, account_id, transaction_type, isCritical, amount, date_of_transaction_initiation,  date_of_transaction_approval, remark FROM tbl_transactions where transaction_id=? and internal_user_approval='P' ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate.query(query, new TransactionRowMapper(), tId);
	}

	private void approveTransaction(Transaction trans, String approvedBy,
			String status) {
		String query = "UPDATE tbl_transactions SET internal_user_approval = ? , date_of_transaction_approval=?, approved_by = ? WHERE transaction_id=? ";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		int res = jdbcTemplate.update(query, new Object[] { status, new Date(),
				approvedBy, trans.getTransactionID() });
		if (res > 0 && status == "A") {
			if (trans.getType().equals("C")) {
				String balquery = "UPDATE tbl_accounts SET balance = balance+? WHERE account_id=? ";
				res = jdbcTemplate
						.update(balquery, new Object[] { trans.getAmount(),
								trans.getAccountId() });
			} else if (trans.getType().equals("D")) {
				Double availBal = getAvailBal(trans.getAccountId());
				if (availBal - trans.getAmount() >= 500) {
					String balquery = "UPDATE tbl_accounts SET balance = balance - ? WHERE account_id=? ";
					res = jdbcTemplate.update(
							balquery,
							new Object[] { trans.getAmount(),
									trans.getAccountId() });
				} else {
					query = "UPDATE tbl_transactions SET internal_user_approval = 'R' , date_of_transaction_approval=?, approved_by = ? WHERE transaction_id=? ";
					jdbcTemplate = new JdbcTemplate(dataSource);
					res = jdbcTemplate.update(query, new Object[] { status,
							new Date(), approvedBy, trans.getTransactionID() });
				}
			}

		}
	}

	@Override
	public Double getAvailBal(long accountId) {
		String sql = "SELECT * FROM tbl_accounts WHERE account_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Useraccounts> userAccounts = jdbcTemplate.query(sql,
				new Object[] { accountId }, new UseraccountsRowMapper());
		return userAccounts.get(0).getBalance();
	}

	@Override
	public List<Transaction> getMerchTransactions(Long accid) {
		String sql = "SELECT * FROM tbl_transactions WHERE account_id = ? AND iscritical='M' AND approved_by is null";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			List<Transaction> transactions = jdbcTemplate.query(sql,
					new TransactionRowMapper(), new Object[] { accid });
			return transactions;
		} catch (EmptyResultDataAccessException e) {
			// logger
			return null;
		}
	}

	@Override
	public Useraccounts getUserAccountsInfoByAccid(Long accid) {
		String sql = "SELECT * FROM tbl_accounts WHERE account_id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Useraccounts userAccounts = jdbcTemplate.queryForObject(sql,
				new Object[] { accid }, new UseraccountsRowMapper());
		return userAccounts;
	}
}
