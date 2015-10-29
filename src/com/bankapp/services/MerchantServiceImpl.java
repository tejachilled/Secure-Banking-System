/**
 * 
 */
package com.bankapp.services;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bankapp.dao.MerchantDAOImpl;
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
	
	private static final Logger logger = Logger.getLogger(MerchantService.class);
	/* (non-Javadoc)
	 * @see com.bankapp.services.MerchantService#isAccountValid(java.lang.Long)
	 */
	@Override
	public boolean isAccountValid(Long accountId, String userName) {
		//merchant cannot put its a/c id for credit/deposit
		String user= merchantDAO.getUserName(accountId);
		return (user!=null);
	}

	@Override
	public boolean insertNewTransaction(Long accountId, Double amount, String remark, String type,
			String userName, String accountType, Useraccounts accUserAccount, String isCritical) {
		try{
			transaction.setAccountId(accountId);
			transaction.setAmount(amount);
			transaction.setRemark(remark);
			transaction.setType(type);
			transaction.setTransactionID(UUID.randomUUID().toString());
			transaction.setAccType(accountType);
			transaction.setIsCritical(isCritical);
			return merchantDAO.insertNewTransaction(transaction, accUserAccount);
		} catch(Exception e){
			System.out.println("Error in inserting merchant txn :: "+e);
			logger.error("Error in inserting merchant txn :: "+e);
			//do logging
			return false;
		}
	}

	@Override
	public List<Transaction> getTransactionHistory(String userName) {
		return merchantDAO.getTransactionHistory(userName);
	}

	@Override
	public List<Useraccounts> getUserAccountsInfoByUserName(String userName) {
		return merchantDAO.getUserAccountsInfoByUserName(userName);
	}

	public Boolean updateBalance(Useraccounts merchAccount, double balance) {
		return merchantDAO.updateBalance(merchAccount, balance);
	}

	public Useraccounts getUserAccountsInfoByAccountId(Long accountId) {
		return merchantDAO.getUserAccountsInfoByAccountId(accountId);
	}

}

