/**
 * 
 */
package com.bankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bankapp.dao.MerchantDAOImpl;
import com.bankapp.model.Transaction;

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
	public boolean isAccountValid(Long accountId) {
		return merchantDAO.getUserName(accountId)!=null;
	}

	@Override
	public boolean insertNewTransaction(Long accountId, Double amount, String remark, String type) {
		try{
			transaction.setAccountId(accountId);
			transaction.setAmount(amount);
			transaction.setRemark(remark);
			transaction.setType(type);
			//transaction.setIsCritical(isCritical);
		} catch(Exception e){
			System.out.println(e);
			//do logging
			return false;
		}
		return merchantDAO.insertNewTransaction(transaction);
	}

	@Override
	public List<Transaction> getTransactionHistory(Long accountId) {
		return merchantDAO.getTransactionHistory(accountId);
	}

}
