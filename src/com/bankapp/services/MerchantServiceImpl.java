/**
 * 
 */
package com.bankapp.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bankapp.dao.MerchantDAOImpl;
import com.bankapp.jdbc.UseraccountsRowMapper;
import com.bankapp.model.Transaction;
import com.bankapp.model.Useraccounts;

/**
 * @author sunny
 *
 */
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	MerchantDAOImpl merchantDAO;
	@Autowired
	Transaction transaction;
	
	/* (non-Javadoc)
	 * @see com.bankapp.services.MerchantService#isAccountValid(java.lang.Long)
	 */
	@Override
	public boolean isAccountValid(Long accountId, String userName) {
		//merchant cannot put its a/c id for credit/deposit
		String user= merchantDAO.getUserName(accountId);
		return (user!=null && !user.equals(userName));
	}

	@Override
	public boolean insertNewTransaction(Long accountId, Double amount, String remark, String type, String userName) {
		
		Useraccounts userAccounts=null;
		try{
			userAccounts = getUserAccountsInfoByUserName(userName);
			transaction.setAccountId(accountId);
			transaction.setAmount(amount);
			transaction.setRemark(remark);
			transaction.setType(type);
			transaction.setTransactionID(UUID.randomUUID().toString());
			transaction.setIsCritical("M"); // all merch txns are critical
		} catch(Exception e){
			System.out.println(e);
			//do logging
			return false;
		}
		return merchantDAO.insertNewTransaction(transaction, userAccounts);
	}

	@Override
	public List<Transaction> getTransactionHistory(String userName) {
		return merchantDAO.getTransactionHistory(userName);
	}

	@Override
	public Useraccounts getUserAccountsInfoByUserName(String userName) {
		return merchantDAO.getUserAccountsInfoByUserName(userName);
	}

}
