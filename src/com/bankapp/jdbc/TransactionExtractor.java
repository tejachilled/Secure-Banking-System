
/**
 * 
 */
package com.bankapp.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.bankapp.model.Transaction;

/**
 * @author manikandan_eshwar
 *
 */
public class TransactionExtractor implements ResultSetExtractor<Transaction>{
	
	@Override
	public Transaction extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Transaction trans = new Transaction();
		trans.setTransactionID(rs.getString("transaction_id"));
		trans.setAccountId(rs.getLong("account_id"));
		trans.setType(rs.getString("transaction_type"));
		trans.setIsCritical(rs.getString("isCritical"));
		trans.setAmount(rs.getDouble("amount"));
	    if(rs.getDate("date_of_transaction_initiation")!=null)
		trans.setDateInitiated(rs.getDate("date_of_transaction_initiation"));
	    if(rs.getDate("date_of_transaction_approval")!=null)
		trans.setDataApproved(rs.getDate("date_of_transaction_approval"));
	    if(rs.getString("remark") !=null){
	    	trans.setRemark(rs.getString("remark"));
	    }
		return trans;
	}

	

}